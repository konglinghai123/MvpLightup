package com.dawnlightning.ucqa.presenter;

import com.dawnlightning.ucqa.model.RegisterModel;
import com.dawnlightning.ucqa.presenterinterface.IRegisterPresenter;
import com.dawnlightning.ucqa.viewinterface.IRegisterView;

/**
 * Created by Administrator on 2016/3/31.
 */
public class ResigterPresenter implements IRegisterPresenter,RegisterModel.registerlistener{
    private IRegisterView view;
    private RegisterModel model;
    public ResigterPresenter(IRegisterView view){
        this.view=view;
        model=new RegisterModel();
    }

    @Override
    public void onRegisterSuccess(RegisterModel bean) {
        view.registerSuccess(bean);
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
