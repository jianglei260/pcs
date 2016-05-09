package com.cuit.backup;

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
public class BackupApp extends AppWrapper {

    public BackupApp(App app) {
        super(app);
    }

    @Override
    public void onStart(AppContext context) {
        super.onStart(context);
        BackupWindowPage backupWindowPage = new BackupWindowPage(this, context);
        startWindowPage(backupWindowPage);
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }
}
