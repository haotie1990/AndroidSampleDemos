// IDownLoaderListener.aidl
package com.gky.aidldemo.aidl;

// Declare any non-default types here with import statements

interface IDownLoaderListener {

    void onProgress(long curSize, long totalSize);

    void onSuccess();

    void onFailed(String error);
}
