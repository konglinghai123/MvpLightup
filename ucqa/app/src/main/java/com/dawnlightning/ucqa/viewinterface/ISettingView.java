package com.dawnlightning.ucqa.viewinterface;

/**
 * Created by Administrator on 2016/4/13.
 */
public interface ISettingView {
    public void docheckupdate(Boolean isShowupdatedialog);
    public void showversion(String versioncode);
    public void showcachesize(String cachesize);
    public void showpushstatus(boolean status);
    public void showmsg(String msg);
    public void help();
    public void clearcachesize();
    public void loginoff();
    public void about();
    public void closepush();
    public void showupdatestatus();
}
