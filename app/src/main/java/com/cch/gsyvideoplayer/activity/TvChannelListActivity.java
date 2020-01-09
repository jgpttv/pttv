package com.cch.gsyvideoplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.cch.gsyvideoplayer.R;
import com.cch.gsyvideoplayer.adapter.TvChannelListAdapter;
import com.cch.gsyvideoplayer.bean.TvChannelInfo;
import com.cch.gsyvideoplayer.bean.VideoInfo;
import com.cch.gsyvideoplayer.http.API;
import com.cch.gsyvideoplayer.http.ListCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TvChannelListActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_DATA_URL = "data_url";

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    @BindView(R.id.srl_refresh)
    SwipeRefreshLayout srl_refresh;


    private ArrayList<VideoInfo> datas;
    private TvChannelListAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvchannel_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        //加载频道数据
        String url = getIntent().getStringExtra(EXTRA_DATA_URL);
        OkHttpUtils.get().url(url).build().execute(new ListCallBack<VideoInfo>(this) {

            @Override
            public void onError(MyError e) {
                //关闭下拉刷新
                srl_refresh.setRefreshing(false);
                Toast.makeText(TvChannelListActivity.this, "请求失败：" + e.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<VideoInfo> list) {
                //关闭下拉刷新
                srl_refresh.setRefreshing(false);
                datas.clear();
                if (list != null) {
                    datas.addAll(list);
                }

                adapter.notifyDataSetChanged();
            }
        });
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


        String name = getIntent().getStringExtra(EXTRA_NAME);
        tv_title.setText(name);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        rv_list.setLayoutManager(layoutManager);

        datas = new ArrayList<>();
        adapter = new TvChannelListAdapter(this, datas);
        adapter.setOnItemClickListener(new TvChannelListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VideoInfo info) {
                Intent intent = new Intent(TvChannelListActivity.this, PlayerActivity.class);
                intent.putExtra(PlayerActivity.VIDEO_NAME_EXTRA, info.getTv_name());
                intent.putExtra(PlayerActivity.VIDEO_SOURCE_EXTRA, info.getTv_sources().get(0).getSource_url());
                startActivity(intent);
            }
        });
        rv_list.setAdapter(adapter);
    }

    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }
}
