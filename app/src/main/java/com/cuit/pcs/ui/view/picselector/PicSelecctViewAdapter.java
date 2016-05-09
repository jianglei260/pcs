package com.cuit.pcs.ui.view.picselector;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cuit.pcs.R;
import com.cuit.pcs.application.MyApplication;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import java.util.List;

/**
 * Created by ASUS-1 on 2015/8/9.
 */
public class PicSelecctViewAdapter extends RecyclerView.Adapter {
    private List<String> list;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public PicSelecctViewAdapter(List<String> list) {
        this.list = list;
        inflater = LayoutInflater.from(MyApplication.getInstance().getApplicationContext());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View views = inflater.inflate(R.layout.pic_item, parent, false);
        PicSelectViewHolder picSelectViewHolder = new PicSelectViewHolder(views);
        return picSelectViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final int width = MyApplication.getInstance().getApplicationContext().getResources().getDimensionPixelOffset(R.dimen.pic_item_width);
        BitmapLoadCallBack<ImageView> bitmapLoadCallBack = new BitmapLoadCallBack<ImageView>() {
            @Override
            public void onLoadCompleted(ImageView imageView, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, width, width, true));
            }

            @Override
            public void onLoadFailed(ImageView imageView, String s, Drawable drawable) {

            }
        };
        MyApplication.getInstance().getBitmapUtils().display(((PicSelectViewHolder) holder).pic, list.get(position), null, bitmapLoadCallBack);
        ((PicSelectViewHolder) holder).pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((PicSelectViewHolder) holder).isSelected) {
                    ((PicSelectViewHolder) holder).indicator.setImageResource(R.mipmap.ic_pic_unchecked_gray);
                    ((PicSelectViewHolder) holder).pic.setColorFilter(null);
                    ((PicSelectViewHolder) holder).isSelected = false;
                } else {
                    ((PicSelectViewHolder) holder).indicator.setImageResource(R.mipmap.ic_pic_checked_blue);
                    ((PicSelectViewHolder) holder).pic.setColorFilter(Color.parseColor("#77000000"));
                    ((PicSelectViewHolder) holder).isSelected = true;
                }

                if (onItemClickListener != null)
                    onItemClickListener.onClick(((PicSelectViewHolder) holder).pic, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onClick(View v, int position);
    }
}
