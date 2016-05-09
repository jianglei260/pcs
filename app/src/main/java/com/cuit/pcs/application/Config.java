package com.cuit.pcs.application;

import android.os.Environment;

import com.cuit.pcs.R;
import com.cuit.pcs.sys.utils.CofigUtil;

import java.io.File;

/**
 * Created by ASUS-1 on 2015/9/9.
 */
public class Config {
    public static int ANIMATION_SPEED_MILLIS = 500;
    public static final String DexOptimizedDirectory = MyApplication.getInstance().getFilesDir().getAbsolutePath();
    public static final String EXTERNAL_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "pcs";
    public static int PICTURE_SCAN_FINISH = 456;
    public static int BG_IMG = R.mipmap.bg;
    public static int THEME = 2;
    public static final int THEME_WIN = 1;
    public static final int THEME_KG = 2;
    public static int COLOR_PRIMARY = R.color.colorPrimary;


    public static void initConfig() {
        BG_IMG = CofigUtil.getWallpaperConfig();
        THEME = CofigUtil.getThemeConfig();
    }
}
