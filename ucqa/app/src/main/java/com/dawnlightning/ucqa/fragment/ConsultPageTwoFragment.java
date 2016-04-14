package com.dawnlightning.ucqa.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dawnlightning.ucqa.Bean.PicsBean;
import com.dawnlightning.ucqa.Bean.UploadPicsBean;
import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.activity.ConsultActivity;
import com.dawnlightning.ucqa.adapter.ConsultPicsAdapter;
import com.dawnlightning.ucqa.adapter.DetailPicsAdapter;
import com.dawnlightning.ucqa.util.SdCardUtil;
import com.dawnlightning.ucqa.util.TimeUtil;
import com.dawnlightning.ucqa.view.ExpandListView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class ConsultPageTwoFragment extends Fragment{
    private ExpandListView lv_pic;
    private View headview;
    private View footerview;
    private ConsultPicsAdapter consultPicsAdapter;
    private ImageView iv_consult_sentvoice;
    private ImageView iv_consult_sentphotos;
    private ImageView iv_consult_sentcamera;
    private EditText et_consult_message;
    private EditText et_consult_subject;
    private ConsultActivity consultActivity;
    private  List<UploadPicsBean> list=new ArrayList<UploadPicsBean>();
    String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
    List<Bitmap> allImages = new ArrayList<Bitmap>();
    List<String> imageUrl=new ArrayList<String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_condition, container,false);
        lv_pic=( ExpandListView)view.findViewById(R.id.lv_consult_pic);
        et_consult_subject=(EditText)view.findViewById(R.id.et_consult_subject);


        consultPicsAdapter =new ConsultPicsAdapter(getActivity(),list);

        headview= LayoutInflater.from(getActivity()).inflate(R.layout.consult_girdview_head,null);
        et_consult_message=(EditText)headview.findViewById(R.id.et_consult_message);

        //footerview= LayoutInflater.from(getActivity()).inflate(R.layout.consult_girdview_footer,null);
        iv_consult_sentvoice=(ImageView)view.findViewById(R.id.iv_consult_sentvoice);
        iv_consult_sentphotos=(ImageView)view.findViewById(R.id.iv_consult_sentphotos);
        iv_consult_sentcamera=(ImageView)view.findViewById(R.id.iv_consult_sentcamera);


        lv_pic.addHeaderView(headview);
        //lv_pic.addFooterView(footerview);
        lv_pic.setAdapter(consultPicsAdapter);
        iv_consult_sentphotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageForAlbum();
            }
        });
        iv_consult_sentcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromCamera();
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        consultActivity=(ConsultActivity)activity;
    }

    private void  getImageForAlbum(){

        if(SdCardUtil.checkSdCard()==true){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");//相片类型
            this.startActivityForResult(intent, 7);
        }else{
            Toast.makeText(getActivity(), "SD卡不存在", Toast.LENGTH_LONG).show();
        }
    }

    private void getImageFromCamera()
    {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName));
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        this.startActivityForResult(openCameraIntent, 1);
    }

    public void getImaged(Intent data){

        Uri uri = null;
        if (data != null) {
            uri = data.getData();
            System.out.println("Data");
        }else{

            uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName));
        }
        //imageUrl.add(uri.getPath());
        Bitmap photo = null;


        if (uri != null) {
            photo = BitmapFactory.decodeFile(uri.getPath());

        }

        if(photo!=null){

            //gridAdapter.notifyDataSetChanged();

            saveImageToFile(photo);
        }

    }
    //截取图片
    public void cropImage(Uri uri, int outputX, int outputY, int requestCode){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 3);
        intent.putExtra("aspectY", 3);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        this.startActivityForResult(intent, requestCode);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            Log.e("requestcode", String.valueOf(requestCode));
            Log.e("result",String.valueOf(requestCode));
            if (SdCardUtil.checkSdCard() == true) {
                if (requestCode == 1  && resultCode==getActivity().RESULT_OK) {
                    getImaged(data);
                } else if (requestCode == 2 && resultCode==getActivity().RESULT_OK) {
                    getImaged(data);
                } else if (requestCode == 3 && resultCode==getActivity().RESULT_OK) {
                    getImaged(data);
                } else if (requestCode == 4 && resultCode==getActivity().RESULT_OK) {
                    getImaged(data);
                } else if (requestCode == 5 && resultCode==getActivity().RESULT_OK) {
                    getImaged(data);
                } else if (requestCode == 6 && resultCode==getActivity().RESULT_OK) {
                    getImaged(data);
                }else if(requestCode==7 && resultCode==getActivity().RESULT_OK){
                    ContentResolver resolver = getActivity().getContentResolver();
                    Uri uri=data.getData();
                    Bitmap bm=null;
                    if(uri!=null){
                        try {
                            bm = MediaStore.Images.Media.getBitmap(resolver, uri);
                        } catch (IOException e) {
                            // TODO 自动生成的 catch 块
                            e.printStackTrace();
                        }

                    }
                    if(bm!=null){
                        //Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(bm, 88, 88);
                        //allImages.add(allImages.size()-1,resizeBmp);
                        //gridAdapter.notifyDataSetChanged();
                        Log.e("consult","准备存储图片");
                        saveImageToFile(bm);
                    }
                } else if(requestCode==8 && resultCode==getActivity().RESULT_OK){
                    /**
                     裁剪完图片
                     */
                    Bitmap photo = null;
                    Uri photoUri = data.getData();

                    if (photoUri != null) {
                        photo = BitmapFactory.decodeFile(photoUri.getPath());

                    }
                    if (photo == null) {
                        Bundle extra = data.getExtras();
                        if (extra != null) {
                            photo = (Bitmap)extra.get("data");
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        }
                    }
                    if(photo!=null){

                        //Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(photo, 88, 88);
                        // allImages.add(allImages.size()-1,resizeBmp);
                        Log.e("ERROR", "传入成功");
                        //gridAdapter.notifyDataSetChanged();
                        saveImageToFile(photo);
                    }
                }
            } else {
                Toast.makeText(getActivity(), "SD卡不存在,请检查您的sd卡",
                        Toast.LENGTH_LONG).show();
            }

    }

    public void saveImageToFile(Bitmap bitmap){
        SdCardUtil.createFileDir(SdCardUtil.FILEDIR + "/" + SdCardUtil.FILEPHOTO+"/");
        //FileOutputStream fos = null;
        String fileName=SdCardUtil.getSdPath()+SdCardUtil.FILEDIR+"/"+SdCardUtil.FILEPHOTO+"/"+ TimeUtil.getCurrentTimeForImage();

        if(fileName.length()>0){
            imageUrl.add(fileName);
        }
        Log.e("filename", fileName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 200) {
            baos.reset();
            options -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(baos.toByteArray());
            fos.flush();
            if (bitmap != null) {
                bitmap.recycle();
            }
            UploadPicsBean bean=new UploadPicsBean();
            bean.setM_auth(consultActivity.userBean.getM_auth());
            bean.setPicturetitle("");
            bean.setPicture(new File(fileName));
            bean.setPictureid(imageUrl.size());
            bean.setUid(consultActivity.userBean.getUserdata().getUid());
            consultPicsAdapter.addlist(bean);
            consultPicsAdapter.notifyDataSetChanged();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
