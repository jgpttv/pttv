package com.cch.gsyvideoplayer;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.cch.gsyvideoplayer.util.JsonUtil;
import com.cch.gsyvideoplayer.util.SpTools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class App extends Application {

    public static Context app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = getApplicationContext();
        initOkHttp();
    }

    private void initOkHttp() {
        CookieJar cookieJar = new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (cookies != null) {
                    SpTools.setString("cookies", JsonUtil.toJson(cookies));
                    Log.d("cookie", "保存cookies：" + JsonUtil.toJson(cookies));
                }
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                String cookiesStr = SpTools.getString("cookies", "");
                ArrayList<Cookie> cookies = new ArrayList<>();
                Log.d("cookie", "取出cookies：" + cookiesStr);
                if (!TextUtils.isEmpty(cookiesStr)) {
                    List<Cookie> list = JsonUtil.toList(cookiesStr, Cookie.class);
                    if (list != null) {
                        cookies.addAll(list);
                    }
                }
                return cookies;
            }
        };
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TV_OKHTTP"))
                .cookieJar(cookieJar)
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)

                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
