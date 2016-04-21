package com.dawnlightning.ucqa.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.Bean.MessageBean;
import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.adapter.ClassifyAdapter;
import com.dawnlightning.ucqa.adapter.ConsultAdapter;
import com.dawnlightning.ucqa.adapter.LeftMenuAdapter;
import com.dawnlightning.ucqa.adapter.MessageAdapter;
import com.dawnlightning.ucqa.adapter.MyFragmentPagerAdapter;
import com.dawnlightning.ucqa.base.BaseActivity;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.base.Menu;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.fragment.MainFragment;
import com.dawnlightning.ucqa.fragment.MessageFragment;
import com.dawnlightning.ucqa.fragment.MyConsultFragment;
import com.dawnlightning.ucqa.presenter.ConsultListPresenter;
import com.dawnlightning.ucqa.presenter.MainViewPresenter;
import com.dawnlightning.ucqa.presenter.MessagePresenter;
import com.dawnlightning.ucqa.push.ExampleUtil;
import com.dawnlightning.ucqa.tools.ImageLoaderOptions;
import com.dawnlightning.ucqa.util.LvHeightUtil;
import com.dawnlightning.ucqa.view.DragLayout;
import com.dawnlightning.ucqa.view.MyViewPager;
import com.dawnlightning.ucqa.viewinterface.IConsultListView;
import com.dawnlightning.ucqa.viewinterface.IMainView;
import com.dawnlightning.ucqa.viewinterface.IMessageView;
import com.dawnlightning.ucqa.viewinterface.IMyconsultListView;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class MainActivity extends BaseActivity implements IMainView{
    private DragLayout dl_main;
    private ListView lv_menu;
    public TextView tv_title;
    private TextView tv_unreadmsg;
    private TextView tv_username;
    private ImageView iv_menu, iv_icon;
    private LeftMenuAdapter menuadapter;
    private MainViewPresenter mainViewPresenter;
    public static UserBean userBean;
    public static List<ConsultClassifyBean> consultClassifyBeanList;
    private static boolean isExit = false;
    public MessageReceiver mMessageReceiver;//推送
    public MyViewPager vp_activity;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    public ArrayList<Fragment> fragmentList=new ArrayList<Fragment>();
    private MainFragment mainFragment;
    private MessageFragment messageFragment;
    private MyConsultFragment myConsultFragment;
    private List<Menu> menuList=new ArrayList<Menu>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );
        setContentView(R.layout.activity_main);
        initview();
        initdata();
        initevent();
        doloaduserdata(getIntent());
        docheckupdate();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void initview() {

        dl_main = (DragLayout) findViewById(R.id.dl_main);
        lv_menu=(ListView)findViewById(R.id.lv_menu);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        tv_username=(TextView)findViewById(R.id.tv_username);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        tv_unreadmsg=(TextView)findViewById(R.id.unread_msg_number);
        tv_title = (TextView) findViewById(R.id.title);
        vp_activity=(MyViewPager)findViewById(R.id.mvp_mainactivity);
        vp_activity.setPagingEnabled(false);//不允许滑动


    }
    @Override
    public void initevent() {
        dl_main.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {
                lv_menu.smoothScrollToPosition(new Random().nextInt(30));
                tv_unreadmsg.setVisibility(View.GONE);
            }

            @Override
            public void onClose() {
                //shake();
            }

            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(iv_menu, 1 - percent);
            }
        });
        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                selectview(position);

            }
        });

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl_main.open();
            }
        });
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, PersonalDataActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("userdata",userBean);
                intent.putExtras(bundle);
                startActivityForResult(intent, Code.ModifyPersonaldataForResult);
            }
        });
    }
        @Override
    public void initdata() {
            mainViewPresenter=new MainViewPresenter(this,getcontext());
             mainFragment=new MainFragment();
            messageFragment=new MessageFragment();
            myConsultFragment=new MyConsultFragment();

            menuList.add(new Menu("主        页", 0));
            menuList.add(new Menu("我的消息", 0));
            menuList.add(new Menu("我的咨询",0));
            menuList.add(new Menu("我要咨询",0));
            menuList.add(new Menu("设        置",0));
            menuadapter=new LeftMenuAdapter(MainActivity.this,menuList);
            lv_menu.setAdapter(menuadapter);

            fragmentList.add(mainFragment);
            fragmentList.add(messageFragment);
            fragmentList.add(myConsultFragment);
            myFragmentPagerAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
            vp_activity.setAdapter(myFragmentPagerAdapter);
            vp_activity.setCurrentItem(0);

    }
    @Override
    public void selectview(int id) {
        switch (id) {
            case 0:
                dl_main.close();
                showtitleclassift("全部");
                vp_activity.setCurrentItem(0);
                break;
            case 1:
                ((Menu)menuadapter.getItem(1)).setStatus(0);
                menuadapter.notifyDataSetChanged();
                dl_main.close();
                showtitleclassift("消息列表");
                vp_activity.setCurrentItem(1);
                //消息列表
                break;
            case 2:
                dl_main.close();
                showtitleclassift("我的咨询");
                vp_activity.setCurrentItem(2);
                //我的咨询
                break;
            case 3:
                Intent consultintent=new Intent();
                consultintent.setClass(MainActivity.this, ConsultActivity.class);
                Bundle consultbundle=new Bundle();
                consultbundle.putSerializable("userdata",userBean);
                consultbundle.putSerializable("classifybeanlist", (Serializable) consultClassifyBeanList);
                consultintent.putExtras(consultbundle);
                startActivityForResult(consultintent, Code.ConsultForResult);
                //发布咨询
                break;
            case 4:
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, SettingActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("userdata",userBean);
                intent.putExtras(bundle);
                startActivityForResult(intent, Code.LoginoffForResult);
                //设置
                break;
            default:
                break;
        }
    }



    /*
     * 检查版本更新
     * */
    @Override
    public void docheckupdate() {

        mainViewPresenter.checkupdate();

    }

    @Override
    public void showupdate() {
        ((Menu)menuadapter.getItem(4)).setStatus(1);
        menuadapter.notifyDataSetChanged();
    }

    /*
        * 获取welcome的传值
        * */
    @Override
    public void doloaduserdata(Intent intent) {
        mainViewPresenter.loaduserdata(intent);

    }
    /*
     * 显示分类（中部）
    * */
    @Override
    public void showtitleclassift(String strclassify) {
        tv_title.setText(strclassify);
    }

    /*
    /*
    * 保存用户数据
    * */
    @Override
    public void saveuserdata(UserBean userBean) {
        this.userBean=userBean;
        initjpush(userBean);
        if (userBean.getUserdata().getName()!=""){
            tv_username.setText(userBean.getUserdata().getName());
        }else{
            tv_username.setText(userBean.getUserdata().getUsername());
        }
        ImageLoader.getInstance().displayImage(userBean.getUserdata().getAvatar_url(), iv_icon, ImageLoaderOptions.getListOptions());
        if (userBean.getUserdata().getAllnotenum()>0){
            tv_unreadmsg.setVisibility(View.VISIBLE);
            tv_unreadmsg.setText(String.valueOf(userBean.getUserdata().getAllnotenum()));
        }else{
            tv_unreadmsg.setVisibility(View.GONE);
        }

        if (userBean.getUserdata().getAllnotenum()>0){
            ((Menu)menuadapter.getItem(1)).setStatus(1);
            menuadapter.notifyDataSetChanged();
        }
    }










    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            ExitHandler.sendEmptyMessageDelayed(0, 2000);

        } else {

            finish();
            System.exit(0);

        }
    }
    @SuppressLint("HandlerLeak")
    Handler ExitHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };




    //注册推送
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Code.MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Code.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String uid=intent.getStringExtra("uid");
                String bwztid=intent.getStringExtra("id");
                ((MainFragment)fragmentList.get(0)).viewdetail(uid,bwztid,userBean);
                vp_activity.setCurrentItem(0);
            }
        }
    }


    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.e(Code.TAG,logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.e(Code.TAG,logs);
                    if (ExampleUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(Code.MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {

                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(Code.TAG,logs);

            }


        }

    };
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Code.MSG_SET_ALIAS:
                    Log.d(Code.TAG, "Set alias in handler.");

                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;
                default:

                    Log.i(Code.TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

    @Override
    public void initjpush(UserBean bean) {
        if(getMySharedPreferenceDb().getPush()){
            registerMessageReceiver();
            //调用JPush API设置Alias
            mHandler.sendMessage(mHandler.obtainMessage(Code.MSG_SET_ALIAS,bean.getUserdata().getUid()));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode==requestCode) {
            switch (resultCode) {
                case Code.ConsultForResult:
                    ((MainFragment)fragmentList.get(0)).update();
                    vp_activity.setCurrentItem(0);
                    break;
                case Code.LoginoffForResult:
                    finish();
                    break;
                case Code.ModifyPersonaldataForResult:
                    String username=data.getStringExtra("username");
                    String avater=data.getStringExtra("avatar");
                    userBean.getUserdata().setName(username);
                    ImageLoader.getInstance().displayImage(avater,iv_icon,ImageLoaderOptions.getListOptions());
                    tv_username.setText(username);
                    break;
                default:
                    break;
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
