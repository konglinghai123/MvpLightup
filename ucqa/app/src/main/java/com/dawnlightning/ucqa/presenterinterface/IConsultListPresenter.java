package com.dawnlightning.ucqa.presenterinterface;

/**
 * Created by Administrator on 2016/4/1.
 */
public interface IConsultListPresenter {
    public  void loadfistlist();//获取咨询列表第一页
    public  void loadassignlist(int page,String bwztclassid);//获取指定的列表
}
