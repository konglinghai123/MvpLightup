package com.dawnlightning.ucqa.Bean;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/3/31.
 */
public class ConsultClassifyBean {
    private HashMap<String, String> mapid = new HashMap<String, String>();//症状分类
    private HashMap<String, String> mapname = new HashMap<String, String>();//科室分类

    public ConsultClassifyBean(HashMap<String, String> mapid, HashMap<String, String> mapname) {
        this.mapid = mapid;
        this.mapname = mapname;
    }
    public  ConsultClassifyBean(){

    }
    public HashMap<String, String> getMapid() {
        return mapid;
    }

    public void setMapid(HashMap<String, String> mapid) {
        this.mapid = mapid;
    }

    public HashMap<String, String> getMapname() {
        return mapname;
    }

    public void setMapname(HashMap<String, String> mapname) {
        this.mapname = mapname;
    }



    @Override
    public String toString() {
        return super.toString();
    }
}
