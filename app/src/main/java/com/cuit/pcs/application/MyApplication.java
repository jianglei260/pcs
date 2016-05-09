package com.cuit.pcs.application;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.PushService;
import com.cuit.pcs.R;
import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppManageService;
import com.cuit.pcs.apkloader.BroadcastCompoment;
import com.cuit.pcs.apkloader.ServiceCompoment;
import com.cuit.pcs.ui.activity.MainActivity;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS-1 on 2015/9/9.
 */
public class MyApplication extends Application {
    private static final String APPID = "dKXQtXhGwST9juoRgiNTuu51";
    private static final String APPKEY = "f3dAM7tVDHzsFg11wKJkkTpi";
    private static MyApplication myApplication;
    private MainActivity mainActivity;
    private BitmapUtils bitmapUtils;
    private List<BroadcastCompoment> broadcastCompomentList;
    private List<ServiceCompoment> serviceCompomentList;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        initLeanCloud();
        bitmapUtils = new BitmapUtils(this);
        BitmapDisplayConfig bitmapDisplayConfig = new BitmapDisplayConfig();
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_default_gray);
        bitmapDisplayConfig.setLoadingDrawable(drawable);
        bitmapUtils.configDefaultDisplayConfig(bitmapDisplayConfig);
        bitmapUtils.configDefaultAutoRotation(true);
        broadcastCompomentList = new ArrayList<>();
        serviceCompomentList = new ArrayList<>();
        Config.initConfig();
        Log.d(TAG, "onCreate: ");
    }

    private static final String TAG = "MyApplication";

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    private void initLeanCloud() {
        AVOSCloud.initialize(this, APPID, APPKEY);
        AVInstallation.getCurrentInstallation().saveInBackground();
        PushService.setDefaultPushCallback(this, MainActivity.class);
    }

    @Override
    public Resources getResources() {
        App runningApp = AppManageService.getInstance().getRunningApp();
        if (runningApp == null)
            return super.getResources();
        return AppManageService.getInstance().currentContext.getResources();
    }

    @Override
    public AssetManager getAssets() {
        Log.d("MyApplication", "getAssets");
        return AppManageService.getInstance().getRunningApp() != null ? AppManageService.getInstance().currentContext.getAssets() : super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return AppManageService.getInstance().getRunningApp() != null ? AppManageService.getInstance().getTask(AppManageService.getInstance().getRunningApp()).getDexClassLoader() : super.getClassLoader();
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public BitmapUtils getBitmapUtils() {
        return bitmapUtils;
    }

    public void registeBroadcastCompoment(BroadcastCompoment broadcastCompoment) {
        broadcastCompomentList.add(broadcastCompoment);
    }

    public void registeServiceCompoment(ServiceCompoment serviceCompoment) {
        serviceCompomentList.add(serviceCompoment);
    }

    public List<BroadcastCompoment> getBroadcastCompomentList() {
        return broadcastCompomentList;
    }

    public List<ServiceCompoment> getServiceCompomentList() {
        return serviceCompomentList;
    }
}
