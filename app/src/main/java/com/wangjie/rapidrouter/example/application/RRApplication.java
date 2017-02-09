package com.wangjie.rapidrouter.example.application;

import android.app.Application;

import com.wangjie.rapidrouter.core.RapidRouter;
import com.wangjie.rapidrouter.core.RapidRouterConfig;
import com.wangjie.rapidrouter.example.router.RapidRouterConfigHaquApp;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/8/17.
 */
public class RRApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RapidRouter.init(RapidRouterConfig.calcAll(new RapidRouterConfigHaquApp()));
        RapidRouter.setOnRapidRouterListener(new ThisOnRapidRouterListener());
    }
}
