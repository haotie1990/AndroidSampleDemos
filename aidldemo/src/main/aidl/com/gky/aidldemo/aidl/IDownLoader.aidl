// IDownLoader.aidl
package com.gky.aidldemo.aidl;

import com.gky.aidldemo.aidl.IDownLoaderListener;

// Declare any non-default types here with import statements

interface IDownLoader {

    boolean startDownLoader(String path, IDownLoaderListener listener);

    boolean pauseDownLoader(String path);

    boolean cancelDownLoader(String path);
}
