package com.dawnlightning.ucqa.presenter;

import android.os.Handler;
import android.os.Message;
import com.dawnlightning.ucqa.Bean.ConsultBean;
import com.dawnlightning.ucqa.Bean.UploadPicsBean;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.model.ConsultModel;
import com.dawnlightning.ucqa.presenterinterface.IConsultPresenter;
import com.dawnlightning.ucqa.viewinterface.IConsultView;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ConsultPresenter implements IConsultPresenter,ConsultModel.SendConsultListener {
    private ConsultModel model;
    private MyApp myApp;
    private IConsultView view;
    public ConsultPresenter(IConsultView view,MyApp app){
        this.view=view;
        this.myApp=app;
        model=new ConsultModel();
    }
    @Override
    public void douploadpics(List<UploadPicsBean> list) {
        model.uploadpic(list,mHandle);
    }

    @Override
    public void dosentconsult(ConsultBean bean) {
        model.sendconsult(bean,this);
    }

    @Override
    public void SendSuccess(int code, String msg) {
       view.sendconsultSuccess(code,msg);
    }

    @Override
    public void SendFailure(int code, String msg) {
       view.sendconsultFailure(code,msg);
    }

    public  Handler mHandle=new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case Code.UPLOADSUCCESS:
                   view.savepicid(msg.arg1,msg.obj.toString());
                   break;
               case Code.UPLOADCHANGE:
                   view.updatepb(msg.arg1,Integer.parseInt(msg.obj.toString()));
                   break;
               case Code.UPLOADFAILURE:
                   view.uploadpicerror(msg.arg1,(File)msg.obj);
                   break;
           }
            super.handleMessage(msg);
        }
    };
}
