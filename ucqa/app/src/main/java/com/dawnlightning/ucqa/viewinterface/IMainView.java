package com.dawnlightning.ucqa.viewinterface;

import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.Bean.UserBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/1.
 */
public interface IMainView {
    public void doloadlist();//从数据库中加载数据
    public void dosavelist(List<ConsultMessageBean> list);//将列表数据存入数据库
    public void selectview(int id);//根据ID跳转不同的view
    public void doloaddetail(int classid);//根据咨询列表id获取咨询详细
    public void dosuccess(String msg);//数据加载成功
    public void dofailure(int code,String msg);//数据加载失败
    public void docheckupdate();//检查用户是否需要更新
    public void showdownloaddialog();//显示更新对话框;
    public void showconsultlist(List<ConsultMessageBean> list);//将列表数据展示
    public void showclassifylist(List<ConsultClassifyBean> list);//将分类数据展示
    public void doloaduserdata();
    public void saveuserdata(UserBean userBean);
}
