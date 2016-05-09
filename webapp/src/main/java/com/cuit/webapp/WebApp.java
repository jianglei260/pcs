package com.cuit.webapp;

import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppContext;
import com.cuit.pcs.apkloader.AppWrapper;

/**
 * Created by ASUS-1 on 2015/11/19.
 */
public class WebApp extends AppWrapper {
    private String defaultPath = "file:///android_asset/index.html";

    public WebApp(App app) {
        super(app);
    }

    @Override
    public void onStart(AppContext context) {
        super.onStart(context);

        WebWindowPage webWindowPage = new WebWindowPage(this, context, getAppInfo().getName(), defaultPath, false);
        webWindowPage.initUI();
        startWindowPage(webWindowPage);
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }
}
