package com.dawnlightning.ucqa.presenter;

import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.model.ClassifyModel;
import com.dawnlightning.ucqa.model.ConsultListModel;
import com.dawnlightning.ucqa.modelinterface.IClassifyModel;
import com.dawnlightning.ucqa.presenterinterface.IClassiftPresenter;
import com.dawnlightning.ucqa.viewinterface.IWelcomeView;
import com.dawnlightning.ucqa.model.ClassifyModel.loadclassifylistener;

import java.util.List;

/**
 * Created by Administrator on 2016/3/31.
 */
public class ClassifyPresenter implements IClassiftPresenter,loadclassifylistener{
    private ClassifyModel model;
    private IWelcomeView view;
    private ConsultListModel consultListModel;
    @Override
    public void onSuccess(List<ConsultClassifyBean> beans) {


    }

    @Override
    public void onFailure(int code, String msg) {
        view.showerror(msg);
    }

    public ClassifyPresenter( IWelcomeView view) {
        this.view = view;
        model =new ClassifyModel();
        consultListModel=new ConsultListModel();
    }
    @Override
    public void load() {
        model.loadclassifybean(ClassifyPresenter.this);
        consultListModel.initfirstlist();
    }
}
