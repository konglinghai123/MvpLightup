package com.dawnlightning.ucqa.Bean;

import java.io.File;

/**
 * Created by Administrator on 2016/4/14.
 */
public class UploadPicsBean {
    private String uid;
    private File picture;
    private String picturetitle;
    private String m_auth;
    private int pictureid;
    private int present;

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public int getPictureid() {
        return pictureid;
    }

    public void setPictureid(int pictureid) {
        this.pictureid = pictureid;
    }

    public String getM_auth() {
        return m_auth;
    }

    public void setM_auth(String m_auth) {
        this.m_auth = m_auth;
    }



    public UploadPicsBean(){

    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getPicturetitle() {
        return picturetitle;
    }

    public void setPicturetitle(String picturetitle) {
        this.picturetitle = picturetitle;
    }

    public File getPicture() {
        return picture;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    public UploadPicsBean(File picture, String uid, String picturetitle, String m_auth, int pictureid, int present) {
        this.picture = picture;
        this.uid = uid;
        this.picturetitle = picturetitle;
        this.m_auth = m_auth;
        this.pictureid = pictureid;
        this.present = present;
    }

    @Override
    public String toString() {
        return "UploadPicsBean{" +
                "uid='" + uid + '\'' +
                ", picture=" + picture +
                ", picturetitle='" + picturetitle + '\'' +
                ", m_auth='" + m_auth + '\'' +
                ", pictureid=" + pictureid +
                ", present=" + present +
                '}';
    }
}
