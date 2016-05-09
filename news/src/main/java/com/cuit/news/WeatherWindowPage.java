package com.cuit.news;

import android.content.Context;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppContext;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.sys.window.WindowPage;
import com.cuit.pcs.webloader.WebLoader;

/**
 * Created by ASUS-1 on 2015/9/11.
 */
public class WeatherWindowPage extends WindowPage {
    private AppContext context;
    private WebLoader webLoader;
    private App app;

    public WeatherWindowPage(App app, AppContext context) {
        super(app.getMainActivity());
        this.context = context;
        this.app = app;
    }

    public void initUI() {
        webLoader = new WebLoader(context);
        titleBar.setTitleText("天气信息");
        titleBar.setLeftImageOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.goBack();
            }
        });
        addBodyView(webLoader.load("http://pcs.avosapps.com/weather"));
    }
}
