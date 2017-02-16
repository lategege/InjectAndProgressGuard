package com.example.xuhongliang.injectlib.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xuhongliang.injectlib.R;
import com.example.xuhongliang.injectlib.service.LocalService;
import com.example.xuhongliang.injectlib.service.RemoteService;
import com.example.xuhongliang.injectlib.tools.annotation.ContentView;
import com.example.xuhongliang.injectlib.tools.annotation.OnClick;
import com.example.xuhongliang.injectlib.tools.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.text)
    TextView text;

    @ViewInject(R.id.button)
    Button button;

    @Override
    void initData() {
        text.setText("DADSAD");
        button.setText("DDDDDDD");
        this.startService(new Intent(this, LocalService.class));
        this.startService(new Intent(this, RemoteService.class));
    }


    @OnClick({R.id.button,R.id.text})
    public void show(View view){
        Toast.makeText(this,"点击事件",Toast.LENGTH_LONG).show();
    }
}
