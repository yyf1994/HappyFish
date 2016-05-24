package com.yyf.happyfish.wechat.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.zxing.Result;
import com.yyf.happyfish.R;
import com.yyf.happyfish.wechat.contract.WeChatDetialContract;
import com.yyf.happyfish.wechat.model.ListEntity;
import com.yyf.happyfish.wechat.presenter.WeChatDetailPresenter;
import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeChatDetailActivity extends AppCompatActivity implements View.OnLongClickListener,WeChatDetialContract.View {

    @BindView(R.id.content_title)
    Toolbar toolbar;
    @BindView(R.id.webview)
    WebView mCustomWebView;

    private ListEntity list;


    private WeChatDetialContract.Present mPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat_detail);
        ButterKnife.bind(this);
        initView();
        initData();
        initParams();
        setListener();
    }

    private void setListener() {
        mCustomWebView.setOnLongClickListener(this);

    }


    private void initParams() {
        mPresent = new WeChatDetailPresenter(this);
    }

    private void initData() {

        Intent intent = getIntent();
        list = (ListEntity) intent.getExtras().get("result");
        String url = list.getUrl();
        Log.d("url", url);
        mCustomWebView.loadUrl(url);
        mCustomWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        WebSettings webSettings = mCustomWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先取缓存
        mCustomWebView.setFocusable(true);
        mCustomWebView.setFocusableInTouchMode(true);
    }

    /**
     * 点击返回时返回上一界面
     */

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mCustomWebView.canGoBack()) {
            mCustomWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onLongClick(View v) {
        mPresent.LongClickCallBack(mCustomWebView);
        return false;
    }

    @Override
    public void showDialog() {
        mPresent.initAdapter(this);
        mPresent.CustomDialog(WeChatDetailActivity.this).show();
    }

    /**
     * 发送给好友
     */
    @Override
    public void sendToFriends(File file) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri imageUri = Uri.parse(file.getAbsolutePath());
        intent.setType("image*//*");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getTitle()));

    }

    /**
     * 先保存到本地再广播到图库
     */
    @Override
    public void saveImageToGallery(Context context, File file) {

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), "code", null);
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"
                    + file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void goIntent(Result result) {
        Uri uri = Uri.parse(result.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void picture(String url) {
        // 获取到图片地址后做相应的处理
        MyAsyncTask mTask = new MyAsyncTask();
        mTask.execute(url);
    }

    @Override
    public void setPresenter(WeChatDetialContract.Present presenter) {

    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mPresent.isQR(handler);
        }

        @Override
        protected String doInBackground(String... params) {
            mPresent.decodeImage(params[0]);
            return null;
        }
    }

    /**
     * 是二维码时，才添加为识别二维码
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                mPresent.addData();
            }
        }
    };
}