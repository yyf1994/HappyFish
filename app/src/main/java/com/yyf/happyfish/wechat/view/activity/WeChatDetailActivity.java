package com.yyf.happyfish.wechat.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yyf.happyfish.R;
import com.yyf.happyfish.wechat.model.ListEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeChatDetailActivity extends AppCompatActivity {

    @BindView(R.id.content_title)
    Toolbar toolbar;
    @BindView(R.id.webview)
    WebView webview;

    private ListEntity list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat_detail);
        ButterKnife.bind(this);
        initView();
        initData();
        setListener();
    }

    private void setListener() {

    }

    private void initData() {

        Intent intent = getIntent();
         list = (ListEntity) intent.getExtras().get("result");
        String url = list.getUrl();
        Log.d("url",url);
        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient(){
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
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先取缓存
    }
    /**
     * 点击返回时返回上一界面
     * */

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
