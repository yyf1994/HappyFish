package com.yyf.happyfish.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yyf.happyfish.receiver.NetWorkBroadcastReceiver;

/**
 * Created by yyf on 2016/5/26.
 */
public class ReceiveMsgService extends Service {
    private  NetWorkBroadcastReceiver myReceiver;
    private Binder binder = new MyBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MyBinder extends Binder
    {
        public ReceiveMsgService getService()
        {
            return ReceiveMsgService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver=new NetWorkBroadcastReceiver();
        registerReceiver(myReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
}
