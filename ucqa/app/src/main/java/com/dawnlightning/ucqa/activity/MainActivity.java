package com.dawnlightning.ucqa.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.dawnlightning.ucqa.R;
import com.dawnlightning.ucqa.adapter.LeftMenuAdapter;
import com.dawnlightning.ucqa.base.BaseActivity;
import com.dawnlightning.ucqa.view.DragLayout;
import com.nineoldandroids.view.ViewHelper;

import java.util.Random;

import me.maxwin.view.XListView;

public class MainActivity extends BaseActivity {
    private DragLayout dl_main;
    private ListView lv_menu;
    private TextView tv_title;
    private ImageView iv_menu, iv_icon;
    private XListView mListview=null;
    private LeftMenuAdapter menuadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void initview() {
        dl_main = (DragLayout) findViewById(R.id.dl_main);
        lv_menu=(ListView)findViewById(R.id.lv_menu);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        tv_title = (TextView) findViewById(R.id.title);
        mListview=(XListView) findViewById(R.id.lv_consult);
        dl_main.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {
                lv_menu.smoothScrollToPosition(new Random().nextInt(30));
            }

            @Override
            public void onClose() {
                //shake();
            }

            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(iv_menu, 1 - percent);
            }
        });

        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dl_main.open();
            }
        });
        menuadapter=new LeftMenuAdapter(MainActivity.this);
        menuadapter=new LeftMenuAdapter(MainActivity.this);
        lv_menu.setAdapter(menuadapter);
        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                //Util.t(getApplicationContext(), "click " + position);
                switch (position) {
                    case 0:
                        break;

                    default:
                        break;
                }

            }
        });
    }

        @Override
    public void initdata() {

    }

}
