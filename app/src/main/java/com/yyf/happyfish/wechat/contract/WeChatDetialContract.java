package com.yyf.happyfish.wechat.contract;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.WebView;

import com.google.zxing.Result;
import com.yyf.happyfish.base.BasePresenter;
import com.yyf.happyfish.base.BaseView;
import com.yyf.happyfish.weight.CustomDialog;

import java.io.File;

/**
 * Created by yyf on 2016/5/9.
 */
public class WeChatDetialContract {

    public interface View extends BaseView<Present> {
        void  showDialog();
        void sendToFriends(File file);//发送给朋友
        void saveImageToGallery(Context context,File file);//先保存到本地再广播到图库
        void goIntent(Result result);
        void picture(String url);
    }

    public interface Present extends BasePresenter {
        void LongClickCallBack(WebView webView);
        void initAdapter(Activity activity);//刷新数据
        CustomDialog CustomDialog(Activity activity);//为dialog刷新数据
        void addData();//图片为二维码时添加相应数据
        boolean decodeImage(String sUrl);//判断是否为二维码
        Bitmap getBitmap(String sUrl);//根据地址获取网络图片
        void saveMyBitmap(Bitmap mBitmap,String bitName);//保存为jpg 图片
        void isQR(Handler handler);
    }
}
