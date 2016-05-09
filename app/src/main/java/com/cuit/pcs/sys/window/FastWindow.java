package com.cuit.pcs.sys.window;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;

import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppManageService;
import com.cuit.pcs.application.Config;
import com.cuit.pcs.application.MyApplication;

import java.util.ArrayList;
import java.util.List;


public class FastWindow {
    private List<WindowPage> windows;
    private static FastWindow fastWindow;
    private ContainerView containerView;
    private Context context;
    private LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    private float width;

    /**
     * ��ȡһ��FastWindowʵ��
     *
     * @return FastWindow
     */
    public static FastWindow getInstance() {
        if (fastWindow == null)
            fastWindow = new FastWindow();
        return fastWindow;
    }

    public void init() {
        windows = new ArrayList<WindowPage>();
        context = MyApplication.getInstance().getApplicationContext();
        containerView = new ContainerView(context);
        width = context.getResources().getDisplayMetrics().widthPixels;
    }

    public static void close() {
        fastWindow = null;
    }

    /**
     * ���´���
     *
     * @param windowPage
     */
    public void startWindow(WindowPage windowPage) {
        Log.d("window page", windowPage.getClass().getName());
        View v = windowPage.getView();
        windows.add(windowPage);
        windowPage.onStart();
        if (windows.size() > 1) {
            openAnimator(windows.get(getWindowSize() - 2).getView(), v);
        }
        containerView.addView(v, layoutParams);
    }

    private View getLastView() {
        View lastView = null;
        if (windows.size() >= 1) {
            lastView = windows.get(windows.size() - 1).getView();
        }
        return lastView;
    }

    public void startWindowForResult(WindowPage windowPage, int requestCode) {
        windowPage.setResultAble(true);
        windowPage.setRequestCode(requestCode);
        View v = windowPage.getView();
        windowPage.onStart();
        windows.add(windowPage);
        if (windows.size() > 1) {
            openAnimator(windows.get(getWindowSize() - 2).getView(), v);
        }
        containerView.addView(v, layoutParams);
    }

    private void closeAnimator(final View currentView, final View nextView) {
        ObjectAnimator closeAnimator1 = ObjectAnimator.ofFloat(currentView, "translationX", 0, width);
        ObjectAnimator closeAnimator2 = ObjectAnimator.ofFloat(nextView, "translationX", -width, 0);
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setDuration(Config.ANIMATION_SPEED_MILLIS);
        animationSet.play(closeAnimator1).with(closeAnimator2);
        animationSet.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub
                containerView.removeView(currentView);
            }
        });
        animationSet.start();
    }

    private void openAnimator(final View currentview, View nextView) {
        ObjectAnimator openAnimator1 = ObjectAnimator.ofFloat(currentview, "translationX", 0, -width);
        ObjectAnimator openAnimator2 = ObjectAnimator.ofFloat(nextView, "translationX", width, 0);
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setDuration(Config.ANIMATION_SPEED_MILLIS);
        animationSet.play(openAnimator1).with(openAnimator2);
        animationSet.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                containerView.removeView(currentview);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });
        animationSet.start();
    }

    public WindowPage getCurrentWindow()

    {
        return windows.get(getWindowSize() - 1);
    }

    /**
     * ������һ������
     *
     * @return boolean �Ƿ��
     */
    public boolean goBack() {
        if (getCurrentWindow().onBackPressed()) {
            return true;
        }
        int size = windows.size();
        if (size > 1) {
            WindowPage page = windows.remove(size - 1);
            WindowPage prePage = windows.get(size - 2);
            page.onBack();
            closeAnimator(page.getView(), prePage.getView());
            containerView.addView(prePage.getView());
            if (page.isResultAble()) {
                prePage.onResult(page.getBackObject(), page.getRequestCode());
            }
            containerView.postDelayed(new Runnable() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {
                    containerView.dispatchTouchEvent(MotionEvent.obtain(100, 100, MotionEvent.ACTION_MOVE, 200, 200, 0));
    //                containerView.onTouchEvent(MotionEvent.obtain(100, 100, MotionEvent.ACTION_MOVE, 200, 200, 0));
                    containerView.requestFocusFromTouch();
                }
            }, 500);
            return true;
        }
        return false;
    }

    /**
     * ��ȡ���д���
     *
     * @return
     */
    public List<WindowPage> getAllWindow() {
        return windows;
    }

    /**
     * ��ȡ����View
     *
     * @return
     */
    public ContainerView getContainerView() {
        return containerView;
    }

    /**
     * ��ȡ������Ŀ
     *
     * @return
     */
    public int getWindowSize() {
        return windows.size();
    }

    /**
     * ��ȡ����λ��
     *
     * @param v
     * @return
     */
    public int getWindowIndex(View v) {
        return windows.indexOf(v);
    }

    /**
     * ������ͼ
     *
     * @author ASUS-1
     */
    public class ContainerView extends FrameLayout {

        public ContainerView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            invalidate();
        }
    }

    public void removeAllBackground() {
        for (int i = 0; i < windows.size() - 1; i++)
            windows.remove(i);
    }

    public void backToFirst() {
        while (goBack()) ;
    }

    public WindowPage getWindowPage(int index) {
        if (index < 0 || index >= getWindowSize())
            new IllegalAccessException("check the window value");
        return windows.get(index);
    }
}
