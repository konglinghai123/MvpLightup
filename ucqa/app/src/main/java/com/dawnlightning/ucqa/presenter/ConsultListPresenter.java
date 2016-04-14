package com.dawnlightning.ucqa.presenter;

import android.content.Context;

import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.model.ConsultListModel;
import com.dawnlightning.ucqa.presenterinterface.IConsultListPresenter;
import com.dawnlightning.ucqa.util.SQLHelper;
import com.dawnlightning.ucqa.viewinterface.IConsultListView;

import java.util.List;

/**
 * Created by Administrator on 2016/4/1.
 */
public class ConsultListPresenter implements IConsultListPresenter,ConsultListModel.consultlistener {
    private ConsultListModel model;
    private IConsultListView view;
    private SQLHelper sqlHelper;
    private MyApp myApp;

    @Override
    public void loadassignlist(int page, int bwztclassid,String m_auth,int identity,int  operate) {
        model.loadassignconsultlist(this, page, bwztclassid,m_auth,identity,operate);
    }

    @Override
    public void consultonSuccess(int code,List<ConsultMessageBean> beans,int identity,int  operate) {
            savaconsultlist(beans);
            view.getSuccess(code, beans,identity,operate);

    }

    @Override
    public void consultonFailure(int code, String msg,int identity, int operate) {
            view.getFailure(code, msg,identity,operate);
    }
    public ConsultListPresenter(IConsultListView view, MyApp myApp){
        this.view=view;
        this.myApp=myApp;
        this.model=new ConsultListModel();
    }

    public void savaconsultlist(List<ConsultMessageBean> list){
        if (sqlHelper==null){
            sqlHelper=new SQLHelper(myApp);
        }
        //sqlHelper.deleteallconsult();
        sqlHelper.insertconsult(list);
    }
}
