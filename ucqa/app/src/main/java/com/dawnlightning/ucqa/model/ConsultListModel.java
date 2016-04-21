package com.dawnlightning.ucqa.model;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dawnlightning.ucqa.Bean.BaseBean;
import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.Bean.UserData;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.modelinterface.IConsultListModel;
import com.dawnlightning.ucqa.util.AsyncHttp;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.net.URI;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/4/1.
 */
public class ConsultListModel implements IConsultListModel {



    public interface consultlistener {
        public void consultonSuccess(int code, List<ConsultMessageBean> beans,int identity ,int operate);

        public void consultonFailure(int code, String msg,int identity,int operate);
    }

    @Override
    public void stopresquest() {
        AsyncHttp.stopresquest();
    }
    public void initfirstlist(){
        RequestParams params=new RequestParams();
        params.put("do","bwzt");
        params.put("view","all");
        params.put("orderby", "dateline");
        params.put("page",1);
        final String url = HttpConstants.HTTP_SELECTION+params.toString();
        if (AsyncHttp.getcache(url)!=null&&AsyncHttp.getcache(url).length()>0){

        }else{
            AsyncHttp.removecache(url);
            AsyncHttp.get(HttpConstants.HTTP_SELECTION, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers,
                                      JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                    if ("0".equals(b.getCode())) {
                        com.alibaba.fastjson.JSONObject js = (com.alibaba.fastjson.JSONObject) JSON.parse(b.getData());
                        String strlist = js.getString("list");
                        List<ConsultMessageBean> newlist = JSON.parseArray(strlist.toString(), ConsultMessageBean.class);
                        if (newlist.size() > 0) {

                            AsyncHttp.savecache(url,response.toString(),60);

                        } else {

                        }

                    } else {

                    }
                }
            });
        }
    }

    @Override
    public void clearcache(int indentify,int uid,String m_auth) {
        if (indentify==Code.ALL){
            RequestParams params = new RequestParams();
            params.put("do", "bwzt");
            params.put("view", "all");
            params.put("orderby", "dateline");
            params.put("page", 1);
            final String url = HttpConstants.HTTP_SELECTION+params.toString();
            AsyncHttp.removecache(url);
        }else if(indentify==Code.ME){
            RequestParams params = new RequestParams();
            params.put("do", "bwzt");
            params.put("uid",uid);
            params.put("view", "me");
            params.put("m_auth",m_auth);
            params.put("orderby", "dateline");
            params.put("page",1);
            final String url = HttpConstants.HTTP_SELECTION+params.toString();
            AsyncHttp.removecache(url);
        }
    }

    @Override
    public void loadassignconsultlist(final consultlistener listener, final int page, int bwztclassid,String m_auth,final int identity, final int operate) {
        RequestParams params = new RequestParams();
        params.put("do", "bwzt");
        if (identity==Code.ALL){
            if (bwztclassid!=-1){
                params.put(" bwztclassid", bwztclassid);
            }
            params.put("view", "all");
        }else  if (identity==Code.ME){
            params.put("uid",bwztclassid);
            params.put("view", "me");
            params.put("m_auth",m_auth);

        }
        params.put("orderby", "dateline");
        params.put("page", page);
        final String url = HttpConstants.HTTP_SELECTION+params.toString();
        if (AsyncHttp.getcache(url)!=null&&AsyncHttp.getcache(url).length()>0){
            String strcache=AsyncHttp.getcache(url);
            BaseBean b = JSON.parseObject(strcache, BaseBean.class);
            com.alibaba.fastjson.JSONObject js = (com.alibaba.fastjson.JSONObject) JSON.parse(b.getData());
            String strlist = js.getString("list");
            List<ConsultMessageBean> newlist = JSON.parseArray(strlist.toString(), ConsultMessageBean.class);
            if (newlist.size() < Code.PAGESIZE) {
                listener.consultonSuccess(Code.LOAD_NOFULL_SUCCESS, newlist, identity, operate);
            } else {
                listener.consultonSuccess(Code.LOAD_FULL_SUCCESS, newlist, identity, operate);
            }
        }else{
            AsyncHttp.removecache(url);
            AsyncHttp.get(HttpConstants.HTTP_SELECTION, params, new JsonHttpResponseHandler() {

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    listener.consultonFailure(Code.SERVER_LOAD_FAILURE, "获取咨询列表失败", identity, operate);
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers,
                                      JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                    if ("0".equals(b.getCode())) {
                        com.alibaba.fastjson.JSONObject js = (com.alibaba.fastjson.JSONObject) JSON.parse(b.getData());
                        String strlist = js.getString("list");
                        List<ConsultMessageBean> newlist = JSON.parseArray(strlist.toString(), ConsultMessageBean.class);
                        if (newlist.size() > 0) {

                            AsyncHttp.savecache(url,response.toString(),60);
                            if (newlist.size() < Code.PAGESIZE) {
                                listener.consultonSuccess(Code.LOAD_NOFULL_SUCCESS, newlist, identity, operate);
                            } else {
                                listener.consultonSuccess(Code.LOAD_FULL_SUCCESS, newlist, identity, operate);
                            }
                        } else {
                            listener.consultonFailure(Code.LOAD_NODATA, "没有更多的咨询", identity, operate);
                        }

                    } else {
                        listener.consultonFailure(Code.LOAD_FAILURE, b.getMsg(), identity, operate);
                    }
                }
            });
        }

    }
}
