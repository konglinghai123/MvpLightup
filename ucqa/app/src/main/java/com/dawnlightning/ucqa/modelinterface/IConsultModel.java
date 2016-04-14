package com.dawnlightning.ucqa.modelinterface;

import android.os.Handler;

import com.dawnlightning.ucqa.Bean.ConsultBean;
import com.dawnlightning.ucqa.Bean.UploadPicsBean;
import com.dawnlightning.ucqa.model.ConsultModel;

import java.util.List;

/**
 * Created by Administrator on 2016/4/14.
 */
public interface IConsultModel {
    public void uploadpic(List<UploadPicsBean> beans,Handler mhandler);
    public void sendconsult(ConsultBean bean,ConsultModel.SendConsultListener sendConsultListener);
}
