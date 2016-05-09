package com.cuit.pcs.ui.view.main;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.View;
import android.widget.ImageView;

import com.cuit.pcs.R;

/**
 * Created by jiang on 2015/12/17.
 */
public class AddActionProvider extends ActionProvider {

    private Context context;
    private ImageView imageView;

    /**
     * Creates a new instance.
     *
     * @param context Context for accessing resources.
     */
    public AddActionProvider(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View onCreateActionView() {
        imageView = new ImageView(context);
        imageView.setImageResource(R.mipmap.ic_add_white);
        int padding = context.getResources().getDimensionPixelOffset(R.dimen.add_menu_padding);
        imageView.setPadding(padding, padding, padding, padding);
        if (onAddClickListener != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddClickListener.onAddClick(v);
                }
            });
        }
        return imageView;
    }

    public void setOnAddClickListener(OnAddClickListener onAddClickListener) {
        this.onAddClickListener = onAddClickListener;
    }

    private OnAddClickListener onAddClickListener;

    public interface OnAddClickListener {
        public void onAddClick(View v);
    }
}
