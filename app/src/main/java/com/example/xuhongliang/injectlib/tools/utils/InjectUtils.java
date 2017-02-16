package com.example.xuhongliang.injectlib.tools.utils;

import android.app.Activity;
import android.view.View;

import com.example.xuhongliang.injectlib.tools.annotation.ContentView;
import com.example.xuhongliang.injectlib.tools.annotation.EventBase;
import com.example.xuhongliang.injectlib.tools.annotation.ViewInject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuhongliang on 16/6/1.
 */
public class InjectUtils {


    public static void inject(Activity activity) {
        injectLayout(activity);
        injectViews(activity);
        injectEvents(activity);
    }


    /**
     * 注入布局
     *
     * @param activity
     */
    private static void injectLayout(Activity activity) {
        Class<?> clazz = activity.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        int layoutRes = contentView.value();
        activity.setContentView(layoutRes);

    }


    /**
     * 注入视图
     *
     * @param activity
     */
    private static void injectViews(Activity activity) {
        Class<?> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                //获取注解的值,设置给属性
                int id = viewInject.value();
                View view = activity.findViewById(id);
                field.setAccessible(true);
                try {
                    field.set(activity, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    /**
     * 注入监听器
     *
     * @param activity
     */
    private static void injectEvents(Activity activity) {
        Class<?> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation antation : annotations) {
                Class<?> annotationType = antation.annotationType();
                EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                if (eventBase == null) {
                    continue;
                }
                //获取事件三要素
                String listenerSetter = eventBase.listenerSetter();
                Class<?> listenerType = eventBase.listenerType();
                String callBackMethod = eventBase.callbackMethod();

                Map<String,Method> mtdMap =new HashMap<>();

                mtdMap.put(callBackMethod,method);
                //获取需要注入事件的空间对象
                try {
                    Method valueMtd = annotationType.getDeclaredMethod("value");
                    int[] viewIds = (int[]) valueMtd.invoke(antation);
                    for (int viewId : viewIds) {
                        View view = activity.findViewById(viewId);
                        if (view == null) {
                            continue;
                        }
                        Method setListenerMtd = view.getClass().getMethod(listenerSetter, listenerType);
                        //listenerType不能直接new,即使new出也不
                        ListenerInvocationHandler listener = new ListenerInvocationHandler(mtdMap,activity);
                        Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, listener);
                        setListenerMtd.invoke(view, proxy);
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }

    }

}
