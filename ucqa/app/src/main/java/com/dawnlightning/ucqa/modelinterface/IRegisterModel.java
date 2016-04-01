package com.dawnlightning.ucqa.modelinterface;


import com.dawnlightning.ucqa.model.RegisterModel;

/**
 * Created by Administrator on 2016/3/31.
 */
public interface IRegisterModel {
    public void register(RegisterModel model,RegisterModel.registerlistener listener);//注册

}
