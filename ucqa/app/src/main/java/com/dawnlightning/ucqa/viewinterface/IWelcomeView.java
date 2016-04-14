package com.dawnlightning.ucqa.viewinterface;

import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.db.SharedPreferenceDb;

import java.util.List;

/**
 * Created by Administrator on 2016/3/31.
 */
public interface IWelcomeView {
    public  void setconsultclassifybean(List<ConsultClassifyBean> bean);//获取分类成功后
    public  void showerror(String msg);//显示错误信息
    public  void AnimationStart();//动画加载开始
    public  void AnimationEnd();//动画加载完成
    public  void TouchUp();//上滑
    public  void showlogin();//上滑后显示登陆
    public  void blurbackground();//模糊化背景


}
