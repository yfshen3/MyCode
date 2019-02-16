package org.lf.admin.api.baseapi.config;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.device.AliasDeviceListResult;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import java.util.List;

public class JpushMsg {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		String masterSecret = SysConfig.getProperty("masterSecret");
		String appKey = SysConfig.getProperty("appKey");
		String alias = "imei860758046760254";
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
//		PushPayload payload = buildPushObject_all_alias_alert(alias,"别名推送测试");
		PushPayload payload = buildPushObject_android_ios_alias_alert(String.valueOf(alias),"updateFriend");
		aliasIsExist(alias);
		
		PushResult result = jpushClient.sendPush(payload);
		System.out.println(result);
		System.out.println(result.statusCode);
		if(result != null && result.isResultOK()){
			System.out.println("针对别名" + alias + "的信息推送成功！");
	    }else{
	        System.out.println("针对别名" + alias + "的信息推送失败！");
	    }
		
	}
	
	//判断有无别名
	public static Boolean aliasIsExist(String alias) {
		Boolean flag = false;
		
		String masterSecret = SysConfig.getProperty("masterSecret");
		String appKey = SysConfig.getProperty("appKey");
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
		try {
			AliasDeviceListResult aresult = jpushClient.getAliasDeviceList(alias, "android");
			AliasDeviceListResult iresult = jpushClient.getAliasDeviceList(alias, "ios");
			List<String> aList = aresult.registration_ids;
			List<String> iList = iresult.registration_ids;
			if(aList.size()>0 || iList.size()>0) {
				flag = true;
			}
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}

	public static boolean pushMsgToOne(String alias, String msg) throws APIConnectionException, APIRequestException {
		String masterSecret = SysConfig.getProperty("masterSecret");
		String appKey = SysConfig.getProperty("appKey");
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
		PushPayload payload = buildPushObject_android_ios_alias_alert(String.valueOf(alias),msg);
		PushResult result = jpushClient.sendPush(payload);
		if (result != null && result.isResultOK()) {
			return true;
		} else {
			return false;
		}
	}

	public static void pushMsgToAll(String msg) throws APIConnectionException, APIRequestException{
		String masterSecret = SysConfig.getProperty("masterSecret");
		String appKey = SysConfig.getProperty("appKey");
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
		PushPayload payload = buildPushObject_all_all_alert(msg);
		PushResult result = jpushClient.sendPush(payload);
		System.out.println(result);
	}

	/**
	* 快捷地构建推送对象：所有平台，所有设备，内容为 ALERT 的通知
	*/
	public static PushPayload buildPushObject_all_all_alert(String msg) {
		return PushPayload.alertAll(msg);
	}

	/**
	*构建推送对象：所有平台，推送目标是别名为 "alias1"，通知内容为 ALERT。 
	*/
	public static PushPayload buildPushObject_all_alias_alert(String alias,String msg) {
		return PushPayload.newBuilder()
		.setPlatform(Platform.all())
		.setAudience(Audience.alias(alias))
		.setNotification(Notification.alert(msg))
		.build();
	}
	
	
	/**
     * 生成极光推送对象PushPayload（采用java SDK）
     * @param alias
     * @param alert
     * @return PushPayload
     */
    public static PushPayload buildPushObject_android_ios_alias_alert(String alias,String alert){
		boolean apnsProduction = ("true".equals(SysConfig.getProperty("apnsProduction")))?true:false;
		return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtra("type", "infomation")
                                .setAlert(alert)
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder()
                        		.setSound("happy")
                                .addExtra("type", "infomation")
                                .setAlert(alert)
                                .build())
                        .build())
                .setOptions(Options.newBuilder()
                        .setApnsProduction(apnsProduction)//true-推送生产环境 false-推送开发环境（测试使用参数）
                        .setTimeToLive(90)//消息在JPush服务器的失效时间（测试使用参数）
                        .build())
                .build();
    }

	/**
	* 构建推送对象：平台是 iOS，推送目标是 "tag1", "tag_all" 的交集
	* 推送内容同时包括通知与消息 - 通知信息是 ALERT，角标数字为 5，通知声音为 "happy"，并且附加字段 from = "JPush"；
	* 消息内容是 MSG_CONTENT。通知是 APNs 推送通道的
	* 消息是 JPush 应用内消息通道的。APNs 的推送环境是“生产”（如果不显式设置的话，Library 会默认指定为开发）
	*/
	public static PushPayload buildPushObject_android_tag_alertWithTitle() {
		return PushPayload.newBuilder()
		.setPlatform(Platform.android())
		.setAudience(Audience.tag("tag1"))
		.setNotification(Notification.android("ALERT", "TITLE", null))
		.build();
	} 


	/**
	* 构建推送对象：平台是 Andorid 与 iOS，推送目标是 （"tag1" 与 "tag2" 的并集）且（"alias1" 与 "alias2" 的并集）
	* 推送内容是 - 内容为 MSG_CONTENT 的消息，并且附加字段 from = JPush。
	*/
	public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
		return PushPayload.newBuilder()
		.setPlatform(Platform.android_ios())
		.setAudience(Audience.newBuilder()
		.addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
		.addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
		.build())
		.setMessage(Message.newBuilder()
		.setMsgContent("MSG_CONTENT")
		.addExtra("from", "JPush")
		.build())
		.build();
	}

}
