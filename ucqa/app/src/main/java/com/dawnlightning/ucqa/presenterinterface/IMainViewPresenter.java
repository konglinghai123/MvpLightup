package com.dawnlightning.ucqa.presenterinterface;

import android.content.Intent;

import com.dawnlightning.ucqa.Bean.UserBean;

/**
 * Created by Administrator on 2016/4/1.
 */
public interface IMainViewPresenter {
    public void loadlist();
    public void loaddetail();
    public void checkupdate();
    public void loadclassify();
    public void loaduserdata(Intent intent);
}
