package com.dawnlightning.ucqa.presenter;

import android.content.Context;

import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.model.ConsultListModel;
import com.dawnlightning.ucqa.presenterinterface.IConsultListPresenter;
import com.dawnlightning.ucqa.viewinterface.IConsultListView;

import java.util.List;

/**
 * Created by Administrator on 2016/4/1.
 */
public class ConsultPresenter implements IConsultListPresenter,ConsultListModel.consultlistener {
    private ConsultListModel model;
    private IConsultListView view;

    @Override
    public void loadfistlist() {
        model.loadfirstconsultlist(this);
    }

    @Override
    public void loadassignlist(int page, String bwztclassid) {
        model.loadassignconsultlist(this,page,bwztclassid);
    }

    @Override
    public void consultonSuccess(List<ConsultMessageBean> beans) {
            view.getSuccess(beans);
    }

    @Override
    public void consultonFailure(int code, String msg) {
            view.getFailure(code,msg);
    }
    public ConsultPresenter(IConsultListView view){
        this.view=view;
        this.model=new ConsultListModel();
    }
}
