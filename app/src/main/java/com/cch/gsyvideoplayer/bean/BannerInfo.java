package com.cch.gsyvideoplayer.bean;

public class BannerInfo {

    /**
     * img : https://raw.githubusercontent.com/jgpttv/pttv/master/image/icon/tb_ws_hn.jpg
     * title : 湖南卫视type=1打开播放器页面
     * type : 1
     * url : http://116.199.5.51:8114/index.m3u8?Fsv_chan_hls_se_idx=6&FvSeid=1&Fsv_filetype=1&Fsv_ctype=LIVES&Fsv_cid=0&Fsv_chan_hls_se_idx=6&Fsv_rate_id=2&Fsv_SV_PARAM1=0&Fsv_ShiftEnable=0&Fsv_ShiftTsp=0&Provider_id=&Pcontent_id=.m3u8&Fsv_CMSID=&Fsv_otype=1
     */

    private String img;
    private String title;
    private int type;
    private String url;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
