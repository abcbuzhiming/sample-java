package com.youming.bootjsonbody.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;





public class JsonContentCachingRequestWrapper extends HttpServletRequestWrapper{

    private byte[] body;

    private BufferedReader reader;

    private ServletInputStream inputStream;

    public JsonContentCachingRequestWrapper(HttpServletRequest request) throws IOException{
        super(request);
        loadBody(request);
    }

    private void loadBody(HttpServletRequest request) throws IOException{
        body = IOUtils.toByteArray(request.getInputStream());		//读取数据流
        inputStream = new RequestCachingInputStream(body);		//写回,使其能重复使用
        
    }

    public byte[] getBody() {
        return body;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
    	//System.out.println("获取ServletInputStream，boye数据为" + new String(body));
    	/*
        if (inputStream != null) {
            return inputStream;
        }
        return super.getInputStream();
        */
    	ServletInputStream inputStream = new RequestCachingInputStream(body);
        return inputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(inputStream, getCharacterEncoding()));
        }
        return reader;
    }

    private static class RequestCachingInputStream extends ServletInputStream {

        private final ByteArrayInputStream byteArrayInputStream;

        public RequestCachingInputStream(byte[] bytes) {
            this.byteArrayInputStream = new ByteArrayInputStream(bytes);
        }
        @Override
        public int read() throws IOException {
            return byteArrayInputStream.read();
        }

        @Override
        public boolean isFinished() {
            return byteArrayInputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readlistener) {
        }

    }

}
