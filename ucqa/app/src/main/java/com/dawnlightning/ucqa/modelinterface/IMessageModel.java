package com.dawnlightning.ucqa.modelinterface;

import com.dawnlightning.ucqa.model.MessageModel;

/**
 * Created by Administrator on 2016/4/10.
 */
public interface IMessageModel{
  public void loadmessagelist(int page,String m_auth,int operate,MessageModel.messagelistener messagelistener);//获取消息列表
}
