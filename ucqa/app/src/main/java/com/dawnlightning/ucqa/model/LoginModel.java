package com.dawnlightning.ucqa.model;

import com.alibaba.fastjson.JSON;
import com.dawnlightning.ucqa.Bean.BaseBean;
import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.Bean.UserData;
import com.dawnlightning.ucqa.modelinterface.ILoginModel;
import com.dawnlightning.ucqa.util.AsyncHttp;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/3/31.
 */
public class LoginModel implements ILoginModel {
    private String username;
    private  String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public  interface loginlistener{
        void onSuccess(UserBean bean);
        void onFailure(int code,String msg);
    }

    @Override
    public void loginoff(String uhash, String m_auth,final loginlistener listener) {
        RequestParams params = new RequestParams();
        params.put("ac", "common");
        params.put("op", "logout");
        params.put("uhash",uhash);
        params.put("m_auth",m_auth);
        AsyncHttp.get(HttpConstants.HTTP_COMMENT, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                listener.onFailure(1, "注销失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                if ("0".equals(b.getCode())) {
                    listener.onSuccess(null);
                } else {
                    listener.onFailure(1,b.getMsg());
                }

            }

        });
    }

    @Override
    public void stopresquest() {
        AsyncHttp.stopresquest();
    }

    @Override
    public void login(String username,String password, final loginlistener listener) {
        RequestParams params = new RequestParams();
        params.put("ac", "login");
        params.put("username",username);
        params.put("password",password);
        params.put("loginsubmit",true);
        final String url =HttpConstants.HTTP_LOGIN+params.toString();
        if (AsyncHttp.getcache(url)!=null&&AsyncHttp.getcache(url).length()>0){
            String strcache=AsyncHttp.getcache(url);
            BaseBean b = JSON.parseObject(strcache, BaseBean.class);
            UserBean bean =new UserBean();
            bean.setM_auth(JSON.parseObject(b.getData().toString()).getString("m_auth"));
            bean.setFormhash(JSON.parseObject(b.getData().toString()).getString("formhash"));
            String space=JSON.parseObject(b.getData().toString()).getString("space");
            UserData data=JSON.parseObject(space, UserData.class);
            bean.setUserdata(data);
            listener.onSuccess(bean);
        }else{
            AsyncHttp.removecache(url);
        AsyncHttp.get(HttpConstants.HTTP_LOGIN, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                listener.onFailure(1, "登陆失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                if ("0".equals(b.getCode())) {
                    UserBean bean =new UserBean();
                    bean.setM_auth(JSON.parseObject(b.getData().toString()).getString("m_auth"));
                    bean.setFormhash(JSON.parseObject(b.getData().toString()).getString("formhash"));
                    String space=JSON.parseObject(b.getData().toString()).getString("space");
                    UserData data=JSON.parseObject(space, UserData.class);
                    bean.setUserdata(data);
                    AsyncHttp.savecache(url, response.toString(), 60*60*24*7);
                    listener.onSuccess(bean);
                } else {
                    listener.onFailure(1,b.getMsg());
                }

            }

        });}
    }
}
