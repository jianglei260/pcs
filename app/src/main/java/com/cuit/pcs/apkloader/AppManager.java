package com.cuit.pcs.apkloader;

import com.cuit.pcs.application.Config;
import com.cuit.pcs.application.MyApplication;
import com.cuit.pcs.file.FileDownloader;
import com.cuit.pcs.sys.utils.Utils;
import com.cuit.pcs.ui.view.add.AddWindowPage;
import com.cuit.pcs.ui.view.main.MainWindowPage;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS-1 on 2015/9/9.
 */
public class AppManager {
    private static AppManager appManager;
    private DbUtils dbUtils;
    private List<AppInfo> installedApps, installingApps;
    private List<AppInfo> storeList;
    private AppInfo addInfo;

    public static AppManager getInstance() {
        if (appManager == null)
            appManager = new AppManager();
        return appManager;
    }

    private AppManager() {
        dbUtils = DbUtils.create(MyApplication.getInstance(), "App");
        try {
            dbUtils.createTableIfNotExist(AppInfo.class);
            installedApps = dbUtils.findAll(Selector.from(AppInfo.class).where("state", "=", "已安装"));
            installingApps = dbUtils.findAll(Selector.from(AppInfo.class).where("state", "=", "下载中"));
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (installedApps == null) {
            installedApps = new ArrayList<>();
        }
        if (installingApps == null) {
            installingApps = new ArrayList<>();
        }
    }

    public void onAppDownloaded(String url) {
        for (AppInfo appInfo : storeList) {
            if (url.equals(appInfo.getPath())) {
                appInfo.setState("已安装");
                String src = Config.EXTERNAL_DIR + File.separator + appInfo.getClassPath() + ".apk";
                String dst = MyApplication.getInstance().getFilesDir().getAbsolutePath() + File.separator + appInfo.getClassPath() + ".apk";
                installedApps.add(0, appInfo);
                EventBus.getInstance().publishEvent(appInfo, MainWindowPage.class, "onEvent");
                EventBus.getInstance().publishEvent(appInfo, AddWindowPage.class, "onEvent");
                Utils.moveFile(src, dst);
                try {
                    dbUtils.update(appInfo, WhereBuilder.b("objectId", "=", appInfo.getObjectId()));
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addApp(final AppInfo appInfo) {
        appInfo.setColumnSpan(1);
        appInfo.setRowSpan(1);
        if (appInfo.getPath().endsWith(".apk")) {
            appInfo.setState("下载中");
            FileDownloader.getInstance().addNewDownload(appInfo.getPath(), appInfo.getClassPath());
            try {
                dbUtils.save(appInfo);
            } catch (DbException e) {
                e.printStackTrace();
            }
        } else {
            appInfo.setState("已安装");
            installedApps.add(0, appInfo);
            EventBus.getInstance().publishEvent(appInfo, MainWindowPage.class, "onEvent");
            EventBus.getInstance().publishEvent(appInfo, AddWindowPage.class, "onEvent");
            try {
                dbUtils.save(appInfo);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }

    }

    public List<AppInfo> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<AppInfo> storeList) {
        this.storeList = storeList;
    }

    public List<AppInfo> getInstalledApps() {
        return installedApps;
    }

    public List<AppInfo> getInstallingApps() {
        return installingApps;
    }

    public void removeApp(AppInfo appInfo) {
        try {
            dbUtils.delete(AppInfo.class, WhereBuilder.b("objectId", "=", appInfo.getObjectId()));
            installedApps.remove(appInfo);
            if (appInfo.getPath().endsWith(".apk")) {
                File file = new File(AppUtil.getPath(appInfo));
                File dexFile = new File(AppUtil.getDexPath(appInfo));
                AppManageService.getInstance().removeApp(appInfo);
                if (file.exists())
                    file.delete();
                if (dexFile.exists())
                    dexFile.delete();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void saveOrUpdateData() {
        try {
            dbUtils.saveAll(installedApps);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
