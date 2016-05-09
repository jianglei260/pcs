package com.cuit.webapp;

import android.webkit.WebView;

import com.cuit.pcs.application.Config;

import dalvik.system.DexClassLoader;

/**
 * Created by ASUS-1 on 2015/11/27.
 */
public class BaseJsInterface {
    WebView webView;

    public BaseJsInterface(WebView webView) {
        this.webView = webView;
    }

    public void loadInterface(String path, String classPath) {
        DexClassLoader dexClassLoader = new DexClassLoader(path, Config.DexOptimizedDirectory, null, this.getClass().getClassLoader());
        try {
            Class<? extends BaseJsInterface> jsInterface = dexClassLoader.loadClass(classPath).asSubclass(BaseJsInterface.class);
            BaseJsInterface baseJsInterface = jsInterface.newInstance();
            webView.addJavascriptInterface(baseJsInterface, jsInterface.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
