package com.wangjie.rapidrouter.example.router;

import android.util.Log;

import com.wangjie.rapidrouter.core.RouterStuff;
import com.wangjie.rapidrouter.core.listener.OnRapidRouterListener;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/8/17.
 */
public class ThisOnRapidRouterListener implements OnRapidRouterListener {
    private static final String TAG = ThisOnRapidRouterListener.class.getSimpleName();

    @Override
    public void onRouterTargetNotFound(RouterStuff routerStuff) {
        Log.i(TAG, "[onRouterTargetNotFound]: " + routerStuff);
    }

    @Override
    public void onRouterError(RouterStuff routerStuff, Throwable throwable) {
        Log.i(TAG, "[onRouterError]: " + routerStuff + ", throwable: " + throwable);
    }

    @Override
    public void onRouterGoBefore(RouterStuff routerStuff) {
        Log.i(TAG, "[onRouterToBefore]: " + routerStuff);
    }

    @Override
    public boolean onRouterGoAround(RouterStuff routerStuff) {
        Log.i(TAG, "[onRouterToAround]: " + routerStuff);
        return false;
    }

    @Override
    public void onRouterGoAfter(RouterStuff routerStuff) {
        Log.i(TAG, "[onRouterToAfter]: " + routerStuff);
    }
}
