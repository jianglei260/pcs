package com.cuit.remind;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;


import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppContext;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.sys.window.WindowPage;

import java.util.List;

import dalvik.system.PathClassLoader;

/**
 * Created by ASUS-1 on 2015/9/13.
 */
public class RemindWindowPage extends WindowPage implements View.OnClickListener {
    private AppContext context;
    private RecyclerView recyclerView;
    private ImageButton addButton;
    private FrameLayout rootView;
    private LinearLayoutManager layoutManager;
    private RemindAdapter remindAdapter;
    private RemindUtils remindUtils;
    private List<RemindInfo> remindInfoList;
    private App app;

    public RemindWindowPage(App app, AppContext context) {
        super(app.getMainActivity());
        this.context = context;
        this.app = app;
    }

    public void initUI() {
        remindUtils = new RemindUtils(context);

        LayoutInflater inflater = LayoutInflater.from(RemindWindowPage.this.context);

        Log.d("inflater context", inflater.getContext().getClassLoader().toString());
        rootView = (FrameLayout) inflater.inflate(R.layout.main_view, null);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.id_main_recyclerview);
        layoutManager = new LinearLayoutManager(RemindWindowPage.this.context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        addButton = (ImageButton) rootView.findViewById(R.id.id_add_button);

        titleBar.setTitleText("提醒");
        titleBar.setLeftImageOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.goBack();
            }
        });
        addBodyView(rootView);
        addButton.setOnClickListener(this);
        remindInfoList = remindUtils.getRemindList();
        remindAdapter = new RemindAdapter(remindInfoList);
        recyclerView.setAdapter(remindAdapter);
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    public void onClick(View v) {
        AddRemindWindowPage addRemindWindowPage = new AddRemindWindowPage(app, context);
        app.startWindowPageForResult(addRemindWindowPage, 0);
    }

    @Override
    public void onResult(Object object, int requestCode) {
        super.onResult(object, requestCode);
        if (object != null) {
            remindInfoList.add((RemindInfo) object);
            remindAdapter.notifyDataSetChanged();
        }

    }
}
