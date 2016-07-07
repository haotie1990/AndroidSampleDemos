package com.gky.toucheventdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.CompoundButton;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private CustomerLayout customerLy;

    private CustomerButton customerBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((SwitchCompat)findViewById(R.id.customLy_isHandleEvent)).setOnCheckedChangeListener(this);
        ((SwitchCompat)findViewById(R.id.customLy_isDispatch)).setOnCheckedChangeListener(this);
        ((SwitchCompat)findViewById(R.id.customLy_isIntercept)).setOnCheckedChangeListener(this);
        ((SwitchCompat)findViewById(R.id.customBt_isDispatch)).setOnCheckedChangeListener(this);
        ((SwitchCompat)findViewById(R.id.customBt_isHandleEvent)).setOnCheckedChangeListener(this);
        ((SwitchCompat)findViewById(R.id.customBt_isHandleTouch)).setOnCheckedChangeListener(this);

        customerLy = (CustomerLayout) findViewById(R.id.customLy);
        customerBt = (CustomerButton) findViewById(R.id.customBt);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent::event:"+ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent::event:"+event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int viewId = buttonView.getId();
        switch (viewId){
            case R.id.customLy_isDispatch:
                customerLy.setDispatch(isChecked);
                break;
            case R.id.customLy_isIntercept:
                customerLy.setIntercept(isChecked);
                break;
            case R.id.customLy_isHandleEvent:
                customerLy.setHandleEvent(isChecked);
                break;
            case R.id.customBt_isDispatch:
                customerBt.setDispatch(isChecked);
                break;
            case R.id.customBt_isHandleTouch:
                customerBt.setHandleTouch(isChecked);
                break;
            case R.id.customBt_isHandleEvent:
                customerBt.setHandleEvent(isChecked);
                break;
            default:
                break;
        }
    }
}
