youming com.youming.push.jiguang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.DefaultResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 极光推送示例代码
 * update by youming
 * 
 * */
public class Main {
	
	private final static String appKey = "";
	private final static String masterSecret = "";

	public static void clientSend(List<PushPayload> list) {
		if (list == null || list.size() == 0) {
			return;
		}
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);
		try {
			for (PushPayload pushPayload : list) {
				jpushClient.sendPush(pushPayload);
			}
		} catch (APIConnectionException e) {
			e.printStackTrace();
			System.out.println("jpushException!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!---------APIConnectionException" + new Date());
		} catch (APIRequestException e) {
			e.printStackTrace();
			System.out.println("jpushException!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!---------APIRequestException" + new Date());
		}
	}

	/**
	 * 推送给多人 按id 别名等推送有单次调用推送目标上限 1次1000
	 * 
	 * @param identifierToken 别名集合
	 * @param deviceType      设备类型
	 * @param content         内容
	 * @param title           标题
	 * @param extras          附加内容
	 * @param isNotification  true 通知 false 自定义消息
	 */
	public static void push(List<String> identifierTokens, Integer deviceType, String content, String title,
			Map<String, String> extras, boolean isNotification) {
		List<List<String>> tokens = splitList(identifierTokens, 999);
		if (identifierTokens == null || deviceType == null || identifierTokens.size() == 0 || tokens == null
				|| tokens.size() == 0) {
			return;
		}
		List<PushPayload> payloadList = new ArrayList<PushPayload>();
		// 手机设备类型 (1.ios 2.安卓 3.win) 3目前不管
		if (deviceType == 1) {
			for (List<String> list : tokens) {
				payloadList.add(getIOSPushPayload(list, content, title, extras, isNotification));
			}
		} else if (deviceType == 2) {
			for (List<String> list : tokens) {
				payloadList.add(getAndroidPushPayload(list, content, title, extras, isNotification));
			}
		} else {
			return;
		}
		clientSend(payloadList);
	}

	public static void pushIos(List<String> identifierTokens, String content, String title, Map<String, String> extras,
			boolean isNotification) {
		List<List<String>> tokens = splitList(identifierTokens, 999);
		if (identifierTokens == null || identifierTokens.size() == 0 || tokens == null || tokens.size() == 0) {
			return;
		}
		List<PushPayload> payloadList = new ArrayList<PushPayload>();
		// 手机设备类型 (1.ios 2.安卓 3.win) 3目前不管
		for (List<String> list : tokens) {
			payloadList.add(getIOSPushPayload(list, content, title, extras, isNotification));
		}
		clientSend(payloadList);
	}

	public static void pushAndroid(List<String> identifierTokens, String content, String title,
			Map<String, String> extras, boolean isNotification) {
		List<List<String>> tokens = splitList(identifierTokens, 999);
		if (identifierTokens == null || identifierTokens.size() == 0 || tokens == null || tokens.size() == 0) {
			return;
		}
		List<PushPayload> payloadList = new ArrayList<PushPayload>();
		// 手机设备类型 (1.ios 2.安卓 3.win) 3目前不管
		for (List<String> list : tokens) {
			payloadList.add(getAndroidPushPayload(list, content, title, extras, isNotification));
		}
		clientSend(payloadList);
	}

	/**	推送给Android
	 * @param identifierToken 别名集合
	 * @param content 内容
	 * @param title	标题
	 * @param extras	附加内容
	 * @param isNotification	true 通知  false 自定义消息
	 * @return
	 */
	public static PushPayload getAndroidPushPayload(Collection<String> identifierToken,String content,String title,Map<String, String> extras,boolean isNotification) {
		Builder builder=PushPayload.newBuilder();   					//构建推送对象
		builder.setPlatform(Platform.android())				  //指定推送的平台
        		.setAudience(Audience.registrationId(identifierToken))					  //指定推送的对象
        		//.setOptions(Options.newBuilder().setApnsProduction(false).build())			   //推送参数  安卓目前忽略
        		;
		if(isNotification){
			builder.setNotification(Notification.android(content,title,extras));      //指定通知
		}else{
			builder.setMessage(Message.newBuilder().setTitle(title).setMsgContent(content).addExtras(extras).build());
		}
		return builder.build();
	            
	 }
	
	/**	推送给IOS
	 * @param identifierToken 别名集合
	 * @param content 内容
	 * @param title	标题
	 * @param extras	附加内容
	 * @param isNotification	true 通知  false 自定义消息
	 * @return
	 */
	public static PushPayload getIOSPushPayload(Collection<String> identifierToken,String content,String title,Map<String, String> extras,boolean isNotification) {
		Builder builder=PushPayload.newBuilder();
			builder.setPlatform(Platform.ios()) 
	        .setAudience(Audience.registrationId(identifierToken)) 
	        .setOptions(Options.newBuilder().setApnsProduction(true).build());//setApnsProduction true生产环境 false开发环境
		if(isNotification){
			builder.setNotification(Notification.newBuilder()
            		.addPlatformNotification(IosNotification.newBuilder()
                            .setAlert(content)
                            .setBadge(+1)
                            .setSound("happy") 
                            .addExtras(extras)
                            .build())
                    .build());
		}else{
			builder.setMessage(Message.newBuilder().setMsgContent(content).addExtras(extras).build());
		}
	      return  builder.build();
	}
	
	public static PushPayload getPushPayload(Collection<String> identifierToken,String content,String title,Map<String, String> extras,boolean isNotification) {
		Builder builder=PushPayload.newBuilder();
			builder.setPlatform(Platform.all()) 
	        .setAudience(Audience.registrationId(identifierToken)) 
	        .setOptions(Options.newBuilder().setApnsProduction(true).build());//setApnsProduction true生产环境 false开发环境
			if(isNotification){
				builder.setNotification(Notification.newBuilder()
	            		.addPlatformNotification(IosNotification.newBuilder()
	                            .setAlert(content)
	                            .setBadge(+1)
	                            .setSound("happy") 
	                            .addExtras(extras)
	                            .build())
	            		.addPlatformNotification(AndroidNotification.newBuilder().setAlert(content).addExtras(extras)
                        .build())
	                    .build());
			}else{
				builder.setMessage(Message.newBuilder().setMsgContent(content).addExtras(extras).build());
			}
			return  builder.build();
	}
	
	/**推送给所有人
	 * @param content 推送内容
	 * @return
	 */
	public static PushPayload pushAll(String content) {
		return PushPayload.alertAll(content);
	}
	
	/**广播
	 * @param content	内容
	 * @param title	标题
	 * @param extras	附加内容
	 * @param isNotification	true 通知  false 自定义消息
	 * @param type	null为广播 1为ios 2为安卓
	 * @return
	 */
	public static PushPayload broadcast(String content,String title,Map<String, String> extras,boolean isNotification,Integer type) {
		Builder builder=PushPayload.newBuilder();
			builder.setPlatform(Platform.all()) 
	        .setAudience(Audience.all()) 
	        .setOptions(Options.newBuilder().setApnsProduction(true).build());//setApnsProduction true生产环境 false开发环境
			if(isNotification){
				cn.jpush.api.push.model.notification.Notification.Builder b =Notification.newBuilder();
				if(type==null||type==1){
					b.addPlatformNotification(IosNotification.newBuilder()
                            .setAlert(content)
                            .setBadge(+1)
                            .setSound("happy") 
                            .addExtras(extras)
                            .build());
				}
				if(type==null||type==2){
					b.addPlatformNotification(AndroidNotification.newBuilder().setAlert(content).addExtras(extras).build());
				}
				builder.setNotification(b.build());
			}else{
				builder.setMessage(Message.newBuilder().setMsgContent(content).addExtras(extras).build());
			}
			return  builder.build();
	}
	
	public static void bindPhoneNum(String id,String phoneNum){
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);
	    try {
	        DefaultResult result =  jpushClient.bindMobile(id,phoneNum);
	        System.out.println("Got result " + result);
	    } catch (APIConnectionException e) {
	        System.out.printf("Connection error. Should retry later. %s", e);
	    } catch (APIRequestException e) {	    	
	        System.out.printf("Error response from JPush server. Should review and fix it. %s", e);
	        System.out.println();
	        System.out.println("HTTP Status: " + e.getStatus());
	        System.out.println("Error Code: " + e.getErrorCode());
	        System.out.println("Error Message: " + e.getErrorMessage());
	    }
	}
	
	public static <T> List<List<T>> splitList(List<T> orginList,Integer listCount){
		if(orginList==null||orginList.size()==0){
			return null;
		}
        List<List<T>> ret=new ArrayList<List<T>>();
        int size=orginList.size();
        if(size<=listCount){ //数据量不足count指定的大小
            ret.add(orginList);
        }else{
            int pre=size/listCount;
            int last=size%listCount;
            //前面pre个集合，每个大小都是count个元素
            for(int i=0;i<pre;i++){
                List<T> itemList=new ArrayList<T>();
                for(int j=0;j<listCount;j++){
                    itemList.add(orginList.get(i*listCount+j));
                }
                ret.add(itemList);
            }
            //last的进行处理
            if(last>0){
                List<T> itemList=new ArrayList<T>();
                for(int i=0;i<last;i++){
                    itemList.add(orginList.get(pre*listCount+i));
                }
                ret.add(itemList);
            }
        }
        return ret;
	}
	
	
	public static void sendPayload(PushPayload payload){
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);
	    try {
	         PushResult pu = jpushClient.sendPush(payload);  
	     } catch (APIConnectionException e) {
	         e.printStackTrace();
	         System.out.println("jpushException!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!---------APIConnectionException"+new Date());
	     } catch (APIRequestException e) {
	         e.printStackTrace();
	         System.out.println("jpushException!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!---------APIRequestException"+new Date());
	     }
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> list=new ArrayList<String>();
		list.add("13065ffa4e22e473083");
		Map<String, String> ex=new HashMap<String,String>();
		ex.put("type", "2");
		ex.put("bizId", "0");
		PushPayload payload;
		payload=getAndroidPushPayload(list,"安卓1.3.2版本以下应用程序有重大更新，请在官网上下载或者联系客服","XXX考",ex,true);
//		payload=getIOSPushPayload(list,"2019XXX考大纲解析，关注微信或添加微信了解详情。","XXX考",ex,true);
//		payload=broadcast("安卓1.0版本以下应用程序有重大更新，","XXX考",null,true,2);
		JPushClient jpushClient = new JPushClient(masterSecret, appKey);
	       try {
	            PushResult pu = jpushClient.sendPush(payload);  
	        } catch (APIConnectionException e) {
	            e.printStackTrace();
	            System.out.println("jpushException!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!---------APIConnectionException"+new Date());
	        } catch (APIRequestException e) {
	            e.printStackTrace();
	            System.out.println("jpushException!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!---------APIRequestException"+new Date());
	        }
	}

}
