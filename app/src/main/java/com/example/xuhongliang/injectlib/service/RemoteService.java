package com.example.xuhongliang.injectlib.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.xuhongliang.injectlib.aidl.ProgressService;

/**
 * Created by xuhongliang on 16/6/2.
 */
public class RemoteService extends Service {


    private MyBind myBind;

    private ServiceConnection con;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBind;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myBind = new MyBind();
        con = new MyServiceCon();

    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        RemoteService.this.bindService(new Intent(RemoteService.this, LocalService.class), con, Context.BIND_IMPORTANT);
    }


    private class MyServiceCon implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("service", "远程服务启动");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("service", "本地服务被杀死");
            RemoteService.this.startService(new Intent(RemoteService.this, LocalService.class));
            RemoteService.this.bindService(new Intent(RemoteService.this, LocalService.class), con, Context.BIND_IMPORTANT);
        }
    }


    private class MyBind extends ProgressService.Stub {

        @Override
        public String getServiceName() throws RemoteException {
            return "this is RemoteService";
        }
    }
}
