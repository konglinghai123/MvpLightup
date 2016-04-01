package com.dawnlightning.ucqa.modelinterface;

import  com.dawnlightning.ucqa.model.ClassifyModel.loadclassifylistener;
/**
 * Created by Administrator on 2016/3/31.
 */
public interface IClassifyModel {

    public  void loadclassifybean(loadclassifylistener listener);//通过网络请求获取咨询分类

}
