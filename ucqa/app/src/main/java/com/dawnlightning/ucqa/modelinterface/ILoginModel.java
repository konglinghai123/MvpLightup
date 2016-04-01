package com.dawnlightning.ucqa.modelinterface;
import com.dawnlightning.ucqa.model.LoginModel;
import  com.dawnlightning.ucqa.model.LoginModel.loginlistener;
/**
 * Created by Administrator on 2016/3/31.
 */
public interface ILoginModel {
    public void login(LoginModel model,loginlistener listener);//登陆
}
