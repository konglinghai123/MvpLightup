package com.dawnlightning.ucqa.modelinterface;

import com.dawnlightning.ucqa.Bean.CommentBean;
import com.dawnlightning.ucqa.model.DetailedModel;

/**
 * Created by Administrator on 2016/4/1.
 */
public interface IDetailedModel {
    public void loadconsultdetailed(int uid,int classid,String m_auth,DetailedModel.detailedlistener detailedlistener);//获取咨询详细
    public void loadmorecomment(int uid,int classid,int page,String m_auth,DetailedModel.commentlistener commentlistener);//获取评论列表
    public void solve(int classid,String m_auth,DetailedModel.solvelistener solvelistener);//采纳
    public void delete(int classid,String m_auth,DetailedModel.deletelistener deletelistener);//关闭咨询
    public void setcomment(int classid,String message,String formhash,String m_auth,DetailedModel.setcommentlistener setcommentlistener);//评论
    public void setreply(CommentBean bean,String username,int postion,int classid,int cid,String message,String formhash,String m_auth,DetailedModel.replytlistener replylistener);//回复
    public void report(String m_auth,int id,String reason,DetailedModel.reportlistener reportlistener);
}
