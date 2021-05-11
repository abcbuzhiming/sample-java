youming com.youming.retrofit;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by PandaQ on 2016/11/2.
 * email : 767807368@qq.coRetrofit
 */

public class HttpInterceptor implements Interceptor {



    @Override
    public Response intercept(Chain chain) throws IOException {
    	
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        request = builder
                .addHeader("version", "1.0.0")
                // .addHeader("Cookie", Contast.Cookie_my)
                .build();
        if (request.method().equals("GET")) {
            //添加公共参数
            HttpUrl httpUrl = request.url()
                    .newBuilder()
                    .addQueryParameter("version", "v1")
                    .build();
            request = builder.url(httpUrl).build();
            System.out.println("get url:" + request.url().toString());
        } else if (request.method().equals("POST")) {
           /*
            //参数就要针对body做操作,我这里针对From表单做操作
            if (request.body() instanceof FormBody) {
                // 构造新的请求表单
                FormBody.Builder builder = new FormBody.Builder();

                FormBody body = (FormBody) request.body();
                //将以前的参数添加
                for (int i = 0; i < body.size(); i++) {
                    builder.add(body.encodedName(i), body.encodedValue(i));
                }
                //追加新的参数
                builder.add("newKye", "newValue");
                request = request.newBuilder().post(builder.build()).build();//构造新的请求体
            }

            Response response = chain.proceed(request);
            return response;*/

            if (request.body() instanceof FormBody) {
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                FormBody formBody = (FormBody) request.body();

                //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
                for (int i = 0; i < formBody.size(); i++) {
                    bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                }
                formBody = bodyBuilder
                        .addEncoded("version", "v1")
                        .build();
                request = builder
                        // .addHeader("Cookie", Contast.Cookie_my)
                        .post(formBody).build();
                System.out.println("post url:" + request.url().toString());
            }
        }
        Response response = null;
        response = chain.proceed(builder.build());


        return response;
    }

    
}
