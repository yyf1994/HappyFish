package com.yyf.happyfish.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Aspsine on 2015/9/2.
 */
public class App extends Application {


    private static Context sContext;
    private static App sInstance;
//    private static NetWorkBroadcastReceiver myReceiver;
//    public static boolean isConnected;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sContext = getApplicationContext();
    }

    public static App getInstance() {
        return sInstance;
    }

  /*  public static void registerReceiver(){
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver=new NetWorkBroadcastReceiver();
        filter.addAction("isConnected");    //只有持有相同的action的接受者才能接收此广播
        getInstance().getApplicationContext().registerReceiver(myReceiver, filter);
    }

    public static void unregisterReceiver(){
        getInstance().getApplicationContext().unregisterReceiver(myReceiver);
    }*/


   /* private void setStrictMode() {
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.enableDefaults();
        }
    }*/
}
