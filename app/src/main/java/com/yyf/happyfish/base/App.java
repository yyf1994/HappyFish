package com.yyf.happyfish.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Aspsine on 2015/9/2.
 */
public class App extends Application {


    private static Context sContext;
    private static App sInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sContext = getApplicationContext();
    }

    public static App getInstance() {
        return sInstance;
    }


   /* private void setStrictMode() {
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.enableDefaults();
        }
    }*/
}
