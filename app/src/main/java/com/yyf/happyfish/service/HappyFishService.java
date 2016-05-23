package com.yyf.happyfish.service;

import com.yyf.happyfish.wechat.model.WeChatEntity;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2016/3/24.
 */
public interface HappyFishService {
    /**
     * http://v.juhe.cn/weixin/query?key=您申请的KEY
     * */
    @GET("/weixin/query?key=a75b2fd40ea0a3231f859c45b92a885b ")
    Call<WeChatEntity> loadWeChat();
//    Call<WeChatEntity> loadWeChat(@Query("key") String key);

    /**
     * http://api.douban.com/labs/bubbler/user/ahbei
     * */
//    @GET("/onebox/news/query")
//    Call<News> loadNews(
//            @Query("q") String q,
//            @Query("dtype") String dtype,
//            @Query("key") String key);


//    @FormUrlEncoded
//    @POST("/onebox/news/query")
//    Call<News> loadNewsPost(
//            @Field("q") String q,
//            @Field("dtype") String dtype,
//            @Field("key") String key);

}
