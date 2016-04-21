package com.dawnlightning.ucqa.modelinterface;
import com.dawnlightning.ucqa.model.LoginModel;
import  com.dawnlightning.ucqa.model.LoginModel.loginlistener;
/**
 * Created by Administrator on 2016/3/31.
 */
public interface ILoginModel {
    public void login(String username,String password,loginlistener listener);//登陆
    public void loginoff(String uhash,String m_auth,loginlistener listener );//注销
    public void stopresquest();
}
