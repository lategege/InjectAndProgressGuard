package com.example.xuhongliang.injectlib.tools.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xuhongliang on 16/6/1.
 */


@Retention(RetentionPolicy.RUNTIME)

@Target(ElementType.FIELD)
public @interface ViewInject {
    int value();
}
