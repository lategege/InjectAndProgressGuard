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
public class LocalService extends Service {

    private MyBind myBind;

    private MyServiceCon con;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBind;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myBind=new MyBind();
        con=new MyServiceCon();
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        LocalService.this.bindService(new Intent(LocalService.this,RemoteService.class),con, Context.BIND_IMPORTANT);
    }




    private class MyServiceCon implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("service","本地服务启动");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("service","远程服务被杀死");
            LocalService.this.startService(new Intent(LocalService.this,RemoteService.class));
            LocalService.this.bindService(new Intent(LocalService.this,RemoteService.class),con,Context.BIND_IMPORTANT);

        }
    }


    private class MyBind extends ProgressService.Stub {

        @Override
        public String getServiceName() throws RemoteException {
            return "this is localService";
        }
    }


}
