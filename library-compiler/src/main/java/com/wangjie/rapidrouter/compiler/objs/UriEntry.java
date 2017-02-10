package com.wangjie.rapidrouter.compiler.objs;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/9/17.
 */
public class UriEntry {
    private Element routerTargetClass;
    private String scheme;
    private String host;
    private String uriRegular;
    private List<ParamEntry> params = new ArrayList<>();

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<ParamEntry> getParams() {
        return params;
    }

    public Element getRouterTargetClass() {
        return routerTargetClass;
    }

    public void setRouterTargetClass(Element routerTargetClass) {
        this.routerTargetClass = routerTargetClass;
    }

    public void setUriRegular(String uriRegular) {
        this.uriRegular = uriRegular;
    }

    public String getUriRegular() {
        return uriRegular;
    }

    @Override
    public String toString() {
        return "UriEntry{" +
                "routerTargetClass=" + routerTargetClass +
                ", scheme='" + scheme + '\'' +
                ", host='" + host + '\'' +
                ", uriRegular='" + uriRegular + '\'' +
                ", params=" + params +
                '}';
    }
}
