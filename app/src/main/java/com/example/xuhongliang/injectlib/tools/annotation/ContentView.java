package com.example.xuhongliang.injectlib.tools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xuhongliang on 16/6/1.
 */

//运行时使用
@Retention(RetentionPolicy.RUNTIME)

//作用在类上
@Target(ElementType.TYPE)
public @interface ContentView {
    int value();
}
