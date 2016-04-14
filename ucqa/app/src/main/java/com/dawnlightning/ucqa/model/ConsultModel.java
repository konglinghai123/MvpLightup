package com.dawnlightning.ucqa.model;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.dawnlightning.ucqa.Bean.BaseBean;
import com.dawnlightning.ucqa.Bean.ConsultBean;
import com.dawnlightning.ucqa.Bean.UploadPicsBean;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.modelinterface.IConsultModel;
import com.dawnlightning.ucqa.util.AsyncHttp;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ConsultModel implements IConsultModel{

    public interface SendConsultListener{
        public void SendSuccess(int code,String msg);
        public void SendFailure(int code,String msg);
    }

    @Override
    public void uploadpic(final List<UploadPicsBean> beans,final Handler mhandler) {
        for (final  UploadPicsBean bean:beans) {
            try {
                String url = String.format(HttpConstants.HTTP_COMMENT + "ac=uplaod&m_auth=%s", bean.getM_auth());
                RequestParams params = new RequestParams();
                params.put("op", " uploadphoto2");
                params.put("uid", bean.getUid());
                params.put("topicid", 0);
                params.put("albumid", 0);
                params.put("attach", bean.getPicture());
                params.put("uploadsubmit2", true);
                params.put("title", bean.getPicturetitle());
                AsyncHttp.post(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                        Message message = mhandler.obtainMessage();
                        if ("0".equals(b.getCode())) {
                            com.alibaba.fastjson.JSONObject j = (com.alibaba.fastjson.JSONObject) JSON.parse(b.getData());
                            com.alibaba.fastjson.JSONObject js = (com.alibaba.fastjson.JSONObject) JSON.parse(j.getString("pic"));
                            String picid = js.getString("picid");
                            message.what = Code.UPLOADSUCCESS;
                            message.arg1 = bean.getPictureid();
                            message.obj = picid;
                            message.sendToTarget();
                        } else {
                            message.what = Code.UPLOADFAILURE;
                            message.arg1 = bean.getPictureid();
                            message.obj = b.getMsg();
                            message.sendToTarget();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Message message = mhandler.obtainMessage();
                        message.what = Code.UPLOADFAILURE;
                        message.arg1 = bean.getPictureid();
                        message.obj = "上传失败";
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
                        message.arg1 = bean.getPictureid();
                        message.obj = count;
                        message.sendToTarget();

                    }

                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Message message = mhandler.obtainMessage();
                message.arg1 = bean.getPictureid();
                message.what = Code.UPLOADFAILURE;
                message.obj = "当前文件不存在";
                message.sendToTarget();
            }
        }

    }

    @Override
    public void sendconsult(ConsultBean bean,final SendConsultListener sendConsultListener) {
        String url= String.format(HttpConstants.HTTP_COMMENT+"ac=bwzt&m_auth=%s",bean.getM_auth());
        RequestParams params = new RequestParams();
        params.put("subject", bean.getSubject());
        params.put("bwztclassid", bean.getBwztclassid());
        params.put("bwztdivisionid", bean.getBwztdivisionid());
        params.put("sex", bean.getSex());
        params.put("age", bean.getAge());
        params.put("message", bean.getMessage());
        params.put("makefeed", 1);
        params.put("bwztsubmit", true);
        params.put("formhash",bean.getFormhash());
        if(bean.getPicids().size()>0){
            for(int i=0;i<bean.getPicids().size();i++){
                String picsid=String.format("picids[%s]",bean.getPicids().get(i));
                params.put(picsid, i);
            }
        }
      AsyncHttp.post(url,params,new JsonHttpResponseHandler(){

          @Override
          public void onFailure(int statusCode, Header[] headers,
                                Throwable throwable, JSONObject errorResponse) {
              super.onFailure(statusCode, headers, throwable, errorResponse);
              sendConsultListener.SendFailure(1, "发布失败");
          }

          @Override
          public void onSuccess(int statusCode, Header[] headers,
                                JSONObject response) {
              super.onSuccess(statusCode, headers, response);
              BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
              if ("0".equals(b.getCode())) {
                  sendConsultListener.SendSuccess(0, "发布成功");
              } else {
                  sendConsultListener.SendFailure(1, b.getMsg());
              }

          }

      });

    }
}
