package com.gky.toucheventdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by 凯阳 on 2016/7/7.
 */
public class CustomerButton extends Button implements View.OnClickListener, View.OnTouchListener{

    private static final String TAG = "CustomerButton";

    private boolean isDispatch = false;

    private boolean isHandleEvent = true;

    private boolean isHandleTouch = false;

    public CustomerButton(Context context) {
        super(context);
    }

    public CustomerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomerButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.setOnClickListener(this);
        this.setOnTouchListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG,"dispatchTouchEvent::event:"+event);
        isDispatch = super.dispatchTouchEvent(event);
        Log.i(TAG, "diapatchTouchEvent:: super dispatch:"+isDispatch);
        return isDispatch;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG,"onTouchEvent::event:"+event);
        return isHandleEvent;
    }

    public void setDispatch(boolean dispatch) {
        isDispatch = dispatch;
    }

    public void setHandleEvent(boolean handleEvent) {
        isHandleEvent = handleEvent;
    }

    public void setHandleTouch(boolean handleTouch) {
        isHandleTouch = handleTouch;
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG,"onClick");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "onTouch::event:"+event);
        return isHandleTouch;
    }
}
