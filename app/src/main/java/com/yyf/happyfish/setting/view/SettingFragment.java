package com.yyf.happyfish.setting.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yyf.happyfish.R;
import com.yyf.happyfish.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by yyf on 2016/6/12.
 */
public class SettingFragment extends BaseFragment {

    @BindView(R.id.yejianmoshi)
    TextView yejianmoshi;

    private Unbinder unbinder;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        unbinder = ButterKnife.bind(this, view);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
