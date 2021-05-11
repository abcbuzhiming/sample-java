package com.youming.bootjsonbody.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.youming.bootjsonbody.annotation.RequestJson;
import com.youming.bootjsonbody.filter.JsonContentCachingRequestWrapper;
import com.youming.bootjsonbody.utils.JsonTransformUtils;

/**
 * Request Body json参数处理器
 * */
@Component
public class RequestJsonHandler implements HandlerMethodArgumentResolver {

	private static final Logger logger = LoggerFactory.getLogger(RequestJsonHandler.class);
	/**
     * json类型
     */
    private static final String JSON_CONTENT_TYPE = "application/json";
    
	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		// TODO Auto-generated method stub
		 //只有被reqeustJson注解标记的参数才能进入
        return methodParameter.hasParameterAnnotation(RequestJson.class);
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
			NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
		
        String contentType = request.getContentType();
        // 不是json
        if (!JSON_CONTENT_TYPE.equalsIgnoreCase(contentType)){
            return null;
        }
        String jsonStr = this.getRequestBody(request);
        if (jsonStr == null) {
			return null;
		}
        
        JsonNode jsonNodeRoot = JsonTransformUtils.toJsonNode(jsonStr);
        if (jsonNodeRoot == null) {
			return null;
		}
        
        RequestJson requestJson = methodParameter.getParameterAnnotation(RequestJson.class);		//得到注解
        String fieldName = requestJson.name();		//获得注解的参数名称
        if (fieldName == null || "".equals(fieldName)){		//为空则和参数名相同
            fieldName = methodParameter.getParameterName();
        }
        Class<?> parameterType = methodParameter.getParameterType();		//获得参数类型
        
        JsonNode jsonNodeField = jsonNodeRoot.findValue(fieldName);		//找到某个字段的值
        if (jsonNodeField == null) {		//没找到退出
			return null;
		}
        Object obj = JsonTransformUtils.getObjectMapper().convertValue(jsonNodeField, parameterType);
        return obj;
	}
	
	
	
    
	/**
     * 获取request中的body数据
     * @param request
	 * @throws IOException 
     */
    private String getRequestBody(HttpServletRequest httpServletRequest) throws IOException{
    	//JsonContentCachingRequestWrapper jsonContentCachingRequestWrapper = new JsonContentCachingRequestWrapper(httpServletRequest);
        //String body = IOUtils.toString(jsonContentCachingRequestWrapper.getBody(),httpServletRequest.getCharacterEncoding());
    	byte[] b = IOUtils.toByteArray(httpServletRequest.getInputStream());
    	String body = IOUtils.toString(b,httpServletRequest.getCharacterEncoding());
        logger.info("body: " + body);
        return body;
    }

}
