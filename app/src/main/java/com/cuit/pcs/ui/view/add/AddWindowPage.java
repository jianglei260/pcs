package com.cuit.pcs.ui.view.add;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cuit.pcs.R;
import com.cuit.pcs.apkloader.AppInfo;
import com.cuit.pcs.apkloader.AppManager;
import com.cuit.pcs.apkloader.EventBus;
import com.cuit.pcs.sys.utils.AVCloudUtil;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.sys.window.WindowPage;
import com.cuit.pcs.ui.widget.MaterialProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS-1 on 2015/9/10.
 */
public class AddWindowPage extends WindowPage implements AddWindowAdapter.OnItemClickListener {
    private Context context;
    private LinearLayout rootView;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<AppInfo> infoList;
    private AddWindowAdapter addWindowAdapter;
    private List<AppInfo> addedAppInfoList;
    private MaterialProgressDialog progressDialog;

    public AddWindowPage(Context context) {
        super(context);
        this.context = context;
        initUI();
    }

    private void initUI() {
        rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.app_list_view, null);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.id_app_list_recyclerview);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        infoList = new ArrayList<>();
        addedAppInfoList = new ArrayList<>();
        addWindowAdapter = new AddWindowAdapter(infoList);
        recyclerView.setAdapter(addWindowAdapter);
        addWindowAdapter.setOnItemClickListener(this);
        catchAppList();
        titleBar.setTitleText("添加插件");
        titleBar.setLeftImageOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FastWindow.getInstance().goBack();
            }
        });
        addBodyView(rootView);
        progressDialog = new MaterialProgressDialog(context, "正在加载", Color.WHITE);
        progressDialog.show();
        EventBus.getInstance().registe(this, "onEvent");
    }

    public void onEvent(Object object) {
        AppInfo appInfo = (AppInfo) object;
        addWindowAdapter.notifyDataSetChanged();
    }


    private void catchAppList() {
        AVCloudUtil.findObject(AppInfo.class, infoList, new AVCloudUtil.CloudFindCallBack() {
            @Override
            public <T> void done(List<T> list) {
                AppManager.getInstance().setStoreList(infoList);
                addWindowAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void failed() {

            }
        });
    }

    @Override
    public void onClick(View v, int postion) {
        if (((TextView) v).getText().toString().equals("下载中"))
            Snackbar.make(rootView, "正在下载插件", Snackbar.LENGTH_SHORT).show();
        else if (!((TextView) v).getText().toString().equals("已安装")) {
            AppManager.getInstance().addApp(infoList.get(postion));
            ((AddWindowViewHolder) recyclerView.findViewHolderForLayoutPosition(postion)).state.setText("下载中");
            Snackbar.make(rootView, "正在下载插件", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(rootView, "该插件已安装，可以卸载后重新安装", Snackbar.LENGTH_SHORT).show();
        }

    }
}
