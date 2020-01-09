package com.cch.gsyvideoplayer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cch.gsyvideoplayer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NavigationFragment extends Fragment {
    @BindView(R.id.home)
    ImageView iv_home;
    @BindView(R.id.channel)
    ImageView iv_channel;
    @BindView(R.id.live)
    ImageView iv_live;

    @BindView(R.id.tv_home)
    TextView tv_home;
    @BindView(R.id.tv_channel)
    TextView tv_channel;
    @BindView(R.id.tv_live)
    TextView tv_live;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_navigation, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    //首页页面监听逻辑
    @OnClick(R.id.ll_home)
    void selectHome() {
        showFragment(iv_home);
        resetImageResource(iv_home);
    }

    @OnClick(R.id.ll_channel)
    void selectChannel() {
        showFragment(iv_channel);
        resetImageResource(iv_channel);
    }

    @OnClick(R.id.ll_live)
    void selectLive() {
        showFragment(iv_live);
        resetImageResource(iv_live);
    }

    /**
     * 选中逻辑
     */
    private void initView() {
        //默认页面
        resetImageResource(iv_home);
        showFragment(iv_home);
        /*//首页页面监听逻辑
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(iv_home);
                resetImageResource(iv_home);
            }
        });
        //频道页面监听逻辑
        iv_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(iv_channel);
                resetImageResource(iv_channel);
            }
        });
        //直播页面监听逻辑
        iv_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(iv_live);
                resetImageResource(iv_live);
            }
        });*/
    }

    /*
     * 是否选中显示
     * */
    private void resetImageResource(View icon) {
        icon.getId();
        //未选中现在
        iv_home.setImageResource(R.mipmap.home1);
        iv_channel.setImageResource(R.mipmap.channel);
        iv_live.setImageResource(R.mipmap.live2);


        tv_home.setTextColor(0xff212325);
        tv_channel.setTextColor(0xff212325);
        tv_live.setTextColor(0xff212325);

        //首页选中显示
        if (icon.getId() == R.id.home) {
            iv_home.setImageResource(R.mipmap.home2);
            tv_home.setTextColor(0xff1496DB);
        }
        //频道选中显示
        else if (icon.getId() == R.id.channel) {
            iv_channel.setImageResource(R.mipmap.channel2);
            tv_channel.setTextColor(0xff1496DB);
        }
        //直播选中显示
        else if (icon.getId() == R.id.live) {
            iv_live.setImageResource(R.mipmap.live);
            tv_live.setTextColor(0xff1496DB);
        }
    }

    private RecommendFragment recommendFragment;
    private TVFragment tvFragment;
    private LiveFragment liveFragment;

    /*
     * 是否选中状态
     * */
    private void showFragment(View icon) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (recommendFragment != null) {                                   //首页未选中执行
            transaction.hide(recommendFragment);
        }
        if (tvFragment != null) {                                          //频道未选中执行
            transaction.hide(tvFragment);
        }
        if (liveFragment != null) {                                        //直播未选中执行
            transaction.hide(liveFragment);
        }
        if (icon.getId() == R.id.home) {
            if (recommendFragment == null) {                            //首页未选中执行
                recommendFragment = new RecommendFragment();
                transaction.add(R.id.fl_tag, recommendFragment);       //显示位置及状态
            } else {
                transaction.show(recommendFragment);
            }
        }
        if (icon.getId() == R.id.channel) {
            if (tvFragment == null) {
                tvFragment = new TVFragment();
                transaction.add(R.id.fl_tag, tvFragment);
            } else {
                transaction.show(tvFragment);
            }
        }
        if (icon.getId() == R.id.live) {
            if (liveFragment == null) {
                liveFragment = new LiveFragment();
                transaction.add(R.id.fl_tag, liveFragment);
            } else {
                transaction.show(liveFragment);
            }
        }
        transaction.commit();                               //状态提交
    }
}

