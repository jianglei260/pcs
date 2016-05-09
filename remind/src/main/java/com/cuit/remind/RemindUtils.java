package com.cuit.remind;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.cuit.pcs.apkloader.BroadcastCompoment;
import com.cuit.pcs.application.MyApplication;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS-1 on 2015/9/13.
 */
public class RemindUtils {
    private Context context;
    private AlarmManager alarmManager;
    private DbUtils dbUtils;

    public RemindUtils(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        dbUtils = DbUtils.create(context);
    }

    public void setRemind(RemindInfo remindInfo) {
        Intent intent = new Intent("com.cuit.pcs");
        intent.putExtra("action", "ACTION_ALARM");
        intent.putExtra("name", remindInfo.getName());
        intent.putExtra("time", remindInfo.getTime());

        BroadcastCompoment broadcastCompoment = new BroadcastCompoment();
        broadcastCompoment.setAction("ACTION_ALARM");
        broadcastCompoment.setClassPath("com.cuit.remind.AlarmReceiver");
        broadcastCompoment.setName("提醒");
        MyApplication.getInstance().registeBroadcastCompoment(broadcastCompoment);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, remindInfo.getTime(), pendingIntent);
        try {
            dbUtils.save(remindInfo);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public List<RemindInfo> getRemindList() {
        List<RemindInfo> remindList = new ArrayList<>();
        try {
            List<RemindInfo> list = dbUtils.findAll(RemindInfo.class);
            if (list != null)
                remindList = list;
        } catch (DbException e) {
            e.printStackTrace();
        }
        return remindList;
    }

}
