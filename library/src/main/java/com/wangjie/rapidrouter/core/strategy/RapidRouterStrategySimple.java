package com.wangjie.rapidrouter.core.strategy;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wangjie.rapidrouter.core.RapidRouterMapping;
import com.wangjie.rapidrouter.core.target.RouterTarget;

import java.util.HashMap;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/10/17.
 */
public class RapidRouterStrategySimple extends RapidRouterAbstractStrategy {
    /**
     * HashMap<{scheme}, HashMap<{host}, {router target}>>
     */
    private HashMap<String, HashMap<String, RouterTarget>> mapping;

    @Override
    public void onRapidRouterMappings(RapidRouterMapping[] rapidRouterMappings) {
        HashMap<String, HashMap<String, RouterTarget>> result = new HashMap<>();
        for (RapidRouterMapping mapping : rapidRouterMappings) {
            mapping.calcRouterMapper(result);
        }
        mapping = result;
    }

    @Nullable
    @Override
    public RouterTarget findRouterTarget(@NonNull Uri uri) {
        if (null == mapping) {
//            throw new RapidRouterIllegalException("Maps not set in SimpleRouterStrategy.");
            return null;
        }
        HashMap<String, RouterTarget> schemeMapper = mapping.get(uri.getScheme());
        if (null != schemeMapper) {
            return schemeMapper.get(uri.getHost());
        }
        return null;
    }
}
