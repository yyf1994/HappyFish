package com.yyf.happyfish.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

/**
 * Created by yyf on 2016/6/12.
 */
public class ScrollWebView extends WebView {
    public OnScrollChangeListener listener;
    public ScrollWebView(Context context) {
        super(context);
    }

    public ScrollWebView(Context context, AttributeSet attrs, OnScrollChangeListener listener) {
        super(context, attrs);
        this.listener = listener;
    }

    public ScrollWebView(Context context, AttributeSet attrs, int defStyleAttr, OnScrollChangeListener listener) {
        super(context, attrs, defStyleAttr);
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float webcontent = getContentHeight() * getScale();// webview的高度
        float webnow = getHeight() + getScrollY();// 当前webview的高度
        Log.i("TAG1", "webview.getScrollY()====>>" + getScrollY());
        if (Math.abs(webcontent - webnow) < 1) {
            // 已经处于底端
            // Log.i("TAG1", "已经处于底端");
            listener.onPageEnd(l, t, oldl, oldt);
        } else if (getScrollY() == 0) {
            // Log.i("TAG1", "已经处于顶端");
            listener.onPageTop(l, t, oldl, oldt);
        } else {
            listener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {

        this.listener = listener;

    }

    public interface OnScrollChangeListener {
        public void onPageEnd(int l, int t, int oldl, int oldt);
        public void onPageTop(int l, int t, int oldl, int oldt);
        public void onScrollChanged(int l, int t, int oldl, int oldt);

    }
}
