package com.dawnlightning.ucqa.presenter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

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
        if (sqlHelper==null) {
            sqlHelper = new SQLHelper(myApp.getApplicationContext());
        }
          List<ConsultMessageBean> list=sqlHelper.queryconsult("全部");
          view.showconsultlist(list);

    }

    @Override
    public void loadclassify() {
        if (sqlHelper==null) {
            sqlHelper = new SQLHelper(myApp.getApplicationContext());
        }
        List<ConsultClassifyBean> list=sqlHelper.queryclassify("眼科");
        if (list.size()<0){
            ConsultClassifyBean bean6=new ConsultClassifyBean();
            bean6.setBwztclassarrid("6");
            bean6.setBwztclassarrname("全部");
            bean6.setBwztdivisionarrid("1");
            bean6.setBwztdivisionarrname("眼科");
            list.add(bean6);
            view.showclassifylist(list);
        }else{
            List<ConsultClassifyBean> staticlist=new ArrayList<ConsultClassifyBean>();
            ConsultClassifyBean bean1=new ConsultClassifyBean();
            bean1.setBwztclassarrid("1");
            bean1.setBwztclassarrname("青少年近视");
            bean1.setBwztdivisionarrid("1");
            bean1.setBwztdivisionarrname("眼科");

            ConsultClassifyBean bean2=new ConsultClassifyBean();
            bean2.setBwztclassarrid("2");
            bean2.setBwztclassarrname("防盲治盲");
            bean2.setBwztdivisionarrid("1");
            bean2.setBwztdivisionarrname("眼科");

            ConsultClassifyBean bean3=new ConsultClassifyBean();
            bean3.setBwztclassarrid("3");
            bean3.setBwztclassarrname("飞秒激光治疗近视");
            bean3.setBwztdivisionarrid("1");
            bean3.setBwztdivisionarrname("眼科"); ConsultClassifyBean bean=new ConsultClassifyBean();

            ConsultClassifyBean bean4=new ConsultClassifyBean();
            bean4.setBwztclassarrid("4");
            bean4.setBwztclassarrname("青光眼");
            bean4.setBwztdivisionarrid("1");
            bean4.setBwztdivisionarrname("眼科");

            ConsultClassifyBean bean5=new ConsultClassifyBean();
            bean5.setBwztclassarrid("5");
            bean5.setBwztclassarrname("白内障");
            bean5.setBwztdivisionarrid("1");
            bean5.setBwztdivisionarrname("眼科");

            ConsultClassifyBean bean6=new ConsultClassifyBean();
            bean6.setBwztclassarrid("6");
            bean6.setBwztclassarrname("全部");
            bean6.setBwztdivisionarrid("1");
            bean6.setBwztdivisionarrname("眼科");
            staticlist.add(bean1);
            staticlist.add(bean2);
            staticlist.add(bean3);
            staticlist.add(bean4);
            staticlist.add(bean5);
            staticlist.add(bean6);
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
                       updateManager.showNoticeDialog();
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
