package com.dawnlightning.ucqa.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dawnlightning.ucqa.Bean.ConsultBean;
import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.adapter.MyFragmentPagerAdapter;
import com.dawnlightning.ucqa.base.BaseActivity;
import com.dawnlightning.ucqa.fragment.ConsultPageOneFragment;
import com.dawnlightning.ucqa.fragment.ConsultPageTwoFragment;
import com.dawnlightning.ucqa.view.MyViewPager;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class ConsultActivity extends BaseActivity{
    private ImageView iv_consult_back;
    private Button bt_consult_page1;
    private Button bt_consult_page2;
    public MyViewPager vp_consult_contentview;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private ArrayList<Fragment> fragmentList=new ArrayList<Fragment>();
    private LinearLayout linearLayout_pageindex;

    private ConsultPageOneFragment consultPageOneFragment;
    private ConsultPageTwoFragment consultPageTwoFragment;
    public static ConsultBean consultBean=new ConsultBean();
    public UserBean userBean;
    public List<ConsultClassifyBean> consultClassifyBeanList;
    @Override
    public void initview() {
        iv_consult_back=(ImageView)findViewById(R.id.iv_consult_back);
        bt_consult_page1=(Button)findViewById(R.id.bt_consult_page1);
        bt_consult_page2=(Button)findViewById(R.id.bt_consult_page2);
        vp_consult_contentview=(MyViewPager)findViewById(R.id.vp_consult_pageconview);
        linearLayout_pageindex=(LinearLayout)findViewById(R.id.ll_consult_pageindex);
    }

    @Override
    public void initdata() {
        consultPageOneFragment=new ConsultPageOneFragment();
        consultPageTwoFragment =new ConsultPageTwoFragment();
        fragmentList.add(consultPageOneFragment);
        fragmentList.add(consultPageTwoFragment);
        myFragmentPagerAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        vp_consult_contentview.setCurrentItem(0);
        setTabSelected(bt_consult_page1);
        vp_consult_contentview.setAdapter(myFragmentPagerAdapter);
        vp_consult_contentview.setPagingEnabled(false);
    }

    @Override
    public void initevent() {
        vp_consult_contentview.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {


            }

            @Override
            public void onPageSelected(int arg0) {
                if (arg0 == 0) {
                    setTabSelected(bt_consult_page1);
                } else if (arg0 == 1) {
                    setTabSelected(bt_consult_page2);
                }

            }

        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        setUserBean(getIntent());
        initview();
        initdata();
        initevent();
    }
    //页面切换时的背景切换
    private void setTabSelected(Button btnSelected) {
        if (btnSelected.getId()==R.id.bt_consult_page1){
            bt_consult_page1.setBackground(getResources().getDrawable(R.mipmap.ic_consult_left_press));
            bt_consult_page2.setBackground(getResources().getDrawable(R.mipmap.ic_consult_right_normal));

        }else{
            bt_consult_page1.setBackground(getResources().getDrawable(R.mipmap.ic_consult_left_normal));
            bt_consult_page2.setBackground(getResources().getDrawable(R.mipmap.ic_consult_right_press));

        }

    }
    //只能从第二页滑动回到第一页，不能反过来
    public void selectpage(int pageid){
        if (pageid==1){
            vp_consult_contentview.setCurrentItem(pageid);
            vp_consult_contentview.setPagingEnabled(true);
        }

    }
    //存储第一页的患者信息
    public void PageOneSetConsultBean(String name,String age,String sex,String classifyname){
        consultBean.setAge(age);
        consultBean.setSex(sex);
        consultBean.setBwztclassid(findclassifyid(classifyname));
        consultBean.setBwztdivisionid(1);//以后可能有更多的科室
    }
    //保存主界面的用户信息
    public void setUserBean(Intent intent){
       userBean=(UserBean)intent.getSerializableExtra("userdata");
       consultClassifyBeanList=(List<ConsultClassifyBean>)intent.getSerializableExtra("classifybeanlist");
        consultBean.setFormhash(userBean.getFormhash());
        consultBean.setM_auth(userBean.getM_auth());
    }
    //根据科室名称寻找对应的ID
    private int findclassifyid(String classifyname){
        for (ConsultClassifyBean bean:consultClassifyBeanList){
            if (bean.getBwztclassarrname().equals(classifyname)){
                return bean.getBwztclassarrid();
            }
        }
        return 1;
    }


}
