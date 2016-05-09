package com.cuit.remind;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ASUS-1 on 2015/9/13.
 */
public class RemindViewHolder extends RecyclerView.ViewHolder {
    public TextView nameText, timeText, stateText;
    public ImageView imageView;

    public RemindViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.id_item_image);
        nameText = (TextView) itemView.findViewById(R.id.id_item_name);
        timeText = (TextView) itemView.findViewById(R.id.id_item_time);
        stateText = (TextView) itemView.findViewById(R.id.id_item_state);
    }
}
