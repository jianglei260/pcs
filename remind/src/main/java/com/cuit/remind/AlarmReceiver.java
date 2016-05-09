package com.cuit.remind;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cuit.pcs.ui.activity.MainActivity;

/**
 * Created by ASUS-1 on 2015/9/13.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        RemindInfo remindInfo = new RemindInfo();
        remindInfo.setName(intent.getStringExtra("name"));
        remindInfo.setTime(intent.getLongExtra("time", 0));
        notify(context, remindInfo);
    }

    public void notify(Context context, RemindInfo remindInfo) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.mipmap.ic_remind_blue, "新提醒", System.currentTimeMillis());
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.vibrate = new long[]{1000, 500};
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setLatestEventInfo(context, "新提醒", remindInfo.getName(), pendingIntent);
        notificationManager.notify((int) remindInfo.getTime(), notification);
    }
}
