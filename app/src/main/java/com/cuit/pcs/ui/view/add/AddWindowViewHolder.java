package com.cuit.pcs.ui.view.add;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuit.pcs.R;

/**
 * Created by ASUS-1 on 2015/9/10.
 */
public class AddWindowViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView name, describe, state, size;

    public AddWindowViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.id_app_image);
        name = (TextView) itemView.findViewById(R.id.id_app_name);
        describe = (TextView) itemView.findViewById(R.id.id_app_describe);
        state = (TextView) itemView.findViewById(R.id.id_app_install);
        size = (TextView) itemView.findViewById(R.id.id_app_size);
    }
}
