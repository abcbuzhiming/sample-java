youming com.youming.retrofit;





import java.util.List;
import java.util.Map;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface RetrofitService {

    /**
     * get接口测试1
     * @param mobile
     * @param pwd
     * @return
     */
    @GET("sample/hello")
    public Call<String> hello();

    /**
     * get接口测试2
     * @param token
     * @return
     */
    @GET("sample/get")
    public Call<String> get();

    
    /**
     * post接口测试
     * @param token
     * @return
     */
    @POST("sample/update")
    public Call<String> update();
    

    


}
