package com.dawnlightning.ucqa.update;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dawnlightning.ucqa.util.AsyncHttp;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class UpdateManager {
	 
	
	 private Context mContext;
	 private Handler handler;
     private String updatenote="";
     private String updatename="";
     private String updateurl="";
	 public UpdateManager(Context context,Handler handler)
	    {
	        this.mContext = context;
	        this.handler=handler;
	    }

	 /**
     * 检测软件更新
     */
    public void checkUpdate()
    {
        if (AsyncHttp.getcache(HttpConstants.Update)!=null&&AsyncHttp.getcache(HttpConstants.Update).length()>0){
            Message msg = new Message();
            com.alibaba.fastjson.JSONObject js = (com.alibaba.fastjson.JSONObject) JSON.parse(AsyncHttp.getcache(HttpConstants.Update).toString());
            com.alibaba.fastjson.JSONObject update = (com.alibaba.fastjson.JSONObject) JSON.parse(js.getString("update"));
            com.alibaba.fastjson.JSONObject j = (com.alibaba.fastjson.JSONObject) JSON.parse(update.getString("Android"));
            // 获取当前软件版本
            int versionCode = getVersionCode(mContext);
            updatenote = j.getString("note");
            updateurl= j.getString("url");
            updatename = j.getString("name");
//                int serviceCode = Integer.parseInt(j.getString("version"));
            int serviceCode = 2;
            // 版本判断
            if (serviceCode > versionCode)
            {
                msg.what=UpdateStatus.UPDATA_CLIENT;
                handler.sendMessage(msg);
            }else{
                msg.what=UpdateStatus.UPDATA_NO;
                handler.sendMessage(msg);
            }
        }else{
            AsyncHttp.removecache(HttpConstants.Update);
            AsyncHttp.get(HttpConstants.Update, null, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    AsyncHttp.savecache(HttpConstants.Update, response.toString(), 60*60*24);
                    Message msg = new Message();
                    com.alibaba.fastjson.JSONObject js = (com.alibaba.fastjson.JSONObject) JSON.parse(response.toString());
                    com.alibaba.fastjson.JSONObject update = (com.alibaba.fastjson.JSONObject) JSON.parse(js.getString("update"));
                    com.alibaba.fastjson.JSONObject j = (com.alibaba.fastjson.JSONObject) JSON.parse(update.getString("Android"));
                    // 获取当前软件版本
                    int versionCode = getVersionCode(mContext);
                    updatenote = j.getString("note");
                    updateurl= j.getString("url");
                    updatename = j.getString("name");
//                int serviceCode = Integer.parseInt(j.getString("version"));
                    int serviceCode = 2;
                    // 版本判断
                    if (serviceCode > versionCode)
                    {
                        msg.what=UpdateStatus.UPDATA_CLIENT;
                        handler.sendMessage(msg);
                    }else{
                        msg.what=UpdateStatus.UPDATA_NO;
                        handler.sendMessage(msg);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Message msg = new Message();
                    msg.what =UpdateStatus.UPDATA_ERROR;
                    handler.sendMessage(msg);
                }
            });
        }

    	
    	
    	
    	
    }
	 /**
     * 检查软件是否有更新版本
     * 
     * @return
     */
    

  
    /**
     * 获取软件版本号
     * 
     * @param context
     * @return
     */
    public int getVersionCode(Context context)
    {
        int versionCode = 0;
        try
        {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo("com.dawnlightning.ucqa", 0).versionCode;
        } catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return versionCode;
    }
    /**
     * 显示软件更新对话框
     */
    public void showNoticeDialog()
    {
        // 构造对话框
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("更新提醒");
        builder.setMessage(updatenote);
        // 更新
        builder.setPositiveButton("更新", new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                Intent intent = new Intent(mContext,UpdateService.class);
    			intent.putExtra("Key_App_Name",updatename);
    			intent.putExtra("Key_Down_Url",updateurl);
    			mContext.startService(intent);
            }
        });
        // 稍后更新
        builder.setNegativeButton("稍后更新", new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }
    public String getversionname(){
    try {
		return mContext.getPackageManager().getPackageInfo("com.dawnlightning.ucqa", 0).versionName;
	} catch (NameNotFoundException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
		
	}
    	return "";
    }

}
