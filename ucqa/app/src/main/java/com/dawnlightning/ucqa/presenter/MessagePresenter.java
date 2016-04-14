package com.dawnlightning.ucqa.presenter;

import android.content.Context;

import com.dawnlightning.ucqa.Bean.DetailedBean;
import com.dawnlightning.ucqa.Bean.MessageBean;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.model.DetailedModel;
import com.dawnlightning.ucqa.model.MessageModel;
import com.dawnlightning.ucqa.modelinterface.IMessageModel;
import com.dawnlightning.ucqa.presenterinterface.IMessagePresenter;
import com.dawnlightning.ucqa.viewinterface.IMessageView;

import java.util.List;

/**
 * Created by Administrator on 2016/4/10.
 */
public class MessagePresenter implements IMessagePresenter,MessageModel.messagelistener {
    private IMessageView view;
    private MessageModel messageModel;
    private DetailedModel detailedModel;
    private MyApp myApp;
    public MessagePresenter(IMessageView view,MyApp myApp){
        this.view=view;
        this.myApp=myApp;
        detailedModel=new DetailedModel();
        messageModel=new MessageModel();
    }
    @Override
    public void messagelist(int page,int operate ,String m_auth) {
        messageModel.loadmessagelist(page,m_auth,operate,this);
    }


    @Override
    public void getmessageSuccess(int code,int operate ,List<MessageBean> bean) {
        view.loadmessageSuccess(bean,code,operate);
    }

    @Override
    public void getmessageFailure(int code,int operate,String msg) {
        view.loadmessageFailure(code,msg,operate);
    }

}
