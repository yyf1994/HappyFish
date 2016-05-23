package com.yyf.happyfish.net;/*
package com.eastfair.exhibiterapp.net;


*/
/**
 * Created by gaoqun on 2016/5/13.
 *//*

public class BaseRepository implements CallBack.ResponseResult {

    @Override
    public <T> void handleSucceed(Response<ResponseData<T>> response, CallBack callBack) {
        if (response == null) {
            callBack.Failed("没有获取到response!");
        } else if (response.body() == null) {
            callBack.Failed("error_" + response.code());
        } else {
            switch (response.body().getStatus()) {
                //获取错误信息
                case 0:
                    if (response.body().getError() != null) {
                        ResponseData.ResponseError responseError = response.body().getError();
                        callBack.Failed(responseError.getError_msg() + "_" + responseError.getError_code());
                    }
                    break;
                //获取数据
                case 1:
                    if (response.body().getData() != null) {
                        callBack.Succeed(response.body().getData());
                    } else {
                        callBack.Failed("数据为空！");
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void handleFailed(Throwable throwable, CallBack callBack) {
        callBack.Failed(throwable.getMessage());
    }
}
*/
