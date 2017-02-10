package com.wangjie.rapidrouter.core.strategy;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wangjie.rapidrouter.core.RapidRouterMapping;
import com.wangjie.rapidrouter.core.target.RouterTarget;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/10/17.
 */
public class RapidRouterStrategyRegular extends RapidRouterAbstractStrategy {
    /**
     * HashMap<{uri reg}, {router target}>
     */
    private HashMap<String, RouterTarget> mapping;

    @Override
    public void onRapidRouterMappings(RapidRouterMapping[] rapidRouterMappings) {
        HashMap<String, RouterTarget> result = new HashMap<>();
        for (RapidRouterMapping mapping : rapidRouterMappings) {
            mapping.calcRegRouterMapper(result);
        }
        mapping = result;
    }

    @Nullable
    @Override
    public RouterTarget findRouterTarget(@NonNull Uri uri) {
        if (null == mapping) {
            return null;
        }
        for (Map.Entry<String, RouterTarget> entry : mapping.entrySet()) {
            if (uri.toString().matches(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
