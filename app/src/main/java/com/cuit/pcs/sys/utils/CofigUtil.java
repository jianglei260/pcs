package com.cuit.pcs.sys.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cuit.pcs.R;
import com.cuit.pcs.application.Config;
import com.cuit.pcs.application.MyApplication;

/**
 * Created by ASUS-1 on 2015/12/12.
 */
public class CofigUtil {
    public static int getThemeConfig() {
        SharedPreferences preferences = MyApplication.getInstance().getSharedPreferences("config", Context.MODE_PRIVATE);
        return preferences.getInt("theme", 1);
    }

    public static void setThemeConfig(int value) {
        SharedPreferences preferences = MyApplication.getInstance().getSharedPreferences("config", Context.MODE_PRIVATE);
        preferences.edit().putInt("theme", value).commit();
    }

    public static int getWallpaperConfig() {
        SharedPreferences preferences = MyApplication.getInstance().getSharedPreferences("config", Context.MODE_PRIVATE);
        return preferences.getInt("wallpaper", R.mipmap.bg);
    }

    public static void setWallpaperConfig(int value) {
        SharedPreferences preferences = MyApplication.getInstance().getSharedPreferences("config", Context.MODE_PRIVATE);
        preferences.edit().putInt("wallpaper", value).commit();
    }
}
