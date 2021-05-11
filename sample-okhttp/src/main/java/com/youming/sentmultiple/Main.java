youming com.youming.sentmultiple;

import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("等待输入，按Q退出");
			String name = sc.nextLine();  //读取字符串型输入
			if (name.equals("q")) {
				break;
			}
			call();
			
		}
	}
	
	public static void call() {
		APIService apiService = ApiClientTwo.createService(APIService.class);
        Call<String> call = apiService.hello();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                	System.out.println("请求成功:" + response.message().toString());
                }
                else{
                	System.out.println("请求失败:" + response.message().toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
            	System.out.println("请求失败:" + throwable.getMessage());
            }
        });
	}

}
