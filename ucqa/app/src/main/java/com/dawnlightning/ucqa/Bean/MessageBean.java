package com.dawnlightning.ucqa.Bean;

import com.dawnlightning.ucqa.util.TimeUtil;

/**
 * Created by Administrator on 2016/4/10.
 */
public class MessageBean {
    private String note;
    private String author;
    private String name;
    private String link;
    private String message;
    private String isnew;
    private String uid;
    private String bwztid;
    private String dateline;
    private String avatar_url;
    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = TimeUtil.TimeStamp2Date(dateline);
    }

    public MessageBean(){

    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBwztid() {
        return bwztid;
    }

    public void setBwztid(String bwztid) {
        this.bwztid = bwztid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIsnew() {
        return isnew;
    }

    public void setIsnew(String isnew) {
        this.isnew = isnew;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "note='" + note + '\'' +
                ", author='" + author + '\'' +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", message='" + message + '\'' +
                ", isnew='" + isnew + '\'' +
                ", uid='" + uid + '\'' +
                ", bwztid='" + bwztid + '\'' +
                ", dateline='" + dateline + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                '}';
    }

    public MessageBean(String note, String avatar_url, String dateline, String bwztid, String uid, String isnew, String message, String link, String name, String author) {
        this.note = note;
        this.avatar_url = avatar_url;
        this.dateline = dateline;
        this.bwztid = bwztid;
        this.uid = uid;
        this.isnew = isnew;
        this.message = message;
        this.link = link;
        this.name = name;
        this.author = author;
    }
}
