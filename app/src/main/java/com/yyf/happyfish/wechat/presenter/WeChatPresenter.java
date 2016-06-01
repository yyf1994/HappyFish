package com.yyf.happyfish.wechat.presenter;

import android.view.View;

import com.yyf.happyfish.wechat.contract.WeChatContract;
import com.yyf.happyfish.wechat.model.WeChatEntity;
import com.yyf.happyfish.wechat.model.WeChatHttp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yyf on 2016/5/9.
 */
public class WeChatPresenter implements WeChatContract.Present {
    private final WeChatContract.View mView;
    Call<WeChatEntity> call;

    public WeChatPresenter(WeChatContract.View view)
    {
        this.mView = view;
        //fragment 要加上这句话  activity不需要
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getData(final View view,int pno,int ps,String key) {
        call = WeChatHttp.getInstance().getData(pno,ps,key);
        call.enqueue(new Callback<WeChatEntity>() {
            @Override
            public void onResponse(Call<WeChatEntity> call, Response<WeChatEntity> response) {
                mView.responseSuccess(response,view);
            }

            @Override
            public void onFailure(Call<WeChatEntity> call, Throwable t) {
                mView.responseFailed(call,t);
            }
        });
    }

    @Override
    public void pulldowntorefresh(int pno,int ps,String key) {
         call = WeChatHttp.getInstance().pulldowntorefresh(pno, ps, key);
        call.enqueue(new Callback<WeChatEntity>() {
            @Override
            public void onResponse(Call<WeChatEntity> call, Response<WeChatEntity> response) {
                mView.RefreshSuccess(response);
            }

            @Override
            public void onFailure(Call<WeChatEntity> call, Throwable t) {
                mView.responseFailed(call,t);
            }
        });
    }

    @Override
    public void upload(int pno,int ps,String key) {
         call = WeChatHttp.getInstance().upload(pno, ps, key);
        call.enqueue(new Callback<WeChatEntity>() {
            @Override
            public void onResponse(Call<WeChatEntity> call, Response<WeChatEntity> response) {
                mView.LoadSuccess(response);
            }

            @Override
            public void onFailure(Call<WeChatEntity> call, Throwable t) {
                mView.responseFailed(call,t);
            }
        });
    }

    @Override
    public void cancelRequest() {
        if(call != null){
            call.cancel();
        }

    }


}
