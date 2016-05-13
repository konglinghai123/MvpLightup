package com.dawnlightning.ucqa.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.gesture.IBase;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.activity.ConsultActivity;
import com.dawnlightning.ucqa.activity.DetailActivity;
import com.dawnlightning.ucqa.activity.MainActivity;
import com.dawnlightning.ucqa.adapter.ClassifyAdapter;
import com.dawnlightning.ucqa.adapter.ConsultAdapter;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.presenter.ConsultListPresenter;
import com.dawnlightning.ucqa.viewinterface.IConsultListView;

import java.io.Serializable;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * Created by Administrator on 2016/4/18.
 */
public class MyConsultFragment extends Fragment implements IConsultListView,IXListViewLoadMore,IXListViewRefreshListener,IBase {
    private XListView mListview;
    private ProgressBar progressBar;

    private LinearLayout ll_error;
    private TextView tv_error;
    private ImageView iv_error;
    private Button bt_error;

    private MainActivity mainActivity;
    private ConsultListPresenter consultListPresenter;
    private ConsultAdapter consultAdapter;
    private ClassifyAdapter classifyAdapter;
    private List<ConsultClassifyBean> consultClassifyBeanList;
    private int Page=1;
    private static int clickpostion=0;
    private static int postion=0;
    @Override
    public void doloadlist() {
        Page=1;

        ll_error.setVisibility(View.GONE);
        startprogressBar();
        refresh(Code.REFRESH);
    }



    @Override
    public void showclassifylist(List<ConsultClassifyBean> list) {

    }

    @Override
    public void doloadassignlist(int page, int bwztclassid, int identity, int operate) {

    }

    @Override
    public void getSuccess(int code, List<ConsultMessageBean> list, int identity, int operate) {
        stopprgressBar();
        if (consultAdapter==null){
            consultAdapter=new ConsultAdapter(getActivity(),list);
        }
        if (code==Code.LOAD_FULL_SUCCESS){
            mListview.setPullLoadEnable(this);
        }else if(code==Code.LOAD_NOFULL_SUCCESS){
            mListview.disablePullLoad();
            mListview.setFooterText("已加载全部内容");
        }
        if (operate== Code.REFRESH){
            mListview.stopRefresh();
            //consultAdapter.setList(list);
            consultAdapter.headinsert(list);

        }else if(operate==Code.LOADMORD){
            mListview.stopLoadMore();
            consultAdapter.addList(list);
        }
        mListview.setAdapter(consultAdapter);
        consultAdapter.notifyDataSetChanged();
        mListview.setSelection(postion + 1);
    }

    @Override
    public void getFailure(int code, String msg, int identity, int operate) {
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
    public void refresh(int operate) {
        if (operate==Code.REFRESH){
            Page = 1;
        } else if (operate == Code.LOADMORD) {
            Page = Page + 1;
        }
        startprogressBar();
        consultListPresenter.loadassignlist(Page, Integer.parseInt(mainActivity.userBean.getUserdata().getUid()), mainActivity.userBean.getM_auth(), Code.ME, operate);
    }



    @Override
    public void initview() {

    }

    @Override
    public void initdata() {

    }

    @Override
    public void initevent() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickpostion=position;
                ConsultMessageBean bean = (ConsultMessageBean) parent.getAdapter().getItem(position);
                viewdetail(bean.getUid(), bean.getBwztid(), mainActivity.userBean);
            }
        });
    }

    @Override
    public void showerror(int code, String msg) {
        ll_error.setVisibility(View.VISIBLE);//显示错误背景
        tv_error.setText(msg);//显示错误原因
        switch (code){
            case Code.LOAD_NODATA:
                iv_error.setBackgroundResource(R.mipmap.nomessage);
                bt_error.setText("发布咨询");
                bt_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent consultintent=new Intent();
                        consultintent.setClass(getActivity(), ConsultActivity.class);
                        Bundle consultbundle=new Bundle();
                        consultbundle.putSerializable("userdata",mainActivity.userBean);
                        consultbundle.putSerializable("classifybeanlist", (Serializable) mainActivity.consultClassifyBeanList);
                        consultintent.putExtras(consultbundle);
                        startActivityForResult(consultintent, Code.ConsultForResult);
                    }
                });
                break;
            case Code.SERVER_LOAD_FAILURE:
                bt_error.setText("重新加载");
                iv_error.setBackgroundResource(R.mipmap.server_error);
                bt_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startprogressBar();
                        consultListPresenter.loadassignlist(Page,Integer.parseInt(mainActivity.userBean.getUserdata().getUid()),mainActivity.userBean.getM_auth(), Code.ME, Code.REFRESH);
                    }
                });
                break;
            case Code.LOAD_FAILURE:
                bt_error.setText("重新加载");
                iv_error.setBackgroundResource(R.mipmap.server_error);
                bt_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startprogressBar();
                        consultListPresenter.loadassignlist(Page,Integer.parseInt(mainActivity.userBean.getUserdata().getUid()),mainActivity.userBean.getM_auth(), Code.ME, Code.REFRESH);

                    }
                });
                break;
        }
    }



    @Override
    public void onLoadMore() {
        refresh(Code.LOADMORD);
    }

    @Override
    public void onRefresh() {
        refresh(Code.REFRESH);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_content, container, false);
        ll_error = (LinearLayout) view.findViewById(R.id.ll_error);
        tv_error = (TextView) view.findViewById(R.id.tv_error);
        iv_error = (ImageView) view.findViewById(R.id.iv_error);
        bt_error = (Button) view.findViewById(R.id.bt_error);
        mListview = (XListView) view.findViewById(R.id.lv_consult);
        mListview.NotRefreshAtBegin();
        progressBar = (ProgressBar) view.findViewById(R.id.consult_list_progressbar);
        mListview.setOnScrollListener(new AbsListView.OnScrollListener() {

            /**
             * 滚动状态改变时调用
             */
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 不滚动时保存当前滚动到的位置
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    postion = mListview.getFirstVisiblePosition();
                }
            }
            /**
             * 滚动时调用
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        mainActivity=(MainActivity)activity;
        consultListPresenter=new ConsultListPresenter(this, MyApp.getApp());

        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        doloadlist();
        initevent();
        super.onStart();
    }
    private void startprogressBar(){
        progressBar.setVisibility(View.VISIBLE);
        mListview.disablePullLoad();
        mListview.disablePullRefreash();

    }
    private void stopprgressBar(){
        progressBar.setVisibility(View.GONE);
        mListview.setPullLoadEnable(this);
        mListview.setPullRefreshEnable(this);
    }
    public void viewdetail(String  uid,String bwztid, UserBean bean) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("userdata", bean);
        bundle.putString("bwztid", bwztid);
        bundle.putString("uid", uid);
        intent.putExtras(bundle);
        this.startActivityForResult(intent, Code.DetailForResult);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode==requestCode) {
            switch (resultCode) {
                case Code.DetailForResultDelete:
                    consultListPresenter.clearcache(Code.ME,Integer.parseInt(mainActivity.userBean.getUserdata().getUid()),mainActivity.userBean.getM_auth());
                  consultAdapter.clearList();
                   mListview.startRefresh();
                    break;
                case Code.DetailForResultSolve:
                    consultListPresenter.clearcache(Code.ME,Integer.parseInt(mainActivity.userBean.getUserdata().getUid()),mainActivity.userBean.getM_auth());
                    consultAdapter.clearList();
                    mListview.startRefresh();
                    break;
                case Code.ConsultForResult:
                    consultListPresenter.clearcache(Code.ME,Integer.parseInt(mainActivity.userBean.getUserdata().getUid()),mainActivity.userBean.getM_auth());
                    consultAdapter.clearList();
                    mListview.startRefresh();
                break;
                default:
                    break;
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
