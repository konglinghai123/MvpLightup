package com.dawnlightning.ucqa.viewinterface;

import com.dawnlightning.ucqa.model.RegisterModel;

/**
 * Created by Administrator on 2016/3/31.
 */
public interface IRegisterView {
    public void doregister(RegisterModel model);//进行注册操作
    public  void registerSuccess(RegisterModel model);//注册完成后
    public  void registerFailure(int code,String msg);//注册失败
}
