package com.dawnlightning.ucqa.viewinterface;

/**
 * Created by Administrator on 2016/4/20.
 */
public interface IPersonalView {
    public void showerror(int code,String msg);
    public void showsuccess(String msg);
    public void showavtar(String url);
    public void updatepb(int present);//更新图片上传进度
}
