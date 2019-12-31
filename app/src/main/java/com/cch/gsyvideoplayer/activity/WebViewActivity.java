package com.cch.gsyvideoplayer.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cch.gsyvideoplayer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_title)
    TextView tv_title;

    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        url = getIntent().getStringExtra("url");
        WebSettings webSettings = webview.getSettings();
        webSettings.setUseWideViewPort(false);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);  //支持js
        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setSupportZoom(false);  //支持缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);  //关闭webview中缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setBuiltInZoomControls(true); //设置支持缩放
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        //设置下载
        webview.setDownloadListener(new MyWebViewDownLoadListener());
        webview.setWebViewClient(new WebViewClient() {

            //覆写shouldOverrideUrlLoading实现内部显示网页
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                    return true;
                }


                view.loadUrl(url);

                return true;
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d("TITLE", "TITLE=" + title);
                tv_title.setText(title);
            }
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                super.onProgressChanged(webView, newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }
            }
        });
        webview.loadUrl(url);
    }
    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }
    @OnClick(R.id.iv_back)
    void back() {
        onBackPressed();
    }

    //下载，打开自带浏览器
    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Log.i("tag", "url=" + url);
            Log.i("tag", "userAgent=" + userAgent);
            Log.i("tag", "contentDisposition=" + contentDisposition);
            Log.i("tag", "mimetype=" + mimetype);
            Log.i("tag", "contentLength=" + contentLength);
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}
