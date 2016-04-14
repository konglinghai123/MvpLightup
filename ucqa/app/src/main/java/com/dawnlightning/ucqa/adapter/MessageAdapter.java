package com.dawnlightning.ucqa.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawnlightning.ucqa.Bean.MessageBean;
import com.dawnlightning.ucqa.Bean.PicsBean;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.activity.DisplayActivity;
import com.dawnlightning.ucqa.tools.ImageLoaderOptions;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.dawnlightning.ucqa.view.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<MessageBean> list;
    private ViewHolder viewHolder;
    private  LayoutInflater layoutInflater;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    public MessageAdapter( Context context ,List<MessageBean> list){
        this(context);
        this.list=list;

    }
    public MessageAdapter(Context context){
        this.context=context;
        layoutInflater = (LayoutInflater) LayoutInflater.from(context);
        options = ImageLoaderOptions.getListOptions();
    }
    public void setlist(List<MessageBean> list){
        this.list=list;
    }
    public void addlist(List<MessageBean> list){
        this.list.addAll(list);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    public void clearlist(){
        if (this.list!=null) {
            this.list.clear();
        }
    }
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = layoutInflater.inflate(R.layout.item_message, null);
            viewHolder=new ViewHolder();
            viewHolder.message_icon=(RoundImageView)convertView.findViewById(R.id.rv_message_icon);
            viewHolder.message_note=(TextView)convertView.findViewById(R.id.tv_messsage_note);
            viewHolder.message_content=(TextView)convertView.findViewById(R.id.tv_message_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final  MessageBean bean=list.get(position);
        if (bean!=null){
            if (bean.getAvatar_url()!=null) {
                imageLoader.displayImage(bean.getAvatar_url(), viewHolder.message_icon, options);
            }
            viewHolder.message_note.setText(bean.getNote());
            viewHolder.message_content.setText(bean.getMessage());
        }
        return convertView;
    }
    public class ViewHolder{
        public RoundImageView message_icon;
        public TextView message_note;
        public TextView message_content;
    }
}
