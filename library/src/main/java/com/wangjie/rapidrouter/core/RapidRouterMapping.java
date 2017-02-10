package com.wangjie.rapidrouter.core;

import com.wangjie.rapidrouter.core.target.RouterTarget;

import java.util.HashMap;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/8/17.
 */
public abstract class RapidRouterMapping {

    protected HashMap<String, RouterTarget> getEnsureMap(HashMap<String, HashMap<String, RouterTarget>> routerMapper, String key) {
        HashMap<String, RouterTarget> map = routerMapper.get(key);
        if (null == map) {
            map = new HashMap<>();
            routerMapper.put(key, map);
        }
        return map;
    }

    public abstract HashMap<String, HashMap<String, RouterTarget>> calcSimpleRouterMapper(HashMap<String, HashMap<String, RouterTarget>> routerMapper);

    public abstract HashMap<String, RouterTarget> calcRegRouterMapper(HashMap<String, RouterTarget> routerMapper);

}
