package com.example.xuhongliang.injectlib.tools.utils;

import android.app.Activity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by xuhongliang on 16/6/1.
 */
public class ListenerInvocationHandler implements InvocationHandler {

    private Map<String, Method> mtdMap;
    private Activity target;

    public ListenerInvocationHandler(Map<String, Method> map,Activity activity) {
        this.mtdMap = map;
        this.target=activity;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        Method mtd = mtdMap.get(name);

        if (mtd != null) {
            return mtd.invoke(target,args);
        }

        return method.invoke(proxy,args);
    }
}
