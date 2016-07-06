package com.yyf.happyfish.news.model;


import com.yyf.happyfish.net.NetWork;
import com.yyf.happyfish.service.HappyFishService;

import retrofit2.Call;

/**
 * Created by yyf on 2016/5/9.
 */
public class NewsHttp<T> {


    private NewsHttp(){};

    private static class InstanceHolder{
        private static final NewsHttp instance = new NewsHttp();
    }

    public static NewsHttp getInstance()
    {
        return InstanceHolder.instance;
    }

    public Call<T> getData(String type,String key) {
        Call<T> call = (Call<T>) NetWork.getRetrofit().create(HappyFishService.class).loadNews(type, key);
        return call;
    }
    public Call<T> pulldowntorefresh(String type,String key) {
        Call<T> call = (Call<T>) NetWork.getRetrofit().create(HappyFishService.class).loadNews(type, key);
        return call;
    }
    public Call<T> upload(String type,String key) {
        Call<T> call = (Call<T>) NetWork.getRetrofit().create(HappyFishService.class).loadNews(type, key);
        return call;
    }
}
