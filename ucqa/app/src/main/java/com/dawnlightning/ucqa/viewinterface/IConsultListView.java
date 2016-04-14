package com.dawnlightning.ucqa.viewinterface;

import com.dawnlightning.ucqa.Bean.ConsultMessageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/1.
 */
public interface IConsultListView {

    public  void doloadassignlist(int page,int bwztclassid,int identity,int  operate);////从网络加载“bwztidclassid”的咨询列表
    public  void getSuccess(int code,List<ConsultMessageBean> list,int identity,int operate);
    public  void getFailure(int code,String msg,int identity,int operate);
    public  void refresh(int  operate);

}
