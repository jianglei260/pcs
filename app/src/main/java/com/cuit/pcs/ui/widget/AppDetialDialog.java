package com.cuit.pcs.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cuit.pcs.R;


/**
 * Created by ASUS-1 on 2015/12/13.
 */
public class AppDetialDialog extends Dialog {
    Context context;
    View view;
    View backView;

    String title;
    TextView titleTextView, removeText;

    RadioButton smallIcon, bigIcon;
    RadioGroup radioGroup;


    public AppDetialDialog(Context context, String title) {
        super(context);
        this.context = context;// init Context
        this.title = title;
        this.setCancelable(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_app_detial);

        view = (RelativeLayout) findViewById(R.id.contentDialog);
        backView = (RelativeLayout) findViewById(R.id.dialog_rootView);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        removeText = (TextView) findViewById(R.id.remove);
        this.titleTextView = (TextView) findViewById(R.id.title);
        setTitle(title);
    }

    @Override
    public void show() {
        // TODO 自动生成的方法存根
        super.show();
        // set dialog enter animations
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_main_show_amination));
        backView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.dialog_root_show_amin));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        if (title == null)
            titleTextView.setVisibility(View.GONE);
        else {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(title);
        }
    }

    public void setOnRemoveListener(final OnRemoveListener listener) {
        removeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRemove(v);
            }
        });
    }

    public void setChoosedItem(int id) {
        radioGroup.check(id);
    }

    public void setOnItemChoosed(final OnItemChoosed onItemChoosed) {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onItemChoosed.onItemChoosed(checkedId);
            }
        });
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public void setTitleTextView(TextView titleTextView) {
        this.titleTextView = titleTextView;
    }


    @Override
    public void dismiss() {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.dialog_main_hide_amination);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        AppDetialDialog.super.dismiss();
                    }
                });

            }
        });
        Animation backAnim = AnimationUtils.loadAnimation(context, R.anim.dialog_root_hide_amin);

        view.startAnimation(anim);
        backView.startAnimation(backAnim);
    }

    private OnItemChoosed onItemChoosed;

    public interface OnItemChoosed {
        public void onItemChoosed(int id);
    }

    public interface OnRemoveListener {
        public void onRemove(View v);
    }
}
