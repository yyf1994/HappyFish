package com.yyf.happyfish.net;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gaoqun on 2016/5/13.
 * 封装公共参数统一拦截
 */
public class CommentParamsInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        HttpUrl.Builder commentParamsUrlBuilder;
       /* SparseArray<String> sparseArray = App.sSparseArray;
        commentParamsUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter(Constant.USERID, sparseArray.get(Constant.SUSERID))
                .addQueryParameter(Constant.TOKEN, sparseArray.get(Constant.STOKEN))
                .addQueryParameter(Constant.DEVICEID, sparseArray.get(Constant.SDEVICEID))
                .addQueryParameter(Constant.VER, sparseArray.get(Constant.SVER))
                .addQueryParameter(Constant.OS, sparseArray.get(Constant.SOS))
                .addQueryParameter(Constant.DEVICETYPE, sparseArray.get(Constant.SDEVICETYPE));
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(commentParamsUrlBuilder.build())
                .build();
                return chain.proceed(newRequest);
*/
        return null;

    }
}
