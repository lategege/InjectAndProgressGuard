package com.example.xuhongliang.injectlib.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.xuhongliang.injectlib.tools.utils.InjectUtils;

/**
 * Created by xuhongliang on 16/6/1.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.inject(this);
        initData();
    }

    abstract void initData();
}
