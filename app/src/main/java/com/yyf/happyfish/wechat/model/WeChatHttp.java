package com.yyf.happyfish.wechat.model;


import com.yyf.happyfish.net.NetWork;
import com.yyf.happyfish.service.HappyFishService;

import retrofit2.Call;

/**
 * Created by yyf on 2016/5/9.
 */
public class WeChatHttp<T> {

    String key = "a75b2fd40ea0a3231f859c45b92a885b";
    private WeChatHttp(){};

    private static class InstanceHolder{
        private static final WeChatHttp instance = new WeChatHttp();
    }

    public static WeChatHttp getInstance()
    {
        return InstanceHolder.instance;
    }

    public Call<T> getData() {
        Call<T> call = (Call<T>) NetWork.getRetrofit().create(HappyFishService.class).loadWeChat();
        return call;
    }
    public Call<T> pulldowntorefresh() {
        Call<T> call = (Call<T>) NetWork.getRetrofit().create(HappyFishService.class).loadWeChat();
        return call;
    }
    public Call<T> upload() {
        Call<T> call = (Call<T>) NetWork.getRetrofit().create(HappyFishService.class).loadWeChat();
        return call;
    }
}
