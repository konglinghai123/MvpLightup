package com.dawnlightning.ucqa.presenter;

import android.content.Context;

import com.dawnlightning.ucqa.model.RegisterModel;
import com.dawnlightning.ucqa.presenterinterface.IRegisterPresenter;
import com.dawnlightning.ucqa.viewinterface.IRegisterView;

/**
 * Created by Administrator on 2016/3/31.
 */
public class ResigterPresenter extends BasePresenter implements IRegisterPresenter,RegisterModel.registerlistener{
    private IRegisterView view;
    private RegisterModel model;
    private Context context;
    public ResigterPresenter(IRegisterView view,Context context){
        this.context=context;
        this.view=view;
        model=new RegisterModel();
    }

    @Override
    public void onRegisterSuccess(RegisterModel bean) {
        if (checkNetwork(context)){
            view.registerSuccess(bean);
        }else{
            view.registerFailure(-1,"网络连接不可用");
        }

    }

    @Override
    public void onRegisterFailure(int code, String msg) {
        view.registerFailure(code,msg);
    }

    @Override
    public void register(RegisterModel model) {
        model.register(model,this);
    }
}
