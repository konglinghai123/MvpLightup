package com.dawnlightning.ucqa.util;

/**
 * Created by Administrator on 2016/4/1.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.Bean.ConsultMessageBean;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class SQLHelper extends SQLiteOpenHelper {
    //数据库名称
    private static final String DATABASE_NAME = "lightup.db";
    // 版本号
    private static final int DATABASE_VERSION = 3;
    // 表名
    public static final String TABLES_CONSULT_NAME = "TB_CONSULT";

    public  static final String TABLES_CLASSIFY_NAME="TB_CLASSIFY";
    // 创建数据库
    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println("creat DB");

    }

    public void onCreate(SQLiteDatabase db) {
        System.out.println("onCreat DB");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLES_CONSULT_NAME + " ("
                + "_ID" + " INTEGER PRIMARY KEY,"
                + "title" + " TEXT,"
                + "content" + " TEXT,"
                + "date" + " TEXT,"
                + "img" + " TEXT,"
                + "replynum" + " TEXT,"
                + "viewnum" + " TEXT,"
                + "bwztid" + " TEXT,"
                + "messagetype" + " TEXT,"
                + "status" + " TEXT,"
                + "uid" + " TEXT"
                + ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLES_CLASSIFY_NAME + " ("
                + "_ID" + " INTEGER PRIMARY KEY,"
                + "bwztclassarrname" + " TEXT,"
                + "bwztclassarrid" + " TEXT,"
                + "bwztdivisionarrname" + " TEXT,"
                + "bwztdivisionarrid" + " TEXT"
                + ");");
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        System.out.println("delete DB");
        db.execSQL("DROP TABLE IF EXISTS TB_CONSULT");
        db.execSQL("DROP TABLE IF EXISTS TB_CLASSIFY");
        onCreate(db);
    }

    public void insertconsult(List<ConsultMessageBean> list){
        SQLiteDatabase db = this.getWritableDatabase();
        for(ConsultMessageBean item : list){
            ContentValues values = new ContentValues();
            values.put("title", item.getSubject());
            values.put("content", item.getMessage());
            values.put("date", item.getDateline());
            values.put("img", item.getAvatar_url());
            values.put("uid", item.getUid());
            values.put("replynum", item.getReplynum());
            values.put("viewnum", item.getViewnum());
            if (item.messagetype==null){
                values.put("messagetype", "全部");
            }else{
                values.put("messagetype", item.messagetype);
            }

            values.put("status", item.getStatus());
            values.put("bwztid", item.getBwztid());
            db.insert(TABLES_CONSULT_NAME, null, values);
        }
        Log.e("SQLHelper","插入成功");
    }
    public void insertclassify(List<ConsultClassifyBean> beans){
        SQLiteDatabase db = this.getWritableDatabase();
        for (ConsultClassifyBean bean:beans){
            HashMap<String,String> nowmapid=bean.getMapid();
            HashMap<String,String> nowmapname=bean.getMapname();
            for (String keyid:nowmapid.keySet()){
                ContentValues values = new ContentValues();
                values.put("bwztclassarrname",keyid);
                values.put("bwztclassarrid",nowmapid.get(keyid));
                for (String keyname:nowmapname.keySet()){
                    values.put("bwztdivisionarrid",nowmapname.get(keyname));
                    values.put("bwztdivisionarrname",keyname);
                }
                db.insert(TABLES_CLASSIFY_NAME, null, values);
            }
        }

    }
    public void deleteclassify(){
        SQLiteDatabase db = this.getWritableDatabase();
        String strsql=String.format("DELETE FROM %s",TABLES_CLASSIFY_NAME);
        db.execSQL(strsql);

    }
    public  void deleteallconsult(){

        SQLiteDatabase db = this.getWritableDatabase();
        String strsql=String.format("DELETE FROM %s",TABLES_CONSULT_NAME);
        db.execSQL(strsql);
        Log.e("SQLHelper", "删除成功");
    }
    public void deleteassignconsult(String newsType){
        SQLiteDatabase db = this.getWritableDatabase();
        String strsql=String.format("DELETE FROM %s WHERE MESSAGETYPE='%s'", TABLES_CONSULT_NAME,newsType);
        db.execSQL(strsql);
    }
    public List<ConsultClassifyBean> queryclassify(String classname){
        List<ConsultClassifyBean> list=new ArrayList<ConsultClassifyBean>();
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL=String.format("select * from %s where bwztdivisionarrname='%s'", TABLES_CLASSIFY_NAME, classname);
        Cursor cursor = db.rawQuery(SQL, null);
        cursor.moveToFirst();
        ConsultClassifyBean bean=new ConsultClassifyBean();
        if (cursor.getCount()>0){
            do {

                HashMap<String,String> mapid=new HashMap<String,String>();
                mapid.put(cursor.getString(2),cursor.getString(1));
                bean.setMapid(mapid);
                HashMap<String,String> mapname=new HashMap<String,String>();
                if (!mapname.keySet().contains(cursor.getString(4))){
                    mapname.put(cursor.getString(4), cursor.getString(3));
                    bean.setMapname(mapname);

                }


            }
            while (cursor.moveToNext());
            list.add(bean);
        }

        return  list;

    }

    public List<ConsultMessageBean> queryconsult(String messagetype){
        List<ConsultMessageBean> list = new ArrayList<ConsultMessageBean>();
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL=String.format("select * from %s where messagetype='%s'",TABLES_CONSULT_NAME,messagetype);
        Cursor cursor = db.rawQuery(SQL, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do{
                ConsultMessageBean item = new ConsultMessageBean();
                item.setSubject(cursor.getString(1));
                item.setMessage(cursor.getString(2));
                item.dateline=cursor.getString(3);
                item.setAvatar_url(cursor.getString(4));
                item.setReplynum(cursor.getString(5));
                item.setViewnum(cursor.getString(6));
                item.setBwztid(cursor.getString(7));
                item.setStatus(cursor.getString(9));
                item.setUid(cursor.getString(10));
                list.add(item);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}

