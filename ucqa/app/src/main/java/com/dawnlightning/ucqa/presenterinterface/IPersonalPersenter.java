package com.dawnlightning.ucqa.presenterinterface;

import com.dawnlightning.ucqa.Bean.PersonalDataBean;

import java.io.File;

/**
 * Created by Administrator on 2016/4/20.
 */
public interface IPersonalPersenter {
    public void douploadavater(File avater,String m_auth);
    public void domodifypersonaldata(PersonalDataBean bean,String m_auth);
    public void doclearlogincache(String phone,String password);
}
