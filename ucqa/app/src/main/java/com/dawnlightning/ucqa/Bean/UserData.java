package com.dawnlightning.ucqa.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/31.
 */

    public  class UserData implements Serializable {
        String uid;//用户ID
        String credit;//用户积分
        String experience;//用户经验
        String username;//用户姓名
        String name;//用户真实姓名
        String avatar_url;//用户头像
        String sex;//用户性别
        String age;//用户年龄
        int allnotenum;//用户未读消息

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
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
    public UserData(){

    }
    public UserData(String uid, String credit, String experience, String username, String avatar_url, String name, String sex, String age, int allnotenum) {
        this.uid = uid;
        this.credit = credit;
        this.experience = experience;
        this.username = username;
        this.avatar_url = avatar_url;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.allnotenum = allnotenum;
    }

    public int getAllnotenum() {

        return allnotenum;
    }

    public void setAllnotenum(int allnotenum) {
        this.allnotenum = allnotenum;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "uid='" + uid + '\'' +
                ", credit='" + credit + '\'' +
                ", experience='" + experience + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", sex='" + sex + '\'' +

                ", age='" + age + '\'' +
                ", allnotenum='" + allnotenum + '\'' +
                '}';
    }


}

