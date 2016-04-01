package com.dawnlightning.ucqa.Bean;

import android.annotation.SuppressLint;

import com.dawnlightning.ucqa.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 评论对象
 * @author Administrator
 *
 */
public class CommentBean {

	private String author;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String message;
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public CommentBean(){
		
	}
	
	@Override
	public String toString() {
		return "CommentBean [author=" + author + ", name=" + name + ", message="
				+ message + "]";
	}

	public CommentBean(String author, String name, String message, String dateline) {
		super();
		this.author = author;
		this.name = name;
		this.message = message;
		this.dateline = dateline;
	}
	public String dateline;


	public String getDateline() {
		return dateline;
	}
	public void setDateline(String dateline) {
		this.dateline = TimeUtil.TimeStamp2Date(dateline);
	}
}
