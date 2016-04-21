package com.dawnlightning.ucqa.viewinterface;

import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.Bean.ConsultMessageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/1.
 */
public interface IConsultListView {
    public void doloadlist();//从数据库中加载数据
    public void showclassifylist(List<ConsultClassifyBean> list);//将分类数据展示
    public  void doloadassignlist(int page,int bwztclassid,int identity,int  operate);////从网络加载“bwztidclassid”的咨询列表
    public  void getSuccess(int code,List<ConsultMessageBean> list,int identity,int operate);
    public  void getFailure(int code,String msg,int identity,int operate);
    public  void refresh(int  operate);
    public void showerror(int code,String msg);
}
