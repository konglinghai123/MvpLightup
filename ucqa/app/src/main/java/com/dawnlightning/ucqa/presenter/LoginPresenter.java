package com.dawnlightning.ucqa.presenter;

import android.content.Context;

import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.model.LoginModel;
import com.dawnlightning.ucqa.model.LoginModel.loginlistener;
import com.dawnlightning.ucqa.presenterinterface.ILoginPresenter;
import com.dawnlightning.ucqa.util.AppUtils;
import com.dawnlightning.ucqa.viewinterface.ILoginView;

/**
 * Created by Administrator on 2016/3/31.
 */
public class LoginPresenter implements ILoginPresenter,loginlistener {
    private ILoginView view;
    private LoginModel model;
    private Context context;
    public  LoginPresenter(ILoginView view,Context context){
        this.view=view;
        this.model=new LoginModel();
        this.context=context;
    }
    @Override
    public void login(LoginModel model) {

        if (model.getUsername()!=""&&model.getPassword()!=""){
            if (model.getUsername()!=""){
                if (AppUtils.checkNetwork(context)){
                    view.showloadingdialog("登陆中");
                   model.login(model, this);
                }else{
                    view.loginFailure(1, "请检查当前网络连接");
                }
            }else{
                view.loginFailure(1, "请输入手机号码");
            }
        }else{
            view.loginFailure(1, "请输入账号密码");
        }

    }

    @Override
    public void onSuccess(UserBean bean) {
        view.dissmissloadingdialog();
        view.loginSuccess(bean);
    }

    @Override
    public void onFailure(int code, String msg) {
        view.dissmissloadingdialog();
        view.loginFailure(code,msg);
    }
}
