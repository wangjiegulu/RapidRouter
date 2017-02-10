package com.wangjie.rapidrouter.example;

import com.wangjie.rapidrouter.core.RapidRouterMapping;
import com.wangjie.rapidrouter.core.target.RouterTarget;
import com.wangjie.rapidrouter.example.activity.AActivity;
import com.wangjie.rapidrouter.example.activity.BActivity;
import com.wangjie.rapidrouter.example.activity.CActivity;

import java.util.HashMap;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/8/17.
 */
public class ThisRapidRouterMapping extends RapidRouterMapping {
    @Override
    public HashMap<String, HashMap<String, RouterTarget>> calcRouterMapper(HashMap<String, HashMap<String, RouterTarget>> routerMapper) {
        HashMap<String, Class> params;

        // com.wangjie.rapidrouter.example.activity.AActivity
        params = new HashMap<>();
        params.put("p_name", String.class);
        params.put("p_age", int.class);
        getEnsureMap(routerMapper, "rr").put("rapidrouter.a", new RouterTarget(AActivity.class, params));

        // com.wangjie.rapidrouter.example.activity.AActivity
        params = new HashMap<>();
        params.put("p_name", String.class);
        params.put("p_age", int.class);
        getEnsureMap(routerMapper, "rr").put("rapidrouter_extra.a", new RouterTarget(AActivity.class, params));

        // com.wangjie.rapidrouter.example.activity.BActivity
        params = new HashMap<>();
        params.put("id", long.class);
        getEnsureMap(routerMapper, "rr").put("rapidrouter.b", new RouterTarget(BActivity.class, params));

        // com.wangjie.rapidrouter.example.activity.CActivity
        getEnsureMap(routerMapper, "rr").put("rapidrouter.c", new RouterTarget(CActivity.class, null));
        return routerMapper;
    }

    @Override
    public HashMap<String, RouterTarget> calcRegRouterMapper(HashMap<String, RouterTarget> routerMapper) {



        return null;
    }
}
