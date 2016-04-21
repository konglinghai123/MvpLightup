package com.dawnlightning.ucqa.presenter;

import android.os.Handler;
import android.os.Message;

import com.dawnlightning.ucqa.Bean.PersonalDataBean;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.model.PersonalDataModel;
import com.dawnlightning.ucqa.modelinterface.IPersonalDataModel;
import com.dawnlightning.ucqa.presenterinterface.IPersonalPersenter;
import com.dawnlightning.ucqa.util.DataCleanManager;
import com.dawnlightning.ucqa.util.SdCardUtil;
import com.dawnlightning.ucqa.viewinterface.IPersonalView;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/4/20.
 */
public class PersonalPersenter implements IPersonalPersenter, PersonalDataModel.modifylistener {
    private IPersonalView view;
    private PersonalDataModel model;
    private MyApp myApp;
    public PersonalPersenter(IPersonalView view,MyApp myApp){
        this.view=view;
        this.myApp=myApp;
        model=new PersonalDataModel();
    }
    @Override
    public void douploadavater(File avater, String m_auth) {
        model.uploadavater(avater, m_auth,  mHandle);
    }

    @Override
    public void domodifypersonaldata(PersonalDataBean bean, String m_auth) {
        model.modifypersonaldata(bean, m_auth, this);
    }

    @Override
    public void modifySuccess(String msg) {
        view.showsuccess(msg);
    }

    @Override
    public void modifyFailure(int code, String msg) {
        view.showerror(code,msg);
    }

    public Handler mHandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Code.UPLOADSUCCESS:
                    clearcache();
                    view.showavtar(msg.obj.toString());
                    break;
                case Code.UPLOADCHANGE:
                    view.updatepb(Integer.parseInt(msg.obj.toString()));
                    break;
                case Code.UPLOADFAILURE:
                    view.showerror(-1,msg.obj.toString());
                    break;
            }
            super.handleMessage(msg);
        }
    };
    public void clearcache() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(myApp, SdCardUtil.FILEDIR + "/" + SdCardUtil.FILECACHE);//获取到缓存的目录地址
        try{
            DataCleanManager.deleteFilesByDirectory(cacheDir);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
