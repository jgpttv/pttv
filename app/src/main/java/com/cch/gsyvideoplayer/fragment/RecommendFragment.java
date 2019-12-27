package com.cch.gsyvideoplayer.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cch.gsyvideoplayer.R;

import butterknife.OnClick;

public class RecommendFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recomment, container, false);
        return view;
    }
    //点击打开频道界面
    @OnClick(R.id.gengduo_1)
    void zhuan1(){

    }
}
