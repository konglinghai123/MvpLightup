package com.dawnlightning.ucqa.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawnlightning.ucqa.Bean.PicsBean;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.activity.DisplayActivity;
import com.dawnlightning.ucqa.tools.ImageLoaderOptions;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/4/8.
 */
public class DetailPicsAdapter extends BaseAdapter {

    private Context context;
    private List<PicsBean> list;
    private ViewHolder viewHolder;
    private  LayoutInflater layoutInflater;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    public DetailPicsAdapter(Context context, List<PicsBean> list){
        this.context=context;
        this.list=list;
        layoutInflater = (LayoutInflater) LayoutInflater.from(context);
        options = ImageLoaderOptions.getLoadPictureOptions();
    }
    public void setlist(List<PicsBean> list){
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
            convertView = layoutInflater.inflate(R.layout.item_detail_picslist, null);
            viewHolder=new ViewHolder();
            viewHolder.img=(ImageView)convertView.findViewById(R.id.iv_item_pic);
            viewHolder.title=(TextView)convertView.findViewById(R.id.tv_item_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final  PicsBean bean=list.get(position);
        if (bean.getUrl()!=null) {
//            imageLoader.displayImage(HttpConstants.HTTP_HEAD+HttpConstants.HTTP_IP+bean.getUrl(),viewHolder.img, options,new SimpleImageLoadingListener(){
//
//                @Override
//                public void onLoadingComplete(String imageUri, View view,
//                                              Bitmap loadedImage) {
//
//                    super.onLoadingComplete(imageUri, view, loadedImage);
//                    Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(loadedImage,120,80);
//                    viewHolder.img.setImageBitmap(resizeBmp);
//
//                }
//
//            });
           imageLoader.displayImage(HttpConstants.HTTP_HEAD+HttpConstants.HTTP_IP+bean.getUrl(), viewHolder.img, options);
            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, DisplayActivity.class);
                    intent.putExtra("image", HttpConstants.HTTP_HEAD + HttpConstants.HTTP_IP + bean.getUrl());
                    context.startActivity(intent);

                }
            });
            viewHolder.title.setText(bean.getTitle());
        }

        return convertView;

    }
    public class ViewHolder{
        public ImageView img;
        public TextView title;
    }
}
