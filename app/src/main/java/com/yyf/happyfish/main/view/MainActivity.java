package com.yyf.happyfish.main.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.yyf.happyfish.R;
import com.yyf.happyfish.entity.TabEntity;
import com.yyf.happyfish.main.adapter.MainPagerAdapter;
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
    @BindView(R.id.toolbar_title)
    TextView title;

    private WeChatFragment mWeChatFragment;
    private WeChatFragment mMessageFragment1;
    private WeChatFragment mMessageFragment2;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MainPagerAdapter mViewPagerAdapter;
    Random mRandom = new Random();
    private String[] mTitles = {"微信", "新闻", "笑话"};
    private int[] mIconUnselectIds = {
            R.mipmap.xiaoxi, R.mipmap.zhanshang,
            R.mipmap.zhanpin};
    private int[] mIconSelectIds = {
            R.mipmap.xiaoxixuanzhong, R.mipmap.zhanshangxuanzhong,
            R.mipmap.zhanpinxuanzhong};
    private Context mContext = this;
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

    }

    private void initView() {
        //初始化toolbar
        setSupportActionBar(toolbar);
        title.setText("微信精选");

        buttomlayout.setTabData(mTabEntities);
        buttomlayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
                if (position == 0) {
                    title.setText("微信精选");
                }else if(position ==1){
                    title.setText("新闻");
                }else{
                    title.setText("笑话");
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
                    title.setText("微信精选");
                }else if(position ==1){
                    title.setText("新闻");
                }else{
                    title.setText("笑话");
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

    }


    private void initData() {

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mWeChatFragment = new WeChatFragment();
        mMessageFragment1 = new WeChatFragment();
        mMessageFragment2 = new WeChatFragment();
        mFragments.add(mWeChatFragment);
        mFragments.add(mMessageFragment1);
        mFragments.add(mMessageFragment2);

    }

    private void setAdapter() {
        mViewPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(),mFragments,mTitles);
        viewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
