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

import java.util.ArrayList;
import java.util.List;

import android.util.Log;


public class SQLHelper extends SQLiteOpenHelper {
    //数据库名称
    private static final String DATABASE_NAME = "lightup.db";
    // 版本号
    private static final int DATABASE_VERSION =5;
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
                + "uid" + " TEXT,"
                + "sex" + " TEXT,"
                + "age" + " TEXT"
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

    public int insertconsult(List<ConsultMessageBean> list){
        int insertcount=0;
        List<ConsultMessageBean> beans=queryallconsult();
        SQLiteDatabase db = this.getWritableDatabase();
        if (beans.size()>0){
            for (ConsultMessageBean item : list){
                for(int i=0;i<beans.size();i++){
                    if (item.getBwztid().equals(beans.get(i).getBwztid())){
                        break;
                    }else if (i==beans.size()-1){
                        ContentValues values = new ContentValues();
                        values.put("title", item.getSubject());
                        values.put("content", item.getMessage());
                        values.put("date", item.getDateline());
                        values.put("img", item.getAvatar_url());
                        values.put("uid", item.getUid());
                        values.put("replynum", item.getReplynum());
                        values.put("viewnum", item.getViewnum());
                        if (item.messagetype == null) {
                            values.put("messagetype", "全部");
                        } else {
                            values.put("messagetype", item.messagetype);
                        }

                        values.put("status", item.getStatus());
                        values.put("bwztid", item.getBwztid());
                        values.put("sex",item.getSex());
                        values.put("age",item.getAge());
                        db.insert(TABLES_CONSULT_NAME, null, values);
                        insertcount++;
                    }else{
                        break;
                    }

                }
            }

       }else{
            for(ConsultMessageBean item : list){
            ContentValues values = new ContentValues();
                    values.put("title", item.getSubject());
                    values.put("content", item.getMessage());
                    values.put("date", item.getDateline());
                    values.put("img", item.getAvatar_url());
                    values.put("uid", item.getUid());
                    values.put("replynum", item.getReplynum());
                    values.put("viewnum", item.getViewnum());
                    if (item.messagetype == null) {
                        values.put("messagetype", "全部");
                    } else {
                        values.put("messagetype", item.messagetype);
                    }

                    values.put("status", item.getStatus());
                    values.put("bwztid", item.getBwztid());
                     values.put("sex",item.getSex());
                     values.put("age",item.getAge());
                    db.insert(TABLES_CONSULT_NAME, null, values);
                    insertcount++;
            }
        }

//        for(ConsultMessageBean item : list){
//            if (beans.size()>0){
//                if (!beans.contains(item)) {
//                    ContentValues values = new ContentValues();
//                    values.put("title", item.getSubject());
//                    values.put("content", item.getMessage());
//                    values.put("date", item.getDateline());
//                    values.put("img", item.getAvatar_url());
//                    values.put("uid", item.getUid());
//                    values.put("replynum", item.getReplynum());
//                    values.put("viewnum", item.getViewnum());
//                    if (item.messagetype == null) {
//                        values.put("messagetype", "全部");
//                    } else {
//                        values.put("messagetype", item.messagetype);
//                    }
//
//                    values.put("status", item.getStatus());
//                    values.put("bwztid", item.getBwztid());
//                    db.insert(TABLES_CONSULT_NAME, null, values);
//                    insertcount++;
//                }
//            }else{
//                ContentValues values = new ContentValues();
//                values.put("title", item.getSubject());
//                values.put("content", item.getMessage());
//                values.put("date", item.getDateline());
//                values.put("img", item.getAvatar_url());
//                values.put("uid", item.getUid());
//                values.put("replynum", item.getReplynum());
//                values.put("viewnum", item.getViewnum());
//                if (item.messagetype == null) {
//                    values.put("messagetype", "全部");
//                } else {
//                    values.put("messagetype", item.messagetype);
//                }
//                values.put("status", item.getStatus());
//                values.put("bwztid", item.getBwztid());
//                db.insert(TABLES_CONSULT_NAME, null, values);
//                insertcount++;
//            }
//        }
        return insertcount;
    }
    public void insertclassify(List<ConsultClassifyBean> beans){
        SQLiteDatabase db = this.getWritableDatabase();
            for (ConsultClassifyBean bean:beans) {
                ContentValues values = new ContentValues();
                values.put("bwztclassarrname", bean.getBwztclassarrname());
                values.put("bwztclassarrid", bean.getBwztclassarrid());
                values.put("bwztdivisionarrid", bean.getBwztdivisionarrid());
                values.put("bwztdivisionarrname", bean.getBwztdivisionarrname());
                db.insert(TABLES_CLASSIFY_NAME, null, values);
            }




    }
    public void deleteclassify(){
        SQLiteDatabase db = this.getWritableDatabase();
        String strsql=String.format("DELETE FROM %s",TABLES_CLASSIFY_NAME);
        db.execSQL(strsql);


    }
    public  List<ConsultMessageBean> queryallconsult(){
        List<ConsultMessageBean> list = new ArrayList<ConsultMessageBean>();
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL=String.format("select * from %s ",TABLES_CONSULT_NAME);
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
                item.setAge(cursor.getString(12));
                item.setSex(cursor.getString(11));
                list.add(item);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return  list;
    }
    public  void deleteallconsult(){

        SQLiteDatabase db = this.getWritableDatabase();
        String strsql=String.format("DELETE FROM %s",TABLES_CONSULT_NAME);
        db.execSQL(strsql);

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

        if (cursor.getCount()>0){
            do {

                ConsultClassifyBean item = new  ConsultClassifyBean();
                item.setBwztdivisionarrname(cursor.getString(3));
                item.setBwztclassarrname(cursor.getString(1));
                item.setBwztclassarrid(cursor.getString(2));
                item.setBwztdivisionarrid(cursor.getString(4));
                list.add(item);
            }
            while (cursor.moveToNext());

        }
        cursor.close();
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
                item.setAge(cursor.getString(12));
                item.setSex(cursor.getString(11));
                list.add(item);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}

