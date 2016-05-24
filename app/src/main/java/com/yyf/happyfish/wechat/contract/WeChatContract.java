package com.yyf.happyfish.wechat.contract;

import com.yyf.happyfish.base.BasePresenter;
import com.yyf.happyfish.base.BaseView;
import com.yyf.happyfish.wechat.model.WeChatEntity;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by yyf on 2016/5/9.
 */
public class WeChatContract {

    public interface View extends BaseView<Present> {
        void responseSuccess(Response<WeChatEntity> response, android.view.View view);
        void responseFailed(Call<WeChatEntity> call, Throwable t);
        void RefreshSuccess(Response<WeChatEntity> response);
        void LoadSuccess(Response<WeChatEntity> response);
    }

    public interface Present extends BasePresenter {
        void getData(android.view.View view);//获取数据
        void pulldowntorefresh();//下拉刷新
        void upload();//上啦加载
        void cancelRequest();//取消请求
    }
}
