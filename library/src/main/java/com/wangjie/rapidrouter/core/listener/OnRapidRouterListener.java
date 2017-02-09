package com.wangjie.rapidrouter.core.listener;

import com.wangjie.rapidrouter.core.RouterStuff;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/9/17.
 */
public interface OnRapidRouterListener {

    void onRouterError(RouterStuff routerStuff, Throwable throwable);

    void onRouterGoAfter(RouterStuff routerStuff);

    boolean onRouterGoAround(RouterStuff routerStuff);

    void onRouterGoBefore(RouterStuff routerStuff);

    void onRouterTargetNotFound(RouterStuff routerStuff);
}
