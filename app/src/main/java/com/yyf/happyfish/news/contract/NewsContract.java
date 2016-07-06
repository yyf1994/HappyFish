package com.yyf.happyfish.news.contract;

import com.yyf.happyfish.base.BasePresenter;
import com.yyf.happyfish.base.BaseView;
import com.yyf.happyfish.news.model.NewsEntity;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by yyf on 2016/5/9.
 */
public class NewsContract {

    public interface View extends BaseView<Present> {
        void responseSuccess(Response<NewsEntity> response, android.view.View view);
        void responseFailed(Call<NewsEntity> call, Throwable t);
        void RefreshSuccess(Response<NewsEntity> response);
        void LoadSuccess(Response<NewsEntity> response);
    }

    public interface Present extends BasePresenter {
        void getData(android.view.View view, String type, String key);//获取数据
        void pulldowntorefresh(String type, String key);//下拉刷新
        void upload(String type, String key);//上啦加载
        void cancelRequest();//取消请求
    }
}
