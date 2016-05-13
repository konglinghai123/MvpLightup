package com.dawnlightning.ucqa.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.base.BaseActivity;
import com.dawnlightning.ucqa.db.SharedPreferenceDb;
import com.dawnlightning.ucqa.fragment.LoginFragment;
import com.dawnlightning.ucqa.model.LoginModel;
import com.dawnlightning.ucqa.presenter.ClassifyPresenter;
import com.dawnlightning.ucqa.tools.Blur;
import com.dawnlightning.ucqa.viewinterface.IWelcomeView;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/3/31.
 */
public class WelcomeActivity extends BaseActivity implements IWelcomeView,LoginModel.loginlistener {


    private RelativeLayout contentview = null;
    private AlphaAnimation start_anima;//开动画始
    private ImageView introduction = null;
    private ClassifyPresenter classifyPresenter=null;
    private LoginModel loginModel;
    private UserBean userBean;
    @Override
    public void onSuccess(UserBean bean) {

        this.userBean=bean;


    }

    @Override
    public void onFailure(int code, String msg) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
        initdata();
        initevent();
    }


    private   void setAndRecycleBackground(View v, int resID) {
        // 获得ImageView当前显示的图片
        Bitmap bitmap1 = null;
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = getResources().openRawResource(resID);
        bitmap1=BitmapFactory.decodeStream(is,null,opt);
        Drawable drawable =new BitmapDrawable(bitmap1);
        v.setBackgroundDrawable(drawable);

    }

    @Override
    public void initview() {
        final  View view = View.inflate(this, R.layout.activity_welcome, null);
        setContentView(view);
        contentview = (RelativeLayout) findViewById(R.id.contentview);
        setAndRecycleBackground(contentview,R.mipmap.welcome_bg);
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
        classifyPresenter=new ClassifyPresenter(getcontext());
        classifyPresenter.load();
        loginModel=new LoginModel();
        SharedPreferenceDb sharedPreferenceDb=getMySharedPreferenceDb();
        String phone=sharedPreferenceDb.getPhone();
        String password=sharedPreferenceDb.getPassword();
        Boolean IsAutologin=sharedPreferenceDb.getAutoLogin();
        if (!IsAutologin&&phone!=""&&password!=""){

        }
        else{
            loginModel.login(phone,password,this);

        }

    }

    @Override
    public void AnimationStart() {

    }

    @Override
    public void AnimationEnd() {
        introduction.setVisibility(View.VISIBLE);
        if (userBean!=null){

            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("userdata", userBean);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();

        }else{
            blurbackground();
        }

    }

    @Override
    public void blurbackground() {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.welcome_bg, options);
            Bitmap newImg = Blur.fastblur(getcontext(), image, 5);
            Drawable drawable =new BitmapDrawable(newImg);
            contentview.setBackgroundDrawable(drawable);
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
    public void showlogin() {
        introduction.setVisibility(View.GONE);
        LoginFragment login=new LoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.popshow_anim, R.anim.pophidden_anim);
        transaction.add(R.id.farm_content,login).commit();
    }

}
