package com.wangjie.rapidrouter.core.strategy;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wangjie.rapidrouter.core.RapidRouterMapping;
import com.wangjie.rapidrouter.core.target.RouterTarget;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/10/17.
 */
public interface RapidRouterStrategy {
    
    void onRapidRouterMappings(RapidRouterMapping[] rapidRouterMappings);

    @Nullable
    RouterTarget findRouterTarget(@NonNull Uri uri);

    String parseParamFromUri(@NonNull Uri uri, @NonNull String paramKey);

}
