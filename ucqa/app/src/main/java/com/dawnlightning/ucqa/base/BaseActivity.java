package com.dawnlightning.ucqa.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import android.view.MotionEvent;

import com.dawnlightning.ucqa.Listener.IBase;
import com.dawnlightning.ucqa.db.SharedPreferenceDb;
import com.dawnlightning.ucqa.dialog.LoadingDialog;
import com.dawnlightning.ucqa.util.AppUtils;
import com.dawnlightning.ucqa.Listener.BackGestureListener;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/3/30.
 */
public abstract  class BaseActivity extends FragmentActivity implements IBase{
    private Context context;
    private  SharedPreferenceDb MySharedPreferenceDb;
    private LoadingDialog dialog=null;
    public static boolean isForeground = false;//推送判断是否在主界面
    protected int activityCloseEnterAnimation;

    protected int activityCloseExitAnimation;
    /** 手势监听 */
    GestureDetector mGestureDetector;
    /** 是否需要监听手势关闭功能 */
    private boolean mNeedBackGesture = false;

    public  void showmessage(String msg,int time){
        Toast.makeText(context,msg,time).show();
    }
    public void initdialog(String msg,DialogInterface.OnDismissListener onDismissListener){
        if (dialog == null) {
            dialog = new LoadingDialog(context,msg,onDismissListener);
            dialog.show();
        } else {
           dialog.show();
        }
    }
    public  void dismissdialog(){
        dialog.dismiss();
    }
    public  void initsharepreference(Context context){
        MySharedPreferenceDb=new SharedPreferenceDb(context);
    }
    public  SharedPreferenceDb getMySharedPreferenceDb(){
        return  this.MySharedPreferenceDb;
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
    protected void onResume() {
        super.onResume();
        isForeground = true;
        JPushInterface.onResume(context);

    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;
        JPushInterface.onPause(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});

        int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);

        activityStyle.recycle();

        activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});

        activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);

        activityCloseExitAnimation = activityStyle.getResourceId(1, 0);

        activityStyle.recycle();
        context=this;
        initsharepreference(context);
        initGestureDetector();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    private void initGestureDetector() {
        if (mGestureDetector == null) {
            mGestureDetector = new GestureDetector(getApplicationContext(),
                    new BackGestureListener(this));
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if(mNeedBackGesture){
            return mGestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    /*
     * 设置是否进行手势监听
     */
    public void setNeedBackGesture(boolean mNeedBackGesture){
        this.mNeedBackGesture = mNeedBackGesture;
    }
    public Boolean checknetwork(Context context){
        return  AppUtils.checkNetwork(context);
    }
    public Context getcontext() {
        return this.context;
    }
    @Override
    public void finish() {

        super.finish();

        overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);

    }
}
