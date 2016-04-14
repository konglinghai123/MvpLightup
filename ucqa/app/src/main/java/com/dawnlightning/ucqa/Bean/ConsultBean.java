package com.dawnlightning.ucqa.Bean;

import java.util.List;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ConsultBean {
    private String m_auth;
    private String formhash;
    private String subject;
    private int bwztclassid;
    private int bwztdivisionid;
    private String sex;
    private String age;
    private String message;
    private List<String> picids;

    public String getM_auth() {
        return m_auth;
    }

    public void setM_auth(String m_auth) {
        this.m_auth = m_auth;
    }

    public List<String> getPicids() {
        return picids;
    }

    public void setPicids(List<String> picids) {
        this.picids = picids;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getBwztdivisionid() {
        return bwztdivisionid;
    }

    public void setBwztdivisionid(int bwztdivisionid) {
        this.bwztdivisionid = bwztdivisionid;
    }

    public int getBwztclassid() {
        return bwztclassid;
    }

    public void setBwztclassid(int bwztclassid) {
        this.bwztclassid = bwztclassid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFormhash() {
        return formhash;
    }

    public void setFormhash(String formhash) {
        this.formhash = formhash;
    }

    @Override
    public String toString() {
        return "ConsultBean{" +
                "m_auth='" + m_auth + '\'' +
                ", formhash='" + formhash + '\'' +
                ", subject='" + subject + '\'' +
                ", bwztclassid=" + bwztclassid +
                ", bwztdivisionid=" + bwztdivisionid +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", message='" + message + '\'' +
                ", picids=" + picids +
                '}';
    }

    public ConsultBean(List<String> picids, String message, String age, String sex, int bwztdivisionid, int bwztclassid, String subject, String formhash, String m_auth) {
        this.picids = picids;
        this.message = message;
        this.age = age;
        this.sex = sex;
        this.bwztdivisionid = bwztdivisionid;
        this.bwztclassid = bwztclassid;
        this.subject = subject;
        this.formhash = formhash;
        this.m_auth = m_auth;
    }
    public ConsultBean(){

    }
}
