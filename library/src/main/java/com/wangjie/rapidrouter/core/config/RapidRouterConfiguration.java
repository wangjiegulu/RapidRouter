package com.wangjie.rapidrouter.core.config;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wangjie.rapidrouter.core.RapidRouterMapping;
import com.wangjie.rapidrouter.core.strategy.RapidRouterStrategy;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/10/17.
 */
public interface RapidRouterConfiguration {
    /**
     * 配置路由策略
     */
    @NonNull
    RapidRouterStrategy[] configRapidRouterStrategies();

    /**
     * 配置路由映射
     */
    @Nullable
    RapidRouterMapping[] configRapidRouterMappings();
}
