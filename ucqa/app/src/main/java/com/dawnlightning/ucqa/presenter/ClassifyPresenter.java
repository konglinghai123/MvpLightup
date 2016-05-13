package com.dawnlightning.ucqa.presenter;

import android.content.Context;

import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.model.ClassifyModel;
import com.dawnlightning.ucqa.model.ConsultListModel;
import com.dawnlightning.ucqa.presenterinterface.IClassiftPresenter;
import com.dawnlightning.ucqa.model.ClassifyModel.loadclassifylistener;

import java.util.List;

/**
 * Created by Administrator on 2016/3/31.
 */
public class ClassifyPresenter extends BasePresenter implements IClassiftPresenter,loadclassifylistener{
    private ClassifyModel model;
    private ConsultListModel consultListModel;
    private Context context;
    @Override
    public void onSuccess(List<ConsultClassifyBean> beans) {


    }

    @Override
    public void onFailure(int code, String msg) {

    }

    public ClassifyPresenter(Context context) {
        this.context=context;
        model =new ClassifyModel();
        consultListModel=new ConsultListModel();
    }
    @Override
    public void load() {
        if (checkNetwork(context)) {
            model.loadclassifybean(ClassifyPresenter.this);
            consultListModel.initfirstlist();
        }

    }
}
