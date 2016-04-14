package com.dawnlightning.ucqa.Bean;

import android.annotation.SuppressLint;

import com.dawnlightning.ucqa.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 评论对象
 * @author Administrator
 *
 */
public class CommentBean {
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String cid;
	private String uid;
	private String id;
	private String author;
	private String name;
	private String message;
	public String dateline;
	private String avatar_url;
	private List<String> replylist;

	public List<String> getReplylist() {
		return replylist;
	}

	public void setReplylist(List<String> replylist) {
		this.replylist = replylist;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

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
	public CommentBean(){

	}

	@Override
	public String toString() {
		return "CommentBean{" +
				"cid='" + cid + '\'' +
				", uid='" + uid + '\'' +
				", id='" + id + '\'' +
				", author='" + author + '\'' +
				", name='" + name + '\'' +
				", message='" + message + '\'' +
				", dateline='" + dateline + '\'' +
				", avatar_url='" + avatar_url + '\'' +
				", replylist=" + replylist +
				'}';
	}

	public CommentBean(String cid, List<String> replylist, String avatar_url, String dateline, String message, String name, String author, String id, String uid) {
		this.cid = cid;
		this.replylist = replylist;
		this.avatar_url = avatar_url;
		this.dateline = dateline;
		this.message = message;
		this.name = name;
		this.author = author;
		this.id = id;
		this.uid = uid;
	}
}
