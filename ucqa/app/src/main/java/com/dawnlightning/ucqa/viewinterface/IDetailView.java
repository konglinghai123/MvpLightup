package com.dawnlightning.ucqa.viewinterface;



import android.content.Intent;

import com.dawnlightning.ucqa.Bean.CommentBean;
import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.Bean.DetailedBean;
import com.dawnlightning.ucqa.Bean.UserBean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/9.
 */
public interface IDetailView {
    public void doloadconsultdetailed(int uid,int classid,String m_auth);//获取咨询详细
    public void doloadmorecomment(int uid,int classid,int page,String m_auth);//获取评论列表
    public void dosetcomment(int classid,String message,String formhash,String m_auth);//评论
    public void showdetailed(DetailedBean bean);//显示咨询详细
    public void loaddetailederror(int code,String msg);//获取咨询详细失败
    public void showcomment(List<CommentBean> beans);//显示评论
    public void loadcommenterror(int code,String msg);//加载评论失败
    public void setcommentSuccess(int code,String msg);//评论成功
    public void setcommenctFailure(int code,String msg);//评论失败
    public void shareconsult();//分享
    public void getuserdate(Intent intent);
    public void setuserdata(UserBean userBean,int bwztid,int uid);//设置用户信息
    public void startloadpb();//开始加载
    public void stoploadpb();//停止加载
    public void replySuccess(CommentBean bean,int postion);//回复成功
    public void replyFailure(int code,String msg);//回复失败
    public void showreplydialog(int postion);//显示回复栏
    public void hidekeyboard();
    public void showketboard();
    public void updatecommentview();
    public void initdialoglistview();
    public void dosolve(int classid,String m_auth);//采纳
    public void solveSuccess(String msg);
    public void solveFailure(int code,String msg);
    public void deleteSuceess(String msg);
    public void deleteFailure(int code,String msg);
    public void dodelete(int classid,String m_auth);//关闭咨询
    public void reportSuceess(String msg);
    public void reportFailure(int code,String msg);
    public void doreport(String m_auth,int classid,String reason);//举报
    public void showreportdialog();

}
