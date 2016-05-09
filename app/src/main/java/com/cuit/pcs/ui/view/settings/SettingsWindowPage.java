package com.cuit.pcs.ui.view.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cuit.pcs.R;
import com.cuit.pcs.apkloader.EventBus;
import com.cuit.pcs.application.Config;
import com.cuit.pcs.sys.utils.CofigUtil;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.sys.window.WindowPage;
import com.cuit.pcs.ui.view.main.MainWindowPage;
import com.cuit.pcs.ui.view.picselector.PicSelectWindowPage;

/**
 * Created by ASUS-1 on 2015/12/12.
 */
public class SettingsWindowPage extends WindowPage {

    private Context context;
    private LinearLayout rootView;
    private RelativeLayout setThemeLayout, setWallpaperLayout;
    private int[] wallpapers = new int[]{R.mipmap.bg, R.mipmap.skin_bg, R.mipmap.bg3};
    private int[] themes = new int[]{Config.THEME_WIN, Config.THEME_KG};

    public SettingsWindowPage(Context context) {
        super(context);
        this.context = context;
        initUI();
    }

    private void initUI() {
        rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.settings_view, null);
        setThemeLayout = (RelativeLayout) rootView.findViewById(R.id.set_theme);
        setWallpaperLayout = (RelativeLayout) rootView.findViewById(R.id.set_wallpaper);

        addBodyView(rootView);
        titleBar.setTitleText("设置");
        titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FastWindow.getInstance().goBack();
            }
        });

        setThemeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setSingleChoiceItems(R.array.themes, getResCheckedItem(themes, CofigUtil.getThemeConfig()), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setTheme(themes[which]);
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        setWallpaperLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setSingleChoiceItems(R.array.wallpapers, getResCheckedItem(wallpapers, CofigUtil.getWallpaperConfig()), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setWallpaper(wallpapers[which]);
                        dialog.dismiss();
                    }
                });
                builder.show();


            }
        });
    }

    private int getResCheckedItem(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value)
                return i;
        }
        return 0;
    }

    private String getResArrayValue(int array, int index) {
        String[] values = context.getResources().getStringArray(array);
        return values[index];
    }

    private void setTheme(int option) {
        Config.THEME = option;
        CofigUtil.setThemeConfig(option);
        EventBus.getInstance().publishEvent(option, MainWindowPage.class, "onThemeChanged");
    }

    private void setWallpaper(int res) {
        Config.BG_IMG = res;
        CofigUtil.setWallpaperConfig(res);
        EventBus.getInstance().publishEvent(res, MainWindowPage.class, "onWallpaperChanged");
    }


}
