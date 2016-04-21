package com.dawnlightning.ucqa.Bean;

/**
 * Created by Administrator on 2016/4/20.
 */
public class PersonalDataBean {
    private String formhash;
    private String name;
    private String sex;
    private String birthyear;
    private String birthmonth;
    private String birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormhash() {
        return formhash;
    }

    public void setFormhash(String formhash) {
        this.formhash = formhash;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(String birthyear) {
        this.birthyear = birthyear;
    }

    public String getBirthmonth() {
        return birthmonth;
    }

    public void setBirthmonth(String birthmonth) {
        this.birthmonth = birthmonth;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public PersonalDataBean(){

    }

    public PersonalDataBean(String formhash, String name, String sex, String birthyear, String birthmonth, String birthday) {
        this.formhash = formhash;
        this.name = name;
        this.sex = sex;
        this.birthyear = birthyear;
        this.birthmonth = birthmonth;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "PersonalDataBean{" +
                "formhash='" + formhash + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", birthyear='" + birthyear + '\'' +
                ", birthmonth='" + birthmonth + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
