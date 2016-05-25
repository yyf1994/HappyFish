package com.yyf.happyfish.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by yyf on 2016/5/25.
 */
public class NetWorkBroadcastReceiver extends BroadcastReceiver {

    public static boolean isConnected;
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo  wifiNetInfo=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            isConnected = false;
//            BSToast.showLong(context, "网络不可以用");
            //改变背景或者 处理网络的全局变量
        }else {
            isConnected = true;
            //改变背景或者 处理网络的全局变量
        }
    }
}
