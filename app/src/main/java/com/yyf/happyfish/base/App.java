package com.yyf.happyfish.base;

import android.app.Application;
import android.content.Context;

import com.umeng.socialize.PlatformConfig;

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

    //各个平台的配置，建议放在全局Application或者程序入口
    {
        //微信    wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //新浪微博
        PlatformConfig.setSinaWeibo("1636054210", "843b045d00da2ac15e7ec2c826bf7e31");
        PlatformConfig.setQQZone("1105456244", "T4ViC2XXCo1w23K3");
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
