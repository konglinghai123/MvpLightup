package com.dawnlightning.ucqa.model;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dawnlightning.ucqa.Bean.BaseBean;
import com.dawnlightning.ucqa.Bean.CommentBean;
import com.dawnlightning.ucqa.Bean.DetailedBean;
import com.dawnlightning.ucqa.Bean.PicsBean;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.modelinterface.IDetailedModel;
import com.dawnlightning.ucqa.util.AsyncHttp;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/4/1.
 */
public class DetailedModel implements IDetailedModel{
    public interface detailedlistener{
        public void detailedSuccess(DetailedBean bean);
        public void detailedFailure(int code,String msg);
    }
    public interface commentlistener{
        public void commentSuccess(List<CommentBean> beans);
        public void commentFailure(int code,String msg);
    }
    public interface replytlistener{
        public void replySuccess(CommentBean bean,int postion);
        public void replyFailure(int code,String msg);
    }
    public interface setcommentlistener{
        public void setcommentSuccess(int code,String msg);
        public void setcommentFailure(int code,String msg);
    }
    public interface solvelistener{
        public void solveSuccess(String msg);
        public void solveFailure(int code,String msg);
    }
    public interface deletelistener{
        public void deleteSuccess(String msg);
        public void deleteFailure(int code,String msg);
    }

    @Override
    public void loadconsultdetailed(int uid, int classid, String m_auth, final  detailedlistener detailedlistener) {
        RequestParams params = new RequestParams();
        params.put("do", "bwzt");
        params.put("uid",uid);
        params.put("id",classid);
        params.put("m_auth",m_auth);
        AsyncHttp.get(HttpConstants.HTTP_SELECTION, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                detailedlistener.detailedFailure(Code.SERVER_LOAD_FAILURE, "获取咨询详细失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                if ("0".equals(b.getCode())) {
                    com.alibaba.fastjson.JSONObject j=(com.alibaba.fastjson.JSONObject) JSON.parse(b.getData());
                    com.alibaba.fastjson.JSONObject js=(com.alibaba.fastjson.JSONObject) JSON.parse(j.getString("bwzt"));
                    DetailedBean  detailedBean=new DetailedBean();
                    detailedBean.setAge(js.getString("age"));
                    detailedBean.setContent(js.getString("message"));
                    detailedBean.setDatetime(js.getString("dateline"));
                    detailedBean.setSubject(js.getString("subject"));
                    detailedBean.setUid(js.getString("uid"));
                    detailedBean.setUsename(js.getString("username"));
                    detailedBean.setAvatar_url(js.getString("avatar_url"));
                    detailedBean.setName(js.getString("name"));
                    detailedBean.setViewnum(js.getString("viewnum"));
                    detailedBean.setName(js.getString("name"));
                    detailedBean.setReplynum(js.getString("replynum"));
                    detailedBean.setBwztid(Integer.parseInt(js.getString("bwztid")));
                    detailedBean.setStatus(Integer.parseInt(js.getString("status")));
                    detailedBean.setSex(js.getString("sex"));
                    detailedBean.setComment(JSON.parseArray(js.getString("replylist"), CommentBean.class));

                    List<CommentBean> newlist=new ArrayList<CommentBean>();
                    for (CommentBean commentbean:detailedBean.getComment()) {
                        newlist.add(commentbean);
                    }
                     for (int i=0;i<detailedBean.getComment().size();i++){
                        List<String> replylist=new ArrayList<String>();
                        String comment=detailedBean.getComment().get(i).getAuthor()+": "+detailedBean.getComment().get(i).getMessage();
                        for (int z=i+1;z<detailedBean.getComment().size();z++){
                            String reply=detailedBean.getComment().get(z).getMessage();
                            if (reply.contains(comment)){
                                String name="";
                                if (detailedBean.getComment().get(z).getName().length()>0){
                                    name=detailedBean.getComment().get(z).getName();
                                }else{
                                    name=detailedBean.getComment().get(z).getAuthor();
                                }
                                reply=name+": "+reply.replace(comment,"");

                                replylist.add(reply);
                                newlist.remove(detailedBean.getComment().get(z));
                            }
                        }
                         if(i<=newlist.size()-1){
                             newlist.get(i).setReplylist(replylist);
                         }

                    }

                    detailedBean.setComment(newlist);
                    JSONArray jsonArray = JSONArray.parseArray(js.getString("pics"));
                    List<PicsBean> list=new ArrayList<PicsBean>();
                    if(jsonArray!=null){
                        for(int k=0;k<jsonArray.size();k++){

                            com.alibaba.fastjson.JSONObject objpic=(com.alibaba.fastjson.JSONObject) JSON.parse(jsonArray.get(k).toString());
                            PicsBean p=new PicsBean( objpic.getString("picurl"),objpic.getString("title"));
                            list.add(p);
                        }

                        detailedBean.setPics(list);
                    }
                    Log.e("list",String.valueOf(list.size()));
                    detailedlistener.detailedSuccess(detailedBean);

                } else {
                    detailedlistener.detailedFailure(Code.LOAD_NODATA, b.getMsg());
                }

            }
        });
    }



        @Override
    public void loadmorecomment(int uid, int classid, int page, String m_auth, final  commentlistener commentlistener) {
            RequestParams params = new RequestParams();
            params.put("do", "bwzt");
            params.put("uid",uid);
            params.put("id",classid);
            params.put("m_auth",m_auth);
            params.put("page",1);
            AsyncHttp.get(HttpConstants.HTTP_SELECTION, params, new JsonHttpResponseHandler() {

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    commentlistener.commentFailure(1, "获取更多评论失败");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers,
                                      JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                    if ("0".equals(b.getCode())) {
                        com.alibaba.fastjson.JSONObject j=(com.alibaba.fastjson.JSONObject) JSON.parse(b.getData());
                        com.alibaba.fastjson.JSONObject js=(com.alibaba.fastjson.JSONObject) JSON.parse(j.getString("bwzt"));

                        List<CommentBean> list=JSON.parseArray(js.getString("replylist"), CommentBean.class);
                        if (list.size()>0){
                            commentlistener.commentSuccess(list);
                        }else{
                            commentlistener.commentFailure(1,"没有更多的评论");
                        }


                    } else {
                        commentlistener.commentFailure(1, b.getMsg());
                    }
            }});
        }

    @Override
    public void solve(int classid, String m_auth, final solvelistener solvelistener) {
        RequestParams params = new RequestParams();
        params.put("ac", "bwzt");
        params.put("bwztid",classid);
        params.put("op","alterstatus");
        params.put("m_auth",m_auth);
        params.put("status",1);
        params.put("bwztsubmit",true);


        AsyncHttp.get(HttpConstants.HTTP_COMMENT, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                solvelistener.solveFailure(1, "采纳失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                for (int i=0;i<headers.length;i++){
                    Log.e("head",headers[i].getName()+":"+headers[i].getValue());
                }
                BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                if ("0".equals(b.getCode())) {

                    solvelistener.solveSuccess("成功采纳");

                } else {
                    solvelistener.solveFailure(1, b.getMsg());
                }
            }});
    }

    @Override
    public void delete(int classid, String m_auth, final  deletelistener deletelistener) {
        RequestParams params = new RequestParams();
        params.put("ac", "bwzt");
        params.put("bwztid",classid);
        params.put("op","delete");
        params.put("m_auth",m_auth);
        params.put("deletesubmit",true);
        AsyncHttp.get(HttpConstants.HTTP_COMMENT, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                deletelistener.deleteFailure(1, "删除失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                if ("0".equals(b.getCode())) {

                    deletelistener.deleteSuccess("删除成功");

                } else {
                    deletelistener.deleteFailure(1, b.getMsg());
                }
            }
        });
    }

    @Override
    public void setcomment(int classid, String message, String formhash,String m_auth,final  setcommentlistener setcommentlistener) {
        String url=String.format(HttpConstants.HTTP_COMMENT+"ac=comment&inajax=1&m_auth=%s",m_auth);
        RequestParams params = new RequestParams();
        params.put("message", message);
        params.put("id",classid);
        params.put("idtype","bwztid");
        params.put("formhash",formhash);
        params.put("commentsubmit",true);
        Log.e("comment", "评论中");
        AsyncHttp.post(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                setcommentlistener.setcommentFailure(1, "评论失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.e("comment", response.toString());
                BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                if ("0".equals(b.getCode())) {

                    setcommentlistener.setcommentSuccess(0, "评论成功");

                } else {

                    setcommentlistener.setcommentFailure(1, b.getMsg()+11);
                }
            }});
    }

    @Override
    public void setreply(final  CommentBean bean,final  String username, final int postion,int classid, int cid, final String message, String formhash, String m_auth, final  replytlistener replylistener) {
        String url=String.format(HttpConstants.HTTP_COMMENT+"ac=comment&inajax=1&m_auth=%s",m_auth);
        RequestParams params = new RequestParams();
        params.put("message", message);
        params.put("id",classid);
        params.put("cid",cid);
        params.put("idtype","bwztid");
        params.put("formhash",formhash);
        params.put("commentsubmit",true);
        AsyncHttp.post(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                replylistener. replyFailure(1, "回复失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                if ("0".equals(b.getCode())) {
                    CommentBean newbean=bean;
                    newbean.getReplylist().add(username + ": " + message);
                    replylistener.replySuccess(newbean, postion);

                } else {
                    replylistener. replyFailure(1, b.getMsg());
                }
            }});
    }
}
