package com.cuit.pcs.ui.view.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cuit.pcs.R;
import com.cuit.pcs.apkloader.AppInfo;
import com.cuit.pcs.application.MyApplication;

import java.util.List;

/**
 * Created by ASUS-1 on 2015/9/10.
 */
public class MainWindowAdapter extends RecyclerView.Adapter {

    private List<AppInfo> list;
    private LayoutInflater inflater;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnViewItemAttached onViewItemAttached;

    public MainWindowAdapter(List<AppInfo> list) {
        this.list = list;
        inflater = LayoutInflater.from(MyApplication.getInstance());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.mian_item_view, null);
        MainWindowViewHolder mainWindowViewHolder = new MainWindowViewHolder(view);
        return mainWindowViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        MyApplication.getInstance().getBitmapUtils().display(((MainWindowViewHolder) viewHolder).imageView, list.get(i).getLogo());
        ((MainWindowViewHolder) viewHolder).textView.setText(list.get(i).getName());
        if (onItemClickListener != null) {
            ((MainWindowViewHolder) viewHolder).imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(((MainWindowViewHolder) viewHolder).imageView, viewHolder.getLayoutPosition());
                }
            });
        }

        if (onItemLongClickListener != null) {
            ((MainWindowViewHolder) viewHolder).imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClickListener.onLongClick(((MainWindowViewHolder) viewHolder).imageView, viewHolder.getLayoutPosition());
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        public void onClick(View v, int postion);
    }

    public interface OnItemLongClickListener {
        public void onLongClick(View v, int postion);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
    public void setOnViewItemAttached(OnViewItemAttached onViewItemAttached) {
        this.onViewItemAttached = onViewItemAttached;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (this.onViewItemAttached != null) {
            onViewItemAttached.onAttahed(holder);
        }
    }

    public interface OnViewItemAttached {
        public void onAttahed(RecyclerView.ViewHolder viewHolder);
    }
}
