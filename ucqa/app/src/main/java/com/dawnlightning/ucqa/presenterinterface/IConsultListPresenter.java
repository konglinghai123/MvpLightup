package com.dawnlightning.ucqa.presenterinterface;

/**
 * Created by Administrator on 2016/4/1.
 */
public interface IConsultListPresenter {
    public  void loadassignlist(int page,int bwztclassid,String m_auth,int identity,int  operate);//获取指定的列表
    public void loadclassify();
    public void stopresquest();
    public void clearcache(int indentify,int uid,String m_auth);
}
