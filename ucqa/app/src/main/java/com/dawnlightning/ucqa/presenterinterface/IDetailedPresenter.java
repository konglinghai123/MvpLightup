package com.dawnlightning.ucqa.presenterinterface;

import android.content.Intent;

import com.dawnlightning.ucqa.Bean.CommentBean;

/**
 * Created by Administrator on 2016/4/9.
 */
public interface IDetailedPresenter {
    public void loadconsultdetailed(int uid,int classid,String m_auth);//获取咨询详细
    public void loadmorecomment(int uid,int classid,int page,String m_auth);//获取评论列表
    public void setcomment(int classid,String message,String formhash,String m_auth);//评论
    public void loaduserdata(Intent intent);
    public void setreply(CommentBean bean,String username,int postion,int classid,int cid,String message,String formhash,String m_auth);//回复
    public void solve(int classid,String m_auth);//采纳
    public void delete(int classid,String m_auth);//关闭咨询
    public void report(String m_auth,int id,String reason);//举报
}
