package com.yyf.happyfish.news.presenter;

import android.view.View;

import com.yyf.happyfish.news.contract.NewsContract;
import com.yyf.happyfish.news.model.NewsEntity;
import com.yyf.happyfish.news.model.NewsHttp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yyf on 2016/7/6.
 */
public class NewsPresenter implements NewsContract.Present {
    private final NewsContract.View mView;
    Call<NewsEntity> call;

    public NewsPresenter(NewsContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
    }

    @Override
    public void getData(final View view, String type, String key) {
        call = NewsHttp.getInstance().getData(type, key);
        call.enqueue(new Callback<NewsEntity>() {
            @Override
            public void onResponse(Call<NewsEntity> call, Response<NewsEntity> response) {
                mView.responseSuccess(response, view);
            }

            @Override
            public void onFailure(Call<NewsEntity> call, Throwable t) {
                mView.responseFailed(call, t);
            }
        });

    }

    @Override
    public void pulldowntorefresh(String type, String key) {
        call = NewsHttp.getInstance().pulldowntorefresh(type, key);
        call.enqueue(new Callback<NewsEntity>() {
            @Override
            public void onResponse(Call<NewsEntity> call, Response<NewsEntity> response) {
                mView.RefreshSuccess(response);
            }

            @Override
            public void onFailure(Call<NewsEntity> call, Throwable t) {
                mView.responseFailed(call, t);
            }
        });

    }

    @Override
    public void upload(String type, String key) {
        call = NewsHttp.getInstance().upload(type, key);
        call.enqueue(new Callback<NewsEntity>() {
            @Override
            public void onResponse(Call<NewsEntity> call, Response<NewsEntity> response) {
                mView.LoadSuccess(response);
            }

            @Override
            public void onFailure(Call<NewsEntity> call, Throwable t) {
                mView.responseFailed(call, t);
            }
        });


    }

    @Override
    public void cancelRequest() {
        if(call != null){
            call.cancel();
        }
    }

    @Override
    public void start() {

    }
}
