package com.dawnlightning.ucqa.model;

import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.dawnlightning.ucqa.Bean.BaseBean;
import com.dawnlightning.ucqa.Bean.MessageBean;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.modelinterface.IMessageModel;
import com.dawnlightning.ucqa.util.AsyncHttp;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/4/10.
 */
public class MessageModel implements IMessageModel {
    public interface messagelistener{
        public void getmessageSuccess(int code,int operate,List<MessageBean> bean);
        public void getmessageFailure(int code,int operate,String msg);
    }

    @Override
    public void loadmessagelist(final int page,String m_auth,final int operate,final messagelistener messagelistener) {

        String url=String.format(HttpConstants.HTTP_SELECTION+"do=notice&m_auth=%s&page=%s",m_auth,page);
        Log.e("url",url);
        AsyncHttp.getnotice(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                messagelistener.getmessageFailure(Code.SERVER_LOAD_FAILURE, operate, "获取消息列表失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                if ("0".equals(b.getCode())) {
                    Log.e("response", response.toString());
                    com.alibaba.fastjson.JSONObject j = (com.alibaba.fastjson.JSONObject) JSON.parse(b.getData());
                    com.alibaba.fastjson.JSONObject js = (com.alibaba.fastjson.JSONObject) JSON.parse(j.getString("notices"));
                    int totalpages = Integer.parseInt(js.getString("pages").toString());
                    List<MessageBean> list = JSON.parseArray(js.getString("list"), MessageBean.class);
                    if (totalpages > page) {
                        messagelistener.getmessageSuccess(Code.HAVENEXTPAGE, operate, list);
                    } else {
                        if (list.size() > 0) {
                            messagelistener.getmessageSuccess(Code.NONEXTPAGE, operate, list);
                        } else {
                            messagelistener.getmessageFailure(Code.LOAD_NODATA, operate, "没有消息");
                        }
                    }

                } else {
                    messagelistener.getmessageFailure(Code.LOAD_FAILURE, operate, b.getMsg());
                }
            }
        });
    }
}
