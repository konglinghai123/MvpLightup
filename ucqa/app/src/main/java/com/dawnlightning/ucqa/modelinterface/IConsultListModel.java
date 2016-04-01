package com.dawnlightning.ucqa.modelinterface;

import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.model.ConsultListModel;

/**
 * Created by Administrator on 2016/4/1.
 */
public interface IConsultListModel {
    public  void loadfirstconsultlist(ConsultListModel.consultlistener listener);//通过网络请求获取咨询列表最新的一页，用于加载

    public void loadassignconsultlist(ConsultListModel.consultlistener listener,int page,String bwztclassid);//按分类不同来获取，用于主界面筛选
    //任何筛选可以在此拓展
}
