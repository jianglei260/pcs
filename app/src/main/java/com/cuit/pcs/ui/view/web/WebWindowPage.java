package com.cuit.pcs.ui.view.web;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cuit.pcs.R;
import com.cuit.pcs.apkloader.BaseJsInterface;
import com.cuit.pcs.application.Config;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.sys.window.WindowPage;

import dalvik.system.DexClassLoader;


/**
 * Created by ASUS-1 on 2015/10/15.
 */
public class WebWindowPage extends WindowPage {

    private Context context;
    private String title;
    private WebView webView;
    private WebChromeClient webChromeClient;
    private WebViewClient webViewClient;
    private String url;
    private RelativeLayout rootView, bottomBar;
    private ImageView goBackImage, goForwardImage, refreshImage;
    private boolean navigationBarVisible = true;

    public WebWindowPage(Context context, String title, String url) {
        super(context);
        this.context = context;
        this.title = title;
        this.url = url;
        init();
    }

    public WebWindowPage(Context context, String title, String url, boolean navigationBarVisible) {
        super(context);
        this.context = context;
        this.title = title;
        this.url = url;
        this.navigationBarVisible = navigationBarVisible;
        init();
    }

    public void init() {
        rootView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.web_layout, null);
        webView = (WebView) rootView.findViewById(R.id.webView);
        goBackImage = (ImageView) rootView.findViewById(R.id.goBack);
        goForwardImage = (ImageView) rootView.findViewById(R.id.goFroward);
        refreshImage = (ImageView) rootView.findViewById(R.id.refresh);
        bottomBar = (RelativeLayout) rootView.findViewById(R.id.bottomBar);
        setNavigationBarVisible(navigationBarVisible);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webChromeClient = new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

        };
        webViewClient = new WebViewClient() {
            @Override
            public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    }
                }
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                //               checkCanGoBack();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                checkCanGoBack();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                titleBar.setTitleText(view.getTitle());
            }
        };
        webView.getSettings().setSupportZoom(true);
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setGeolocationDatabasePath(context.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getAbsolutePath());

        titleBar.setTitleText(title);
        titleBar.setLeftImageOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FastWindow.getInstance().goBack();
            }
        });
        addBodyView(rootView);

        goBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack())
                    webView.goBack();
            }
        });

        goForwardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoForward()) {
                    webView.goForward();
                }
            }
        });

        refreshImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });
    }

    public void initUI(){
        webView.loadUrl(url);
    }

    private void checkCanGoBack() {
        if (webView.canGoForward()) {
            goForwardImage.setImageResource(R.mipmap.ic_goforward);
            goForwardImage.setClickable(true);
        } else {
            goForwardImage.setImageResource(R.mipmap.ic_goforward_gray);
            goForwardImage.setClickable(false);
        }
        if (webView.canGoBack()) {
            goBackImage.setImageResource(R.mipmap.ic_goback);
            goBackImage.setClickable(true);
        } else {
            goBackImage.setImageResource(R.mipmap.ic_goback_gray);
            goBackImage.setClickable(false);
        }
    }

    public void setNavigationBarVisible(boolean visible) {
        navigationBarVisible = visible;
        if (visible)
            bottomBar.setVisibility(View.VISIBLE);
        else
            bottomBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }

    public void setJsInterface(Class<? extends BaseJsInterface> jsInterface) {
        try {
            BaseJsInterface baseJsInterface = jsInterface.newInstance();
            webView.addJavascriptInterface(baseJsInterface, jsInterface.getName());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void loadInterface(String path, String classPath) {
        DexClassLoader dexClassLoader = new DexClassLoader(path, Config.DexOptimizedDirectory, null, this.getClass().getClassLoader());
        try {
            Class<? extends BaseJsInterface> jsInterface = dexClassLoader.loadClass(classPath).asSubclass(BaseJsInterface.class);
            setJsInterface(jsInterface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
