package com.dawnlightning.ucqa.Bean;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailedBean {
	private String uid;
	private String usename;
	private String datetime;
	private String subject;
	private String message;
	private List<PicsBean> picsBean;
	private List<CommentBean> commentBean;
	private String avatar_url;
	private String name;
	private String sex;
	private String age;
	private String viewnum;
	private String replynum;
	private int status;
	private int bwztid;

	public int getBwztid() {
		return bwztid;
	}

	public void setBwztid(int bwztid) {
		this.bwztid = bwztid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReplynum() {
		return replynum;
	}

	public void setReplynum(String replynum) {
		this.replynum = replynum;
	}

	public String getViewnum() {
		return viewnum;
	}

	public void setViewnum(String viewnum) {
		this.viewnum = viewnum;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public List<CommentBean> getCommentBean() {
		return commentBean;
	}

	public void setCommentBean(List<CommentBean> commentBean) {
		this.commentBean = commentBean;
	}

	public List<PicsBean> getPicsBean() {
		return picsBean;
	}

	public void setPicsBean(List<PicsBean> picsBean) {
		this.picsBean = picsBean;
	}

	private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;  
    
  
    private static final String ONE_SECOND_AGO = "秒前";  
    private static final String ONE_MINUTE_AGO = "分钟前";  
    private static final String ONE_HOUR_AGO = "小时前";  
    
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getUsename() {
		return usename;
	}
	public void setUsename(String usename) {
		this.usename = usename;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = TimeStamp2Date(datetime);
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return message;
	}
	public void setContent(String message) {
		this.message = message;
	}
	public List<PicsBean> getPics() {
		return picsBean;
	}
	public void setPics(List<PicsBean> list) {
		this.picsBean = list;
	}
	public List<CommentBean> getComment() {
		return commentBean;
	}
	public void setComment(List<CommentBean> list) {
		this.commentBean = list;
	}
	
	public DetailedBean() {
		// TODO 自动生成的构造函数存根
	}
	public String getAvatar_url() {
		return avatar_url;
	}
	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@SuppressLint("SimpleDateFormat")
	public String TimeStamp2Date(String timestampString){  
		  Long timestamp = Long.parseLong(timestampString)*1000;  
		  @SuppressWarnings("unused")
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");  
		  
		  Date date = new Date(timestamp);
		  long delta = new Date().getTime() - date.getTime();
		  if (delta < 1L * ONE_MINUTE) {
	          long seconds = toSeconds(delta);
	          return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
	      }
		  else if (delta < 45L * ONE_MINUTE) {
	          long minutes = toMinutes(delta);
	          return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
	      }
		  else if (delta < 24L * ONE_HOUR) {
	          long hours = toHours(delta);
	          return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
	      } else {

	          return  new SimpleDateFormat("yyyy/MM/dd").format(new Date(timestamp));
	      }  
	     
	     
		}

	public DetailedBean(String uid, int bwztid, int status, String replynum, String viewnum, String age, String usename, String sex, String name, String avatar_url, List<CommentBean> commentBean, List<PicsBean> picsBean, String message, String subject, String datetime) {
		this.uid = uid;
		this.bwztid = bwztid;
		this.status = status;
		this.replynum = replynum;
		this.viewnum = viewnum;
		this.age = age;
		this.usename = usename;
		this.sex = sex;
		this.name = name;
		this.avatar_url = avatar_url;
		this.commentBean = commentBean;
		this.picsBean = picsBean;
		this.message = message;
		this.subject = subject;
		this.datetime = datetime;
	}

	@Override
	public String toString() {
		return "DetailedBean{" +
				"uid='" + uid + '\'' +
				", usename='" + usename + '\'' +
				", datetime='" + datetime + '\'' +
				", subject='" + subject + '\'' +
				", message='" + message + '\'' +
				", picsBean=" + picsBean +
				", commentBean=" + commentBean +
				", avatar_url='" + avatar_url + '\'' +
				", name='" + name + '\'' +
				", sex='" + sex + '\'' +
				", age='" + age + '\'' +
				", viewnum='" + viewnum + '\'' +
				", replynum='" + replynum + '\'' +
				", status=" + status +
				", bwztid=" + bwztid +
				'}';
	}

	private static long toSeconds(long date) {
	    return date / 1000L;  
	}  
	private static long toMinutes(long date) {  
	    return toSeconds(date) / 60L;  
	}  

	private static long toHours(long date) {  
	    return toMinutes(date) / 60L;  
	}  
	
}
