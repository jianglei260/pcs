package com.cuit.pcs.apkloader;

import com.cuit.pcs.application.MyApplication;
import com.cuit.pcs.sys.window.WindowPage;
import com.cuit.pcs.ui.activity.MainActivity;

/**
 * Created by ASUS-1 on 2015/11/20.
 */
public class AppWrapper extends App {
    private App mApp;

    public AppWrapper(App app) {
        mApp = app;
    }

    @Override
    public void startWindowPage(WindowPage windowPage) {
        mApp.startWindowPage(windowPage);
    }

    @Override
    public void startWindowPageForResult(WindowPage windowPage, int result) {
        mApp.startWindowPageForResult(windowPage, result);
    }

    @Override
    public void onStart(AppContext context) {
        mApp.onStart(context);
    }

    @Override
    public void onFinish() {
        mApp.onFinish();
    }

    @Override
    public void finish() {
        mApp.finish();
    }

    @Override
    public void registerBroadCast(BroadcastCompoment broadcastCompoment) {
        mApp.registerBroadCast(broadcastCompoment);
    }

    @Override
    public void startService(ServiceCompoment serviceCompoment) {
        mApp.startService(serviceCompoment);
    }

    @Override
    public void onRemove() {
        mApp.onRemove();
    }

    @Override
    public void startApp(AppInfo appInfo) {
        mApp.startApp(appInfo);
    }

    @Override
    public void goBack() {
        mApp.goBack();
    }

    @Override
    public MainActivity getMainActivity() {
        return mApp.getMainActivity();
    }

    @Override
    public MyApplication getMyApplication() {
        return mApp.getMyApplication();
    }

    @Override
    public AppInfo getAppInfo() {
        return mApp.getAppInfo();
    }
}
