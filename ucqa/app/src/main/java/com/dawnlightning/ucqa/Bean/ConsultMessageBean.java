package com.dawnlightning.ucqa.Bean;

import android.annotation.SuppressLint;

import com.dawnlightning.ucqa.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsultMessageBean {

private String bwztid;
private String message;
private String uid;
private String usename;
private String subject;
private String bwztclassid;
private String bwztdivisionid;
private String sex;
private String age;
private String viewnum;
private String replynum;
public String dateline;
private String avatar_url;
public String messagetype;
private String status;
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getUsename() {
	return usename;
}
public void setUsename(String usename) {
	this.usename = usename;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public String getBwztclassid() {
	return bwztclassid;
}
public void setBwztclassid(String bwztclassid) {
	this.bwztclassid = bwztclassid;
}
public String getBwztdivisionid() {
	return bwztdivisionid;
}
public void setBwztdivisionid(String bwztdivisionid) {
	this.bwztdivisionid = bwztdivisionid;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public String getAge() {
	return age;
}
public void setAge(String age) {
	this.age = age;
}
public String getViewnum() {
	return viewnum;
}
public void setViewnum(String viewnum) {
	this.viewnum = viewnum;
}
public String getReplynum() {
	return replynum;
}
public void setReplynum(String replynum) {
	this.replynum = replynum;
}
public String getDateline() {
	return dateline;
}
public void setDateline(String dateline) {
	this.dateline = TimeUtil.TimeStamp2Date(dateline);
}
public String getAvatar_url() {
	return avatar_url;
}
public void setAvatar_url(String pic) {
	this.avatar_url = pic;
}

public ConsultMessageBean(){

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
public ConsultMessageBean(String bwztid, String message, String uid,
						  String usename, String subject, String bwztclassid,
						  String bwztdivisionid, String sex, String age, String viewnum,
						  String replynum, String dateline, String avatar_url,
						  String messagetype, String status) {
	super();
	this.bwztid = bwztid;
	this.message = message;
	this.uid = uid;
	this.usename = usename;
	this.subject = subject;
	this.bwztclassid = bwztclassid;
	this.bwztdivisionid = bwztdivisionid;
	this.sex = sex;
	this.age = age;
	this.viewnum = viewnum;
	this.replynum = replynum;
	this.dateline = dateline;
	this.avatar_url = avatar_url;
	this.messagetype = messagetype;
	this.status = status;
}
@Override
public String toString() {
	return "ConsultMessageBean [bwztid=" + bwztid + ", message=" + message
			+ ", uid=" + uid + ", usename=" + usename + ", subject=" + subject
			+ ", bwztclassid=" + bwztclassid + ", bwztdivisionid="
			+ bwztdivisionid + ", sex=" + sex + ", age=" + age + ", viewnum="
			+ viewnum + ", replynum=" + replynum + ", dateline=" + dateline
			+ ", avatar_url=" + avatar_url + ", messagetype=" + messagetype
			+ ", status=" + status + "]";
}

}
