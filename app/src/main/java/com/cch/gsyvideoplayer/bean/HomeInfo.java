package com.cch.gsyvideoplayer.bean;

import java.util.List;

public class HomeInfo {
   private List<BannerInfo> banners;//	Array
   private List<VideoInfo> net_rebos;//	Array
   private List<VideoInfo> rebos;//	Array
   private List<VideoInfo> tv_rebos;//	Array

    public List<BannerInfo> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerInfo> banners) {
        this.banners = banners;
    }

    public List<VideoInfo> getNet_rebos() {
        return net_rebos;
    }

    public void setNet_rebos(List<VideoInfo> net_rebos) {
        this.net_rebos = net_rebos;
    }

    public List<VideoInfo> getRebos() {
        return rebos;
    }

    public void setRebos(List<VideoInfo> rebos) {
        this.rebos = rebos;
    }

    public List<VideoInfo> getTv_rebos() {
        return tv_rebos;
    }

    public void setTv_rebos(List<VideoInfo> tv_rebos) {
        this.tv_rebos = tv_rebos;
    }
}
