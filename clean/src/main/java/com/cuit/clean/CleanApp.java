package com.cuit.clean;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.view.View;

import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppContext;
import com.cuit.pcs.apkloader.AppWrapper;
import com.cuit.pcs.apkloader.IApp;
import com.cuit.pcs.sys.window.FastWindow;

/**
 * Created by ASUS-1 on 2015/9/11.
 */
public class CleanApp extends AppWrapper {

    public CleanApp(App app) {
        super(app);
    }

    @Override
    public void onStart(AppContext context) {
        super.onStart(context);
        CleanWindowPage cleanWindowPage = new CleanWindowPage(this, context);
        cleanWindowPage.initUI();
        startWindowPage(cleanWindowPage);
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }
}
