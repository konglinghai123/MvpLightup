package com.dawnlightning.ucqa.viewinterface;

import com.dawnlightning.ucqa.Bean.MessageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/10.
 */
public interface IMessageView {
    public void doloadmessagelist(int page,int operate,String m_auth);//获取消息列表
    public void loadmessageSuccess(List<MessageBean> beans,int code,int operate);
    public void loadmessageFailure(int code,String msg,int operate);
    public  void messagelistrefresh(int  operate);
    public void showerror(int code,String msg);
}
