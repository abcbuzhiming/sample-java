package com.youming.demookhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class App {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Hello OkHttp3");
		//new SynchronousRequest().run();
		//new AsynchronousRequest().run();
		
		ThreadRequest threadRequest = new ThreadRequest();
		Thread t1 = new Thread(threadRequest); 
		t1.start();		
	}

	

}
