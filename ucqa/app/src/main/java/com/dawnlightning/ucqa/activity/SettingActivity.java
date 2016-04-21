package com.dawnlightning.ucqa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.base.BaseActivity;
import com.dawnlightning.ucqa.base.Code;
import com.dawnlightning.ucqa.base.MyApp;
import com.dawnlightning.ucqa.presenter.SettingPresenter;
import com.dawnlightning.ucqa.viewinterface.ISettingView;

/**
 * Created by Administrator on 2016/4/13.
 */
public class SettingActivity extends BaseActivity implements ISettingView{
    private RelativeLayout re_help;
    private RelativeLayout re_clearcache;
    private RelativeLayout re_checkupdate;
    private RelativeLayout re_about;
    private Button bt_loginoff;
    private ToggleButton toggleButton;
    private TextView tv_version;
    private TextView tv_cachesize;
    private SettingPresenter settingPresenter;
    private UserBean userBean;
    private ImageView iv_back;
    private TextView tv_update;
    @Override
    public void initview() {
        re_help= (RelativeLayout) findViewById(R.id.re_setting_help);
        re_clearcache=(RelativeLayout)findViewById(R.id.re_setting_clearcahe);
        re_checkupdate=(RelativeLayout)findViewById(R.id.re_setting_checkupdate);
        re_about=(RelativeLayout)findViewById(R.id.re_setting_aboutme);
        bt_loginoff= (Button) findViewById(R.id.bt_setting_logoff);
        toggleButton= (ToggleButton) findViewById(R.id.tb_setting_push);
        tv_version=(TextView)findViewById(R.id.tv_setting_version);
        tv_cachesize=(TextView)findViewById(R.id.tv_setting_cahesize);
        iv_back=(ImageView)findViewById(R.id.iv_setting_back);
        tv_update=(TextView)findViewById(R.id.tv_setting_update_status);
    }

    @Override
    public void initdata() {
        settingPresenter=new SettingPresenter(this,getcontext());
        settingPresenter.getcachesize();
        settingPresenter.getversion();
        settingPresenter.getpushstatus();
        docheckupdate(false);

    }

    @Override
    public void initevent() {
        re_clearcache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              clearcachesize();
            }
        });
        re_checkupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              docheckupdate(true);
            }
        });
        bt_loginoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginoff();
            }
        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                closepush();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setNeedBackGesture(true);//设置需要手势监听
        initview();
        userBean=(UserBean)getIntent().getSerializableExtra("userdata");
        initdata();
        initevent();

    }

    @Override
    public void docheckupdate(Boolean isShowupdatedialog) {
        settingPresenter.checkupdate(isShowupdatedialog);
    }

    @Override
    public void showversion(String versioncode) {
        tv_version.setText(versioncode);
    }

    @Override
    public void showcachesize(String cachesize) {
      tv_cachesize.setText(cachesize);
    }

    @Override
    public void showpushstatus(boolean status) {
        toggleButton.setChecked(status);
    }

    @Override
    public void showmsg(String msg) {
        showmessage(msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void help() {

    }

    @Override
    public void clearcachesize() {
        settingPresenter.clearcache();
    }

    @Override
    public void loginoff() {
        setResult(Code.LoginoffForResult);
        finish();
      settingPresenter.logoff(userBean.getUhash(), userBean.getM_auth());
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }

    @Override
    public void about() {

    }

    @Override
    public void closepush() {
        settingPresenter.Push(toggleButton.isChecked());
    }

    @Override
    public void showupdatestatus() {
        tv_update.setVisibility(View.VISIBLE);
    }
}
