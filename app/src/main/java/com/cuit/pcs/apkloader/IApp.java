package com.cuit.pcs.apkloader;

import android.content.Context;
import android.view.View;

/**
 * Created by ASUS-1 on 2015/9/9.
 */
public interface IApp {
    /**
     * init中初始化执行前需要的操作
     *
     * @return void
     * @author ASUS-1
     */
    public void init(Context context);

    /**
     * run方法执行后开始执行必要的操作
     *
     * @return boolean 是否成功执行
     * @author ASUS-1
     */
    public boolean run();

    public View getPreview();
}
