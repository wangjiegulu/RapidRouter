package com.wangjie.rapidrouter.example;

import com.wangjie.rapidrouter.core.RapidRouterConfig;
import com.wangjie.rapidrouter.core.target.RouterTarget;
import com.wangjie.rapidrouter.example.activity.AActivity;
import com.wangjie.rapidrouter.example.activity.CActivity;

import java.util.HashMap;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/8/17.
 */
public class ThisRapidRouterConfig extends RapidRouterConfig {
    @Override
    public HashMap<String, HashMap<String, RouterTarget>> calcRouterMapper(HashMap<String, HashMap<String, RouterTarget>> routerMapper) {

        HashMap<String, RouterTarget> ensureMap = getEnsureMap(routerMapper, "rr");

        HashMap<String, Class> params = new HashMap<>();
        params.put("p_name", String.class);
        params.put("p_age", int.class);
        ensureMap.put("rapidrouter.a", new RouterTarget(AActivity.class, params));

        ensureMap.put("rapidrouter.c", new RouterTarget(CActivity.class, null));

        return routerMapper;
    }
}
