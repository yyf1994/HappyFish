package com.yyf.happyfish.service;

import com.yyf.happyfish.news.model.NewsEntity;
import com.yyf.happyfish.wechat.model.WeChatEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/3/24.
 */
public interface HappyFishService {
    /**
     * http://v.juhe.cn/weixin/query?key=您申请的KEY
     * http://v.juhe.cn/weixin/query?pno=1&ps=10&key=a75b2fd40ea0a3231f859c45b92a885b "
     * */
    @GET("/weixin/query")
    Call<WeChatEntity> loadWeChat(@Query("pno")int pno,@Query("ps")int ps,@Query("key")String key );

    /**
     * http://v.juhe.cn/toutiao/index?type=top&key=254079df18196a395ab86883e659b8dc
     * **/
    @GET("/toutiao/index")
    Call<NewsEntity> loadNews(@Query("type") String type, @Query("key") String key);

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
