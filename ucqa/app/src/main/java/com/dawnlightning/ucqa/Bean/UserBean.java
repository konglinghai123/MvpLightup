package com.dawnlightning.ucqa.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/31.
 */
public class UserBean implements Serializable {

    String m_auth;//登陆验证秘钥
    String formhash;//防伪验证码
    UserData userdata;//用户数据
    public UserData getUserdata() {
        return userdata;
    }

    public void setUserdata(UserData userdata) {
        this.userdata = userdata;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "m_auth='" + m_auth + '\'' +
                ", formhash='" + formhash + '\'' +
                ", userdata=" + userdata +
                '}';
    }

    public UserBean(String m_auth, String formhash, UserData userdata) {

        this.m_auth = m_auth;
        this.formhash = formhash;
        this.userdata = userdata;
    }

    public UserBean(){

    }
    public String getFormhash() {
        return formhash;
    }
    public void setFormhash(String formhash) {
        this.formhash = formhash;
    }
    public String getM_auth() {
        return m_auth;
    }
    public void setM_auth(String m_auth) {
        this.m_auth = m_auth;
    }

}
