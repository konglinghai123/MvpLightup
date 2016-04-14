package com.dawnlightning.ucqa.modelinterface;

import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.model.ConsultListModel;

/**
 * Created by Administrator on 2016/4/1.
 */
public interface IConsultListModel {
    public void loadassignconsultlist(ConsultListModel.consultlistener listener,int page,int bwztclassid,String m_auth,int identity,int operate);//按分类不同来获取，用于主界面筛选
    //任何筛选可以在此拓展
}
