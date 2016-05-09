package com.cuit.pcs.ui.view.picselector;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.cuit.pcs.R;


/**
 * Created by ASUS-1 on 2015/8/9.
 */
public class PicSelectViewHolder extends RecyclerView.ViewHolder {
    public ImageView pic, indicator;
    boolean isSelected = false;

    public PicSelectViewHolder(View itemView) {
        super(itemView);
        pic = (ImageView) itemView.findViewById(R.id.id_pic_item_image);
        indicator = (ImageView) itemView.findViewById(R.id.id_pic_item_indicator);
    }
}
