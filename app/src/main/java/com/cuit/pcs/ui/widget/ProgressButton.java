package com.cuit.pcs.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.cuit.pcs.R;

/**
 * Created by ASUS-1 on 2015/8/3.
 */
public class ProgressButton extends Button implements View.OnClickListener {

    private int progress = 0;
    private Drawable prgressDrawable;
    private StateListDrawable normalStateListDrawable;
    private float indicatorWidth;
    private boolean isPause = false;
    private OnStateChangeListener onStateChangeListener;
    private String startText = "", completeText = "";

    public ProgressButton(Context context) {
        super(context);
        init();
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.ProgressButton);
        if (attr != null) {
            startText = attr.getString(R.styleable.ProgressButton_startText);
            completeText = attr.getString(R.styleable.ProgressButton_completeText);
        }

        init();
    }

    private void init() {
        normalStateListDrawable = new StateListDrawable();
        normalStateListDrawable.addState(new int[]{android.R.attr.state_pressed}, getPressDrawable());
        normalStateListDrawable.addState(new int[]{}, getNormalDrawable());
        setBackgroundCompat(normalStateListDrawable);
        setText(startText);
        setOnClickListener(this);
    }

    private Drawable getPressDrawable() {
        return getResources().getDrawable(R.drawable.progrees_button_pressed);
    }

    private Drawable getNormalDrawable() {
        return getResources().getDrawable(R.drawable.progress_bar_normal);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        indicatorWidth = ((float) progress / (float) 100) * getMeasuredWidth();
        prgressDrawable = getDrawable(R.drawable.progress_bar_rect);
        prgressDrawable.setBounds(0, 0, (int) indicatorWidth, getMeasuredHeight());
        prgressDrawable.draw(canvas);
        if (progress > 0 && progress < 100) {
            if (isPause == true)
                setText("继续");
            else
                setText("暂停");
        }

        super.onDraw(canvas);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        if (progress < 0 || progress > 100)
            throw new IllegalArgumentException("the value should in 0-100");
        this.progress = progress;
        if (progress == 0)
            onNormalState();
        else if (progress > 0 && progress < 100)
            onProgress();
        else
            onComplete();
        invalidate();
    }

    private void onNormalState() {
        setBackgroundCompat(getNormalDrawable());
    }

    private void onProgress() {
        setBackgroundCompat(getNormalDrawable());
    }

    private void onComplete() {
        setBackgroundCompat(getNormalDrawable());
        setText(completeText);
    }

    private Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public void setBackgroundCompat(Drawable drawable) {
        int pL = getPaddingLeft();
        int pT = getPaddingTop();
        int pR = getPaddingRight();
        int pB = getPaddingBottom();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
        setPadding(pL, pT, pR, pB);
    }

    public void setStateChangeListener(OnStateChangeListener stateChangeListener) {
        this.onStateChangeListener = stateChangeListener;
    }

    public void setStateStart() {
        if (onStateChangeListener != null)
            onStateChangeListener.onStart(this);
    }

    public void setStatePause() {
        if (onStateChangeListener != null)
            onStateChangeListener.onPause(this, progress);
    }

    public void setStateComplete() {
        if (onStateChangeListener != null)
            onStateChangeListener.onComplete(this);
    }

    public void setStateContinue() {
        if (onStateChangeListener != null)
            onStateChangeListener.onContinue(this, progress);
    }

    @Override
    public void onClick(View v) {
        if (onStateChangeListener == null)
            return;
        if (progress == 0) {
            onStateChangeListener.onStart(this);
        } else if (progress > 0 && progress < 100) {
            if (isPause) {
                setText("继续");
                onStateChangeListener.onContinue(this, progress);
                isPause = false;
            } else {
                setText("暂停");
                onStateChangeListener.onPause(this, progress);
                isPause = true;
            }
        } else {
            onStateChangeListener.onComplete(this);
        }
    }

    public interface OnStateChangeListener {
        public void onStart(View v);

        public void onPause(View v, int progress);

        public void onContinue(View v, int progress);

        public void onComplete(View v);
    }
}
