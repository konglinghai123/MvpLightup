package com.dawnlightning.ucqa.fragment;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.dawnlightning.ucqa.Bean.UserBean;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.activity.MainActivity;
import com.dawnlightning.ucqa.activity.WelcomeActivity;
import com.dawnlightning.ucqa.db.SharedPreferenceDb;
import com.dawnlightning.ucqa.model.RegisterModel;
import com.dawnlightning.ucqa.presenter.LoginPresenter;
import com.dawnlightning.ucqa.presenter.ResigterPresenter;
import com.dawnlightning.ucqa.util.AppUtils;
import com.dawnlightning.ucqa.view.EditTextWithDel;
import com.dawnlightning.ucqa.view.PassWordShow;
import com.dawnlightning.ucqa.viewinterface.ILoginView;
import com.dawnlightning.ucqa.viewinterface.IRegisterView;

/**
 * Created by Administrator on 2016/3/31.
 */
public class LoginFragment extends  Fragment implements ILoginView,IRegisterView {
    private PassWordShow password;
    private PassWordShow password2;
    private EditTextWithDel phone;
    private TextView tv_register;
    private WelcomeActivity activity;
    private LoginPresenter loginpresenter;
    private boolean IsLogin=true;
    private ResigterPresenter resigterPresenter;
    private SharedPreferenceDb sharedPreferenceDb;

    @Override
    public View initview(LayoutInflater inflater,ViewGroup container) {
        View view =inflater.inflate(R.layout.fragment_login, container,false);

        loginpresenter=new LoginPresenter(this,getActivity());
        resigterPresenter=new ResigterPresenter(this);
        password=(PassWordShow)view.findViewById(R.id.password);
        password2=(PassWordShow)view.findViewById(R.id.password2);
        phone=(EditTextWithDel)view.findViewById(R.id.phone);
        tv_register=(TextView)view.findViewById(R.id.register);
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsLogin) {
                    IsLogin=false;
                    password2.setVisibility(View.VISIBLE);
                    tv_register.setText("登陆");
                }else{
                    IsLogin=true;
                    password2.setVisibility(View.GONE);
                    tv_register.setText("注册");
                }


            }
        });
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    ((InputMethodManager) password.getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    getActivity()
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (IsLogin){
                        loginpresenter.login(phone.getText().toString().trim(), password.getText().toString().trim());
                    }else{

                        activity.showmessage("请再次输入密码",Toast.LENGTH_SHORT);
                    }



                    return true;
                }
                return false;
            }

        });
        password2.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_GO){
                    ((InputMethodManager) password2.getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    getActivity()
                                            .getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    if (!IsLogin){
                        if (phone.getText().toString()!=""){
                            if (password.getText().toString()!=""&&password2.getText().toString()!=""){
                                if (password.getText().toString().equals(password2.getText().toString())){
                                    if (AppUtils.checkNetwork(getActivity())){
                                        showloadingdialog("注册中");
                                        RegisterModel model =new RegisterModel(password.getText().toString().trim(),phone.getText().toString().trim());
                                        doregister(model);
                                    }else{
                                        activity.showmessage("请检查当前网络连接",Toast.LENGTH_SHORT);
                                    }
                                }else{
                                    activity.showmessage("两次密码不相同",Toast.LENGTH_SHORT);
                                }

                            }else{
                                activity.showmessage("请输入密码",Toast.LENGTH_SHORT);
                            }
                        }else{
                            activity.showmessage("请再次输入手机号",Toast.LENGTH_SHORT);
                        }

                    }

                    return true;
                }
                return false;
            }

        });
        return view;
    }

    @Override
    public void saveuserdata(UserBean bean) {
        sharedPreferenceDb=this.activity.getMySharedPreferenceDb();
        sharedPreferenceDb.setage(bean.getUserdata().getAge());
        sharedPreferenceDb.setAVATOR(bean.getUserdata().getAvatar_url());
        sharedPreferenceDb.setuserAutoLogin(true);
        sharedPreferenceDb.setm_auth(bean.getM_auth());
        sharedPreferenceDb.setformhash(bean.getFormhash());
        sharedPreferenceDb.setPhone(phone.getText().toString().trim());
        sharedPreferenceDb.setPassword(password.getText().toString().trim());
        if(bean.getUserdata().getSex()=="0"){
            sharedPreferenceDb.setsex("男");
        }else{
            sharedPreferenceDb.setsex("女");
        }


    }

    @Override
    public void dologin(String phone,String password) {

        loginpresenter.login(phone,password);
    }

    @Override
    public void dissmissloadingdialog() {
        activity.dismissdialog();
    }

    @Override
    public void loginSuccess(UserBean bean) {
        saveuserdata(bean);
        Intent intent = new Intent();
        intent.setClass(getActivity(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("userdata",bean);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.finish();

    }

    @Override
    public void showloadingdialog(String msg) {
        activity.initdialog(msg, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
    }

    @Override
    public void clearpassword() {
        password.setText("");
        password2.setText("");
    }

    @Override
    public void loginFailure(int code, String msg) {
        activity.showmessage(msg,Toast.LENGTH_LONG);
        clearpassword();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=(WelcomeActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return initview(inflater,container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public void doregister(RegisterModel model) {
        resigterPresenter.register(model);
    }

    @Override
    public void registerSuccess(RegisterModel model) {
        loginpresenter.login(model.getUsename(),model.getPassword());
        dissmissloadingdialog();

    }

    @Override
    public void registerFailure(int code, String msg) {

        dissmissloadingdialog();activity.showmessage(msg,Toast.LENGTH_LONG);
    }
}
