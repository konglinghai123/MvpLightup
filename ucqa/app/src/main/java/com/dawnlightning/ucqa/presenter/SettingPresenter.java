package com.dawnlightning.ucqa.presenter;

import android.os.Handler;
import android.os.Message;

import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.db.SharedPreferenceDb;
import com.dawnlightning.ucqa.model.LoginModel;
import com.dawnlightning.ucqa.presenterinterface.ISettingPresenter;
import com.dawnlightning.ucqa.update.UpdateManager;
import com.dawnlightning.ucqa.update.UpdateStatus;
import com.dawnlightning.ucqa.util.DataCleanManager;
import com.dawnlightning.ucqa.util.SdCardUtil;
import com.dawnlightning.ucqa.viewinterface.ISettingView;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/13.
 */
public class SettingPresenter implements ISettingPresenter,LoginModel.loginlistener {
    public ISettingView view;
    public MyApp myApp;
    private UpdateManager updateManager;
    private SharedPreferenceDb sharedPreferenceDb;
    private LoginModel model;
    public SettingPresenter(ISettingView view,MyApp myApp){
        this.view=view;
        this.myApp=myApp;
        sharedPreferenceDb=new SharedPreferenceDb(myApp);

        model=new LoginModel();
    }
    @Override
    public void checkupdate() {
        updateManager=new UpdateManager(myApp.getApplicationContext(),new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {

                    case UpdateStatus.UPDATA_CLIENT:
                        updateManager.showNoticeDialog();
                        break;
                    case UpdateStatus.UPDATA_ERROR:
                        view.showmsg("获取版本错误");
                        break;
                    case UpdateStatus.UPDATA_NO:
                        view.showmsg("已为最新版本");
                        break;
                }
            }
        });
    }

    @Override
    public void getversion() {
        if (updateManager!=null){
            view.showversion("V"+String.valueOf(updateManager.getversionname()));
        }

    }

    @Override
    public void getpushstatus() {
       view.showpushstatus(sharedPreferenceDb.getPush());

    }

    @Override
    public void getcachesize() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(myApp, SdCardUtil.FILEDIR + "/" + SdCardUtil.FILECACHE);//获取到缓存的目录地址
        try{

            view.showcachesize(DataCleanManager.getCacheSize(cacheDir));
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void clearcache() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(myApp, SdCardUtil.FILEDIR + "/" + SdCardUtil.FILECACHE);//获取到缓存的目录地址
        try{
            DataCleanManager.deleteFilesByDirectory(cacheDir);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        view.showmsg("清除成功");
        getcachesize();

    }

    @Override
    public void Push(Boolean isOpen) {
        sharedPreferenceDb.setPush(isOpen);
        view.showmsg("设置成功");
    }

    @Override
    public void logoff(String uhash,String m_auth) {
        model.loginoff(uhash,m_auth,this);
        sharedPreferenceDb.setuserAutoLogin(false);
    }

    @Override
    public void onSuccess(UserBean bean) {

    }

    @Override
    public void onFailure(int code, String msg) {

    }
}
