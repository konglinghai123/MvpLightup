package com.dawnlightning.ucqa.modelinterface;

import android.os.Handler;

import com.dawnlightning.ucqa.Bean.PersonalDataBean;
import com.dawnlightning.ucqa.model.PersonalDataModel;

import java.io.File;

/**
 * Created by Administrator on 2016/4/20.
 */
public interface IPersonalDataModel {
    public void uploadavater(File avater,String m_auth,final Handler mhandler);
    public void modifypersonaldata(PersonalDataBean bean,String m_auth,PersonalDataModel.modifylistener modifylistener);
}
