package com.wangjie.rapidrouter.core.target;

import java.util.HashMap;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/8/17.
 */
public class RouterTarget {
    private Class targetClass;
    private HashMap<String, Class> params;

    public RouterTarget(Class targetClass, HashMap<String, Class> params) {
        this.targetClass = targetClass;
        this.params = params;
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
