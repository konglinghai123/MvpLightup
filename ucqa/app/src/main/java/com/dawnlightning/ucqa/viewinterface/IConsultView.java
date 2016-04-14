package com.dawnlightning.ucqa.viewinterface;

import com.dawnlightning.ucqa.Bean.ConsultBean;
import com.dawnlightning.ucqa.Bean.UploadPicsBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/14.
 */
public interface IConsultView {
    public void showerror(int code,String msg);//显示错误
    public void savepicid(int picid,String strpicid);//保存服务器返回的图片ID
    public void updatepb(int pbid,int present);//更新图片上传进度
    public void sendconsult(ConsultBean bean);//发布咨询
    public void uploadpic(List<UploadPicsBean> list);//上传图片
}
