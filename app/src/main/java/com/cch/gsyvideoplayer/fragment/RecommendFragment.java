package com.cch.gsyvideoplayer.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.cch.gsyvideoplayer.App;
import com.cch.gsyvideoplayer.R;
import com.cch.gsyvideoplayer.activity.MainActivity;
import com.cch.gsyvideoplayer.activity.PlayerActivity;
import com.cch.gsyvideoplayer.activity.WebViewActivity;
import com.cch.gsyvideoplayer.bean.BannerInfo;
import com.cch.gsyvideoplayer.bean.HomeInfo;
import com.cch.gsyvideoplayer.http.API;
import com.cch.gsyvideoplayer.http.ObjCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RecommendFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.convenientBanner)
    ConvenientBanner banner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recomment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initData() {
        //加载首页数据
        OkHttpUtils.get().url(API.get_home_data).build().execute(new ObjCallBack<HomeInfo>(getActivity()) {

            @Override
            public void onError(MyError e) {
                Toast.makeText(getActivity(),"请求失败："+e.getMsg(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(HomeInfo obj) {
                //数据获取成功
                //设置banner数据
                setBannerInfo(obj.getBanners());
            }
        });
    }

    private void setBannerInfo(final List<BannerInfo> banners) {
        ViewGroup.LayoutParams params = banner.getLayoutParams();
        int height = (int) (getScreenWidth() * (5f / 8f));
//        int height = dip2px(160);
        if (params == null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        }
        params.width = getScreenWidth();
        params.height = height;
        banner.setLayoutParams(params);
        banner.stopTurning();
        if (banners == null || banners.size() == 0) {
            banner.setVisibility(View.GONE);
        } else {
            banner.setVisibility(View.VISIBLE);
            banner.setPages(new CBViewHolderCreator() {

                @Override
                public Holder createHolder(View itemView) {
                    return new BannerItem(itemView);
                }

                @Override
                public int getLayoutId() {
                    return R.layout.item_banner;
                }
            }, banners)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                    //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)

                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            BannerInfo info = banners.get(position);
                            //ad_type 1：下载页面 2：h5页面
                            if (info.getType()==1) {
                                Intent intent = new Intent(getActivity(), PlayerActivity.class);
                                intent.putExtra(PlayerActivity.VIDEO_NAME_EXTRA, info.getTitle());
                                intent.putExtra(PlayerActivity.VIDEO_SOURCE_EXTRA, info.getUrl());
                                startActivity(intent);
                            } else if (info.getType()==2) {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri content_url = Uri.parse(info.getUrl());
                                intent.setData(content_url);
                                startActivity(intent);
                            } else if (info.getType()==3) {
                                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                                intent.putExtra("url", info.getUrl());
                                startActivity(intent);
                            }
                        }
                    });
        }
        banner.startTurning(5000);
    }

    //获取屏幕宽度
    private int getScreenWidth() {
        int screenW = 0;
        try {
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            screenW = dm.widthPixels;
        } catch (Exception e) {

        }
        return screenW;
    }

    private void initView() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    //点击打开频道界面
    @OnClick(R.id.gengduo_1)
    void zhuan1(){

    }

    class BannerItem extends Holder<BannerInfo> {
        private ImageView imageView;
        private TextView textView;


        public BannerItem(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            imageView=itemView.findViewById(R.id.iv_img);
            textView=itemView.findViewById(R.id.tv_title);
        }

        @Override
        public void updateUI(BannerInfo data) {
            Log.d("UpdateUI", "UpdateUI   BannerItem  data:" + data);
            Glide
                    .with(RecommendFragment.this)
                    .load(data.getImg())
                    .centerCrop()
//                    .placeholder(R.drawable.loading_spinner)
                    .into(imageView);
            textView.setText(data.getTitle());
        }
    }

}
