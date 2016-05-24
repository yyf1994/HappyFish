package com.yyf.happyfish.wechat.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.yyf.happyfish.R;
import com.yyf.happyfish.wechat.contract.WeChatDetialContract;
import com.yyf.happyfish.weight.CustomDialog;
import com.yyf.happyfish.zxing.DecodeImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yyf on 2016/5/9.
 */
public class WeChatDetailPresenter implements WeChatDetialContract.Present{
    private final WeChatDetialContract.View mView;
    private ArrayAdapter<String> adapter;
    private CustomDialog mCustomDialog;
    private boolean isQR;//判断是否为二维码
    private Result result;//二维码解析结果
    private File file;
//    Call<WeChatEntity> call;
    public WeChatDetailPresenter(WeChatDetialContract.View view)
    {
        this.mView = view;
        //fragment 要加上这句话  activity不需要
//        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

//    @Override
//    public void cancelRequest() {
//        call.cancel();
//    }

    @Override
    public void LongClickCallBack(WebView webView) {

        // 长按事件监听（注意：需要实现LongClickCallBack接口并传入对象）
        final WebView.HitTestResult htr = webView.getHitTestResult();//获取所点击的内容
        if (htr.getType() == WebView.HitTestResult.IMAGE_TYPE) {//判断被点击的类型为图片
            String imgUrl = htr.getExtra();
            mView.picture(imgUrl);
            mView.showDialog();
        }
    }

    @Override
    public void initAdapter(Activity activity) {
        adapter = new ArrayAdapter<>(activity, R.layout.item_dialog);
        adapter.add("发送给朋友");
        adapter.add("保存到手机");
        adapter.add("收藏");
    }

    @Override
    public CustomDialog CustomDialog(final Activity activity) {
        mCustomDialog = new CustomDialog(activity) {
            @Override
            public void initViews() {
                // 初始CustomDialog化控件
                ListView mListView = (ListView) findViewById(R.id.lv_dialog);
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // 点击事件
                        switch (position) {
                            case 0:
                                mView.sendToFriends(file);//把图片发送给好友
                                closeDialog();
                                break;
                            case 1:
                                mView.saveImageToGallery(activity,file);
                                closeDialog();
                                break;
                            case 2:
                                Toast.makeText(activity, "已收藏", Toast.LENGTH_LONG).show();
                                closeDialog();
                                break;
                            case 3:
                                mView.goIntent(result);
                                closeDialog();
                                break;
                        }

                    }
                });
            }
        };

        return mCustomDialog;

    }

    @Override
    public void addData() {
        if (isQR){
            adapter.add("识别图中二维码");
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean decodeImage(String sUrl) {
        result = DecodeImage.handleQRCodeFormBitmap(getBitmap(sUrl));
        if(result == null){
            isQR = false;
        }else {
            isQR = true;
        }
        return isQR;
    }

    @Override
    public Bitmap getBitmap(String sUrl) {
        try {
            URL url = new URL(sUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if(conn.getResponseCode() == 200){
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                saveMyBitmap(bitmap,"code");//先把bitmap生成jpg图片
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveMyBitmap(Bitmap mBitmap, String bitName) {
        file= new File( Environment.getExternalStorageDirectory()+"/"+bitName + ".jpg");
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void isQR(Handler handler) {
        if (isQR) {
            handler.sendEmptyMessage(0);
        }
    }
}
