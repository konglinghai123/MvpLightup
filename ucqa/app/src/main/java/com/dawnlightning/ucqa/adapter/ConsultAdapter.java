package com.dawnlightning.ucqa.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawnlightning.ucqa.Bean.ConsultMessageBean;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.tools.ImageLoaderOptions;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.dawnlightning.ucqa.view.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
public class ConsultAdapter extends BaseAdapter{
	private ViewHolder holder;
	private LayoutInflater layoutInflater;
	private Context context;
	private List<ConsultMessageBean> list;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	public ConsultAdapter(Context c, List<ConsultMessageBean> list) {
		super();
		this.context=c;
		this.list=list;
		layoutInflater = (LayoutInflater) LayoutInflater.from(c);
		options = ImageLoaderOptions.getListOptions();
	
	}
	
	public void setList(List<ConsultMessageBean> list){
		this.list = list;
	}
	//刷新时向最开头插入不重复数据
	//count为增加的条目数
	public int headinsert(List<ConsultMessageBean> list){
		int count=0;
		if(this.list.size()>0){
			for (ConsultMessageBean consultMessageBean:list){
				for (int i=0;i<this.list.size();i++){
					if(consultMessageBean.getBwztid().equals(this.list.get(i).getBwztid())){
						break;
					}else if(i==this.list.size()-1){
						this.list.add(0,consultMessageBean);
						count++;
					}else{
						break;
					}
				}
			}
		}else{
			setList(list);
			return  list.size();
		}

		return count;
	}
	public void addList(List<ConsultMessageBean> list){
		List<ConsultMessageBean> newlist=new ArrayList<ConsultMessageBean>();

		for (ConsultMessageBean bean:list) {
			if (!this.list.contains(bean)){
				newlist.add(bean);
			}
		}
		this.list.addAll(newlist);
 	}
	public void clearList(){
		this.list.clear();
 	}
	public List<ConsultMessageBean> getList(){
		return list;
	}
	public void removeItem(int position){
		if(list.size() > 0){
			list.remove(position);
		}
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

	@SuppressWarnings("static-access")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressLint({ "NewApi", "InflateParams" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = layoutInflater.inflate(R.layout.item_consult, null);
			holder = new ViewHolder();
			holder.suject=(TextView) convertView.findViewById(R.id.subject);
			holder.pic=(RoundImageView) convertView.findViewById(R.id.iv_consultpic);
			holder.message=(TextView) convertView.findViewById(R.id.message);
			holder.viewnum=(TextView) convertView.findViewById(R.id.numview);
			holder.replynum=(TextView) convertView.findViewById(R.id.numreply);
			holder.time=(TextView) convertView.findViewById(R.id.time);
			holder.status=(ImageView) convertView.findViewById(R.id.consult_status);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		  ConsultMessageBean item = list.get(position);
		
		if (null != item) {
			holder.suject.setText(item.getSubject());
			holder.message.setText(Html.fromHtml(item.getMessage()).toString());
			holder.viewnum.setText(item.getViewnum());
			holder.replynum.setText(item.getReplynum());
			holder.time.setText(item.getDateline());
			
			if(item.getAvatar_url().length()!=0&&item.getAvatar_url()!=null){
			
				holder.pic.setVisibility(View.VISIBLE);
				
				imageLoader.displayImage(item.getAvatar_url(), holder.pic, options);
				
			}else{
				holder.pic.setVisibility(holder.pic.GONE);
			
			}
			
			if(item.getStatus().contains("1")){
				holder.status.setVisibility(View.VISIBLE);

			}else{
				holder.status.setVisibility(View.GONE);
			}

		
			
			
		}
		return convertView;
	}
    private class ViewHolder {
    	TextView suject;
    	RoundImageView pic;
    	TextView message;
    	TextView viewnum;
    	TextView replynum;
    	TextView time;
    	ImageView status;

    }
}
