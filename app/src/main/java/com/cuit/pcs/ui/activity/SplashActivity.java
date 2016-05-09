package com.cuit.pcs.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cuit.pcs.R;
import com.cuit.pcs.application.MyApplication;

public class SplashActivity extends Activity {

    private WebView webView;
    private boolean isFirst;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("boot_recored", Context.MODE_PRIVATE);
        isFirst = sharedPreferences.getBoolean("isFirst", true);
        if (isFirst) {
            startGuide();
            sharedPreferences.edit().putBoolean("isFirst", false).commit();
        } else {
            startSplash();
        }
    }

    public void startGuide() {
        webView = new WebView(this);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

        });
        webView.setWebChromeClient(new WebChromeClient() {

        });
        webView.loadUrl("file:///android_asset/guide/index.html");
        webView.addJavascriptInterface(new JsAPI(), "jsapi");
        setContentView(webView);
    }

    public void startSplash() {
        setContentView(R.layout.splash);
        imageView = (ImageView) findViewById(R.id.splash_image);
        imageView.setBackgroundColor(Color.WHITE);
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_anmi));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 1000);
    }

    public class JsAPI {
        @JavascriptInterface
        public void start() {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            SplashActivity.this.startActivity(intent);
            SplashActivity.this.finish();
        }
    }
}
