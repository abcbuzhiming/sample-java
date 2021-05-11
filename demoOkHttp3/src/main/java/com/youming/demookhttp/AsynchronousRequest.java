package com.youming.demookhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AsynchronousRequest {
	private final OkHttpClient client = new OkHttpClient();

	public void run(){
		System.out.println("我是主线程,线程Id为:" + Thread.currentThread().getId() + ";线程总数:" + Thread.activeCount());
		Request request = new Request.Builder().url("http://publicobject.com/helloworld.txt").build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				e.printStackTrace();
				System.out.println("我是异步线程,线程Id为:" + Thread.currentThread().getId() + ";线程总数:" + Thread.activeCount());
				run();
			}

			@Override
			public void onResponse(Call call, Response response){
				System.out.println("我是异步线程,线程Id为:" + Thread.currentThread().getId() + ";线程总数:" + Thread.activeCount());
				try (ResponseBody responseBody = response.body()) {
					if (response.isSuccessful()) {
						Headers responseHeaders = response.headers();
						/*
						for (int i = 0, size = responseHeaders.size(); i < size; i++) {
							System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
						}
						System.out.println(responseBody.string());
						*/
						System.out.println(responseHeaders.name(1) + ": " + responseHeaders.value(1));
						//System.out.println(responseBody.string());
					}else {
						//throw new IOException("Unexpected code " + response);
						System.out.println("Unexpected code " + response);
					}
				}catch (Exception e) {
			        e.printStackTrace();
			        System.out.println("Exception msg:" + e.getMessage());
			    }
				
				run();
			}
		});
	}
}
