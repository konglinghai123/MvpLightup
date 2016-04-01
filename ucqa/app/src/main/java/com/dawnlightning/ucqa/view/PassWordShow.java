package com.dawnlightning.ucqa.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import com.dawnlightning.ucqa.R;

public class PassWordShow extends EditText {
	private final static String TAG = "EditTextWithDel";
	private Drawable imgInable;
	private Drawable imgAble;
	private Boolean isClick=false;
	@SuppressWarnings("unused")
	private Context mContext;

	public PassWordShow(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public PassWordShow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}

	public PassWordShow(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
	private void init() {
		imgAble = mContext.getResources().getDrawable(R.mipmap.passwshow);
		addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				setDrawable();
			}
		});
		setDrawable();
	}
	
	private void setDrawable() {
		if(length() < 1)
			setCompoundDrawablesWithIntrinsicBounds(null, null, imgInable, null);
		else
			setCompoundDrawablesWithIntrinsicBounds(null, null, imgAble, null);
	}
	
    @SuppressLint("ClickableViewAccessibility")
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        if (imgAble != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 50;
            if(!isClick){
            
            	if(rect.contains(eventX, eventY)) {
            		isClick=true;
            		setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            	}
            }else{
            	if(rect.contains(eventX, eventY)) {
            		isClick=false;
            		setTransformationMethod(PasswordTransformationMethod.getInstance());
            	}
            	
            }
            
            	
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
