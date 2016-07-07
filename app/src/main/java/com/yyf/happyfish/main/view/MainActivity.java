package com.yyf.happyfish.main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yyf.happyfish.R;
import com.yyf.happyfish.entity.TabEntity;
import com.yyf.happyfish.main.adapter.MainPagerAdapter;
import com.yyf.happyfish.news.view.fragment.NewsTabFragment;
import com.yyf.happyfish.setting.view.SettingFragment;
import com.yyf.happyfish.wechat.view.fragment.WeChatFragment;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    CommonTabLayout buttomlayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.toolbar_base)
    Toolbar toolbar;

    private WeChatFragment mWeChatFragment;
    private NewsTabFragment mNewsTabFragment;
    private WeChatFragment mMessageFragment2;
    private SettingFragment mSettingFragment;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MainPagerAdapter mViewPagerAdapter;
    Random mRandom = new Random();
    private String[] mTitles = {"微信", "新闻", "笑话", "设置"};
    private int[] mIconUnselectIds = {
            R.mipmap.xiaoxi, R.mipmap.zhanshang,
            R.mipmap.zhanpin, R.mipmap.zhanpin};
    private int[] mIconSelectIds = {
            R.mipmap.wechatselect, R.mipmap.wechatselect,
            R.mipmap.wechatselect, R.mipmap.wechatselect};
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * R.layout.activity_main2 为滑动时toolbar消失
         * activity_main 为滑动时toolbar 显示
         * */
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        initData();
        initView();
        setAdapter();

        /*当背景颜色为白色时，修改小米系统的状态栏的字体颜色为深色*/
        //StatusBarUtil.MIUISetStatusBarLightMode(getWindow(),true);

    }


    private void initView() {
        //初始化toolbar
        toolbar.setTitle("微信精选");
        setSupportActionBar(toolbar);

        buttomlayout.setTabData(mTabEntities);
        buttomlayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
                if (position == 0) {
                    toolbar.setTitle("微信精选");
                } else if (position == 1) {
                    toolbar.setTitle("新闻");
                } else if (position == 2) {
                    toolbar.setTitle("笑话");
                } else {
                    toolbar.setTitle("设置");
                }
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                    buttomlayout.showMsg(0, mRandom.nextInt(100) + 1);
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    toolbar.setTitle("微信精选");
                } else if (position == 1) {
                    toolbar.setTitle("新闻");
                } else if (position == 2) {
                    toolbar.setTitle("笑话");
                } else {
                    toolbar.setTitle("设置");
                }
            }

            @Override
            public void onPageSelected(int position) {
                buttomlayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(1);

        setSupportActionBar(toolbar);

    }


    private void initData() {

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mWeChatFragment = new WeChatFragment();
        mNewsTabFragment = new NewsTabFragment();
        mMessageFragment2 = new WeChatFragment();
        mSettingFragment = new SettingFragment();
        mFragments.add(mWeChatFragment);
        mFragments.add(mNewsTabFragment);
        mFragments.add(mMessageFragment2);
        mFragments.add(mSettingFragment);
    }

    private void setAdapter() {
        mViewPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
