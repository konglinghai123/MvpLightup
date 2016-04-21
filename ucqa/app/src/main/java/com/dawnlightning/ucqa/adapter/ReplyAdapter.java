package com.dawnlightning.ucqa.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dawnlightning.ucqa.Bean.CommentBean;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.tools.ImageLoaderOptions;
import com.dawnlightning.ucqa.view.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/4/9.
 */
public class ReplyAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private ViewHolder viewHolder;
    private LayoutInflater layoutInflater;

    public  ReplyAdapter( Context context, List<String> list){
        this.context=context;
        this.list=list;
        layoutInflater = (LayoutInflater) LayoutInflater.from(context);

    }
    public void setlist(List<String> list){
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
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
            convertView = layoutInflater.inflate(R.layout.item_reply, null);
            viewHolder=new ViewHolder();
            viewHolder.reply_msg=(TextView)convertView.findViewById(R.id.tv_reply_message);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String message=list.get(position);
        if (message!=null) {
           viewHolder.reply_msg.setText(message);
        }
        return convertView;

    }
    public class ViewHolder{
        public TextView reply_msg;

    }
}
