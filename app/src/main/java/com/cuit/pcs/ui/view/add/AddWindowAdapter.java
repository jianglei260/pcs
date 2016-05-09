package com.cuit.pcs.ui.view.add;

import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cuit.pcs.R;
import com.cuit.pcs.apkloader.AppInfo;
import com.cuit.pcs.apkloader.AppManager;
import com.cuit.pcs.apkloader.AppUtil;
import com.cuit.pcs.application.MyApplication;
import com.cuit.pcs.sys.utils.Utils;

import java.io.File;
import java.util.List;

/**
 * Created by ASUS-1 on 2015/9/10.
 */
public class AddWindowAdapter extends RecyclerView.Adapter {

    private List<AppInfo> list;
    private LayoutInflater inflater;

    private OnItemClickListener onItemClickListener;
    private OnViewItemAttached onViewItemAttached;
    private List<AppInfo> installedList, installingList;
    private static final String TAG = "AddWindowAdapter";

    public AddWindowAdapter(List<AppInfo> list) {
        this.list = list;
        installedList = AppManager.getInstance().getInstalledApps();
        installingList = AppManager.getInstance().getInstallingApps();
        inflater = LayoutInflater.from(MyApplication.getInstance());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.app_item_view, null);
        AddWindowViewHolder addWindowViewHolder = new AddWindowViewHolder(view);
        return addWindowViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int i) {
        AppInfo appInfo = list.get(i);
        MyApplication.getInstance().getBitmapUtils().display(((AddWindowViewHolder) viewHolder).imageView, appInfo.getLogo());
        ((AddWindowViewHolder) viewHolder).name.setText(appInfo.getName());
        ((AddWindowViewHolder) viewHolder).describe.setText(appInfo.getDescribe());
        ((AddWindowViewHolder) viewHolder).state.setText("安装");

        ((AddWindowViewHolder) viewHolder).state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, viewHolder.getLayoutPosition());
                }
            }
        });

        for (AppInfo localAppInfo : installedList) {
            if (appInfo.getObjectId().equals(localAppInfo.getObjectId())) {
                ((AddWindowViewHolder) viewHolder).state.setText(localAppInfo.getState());
                ((AddWindowViewHolder) viewHolder).size.setText(Formatter.formatFileSize(MyApplication.getInstance(), new File(AppUtil.getPath(appInfo)).length()));
            }

        }
        if (appInfo.getClassPath() == null) {
            ((AddWindowViewHolder) viewHolder).size.setText("web");
        }
        for (AppInfo localAppInfo : installingList) {
            if (appInfo.getObjectId().equals(localAppInfo.getObjectId())) {
                ((AddWindowViewHolder) viewHolder).state.setText(localAppInfo.getState());
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        public void onClick(View v, int postion);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
