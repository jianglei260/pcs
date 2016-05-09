package com.cuit.pcs.sys.window;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.cuit.pcs.R;
import com.cuit.pcs.ui.widget.TitleBar;

/**
 * Created by ASUS-1 on 2015/11/20.
 */

public class WindowPage {
    protected boolean resultAble;
    protected Context context;
    protected LinearLayout rootLayout;
    protected TitleBar titleBar;
    private LayoutParams totleBarLayoutParams, layoutParams;
    private Object object;
    private int requestCode;

    public WindowPage(Context context) {
        this.context = context;
        rootLayout = new LinearLayout(context);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        totleBarLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelOffset(R.dimen.app_bar_height));
        titleBar = new TitleBar(context);
        rootLayout.addView(titleBar, totleBarLayoutParams);
    }

    /**
     * 隐藏titlebar
     */
    public void setNoTitleBar() {
        rootLayout.removeView(titleBar);
    }

    /**
     * 添加布局视图
     * @param v
     */
    public void addBodyView(View v) {
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        rootLayout.addView(v, layoutParams);
    }

    /**
     * 获取当前windowpage的布局
     * @return LinearLayout
     */
    public LinearLayout getView() {
        return rootLayout;
    }

    /**
     * windowpage打开前调用
     */
    public void onStart() {
    }

    /**
     * windowpage返回时调用
     */
    public void onBack() {
    }

    /**
     * 从另一个windowpage返回时调用
     * @param object windowpage传回的值
     * @param requestCode 打开时的flag
     */
    public void onResult(Object object, int requestCode) {
    }

    public void finish() {
    }

    /**
     * 本windowpage是否可以返回
     * @return Boolean
     */
    public boolean isResultAble() {
        return resultAble;
    }

    /**
     * 设置此windowpage是否可以返回
     * @param resultAble
     */
    public void setResultAble(boolean resultAble) {
        this.resultAble = resultAble;
    }

    /**
     * 设置返回上一windowpage的结果
     * @param o
     */
    public void putBackObject(Object o) {
        this.object = o;
    }

    /**
     * 获取返回结果
     * @return Object
     */
    Object getBackObject() {
        return this.object;
    }
    /**
     * 获取返回flag
     * @return int
     */
    public int getRequestCode() {
        return requestCode;
    }

    /**
     * 设置返回flag
     * @param requestCode
     */
    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    /**
     * 当返回时调用
     * @return 是否对返回事件进行处理
     */
    public boolean onBackPressed() {
        return false;
    }
}
