package com.dawnlightning.ucqa.presenterinterface;


import com.dawnlightning.ucqa.Bean.ConsultBean;
import com.dawnlightning.ucqa.Bean.UploadPicsBean;
import java.util.List;

/**
 * Created by Administrator on 2016/4/14.
 */
public interface IConsultPresenter {
    public void douploadpics(List<UploadPicsBean> list);//上传图片
    public void dosentconsult(ConsultBean bean);//发布咨询
}
