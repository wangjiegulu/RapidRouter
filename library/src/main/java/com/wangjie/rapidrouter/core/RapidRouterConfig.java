package com.wangjie.rapidrouter.core;

import com.wangjie.rapidrouter.core.target.RouterTarget;

import java.util.HashMap;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/8/17.
 */
public abstract class RapidRouterConfig {

    public static HashMap<String, HashMap<String, RouterTarget>> calcAll(RapidRouterConfig... rapidRouterConfigs) {
        HashMap<String, HashMap<String, RouterTarget>> result = new HashMap<>();
        for (RapidRouterConfig rapidRouterConfig : rapidRouterConfigs) {
            rapidRouterConfig.calcRouterMapper(result);
        }
        return result;
    }

//    private <K, K1, V1> HashMap<K1, V1> autoMap(HashMap<K, HashMap<K1, V1>> map, K key){
//        map.get()
//    }

    protected HashMap<String, RouterTarget> getEnsureMap(HashMap<String, HashMap<String, RouterTarget>> routerMapper, String key) {
        HashMap<String, RouterTarget> map = routerMapper.get(key);
        if (null == map) {
            map = new HashMap<>();
            routerMapper.put(key, map);
        }
        return map;
    }

    public abstract HashMap<String, HashMap<String, RouterTarget>> calcRouterMapper(HashMap<String, HashMap<String, RouterTarget>> routerMapper);
}
