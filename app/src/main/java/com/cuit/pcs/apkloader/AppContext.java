package com.cuit.pcs.apkloader;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;

import org.chromium.base.Log;

import java.lang.reflect.Method;

/**
 * Created by ASUS-1 on 2015/11/20.
 */
public class AppContext extends ContextWrapper {
    private Context mBase;
    private AppInfo mAppInfo;
    private AssetManager mAssetManager = null;
    private Resources mResources = null;

    public AppContext(Context base, AppInfo appInfo) {
        super(base);
        mBase = base;
        mAppInfo = appInfo;
    }

    @Override
    public AssetManager getAssets() {
        Log.d("AppContext", "getAssets");
        if (mAssetManager == null) {
            loadResources(AppUtil.getPath(mAppInfo));
        }
        return mAssetManager;
    }

    public void loadResources(String appPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, appPath);
            mAssetManager = assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Resources superRes = mBase.getResources();
        mResources = new Resources(mAssetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
    }

    @Override
    public Resources getResources() {
        Log.d("AppContext", "getResource");
        if (mResources == null) {
            loadResources(AppUtil.getPath(mAppInfo));
        }
        return mResources;
    }
}
