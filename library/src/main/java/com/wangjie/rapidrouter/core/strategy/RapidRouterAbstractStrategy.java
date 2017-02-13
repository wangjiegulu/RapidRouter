package com.wangjie.rapidrouter.core.strategy;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wangjie.rapidrouter.core.RapidRouterMapping;
import com.wangjie.rapidrouter.core.target.RouterTarget;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/10/17.
 */
public abstract class RapidRouterAbstractStrategy implements RapidRouterStrategy {
    @Override
    public void onRapidRouterMappings(RapidRouterMapping[] rapidRouterMappings) {
        // ignore
    }

    @Nullable
    @Override
    public final RouterTarget findRouterTarget(@NonNull Uri uri) {
        RouterTarget routerTarget = findRouterTargetInternal(uri);
        if (null != routerTarget) {
            routerTarget.setRouterStrategy(this);
        }
        return routerTarget;
    }

    @Nullable
    protected abstract RouterTarget findRouterTargetInternal(@NonNull Uri uri);
}
