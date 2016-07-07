package com.gky.toucheventdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by 凯阳 on 2016/7/7.
 */
public class CustomerLayout extends LinearLayout{

    private static final String TAG = "CustomerLayout";

    private boolean isDispatch = true;

    private boolean isIntercept = false;

    private boolean isHandleEvent = false;

    public CustomerLayout(Context context) {
        super(context);
    }

    public CustomerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent::event:"+ev);
        Log.i(TAG, "dispatchTouchEvent::super dispatch:"+super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setDispatch(boolean dispatch) {
        isDispatch = dispatch;
    }

    public void setHandleEvent(boolean handleEvent) {
        isHandleEvent = handleEvent;
    }

    public void setIntercept(boolean intercept) {
        isIntercept = intercept;
    }
}
