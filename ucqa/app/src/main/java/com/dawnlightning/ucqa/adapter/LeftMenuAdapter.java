package com.dawnlightning.ucqa.adapter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawnlightning.ucqa.R;

public class LeftMenuAdapter extends BaseAdapter{
	private List<String> menu=new ArrayList<String>();
	private Context context;
	private ViewHolder viewholder=null;
	public LeftMenuAdapter(Context context){
		menu.add("主        页");
		menu.add("我的消息");
		menu.add("我的咨询");
		menu.add("我要咨询");
		menu.add("设        置");
	    this.context=context;
	}
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return menu.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return menu.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.left_menu_item, null);
			viewholder=new ViewHolder();
			viewholder.img=(ImageView) convertView.findViewById(R.id.left_menu_img);
			viewholder.tv=(TextView)convertView.findViewById(R.id.left_menu_tv);
			convertView.setTag(viewholder);
		}else{
			viewholder=(ViewHolder) convertView.getTag();
		}
		switch(position){
			case 0:
				viewholder.img.setBackgroundResource(R.mipmap.left_menu_1);
				viewholder.tv.setText(menu.get(position));
				break;
			case 1:
				viewholder.img.setBackgroundResource(R.mipmap.left_menu_2);
				viewholder.tv.setText(menu.get(position));
				break;
			case 2:
				viewholder.img.setBackgroundResource(R.mipmap.left_menu_3);
				viewholder.tv.setText(menu.get(position));
				break;
			case 3:
				viewholder.img.setBackgroundResource(R.mipmap.left_menu_4);
				viewholder.tv.setText(menu.get(position));
				break;
			case 4:
				viewholder.img.setBackgroundResource(R.mipmap.left_menu_5);
				viewholder.tv.setText(menu.get(position));
				break;
				
		
		}
	
		
		return convertView;
		
	}
	public class ViewHolder{
		public ImageView img;
		public TextView tv;
	}

}
