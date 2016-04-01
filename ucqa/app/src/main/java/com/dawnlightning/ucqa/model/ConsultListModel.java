package com.dawnlightning.ucqa.model;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dawnlightning.ucqa.Bean.BaseBean;
import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.Bean.UserData;
import com.dawnlightning.ucqa.modelinterface.IConsultListModel;
import com.dawnlightning.ucqa.util.AsyncHttp;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/4/1.
 */
public class ConsultListModel implements IConsultListModel {
    public interface consultlistener{
        public void consultonSuccess(List<ConsultMessageBean> beans);
        public void consultonFailure(int code,String msg);
    }

    @Override
    public void loadfirstconsultlist(final  consultlistener listener) {
        RequestParams params = new RequestParams();
        params.put("do", "bwzt");
        params.put("view","all");
        params.put("orderby","dateline");
        params.put("page",1);
        AsyncHttp.get(HttpConstants.HTTP_SELECTION, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                listener.consultonFailure(1, "获取咨询列表失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                if ("0".equals(b.getCode())) {
                    com.alibaba.fastjson.JSONObject js=(com.alibaba.fastjson.JSONObject) JSON.parse(b.getData());
                    String strlist=js.getString("list");
                    List<ConsultMessageBean> newlist=JSON.parseArray(strlist.toString(),ConsultMessageBean.class);
                    if (newlist.size()>0){
                        listener.consultonSuccess(newlist);
                    }else{
                        listener.consultonFailure(1,"没有更多的咨询");
                    }

                } else {
                    listener.consultonFailure(1, b.getMsg());
                }

            }

        });
    }

    @Override
    public void loadassignconsultlist(consultlistener listener, int page, String bwztclassid) {

    }
}
