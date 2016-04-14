package com.dawnlightning.ucqa.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dawnlightning.ucqa.Bean.PicsBean;
import com.dawnlightning.ucqa.Bean.UploadPicsBean;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.activity.DisplayActivity;
import com.dawnlightning.ucqa.tools.ImageLoaderOptions;
import com.dawnlightning.ucqa.util.HttpConstants;
import com.dawnlightning.ucqa.view.ProcessImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2016/4/14.
 */
public class ConsultPicsAdapter extends BaseAdapter{
    private Context context;
    private List<UploadPicsBean> list;
    private ViewHolder viewHolder;
    private LayoutInflater layoutInflater;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private  DeletePicture  deletePicture;
    public ConsultPicsAdapter(Context context, List<UploadPicsBean> list){
        this.context=context;
        this.list=list;
        layoutInflater = (LayoutInflater) LayoutInflater.from(context);
        options = ImageLoaderOptions.getLoadPictureOptions();
    }
    public void setlist(List<UploadPicsBean> list){
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
    public void addlist(UploadPicsBean bean){
        this.list.add(bean);
    }
    public void  setDeletePicture(DeletePicture deletePicture){
        this.deletePicture=deletePicture;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (null == convertView) {
            convertView = layoutInflater.inflate(R.layout.item_consult_pics, null);
            viewHolder=new ViewHolder();
            viewHolder.img=(ProcessImageView)convertView.findViewById(R.id.piv_consult_picture);
            viewHolder.title=(EditText)convertView.findViewById(R.id.et_consult_picture_title);
            viewHolder.delete=(ImageView)convertView.findViewById(R.id.iv_consult_picture_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final  UploadPicsBean bean=list.get(position);
        if(bean!=null){
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
            imageLoader.displayImage("file://"+bean.getPicture().getPath(), viewHolder.img, options);
            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, DisplayActivity.class);
                    intent.putExtra("image", "file://" + bean.getPicture().getPath());
                    context.startActivity(intent);

                }
            });
            viewHolder.title.setText(bean.getPicturetitle());
            viewHolder.title.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                     bean.setPicturetitle(viewHolder.title.getText().toString());
                }
            });
            viewHolder.delete.setBackgroundResource(R.mipmap.ic_delete);
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deletePicture!=null){
                        deletePicture.Detele(position);
                    }
                }
            });
        }


        return convertView;

    }
    //
    public class ViewHolder{
        public ProcessImageView img;
        public TextView title;
        public ImageView delete;
    }
    public interface DeletePicture{
        public void Detele(int postion);
    }
    public interface AdapterRefreshListener{
        public void Refresh();
    }
}
