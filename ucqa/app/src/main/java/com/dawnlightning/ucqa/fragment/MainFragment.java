package com.dawnlightning.ucqa.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dawnlightning.ucqa.Bean.ConsultClassifyBean;
import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.Listener.IBase;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.activity.ConsultActivity;
import com.dawnlightning.ucqa.activity.DetailActivity;
import com.dawnlightning.ucqa.activity.MainActivity;
import com.dawnlightning.ucqa.adapter.ClassifyAdapter;
import com.dawnlightning.ucqa.adapter.ConsultAdapter;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.presenter.ConsultListPresenter;
import com.dawnlightning.ucqa.view.OtherGridView;
import com.dawnlightning.ucqa.viewinterface.IConsultListView;

import java.io.Serializable;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

/**
 * Created by Administrator on 2016/4/18.
 */
public class MainFragment extends Fragment implements IConsultListView,IXListViewLoadMore,IXListViewRefreshListener,IBase{
    private XListView mListView;
    private ProgressBar progressBar;
    private OtherGridView gv_listview_header;
    private View headerView;
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
    private int Bwztclassid=0;
    private static int clickpostion=0;
    @Override
    public void doloadlist() {
        consultListPresenter.loadclassify();
        startprogressBar();
        refresh(Code.REFRESH);
    }
    @Override
    public void showclassifylist(List<ConsultClassifyBean> list) {
        mainActivity.consultClassifyBeanList=list;
        consultClassifyBeanList=list;
        classifyAdapter=new ClassifyAdapter(getActivity(),list);
        gv_listview_header.setAdapter(classifyAdapter);
        classifyAdapter.notifyDataSetChanged();
        mListView.addHeaderView(headerView);
        mainActivity.showtitleclassift("全部");

    }

    @Override
    public void doloadassignlist(int page, int bwztclassid, int identity, int operate) {
        consultListPresenter.loadassignlist(page, bwztclassid, "", Code.ALL, operate);
    }

    @Override
    public void getSuccess(int code, List<ConsultMessageBean> list, int identity, int operate) {
        if (consultAdapter==null){
            consultAdapter=new ConsultAdapter(getActivity(),list);
        }
        stopprgressBar();//停止进度条
        if (code==Code.LOAD_FULL_SUCCESS)//可能有下一页
        {
            mListView.setPullLoadEnable(this);
        }else if(code==Code.LOAD_NOFULL_SUCCESS)//不可能有下一页
        {
            mListView.disablePullLoad();
        }

        if (operate== Code.REFRESH)//如果是下拉刷新
        {
            mListView.stopRefresh();//停止刷新
            consultAdapter.setList(list);
            //consultAdapter.headinsert(list);//list头插

        }
        else if(operate==Code.CHANGE) //如果是点击Girdview
        {

            mListView.stopRefresh();//停止刷新
            consultAdapter.setList(list);

        }
        else if(operate==Code.LOADMORD)//如果是上拉加载
        {
            mListView.stopLoadMore();//停止更新
            consultAdapter.addList(list);//尾插
        }
        mListView.setAdapter(consultAdapter);
        consultAdapter.notifyDataSetChanged();//刷新列表
    }

    @Override
    public void getFailure(int code, String msg, int identity, int operate) {
        stopprgressBar();
        //停止一切上拉下拉刷新
        if (code == Code.SERVER_LOAD_FAILURE)
        {
            mListView.disablePullRefreash();
            mListView.disablePullLoad();
        } else if (code == Code.LOAD_FAILURE) {
            mListView.disablePullRefreash();
            mListView.disablePullLoad();
        }

        if (operate == Code.REFRESH) {
            mListView.stopRefresh();
        }
        else if (operate == Code.CHANGE) {

            if (code == Code.LOAD_NODATA) {
                consultAdapter.clearList();
                mListView.disablePullLoad();
                mListView.disablePullRefreash();
            }
            mListView.stopRefresh();
        } else {
            mListView.stopLoadMore();
        }
        consultAdapter.notifyDataSetChanged();
        if (operate == Code.CHANGE) {
            showerror(code, msg);
        } else {
            mainActivity.showmessage(msg, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void showerror(int code, String msg) {
        ll_error.setVisibility(View.VISIBLE);//显示错误背景
        tv_error.setText(msg);//显示错误原因
        final ConsultClassifyBean bean=new ConsultClassifyBean();
        bean.setBwztclassarrname(mainActivity.tv_title.getText().toString());
        bean.setBwztclassarrid(String.valueOf(Bwztclassid));
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
                        girdviewonclick(bean);
                    }
                });
                break;
            case Code.LOAD_FAILURE:
                bt_error.setText("重新加载");
                iv_error.setBackgroundResource(R.mipmap.server_error);
                bt_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        girdviewonclick(bean);


                    }
                });
                break;
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
        if(mainActivity.tv_title.getText().toString()=="全部"){
            doloadassignlist(Page, -1, Code.ALL, operate);
        }else{
            doloadassignlist(Page, Bwztclassid, Code.ALL, operate);
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
        final View view =inflater.inflate(R.layout.fragment_content, container,false);
        ll_error=(LinearLayout)view.findViewById(R.id.ll_error);
        tv_error=(TextView)view.findViewById(R.id.tv_error);
        iv_error=(ImageView)view.findViewById(R.id.iv_error);
        bt_error=(Button)view.findViewById(R.id.bt_error);
        mListView=(XListView)view.findViewById(R.id.lv_consult);
        mListView.NotRefreshAtBegin();
        progressBar=(ProgressBar) view.findViewById(R.id.consult_list_progressbar);
        headerView=getActivity().getLayoutInflater().inflate(R.layout.listview_header_girdview,null);
        gv_listview_header=(OtherGridView)headerView.findViewById(R.id.gv_classify);
        initdata();
        initevent();
        return view;
    }

    @Override
    public void initview() {

    }

    @Override
    public void initdata() {
        doloadlist();
    }

    @Override
    public void initevent() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickpostion=position;
                ConsultMessageBean bean = (ConsultMessageBean) parent.getAdapter().getItem(position);
                viewdetail(bean.getUid(), bean.getBwztid(), mainActivity.userBean);
            }
        });
        gv_listview_header.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ConsultClassifyBean bean = (ConsultClassifyBean) classifyAdapter.getItem(position);
                girdviewonclick(bean);

            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        mainActivity=(MainActivity)activity;
        consultListPresenter=new ConsultListPresenter(this, MyApp.getApp());
        super.onAttach(activity);
    }

    @Override
    public void onStart() {

        super.onStart();
    }
    private void startprogressBar(){

        progressBar.setVisibility(View.VISIBLE);
        mListView.disablePullLoad();
        mListView.disablePullRefreash();

    }
    private void stopprgressBar(){
        progressBar.setVisibility(View.GONE);
        mListView.setPullLoadEnable(this);
        mListView.setPullRefreshEnable(this);
    }
    private void girdviewonclick(ConsultClassifyBean bean) {
        consultListPresenter.stopresquest();
        mListView.setPullLoadEnable(this);
        mListView.setPullRefreshEnable(this);
        ll_error.setVisibility(View.GONE);
        Bwztclassid=bean.getBwztclassarrid();
        mainActivity.showtitleclassift(bean.getBwztclassarrname());
        consultAdapter.clearList();
        consultAdapter.notifyDataSetChanged();
        startprogressBar();
        refresh(Code.CHANGE);
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
                    update();
                    break;
                case Code.DetailForResultSolve:
                    update();
                    mListView.startRefresh();
                    break;
                case Code.ConsultForResult:
                    update();
                    break;
                default:
                    break;
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
    public void update(){
        consultListPresenter.clearcache(Code.ALL,Integer.parseInt(mainActivity.userBean.getUserdata().getUid()),mainActivity.userBean.getM_auth());
        consultAdapter.clearList();
        mListView.startRefresh();
    }
}
