youming com.youming.sentmultiple;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientTwo {
	private static final String BASE_URL_API = "http://127.0.0.1:8080/";
	private static Retrofit retrofit;
	
	public static <T> T createService(Class<T> clazz) {
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
	    logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

	    //File cacheDir = new File(context.getCacheDir(), HttpConfig.RESPONSE_CACHE);
	    OkHttpClient sOkHttpClient = new OkHttpClient.Builder()
	            //.cache(new Cache(cacheDir, HttpConfig.RESPONSE_CACHE_SIZE))
	            .connectTimeout(10, TimeUnit.MILLISECONDS)
	            .readTimeout(15, TimeUnit.MILLISECONDS)
	            .writeTimeout(15, TimeUnit.MILLISECONDS)
	            .addInterceptor(logging)
	            .build();

	    retrofit = new Retrofit.Builder()
	            .baseUrl(BASE_URL_API)
	            .addConverterFactory(GsonConverterFactory.create())
	            .client(sOkHttpClient)
	            .build();
		
	    return retrofit.create(clazz);
	}
}
