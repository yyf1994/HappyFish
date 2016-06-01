package com.yyf.happyfish.wechat.model;


import com.yyf.happyfish.net.NetWork;
import com.yyf.happyfish.service.HappyFishService;

import retrofit2.Call;

/**
 * Created by yyf on 2016/5/9.
 */
public class WeChatHttp<T> {


    private WeChatHttp(){};

    private static class InstanceHolder{
        private static final WeChatHttp instance = new WeChatHttp();
    }

    public static WeChatHttp getInstance()
    {
        return InstanceHolder.instance;
    }

    public Call<T> getData(int pno,int ps,String key) {
        Call<T> call = (Call<T>) NetWork.getRetrofit().create(HappyFishService.class).loadWeChat(pno,ps,key);
        return call;
    }
    public Call<T> pulldowntorefresh(int pno,int ps,String key) {
        Call<T> call = (Call<T>) NetWork.getRetrofit().create(HappyFishService.class).loadWeChat(pno,ps,key);
        return call;
    }
    public Call<T> upload(int pno,int ps,String key) {
        Call<T> call = (Call<T>) NetWork.getRetrofit().create(HappyFishService.class).loadWeChat(pno,ps,key);
        return call;
    }
}
