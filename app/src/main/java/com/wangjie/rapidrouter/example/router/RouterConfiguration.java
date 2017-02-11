package com.wangjie.rapidrouter.example.router;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wangjie.rapidrouter.api.annotations.RRConfig;
import com.wangjie.rapidrouter.core.RapidRouterMapping;
import com.wangjie.rapidrouter.core.config.RapidRouterConfiguration;
import com.wangjie.rapidrouter.core.strategy.RapidRouterStrategy;
import com.wangjie.rapidrouter.core.strategy.RapidRouterStrategyRegular;
import com.wangjie.rapidrouter.core.strategy.RapidRouterStrategySimple;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/9/17.
 */
@RRConfig(mappingName = "RapidRouterMappingHaquApp")
public class RouterConfiguration implements RapidRouterConfiguration {
    @NonNull
    @Override
    public RapidRouterStrategy[] configRapidRouterStrategies() {
        return new RapidRouterStrategy[]{
                new RapidRouterStrategySimple(),
                new RapidRouterStrategyRegular()
        };
    }

    @Nullable
    @Override
    public RapidRouterMapping[] configRapidRouterMappings() {
        return new RapidRouterMapping[]{
                new RapidRouterMappingHaquApp()
        };
    }
}
