package com.cch.gsyvideoplayer.bean;

public class TvChannelInfo {


    /**
     * icon : https://raw.githubusercontent.com/jgpttv/pttv/master/image/channel/channel_hot.png
     * name : 热播
     * url : https://raw.githubusercontent.com/jgpttv/pttv/master/json/channels/channel_hot.json
     */

    private String icon;
    private String name;
    private String url;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
