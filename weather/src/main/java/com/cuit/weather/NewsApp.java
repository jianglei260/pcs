package com.cuit.weather;

import android.content.Context;
import android.view.View;

import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppContext;
import com.cuit.pcs.apkloader.AppWrapper;
import com.cuit.pcs.apkloader.IApp;
import com.cuit.pcs.sys.window.FastWindow;

/**
 * Created by ASUS-1 on 2015/9/12.
 */
public class NewsApp extends AppWrapper {

    public NewsApp(App app) {
        super(app);
    }

    @Override
    public void onStart(AppContext context) {
        super.onStart(context);
        NewsWindowPage newsWindowPage = new NewsWindowPage(this, context);
        newsWindowPage.initUI();
        startWindowPage(newsWindowPage);
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }
}
