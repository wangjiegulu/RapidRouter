package com.wangjie.rapidrouter.core.strategy;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wangjie.rapidrouter.core.RapidRouterMapping;
import com.wangjie.rapidrouter.core.target.RouterTarget;

import java.util.HashMap;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/13/17.
 */
public class RapidRouterStrategyRESTful extends RapidRouterAbstractStrategy {

    /**
     * HashMap<{scheme}, HashMap<{host}, {router target}>>
     */
    private HashMap<String, HashMap<String, RouterTarget>> mapping;

    @Override
    public void onRapidRouterMappings(RapidRouterMapping[] rapidRouterMappings) {
        // ignore
    }

    @Nullable
    @Override
    protected RouterTarget findRouterTargetInternal(@NonNull Uri uri) {

        return null;
    }

    @Override
    public String parseParamFromUri(@NonNull Uri uri, @NonNull String paramKey) {
//        uri.getPathSegments();

        return null;
    }

}
