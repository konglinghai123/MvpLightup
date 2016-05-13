package com.dawnlightning.ucqa.modelinterface;

import com.dawnlightning.ucqa.model.ConsultListModel;

/**
 * Created by Administrator on 2016/4/1.
 */
public interface IConsultListModel {
    public void loadassignconsultlist(ConsultListModel.consultlistener listener,int page,int bwztclassid,String m_auth,int identity,int operate);//按分类不同来获取，用于主界面筛选
    //任何筛选可以在此拓展
    public  void stopresquest();//取消当前请求
    public  void clearcache(int indentify,int uid,String m_auth);//清空缓存
}
