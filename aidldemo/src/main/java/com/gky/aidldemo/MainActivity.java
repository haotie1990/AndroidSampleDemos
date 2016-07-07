package com.gky.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gky.aidldemo.aidl.IDownLoader;
import com.gky.aidldemo.aidl.IDownLoaderListener;

public class MainActivity extends AppCompatActivity {

    private ServiceConnectionImpl mServiceConnection = new ServiceConnectionImpl();

    private DownLoaderListenerImpl mDownLoaderLister = new DownLoaderListenerImpl();

    private IDownLoader mDownLoader;

    private String url = "http://117.27.243.24/apk.r1.market.hiapk.com/data/upload/apkres/2016/7_1/14/com.tencent.mm_025831.apk";

    private ProgressBar mProgressBar;

    private TextView mDownInfo;

    private Button mBtStartDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mDownInfo = (TextView) findViewById(R.id.tv_downinfo);
        mBtStartDown = (Button) findViewById(R.id.bt_startdown);
        mBtStartDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDownLoader != null){
                    try {
                        mDownLoader.startDownLoader(url, mDownLoaderLister);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                mBtStartDown.setEnabled(false);
            }
        });

        bindService(new Intent(this, MainService.class), mServiceConnection, BIND_AUTO_CREATE);
    }

    private class DownLoaderListenerImpl extends IDownLoaderListener.Stub{


        @Override
        public void onProgress(final long curSize, final long totalSize) throws RemoteException {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int percent = (int) (curSize / totalSize);
                    mProgressBar.setProgress(percent);
                    String downInfo = String.format("%dMb/%dMb", ((curSize/1000)/1000),((totalSize/1000)/1000));
                    mDownInfo.setText(downInfo);
                    System.out.println(percent+"-"+downInfo);
                }
            });
        }

        @Override
        public void onSuccess() throws RemoteException {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "DownLoad Successfully.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onFailed(final String error) throws RemoteException {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "Down Error:"+error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class ServiceConnectionImpl implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownLoader = IDownLoader.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
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
}
