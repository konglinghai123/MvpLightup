package com.dawnlightning.ucqa.presenterinterface;

/**
 * Created by Administrator on 2016/4/13.
 */
public interface ISettingPresenter {

  public void checkupdate();
    public void getversion();
    public void getcachesize();
    public void getpushstatus();
    public void clearcache();
    public void Push(Boolean isOpen);
    public void logoff(String uhash,String m_auth);

}
