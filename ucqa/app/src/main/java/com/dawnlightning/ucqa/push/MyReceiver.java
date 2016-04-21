package com.dawnlightning.ucqa.push;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dawnlightning.ucqa.activity.MainActivity;
import com.dawnlightning.ucqa.activity.WelcomeActivity;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.db.SharedPreferenceDb;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	//processCustomMessage(context, bundle);
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            //processCustomMessage(context, bundle);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			if(!checkBrowser("com.dawnlightning.ucqa",context)){
				Intent i = new Intent(context, WelcomeActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
			}else {
				if (!MainActivity.isForeground) {
					//如果在后台
					String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
					com.alibaba.fastjson.JSONObject extraJson = (com.alibaba.fastjson.JSONObject) JSON.parse(extras);
					if (null != extraJson) {
						String id = extraJson.getString("id");
						String uid = extraJson.getString("uid");
						KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(context.KEYGUARD_SERVICE);
						KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
						keyguardLock.disableKeyguard();
						Intent i = new Intent(context, MainActivity.class);
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						Bundle userbundle = new Bundle();
						userbundle.putSerializable("userdata", MainActivity.userBean);
						userbundle.putString("id", id);
						userbundle.putString("uid", uid);
						i.putExtras(userbundle);
						context.startActivity(i);
						processCustomMessage(context, bundle);
					}

				} else {
					//如果不在后台
					processCustomMessage(context, bundle);
				}
			}
            //processCustomMessage(context, bundle);
        	//打开自定义的Activity
        
        	
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}
	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} 
			else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	private boolean checkBrowser(String packageName,Context context) {
		Intent intent = new Intent();
		intent.setClassName(packageName,"MainActivity");
		if (context.getPackageManager().resolveActivity(intent, 0) == null) {
			return false;
		}
		return  true;
	}
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
	
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent( Code.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra( Code.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (null != extraJson && extraJson.length() > 0) {
					  String id=extraJson.getString("id");
					  String uid=extraJson.getString("uid");
						msgIntent.putExtra(Code.KEY_EXTRAS, extras);
						msgIntent.putExtra("id",id);
						msgIntent.putExtra("uid",uid);
					}
				} catch (JSONException e) {

				}

			}
			context.sendBroadcast(msgIntent);
		
	}
}
