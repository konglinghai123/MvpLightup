package com.dawnlightning.ucqa.model;

import com.alibaba.fastjson.JSON;
import com.dawnlightning.ucqa.Bean.BaseBean;
import com.dawnlightning.ucqa.modelinterface.IRegisterModel;
import com.dawnlightning.ucqa.util.AsyncHttp;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016/3/31.
 */
public class RegisterModel implements IRegisterModel {
    private  String password;
    private  String usename;

    @Override
    public void register(final RegisterModel model,final registerlistener listener) {
        RequestParams params = new RequestParams();
        params.put("ac", "register");
        params.put("op","seccode");
        AsyncHttp.get(HttpConstants.HTTP_LOGIN, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                listener.onRegisterFailure(1, "获取注册验证码失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                if ("0".equals(b.getCode())) {
                    String strcode=JSON.parseObject(b.getData().toString()).getString("seccode");
                    String strm_auth=JSON.parseObject(b.getData().toString()).getString("seccode_auth");
                    true_register(strm_auth,strcode,model,listener);
                } else {
                    listener.onRegisterFailure(1, b.getMsg());
                }

            }

        });
    }
    private void true_register(String m_auth,String seccode, final RegisterModel model,final registerlistener listener){
        RequestParams params = new RequestParams();
        params.put("ac", "register");
        params.put("registersubmit",true);
        params.put("username",model.getUsename());
        params.put("password",model.getPassword());
        params.put("password2",model.getPassword());
        params.put("seccode",seccode);
        params.put("m_auth",m_auth);
        AsyncHttp.get(HttpConstants.HTTP_LOGIN, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                listener.onRegisterFailure(1, "注册失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                BaseBean b = JSON.parseObject(response.toString(), BaseBean.class);
                if ("0".equals(b.getCode())) {
                    listener.onRegisterSuccess(model);

                } else {
                    listener.onRegisterFailure(1, b.getMsg());
                }

            }

        });
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public RegisterModel(){

    }
    public RegisterModel(String password, String usename) {
        this.password = password;
        this.usename = usename;
    }

    public String getUsename() {

        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }
    public interface registerlistener{
        void onRegisterSuccess(RegisterModel bean);
        void onRegisterFailure(int code,String msg);
    }


}
