package com.cuit.pcs.apkloader;

import com.cuit.pcs.application.Config;
import com.cuit.pcs.application.MyApplication;

import java.io.File;

/**
 * Created by ASUS-1 on 2015/11/20.
 */
public class AppUtil {
    public static String getPath(AppInfo appInfo) {
        return MyApplication.getInstance().getFilesDir().getAbsolutePath() + File.separator + appInfo.getClassPath() + ".apk";
    }

    public static String getDexPath(AppInfo appInfo) {
        return Config.DexOptimizedDirectory + File.separator + appInfo.getClassPath() + ".dex";
    }

}
