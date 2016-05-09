package com.cuit.pcs.webloader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.fastjson.JSON;
import com.cuit.pcs.apkloader.AppManageService;
import com.cuit.pcs.application.MyApplication;
import com.cuit.pcs.sys.user.User;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.ui.view.login.LoginWindowPage;

/**
 * Created by ASUS-1 on 2015/9/11.
 */
public class WebLoader {
    private WebView webView;
    private Context context;
    private WebChromeClient webChromeClient;
    private WebViewClient webViewClient;

    public WebLoader(Context context) {
        this.context = context;
        webView = new WebView(context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsInterface(), "pcs");
        webChromeClient = new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

        };
        webViewClient = new WebViewClient() {

        };
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setGeolocationDatabasePath(context.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getAbsolutePath());

    }


    public WebView load(String url) {
        webView.loadUrl(url);
        return webView;
    }

    public class JsInterface {
        @JavascriptInterface
        public String getUserName() {
            if (User.getInstance().getUserInfo() != null)
                return User.getInstance().getUserInfo().getUserName();
            else FastWindow.getInstance().startWindow(new LoginWindowPage(context));
            return null;
        }

        @JavascriptInterface
        public void goBack() {
            MyApplication.getInstance().getMainActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AppManageService.getInstance().onBackPressed();
                }
            });
        }
    }

    public void setWebViewClient(WebViewClient webViewClient) {
        webView.setWebViewClient(webViewClient);
    }

    public void setWebChromeClient(WebChromeClient webChromeClient) {
        webView.setWebChromeClient(webChromeClient);
    }
}
