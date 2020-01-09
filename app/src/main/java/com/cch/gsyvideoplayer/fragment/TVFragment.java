package com.cch.gsyvideoplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cch.gsyvideoplayer.R;
import com.cch.gsyvideoplayer.activity.TvChannelListActivity;
import com.cch.gsyvideoplayer.adapter.TvChannelAdapter;
import com.cch.gsyvideoplayer.bean.TvChannelInfo;
import com.cch.gsyvideoplayer.http.API;
import com.cch.gsyvideoplayer.http.ListCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TVFragment extends Fragment {

    @BindView(R.id.rv_list)RecyclerView rv_list;

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srl_refresh;

    private ArrayList<TvChannelInfo> datas;
    private TvChannelAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tv, container, false);
        ButterKnife.bind(this,view);
        initView();
        initData();
        return view;
    }



    private void initView() {
        //初始化下拉刷新
        srl_refresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light);
        //给swipeRefreshLayout绑定刷新监听
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });


        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);

        rv_list.setLayoutManager(layoutManager);

        datas = new ArrayList<>();
        adapter = new TvChannelAdapter(getActivity(), datas);
        adapter.setOnItemClickListener(new TvChannelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TvChannelInfo info) {
                Intent intent = new Intent(getActivity(), TvChannelListActivity.class);
                intent.putExtra(TvChannelListActivity.EXTRA_NAME, info.getName());
                intent.putExtra(TvChannelListActivity.EXTRA_DATA_URL, info.getUrl());
                startActivity(intent);
            }
        });
        rv_list.setAdapter(adapter);
    }

    private void initData() {
        //加载频道数据
        OkHttpUtils.get().url(API.get_channel_datas).build().execute(new ListCallBack<TvChannelInfo>(getActivity()) {

            @Override
            public void onError(MyError e) {
                //关闭下拉刷新
                srl_refresh.setRefreshing(false);
                Toast.makeText(getActivity(), "请求失败：" + e.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<TvChannelInfo> list) {
                //关闭下拉刷新
                srl_refresh.setRefreshing(false);
                datas.clear();
                if(list!=null){
                    datas.addAll(list);
                }

                adapter.notifyDataSetChanged();
            }
        });
    }
}
