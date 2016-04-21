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
    public void selectview(int id);//根据ID跳转不同的view
    public void docheckupdate();//检查用户是否需要更新
    public void doloaduserdata(Intent intent);//加载用户数据
    public void saveuserdata(UserBean userBean);//保存用户数据
    public void showtitleclassift(String strclassify);//标题显示分类
    public void initjpush(UserBean bean);//初始化推送服务
    public void showupdate();//左边菜单显示new

}
