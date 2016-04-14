package com.dawnlightning.ucqa.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.base.BaseActivity;
import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.db.SharedPreferenceDb;
import com.dawnlightning.ucqa.fragment.LoginFragment;
import com.dawnlightning.ucqa.model.LoginModel;
import com.dawnlightning.ucqa.presenter.ClassifyPresenter;
import com.dawnlightning.ucqa.presenter.ConsultListPresenter;
import com.dawnlightning.ucqa.tools.Blur;
import com.dawnlightning.ucqa.util.SQLHelper;
import com.dawnlightning.ucqa.viewinterface.IConsultListView;
import com.dawnlightning.ucqa.viewinterface.IWelcomeView;

import java.util.List;

/**
 * Created by Administrator on 2016/3/31.
 */
public class WelcomeActivity extends BaseActivity implements IWelcomeView,IConsultListView,LoginModel.loginlistener {


    private RelativeLayout contentview = null;
    private Bitmap bitmap = null;
    private AlphaAnimation start_anima;//开动画始

    @Override
    public void onSuccess(UserBean bean) {

        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("userdata",bean);
        intent.putExtras(bundle);
        startActivity(intent);
        dismissdialog();
        finish();
    }

    @Override
    public void onFailure(int code, String msg) {
        dismissdialog();
        blurbackground();
    }

    private ImageView iv_top = null;
    private ImageView iv_buttom = null;
    private ImageView introduction = null;
    private AlphaAnimation animaTop = null;//上箭头动画
    private AlphaAnimation animabuttom = null;//下箭头动画
    private Boolean IsSlide=false;//是否滑动过
    private Boolean allowSlide=false; //是否允许滑动
    float x1 = 0;  float x2 = 0;  float y1 = 0;  float y2 = 0;
    private ClassifyPresenter classifyPresenter=null;
    private ConsultListPresenter consultListPresenter =null;
    private SQLHelper sqlHelper;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
        initdata();
        initevent();
    }




    @Override
    public void initview() {
        final  View view = View.inflate(this, R.layout.activity_welcome, null);
        setContentView(view);
        contentview = (RelativeLayout) findViewById(R.id.contentview);
        iv_top = (ImageView) findViewById(R.id.up_top);
        iv_buttom = (ImageView) findViewById(R.id.up_buttom);
        introduction=(ImageView) findViewById(R.id.introduction);
        start_anima = new AlphaAnimation(0.3f, 1.0f);
        start_anima.setDuration(3000);
        view.post(new Runnable() {
            @Override
            public void run() {
                view.startAnimation(start_anima);
            }
        });


    }

    @Override
    public void initdata() {
        classifyPresenter=new ClassifyPresenter(this);
        consultListPresenter =new ConsultListPresenter(this,MyApp.getApp());
        sqlHelper=((MyApp)getApplication()).getSQLHelper();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            x1 = event.getX();
            y1 = event.getY();  }
        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            y2 = event.getY();
            if(y1 - y2 > 50)
            {

                TouchUp();
            }
            else if(y2 - y1 > 50)
            {
                //下滑
            }
            else if(x1 - x2 > 50)

            {
                //左滑
            }
            else if(x2 - x1 > 50)
            {
                //右滑

            }
        }  return super.onTouchEvent(event);
    }

    @Override
    public void setconsultclassifybean(List<ConsultClassifyBean> beans) {
        sqlHelper.deleteclassify();
        sqlHelper.insertclassify(beans);
        //List<ConsultClassifyBean> list=sqlHelper.queryclassify("眼科");
    }

    @Override
    public void showerror(String msg) {
        showmessage(msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void AnimationStart() {
        if(checknetwork(getcontext())){
            classifyPresenter.load();
            doloadassignlist(1,-1,Code.ALL,Code.REFRESH);

        }
        else
        {
            showerror("请检查网络连接");
        }
    }

    @Override
    public void AnimationEnd() {
        allowSlide=true;
        introduction.setVisibility(View.VISIBLE);
        iv_top.setVisibility(View.VISIBLE);
        iv_buttom.setVisibility(View.VISIBLE);

        animaTop=new AlphaAnimation(0,1.0f);
        animaTop.setDuration(1000);
        animaTop.setRepeatCount(AlphaAnimation.INFINITE);
        iv_top.setAnimation(animaTop);

        animabuttom=new AlphaAnimation(0.8f,0);
        animabuttom.setDuration(1000);
        animabuttom.setRepeatCount(AlphaAnimation.INFINITE);
        iv_buttom.setAnimation(animabuttom);
    }

    @Override
    public void blurbackground() {


            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.bg, options);
            Bitmap newImg = Blur.fastblur(getcontext(), image, 5);
            Drawable drawable =new BitmapDrawable(newImg);
            contentview.setBackground(drawable);
            showlogin();

    }

    @Override
    public void initevent() {
        start_anima.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                AnimationStart();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AnimationEnd();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void TouchUp() {
        //上滑
        if(!IsSlide&&allowSlide){
            IsSlide=true;
        SharedPreferenceDb sharedPreferenceDb=getMySharedPreferenceDb();
            String phone=sharedPreferenceDb.getPhone();
            String password=sharedPreferenceDb.getPassword();
            Boolean IsAutologin=sharedPreferenceDb.getAutoLogin();
        if (!IsAutologin&&phone!=""&&password!=""){

            blurbackground();
        }

        else{
            if(checknetwork(getcontext())) {

                LoginModel loginModel = new LoginModel();
                loginModel.setUsername(phone);
                loginModel.setPassword(password);
                loginModel.login(loginModel, this);
                this.initdialog("加载中");
            }else{
                showerror("请检查网络连接");
            }
        }
        }
    }



    @Override
    public void showlogin() {
        introduction.setVisibility(View.GONE);
        iv_top.clearAnimation();
        iv_top.setVisibility(View.GONE);
        iv_buttom.clearAnimation();
        iv_buttom.setVisibility(View.GONE);
        LoginFragment login=new LoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.popshow_anim, R.anim.pophidden_anim);
        transaction.add(R.id.farm_content,login).commit();
    }

    @Override
    public void doloadassignlist(int page, int bwztclassid, int identity, int operate) {

            consultListPresenter.loadassignlist(page,bwztclassid,"",identity,operate);

    }

    @Override
    public void getSuccess(int code,List<ConsultMessageBean> list,int indentity,int  operate) {

    }

    @Override
    public void getFailure(int code, String msg,int indentity,int  operate) {

    }
    @Override
    public void refresh(int operate) {

    }
}
