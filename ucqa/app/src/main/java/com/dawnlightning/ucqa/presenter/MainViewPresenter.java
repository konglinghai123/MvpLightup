package com.dawnlightning.ucqa.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dawnlightning.ucqa.Bean.BaseBean;
import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.db.SharedPreferenceDb;
import com.dawnlightning.ucqa.presenterinterface.IMainViewPresenter;
import com.dawnlightning.ucqa.update.UpdateManager;
import com.dawnlightning.ucqa.update.UpdateStatus;
import com.dawnlightning.ucqa.util.AsyncHttp;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.dawnlightning.ucqa.viewinterface.IMainView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/4/1.
 */
public class MainViewPresenter implements IMainViewPresenter {
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
