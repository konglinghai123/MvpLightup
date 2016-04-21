package com.dawnlightning.ucqa.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dawnlightning.ucqa.Bean.BaseBean;
import com.dawnlightning.ucqa.Bean.PersonalDataBean;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.modelinterface.IPersonalDataModel;
import com.dawnlightning.ucqa.util.AsyncHttp;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/4/20.
 */
public class PersonalDataModel implements IPersonalDataModel {

    public interface modifylistener{
        public void modifySuccess(String msg);
        public void modifyFailure(int code,String msg);
    }

    @Override
    public void uploadavater(File avater, String m_auth,final Handler mhandler) {

        try {
            String url = String.format(HttpConstants.HTTP_COMMENT + "ac=avatar&m_auth=%s&avatarsubmit=ture",m_auth);
            RequestParams params = new RequestParams();
            params.put("Filedata",avater, "image/jpeg");
            params.setForceMultipartEntityContentType(true);
            AsyncHttp.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                    Message message = mhandler.obtainMessage();
                    if ("0".equals(b.getCode())) {
                        com.alibaba.fastjson.JSONObject j = (com.alibaba.fastjson.JSONObject) JSON.parse(b.getData());
                        String avater=j.getString("avatar_url");
                        message.what = Code.UPLOADSUCCESS;
                        message.obj =avater;
                        message.sendToTarget();

                    } else {
                        message.what = Code.UPLOADFAILURE;
                        message.obj =b.getMsg();
                        message.sendToTarget();

                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Message message = mhandler.obtainMessage();
                    message.what = Code.UPLOADFAILURE;
                    message.obj ="头像上传失败";
                    message.sendToTarget();

                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    // TODO 自动生成的方法存根
                    super.onProgress(bytesWritten, totalSize);
                    int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                    // 上传进度显示
                    Message message = mhandler.obtainMessage();
                    message.what = Code.UPLOADCHANGE;
                    message.obj = count;
                    message.sendToTarget();

                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Message message = mhandler.obtainMessage();
            message.what = Code.UPLOADFAILURE;
            message.obj ="文件不存在";
            message.sendToTarget();
        }

    }

    @Override
    public void modifypersonaldata(PersonalDataBean bean,String m_auth ,final  modifylistener modifylistener) {
        String url = String.format(HttpConstants.HTTP_COMMENT + "ac=profile&m_auth=%s&op=base",m_auth);
        RequestParams params = new RequestParams();
        params.put("profilesubmit",true);
        params.put("formhash",bean.getFormhash());
        params.put("name",bean.getName());
        params.put("sex",bean.getSex());
        params.put("birthyear",bean.getBirthyear());
        params.put("birthmonth",bean.getBirthmonth());
        params.put("birthday",bean.getBirthday());
        AsyncHttp.post(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                modifylistener.modifyFailure(-1,"修改失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                if ("0".equals(b.getCode())) {
                    modifylistener.modifySuccess("修改成功");
                } else {
                    modifylistener.modifyFailure(-1,"修改失败");
                }

            }
        });


    }
}
