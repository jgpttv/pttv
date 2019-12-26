package com.cch.gsyvideoplayer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cch.gsyvideoplayer.R;
import com.cch.gsyvideoplayer.adapter.CommAdapter;
import com.cch.gsyvideoplayer.adapter.ViewHolder;
import com.cch.gsyvideoplayer.bean.VideoInfo;
import com.cch.gsyvideoplayer.http.API;
import com.cch.gsyvideoplayer.http.ListCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.gv_list)
    GridView gv_list;
    private ArrayList<VideoInfo> videoInfos;
    private CommAdapter<VideoInfo> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {

        //发送网络请求获取数据
        OkHttpUtils
                .get()
                .url(API.get_index_list)
                .build()
                .execute(new ListCallBack<VideoInfo>(MainActivity.this) {

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                        // srl_refresh.setRefreshing(true);
                    }

                    @Override
                    public void onError(MyError e) {
                        //setAdInfo(getDefAd());
                        // srl_refresh.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(List<VideoInfo> list) {
                        //srl_refresh.setRefreshing(false);
                        videoInfos.clear();
                        if (list != null && list.size() != 0) {
                            videoInfos.addAll(list);
                        }

                        adapter.notifyDataSetChanged();
                    }
                });

       /* videoInfos.add(new VideoInfo("湖南卫视","http://116.199.5.51:8114/index.m3u8?Fsv_chan_hls_se_idx=6&FvSeid=1&Fsv_filetype=1&Fsv_ctype=LIVES&Fsv_cid=0&Fsv_chan_hls_se_idx=6&Fsv_rate_id=2&Fsv_SV_PARAM1=0&Fsv_ShiftEnable=0&Fsv_ShiftTsp=0&Provider_id=&Pcontent_id=.m3u8&Fsv_CMSID=&Fsv_otype=1"));
        videoInfos.add(new VideoInfo("浙江卫视","http://ali.m.l.cztv.com/channels/lantian/channel01/360p.m3u8"));
        videoInfos.add(new VideoInfo("东方卫视","http://cctvalih5ca.v.myalicdn.com/wstv/dongfang_2/index.m3u8"));
        videoInfos.add(new VideoInfo("广东卫视","http://cctvalih5ca.v.myalicdn.com/wstv/guangdong_2/index.m3u8"));
        videoInfos.add(new VideoInfo("广西卫视","http://stream.cdn.liangtv.cn/live/liangtv_gxws/playlist.m3u8?wsSession=-"));
        videoInfos.add(new VideoInfo("云南卫视","http://cctvalih5ca.v.myalicdn.com/wstv/yunnan_2/index.m3u8"));
        videoInfos.add(new VideoInfo("四川卫视","http://cctvalih5ca.v.myalicdn.com/wstv/sichuan_2/index.m3u8"));
        videoInfos.add(new VideoInfo("湖北卫视","http://cctvalih5ca.v.myalicdn.com/wstv/hubei_2/index.m3u8"));
        videoInfos.add(new VideoInfo("安徽卫视","http://cctvalih5ca.v.myalicdn.com/wstv/anhui_2/index.m3u8"));
        videoInfos.add(new VideoInfo("贵州卫视","http://cctvalih5ca.v.myalicdn.com/wstv/guizhou_2/index.m3u8"));
        videoInfos.add(new VideoInfo("青海卫视","http://cctvalih5ca.v.myalicdn.com/wstv/qinghai_2/index.m3u8"));
        videoInfos.add(new VideoInfo("山东卫视","http://cctvalih5ca.v.myalicdn.com/wstv/shandong_2/index.m3u8"));
        videoInfos.add(new VideoInfo("山西卫视","http://cctvalih5ca.v.myalicdn.com/wstv/shan1xi_2/index.m3u8"));
        videoInfos.add(new VideoInfo("河南卫视","http://cctvalih5ca.v.myalicdn.com/wstv/henan_2/index.m3u8"));
     */   //videoInfos.add(new VideoInfo("湖南卫视",""));
        //videoInfos.add(new VideoInfo("湖南卫视",""));


    }

    private void initView() {
        videoInfos = new ArrayList<>();
        adapter = new CommAdapter<VideoInfo>(this, videoInfos, R.layout.video_item) {
            @Override
            public void convert(ViewHolder holder, VideoInfo videoInfo) {
                TextView tv_name = holder.getView(R.id.tv_name);
                ImageView iv_icon = holder.getView(R.id.iv_icon);
                tv_name.setText(videoInfo.getTv_name());
                Glide.with(MainActivity.this)
                        .load(videoInfo.getTv_icon())
                        .into(iv_icon);
            }
        };

        gv_list.setAdapter(adapter);
        gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoInfo videoInfo = videoInfos.get(position);
                if (videoInfo != null && videoInfo.getTv_sources() != null && videoInfo.getTv_sources().size() != 0) {
                    Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                    intent.putExtra(PlayerActivity.VIDEO_NAME_EXTRA, videoInfo.getTv_name());
                    intent.putExtra(PlayerActivity.VIDEO_SOURCE_EXTRA, videoInfo.getTv_sources().get(0).getSource_url());
                    startActivity(intent);
                }
            }
        });
    }


}