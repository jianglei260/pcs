package com.cuit.pcs.ui.view.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cuit.pcs.R;
import com.cuit.pcs.ui.widget.UnTouchableLinearLayout;

/**
 * Created by ASUS-1 on 2015/9/10.
 */
public class MainWindowViewHolder extends RecyclerView.ViewHolder {

    public UnTouchableLinearLayout imageView;
    public TextView textView;

    public MainWindowViewHolder(View itemView) {
        super(itemView);
        imageView = (UnTouchableLinearLayout) itemView.findViewById(R.id.id_main_item_image);
        textView = (TextView) itemView.findViewById(R.id.id_main_item_name);
    }
}
