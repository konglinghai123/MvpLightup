package com.dawnlightning.ucqa.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.gesture.IBase;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.activity.ConsultActivity;
import com.dawnlightning.ucqa.adapter.OtherAdapter;
import com.dawnlightning.ucqa.view.OtherGridView;
import com.dawnlightning.ucqa.view.wheelview.NumericWheelAdapter;
import com.dawnlightning.ucqa.view.wheelview.OnWheelScrollListener;
import com.dawnlightning.ucqa.view.wheelview.WheelView;

import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.widget.PopupWindow.OnDismissListener;
/**
 * Created by Administrator on 2016/4/13.
 */
public class ConsultPageOneFragment extends Fragment implements OnItemClickListener,IBase {
    private ConsultActivity consultActivity;
    private OtherGridView gv_sex_select;
    private OtherGridView gv_classify_select;
    private OtherAdapter sexadapter;
    private OtherAdapter classifyadapter;
    List<String> sexlist=new ArrayList<String>();
    List<String> classify=new ArrayList<String>();
    private TextView tv_consult_sex;
    private TextView tv_consult_clssify;
    private TextView tv_consult_age;
    private EditText et_consult_name;
    private RelativeLayout rl_consult_age;
    private RelativeLayout rl_consult_name;

    private Button bt_consult_nextpage;
    private WheelView tv_dialog_year;
    private WheelView tv_dialog_month;
    private WheelView tv_dialog_day;
    private TextView  tv_dialog_age;
    PopupWindow menuWindow;
    /** 是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。 */
    boolean isMove = false;
    private Boolean isShowInput=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.fragment_patient_info, container,false);
        gv_sex_select=(OtherGridView)view.findViewById(R.id.gv_sex_select);
        gv_classify_select=(OtherGridView)view.findViewById(R.id.gv_consult_classify);
        tv_consult_sex=(TextView)view.findViewById(R.id.tv_consult_sex);
        tv_consult_clssify=(TextView)view.findViewById(R.id.tv_consult_classifyname);
        tv_consult_age=(TextView)view.findViewById(R.id.tv_consult_age);
        rl_consult_age=(RelativeLayout)view.findViewById(R.id.rl_consult_age);
        et_consult_name=(EditText)view.findViewById(R.id.et_consult_name);
        rl_consult_name=(RelativeLayout)view.findViewById(R.id.rl_consult_name);
        bt_consult_nextpage=(Button)view.findViewById(R.id.bt_consult_nextstep);
        initdata();
        initevent();
        return view;
    }

    @Override
    public void initview() {

    }

    @Override
    public void initdata() {
        sexlist.add("男");
        sexlist.add("女");
        for (ConsultClassifyBean bean:consultActivity.consultClassifyBeanList)
        {
            if (bean.getBwztclassarrname().contains("全部")){
                classify.add("未知分类");
            }else{
                classify.add(bean.getBwztclassarrname());
            }
        }
        sexadapter=new OtherAdapter(getActivity(),sexlist);
        gv_sex_select.setAdapter(sexadapter);
        classifyadapter=new OtherAdapter(getActivity(),classify);
        gv_classify_select.setAdapter(classifyadapter);

        bt_consult_nextpage.setClickable(false);//不可点击
    }

    @Override
    public void initevent() {
        gv_sex_select.setOnItemClickListener(this);
        gv_classify_select.setOnItemClickListener(this);
        rl_consult_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidekeyboard();
                showPopwindow(getDataPick());

            }
        });
        et_consult_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setnextbuttonbackgroundcolor();
            }
        });
        rl_consult_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showketboard();
                setnextbuttonbackgroundcolor();
            }
        });
        bt_consult_nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultActivity.selectpage(1);
                consultActivity.PageOneSetConsultBean(et_consult_name.getText().toString(), tv_consult_age.getText().toString()
                        , tv_consult_sex.getText().toString().replace("岁", ""), tv_consult_clssify.getText().toString());
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.consultActivity=(ConsultActivity)activity;
    }

    /**
     * 初始化popupWindow
     * @param view
     */
    private void showPopwindow(View view) {
        menuWindow = new PopupWindow(view,LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        menuWindow.setFocusable(true);
        menuWindow.setBackgroundDrawable(new BitmapDrawable());
        menuWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);

        menuWindow.setOnDismissListener(new OnDismissListener() {
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
        LayoutInflater inflater =getActivity().getLayoutInflater();
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
                //String str = ( tv_dialog_year.getCurrentItem()+1930) + "-"+ ( tv_dialog_month.getCurrentItem()+1)+"-"+( tv_dialog_day.getCurrentItem()+1);//出身年月
               tv_consult_age.setText(tv_dialog_age.getText().toString());
                setnextbuttonbackgroundcolor();
                hidekeyboard();
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
    /** GRIDVIEW对应的ITEM点击监听接口  */
    @Override
    public void onItemClick(AdapterView<?> parent, final View view, final int position,long id) {
        hidekeyboard();
        //如果点击的时候，之前动画还没结束，那么就让点击事件无效
        if(isMove){
            return;
        }
        switch (parent.getId()) {

            case R.id.gv_sex_select:
                final ImageView movesexImageView = getView(view);
                if (movesexImageView != null){
                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final String channel = ((OtherAdapter) parent.getAdapter()).getItem(position);

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                //获取终点的坐标
                                tv_consult_sex.setText(channel);
                                setnextbuttonbackgroundcolor();
                                hidekeyboard();
                                MoveAnim(movesexImageView, startLocation, endLocation, channel, gv_sex_select);
                                sexadapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;
            case R.id.gv_consult_classify:
                final ImageView moveclassifyImageView = getView(view);
                if (moveclassifyImageView != null){
                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);
                    final int[] startLocation = new int[2];
                    newTextView.getLocationInWindow(startLocation);
                    final String channel = ((OtherAdapter) parent.getAdapter()).getItem(position);

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            try {
                                int[] endLocation = new int[2];
                                //获取终点的坐标
                                tv_consult_clssify.setText(channel);
                                setnextbuttonbackgroundcolor();
                                hidekeyboard();
                                MoveAnim(moveclassifyImageView, startLocation, endLocation, channel, gv_classify_select);
                                classifyadapter.setRemove(position);
                            } catch (Exception localException) {
                            }
                        }
                    }, 50L);
                }
                break;
            default:
                break;
        }
    }
    /**
     * 点击ITEM移动动画
     * @param moveView
     * @param startLocation
     * @param endLocation
     * @param moveChannel
     * @param clickGridView
     */
    private void MoveAnim(View moveView, int[] startLocation,int[] endLocation, final String moveChannel,
                          final GridView clickGridView) {
        int[] initLocation = new int[2];
        //获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        //得到要移动的VIEW,并放入对应的容器中
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
        //创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1],
                endLocation[1]);
        moveAnimation.setDuration(300L);//动画时间
        //动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);//动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                isMove = false;
            }
        });
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        //viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     */
    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getActivity().getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(getActivity());
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    /**
     * 获取点击的Item的对应View，
     * @param view
     * @return
     */
    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView iv = new ImageView(getActivity());
        iv.setImageBitmap(cache);
        return iv;
    }

    private void hidekeyboard() {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_consult_name.getWindowToken(), 0);
    }


    private void showketboard() {
        et_consult_name.requestFocus();
        et_consult_name.setCursorVisible(true);
        InputMethodManager imm = (InputMethodManager) et_consult_name.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        et_consult_name.setFocusable(true);
        isShowInput=true;
    }
    private void setnextbuttonbackgroundcolor(){
        if (tv_consult_age.length()>0&&tv_consult_clssify.length()>0&&tv_consult_sex.length()>0&&et_consult_name.getText().toString().length()>0) {
            bt_consult_nextpage.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
            bt_consult_nextpage.setClickable(true);
        }else{
            bt_consult_nextpage.setBackgroundColor(getActivity().getResources().getColor(R.color.lightgray));
            bt_consult_nextpage.setClickable(false);
            consultActivity.vp_consult_contentview.setPagingEnabled(false);
        }
    }

}
