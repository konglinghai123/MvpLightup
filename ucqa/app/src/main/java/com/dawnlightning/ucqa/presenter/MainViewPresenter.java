package com.dawnlightning.ucqa.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.db.SharedPreferenceDb;
import com.dawnlightning.ucqa.presenterinterface.IMainViewPresenter;
import com.dawnlightning.ucqa.update.UpdateManager;
import com.dawnlightning.ucqa.update.UpdateStatus;
import com.dawnlightning.ucqa.viewinterface.IMainView;

/**
 * Created by Administrator on 2016/4/1.
 */
public class MainViewPresenter extends BasePresenter implements IMainViewPresenter {
    private Context mContext;
    private IMainView view;
    private UpdateManager updateManager;
    private SharedPreferenceDb sharedPreferenceDb;
    public MainViewPresenter(IMainView view,Context mContext){
        this.view=view;
        this.mContext=mContext;

    }
    @Override
    public void loaduserdata(Intent intent) {
        UserBean user=(UserBean)intent.getSerializableExtra("userdata");
        view.saveuserdata(user);


    }
    @Override
    public void checkupdate() {
        updateManager=new UpdateManager(mContext,new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                switch (msg.what) {

                    case UpdateStatus.UPDATA_CLIENT:
                        view.showupdate();
                        break;
                    case UpdateStatus.UPDATA_ERROR:
                        break;
                    case UpdateStatus.UPDATA_NO:
                        break;
                }
            }
        });
        updateManager.checkUpdate();
    }
}
