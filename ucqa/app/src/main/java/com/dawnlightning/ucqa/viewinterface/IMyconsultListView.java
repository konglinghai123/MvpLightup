package com.dawnlightning.ucqa.viewinterface;

import com.dawnlightning.ucqa.Bean.ConsultMessageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/10.
 * 获取我的咨询列表
 */
public interface IMyconsultListView {
    public  void doloadmylist(int page,int bwztclassid,String m_auth,int identity,int  operate);////从网络加载“bwztidclassid”的咨询列表
    public  void getmylistSuccess(int code,List<ConsultMessageBean> list, int operate);
    public  void getmylistFailure(int code,String msg,int  operate);
    public  void mylistrefresh(int  operate);
    public void  showerror(int code,String msg);

}
