package com.yyf.happyfish.news.view.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yyf.happyfish.R;
import com.yyf.happyfish.base.BaseFragment;
import com.yyf.happyfish.receiver.NetWorkBroadcastReceiver;
import com.yyf.happyfish.service.ReceiveMsgService;
import com.yyf.happyfish.util.CheckNetUtil;
import com.yyf.happyfish.util.DiskCacheUtil;
import com.yyf.happyfish.wechat.adapter.WeChatAdapter;
import com.yyf.happyfish.wechat.contract.WeChatContract;
import com.yyf.happyfish.wechat.model.ListEntity;
import com.yyf.happyfish.wechat.model.ResultEntity;
import com.yyf.happyfish.wechat.model.WeChatEntity;
import com.yyf.happyfish.wechat.presenter.WeChatPresenter;
import com.yyf.happyfish.wechat.view.activity.WeChatDetailActivity;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class NewsFragment extends BaseFragment implements WeChatContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<ListEntity> mData;
    private LinearLayoutManager linearLayoutManager;
    private static int TOTAL_COUNTER = 0;
    private static final int PAGE_SIZE = 6;//正式使用的时候设置为10，让第一屏满屏
    private int mCurrentCounter = 0;
    private WeChatContract.Present mPresent;
    private WeChatAdapter mAdapter;
    private WeChatEntity weChatEntity;
    private ResultEntity resultEntity;
    private List<ListEntity> list;
    private DiskCacheUtil diskCacheUtil;
    private Intent intent;
    private CheckNetUtil checkNetUtil = new CheckNetUtil();
    public static boolean isConnected;
    private  NetWorkBroadcastReceiver myReceiver;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        //设置recyclerview
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        //设置加载时进度条颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorPrimaryDark, R.color.colorPrimaryDark);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        initParams();
        initAdapter();
        setListener();
        //初始化数据
        if(checkNetUtil.isNetworkConnected(getActivity())){

            mPresent.getData(view);
        }
      /*  if(isConnected){
            mPresent.getData(view);
        }*/
        else{
            //无网络时加载缓存
            List<ListEntity> list1 = diskCacheUtil.getAsSerializable("ListEntity");
            mData = list1;
            mAdapter.setNewData(mData);
            //无网络时禁止刷新
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setEnabled(false);
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume","onResume");
        Intent intent = new Intent(getActivity().getApplicationContext(), ReceiveMsgService.class);
        getActivity().getApplicationContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wechat;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            diskCacheUtil = new DiskCacheUtil(getActivity());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setPresenter(WeChatContract.Present presenter) {

    }

    private void initParams() {
        mPresent = new WeChatPresenter(this);
    }

    private void initAdapter() {
        mAdapter = new WeChatAdapter(getActivity(), mData);
        mAdapter.openLoadAnimation();
        recyclerView.setAdapter(mAdapter);
        mCurrentCounter = mAdapter.getItemCount();

        if(checkNetUtil.isNetworkConnected(getActivity())){
            mAdapter.setOnLoadMoreListener(this);
            mAdapter.openLoadMore(PAGE_SIZE, true);
        }
       /* if(isConnected){
            mAdapter.setOnLoadMoreListener(this);
            mAdapter.openLoadMore(PAGE_SIZE, true);
        }*/
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), WeChatDetailActivity.class);
                intent.putExtra("result", mData.get(position));
                startActivity(intent);
            }
        });
        mAdapter.setOnRecyclerViewItemLongClickListener(new BaseQuickAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int i) {
                Toast.makeText(getActivity(), "长按应该加入什么功能呢", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void setListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        if(checkNetUtil.isNetworkConnected(getActivity())) {
            mPresent.pulldowntorefresh();
        }
        /*if(isConnected) {
            mPresent.pulldowntorefresh();
        }*/
    }

    @Override
    public void onLoadMoreRequested() {
        if(checkNetUtil.isNetworkConnected(getActivity())) {
            mPresent.upload();
        }
       /* if(isConnected) {
            mPresent.upload();
        }*/
    }

    @Override
    public void responseSuccess(Response<WeChatEntity> response, View view) {

        weChatEntity = response.body();
        String result = weChatEntity.getReason();
        if (result.equals("success")) {
            resultEntity = weChatEntity.getResult();
            list = resultEntity.getList();
            diskCacheUtil.put("ListEntity", (Serializable) list);
            TOTAL_COUNTER = resultEntity.getTotalPage();
            mData = list;
        }

        mAdapter.setNewData(mData);
        mCurrentCounter = PAGE_SIZE;
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void responseFailed(Call<WeChatEntity> call, Throwable t) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
        t.printStackTrace();
    }

    @Override
    public void RefreshSuccess(Response<WeChatEntity> response) {
        weChatEntity = response.body();
        String result = weChatEntity.getReason();

        if (result.equals("success")) {
            resultEntity = weChatEntity.getResult();
            list = resultEntity.getList();
            TOTAL_COUNTER = resultEntity.getTotalPage();
            mData = list;
        }
        mAdapter.setNewData(mData);
        mCurrentCounter = PAGE_SIZE;
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void LoadSuccess(Response<WeChatEntity> response) {
        if (mCurrentCounter >= TOTAL_COUNTER) {
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataChangedAfterLoadMore(false);
                }
            });

        } else {

            weChatEntity = response.body();
            String result = weChatEntity.getReason();

            if (result.equals("success")) {
                resultEntity = weChatEntity.getResult();
                list = resultEntity.getList();
                mData = list;
            }
            mAdapter.addData(mData);
            mAdapter.notifyDataChangedAfterLoadMore(mData, true);
            mCurrentCounter = mAdapter.getItemCount();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(checkNetUtil.isNetworkConnected(getActivity())) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        }

      /*  if(isConnected) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();
        //取消请求
        if(checkNetUtil.isNetworkConnected(getActivity())) {
            mPresent.cancelRequest();
        }
      /*  if(isConnected) {
            mPresent.cancelRequest();
        }*/
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getApplicationContext().unbindService(serviceConnection);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ReceiveMsgService receiveMsgService = ((ReceiveMsgService.MyBinder) service)
                    .getService();
            if(isConnected){
                handler.sendEmptyMessage(1);
            }else{
                handler.sendEmptyMessage(2);
            }
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:// 已连接
                    Log.d("handler","网络已经连接");
                    //网络连接之后可以刷新数据
//                    if (swipeRefreshLayout != null) {
//                        swipeRefreshLayout.setEnabled(true);
////                        swipeRefreshLayout.setRefreshing(true);
//                    }
//
//                    initAdapter();
//                    mPresent.pulldowntorefresh();
                    break;
                case 2:// 未连接
                    Log.d("handler","网络未连接");
                    break;
                default:
                    break;
            }
            ;
        };
    };

}

