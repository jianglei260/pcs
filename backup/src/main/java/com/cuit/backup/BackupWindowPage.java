package com.cuit.backup;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cuit.pcs.apkloader.App;
import com.cuit.pcs.apkloader.AppContext;
import com.cuit.pcs.application.MyApplication;
import com.cuit.pcs.sys.window.FastWindow;
import com.cuit.pcs.sys.window.WindowPage;
import com.cuit.pcs.ui.widget.MaterialProgressDialog;

import java.util.List;

/**
 * Created by ASUS-1 on 2015/9/12.
 */
public class BackupWindowPage extends WindowPage {
    private AppContext context;
    private LinearLayout rootView;
    private Button buckupButton, restoreButton;
    private TextView infoText;
    private BackupUtils backupUtils;
    private App app;

    public BackupWindowPage(App app, AppContext context) {
        super(app.getMainActivity());
        this.context = context;
        this.app = app;
        initUI();
    }

    public void initUI() {
        rootView = new LinearLayout(context);
        rootView.setOrientation(LinearLayout.VERTICAL);
        rootView.setGravity(Gravity.CENTER);
        rootView.setBackgroundColor(Color.parseColor("#FF18B4ED"));

        infoText = new TextView(context);
        infoText.setTextColor(Color.WHITE);
        infoText.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams infoTextLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.addView(infoText, infoTextLayoutParams);

        buckupButton = new Button(context);
        buckupButton.setText("一键备份");
        buckupButton.setTextColor(Color.WHITE);
        buckupButton.setBackgroundColor(Color.parseColor("#FF37BC84"));
        LinearLayout.LayoutParams buckupButtonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buckupButtonLayoutParams.rightMargin = buckupButtonLayoutParams.leftMargin = 160;
        buckupButtonLayoutParams.topMargin = 160;
        rootView.addView(buckupButton, buckupButtonLayoutParams);

        restoreButton = new Button(context);
        restoreButton.setText("一键还原");
        restoreButton.setTextColor(Color.WHITE);
        restoreButton.setBackgroundColor(Color.parseColor("#FF37BC84"));
        LinearLayout.LayoutParams restoreButtonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        restoreButtonLayoutParams.rightMargin = restoreButtonLayoutParams.leftMargin = 160;
        restoreButtonLayoutParams.topMargin = 160;
        rootView.addView(restoreButton, restoreButtonLayoutParams);

        titleBar.setTitleText("信息备份");
        titleBar.setLeftImageOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.goBack();
            }
        });
        addBodyView(rootView);
        backupUtils = new BackupUtils(context);
        List<SMSInfo> list = backupUtils.getSMSList();
        infoText.setText("共有" + list.size() + "条短信");
        buckupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialProgressDialog dialog = new MaterialProgressDialog(MyApplication.getInstance().getMainActivity(), "正在备份");
                dialog.show();
                backupUtils.backup(new BackupUtils.BackupCallback() {
                    @Override
                    public void onSuccess() {
                        dialog.dismiss();
                        Toast.makeText(context, "备份成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed() {
                        dialog.dismiss();
                        Toast.makeText(context, "备份失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        restoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialProgressDialog dialog = new MaterialProgressDialog(MyApplication.getInstance().getMainActivity(), "正在还原");
                dialog.show();
                backupUtils.restore(new BackupUtils.BackupCallback() {
                    @Override
                    public void onSuccess() {
                        dialog.dismiss();
                        Toast.makeText(context, "还原成功", Toast.LENGTH_SHORT).show();
                        infoText.setText("共有" + backupUtils.getSMSList().size() + "条短信");
                    }

                    @Override
                    public void onFailed() {
                        dialog.dismiss();
                        Toast.makeText(context, "还原失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
