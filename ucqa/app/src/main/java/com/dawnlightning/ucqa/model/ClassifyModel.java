package com.dawnlightning.ucqa.model;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dawnlightning.ucqa.Bean.BaseBean;
import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.modelinterface.IClassifyModel;
import com.dawnlightning.ucqa.util.AsyncHttp;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/3/31.
 */
public class ClassifyModel implements IClassifyModel {
    private List<ConsultClassifyBean> beans=new ArrayList<ConsultClassifyBean>();
    private  int status=1;

    public  interface loadclassifylistener{
        void onSuccess(List<ConsultClassifyBean> bean);
        void onFailure(int code,String msg);
    }
    @Override
    public void loadclassifybean(final loadclassifylistener listener) {
        RequestParams params = new RequestParams();
        params.put("do", "bwzt");
        params.put("view","class");
        AsyncHttp.get(HttpConstants.HTTP_SELECTION, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                listener.onFailure(1,"获取分类失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);

                if ("0".equals(b.getCode())) {
                    try{
                        status=0;
                        Log.e("tag",b.getData());
                        com.alibaba.fastjson.JSONObject js=(com.alibaba.fastjson.JSONObject) JSON.parse(b.getData());
                        com.alibaba.fastjson.JSONObject bwztid=(com.alibaba.fastjson.JSONObject) JSON.parse(js.getString("bwztclassarr"));
                        com.alibaba.fastjson.JSONObject bwztname=(com.alibaba.fastjson.JSONObject) JSON.parse(js.getString("bwztdivisionarr"));
                        Iterator it=bwztname.keySet().iterator();
                        Iterator iterator=bwztid.keySet().iterator();
                        ConsultClassifyBean bean=new ConsultClassifyBean();
                        HashMap<String,String> mapid=new HashMap<String, String>();
                        HashMap<String,String> mapname=new HashMap<String, String>();
                        while (iterator.hasNext()){
                            String s=iterator.next().toString();
                            mapid.put(bwztid.getString(s), s);
                        }
                        while(it.hasNext()){
                            String s=it.next().toString();
                            mapname.put(bwztname.getString(s), s);
                        }
                        bean.setMapid(mapid);
                        bean.setMapname(mapname);
                        beans.add(bean);
                        listener.onSuccess(beans);
                    }catch (Exception e){
                        listener.onFailure(1,b.getData());
                    }

                } else {
                    status=1;
                    listener.onFailure(1,b.getMsg());
                }

            }

        });
    }
}
