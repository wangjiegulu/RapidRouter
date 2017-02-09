package com.wangjie.rapidrouter.core;

import android.content.Context;
import android.content.Intent;

import com.wangjie.rapidrouter.core.exception.RapidRouterIllegalException;
import com.wangjie.rapidrouter.core.listener.RouterErrorCallback;
import com.wangjie.rapidrouter.core.listener.RouterGoAfterCallback;
import com.wangjie.rapidrouter.core.listener.RouterGoAroundCallback;
import com.wangjie.rapidrouter.core.listener.RouterGoBeforeCallback;
import com.wangjie.rapidrouter.core.listener.RouterTargetNotFoundCallback;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/9/17.
 */
public class RouterStuff {
    private Context context;
    private Intent intent;
    private String uriStr;

    private RouterErrorCallback error;
    private RouterTargetNotFoundCallback targetNotFound;
    private RouterGoBeforeCallback goBefore;
    private RouterGoAroundCallback goAround;
    private RouterGoAfterCallback goAfter;

    public RouterStuff intent(Intent intent) {
        this.intent = intent;
        return this;
    }

    public Context context() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public RouterStuff uri(String uriStr) {
        this.uriStr = uriStr;
        return this;
    }

    public Intent intent() {
        return intent;
    }

    public String uriAsString() {
        return uriStr;
    }

    public RouterErrorCallback error() {
        return error;
    }

    public RouterStuff error(RouterErrorCallback errorListener) {
        this.error = errorListener;
        return this;
    }

    public RouterTargetNotFoundCallback targetNotFound() {
        return targetNotFound;
    }

    public RouterStuff targetNotFound(RouterTargetNotFoundCallback targetNotFoundListener) {
        this.targetNotFound = targetNotFoundListener;
        return this;
    }

    public RouterGoBeforeCallback goBefore() {
        return goBefore;
    }

    public RouterStuff goBefore(RouterGoBeforeCallback goBeforeListener) {
        this.goBefore = goBeforeListener;
        return this;
    }

    public RouterGoAroundCallback goAround() {
        return goAround;
    }

    public RouterStuff goAround(RouterGoAroundCallback goAroundListener) {
        this.goAround = goAroundListener;
        return this;
    }

    public RouterGoAfterCallback goAfter() {
        return goAfter;
    }

    public RouterStuff goAfter(RouterGoAfterCallback goAfterListener) {
        this.goAfter = goAfterListener;
        return this;
    }

    public boolean go() {
        if (null == context) {
            throw new RapidRouterIllegalException("Context can not be null!");
        }
        if (null == uriStr) {
            throw new RapidRouterIllegalException("Uri can not be null!");
        }
        return RapidRouter.to(this);
    }

    @Override
    public String toString() {
        return "RouterStuff{" +
                "context=" + context +
                ", intent=" + intent +
                ", uriStr='" + uriStr + '\'' +
                ", error=" + error +
                ", targetNotFound=" + targetNotFound +
                ", goBefore=" + goBefore +
                ", goAround=" + goAround +
                ", goAfter=" + goAfter +
                '}';
    }
}
