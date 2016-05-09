package com.cuit.pcs.apkloader;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.LruCache;
import android.view.View;

import com.cuit.pcs.application.Config;
import com.cuit.pcs.application.MyApplication;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.sys.window.WindowPage;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import dalvik.system.DexClassLoader;

/**
 * Created by ASUS-1 on 2015/9/26.
 */
public class AppManageService {
    private static AppManageService appManageService;
    private LruCache<AppInfo, Task> taskLruCache = new LruCache<>(8);
    private Context context;
    private App currentApp;
    public AppContext currentContext;

    public static AppManageService getInstance() {
        if (appManageService == null)
            appManageService = new AppManageService();
        return appManageService;
    }

    private AppManageService() {
        context = MyApplication.getInstance().getMainActivity();
    }

    public void start(AppInfo appInfo) {
        App plugApp = getPlugApp(appInfo);
        if (plugApp == null)
            return;
        AppContext appContext = new AppContext(MyApplication.getInstance().getBaseContext(), appInfo);
        currentApp = plugApp;
        currentContext = appContext;
        plugApp.onStart(appContext);
    }

    public void finish(App app) {
        FastWindow.getInstance().backToFirst();
        currentApp = null;
    }

    public void goBack(App app) {
        Stack<WindowPage> stack = getTask(app).getWindowPageStack();
        if (stack.size() > 0) {
            WindowPage windowPage = stack.peek();
            if (windowPage.onBackPressed()) {
                return;
            }
            stack.pop();
            FastWindow.getInstance().goBack();
        }
        if (getTask(app).getWindowPageStack().isEmpty()) {
            app.onFinish();
            currentApp = null;
        }
    }

    public boolean onBackPressed() {
        if (currentApp != null) {
            Stack<WindowPage> stack = getTask(currentApp).getWindowPageStack();
            if (stack.size() > 0) {
                WindowPage windowPage = stack.peek();
                if (!windowPage.onBackPressed()) {
                    goBack(currentApp);
                    return true;
                }
            }
        }
        return false;
    }

    public void removeApp(AppInfo appInfo) {
        getPlugApp(appInfo).onRemove();
        clear(appInfo);
    }

    public void startWindowPage(App app, WindowPage windowPage) {
        getTask(app).getWindowPageStack().push(windowPage);
        FastWindow.getInstance().startWindow(windowPage);
    }

    public void startWindowPageForResult(App app, WindowPage windowPage, int reslut) {
        getTask(app).getWindowPageStack().push(windowPage);
        FastWindow.getInstance().startWindowForResult(windowPage, reslut);
    }

    private App getPlugApp(AppInfo appInfo) {
        App plugApp = null;
        Task task = taskLruCache.get(appInfo);
        if (task == null) {
            String dexPath = AppUtil.getPath(appInfo);
            DexClassLoader dexClassLoader = new DexClassLoader(dexPath, Config.DexOptimizedDirectory, null, this.getClass().getClassLoader());
            plugApp = createApp(dexClassLoader, appInfo);
            task = new Task();
            task.setApp(plugApp);
            task.setDexClassLoader(dexClassLoader);
            taskLruCache.put(appInfo, task);
        } else {
            plugApp = task.getApp();
        }
        return plugApp;
    }

    public Task getTask(App app) {
        return taskLruCache.get(app.getAppInfo());
    }

    private App createApp(DexClassLoader dexClassLoader, AppInfo appInfo) {
        App plugApp = null;
        AppImpl app = new AppImpl(appInfo);
        try {
            Class clazz = dexClassLoader.loadClass(appInfo.getClassPath());
            Constructor constructor = clazz.getDeclaredConstructor(App.class);
            plugApp = (App) constructor.newInstance(app);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plugApp;
    }

    public void clear(AppInfo appInfo) {
        taskLruCache.remove(appInfo);
    }

    public App getRunningApp() {
        return currentApp;
    }

    public AppInfo findAppInfo(String classPath) {
        Set<Map.Entry<AppInfo, Task>> enterys = taskLruCache.snapshot().entrySet();
        for (Map.Entry<AppInfo, Task> entry : enterys) {
            if (classPath.equals(entry.getKey().getClassPath()))
                return entry.getKey();
        }
        return null;
    }

    public boolean onBroadcastReceive(String dexPath, String className, Context context, Intent intent) {
        DexClassLoader dexClassLoader = new DexClassLoader(dexPath, Config.DexOptimizedDirectory, null, this.getClass().getClassLoader());
        try {
            Class clazz = dexClassLoader.loadClass(className);
            Object object = clazz.newInstance();
            Method onReceive = clazz.getDeclaredMethod("onReceive", Context.class, Intent.class);
            onReceive.invoke(object, context, intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
