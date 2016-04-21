package com.dawnlightning.ucqa.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 支持ListView 嵌套子listview工具类
 * 
 * @author Administrator
 * 
 */
public class LvHeightUtil {
	public static void setListViewHeightBasedOnChildren(GridView listView,int numcol) {
		// 获取listview的adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int spaceheight=10;
		// 固定列宽，有多少列
		int col =numcol;
		int totalHeight = 0;
		// i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
		// listAdapter.getCount()小于等于8时计算两次高度相加
		for (int i = 0; i < listAdapter.getCount(); i += col) {
			// 获取listview的每一个item
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			spaceheight=spaceheight+10;
			// 获取item的高度和
			totalHeight += listItem.getMeasuredHeight()+spaceheight;
		}

		// 获取listview的布局参数
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		// 设置高度
		params.height = totalHeight;
		// 设置margin
		((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		// 设置参数
		listView.setLayoutParams(params);
	}
	public static void setListViewHeightBasedOnChildren(ListView listView,int addedrow) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}
		int itemheight=0;
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
			itemheight=listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()))+addedrow*itemheight;

		listView.setLayoutParams(params);
	}
	/**
	 * 得到设备屏幕的宽度
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 得到设备屏幕的高度
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 得到设备的密度
	 */
	public static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	/**
	 * 把密度转换为像素
	 */
	public static int dip2px(Context context, float px) {
		final float scale = getScreenDensity(context);
		return (int) (px * scale + 0.5);
	}
}
