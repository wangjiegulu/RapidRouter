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
    public HashMap<String, RouterTarget> calcSimpleRouterMapper(HashMap<String, RouterTarget> routerMapper) {
        HashMap<String, Class> params;
        // com.wangjie.rapidrouter.example.activity.AActivity
        params = new HashMap<>(2, 1F);
        params.put("p_name", String.class);
        params.put("p_age", int.class);
        routerMapper.put("rr://rapidrouter.a", new RouterTarget(AActivity.class, params));
        // com.wangjie.rapidrouter.example.activity.AActivity
        params = new HashMap<>(2, 1F);
        params.put("p_name", String.class);
        params.put("p_age", int.class);
        routerMapper.put("rr://rapidrouter.a", new RouterTarget(AActivity.class, params));
        // com.wangjie.rapidrouter.example.activity.BActivity
        params = new HashMap<>(1, 1F);
        params.put("id", long.class);
        routerMapper.put("rr://rapidrouter.b", new RouterTarget(BActivity.class, params));
        return routerMapper;
    }

    @Override
    public HashMap<String, RouterTarget> calcRegRouterMapper(HashMap<String, RouterTarget> routerMapper) {
        HashMap<String, Class> params;
        // com.wangjie.rapidrouter.example.activity.CActivity
        params = new HashMap<>(1, 1F);
        params.put("paramOfCActivity", float.class);
        routerMapper.put("((rr)|(sc))://wang.*jie\\.[cx].*", new RouterTarget(CActivity.class, params));
        return routerMapper;
    }
}
