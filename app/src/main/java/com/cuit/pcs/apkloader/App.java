package com.cuit.pcs.apkloader;

import com.cuit.pcs.application.MyApplication;
import com.cuit.pcs.sys.window.WindowPage;
import com.cuit.pcs.ui.activity.MainActivity;

/**
 * Created by ASUS-1 on 2015/11/20.
 */
public abstract class App {

    /**
     * ????????windowpage
     * @param windowPage ?????windowpage
     */
    public abstract void startWindowPage(WindowPage windowPage);

    /**
     * ??????з????windowpage??????windowpage????????????????windowpage??onResult????
     * @param windowPage ?????windowpage
     * @param result ?????windowpage??flag??????????????????????????windowpage
     */
    public abstract void startWindowPageForResult(WindowPage windowPage, int result);

    /**
     * ?????????????????????????????????????г????????????
     * @param context ??????????????
     */
    public abstract void onStart(AppContext context);

    /**
     * ????????????????????????????????????н????Щ??????
     */
    public abstract void onFinish();

    /**
     * ?????????
     */
    public abstract void finish();

    /**
     * ???????????
     * @param broadcastCompoment ?????broadcast???
     */
    public abstract void registerBroadCast(BroadcastCompoment broadcastCompoment);

    /**
     * ??????????????
     * @deprecated
     * @param serviceCompoment ???????service???
     */
    public abstract void startService(ServiceCompoment serviceCompoment);

    /**
     * ???????ж???????
     */
    public abstract void onRemove();

    /**
     * ????????????
     * @param appInfo ???????appinfo
     */
    public abstract void startApp(AppInfo appInfo);

    /**
     * ????
     */
    public abstract void goBack();

    /**
     * ??????????MainActivity
     * @return MainActivity
     */
    public abstract MainActivity getMainActivity();

    /**
     * ??????????Application
     * @return
     */
    public abstract MyApplication getMyApplication();

    /**
     * ??????????????
     * @return AppInfo
     */
    public abstract AppInfo getAppInfo();
}
