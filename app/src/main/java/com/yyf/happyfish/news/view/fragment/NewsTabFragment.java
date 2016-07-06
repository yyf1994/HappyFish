package com.yyf.happyfish.news.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yyf.happyfish.R;
import com.yyf.happyfish.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsTabFragment extends BaseFragment implements OnTabSelectListener {

    @BindView(R.id.tab_layout)
    SlidingTabLayout tablayout;
//    TabLayout tablayout;


    @BindView(R.id.vp)
    ViewPager vp;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private List<String> list_title;
    private final String[] mTitles = {
            "头条", "社会", "国内", "国际"
            , "娱乐", "体育", "军事", "科技", "财经", "时尚"
    };
    //top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
    private final String[] mType = {
            "top", "shehui", "guonei", "guoji"
            , "yule", "tiyu", "junshi", "keji", "caijing", "shishang"
    };

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        for (String type : mType) {
            mFragments.add(NewsFragment.getInstance(type));
        }
        vp.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        tablayout.setViewPager(vp);
        vp.setCurrentItem(0);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onTabSelect(int position) {
    }

    @Override
    public void onTabReselect(int position) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}

