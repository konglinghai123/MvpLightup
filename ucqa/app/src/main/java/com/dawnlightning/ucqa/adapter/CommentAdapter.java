package com.dawnlightning.ucqa.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dawnlightning.ucqa.Bean.CommentBean;
import com.dawnlightning.ucqa.Bean.PicsBean;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.dialog.ReplyDialog;
import com.dawnlightning.ucqa.tools.ImageLoaderOptions;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.dawnlightning.ucqa.util.LvHeightUtil;
import com.dawnlightning.ucqa.view.ExpandListView;
import com.dawnlightning.ucqa.view.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/9.
 */
public class CommentAdapter extends BaseAdapter{
    private Context context;
    private List<CommentBean> list;
    private ViewHolder viewHolder;
    private LayoutInflater layoutInflater;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private ReplyAdapter replyAdapter;
    private IReply replylistener;
    public void setreplylistener(IReply replylistener){
        this.replylistener=replylistener;
    }
    public  CommentAdapter( Context context ,List<CommentBean> list,IReply replylistener){
        this.context=context;
        this.list=list;
        this.replylistener=replylistener;
        layoutInflater = (LayoutInflater) LayoutInflater.from(context);
        options = ImageLoaderOptions.getListOptions();
    }
    public void setlist(List<CommentBean> list){
        this.list=list;
    }
    public List<CommentBean> addlist(CommentBean bean){
        this.list.add(bean);
        return this.list;
    }
    public void  addall(List<CommentBean> beans){
        this.list.addAll(beans);
    }
    public void replacecommentbean(CommentBean bean,int postion){
        list.get(postion).setReplylist(bean.getReplylist());
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (null == convertView) {
            convertView = layoutInflater.inflate(R.layout.item_consult_comment, null);
            viewHolder=new ViewHolder();
            viewHolder.comment_msg=(TextView)convertView.findViewById(R.id.tv_comment_message);
            viewHolder.comment_time=(TextView)convertView.findViewById(R.id.tv_comment_time);
            viewHolder.comment_icon=(RoundImageView)convertView.findViewById(R.id.iv_comment_icon);
            viewHolder.comment_replylist=(ExpandListView)convertView.findViewById(R.id.lv_comment_reply);
            viewHolder.comment_reply=(TextView)convertView.findViewById(R.id.tv_comment_reply);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final CommentBean bean=list.get(position);
        if (bean!=null) {
            String username="";
            if (bean.getName().length()>0){
                username=bean.getName();
            }else{
                username=bean.getAuthor();
            }
            imageLoader.displayImage(bean.getAvatar_url(), viewHolder.comment_icon, options);
            viewHolder.comment_msg.setText(username + ": " + bean.getMessage());
            viewHolder.comment_time.setText(bean.getDateline());
            if (bean.getReplylist()!=null) {
                if (bean.getReplylist().size() > 0) {
                    replyAdapter = new ReplyAdapter(context, bean.getReplylist());
                    viewHolder.comment_replylist.setAdapter(replyAdapter);
                    replyAdapter.notifyDataSetChanged();
                }
            }
            viewHolder.comment_reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (replylistener!=null){
                        replylistener.doReply(bean,position);
                    }


                }
            });
        }
        return convertView;

    }
    public class ViewHolder{
        public TextView comment_msg;
        public TextView comment_time;
        public RoundImageView comment_icon;
        public ExpandListView comment_replylist;
        public TextView comment_reply;
    }
    public interface IReply{
        public void doReply(final CommentBean bean,int postion);
    }
}
