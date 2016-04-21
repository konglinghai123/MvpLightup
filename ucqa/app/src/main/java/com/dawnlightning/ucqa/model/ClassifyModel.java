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

    public  interface loadclassifylistener{
        void onSuccess(List<ConsultClassifyBean> bean);
        void onFailure(int code,String msg);
    }
    @Override
    public void loadclassifybean(final loadclassifylistener listener) {
        RequestParams params = new RequestParams();
        params.put("do", "bwzt");
        params.put("view", "class");
        final String url = HttpConstants.HTTP_SELECTION+params.toString();
        if (AsyncHttp.getcache(url)!=null&&AsyncHttp.getcache(url).length()>0){
            List<ConsultClassifyBean> beans=new ArrayList<ConsultClassifyBean>();
            String strcache=AsyncHttp.getcache(url);
            BaseBean b = JSON.parseObject(strcache, BaseBean.class);
            com.alibaba.fastjson.JSONObject js=(com.alibaba.fastjson.JSONObject) JSON.parse(b.getData());
            com.alibaba.fastjson.JSONObject bwztid=(com.alibaba.fastjson.JSONObject) JSON.parse(js.getString("bwztclassarr"));
            com.alibaba.fastjson.JSONObject bwztname=(com.alibaba.fastjson.JSONObject) JSON.parse(js.getString("bwztdivisionarr"));
            Iterator it=bwztname.keySet().iterator();
            Iterator iterator=bwztid.keySet().iterator();
            while (iterator.hasNext()){
                ConsultClassifyBean bean=new ConsultClassifyBean();
                String s=iterator.next().toString();
                bean.setBwztclassarrid(s);
                bean.setBwztclassarrname(bwztid.getString(s));
                bean.setBwztdivisionarrid("1");
                bean.setBwztdivisionarrname("眼科");
                while(it.hasNext()){
                    String version=it.next().toString();
                    bean.setBwztdivisionarrid(version);
                    bean.setBwztdivisionarrname(bwztname.getString(version));
                }
                beans.add(bean);
            }
            listener.onSuccess(beans);
        }else{
            AsyncHttp.removecache(url);
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
                            List<ConsultClassifyBean> beans=new ArrayList<ConsultClassifyBean>();
                            com.alibaba.fastjson.JSONObject js=(com.alibaba.fastjson.JSONObject) JSON.parse(b.getData());
                            com.alibaba.fastjson.JSONObject bwztid=(com.alibaba.fastjson.JSONObject) JSON.parse(js.getString("bwztclassarr"));
                            com.alibaba.fastjson.JSONObject bwztname=(com.alibaba.fastjson.JSONObject) JSON.parse(js.getString("bwztdivisionarr"));
                            Iterator it=bwztname.keySet().iterator();
                            Iterator iterator=bwztid.keySet().iterator();
                            while (iterator.hasNext()){
                                ConsultClassifyBean bean=new ConsultClassifyBean();
                                String s=iterator.next().toString();
                                bean.setBwztclassarrid(s);
                                bean.setBwztclassarrname(bwztid.getString(s));
                                bean.setBwztdivisionarrid("1");
                                bean.setBwztdivisionarrname("眼科");
                                while(it.hasNext()){
                                    String version=it.next().toString();
                                    bean.setBwztdivisionarrid(version);
                                    bean.setBwztdivisionarrname(bwztname.getString(version));

                                }
                                beans.add(bean);
                            }
                            AsyncHttp.savecache(url,response.toString(),4*60*60);
                            listener.onSuccess(beans);
                        }catch (Exception e){
                            listener.onFailure(1,b.getData());
                        }

                    } else {

                        listener.onFailure(1,b.getMsg());
                    }

                }

            });
        }


    }

}
