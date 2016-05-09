package com.cuit.news;

import android.content.Context;
import android.view.View;

import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppContext;
import com.cuit.pcs.apkloader.AppWrapper;
import com.cuit.pcs.apkloader.IApp;
import com.cuit.pcs.sys.window.FastWindow;

/**
 * Created by ASUS-1 on 2015/9/11.
 */
public class WeatherApp extends AppWrapper {


    public WeatherApp(App app) {
        super(app);
    }

    @Override
    public void onStart(AppContext context) {
        super.onStart(context);
        WeatherWindowPage weatherWindowPage = new WeatherWindowPage(this, context);
        weatherWindowPage.initUI();
        startWindowPage(weatherWindowPage);
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }
}
