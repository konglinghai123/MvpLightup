package com.dawnlightning.ucqa.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawnlightning.ucqa.R;

/**
 * Created by Administrator on 2016/3/31.
 */
public class LoadingDialog extends ProgressDialog {
    private  Context context;
    private  String msg;
    private OnDismissListener onDismissListener;
    public LoadingDialog(Context context, String strMessage,OnDismissListener onDismissListener) {
        super(context);
        this.context=context;
        this.msg=strMessage;
        this.onDismissListener=onDismissListener;
    }

    @Override
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus) {
            dismiss();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_loading);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        TextView tvMsg = (TextView) this.findViewById(R.id.id_tv_loadingmsg);
        ImageView ivLogo=(ImageView)this.findViewById(R.id.loadingImageView);
        if (tvMsg != null) {
            tvMsg.setText(msg);
        }
        if(ivLogo!=null){
            ivLogo.setBackgroundResource(R.drawable.loading);
            final AnimationDrawable frameAnimation = (AnimationDrawable)ivLogo.getBackground();
            ivLogo.post(new Runnable() {
                @Override
                public void run() {
                    frameAnimation.start();
                }
            });
        }
        if (onDismissListener!=null){
            this.setOnDismissListener(onDismissListener);
        }
    }
}
