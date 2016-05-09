package com.cuit.remind;

import android.content.Context;
import android.view.View;

import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppContext;
import com.cuit.pcs.apkloader.AppWrapper;
import com.cuit.pcs.apkloader.IApp;
import com.cuit.pcs.sys.window.FastWindow;

/**
 * Created by ASUS-1 on 2015/9/13.
 */
public class RemindApp extends AppWrapper {

    public RemindApp(App app) {
        super(app);
    }

    @Override
    public void onStart(AppContext context) {
        super.onStart(context);
        RemindWindowPage remindWindowPage = new RemindWindowPage(this, context);
        remindWindowPage.initUI();
        startWindowPage(remindWindowPage);
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

}
