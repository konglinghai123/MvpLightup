package com.dawnlightning.ucqa.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dawnlightning.ucqa.Bean.BaseBean;
import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.model.ConsultListModel;
import com.dawnlightning.ucqa.presenterinterface.IConsultListPresenter;
import com.dawnlightning.ucqa.util.AsyncHttp;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.dawnlightning.ucqa.viewinterface.IConsultListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/4/1.
 */
public class ConsultListPresenter implements IConsultListPresenter,ConsultListModel.consultlistener {
    private ConsultListModel model;
    private IConsultListView view;
    private MyApp myApp;

    @Override
    public void loadassignlist(int page, int bwztclassid,String m_auth,int identity,int  operate) {
        model.loadassignconsultlist(this, page, bwztclassid, m_auth, identity, operate);
    }

    @Override
    public void consultonSuccess(int code,List<ConsultMessageBean> beans,int identity,int  operate) {

            view.getSuccess(code, beans, identity, operate);

    }

    @Override
    public void clearcache(int indentify, int uid, String m_auth) {
        model.clearcache(indentify,uid,m_auth);
    }

    @Override
    public void consultonFailure(int code, String msg,int identity, int operate) {
            view.getFailure(code, msg, identity, operate);
    }
    public ConsultListPresenter(IConsultListView view, MyApp myApp){
        this.view=view;
        this.myApp=myApp;
        this.model=new ConsultListModel();
    }


    @Override
    public void stopresquest() {
        model.stopresquest();
    }

    @Override
    public void loadclassify() {
        List<ConsultClassifyBean> list=new ArrayList<ConsultClassifyBean>();
        RequestParams params = new RequestParams();
        params.put("do", "bwzt");
        params.put("view", "class");
        final String url = HttpConstants.HTTP_SELECTION+params.toString();
        if (AsyncHttp.getcache(url)!=null&&AsyncHttp.getcache(url).length()>0){
            String strcache=AsyncHttp.getcache(url);
            Log.e("load", strcache);
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
                list.add(bean);
            }}

        if (list.size()>0){
            ConsultClassifyBean bean6=new ConsultClassifyBean();
            bean6.setBwztclassarrid("6");
            bean6.setBwztclassarrname("全部");
            bean6.setBwztdivisionarrid("1");
            bean6.setBwztdivisionarrname("眼科");
            list.add(bean6);
            view.showclassifylist(list);
        }else{
            List<ConsultClassifyBean> staticlist=new ArrayList<ConsultClassifyBean>();
            ConsultClassifyBean bean1=new ConsultClassifyBean();
            bean1.setBwztclassarrid("1");
            bean1.setBwztclassarrname("青少年近视");
            bean1.setBwztdivisionarrid("1");
            bean1.setBwztdivisionarrname("眼科");

            ConsultClassifyBean bean2=new ConsultClassifyBean();
            bean2.setBwztclassarrid("2");
            bean2.setBwztclassarrname("防盲治盲");
            bean2.setBwztdivisionarrid("1");
            bean2.setBwztdivisionarrname("眼科");

            ConsultClassifyBean bean3=new ConsultClassifyBean();
            bean3.setBwztclassarrid("3");
            bean3.setBwztclassarrname("飞秒激光治疗近视");
            bean3.setBwztdivisionarrid("1");
            bean3.setBwztdivisionarrname("眼科"); ConsultClassifyBean bean=new ConsultClassifyBean();

            ConsultClassifyBean bean4=new ConsultClassifyBean();
            bean4.setBwztclassarrid("4");
            bean4.setBwztclassarrname("青光眼");
            bean4.setBwztdivisionarrid("1");
            bean4.setBwztdivisionarrname("眼科");

            ConsultClassifyBean bean5=new ConsultClassifyBean();
            bean5.setBwztclassarrid("5");
            bean5.setBwztclassarrname("白内障");
            bean5.setBwztdivisionarrid("1");
            bean5.setBwztdivisionarrname("眼科");

            ConsultClassifyBean bean6=new ConsultClassifyBean();
            bean6.setBwztclassarrid("6");
            bean6.setBwztclassarrname("全部");
            bean6.setBwztdivisionarrid("1");
            bean6.setBwztdivisionarrname("眼科");
            staticlist.add(bean1);
            staticlist.add(bean2);
            staticlist.add(bean3);
            staticlist.add(bean4);
            staticlist.add(bean5);
            staticlist.add(bean6);
            view.showclassifylist(staticlist);
        }


    }

}
