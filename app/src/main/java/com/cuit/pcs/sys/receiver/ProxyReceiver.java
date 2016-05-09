package com.cuit.pcs.sys.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.cuit.pcs.apkloader.AppManageService;
import com.cuit.pcs.apkloader.BroadcastCompoment;
import com.cuit.pcs.application.MyApplication;

import java.io.File;
import java.util.List;

/**
 * Created by ASUS-1 on 2015/9/13.
 */
public class ProxyReceiver extends BroadcastReceiver {
    private List<BroadcastCompoment> broadcastCompomentList;

    @Override
    public void onReceive(Context context, Intent intent) {
        broadcastCompomentList = MyApplication.getInstance().getBroadcastCompomentList();
        String action = intent.getStringExtra("action");
        for (BroadcastCompoment broadcastCompoment : broadcastCompomentList) {
            if (broadcastCompoment.getAction().equals(action)) {
                String path = context.getFilesDir().getAbsolutePath() + File.separator + broadcastCompoment.getName() + ".apk";
                AppManageService.getInstance().onBroadcastReceive(path, broadcastCompoment.getClassPath(), context, intent);
                break;
            }
        }
    }
}
