package com.dawnlightning.ucqa.presenter;

import android.content.Context;
import android.content.Intent;

import com.dawnlightning.ucqa.Bean.CommentBean;
import com.dawnlightning.ucqa.Bean.DetailedBean;
import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.model.DetailedModel;
import com.dawnlightning.ucqa.presenterinterface.IDetailedPresenter;
import com.dawnlightning.ucqa.model.DetailedModel.detailedlistener;
import com.dawnlightning.ucqa.model.DetailedModel.setcommentlistener;
import com.dawnlightning.ucqa.model.DetailedModel.commentlistener;
import com.dawnlightning.ucqa.model.DetailedModel.replytlistener;
import com.dawnlightning.ucqa.model.DetailedModel.solvelistener;
import com.dawnlightning.ucqa.model.DetailedModel.deletelistener;
import com.dawnlightning.ucqa.model.DetailedModel.reportlistener;
import com.dawnlightning.ucqa.viewinterface.IDetailView;

import java.util.List;

/**
 * Created by Administrator on 2016/4/9.
 */
public class DetailedPresenter extends BasePresenter implements IDetailedPresenter,commentlistener,detailedlistener,setcommentlistener,replytlistener,deletelistener,solvelistener,reportlistener{
    private DetailedModel model;
    private IDetailView view;
    private Context context;
    public DetailedPresenter(IDetailView view,Context context){
        this.view=view;
        this.context=context;
        model=new DetailedModel();
    }
    @Override
    public void loadconsultdetailed(int uid, int classid, String m_auth) {

        model.loadconsultdetailed(uid,classid,m_auth,this);
    }

    @Override
    public void loaduserdata(Intent intent) {
        UserBean user=(UserBean)intent.getSerializableExtra("userdata");
        int bwztid=Integer.parseInt(intent.getStringExtra("bwztid"));
        int uid=Integer.parseInt(intent.getStringExtra("uid"));
        view.setuserdata(user, bwztid, uid);

    }

    @Override
    public void report(String m_auth, int id, String reason) {
        if (checkNetwork(context)){
            model.report(m_auth,id,reason,this);
        }else{
            view.solveFailure(-1,"网络连接不可用");
        }

    }

    @Override
    public void loadmorecomment(int uid, int classid, int page, String m_auth) {
        if (checkNetwork(context)){
            model.loadmorecomment(uid, classid, page, m_auth, this);
        }else{
            view.solveFailure(-1,"网络连接不可用");
        }

    }

    @Override
    public void setcomment(int classid, String message, String formhash, String m_auth) {
        if(checkNetwork(context)){
            model.setcomment(classid, message, formhash, m_auth, this);
        }else{
            view.solveFailure(-1,"网络连接不可用");
        }

    }

    @Override
    public void commentSuccess(List<CommentBean> beans) {
        view.showcomment(beans);
    }

    @Override
    public void commentFailure(int code, String msg) {
        view.loadcommenterror(code, msg);
    }
    @Override
    public void detailedSuccess(DetailedBean bean) {
        view.showdetailed(bean);
    }

    @Override
    public void detailedFailure(int code, String msg) {
        view.loaddetailederror(code, msg);
    }

    @Override
    public void setreply(CommentBean bean,String username,int postion,int classid,int cid, String message, String formhash, String m_auth) {
        if (checkNetwork(context)){
            model.setreply(bean, username, postion, classid, cid, message, formhash, m_auth, this);
        }else{
            view.solveFailure(-1,"网络连接不可用");
        }

    }

    @Override
    public void setcommentSuccess(int code, String msg) {
        view.setcommentSuccess(code, msg);
    }

    @Override
    public void setcommentFailure(int code, String msg) {
        view.setcommenctFailure(code, msg);
    }

    @Override
    public void replySuccess(CommentBean bean,int postion) {
        view.replySuccess(bean, postion);
    }

    @Override
    public void replyFailure(int code, String msg) {
        view.replyFailure(code, msg);
    }

    @Override
    public void solve(int classid, String m_auth) {
        if (checkNetwork(context)){
            model.solve(classid, m_auth, this);
        }else{
            view.solveFailure(-1,"网络连接不可用");
        }

    }

    @Override
    public void delete(int classid, String m_auth) {
        if (checkNetwork(context)){
            model.delete(classid, m_auth, this);
        }else{
            view.solveFailure(-1,"网络连接不可用");
        }

    }


    @Override
    public void solveSuccess(String msg) {
        view.solveSuccess(msg);
    }

    @Override
    public void deleteSuccess(String msg) {
        view.deleteSuceess(msg);
    }

    @Override
    public void deleteFailure(int code, String msg) {
        view.deleteFailure(code,msg);
    }

    @Override
    public void solveFailure(int code, String msg) {
        view.solveFailure(code,msg);
    }

    @Override
    public void reportSuccess(String msg) {
        view.reportSuceess(msg);
    }

    @Override
    public void reportFailure(int code, String msg) {
        view.replyFailure(code,msg);
    }
}
