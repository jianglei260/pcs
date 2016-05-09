package com.cuit.pcs.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cuit.pcs.R;


/**
 * Created by ASUS-1 on 2015/8/11.
 */
public class TitleBar extends RelativeLayout {
    private RelativeLayout titlebarLayout;
    private ImageView leftImage, rightImage;
    private TextView titleText;
    private Context context;

    public TitleBar(Context context) {
        super(context);
        this.context = context;
        initUI();
    }

    private void initUI() {
        titlebarLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.titlebar_view, null);
        leftImage = (ImageView) titlebarLayout.findViewById(R.id.id_titlebar_leftImage);
        rightImage = (ImageView) titlebarLayout.findViewById(R.id.id_titlebar_rightImage);
        titleText = (TextView) titlebarLayout.findViewById(R.id.id_titlebar_titleText);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(titlebarLayout, layoutParams);
    }

    public void setLeftImage(int res) {
        leftImage.setImageResource(res);
    }

    public void setLeftImageOnclick(OnClickListener onClickListener) {
        leftImage.setOnClickListener(onClickListener);
    }

    public void setTitleText(String text) {
        titleText.setText(text);
    }

    public void setRightImage(int res) {
        rightImage.setImageResource(res);
    }

    public void setRightImageOnClick(OnClickListener onClickListener) {
        rightImage.setOnClickListener(onClickListener);
    }

    public void setTitleInCenter() {
        titlebarLayout.removeView(titleText);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, context.getResources().getDimensionPixelOffset(R.dimen.app_bar_height));
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        titlebarLayout.addView(titleText, layoutParams);
    }

    public ImageView getLeftImage() {
        return leftImage;
    }

    public void setLeftImage(ImageView leftImage) {
        this.leftImage = leftImage;
    }

    public ImageView getRightImage() {
        return rightImage;
    }

    public void setRightImage(ImageView rightImage) {
        this.rightImage = rightImage;
    }

    public TextView getTitleText() {
        return titleText;
    }

    public void setTitleText(TextView titleText) {
        this.titleText = titleText;
    }
}
