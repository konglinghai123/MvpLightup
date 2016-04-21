package com.dawnlightning.ucqa.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dawnlightning.ucqa.Bean.PersonalDataBean;
import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.base.BaseActivity;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.presenter.PersonalPersenter;
import com.dawnlightning.ucqa.tools.ImageLoaderOptions;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.dawnlightning.ucqa.util.SdCardUtil;
import com.dawnlightning.ucqa.view.ProcessImageView;
import com.dawnlightning.ucqa.view.wheelview.NumericWheelAdapter;
import com.dawnlightning.ucqa.view.wheelview.OnWheelScrollListener;
import com.dawnlightning.ucqa.view.wheelview.WheelView;
import com.dawnlightning.ucqa.viewinterface.IPersonalView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/4/20.
 */
public class PersonalDataActivity extends BaseActivity implements IPersonalView {
    private RelativeLayout rl_personaldata_avatar;
    private ImageView iv_personaldata_avatar;
    private EditText et_personaldata_name;
    private RelativeLayout rl_personaldata_sex;
    private RelativeLayout rl_personaldata_age;
    private TextView tv_personaldata_save;
    private TextView tv_personaldata_sex;
    private TextView tv_personaldata_age;
    private ImageView iv_personaldata_back;
    private UserBean userBean;
    private WheelView tv_dialog_year;
    private WheelView tv_dialog_month;
    private WheelView tv_dialog_day;
    private TextView  tv_dialog_age;
    private ProcessImageView piv_avater;//头像上传图片框
    PopupWindow menuWindow;
    private PersonalPersenter personalPersenter;
    private PersonalDataBean bean=new PersonalDataBean();
    String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
    String avaterurl="";
    String username="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNeedBackGesture(true);//设置需要手势监听
        setContentView(R.layout.activity_personaldata);
          rl_personaldata_avatar = (RelativeLayout)findViewById(R.id.rl_personaldata_avatar);
         iv_personaldata_avatar = (ImageView)findViewById(R.id.iv_personal_avatar);
         et_personaldata_name =(EditText)findViewById(R.id.et_personaldata_name);
         rl_personaldata_sex = (RelativeLayout)findViewById(R.id.rl_personaldata_sex);
         rl_personaldata_age = (RelativeLayout)findViewById(R.id.rl_personaldata_age);
          tv_personaldata_save = (TextView)findViewById(R.id.tv_personaldata_save);
         tv_personaldata_sex = (TextView)findViewById(R.id.tv_personaldata_sex);
         tv_personaldata_age = (TextView)findViewById(R.id.tv_personaldata_age);
         iv_personaldata_back = (ImageView) findViewById(R.id.iv_personaldata_back);
        initdata();
        initevent();
    }

    @Override
    public void initview() {

    }

    @Override
    public void initdata() {
        personalPersenter=new PersonalPersenter(this, MyApp.getApp());
        userBean=(UserBean)this.getIntent().getSerializableExtra("userdata");
        bean.setFormhash(userBean.getFormhash());

       avaterurl=userBean.getUserdata().getAvatar_url();
        ImageLoader.getInstance().displayImage(userBean.getUserdata().getAvatar_url(), iv_personaldata_avatar, ImageLoaderOptions.getListOptions());
        if (userBean.getUserdata().getName().length()>0){
            et_personaldata_name.setText(userBean.getUserdata().getName());
            username=userBean.getUserdata().getName();
        }else{

            et_personaldata_name.setText(userBean.getUserdata().getUsername());
            username=userBean.getUserdata().getUsername();
        }
        if (userBean.getUserdata().getSex().length()>0){
            tv_personaldata_sex.setText(userBean.getUserdata().getSex());
        }else{
            tv_personaldata_sex.setText("未设置");
        }
       if (userBean.getUserdata().getAge().length()>0){
           Calendar   mycalendar=Calendar.getInstance();//获取现在时间
           String year=String.valueOf(mycalendar.get(Calendar.YEAR));//获取年份
           String month=String.valueOf(mycalendar.get(Calendar.MONTH));
           String day=String.valueOf(mycalendar.get(Calendar.DAY_OF_WEEK));
           // 用文本框输入年龄
           int age= Integer.parseInt(userBean.getUserdata().getAge());
           int birth=Integer.parseInt(year)-age;
           bean.setBirthyear(String.valueOf(birth));
           bean.setBirthmonth(month);
           bean.setBirthday(day);
           tv_personaldata_age.setText(userBean.getUserdata().getAge()+"岁");
       }else{
           tv_personaldata_age.setText("未设置");
       }
    }

    @Override
    public void initevent() {
        rl_personaldata_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindow(getDataPick());
            }
        });
        rl_personaldata_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindow(picturepike());
            }
        });
        rl_personaldata_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindow(sexselectdialog());
            }
        });
        iv_personaldata_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_personaldata_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = et_personaldata_name.getText().toString();
                bean.setName(username);
                personalPersenter.domodifypersonaldata(bean, userBean.getM_auth());

            }
        });
        iv_personaldata_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PersonalDataActivity.this, DisplayActivity.class);
                intent.putExtra("image",userBean.getUserdata().getAvatar_url());
                startActivity(intent);
            }
        });
    }
    /**
     * 初始化popupWindow
     * @param view
     */
    private void showPopwindow(View view) {
        menuWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        menuWindow.setFocusable(true);
        menuWindow.setBackgroundDrawable(new BitmapDrawable());
        menuWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);
        menuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                menuWindow = null;
            }
        });
    }
    /**
     *
     * @return
     */
    private View getDataPick() {
        Calendar c = Calendar.getInstance();
        int curYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
        int curDate = c.get(Calendar.DATE);
        LayoutInflater inflater =this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_age, null);

        tv_dialog_year = (WheelView) view.findViewById(R.id.wv_dialog_year);
        tv_dialog_year.setAdapter(new NumericWheelAdapter(1930, curYear));
        tv_dialog_year.setLabel("年");
        tv_dialog_year.setCyclic(true);
        tv_dialog_year.addScrollingListener(scrollListener);

        tv_dialog_month = (WheelView) view.findViewById(R.id.wv_dialog_month);
        tv_dialog_month.setAdapter(new NumericWheelAdapter(1, 12));
        tv_dialog_month.setLabel("月");
        tv_dialog_month.setCyclic(true);
        tv_dialog_month.addScrollingListener(scrollListener);

        tv_dialog_day = (WheelView) view.findViewById(R.id.wv_dialog_day);
        initDay(curYear,curMonth);
        tv_dialog_day.setLabel("日");
        tv_dialog_day.setCyclic(true);

        tv_dialog_year.setCurrentItem(curYear - 1930);
        tv_dialog_month.setCurrentItem(curMonth - 1);
        tv_dialog_day.setCurrentItem(curDate - 1);

        tv_dialog_age=(TextView) view.findViewById(R.id.tv_dialog_age);
        tv_dialog_age.setText("0岁");
        Button bt = (Button) view.findViewById(R.id.set);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_personaldata_age.setText(tv_dialog_age.getText().toString());
                bean.setBirthyear(String.valueOf((tv_dialog_year.getCurrentItem() + 1930)));
                bean.setBirthmonth(String.valueOf(tv_dialog_month.getCurrentItem() + 1));
                bean.setBirthday(String.valueOf(tv_dialog_day.getCurrentItem()+1));
                menuWindow.dismiss();
            }
        });

        return view;
    }

    OnWheelScrollListener scrollListener = new OnWheelScrollListener() {

        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            // TODO Auto-generated method stub
            String str = ( tv_dialog_year.getCurrentItem()+1930) + "-"+ ( tv_dialog_month.getCurrentItem()+1)+"-"+( tv_dialog_day.getCurrentItem()+1);
            calculateAge(str);
        }
    };
    private void calculateAge(String str){
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date=new Date();
        java.util.Date mydate;
        try {
            mydate = myFormatter.parse(str);
            int day=(int) ((date.getTime()-mydate.getTime())/(24*60*60*1000) + 1);
            int age=day/365;

            tv_dialog_age.setText(age+"岁");
        } catch (ParseException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

    }
    /**
     *
     * @param year
     * @param month
     * @return
     */
    private int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }
    /**
     */
    private void initDay(int arg1, int arg2) {
        tv_dialog_day.setAdapter(new NumericWheelAdapter(1, getDay(arg1, arg2), "%02d"));
    }
    private View picturepike(){
        LayoutInflater inflater =this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_picturepike, null);
        Button bt_album=(Button)view.findViewById(R.id.bt_personaldata_picture_album);
        bt_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SdCardUtil.checkSdCard() == true) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");// 相片类型
                    startActivityForResult(intent, 2);
                } else {
                   showerror(-1,"SD卡不存在");
                }
                menuWindow.dismiss();
            }
        });
        Button bt_camera=(Button)view.findViewById(R.id.bt_personaldata_picture_camera);
        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName));
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(openCameraIntent, 1);
                menuWindow.dismiss();
            }
        });
        Button bt_cancel= (Button) view.findViewById(R.id.bt_personaldata_picture_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuWindow.dismiss();
            }
        });
        return view;
    }
    private View sexselectdialog(){
        LayoutInflater inflater =this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sex, null);
        final Button bt_man=(Button)view.findViewById(R.id.bt_personaldata_sex_man);
        bt_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_personaldata_sex.setText("男");
                bean.setSex("1");
                menuWindow.dismiss();
            }
        });
        Button bt_woman=(Button)view.findViewById(R.id.bt_personaldata_sex_woman);
        bt_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_personaldata_sex.setText("女");
                bean.setSex("2");
                menuWindow.dismiss();
            }
        });
        Button bt_cancel= (Button) view.findViewById(R.id.bt_personaldata_sex_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuWindow.dismiss();
            }
        });
        return view;
    }

    @Override
    public void showerror(int code, String msg) {
        showmessage(msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showsuccess(String msg) {
        showmessage(msg, Toast.LENGTH_SHORT);
        finish();
    }

    @Override
    public void showavtar(String url) {
        menuWindow.dismiss();
        avaterurl=url;
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiscCache();
        ImageLoader.getInstance().displayImage(url, iv_personaldata_avatar, ImageLoaderOptions.getListOptions());
    }

    @Override
    public void updatepb(int present) {
        piv_avater.setProgress(present);
    }

    public void saveImageToFile(Bitmap bitmap) {
        SdCardUtil.createFileDir(SdCardUtil.FILEDIR + "/" + "/icon" + "/");
        FileOutputStream fos = null;
        String fileName = SdCardUtil.getSdPath() + SdCardUtil.FILEDIR + "/"
                + "/icon" + "/" + "userIcon"
                + String.valueOf(System.currentTimeMillis()) + ".jpg";

        File f = new File(fileName);
        if (f.exists()) {
            f.delete();
        }
        try {
            fos = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                if (bitmap != null) {
                    bitmap.recycle();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                showPopwindow(showpivturesentdialog(fileName));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int respCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, respCode, data);

        if (requestCode == 1 && respCode == RESULT_OK) {

            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                System.out.println("Data");
            }else{
                uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName));
            }

            cropImage(uri, 120, 120, 3);


        } else if (requestCode == 2 && respCode == RESULT_OK) {

            Uri uri = data.getData();
            try {

                cropImage(uri, 120, 120, 3);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if(requestCode==3 && respCode==RESULT_OK){
            Bitmap photo = null;
            Uri photoUri = data.getData();
            if (photoUri != null) {
                photo = BitmapFactory.decodeFile(photoUri.getPath());
            }
            if (photo == null) {
                Bundle extra = data.getExtras();
                if (extra != null) {
                    photo = (Bitmap)extra.get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                }
            }
            if(photo!=null){

                saveImageToFile(photo);
            }

        }
    }
    //截取图片
    public void cropImage(Uri uri, int outputX, int outputY, int requestCode){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, requestCode);
    }
    public View showpivturesentdialog(String filename){
        final  File file=new File(filename);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_avater, null);
        Button bt_sent=(Button)view.findViewById(R.id.bt_personaldata_picture_sent);
        bt_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalPersenter.douploadavater(file, userBean.getM_auth());
            }
        });
        Button bt_cancel=(Button)view.findViewById(R.id.bt_personaldata_picture_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuWindow.dismiss();
            }
        });
        piv_avater=(ProcessImageView)view.findViewById(R.id.piv_personaldata_picture);
        ImageLoader.getInstance().displayImage("file://"+filename,piv_avater,ImageLoaderOptions.getLoadPictureOptions());
        return view;
    }

    @Override
    public void finish() {
        Intent intent=new Intent();
        intent.putExtra("username",username);
        intent.putExtra("avatar",avaterurl);
        setResult(Code.ModifyPersonaldataForResult,intent);
        super.finish();
    }
}
