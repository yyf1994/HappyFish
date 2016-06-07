package com.yyf.happyfish.wechat.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.yyf.happyfish.R;
import com.yyf.happyfish.util.CheckNetUtil;
import com.yyf.happyfish.wechat.contract.WeChatDetialContract;
import com.yyf.happyfish.wechat.model.ListEntity;
import com.yyf.happyfish.wechat.presenter.WeChatDetailPresenter;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeChatDetailActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener,View.OnLongClickListener, WeChatDetialContract.View {

    @BindView(R.id.content_title)
    Toolbar toolbar;
    @BindView(R.id.webview)
    WebView mCustomWebView;
    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;
    @BindView(R.id.textview_error)
    TextView tv_error;

    private ListEntity list;
    private String url;
    private String firstimg;
    private String title;
    private String text;
    private CheckNetUtil checkNetUtil = new CheckNetUtil();
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
        initPermission();
    }

    private void initPermission() {
        //可以将一下代码加到你的MainActivity中，或者在任意一个需要调用分享功能的activity当中
        String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.GET_ACCOUNTS};
        ActivityCompat.requestPermissions(WeChatDetailActivity.this, mPermissionList, 100);
    }


    private void setListener() {
        mCustomWebView.setOnLongClickListener(this);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void initParams() {
        mPresent = new WeChatDetailPresenter(this);
    }

    private void initData() {

        Intent intent = getIntent();
        list = (ListEntity) intent.getExtras().get("result");
        url = list.getUrl();
        firstimg = list.getFirstImg();
        title = list.getTitle();
        text = list.getSource();
        Log.d("url", url);
        mCustomWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
            }
        });
        mCustomWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                mCustomWebView.setVisibility(View.GONE);
                tv_error.setVisibility(View.VISIBLE);
                tv_error.setText("网页加载失败,点击重试");
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            //在网页加载完成后使得进度条消失
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
            }

            //在网页加载开始时使得进度条显示
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
        mCustomWebView.loadUrl(url);
    }

    private void initView() {
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.inflateMenu(R.menu.toolbar_menu);//设置右上角的填充菜单
        WebSettings webSettings = mCustomWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先取缓存
        mCustomWebView.setFocusable(true);
        mCustomWebView.setFocusableInTouchMode(true);
        mCustomWebView.setWebChromeClient(new WebChromeClient());
    }

    @OnClick(R.id.textview_error)
    public void tv_error_click() {
        Toast.makeText(WeChatDetailActivity.this, "tv_error_click", Toast.LENGTH_SHORT).show();
        mCustomWebView.reload();

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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Log.d("result","onActivityResult");
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int menuItemId = item.getItemId();
        if (menuItemId == R.id.action_item1) {
            /**分享面板增加自定义按钮,以及不同分享平台不同分享内容，不同回调监听**/
            final UMImage image = new UMImage(this, firstimg);
               new ShareAction(this).setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                       .setShareboardclickCallback(new ShareBoardlistener() {
                           @Override
                           public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                               if (share_media == SHARE_MEDIA.SINA){
//                                       new ShareAction(WeChatDetailActivity.this).setPlatform(share_media).setCallback(testmulListener)
//                                               .withText("hello sina")
//                                               .share();
                               }else if (share_media == SHARE_MEDIA.QQ){
                                   new ShareAction(WeChatDetailActivity.this).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener)
                                           .withTitle(title)
                                           .withText(text)
                                           .withMedia(image)
                                           .share();
                               }else if(share_media == SHARE_MEDIA.QZONE){
                                   new ShareAction(WeChatDetailActivity.this).setPlatform(SHARE_MEDIA.QZONE).setCallback(umShareListener)
                                           .withTitle(title)
                                           .withText(text)
                                           .withMedia(image)
                                           .withTargetUrl(url)
                                           .share();
                               }else if(share_media == SHARE_MEDIA.WEIXIN){
                                   new ShareAction(WeChatDetailActivity.this).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                                           .withTitle(title)
                                           .withText(text)
                                           .withMedia(image)
                                           .withTargetUrl(url)
                                           .share();
                               }
                               else {
//                                       new ShareAction(WeChatDetailActivity.this).setPlatform(share_media).setCallback(testmulListener)
//                                               .withText("hello other platform")
//                                               .share();
                               }
                           }
                       }).open();
        } else if (menuItemId == R.id.action_item2) {
            Toast.makeText(WeChatDetailActivity.this, R.string.item02, Toast.LENGTH_SHORT).show();

        }
        return true;
    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mPresent.isQR(handler);
        }

        @Override
        protected String doInBackground(String... params) {
            if (checkNetUtil.isNetworkConnected(WeChatDetailActivity.this)) {
                mPresent.decodeImage(params[0]);
            }

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

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            if(platform.name().equals("WEIXIN_FAVORITE")){
                Toast.makeText(WeChatDetailActivity.this,platform + " 收藏成功啦",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(WeChatDetailActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(WeChatDetailActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(WeChatDetailActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
}
