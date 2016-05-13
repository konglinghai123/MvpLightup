package com.dawnlightning.ucqa.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.dawnlightning.ucqa.Bean.PersonalDataBean;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.model.PersonalDataModel;
import com.dawnlightning.ucqa.presenterinterface.IPersonalPersenter;
import com.dawnlightning.ucqa.util.DataCleanManager;
import com.dawnlightning.ucqa.util.SdCardUtil;
import com.dawnlightning.ucqa.viewinterface.IPersonalView;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/4/20.
 */
public class PersonalPersenter extends  BasePresenter implements IPersonalPersenter, PersonalDataModel.modifylistener {
    private IPersonalView view;
    private PersonalDataModel model;
    private Context context;
    public PersonalPersenter(IPersonalView view,Context context){
        this.view=view;
        this.context=context;
        model=new PersonalDataModel();
    }

    @Override
    public void doclearlogincache(String phone,String password) {

        model.clearlogincache(phone,password);
    }

    @Override
    public void douploadavater(File avater, String m_auth) {
        if (checkNetwork(context)){
            model.uploadavater(avater, m_auth,  mHandle);
        }else{
            view.showerror(-1,"网络连接不可用");
        }

    }

    @Override
    public void domodifypersonaldata(PersonalDataBean bean, String m_auth) {
        if (checkNetwork(context)){
            model.modifypersonaldata(bean, m_auth, this);
        }else {
            view.showerror(-1,"网络连接不可用");
        }

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
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, SdCardUtil.FILEDIR + "/" + SdCardUtil.FILECACHE);//获取到缓存的目录地址
        try{
            DataCleanManager.deleteFilesByDirectory(cacheDir);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
