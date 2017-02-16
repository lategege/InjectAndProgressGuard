package com.example.xuhongliang.injectlib.tools.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xuhongliang on 16/6/1.
 */

@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter ="setOnClickListener",listenerType = View.OnClickListener.class,callbackMethod = "onClick")
@Target(ElementType.METHOD)
public @interface OnClick {
    int[] value();
}
