package com.dawnlightning.ucqa.activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Handler.Callback;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.dawnlightning.ucqa.Bean.CommentBean;
import com.dawnlightning.ucqa.Bean.DetailedBean;
import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.adapter.CommentAdapter;
import com.dawnlightning.ucqa.adapter.DetailPicsAdapter;
import com.dawnlightning.ucqa.base.BaseActivity;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.dialog.ActionItem;
import com.dawnlightning.ucqa.dialog.TitlePopup;
import com.dawnlightning.ucqa.presenter.DetailedPresenter;
import com.dawnlightning.ucqa.share.ShareAdapter;
import com.dawnlightning.ucqa.share.ShareModel;
import com.dawnlightning.ucqa.share.ShareTool;
import com.dawnlightning.ucqa.tools.ImageLoaderOptions;
import com.dawnlightning.ucqa.util.TimeUtil;
import com.dawnlightning.ucqa.view.ExpandListView;
import com.dawnlightning.ucqa.view.OtherGridView;
import com.dawnlightning.ucqa.viewinterface.IDetailView;
import com.mob.tools.utils.UIHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

import android.view.ViewGroup.LayoutParams;
/**
 * Created by Administrator on 2016/4/9.
 */
public class DetailActivity extends BaseActivity implements IDetailView,CommentAdapter.IReply,PlatformActionListener,Callback {
    private ImageView iv_back;
    private ImageView iv_action;
    private ImageView iv_icon;
    private TextView tv_subject;
    private TextView tv_userdata;
    private ImageView iv_status;
    private TextView tv_message;
    private OtherGridView gv_picslist;
    private TextView tv_numviewpeople;
    private TextView tv_numcommentpeople;
    private TextView tv_time;
    private TextView tv_footview;
    private LinearLayout linearLayout;//时间回复数浏览人数
    private LinearLayout setcommentlinearLayout;
    private ExpandListView xListView;
    private View footerview;
    private EditText ed_setcomment;
    private ProgressBar progressBar;
    private ProgressBar loadprgressBar;
    private UserBean userBean;
    private RelativeLayout errorRelativeLayout;
    private TextView tv_error;
    private ImageView iv_error;
    private Button bt_error;
    private Button bt_sentcomment;
    private RelativeLayout commentRelativeLayout;
    private RelativeLayout contentRelativeLayout;
    private LinearLayout detailLinearLayout;
    private DetailedBean detailedBean;
    private DetailedPresenter detailedPresenter;
    private DetailPicsAdapter detailPicsAdapter;
    private CommentAdapter commentAdapter;
    private CommentBean commentBean;//要回复的评论
    private int ReplyPostion=0;//要回复的评论位置
    private static String tempcommenttext="";
    private static int loadcomment=Code.INITCOMMENT;//默认是初始化评论列表
    private static int operate=Code.COMMENT;//默认是评论
    private static int commentcount=0;
    private static int commentpage=1;//默认是评论列表第一页
    private boolean IsLoadMore=false;
    private TitlePopup titlePopup;
    private PopupWindow reportdialog;
    private  ShareTool  Share;

    @Override
    public void initview() {
        setContentView(R.layout.activity_detail);
        setNeedBackGesture(true);//设置需要手势监听
        iv_back=(ImageView)findViewById(R.id.iv_detail_back);
        iv_action=(ImageView)findViewById(R.id.iv_detail_actions);
        iv_icon=(ImageView)findViewById(R.id.iv_detail_icon);
        loadprgressBar=(ProgressBar)findViewById(R.id.pb_detail_load);
        iv_status=(ImageView)findViewById(R.id.iv_detail_consult_status);
        tv_subject=(TextView)findViewById(R.id.tv_detail_subject);
        tv_message=(TextView)findViewById(R.id.tv_detail_message);
        tv_userdata=(TextView)findViewById(R.id.tv_detail_userdata);
        tv_numviewpeople=(TextView)findViewById(R.id.tv_detail_numview);
        tv_numcommentpeople=(TextView)findViewById(R.id.tv_detail_numreply);
        tv_time=(TextView)findViewById(R.id.tv_detail_time);
        tv_error=(TextView)findViewById(R.id.tv_detail_error);
        iv_error=(ImageView)findViewById(R.id.iv_detail_error);
        bt_error=(Button)findViewById(R.id.bt_detail_error);
        bt_sentcomment=(Button)findViewById(R.id.bt_sentcomment);
        gv_picslist=(OtherGridView)findViewById(R.id.gv_detail_picslist);
        xListView=(ExpandListView)findViewById(R.id.lv_comment_list);
        footerview=getLayoutInflater().inflate(R.layout.comment_xlistview_footer,null);
        progressBar=(ProgressBar)footerview.findViewById(R.id.comment_xlistview_footer_progressbar);
        tv_footview=(TextView)footerview.findViewById(R.id.comment_xlistview_footer_hint_textview);
        commentRelativeLayout=(RelativeLayout)findViewById(R.id.rl_detail_comment);
        contentRelativeLayout=(RelativeLayout)findViewById(R.id.rl_detail_content);
        errorRelativeLayout=(RelativeLayout)findViewById(R.id.rl_detail_error);
        linearLayout=(LinearLayout)findViewById(R.id.ll_detail_sign);
        detailLinearLayout=(LinearLayout)findViewById(R.id.ll_detail_parent);
        setcommentlinearLayout=(LinearLayout)findViewById(R.id.ll_detail_setcomment);
        ed_setcomment=(EditText)findViewById(R.id.ed_setcomment);

    }

    @Override
    public void initdata() {
        startloadpb();
        detailedPresenter=new DetailedPresenter(this, MyApp.getApp());

    }

    @Override
    public void initevent() {
        footerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IsLoadMore) {
                    progressBar.setVisibility(View.VISIBLE);
                    tv_footview.setVisibility(View.GONE);
                    commentpage = commentpage + 1;
                    doloadmorecomment(Integer.parseInt(userBean.getUserdata().getUid()),detailedBean.getBwztid(), commentpage, userBean.getM_auth());
                    IsLoadMore = true;
                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titlePopup!=null){
                    titlePopup.show(v);
                }else{
                    initdialoglistview();
                }
            }
        });
        ed_setcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showketboard();
            }
        });
        bt_sentcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempcommenttext = ed_setcomment.getText().toString().trim();
                ed_setcomment.setText("");
                if (operate == Code.COMMENT) {
                    dosetcomment(detailedBean.getBwztid(), tempcommenttext, userBean.getFormhash(), userBean.getM_auth());
                } else if (operate == Code.REPLY) {
                    detailedPresenter.setreply(commentBean, username(), ReplyPostion, Integer.parseInt(commentBean.getId()), Integer.parseInt(commentBean.getCid()), tempcommenttext, userBean.getFormhash(), userBean.getM_auth());
                }

            }
        });


    }


    @Override
    public void getuserdate(Intent intent) {
        detailedPresenter.loaduserdata(intent);
    }

    @Override
    public void setuserdata(UserBean userBean,int bwztid,int uid) {
        this.userBean=userBean;

        doloadconsultdetailed(uid, bwztid, userBean.getM_auth());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
        initdata();
        initevent();
        getuserdate(getIntent());
    }

    @Override
    public void doloadconsultdetailed(int uid, int classid, String m_auth) {
        loadcomment=Code.INITCOMMENT;
        detailedPresenter.loadconsultdetailed(uid, classid, m_auth);
    }

    @Override
    public void doloadmorecomment(int uid, int classid, int page, String m_auth) {
        loadcomment=Code.LOADMORECOMMENT;
        detailedPresenter.loadmorecomment(uid, classid, page, userBean.getM_auth());
    }
    @Override
    public void dosetcomment(int classid, String message, String formhash, String m_auth) {
        detailedPresenter.setcomment(classid, message, formhash, m_auth);
    }

    @Override
    public void showdetailed(DetailedBean bean) {
        stoploadpb();
        this.detailedBean=bean;
        initdialoglistview();
        contentRelativeLayout.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        setcommentlinearLayout.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().displayImage(bean.getAvatar_url(), iv_icon, ImageLoaderOptions.getListOptions());
        tv_subject.setText(bean.getSubject());
        tv_userdata.setText(bean.getSex() + "," + detailedBean.getAge() + "岁");
        if (detailedBean.getStatus()==1){
            iv_status.setBackgroundResource(R.mipmap.ic_consult_status);
        }
        tv_message.setText(detailedBean.getMessage());
        tv_numviewpeople.setText(detailedBean.getViewnum());
        tv_numcommentpeople.setText(detailedBean.getReplynum());
        tv_time.setText(bean.getDatetime());
        if (bean.getPicsBean()!=null){

            gv_picslist.setVisibility(View.VISIBLE);
            detailPicsAdapter =new DetailPicsAdapter(getcontext(),bean.getPicsBean());
            gv_picslist.setAdapter(detailPicsAdapter);
            detailPicsAdapter.notifyDataSetChanged();
        }else{
            gv_picslist.setVisibility(View.GONE);
        }
        showcomment(bean.getComment());//显示评论

    }

    @Override
    public void loaddetailederror(int code, String msg) {
        stoploadpb();
        errorRelativeLayout.setVisibility(View.VISIBLE);
        tv_error.setText(msg);
        switch (code){
            case Code.LOAD_NODATA:
                iv_error.setBackgroundResource(R.mipmap.nomessage);
                bt_error.setText("发布咨询");
                bt_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        errorRelativeLayout.setVisibility(View.GONE);
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
                        errorRelativeLayout.setVisibility(View.GONE);
                        startloadpb();
                        doloadconsultdetailed(Integer.parseInt(detailedBean.getUid()),detailedBean.getBwztid(), userBean.getM_auth());
                    }
                });
                break;
            case Code.LOAD_FAILURE:
                bt_error.setText("重新加载");
                iv_error.setBackgroundResource(R.mipmap.server_error);
                bt_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        errorRelativeLayout.setVisibility(View.GONE);
                        startloadpb();
                        doloadconsultdetailed(Integer.parseInt(detailedBean.getUid()),detailedBean.getBwztid(), userBean.getM_auth());

                    }
                });
                break;
        }
        showmessage(msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showcomment(List<CommentBean> beans) {
        if(beans.size()>0){
            commentcount=commentcount+beans.size();
            if(loadcomment==Code.INITCOMMENT){
                commentAdapter=new CommentAdapter(getcontext(),beans,this);
            }else if(loadcomment==Code.LOADMORECOMMENT){
                if (commentcount>=Integer.parseInt(tv_numcommentpeople.getText().toString())){
                    footerview.setVisibility(View.GONE);
                }
                IsLoadMore=false;
                progressBar.setVisibility(View.GONE);
                tv_footview.setVisibility(View.VISIBLE);
                if (commentAdapter!=null){
                    commentAdapter.addall(beans);
                }else{
                    commentAdapter=new CommentAdapter(getcontext(),beans,this);
                }
            }

            xListView.setAdapter(commentAdapter);
            commentAdapter.notifyDataSetChanged();
        }
        if (Integer.parseInt(tv_numcommentpeople.getText().toString())==0){
            commentRelativeLayout.setVisibility(View.GONE);

        }else{
            commentRelativeLayout.setVisibility(View.VISIBLE);
            if (commentcount<Integer.parseInt(tv_numcommentpeople.getText().toString())){
                xListView.addFooterView(footerview);
            }

        }
    }

    @Override
    public void loadcommenterror(int code, String msg) {
            commentpage=commentpage-1;
            IsLoadMore=false;
            progressBar.setVisibility(View.GONE);
            tv_footview.setVisibility(View.VISIBLE);
            showmessage(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void setcommentSuccess(int code, String msg) {
        hidekeyboard();
        updatecommentview();
        CommentBean bean=new CommentBean();
        List<String> replylist=new ArrayList<String>();
        bean.setReplylist(replylist);
        bean.setAuthor(userBean.getUserdata().getUsername());
        bean.setAvatar_url(userBean.getUserdata().getAvatar_url());
        bean.setMessage(tempcommenttext);
        bean.setName((userBean.getUserdata().getName()));
        bean.setDateline(String.valueOf(TimeUtil.getStringToDate(TimeUtil.getCurrentTime())));
        List<CommentBean> list=new ArrayList<CommentBean>();
        if (commentAdapter==null){
            list.add(bean);
        }else{
            list=commentAdapter.addlist(bean);
        }
        showcomment(list);
        showmessage(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void setcommenctFailure(int code, String msg) {
        ed_setcomment.setText(tempcommenttext);
        showmessage(msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void shareconsult() {
        Share = new ShareTool(getcontext());
        Share.setPlatformActionListener(this);
        ShareModel model = new ShareModel();
        model.setImageUrl("http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=false&word=美女&pn=0&spn=0&di=0&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&in=3354&cl=2&lm=-1&cs=1735880100%2C3223346182&os=2896705163%2C2839266134&simid=&adpicid=0&fr=ala&fm=&sme=&statnum=girl&cg=girl&bdtype=-1&oriquery=&objurl=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fe7cd7b899e510fb34395d1c3de33c895d0430cd1.jpg&fromurl=http%3A%2F%2Fimage.baidu.com%2Fdetail%2Fnewindex%3Fcol%3D%26tag%3D%26pn%3D0%26pid%3D31752171852%26aid%3D400266147%26user_id%3D980778976%26setid%3D-1%26sort%3D0%26newsPn%3D%26star%3D%26fr%3D%26from%3D2&gsm=0");
        model.setText(detailedBean.getContent());
        model.setTitle(detailedBean.getSubject());
        String url=String.format("https://ucqa.dawnlightning.com/space.php?uid=%s&do=bwzt&id=%s", detailedBean.getUid(), detailedBean.getBwztid());
        model.setUrl(url);
        Share.initShareParams(model);

        Share.showShareWindow();
    }

    @Override
    public void onCancel(Platform arg0, int arg1)
    {

        Message msg = new Message();
        msg.what = 0;
        UIHandler.sendMessage(msg, this);
        Share.dismiss();
    }

    @Override
    public void onComplete(Platform plat, int action, HashMap<String, Object> res)
    {
        Message msg = new Message();
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
        Share.dismiss();
    }

    @Override
    public void onError(Platform arg0, int arg1, Throwable arg2)
    {
        Message msg = new Message();
        msg.what = 1;
        UIHandler.sendMessage(msg, this);
        Share.dismiss();
    }

    @Override
    public boolean handleMessage(Message msg)
    {
        int what = msg.what;
        if (what == 1)
        {
            Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
            Share.dismiss();
        }else{

            Share.dismiss();
        }

        return false;
    }
    @Override
    public void startloadpb() {
        loadprgressBar.setVisibility(View.VISIBLE);
        loadprgressBar.setProgress(0);
        myHandle.sendEmptyMessageDelayed(0, 100);

    }
   Handler myHandle=new Handler() {
        int mProgressStatus=0;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            java.util.Random random=new java.util.Random();// 定义随机类int
            int index=random.nextInt(10);// 返回[0,10)集合中的整数，注意不包括10
            mProgressStatus += index;
            if(mProgressStatus >100){
                mProgressStatus = 70;
            }
            //设置进度条当前的完成进度
            loadprgressBar.setProgress(mProgressStatus);

            if(mProgressStatus <100){
                myHandle.sendEmptyMessageDelayed(0,100);
            }
        }
    };
    @Override
    public void stoploadpb() {
        loadprgressBar.setProgress(100);
        loadprgressBar.setVisibility(View.GONE);
    }

    @Override
    public void replySuccess(CommentBean bean,int postion) {
        hidekeyboard();
        updatecommentview();
        if (commentAdapter!=null){
            commentAdapter.replacecommentbean(bean,postion);
            xListView.setAdapter(commentAdapter);
            //LvHeightUtil.setListViewHeightBasedOnChildren(xListView,2);
            commentAdapter.notifyDataSetChanged();
        }
        ed_setcomment.setHint("写下你的想法和大家交流吧");
        operate=Code.COMMENT;
        showmessage("回复成功", Toast.LENGTH_SHORT);
    }

    @Override
    public void replyFailure(int code, String msg) {
        showmessage(msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void doReply(final CommentBean bean,int postion) {

        this.commentBean=bean;
        this.ReplyPostion=postion;
        showreplydialog(postion);
    }

    @Override
    public void showreplydialog(int postion) {
        showketboard();
        operate=Code.REPLY;
        CommentBean bean=(CommentBean)commentAdapter.getItem(postion);
        String replyname="";
        if (bean.getName().length()>0){
            replyname=bean.getName();
        }else{
            replyname=bean.getAuthor();
        }
        ed_setcomment.setHint("@" + replyname);
    }

    @Override
    public void hidekeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ed_setcomment.getWindowToken(), 0);
    }

    @Override
    public void showketboard() {
        ed_setcomment.requestFocus();
        InputMethodManager imm = (InputMethodManager) ed_setcomment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        ed_setcomment.setFocusable(true);
    }

    @Override
    public void updatecommentview() {
        int count=Integer.parseInt(tv_numcommentpeople.getText().toString().trim())+1;
        tv_numcommentpeople.setText(String.valueOf(count));
    }
    private String username(){
        String username="";
        if (userBean.getUserdata().getName().length()>0){
           return username=userBean.getUserdata().getName();
        }else{
            return  username=userBean.getUserdata().getUsername();
        }
    }

    @Override
    public void reportSuceess(String msg) {
        reportdialog.dismiss();
        showmessage(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void reportFailure(int code, String msg) {
        showmessage(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void doreport(String m_auth, int classid, String reason) {
        detailedPresenter.report(m_auth,classid,reason);
    }

    @Override
    public void showreportdialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_report, null);
        reportdialog = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,true);
        final  ImageView iv_consult_report_close=(ImageView)view.findViewById(R.id.iv_consult_report_close);
        final EditText et_consult_report_reason=(EditText)view.findViewById(R.id.et_consult_report_reason);
        final  Button bt_consult_report_sent= (Button) view.findViewById(R.id.bt_consult_report_sent);
        bt_consult_report_sent.setClickable(false);
        bt_consult_report_sent.setBackgroundColor(getResources().getColor(R.color.lightgray));
        iv_consult_report_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportdialog.dismiss();
            }
        });
        et_consult_report_reason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 20) {
                    bt_consult_report_sent.setClickable(true);
                    bt_consult_report_sent.setBackgroundColor(getResources().getColor(R.color.green));
                } else {
                    bt_consult_report_sent.setClickable(false);
                    bt_consult_report_sent.setBackgroundColor(getResources().getColor(R.color.lightgray));
                }
            }
        });
        bt_consult_report_sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doreport(userBean.getM_auth(), detailedBean.getBwztid(), et_consult_report_reason.getText().toString());

            }
        });

        reportdialog.setFocusable(true);
        // 必须设置背景
        reportdialog.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击其他地方 就消失 (只设置这个，没有效果)
        reportdialog.setOutsideTouchable(true);
        reportdialog.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);

        reportdialog.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                reportdialog=null;
            }
        });
    }

    @Override
    public void initdialoglistview() {
        //实例化标题栏弹窗
        titlePopup = new TitlePopup(this, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        //给标题栏弹窗添加子类
        titlePopup.addAction(new ActionItem(this, "分享", R.mipmap.ic_share));
        titlePopup.addAction(new ActionItem(this,"举报",R.mipmap.ic_report));
        if(detailedBean.getUid().equals(userBean.getUserdata().getUid())){
            titlePopup.addAction(new ActionItem(this, "采纳", R.mipmap.ic_slove));
            titlePopup.addAction(new ActionItem(this, "删除",  R.mipmap.ic_delete));
        }
        titlePopup.setItemOnClickListener(new TitlePopup.OnItemOnClickListener() {
            @Override
            public void onItemClick(ActionItem item, int position) {
                if (position==0){
                    shareconsult();//分享
                }else if(position==1){
                    showreportdialog();//举报
                }else{
                    if(detailedBean.getUid().equals(userBean.getUserdata().getUid())) {
                        switch (position){
                            case 2:
                                dosolve(detailedBean.getBwztid(),userBean.getM_auth());
                                break;
                            case 3:
                                dodelete(detailedBean.getBwztid(), userBean.getM_auth());
                                break;
                        }
                    }
                }

            }
        });


    }

    @Override
    public void dosolve(int classid, String m_auth) {
        detailedPresenter.solve(classid, m_auth);

    }

    @Override
    public void solveSuccess(String msg) {
        showmessage(msg, Toast.LENGTH_SHORT);
        iv_status.setBackgroundResource(R.mipmap.ic_consult_status);
        Intent intent=new Intent();
        intent.putExtra("bwztid",detailedBean.getBwztid());
        this.setResult(Code.DetailForResultSolve, intent);
    }

    @Override
    public void solveFailure(int code, String msg) {
        showmessage(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void deleteSuceess(String msg) {
        showmessage(msg,Toast.LENGTH_SHORT);
        Intent intent=new Intent();
        intent.putExtra("bwztid",detailedBean.getBwztid());
        this.setResult(Code.DetailForResultDelete, intent);
        this.finish();
    }

    @Override
    public void deleteFailure(int code, String msg) {
        showmessage(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void dodelete(int classid, String m_auth) {
        detailedPresenter.delete(classid,m_auth);
    }

}
