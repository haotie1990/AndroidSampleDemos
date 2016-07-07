package com.gky.aidldemo;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;

import com.gky.aidldemo.aidl.IDownLoader;
import com.gky.aidldemo.aidl.IDownLoaderListener;

public class MainService extends Service {

    private DownloadManager mDownLoadManager;

    private long mTaskId;

    private IDownLoaderListener mListener;

    public MainService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mDownLoadManager = (DownloadManager) getApplicationContext().getSystemService(DOWNLOAD_SERVICE);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new DownLoaderImpl();
    }

    private class DownLoadReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(mTaskId);
            Cursor cursor = mDownLoadManager.query(query);
            if(cursor.moveToFirst()){
                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                switch (status){
                    case DownloadManager.STATUS_RUNNING:
                        long totalSize = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                        long curSize = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        try {
                            mListener.onProgress(curSize, totalSize);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    case DownloadManager.STATUS_PAUSED:
                    case DownloadManager.STATUS_PENDING:
                    case DownloadManager.STATUS_FAILED:
                        String reson = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_REASON));
                        try {
                            mListener.onFailed(reson);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:
                        try {
                            mListener.onSuccess();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }
    }

    private class DownLoaderImpl extends IDownLoader.Stub{

        @Override
        public boolean startDownLoader(String path, IDownLoaderListener listener) throws RemoteException {
            mListener = listener;
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(path));
            request.setAllowedOverRoaming(false);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            request.setVisibleInDownloadsUi(false);
            request.setDestinationInExternalPublicDir("/download/","");
            mTaskId = mDownLoadManager.enqueue(request);
            getApplicationContext().registerReceiver(new DownLoadReceiver(), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            return true;
        }

        @Override
        public boolean pauseDownLoader(String path) throws RemoteException {
            return false;
        }

        @Override
        public boolean cancelDownLoader(String path) throws RemoteException {
            mDownLoadManager.remove(mTaskId);
            return false;
        }
    }
}
