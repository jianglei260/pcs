package com.cuit.pcs.apkloader;

import android.view.View;

import com.cuit.pcs.sys.window.WindowPage;

import java.util.Stack;

import dalvik.system.DexClassLoader;

/**
 * Created by ASUS-1 on 2015/9/26.
 */
public class Task {
    private DexClassLoader dexClassLoader;
    private Stack<WindowPage> windowPageStack;
    private App app;

    public Task() {
        windowPageStack = new Stack<>();
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public void setDexClassLoader(DexClassLoader dexClassLoader) {
        this.dexClassLoader = dexClassLoader;
    }

    public Stack<WindowPage> getWindowPageStack() {
        return windowPageStack;
    }

    public void setWindowPageStack(Stack<WindowPage> windowPageStack) {
        this.windowPageStack = windowPageStack;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
