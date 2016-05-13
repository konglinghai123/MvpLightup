package com.dawnlightning.ucqa.presenter;

import android.content.Context;

import com.dawnlightning.ucqa.Bean.MessageBean;
import com.dawnlightning.ucqa.model.DetailedModel;
import com.dawnlightning.ucqa.model.MessageModel;
import com.dawnlightning.ucqa.presenterinterface.IMessagePresenter;
import com.dawnlightning.ucqa.viewinterface.IMessageView;

import java.util.List;

/**
 * Created by Administrator on 2016/4/10.
 */
public class MessagePresenter extends BasePresenter implements IMessagePresenter,MessageModel.messagelistener {
    private IMessageView view;
    private MessageModel messageModel;
    private DetailedModel detailedModel;
    private Context context;
    public MessagePresenter(IMessageView view,Context context){
        this.view=view;
        this.context=context;
        detailedModel=new DetailedModel();
        messageModel=new MessageModel();
    }
    @Override
    public void messagelist(int page,int operate ,String m_auth) {
        if (checkNetwork(context)){
            messageModel.loadmessagelist(page,m_auth,operate,this);
        }else{
            view.showerror(-1,"网络连接不可用");
        }


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
