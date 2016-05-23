package com.yyf.happyfish.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * @author wangyan
 * @name BaseActivity
 * @description ui初始化及常用界面方法父类(所有activity继承此类)
 * @date 20151204
 */
public abstract class BaseFragment extends Fragment {
    //    private CustomProgressDialog progressDialog = null;
    Activity mActivity;
//    AppCompatActivity mAppCompatActivity;
    protected abstract void initView(View view, Bundle savedInstanceState);

    //获取fragment布局文件ID
    protected abstract int getLayoutId();


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        setHasOptionsMenu(true);
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);
        return view;

    }

    public Toolbar initToolbar(AppCompatActivity mAppCompatActivity, int toolbarId) {
        Toolbar toolbar = (Toolbar) mAppCompatActivity.findViewById(toolbarId);
        mAppCompatActivity.setSupportActionBar(toolbar);
        mAppCompatActivity.getSupportActionBar().setTitle("title");
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        return toolbar;
    }

    /**
     * 吐出一个短的消息提示
     * @param message      消息内容
     */
    public void toastS(String message) {
        //  Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        Toast toast= Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    public void toastSError() {
        // Toast.makeText(getActivity(), "无法连接服务器", Toast.LENGTH_SHORT).show();
        Toast toast= Toast.makeText(getActivity(), "无法连接服务器", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    /**
     * 吐出一个短的消息提示
     *
     * @param messageId 消息ID
     */
    public void toastS(int messageId) {
//        Toast.makeText(getActivity(), getString(messageId),
//                Toast.LENGTH_SHORT).show();
        Toast toast= Toast.makeText(getActivity(), getString(messageId), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }



    /**
     * 开始加载数据时候的进度
     */
/*    public void startProgressDialog() {
        if (progressDialog == null) {
            progressDialog = CustomProgressDialog.createDialog(getActivity());
            progressDialog.setMessage("正在加载中...");
        }
        if (!getActivity().isFinishing()) {
            progressDialog.show();
        }
    }*/

    /**
     * 停止加载
     */
/*    public void stopProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }*/
    public void SkipActivity(Class activityClass) {
        Intent intent = new Intent(getActivity(), activityClass);
        startActivity(intent);
//        getActivity().overridePendingTransition(R.anim.grow_from_top,
//                R.anim.small_2_big);
    }

}






