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
import com.dawnlightning.ucqa.base.Menu;

public class LeftMenuAdapter extends BaseAdapter{
	private List<Menu> menu;
	private Context context;
	private ViewHolder viewholder=null;
	public LeftMenuAdapter(Context context,List<Menu> list){
		this.menu=list;
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
			viewholder.update=(TextView)convertView.findViewById(R.id.tv_update_status);
			convertView.setTag(viewholder);
		}else{
			viewholder=(ViewHolder) convertView.getTag();
		}
		switch(position){
			case 0:
				viewholder.img.setBackgroundResource(R.mipmap.left_menu_1);
				viewholder.tv.setText(menu.get(position).getMenuname());
				if (menu.get(position).getStatus()==1){
					viewholder.update.setVisibility(View.VISIBLE);
				}else{
					viewholder.update.setVisibility(View.GONE);
				}
				break;
			case 1:
				viewholder.img.setBackgroundResource(R.mipmap.left_menu_2);
				viewholder.tv.setText(menu.get(position).getMenuname());
				if (menu.get(position).getStatus()==1){
					viewholder.update.setVisibility(View.VISIBLE);
				}else{
					viewholder.update.setVisibility(View.GONE);
				}
				break;
			case 2:
				viewholder.img.setBackgroundResource(R.mipmap.left_menu_3);
				viewholder.tv.setText(menu.get(position).getMenuname());
				if (menu.get(position).getStatus()==1){
					viewholder.update.setVisibility(View.VISIBLE);
				}else{
					viewholder.update.setVisibility(View.GONE);
				}
				break;
			case 3:
				viewholder.img.setBackgroundResource(R.mipmap.left_menu_4);
				viewholder.tv.setText(menu.get(position).getMenuname());
				if (menu.get(position).getStatus()==1){
					viewholder.update.setVisibility(View.VISIBLE);
				}else{
					viewholder.update.setVisibility(View.GONE);
				}
			break;
			case 4:
				viewholder.img.setBackgroundResource(R.mipmap.left_menu_5);
				viewholder.tv.setText(menu.get(position).getMenuname());
				if (menu.get(position).getStatus()==1){
					viewholder.update.setVisibility(View.VISIBLE);
				}else{
					viewholder.update.setVisibility(View.GONE);
				}
				break;
				
		
		}
	
		
		return convertView;
		
	}
	public class ViewHolder{
		public ImageView img;
		public TextView tv;
		public TextView update;
	}

	/*
	* 	menu.add(new Menu("主        页",0));
		menu.add(new Menu("我的消息",0));
		menu.add(new Menu("我的咨询",0));
		menu.add(new Menu("我要咨询",0));
		menu.add(new Menu("设        置",0));
	*
	* */
}
