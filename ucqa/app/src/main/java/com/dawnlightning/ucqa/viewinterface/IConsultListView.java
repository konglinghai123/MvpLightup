package com.dawnlightning.ucqa.viewinterface;

import com.dawnlightning.ucqa.Bean.ConsultMessageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/1.
 */
public interface IConsultListView {
    public void doloadfirstlist(); //获取咨询第一页
    public  void doloadassignlist(int page,String bwztclassid);//加载指定列表
    public  void getSuccess(List<ConsultMessageBean> list);
    public  void getFailure(int code,String msg);
}
