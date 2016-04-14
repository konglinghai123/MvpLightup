package com.dawnlightning.ucqa.activity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dawnlightning.ucqa.Bean.ConsultBean;
import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.adapter.ClassifyAdapter;
import com.dawnlightning.ucqa.adapter.MyFragmentPagerAdapter;
import com.dawnlightning.ucqa.base.BaseActivity;
import com.dawnlightning.ucqa.fragment.ConsultPageOneFragment;
import com.dawnlightning.ucqa.fragment.ConsultPageTwoFragment;
import com.dawnlightning.ucqa.util.LvHeightUtil;
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
    private LinearLayout linearLayout_pageindex;
    private ArrayList<Fragment> fragmentList=new ArrayList<Fragment>();
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
        vp_consult_contentview.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO 自动生成的方法存根

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO 自动生成的方法存根

            }

            @Override
            public void onPageSelected(int arg0) {
                // TODO 自动生成的方法存根
                if (arg0 == 0) {
                    setTabSelected(bt_consult_page1);
                } else if (arg0 == 1) {
                    setTabSelected(bt_consult_page2);
                }

            }

        });
        vp_consult_contentview.setAdapter(myFragmentPagerAdapter);
        vp_consult_contentview.setPagingEnabled(false);
    }

    @Override
    public void initevent() {

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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
    private void setTabSelected(Button btnSelected) {
        Drawable selectedDrawable = this.getResources().getDrawable(R.drawable.consult_page_line);
        int screenWidth = LvHeightUtil.getScreenWidth(this);
        int right = screenWidth / 2;
        selectedDrawable.setBounds(0, 0, right, LvHeightUtil.dip2px(this, 3));
        btnSelected.setSelected(true);
        btnSelected.setCompoundDrawables(null, null, null, selectedDrawable);
        int size = linearLayout_pageindex.getChildCount();
        for (int i = 0; i < size; i++) {
            if (btnSelected.getId() != linearLayout_pageindex.getChildAt(i).getId()) {
                linearLayout_pageindex.getChildAt(i).setSelected(false);
                ((Button)linearLayout_pageindex.getChildAt(i)).setCompoundDrawables(null, null, null, null);
            }
        }
    }
    public void selectpage(int pageid){
        if (pageid==1){
            vp_consult_contentview.setCurrentItem(pageid);
            vp_consult_contentview.setPagingEnabled(true);
        }

    }

    public void PageOneSetConsultBean(String name,String age,String sex,String classifyname){
        consultBean.setAge(age);
        consultBean.setSex(sex);
        consultBean.setBwztclassid(findclassifyid(classifyname));
        consultBean.setBwztdivisionid(1);//以后可能有更多的科室
    }
    public void setUserBean(Intent intent){
       userBean=(UserBean)intent.getSerializableExtra("userdata");
       consultClassifyBeanList=(List<ConsultClassifyBean>)intent.getSerializableExtra("classifybeanlist");
        consultBean.setFormhash(userBean.getFormhash());
        consultBean.setM_auth(userBean.getM_auth());
    }
    private int findclassifyid(String classifyname){
        for (ConsultClassifyBean bean:consultClassifyBeanList){
            if (bean.getBwztclassarrname().equals(classifyname)){
                return bean.getBwztclassarrid();
            }
        }
        return 1;
    }


}
