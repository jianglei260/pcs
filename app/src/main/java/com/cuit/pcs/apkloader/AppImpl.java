package com.cuit.pcs.apkloader;

import com.cuit.pcs.application.MyApplication;
import com.cuit.pcs.sys.window.WindowPage;
import com.cuit.pcs.ui.activity.MainActivity;

/**
 * Created by ASUS-1 on 2015/11/20.
 */
public class AppImpl extends App {
    private AppInfo appInfo;

    public AppImpl(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    @Override
    public void startWindowPage(WindowPage windowPage) {
        AppManageService.getInstance().startWindowPage(this, windowPage);
    }

    @Override
    public void startWindowPageForResult(WindowPage windowPage, int result) {
        AppManageService.getInstance().startWindowPageForResult(this, windowPage, result);
    }

    @Override
    public void onStart(AppContext context) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void finish() {
        onFinish();
        AppManageService.getInstance().finish(this);
    }

    @Override
    public void registerBroadCast(BroadcastCompoment broadcastCompoment) {
        MyApplication.getInstance().registeBroadcastCompoment(broadcastCompoment);
    }

    @Override
    public void startService(ServiceCompoment serviceCompoment) {

    }

    @Override
    public void onRemove() {

    }

    @Override
    public void startApp(AppInfo appInfo) {
        AppManageService.getInstance().start(appInfo);
    }

    @Override
    public void goBack() {
        AppManageService.getInstance().goBack(this);
    }

    @Override
    public MainActivity getMainActivity() {
        return MyApplication.getInstance().getMainActivity();
    }

    @Override
    public MyApplication getMyApplication() {
        return MyApplication.getInstance();
    }

    @Override
    public AppInfo getAppInfo() {
        return appInfo;
    }
}
