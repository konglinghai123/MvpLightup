package com.dawnlightning.ucqa.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.dawnlightning.ucqa.base.BaseActivity;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.presenter.ConsultListPresenter;
import com.dawnlightning.ucqa.presenter.MainViewPresenter;
import com.dawnlightning.ucqa.presenter.MessagePresenter;
import com.dawnlightning.ucqa.push.ExampleUtil;
import com.dawnlightning.ucqa.tools.ImageLoaderOptions;
import com.dawnlightning.ucqa.util.LvHeightUtil;
import com.dawnlightning.ucqa.view.DragLayout;
import com.dawnlightning.ucqa.viewinterface.IConsultListView;
import com.dawnlightning.ucqa.viewinterface.IMainView;
import com.dawnlightning.ucqa.viewinterface.IMessageView;
import com.dawnlightning.ucqa.viewinterface.IMyconsultListView;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

public class MainActivity extends BaseActivity implements IMainView,IConsultListView,IXListViewLoadMore,IXListViewRefreshListener,IMyconsultListView,IMessageView{
    private DragLayout dl_main;
    private LinearLayout ll_error;
    private ListView lv_menu;
    private TextView tv_error;
    private ImageView iv_error;
    private Button bt_error;
    private TextView tv_title;
    private TextView tv_unreadmsg;
    private TextView tv_username;
    private ImageView iv_menu, iv_icon;
    private XListView mListview=null;
    private ConsultAdapter consultAdapter;
    private LeftMenuAdapter menuadapter;
    private MessageAdapter messageAdapter;
    private MainViewPresenter mainViewPresenter;
    private ConsultListPresenter consultListPresenter;
    private MessagePresenter messagePresenter;
    public static UserBean userBean;
    private List<ConsultMessageBean> consultMessageBeanList;
    private List<ConsultClassifyBean> consultClassifyBeanList;
    private GridView gv_listview_header;
    private ClassifyAdapter classifyAdapter;
    private  View headerView;
    private ProgressBar progressBar;
    private int Page=1;
    private int Bwztclassid=0;
    private static boolean isExit = false;
    private static int MenuPostion=0;
    private MessageReceiver mMessageReceiver;//推送
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        );
        setContentView(R.layout.activity_main);
        initview();
        initdata();
        initevent();
        doloaduserdata(getIntent());
        doloadlist();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
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
    public void initview() {

        dl_main = (DragLayout) findViewById(R.id.dl_main);

        ll_error=(LinearLayout)findViewById(R.id.ll_error);
        tv_error=(TextView)findViewById(R.id.tv_error);
        iv_error=(ImageView)findViewById(R.id.iv_error);
        bt_error=(Button)findViewById(R.id.bt_error);

        lv_menu=(ListView)findViewById(R.id.lv_menu);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        tv_username=(TextView)findViewById(R.id.tv_username);

        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        tv_unreadmsg=(TextView)findViewById(R.id.unread_msg_number);
        tv_title = (TextView) findViewById(R.id.title);

        mListview=(XListView) findViewById(R.id.lv_consult);
        mListview.NotRefreshAtBegin();
        progressBar=(ProgressBar) findViewById(R.id.consult_list_progressbar);
        headerView=getLayoutInflater().inflate(R.layout.listview_header_girdview,null);
        gv_listview_header=(GridView)headerView.findViewById(R.id.gv_classify);


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
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dl_main.open();
            }
        });
    }
        @Override
    public void initdata() {
            mainViewPresenter=new MainViewPresenter(this, MyApp.getApp());
            consultListPresenter =new ConsultListPresenter(this, MyApp.getApp());
            consultAdapter=new ConsultAdapter(getApplication(),consultMessageBeanList);
            messagePresenter=new MessagePresenter(this,MyApp.getApp());
            menuadapter=new LeftMenuAdapter(MainActivity.this);
            lv_menu.setAdapter(menuadapter);
    }
    @Override
    public void selectview(int id) {
        switch (id) {
            case 0:
                dl_main.close();
                backhome();
                break;
            case 1:
                dl_main.close();
                doloadmessagelist(Page,Code.REFRESH,userBean.getM_auth());
                //消息列表
                break;
            case 2:
                dl_main.close();
                doloadmylist(Page, Integer.parseInt(userBean.getUserdata().getUid()), userBean.getM_auth(), Code.ME,Code.REFRESH);
                //我的咨询
                break;
            case 3:
                Intent consultintent=new Intent();
                consultintent.setClass(MainActivity.this, ConsultActivity.class);
                Bundle consultbundle=new Bundle();
                consultbundle.putSerializable("userdata",userBean);
                consultbundle.putSerializable("classifybeanlist", (Serializable) consultClassifyBeanList);
                consultintent.putExtras(consultbundle);
                startActivity(consultintent);
                //发布咨询
                break;
            case 4:
                Intent intent=new Intent();
                intent.setClass(MainActivity.this, SettingActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("userdata",userBean);
                intent.putExtras(bundle);
                startActivity(intent);
                //设置
                break;
            default:
                break;
        }
    }
    /*
    * 加载数据库内容，分类和咨询列表
    * */
    @Override
    public void doloadlist() {
        mainViewPresenter.loadlist();
        mainViewPresenter.loadclassify();
    }
    /*
   * 显示咨询列表
   * */
    @Override
    public void showconsultlist(List<ConsultMessageBean> list) {
        consultAdapter.setList(list);
        mListview.setAdapter(consultAdapter);
        consultAdapter.notifyDataSetChanged();
        stopprgressBar();
        mListview.setPullLoadEnable(this);
        mListview.setPullRefreshEnable(this);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ConsultMessageBean bean = (ConsultMessageBean) parent.getAdapter().getItem(position);
                viewdetail(bean.getUid(), bean.getBwztid(), userBean);
            }
        });
    }
    /*
    * 加载相应的咨询列表
    * */
    @Override
    public void doloadassignlist(int page, int bwztclassid,int identity, int operate) {
        consultListPresenter.loadassignlist(page, bwztclassid, "", Code.ALL, operate);
    }
    @Override
    public void getSuccess(int code,List<ConsultMessageBean> list,int identity,int  operate) {
             if (identity==Code.ALL)//如果加载的主页
            {
            stopprgressBar();//停止进度条
            if (code==Code.LOAD_FULL_SUCCESS)//可能有下一页
            {
                mListview.setPullLoadEnable(MainActivity.this);
            }else if(code==Code.LOAD_NOFULL_SUCCESS)//不可能有下一页
            {
                mListview.disablePullLoad();
            }

            if (operate== Code.REFRESH)//如果是下拉刷新
            {
                mListview.stopRefresh();//停止刷新
                consultAdapter.setList(list);
                //consultAdapter.headinsert(list);//list头插

            }
            else if(operate==Code.CHANGE) //如果是点击Girdview
            {

                mListview.stopRefresh();//停止刷新
                consultAdapter.setList(list);

            }
            else if(operate==Code.LOADMORD)//如果是上拉加载
            {
                mListview.stopLoadMore();//停止更新
                consultAdapter.addList(list);//尾插
            }
            consultAdapter.notifyDataSetChanged();//刷新列表
        }
        else if (identity==Code.ME)//如果加载的是我的咨询
        {
            getmylistSuccess(code, list, operate);
        }

    }

    @Override
    public void getFailure(int code, String msg,int identity ,int operate) {
        if (identity==Code.ALL) {
            stopprgressBar();
            //停止一切上拉下拉刷新
            if (code == Code.SERVER_LOAD_FAILURE)
            {
                mListview.disablePullRefreash();
                mListview.disablePullLoad();
            } else if (code == Code.LOAD_FAILURE) {
                mListview.disablePullRefreash();
                mListview.disablePullLoad();
            }

            if (operate == Code.REFRESH) {
                mListview.stopRefresh();
            }
            else if (operate == Code.CHANGE) {

                if (code == Code.LOAD_NODATA) {
                    consultAdapter.clearList();
                    mListview.disablePullLoad();
                    mListview.disablePullRefreash();
                }
                mListview.stopRefresh();
            } else {
                mListview.stopLoadMore();
            }
            consultAdapter.notifyDataSetChanged();
            if (operate == Code.CHANGE) {
                showerror(code, msg);
            } else {
                showmessage(msg, Toast.LENGTH_SHORT);
            }
        }else if(identity==Code.ME){
            getmylistFailure(code, msg, operate);
        }
    }
    /*
     * 检查版本更新
     * */
    @Override
    public void docheckupdate() {

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
    * 显示分类
    * */

    @Override
    public void showclassifylist(List<ConsultClassifyBean> list) {
        consultClassifyBeanList=list;
        classifyAdapter=new ClassifyAdapter(getApplicationContext(),list);
        gv_listview_header.setAdapter(classifyAdapter);
        LvHeightUtil.setListViewHeightBasedOnChildren(gv_listview_header, 3);
        classifyAdapter.notifyDataSetChanged();
        mListview.addHeaderView(headerView);
        gv_listview_header.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ConsultClassifyBean bean = (ConsultClassifyBean) classifyAdapter.getItem(position);
                girdviewonclick(bean);

            }
        });
        showtitleclassift("全部");

    }
    /*
    * 圆形进度条开始
    * */
    @Override
    public void startprogressBar() {
        progressBar.setVisibility(View.VISIBLE);
        mListview.disablePullLoad();
        mListview.disablePullRefreash();

    }
    /*
* 圆形进度条结束
* */
    @Override
    public void stopprgressBar() {

        progressBar.setVisibility(View.GONE);
        mListview.setPullLoadEnable(this);
        mListview.setPullRefreshEnable(this);

    }
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

    }
    @Override
    public void showerror(int code, String msg) {
        ll_error.setVisibility(View.VISIBLE);//显示错误背景
        tv_error.setText(msg);//显示错误原因
        final ConsultClassifyBean bean=new ConsultClassifyBean();
        bean.setBwztclassarrname(tv_title.getText().toString());
        bean.setBwztclassarrid(String.valueOf(Bwztclassid));
        switch (code){
            case Code.LOAD_NODATA:
                iv_error.setBackgroundResource(R.mipmap.nomessage);
                bt_error.setText("发布咨询");
                bt_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //发布咨询
                    }
                });
                break;
            case Code.SERVER_LOAD_FAILURE:
                bt_error.setText("重新加载");
                iv_error.setBackgroundResource(R.mipmap.server_error);
                bt_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MenuPostion==0){
                            girdviewonclick(bean);
                        }else if (MenuPostion==2){
                            startprogressBar();
                            consultListPresenter.loadassignlist(Page,Integer.parseInt(userBean.getUserdata().getUid()),userBean.getM_auth(), Code.ME, Code.REFRESH);
                        }


                    }
                });
                break;
            case Code.LOAD_FAILURE:
                bt_error.setText("重新加载");
                iv_error.setBackgroundResource(R.mipmap.server_error);
                bt_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MenuPostion==0){
                            girdviewonclick(bean);
                        }else if (MenuPostion==2){
                            startprogressBar();
                            consultListPresenter.loadassignlist(Page,Integer.parseInt(userBean.getUserdata().getUid()),userBean.getM_auth(), Code.ME, Code.REFRESH);
                        }

                    }
                });
                break;
        }

    }

    @Override
    public void onRefresh() {
        if (MenuPostion==0){
            refresh(Code.REFRESH);

        } else if(MenuPostion==1){
            messagelistrefresh(Code.REFRESH);
        }else if (MenuPostion==2){
            mylistrefresh(Code.REFRESH);
        }

    }

    @Override
    public void onLoadMore() {
        if (MenuPostion==0){
            refresh(Code.LOADMORD);

        }else if(MenuPostion==1){
            messagelistrefresh(Code.LOADMORD);
        }
        else if (MenuPostion==2){
            mylistrefresh(Code.LOADMORD);
        }


    }
    @Override
    public void refresh(int operate) {
        if (operate==Code.REFRESH){
            Page=1;
        }else if(operate==Code.LOADMORD){
            Page=Page+1;
        }else if(operate==Code.CHANGE){
            Page=1;
        }
        if(tv_title.getText().toString()=="全部"){
            doloadassignlist(Page, -1, Code.ALL, operate);
        }else{
            doloadassignlist(Page, Bwztclassid, Code.ALL, operate);
        }
    }

    @Override
    public void girdviewonclick(ConsultClassifyBean bean) {
        mListview.setPullLoadEnable(this);
        mListview.setPullRefreshEnable(this);
        ll_error.setVisibility(View.GONE);
        Bwztclassid=bean.getBwztclassarrid();
        showtitleclassift(bean.getBwztclassarrname());
        consultAdapter.clearList();
        consultAdapter.notifyDataSetChanged();
        startprogressBar();
        refresh(Code.CHANGE);
    }

    @Override
    public void doloadmylist(int page, int bwztclassid,String m_auth ,int identity, int operate) {
        if (MenuPostion==1){
            messageAdapter.clearlist();
            messageAdapter.notifyDataSetChanged();
        }
        if (MenuPostion!=2) {
            MenuPostion=2;
            Page=1;
            showtitleclassift("我的咨询");
            ll_error.setVisibility(View.GONE);
            mListview.removeHeaderView(headerView);
            consultAdapter.clearList();
            consultAdapter.notifyDataSetChanged();
            mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ConsultMessageBean bean = (ConsultMessageBean) parent.getAdapter().getItem(position);
                    viewdetail(bean.getUid(), bean.getBwztid(), userBean);
                }
            });
            startprogressBar();
            consultListPresenter.loadassignlist(page, bwztclassid, m_auth, Code.ME, Code.REFRESH);
        }
    }

    @Override
    public void getmylistSuccess(int code, List<ConsultMessageBean> list, int operate) {
        stopprgressBar();
        if (code==Code.LOAD_FULL_SUCCESS){
            mListview.setPullLoadEnable(MainActivity.this);
        }else if(code==Code.LOAD_NOFULL_SUCCESS){
            mListview.disablePullLoad();
        }
        if (operate== Code.REFRESH){
            mListview.stopRefresh();
            consultAdapter.setList(list);

        }else if(operate==Code.LOADMORD){
            mListview.stopLoadMore();
            consultAdapter.addList(list);
        }
        mListview.setAdapter(consultAdapter);
        consultAdapter.notifyDataSetChanged();
    }

    @Override
    public void getmylistFailure(int code, String msg, int operate) {
        stopprgressBar();
        if (code == Code.SERVER_LOAD_FAILURE) {
            mListview.disablePullLoad();
            mListview.disablePullRefreash();
        } else if (code == Code.LOAD_FAILURE) {
            mListview.disablePullLoad();
            mListview.disablePullRefreash();
        }else if(code==Code.LOAD_NODATA){
            mListview.disablePullLoad();
            mListview.disablePullRefreash();
        }
        if (operate == Code.REFRESH) {
            mListview.stopRefresh();
        } else {
            mListview.stopLoadMore();
        }
        showerror(code, msg);
    }

    @Override
    public void mylistrefresh(int operate) {
        if (operate==Code.REFRESH){
            Page = 1;
        } else if (operate == Code.LOADMORD) {
            Page = Page + 1;
        }
        startprogressBar();
        consultListPresenter.loadassignlist(Page, Integer.parseInt(userBean.getUserdata().getUid()), userBean.getM_auth(), Code.ME, operate);

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

    @Override
    public void backhome() {
        if (MenuPostion==1){
            messageAdapter.clearlist();
            messageAdapter.notifyDataSetChanged();
        }
        if (MenuPostion!=0){
            Page=1;
            MenuPostion=0;
            ll_error.setVisibility(View.GONE);
            consultAdapter.clearList();
            consultAdapter.notifyDataSetChanged();

            startprogressBar();
            doloadlist();
        }

    }

    @Override
    public void doloadmessagelist(int page,int operate,String m_auth) {
        if (MenuPostion!=1) {
            MenuPostion=1;
            Page=1;
            showtitleclassift("消息列表");
            ll_error.setVisibility(View.GONE);
            mListview.removeHeaderView(headerView);
            consultAdapter.clearList();
            consultAdapter.notifyDataSetChanged();
            messageAdapter=new MessageAdapter(getcontext());
            startprogressBar();
            messagePresenter.messagelist(page, operate, m_auth);
        }

    }

    @Override
    public void loadmessageSuccess(List<MessageBean> beans, int code,int operate) {
        stopprgressBar();
        if (code==Code.NONEXTPAGE){
            mListview.disablePullLoad();
        }else if(code==Code.HAVENEXTPAGE){
           mListview.setPullLoadEnable(MainActivity.this);
        }
        if (operate== Code.REFRESH){
            messageAdapter.setlist(beans);
            mListview.stopRefresh();

        }else if(operate==Code.LOADMORD){
            messageAdapter.addlist(beans);
            mListview.stopLoadMore();

        }
        mListview.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              MessageBean bean = (MessageBean) parent.getAdapter().getItem(position);
              viewdetail(bean.getLink().substring(bean.getLink().indexOf("=")+1,bean.getLink().indexOf("&")),bean.getBwztid(),userBean);
          }
      });
    }

    @Override
    public void loadmessageFailure(int code, String msg,int operate) {
        stopprgressBar();

        mListview.disablePullRefreash();
        mListview.disablePullLoad();
        if (operate==Code.LOADMORD){
            mListview.stopLoadMore();
        }else if(operate==Code.REFRESH){

            mListview.stopRefresh();
        }
         showerror(code,msg);
    }

    @Override
    public void messagelistrefresh(int operate) {
        if (operate==Code.REFRESH){
            Page=1;
        }else if(operate==Code.LOADMORD){
            Page=Page+1;
        }
        startprogressBar();
        messagePresenter.messagelist(Page, operate, userBean.getM_auth());
    }
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
                Log.e("收到通知", "---------------");
                String uid=intent.getStringExtra("uid");
                String bwztid=intent.getStringExtra("id");
                viewdetail(uid,bwztid,userBean);
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
    public void viewdetail(String  uid,String bwztid, UserBean bean) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("userdata", bean);
        bundle.putString("bwztid", bwztid);
        bundle.putString("uid",uid );
        intent.putExtras(bundle);
        startActivityForResult(intent, Code.DetailForResult);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode==requestCode) {
            switch (resultCode) {
                case Code.DetailForResultDelete:
                    mListview.startRefresh();
                    break;
                case Code.DetailForResultSolve:
                    mListview.startRefresh();
                    break;
                default:
                    break;
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
