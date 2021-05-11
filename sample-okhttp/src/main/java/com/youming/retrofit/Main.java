youming com.youming.retrofit;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 此方法用来验证Retrofit2 + okHttp 验证可能出现的多次请求bug，关键字Retrofit Duplicate request
 * https://github.com/square/retrofit/issues/1724
 * https://github.com/square/retrofit/issues/2367
 */
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello Retrofit2");
		Map<String, String> params = new HashMap<>();
		Main main = new Main();
		String url = "http://127.0.0.1:8080/";
		RetrofitService retrofitService = main.getDataService(url);
		
		Call<String> callHello = retrofitService.hello();
		// 步骤:发送网络请求(异步)
		callHello.enqueue(new Callback<String>() {
			@Override
			public void onResponse(Call<String> call, Response<String> response) {
				// Log.i("jin",response.message().toString());
				// Log.i("jin",response.body().getResults().get(1).getUrl());
				System.out.println("请求结果:" + response.message().toString());
			}

			@Override
			public void onFailure(Call<String> call, Throwable t) {
				// TODO Auto-generated method stub
				System.out.println("请求失败:" + t.getMessage());
			}
		});

		retrofitService = main.getDataService(url);
		Call<String> callGet = retrofitService.get();
		// 步骤:发送网络请求(异步)
		callGet.enqueue(new Callback<String>() {
			@Override
			public void onResponse(Call<String> call, Response<String> response) {
				// Log.i("jin",response.message().toString());
				// Log.i("jin",response.body().getResults().get(1).getUrl());
				System.out.println("请求结果:" + response.message().toString());
			}

			@Override
			public void onFailure(Call<String> call, Throwable t) {
				// TODO Auto-generated method stub
				System.out.println("请求失败:" + t.getMessage());
			}
		});

		System.out.println("请求结束");
	}

	public RetrofitService getDataService(String url) {
		// SSLSocketFactory.getSocketFactory().setHostnameVerifier(new
		// AllowAllHostnameVerifier());//6.0以下https无效
		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
				.readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
				// 添加应用拦截器
				.addInterceptor(new HttpInterceptor())
				// .addInterceptor(new HttpHeadInterceptor())
				/*
				 * .addInterceptor(new Interceptor() {
				 * 
				 * @Override public Response intercept(Chain chain) throws IOException {
				 * 
				 * Response resp = chain.proceed(chain.request()); List<String> cookies =
				 * resp.headers("Set-Cookie"); String cookieStr = ""; if (cookies != null &&
				 * cookies.size() > 0) { for (int i = 0; i < cookies.size(); i++) { cookieStr +=
				 * cookies.get(i); } } String strcookie = cookies.get(1); String[] strarray=
				 * strcookie.split(";"); String userCookie = strarray[0].substring(8);
				 * 
				 * Log.e("strnew", userCookie+""); new UserData(context).saveCookie(userCookie);
				 * return resp; } })
				 */
				// .sslSocketFactory(createSSLSocketFactory())
				// 添加网络拦截器
				.build();

		Retrofit retrofit = new Retrofit.Builder().baseUrl(url)
				// 将client与retrofit关联
				.client(client)
				// .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				// .addConverterFactory(GsonConverterFactory.create())
				// .addConverterFactory(new NullOnEmptyConverterFactory())
				.addConverterFactory(GsonConverterFactory.create(getGson())).build();
		RetrofitService retrofitService = retrofit.create(RetrofitService.class);

		return retrofitService;
	}

	private Gson getGson() {

		GsonBuilder builder = new GsonBuilder();
		builder.setLenient();
		// builder.setFieldNamingStrategy(new AnnotateNaming());
		builder.serializeNulls();
		Gson gson = builder.create();

		return gson;
	}

}
