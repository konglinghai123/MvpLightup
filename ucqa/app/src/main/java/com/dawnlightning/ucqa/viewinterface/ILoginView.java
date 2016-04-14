package com.dawnlightning.ucqa.viewinterface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.model.LoginModel;

/**
 * Created by Administrator on 2016/3/31.
 */
public interface ILoginView {
    public  View initview(LayoutInflater inflater,ViewGroup container);//初始化视图
    public  void dologin(LoginModel model);//登陆操作
    public  void loginSuccess(UserBean bean);//登陆完成后保存用户数据
    public  void loginFailure(int code,String msg);
    public  void clearpassword();//登陆失败后清除输入框
    public void showloadingdialog(String msg);//显示加载对话框
    public void dissmissloadingdialog();//关闭加载对话框
    public void saveuserdata(UserBean bean);//登陆成功后保存用户数据

}
