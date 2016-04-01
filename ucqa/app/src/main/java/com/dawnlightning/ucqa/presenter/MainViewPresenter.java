package com.dawnlightning.ucqa.presenter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.db.SharedPreferenceDb;
import com.dawnlightning.ucqa.presenterinterface.IMainViewPresenter;
import com.dawnlightning.ucqa.update.UpdateManager;
import com.dawnlightning.ucqa.update.UpdateStatus;
import com.dawnlightning.ucqa.util.SQLHelper;
import com.dawnlightning.ucqa.viewinterface.IMainView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/1.
 */
public class MainViewPresenter implements IMainViewPresenter {
    private MyApp myApp;
    private IMainView view;
    private UpdateManager updateManager;
    private SQLHelper sqlHelper;
    private SharedPreferenceDb sharedPreferenceDb;
    public MainViewPresenter(IMainView view,MyApp myApp){
        this.view=view;
        this.myApp=myApp;

    }

    @Override
    public void loaduserdata(Intent intent) {
        UserBean user=(UserBean)intent.getSerializableExtra("userdata");
        view.saveuserdata(user);
    }

    @Override
    public void loadlist() {
        if (sqlHelper!=null) {
            sqlHelper = new SQLHelper(myApp.getApplicationContext());
        }
          List<ConsultMessageBean> list=sqlHelper.queryconsult("全部");
          view.showconsultlist(list);

    }

    @Override
    public void loadclassify() {
        if (sqlHelper!=null) {
            sqlHelper = new SQLHelper(myApp.getApplicationContext());
        }
        List<ConsultClassifyBean> list=sqlHelper.queryclassify("眼科");
        if (list.size()>0){
            view.showclassifylist(list);
        }else{
            List<ConsultClassifyBean> staticlist=new ArrayList<ConsultClassifyBean>();
            ConsultClassifyBean bean=new ConsultClassifyBean();
            HashMap<String,String> mapid=new HashMap<String,String>();
            HashMap<String,String> mapname=new HashMap<String,String>();
            mapid.put("青少年近视","1");
            mapid.put("防盲治盲","2");
            mapid.put("飞秒激光治疗近视","3");
            mapid.put("青光眼","4");
            mapid.put("白内障","5");
            mapname.put("眼科","1");
            bean.setMapid(mapid);
            bean.setMapname(mapname);
            staticlist.add(bean);
            view.showclassifylist(staticlist);
        }


    }

    @Override
    public void loaddetail() {

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
                        view.showdownloaddialog();
                        break;
                    case UpdateStatus.UPDATA_ERROR:

                        break;
                    case UpdateStatus.UPDATA_NO:

                        break;
                }
            }
        });
    }
}
