youming com.youming.sentmultiple;

import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
	private static final String BASE_URL_API = "http://127.0.0.1:8080/";
	public static final String BASE_URL = "http://192.168.86.140/";
	private static final String TAG = ApiClient.class.getSimpleName();

	private static Gson gson = new GsonBuilder().setLenient().create();

	private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL_API)
			.addConverterFactory(GsonConverterFactory.create(gson));

	private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
			.setLevel(HttpLoggingInterceptor.Level.BASIC);

	private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS)
			.connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS);

	private static Retrofit retrofit = builder.build();

	static Retrofit getRetrofit() {
		return retrofit;
	}


	public static <S> S createService(Class<S> serviceClass) {
		if (!httpClient.interceptors().contains(logging)) {
			httpClient.addInterceptor(logging);
        }
		retrofit = builder.client(httpClient.build()).build();

		return retrofit.create(serviceClass);
	}
}
