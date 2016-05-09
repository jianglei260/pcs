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
     * ����titlebar
     */
    public void setNoTitleBar() {
        rootLayout.removeView(titleBar);
    }

    /**
     * ��Ӳ�����ͼ
     * @param v
     */
    public void addBodyView(View v) {
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        rootLayout.addView(v, layoutParams);
    }

    /**
     * ��ȡ��ǰwindowpage�Ĳ���
     * @return LinearLayout
     */
    public LinearLayout getView() {
        return rootLayout;
    }

    /**
     * windowpage��ǰ����
     */
    public void onStart() {
    }

    /**
     * windowpage����ʱ����
     */
    public void onBack() {
    }

    /**
     * ����һ��windowpage����ʱ����
     * @param object windowpage���ص�ֵ
     * @param requestCode ��ʱ��flag
     */
    public void onResult(Object object, int requestCode) {
    }

    public void finish() {
    }

    /**
     * ��windowpage�Ƿ���Է���
     * @return Boolean
     */
    public boolean isResultAble() {
        return resultAble;
    }

    /**
     * ���ô�windowpage�Ƿ���Է���
     * @param resultAble
     */
    public void setResultAble(boolean resultAble) {
        this.resultAble = resultAble;
    }

    /**
     * ���÷�����һwindowpage�Ľ��
     * @param o
     */
    public void putBackObject(Object o) {
        this.object = o;
    }

    /**
     * ��ȡ���ؽ��
     * @return Object
     */
    Object getBackObject() {
        return this.object;
    }
    /**
     * ��ȡ����flag
     * @return int
     */
    public int getRequestCode() {
        return requestCode;
    }

    /**
     * ���÷���flag
     * @param requestCode
     */
    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    /**
     * ������ʱ����
     * @return �Ƿ�Է����¼����д���
     */
    public boolean onBackPressed() {
        return false;
    }
}
