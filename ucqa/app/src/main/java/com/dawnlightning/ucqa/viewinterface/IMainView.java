package com.dawnlightning.ucqa.viewinterface;

import android.content.Intent;

import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.Bean.UserBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/1.
 */
public interface IMainView {
    public void doloadlist();//从数据库中加载数据
    public void selectview(int id);//根据ID跳转不同的view
    public void docheckupdate();//检查用户是否需要更新
    public void showconsultlist(List<ConsultMessageBean> list);//将列表数据展示
    public void showclassifylist(List<ConsultClassifyBean> list);//将分类数据展示
    public void doloaduserdata(Intent intent);//加载用户数据
    public void saveuserdata(UserBean userBean);//保存用户数据
    public void showtitleclassift(String strclassify);//标题显示分类
    public void startprogressBar();//显示进度条加载
    public void stopprgressBar();//关闭进度条加载
    public void showerror(int code,String msg);//错误显示
    public void girdviewonclick(ConsultClassifyBean bean);//分类girdview的点击
    public void backhome();//用户点击主页
    public void initjpush(UserBean bean);//初始化推送服务
    public void viewdetail(String  uid,String bwztid,UserBean bean);
}
