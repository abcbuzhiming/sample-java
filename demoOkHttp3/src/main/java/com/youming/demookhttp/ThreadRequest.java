package com.youming.demookhttp;

import okhttp3.OkHttpClient;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.crypto.spec.RC2ParameterSpec;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ThreadRequest implements Runnable {

	private final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(90, TimeUnit.SECONDS).build(); // 最长90S读超时

	private String serverId;
	private String sessionId;
	private String urlSockjs = "http://221.234.36.70:18080/teacherTrainingSystem/websocket/sockjs/"; // 注意,实际开发时，url应该读取自配置文件，这里简化为直接赋值了
	// private String urlSockjs =
	// "http://127.0.0.1:8080/teacherTrainingSystem/websocket/sockjs/";

	private String cookie = "JCOOKIE_TEACH_PLATFORM=AjAEeAxkHYbnvVGI9QIp2XAqZoeRJc2t6BF6WTlEolcJqXhUsMaFSbjtzOEiaeJugTDUIqpoGFdk0zvmryk8n1u//lU7+jLNQwAmAcQ0WdvyNzRTkojtQ/qt1vt8mRqnvMGJlwI/f0WL7C8WAhS2Z+WKN1sASFD/T1/kzSmaO3sXQJO5ETusWjhwq2Pg2ltZH0pnuPTGmEvHIiTF3fqelT12t8MBhPH2YTB7skvuQ16NvxhU1Ygh9kudplrNY13kFcmGf32E480JvOHa8yTJcxQy9MThIi2lnaE2E3DQJUtV1snnCxq/jfc6anJjWbOZnMVWPGzqjiDnM7iB8LLnnyL5UpuqABDuBDxhB+gBYcH1vx9P2EZj6fAEoED0s+IeNQi70CKPCZivxnYSJFh7FOhf4BctLobQ6xQ3EPf2vVt4CgUiLDKYOkGSB4yIo6AX9e2SrMhOgsGnSUT1F7h8TREXCu5m4/IeOJH/O2f7G833fuELNjbJYmxHjNtx4AYrcwgVwd1FKwsqtIwuFekbtAEOD/5z9c/oKc7zgzNdFrYtCVBYzttQ0YT5AsFhhi10l2MHN7LotcjnvKwbOZqDoU5QVXBNU40NQTi+tZFDL72Ty3V9cDiC8Nj9BlzaXsrU/KRa5IQos+U9RaKi4lebxGdjfhz5VOWIvILK5C2IXqEINoZBcktE5cgOlzk90pc5Obzw6svmSf7X28MBtLtXZDb2S0pT1TOWlEC0YBAtTq+/iCG/5zmDOQRMOIm1+FyYGlqNzST1CyuYEx8Qkoi8Kg==;"; // 注意,实际在移动端开发时，cookie应该是外部传入的，这里简化了为直接赋值了

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// String baseStr = "0123456789";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 3; i++) {
			int temp = (int) Math.random() * 9;
			sb.append(temp);
		}
		serverId = sb.toString(); // 计算得到sockjs的serverId(3位随机数字)
		sb.setLength(0); // 清空

		String baseStr = "0123456789abcdefghijklmnopqrstuvwxyz";
		int len = baseStr.length();
		for (int i = 0; i < 8; i++) {
			sb.append(baseStr.charAt((int) Math.round(Math.random() * (len - 1))));
		}
		sessionId = sb.toString(); // 计算得到sockjs的sessionId(8位随机字符串)

		while (true) {
			// System.out.println("我是异步线程,线程Id为:" + Thread.currentThread().getId() +
			// ";线程总数:" + Thread.activeCount());
			String pushData = null;

			try {
				pushData = this.request(); // 得到返回数据
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				if (e.getMessage().equals("404")) { // 404属于长久没有消息,连接被服务器断开并清除,所以404错误应立即重新连接，其它错误等待10S再连接
					try {
						Thread.sleep(10 * 1000); // 如果出错，睡眠10秒再开始
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				continue; // 如果出错，不继续向后执行
			}

			if (pushData == null || pushData.length() < 1) { // 这种情况属于推送数据错误

			} else if (pushData.substring(0, 1).equals("o") || pushData.substring(0, 1).equals("h")) { // 第一个字符是o或者h,o是第一次连接时返回的正常状态提示,可以开始下一次连接；h代表没有数据,超时返回,应立即开始下一次长轮询
				System.out.println("非数据推送,立即发起下一次连接");

			} else if (pushData.substring(0, 1).equals("c")) { // 没有登录或者cookie失效，轮询必须终止
				System.out.println("需要登录,长轮询循环退出");
				/*
				 * runOnUiThread(new Runnable() {
				 * 
				 * @Override public void run() { //拉起登录页面 } });
				 */

				break;
			} else if (pushData.substring(0, 1).equals("a")) { // 真实数据,一般格式为a["message"]
				String data = pushData.substring(3, pushData.length() - 3);
				System.out.println("收到推送数据:" + data);

				/*
				 * runOnUiThread(new Runnable() {
				 * 
				 * @Override public void run() { //将数据data提交给主界面,主界面做出提醒动画或出现图标 } });
				 */
			}

		}

	}

	protected String request() throws Exception {
		// Request request = new
		// Request.Builder().url("https://publicobject.com/helloworld.txt").build();
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		RequestBody body = RequestBody.create(JSON, "");
		String url = this.urlSockjs + this.serverId + "/" + this.sessionId + "/xhr?t=" + System.currentTimeMillis();
		System.out.println("发起轮询:" + url);
		Request request = new Request.Builder().url(url).header("Cookie", this.cookie).post(body).build();

		try (Response response = client.newCall(request).execute()) {
			Headers responseHeaders = response.headers();
			if (!response.isSuccessful()) {
				for (int i = 0; i < responseHeaders.size(); i++) {
					System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
					
				}
				throw new IOException(response.code() + ""); // 抛出错误code码
			}

			/*
			 * for (int i = 0; i < responseHeaders.size(); i++) {
			 * System.out.println(responseHeaders.name(i) + ": " +
			 * responseHeaders.value(i)); } System.out.println(response.body().string());
			 */
			// System.out.println(responseHeaders.name(1) + ": " +
			// responseHeaders.value(1));
			String returnData = response.body().string();
			System.out.println("返回数据:" + returnData);
			return returnData;
		}
	}

}
