package com.cuit.pcs.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by ASUS-1 on 2015/9/11.
 */
public class UnTouchableLinearLayout extends LinearLayout {
    public UnTouchableLinearLayout(Context context) {
        super(context);
    }

    public UnTouchableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnTouchableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public UnTouchableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
