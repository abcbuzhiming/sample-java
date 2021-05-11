youming com.youming.okhttp;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 发送多个文件
 * */
public class SendMultipleFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello SendMultipleFile");
		File fileVideo = new File("F:/01.mp4");
		RequestBody fileVideoBody = RequestBody.create(MediaType.parse("application/octet-stream"),fileVideo);
		File fileAudio = new File("F:/01.wav");
		RequestBody fileAudioBody = RequestBody.create(MediaType.parse("application/octet-stream"),fileAudio);
		
		MultipartBody.Builder builderMultipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
		builderMultipartBody.addFormDataPart("file","01.mp4",fileVideoBody);		//file是参数名，01.mp4是文件名
		builderMultipartBody.addFormDataPart("file","01.wav",fileAudioBody);
		builderMultipartBody.addFormDataPart("pushMsgId","1");		//添加一个参数pushMsgId
		
		Request request = new Request.Builder().post(builderMultipartBody.build())
                .url("http://127.0.0.1:8080/ccnu-study-schedule-butler-system/objectStorage/uploadStudySituationFile")
                .build();

		OkHttpClient okHttpClient = new OkHttpClient();
		try {
			String string = okHttpClient.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).build().newCall(request).execute().body().string();
			System.out.println(string);
			//结果返回范例:{"msg":"SUCCESS","result":{"pathList":["studySituation/1/01.mp4","studySituation/1/01.wav"]},"code":0,"msgZHCN":"调用成功"}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("end SendMultipleFile");
		
	}

}
