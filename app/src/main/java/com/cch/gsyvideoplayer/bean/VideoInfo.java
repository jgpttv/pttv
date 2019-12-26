package com.cch.gsyvideoplayer.bean;

import java.util.List;

public class VideoInfo {
    private String tv_name;
    private String tv_icon;
    private List<SourceInfo> tv_sources;

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_icon() {
        return tv_icon;
    }

    public void setTv_icon(String tv_icon) {
        this.tv_icon = tv_icon;
    }

    public List<SourceInfo> getTv_sources() {
        return tv_sources;
    }

    public void setTv_sources(List<SourceInfo> tv_sources) {
        this.tv_sources = tv_sources;
    }
}
