package com.yyf.happyfish.news.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yyf.happyfish.R;
import com.yyf.happyfish.base.BaseFragment;
import com.yyf.happyfish.news.adapter.NewsAdapter;
import com.yyf.happyfish.news.contract.NewsContract;
import com.yyf.happyfish.news.model.NewsEntity;
import com.yyf.happyfish.news.model.NewsListEntity;
import com.yyf.happyfish.news.model.NewsResultEntity;
import com.yyf.happyfish.news.presenter.NewsPresenter;
import com.yyf.happyfish.util.CheckNetUtil;
import com.yyf.happyfish.util.DiskCacheUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class NewsFragment extends BaseFragment implements NewsContract.View, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private List<NewsListEntity> mData;
    private List<NewsListEntity> mAllData;
    private LinearLayoutManager linearLayoutManager;
    private int mCurrentCounter = 0;
    private NewsContract.Present mPresent;
    private NewsAdapter mAdapter;
    private NewsEntity newsEntity;
    private NewsResultEntity resultEntity;
    private List<NewsListEntity> list;
    private DiskCacheUtil diskCacheUtil;
    private CheckNetUtil checkNetUtil = new CheckNetUtil();
    public static boolean isConnected;
    private String key = "254079df18196a395ab86883e659b8dc";
    private String mType = "top";
    private int PAGE_SIZE = 10;

    public static NewsFragment getInstance(String type) {
        NewsFragment nf = new NewsFragment();
        nf.mType = type;
        return nf;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        mAllData = new ArrayList<>();

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
        if (checkNetUtil.isNetworkConnected(getActivity())) {

            mPresent.getData(view, mType, key);
        } else {
            //无网络时加载缓存
            List<NewsListEntity> list1 = diskCacheUtil.getAsSerializable("NewsListEntity");
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
//        Intent intent = new Intent(getActivity().getApplicationContext(), ReceiveMsgService.class);
//        getActivity().getApplicationContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
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

    private void initParams() {
        mPresent = new NewsPresenter(this);
    }

    private void initAdapter() {
        mAdapter = new NewsAdapter(getActivity(), mData);
        mAdapter.openLoadAnimation();
        recyclerView.setAdapter(mAdapter);
        mCurrentCounter = mAdapter.getItemCount();

        if (checkNetUtil.isNetworkConnected(getActivity())) {
            mAdapter.setOnLoadMoreListener(this);
            mAdapter.openLoadMore(PAGE_SIZE, true);
        }
//        if (isConnected) {
//            mAdapter.setOnLoadMoreListener(this);
//            mAdapter.openLoadMore(PAGE_SIZE, true);
//        }

        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(getActivity(), WeChatDetailActivity.class);
//                intent.putExtra("result", mData.get(position));
//                startActivity(intent);
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

    @OnClick(R.id.fab)
    public void fab() {
        linearLayoutManager.scrollToPosition(0);
    }

    @Override
    public void onRefresh() {
        if (checkNetUtil.isNetworkConnected(getActivity())) {
            mPresent.pulldowntorefresh(mType, key);
        }

    }

    @Override
    public void onLoadMoreRequested() {
        if (checkNetUtil.isNetworkConnected(getActivity())) {
            mPresent.upload(mType, key);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (checkNetUtil.isNetworkConnected(getActivity())) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        }

        if (isConnected) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        //取消请求
        if (checkNetUtil.isNetworkConnected(getActivity())) {
            mPresent.cancelRequest();
        }
        if (isConnected) {
            mPresent.cancelRequest();
        }

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void responseSuccess(Response<NewsEntity> response, View view) {
        loadNewsData(response);
    }

    private void loadNewsData(Response<NewsEntity> response) {
        newsEntity = response.body();
        resultEntity = newsEntity.getResult();
        list = resultEntity.getData();
        diskCacheUtil.put("NewsListEntity", (Serializable) list);
        mData = list;
        mAllData.clear();
        mAllData.addAll(mData);
        mAdapter.setNewData(mData);
        if (mData.size() == 0) {
            View emptyView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
            mAdapter.setEmptyView(emptyView);
        }
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void responseFailed(Call<NewsEntity> call, Throwable t) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
        t.printStackTrace();
        Toast.makeText(getActivity(),"网络连接超时",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RefreshSuccess(Response<NewsEntity> response) {
        loadNewsData(response);
    }

    @Override
    public void LoadSuccess(Response<NewsEntity> response) {

    }

    @Override
    public void setPresenter(NewsContract.Present presenter) {

    }
}

