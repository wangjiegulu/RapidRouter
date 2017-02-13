package com.wangjie.rapidrouter.core.target;

import com.wangjie.rapidrouter.core.strategy.RapidRouterStrategy;

import java.util.HashMap;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/8/17.
 */
public class RouterTarget {
    private RapidRouterStrategy routerStrategy;
    private Class targetClass;
    private HashMap<String, Class> params;

    public RouterTarget(Class targetClass, HashMap<String, Class> params) {
        this.targetClass = targetClass;
        this.params = params;
    }

    public RapidRouterStrategy getRouterStrategy() {
        return routerStrategy;
    }

    public void setRouterStrategy(RapidRouterStrategy routerStrategy) {
        this.routerStrategy = routerStrategy;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
    }

    public HashMap<String, Class> getParams() {
        return params;
    }

    public void setParams(HashMap<String, Class> params) {
        this.params = params;
    }
}
