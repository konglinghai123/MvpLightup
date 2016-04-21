package com.dawnlightning.ucqa.share;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.dialog.LoadingDialog;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import cn.sharesdk.wechat.utils.WechatHelper;

/**
 * 分享工具
 * 
 * @author xiaojun
 * 
 */
public class ShareTool
{
    private Context context;
    private PlatformActionListener platformActionListener;
    private ShareParams shareParams;
    private ProgressDialog pd;
    private  PopupWindow shareWindow;
    public ShareTool(Context context)
    {
        this.context = context;


    }
    public void initdialog(String msg){
        if (pd == null) {
            pd = new LoadingDialog(context, msg, new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                }
            });
            pd.show();
        } else {
            pd.show();
        }
    }
    public  void dismissdialog(){
        pd.dismiss();
    }
    /**
     * 隐藏加载提示
     */
    public void dismiss()
    {
        if (pd != null)
        {
            pd.dismiss();
        }
    }

    public PlatformActionListener getPlatformActionListener()
    {
        return platformActionListener;
    }

    public void setPlatformActionListener(PlatformActionListener platformActionListener)
    {
        this.platformActionListener = platformActionListener;
    }

    @SuppressLint("InflateParams")
	public void showShareWindow()
    {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);
        GridView gridView = (GridView) view.findViewById(R.id.share_gridview);
        ShareAdapter adapter = new ShareAdapter(context);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                initdialog("分享中");
                share(position);
                shareWindow.dismiss();
            }
        });
        shareWindow = new PopupWindow(view, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,true);
        shareWindow.setFocusable(true);
        // 必须设置背景
        shareWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击其他地方 就消失 (只设置这个，没有效果)
        shareWindow.setOutsideTouchable(true);
        shareWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);

        shareWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                shareWindow=null;
            }
        });

    }


    /**
     * 分享
     * 
     * @param position
     */
    private void share(int position)
    {

        if (position == 3)
        {
            qzone();
        }else if(position==0){
        	weiXinWebShare(true);
        }else if(position==1){
        	weiXinWebShare(false);
        }
        else
        {
        	qq();
            
        }
    }

    /**
     * 初始化分享参数
     * 
     * @param shareModel
     */
    public void initShareParams(ShareModel shareModel)
    {
        if (shareModel != null)
        {
            ShareParams sp = new ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setTitle(shareModel.getTitle());
            sp.setText(shareModel.getText());
            sp.setUrl(shareModel.getUrl());
            sp.setImageUrl(shareModel.getImageUrl());
            shareParams = sp;
        }
    }

   
    /**
     * 分享到QQ空间
     */
    private void qzone()
    {
        ShareParams sp = new ShareParams();
        sp.setTitle(shareParams.getTitle());
        sp.setTitleUrl(shareParams.getUrl()); // 标题的超链接
        sp.setText(shareParams.getText());
        sp.setImageUrl(shareParams.getImageUrl());
        sp.setComment("我对此分享内容的评论");
        sp.setSite(shareParams.getTitle());
        sp.setSiteUrl(shareParams.getUrl());
        Platform qzone = ShareSDK.getPlatform(context, "QZone");
        qzone.SSOSetting(false);
        qzone.setPlatformActionListener(platformActionListener); // 设置分享事件回调 // 执行图文分享
        qzone.share(sp);
    }
    private void weiXinWebShare(boolean isWeixin)
    
    {
   
    WechatHelper.ShareParams sp = null;
    
    if (isWeixin)
   
    {
   
    sp = new Wechat.ShareParams();
   
    }
   
    else
   
    {
   
    sp = new WechatMoments.ShareParams();
   
    }
    sp.setShareType(Platform.SHARE_WEBPAGE);
    sp.title = shareParams.getTitle();
    sp.text = shareParams.getText();
    sp.imageData=((BitmapDrawable)context.getResources().getDrawable(R.drawable.mylogo)).getBitmap(); 
    sp.url = shareParams.getUrl(); 
   
   
    Platform plat = null;
    
    if (isWeixin)
    
    {
   
    plat = ShareSDK.getPlatform(context, Wechat.NAME);
    
    }
    
    else
   
    {
 
    plat = ShareSDK.getPlatform(context, WechatMoments.NAME);
    
    }
    
    plat.setPlatformActionListener(platformActionListener);
    
    plat.share(sp);
    
    }
   
    private void qq() {
        QQ.ShareParams sp = new QQ.ShareParams();

        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle(shareParams.getTitle());
        sp.setTitleUrl(shareParams.getUrl()); // 标题的超链接
        sp.setText(shareParams.getText());
        sp.setImageUrl(shareParams.getImageUrl());
        sp.setComment("我对此分享内容的评论");
        sp.setSite(shareParams.getTitle());
        sp.setSiteUrl(shareParams.getUrl());
        Platform qq = ShareSDK.getPlatform(context, "QQ");
        qq.SSOSetting(false);
        qq.setPlatformActionListener(platformActionListener);
        qq.share(sp);
    }
    
}
