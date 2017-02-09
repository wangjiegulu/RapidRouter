package com.wangjie.rapidrouter.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.wangjie.rapidrouter.core.exception.RapidRouterIllegalException;
import com.wangjie.rapidrouter.core.listener.OnRapidRouterListener;
import com.wangjie.rapidrouter.core.listener.RouterGoAroundCallback;
import com.wangjie.rapidrouter.core.listener.RouterGoBeforeCallback;
import com.wangjie.rapidrouter.core.listener.RouterTargetNotFoundCallback;
import com.wangjie.rapidrouter.core.target.RouterTarget;

import java.util.HashMap;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 2/8/17.
 */
public class RapidRouter {
    private static OnRapidRouterListener onRapidRouterListener;

    public static void setOnRapidRouterListener(OnRapidRouterListener onRapidRouterListener) {
        RapidRouter.onRapidRouterListener = onRapidRouterListener;
    }

    private static final String TAG = RapidRouter.class.getSimpleName();

    /**
     * HashMap<{scheme}, HashMap<{host}, {activity Class}>>
     */
    private static HashMap<String, HashMap<String, RouterTarget>> maps;

    public static void init(HashMap<String, HashMap<String, RouterTarget>> map) {
        maps = map;
    }

    public static RouterStuff with(@NonNull Context context) {
        RouterStuff routerStuff = new RouterStuff();
        routerStuff.setContext(context);
        return routerStuff;
    }

    protected static boolean to(RouterStuff routerStuff) {
        if (null == maps) {
            throw new RuntimeException("RapidRouter is not initialized");
        }
        RouterTarget routerTarget = null;

        String uriStr = routerStuff.uriAsString();
        try {
            Uri uri = Uri.parse(uriStr);

            HashMap<String, RouterTarget> schemeMapper = maps.get(uri.getScheme());
            if (null != schemeMapper) {
                routerTarget = schemeMapper.get(uri.getHost());
            }

            if (null == routerTarget) {
                RouterTargetNotFoundCallback targetNotFoundCallback = routerStuff.targetNotFound();
                if (null == targetNotFoundCallback || !targetNotFoundCallback.onRouterTargetNotFound(routerStuff)) {
                    if (null != onRapidRouterListener) {
                        onRapidRouterListener.onRouterTargetNotFound(routerStuff);
                    }
                }

                return false;
            }

            Intent intent = routerStuff.intent();

            if (null == intent) {
                intent = new Intent();
                routerStuff.intent(intent);
            }

            Context context = routerStuff.context();
            intent.setComponent(new ComponentName(context, routerTarget.getTargetClass()));
            intent.setData(uri);
            HashMap<String, Class> params = routerTarget.getParams();

            for (String paramName : uri.getQueryParameterNames()) {
                Class tempClass;
                Class paramClass = null == params || null == (tempClass = params.get(paramName)) ? String.class : tempClass;
                putExtraToIntent(intent, paramClass, paramName, uri.getQueryParameter(paramName));
            }

            RouterGoBeforeCallback goBeforeCallback = routerStuff.goBefore();
            if (null == goBeforeCallback || !goBeforeCallback.onRouterGoBefore(routerStuff)) {
                if (null != onRapidRouterListener) {
                    onRapidRouterListener.onRouterGoBefore(routerStuff);
                }
            }

            RouterGoAroundCallback goAroundCallback = routerStuff.goAround();
            if (null != goAroundCallback) {
                goAroundCallback.onRouterGoAround(routerStuff);
                if (null != onRapidRouterListener) {
                    onRapidRouterListener.onRouterGoAfter(routerStuff);
                }
            } else {
                boolean proceed = true;
                if (null != onRapidRouterListener) {
                    proceed = !onRapidRouterListener.onRouterGoAround(routerStuff);
                }
                if (proceed) {
                    context.startActivity(intent);
                    if (null != onRapidRouterListener) {
                        onRapidRouterListener.onRouterGoAfter(routerStuff);
                    }
                }
            }

            return true;
        } catch (Throwable throwable) {
            Log.e(TAG, "", throwable);
            if (null != onRapidRouterListener) {
                onRapidRouterListener.onRouterError(routerStuff, throwable);
            }
            return false;
        }
    }


    private static void putExtraToIntent(Intent intent, Class clazz, String paramName, String value) {
        if (null != value) {
            if (String.class == clazz) {
                intent.putExtra(paramName, value);
            } else if (int.class == clazz || Integer.class == clazz) {
                try {
                    intent.putExtra(paramName, Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    throw new RapidRouterIllegalException("Expect type of " + paramName + ": " + clazz + ", actual value: " + value);
                }
            } else if (long.class == clazz || Long.class == clazz) {
                try {
                    intent.putExtra(paramName, Long.parseLong(value));
                } catch (NumberFormatException e) {
                    throw new RapidRouterIllegalException("Expect type of " + paramName + ": " + clazz + ", actual value: " + value);
                }
            } else if (boolean.class == clazz || Boolean.class == clazz) {
                try {
                    intent.putExtra(paramName, Boolean.parseBoolean(value));
                } catch (NumberFormatException e) {
                    throw new RapidRouterIllegalException("Expect type of " + paramName + ": " + clazz + ", actual value: " + value);
                }
            } else if (float.class == clazz || Float.class == clazz) {
                try {
                    intent.putExtra(paramName, Float.parseFloat(value));
                } catch (NumberFormatException e) {
                    throw new RapidRouterIllegalException("Expect type of " + paramName + ": " + clazz + ", actual value: " + value);
                }
            } else if (double.class == clazz || Double.class == clazz) {
                try {
                    intent.putExtra(paramName, Double.parseDouble(value));
                } catch (NumberFormatException e) {
                    throw new RapidRouterIllegalException("Expect type of " + paramName + ": " + clazz + ", actual value: " + value);
                }
            } else if (short.class == clazz || Short.class == clazz) {
                try {
                    intent.putExtra(paramName, Short.parseShort(value));
                } catch (NumberFormatException e) {
                    throw new RapidRouterIllegalException("Expect type of " + paramName + ": " + clazz + ", actual value: " + value);
                }
            } else if ((char.class == clazz || Character.class == clazz) && value.length() > 0) {
                try {
                    intent.putExtra(paramName, value.charAt(0));
                } catch (NumberFormatException e) {
                    throw new RapidRouterIllegalException("Expect type of " + paramName + ": " + clazz + ", actual value: " + value);
                }
            } else if (byte.class == clazz || Byte.class == clazz) {
                try {
                    intent.putExtra(paramName, Byte.parseByte(value));
                } catch (NumberFormatException e) {
                    throw new RapidRouterIllegalException("Expect type of " + paramName + ": " + clazz + ", actual value: " + value);
                }
            } else {
                intent.putExtra(paramName, value);
            }
        }
    }

}
