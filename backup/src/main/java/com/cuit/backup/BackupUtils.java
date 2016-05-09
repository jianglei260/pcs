package com.cuit.backup;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.cuit.pcs.sys.user.User;
import com.cuit.pcs.sys.user.UserInfo;
import com.cuit.pcs.sys.utils.AVCloudUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS-1 on 2015/9/12.
 */
public class BackupUtils {
    private Context context;
    private List<SMSInfo> list;
    private Uri uri = Uri.parse("content://sms/");
    private ContentResolver contentResolver;
    private String[] projection = {Telephony.Sms.ADDRESS, Telephony.Sms.BODY, Telephony.Sms.TYPE, Telephony.Sms.DATE, Telephony.Sms.DATE_SENT};
    private UserInfo userInfo;

    public BackupUtils(Context context) {
        this.context = context;
        list = new ArrayList<>();
        contentResolver = context.getContentResolver();
    }

    public List<SMSInfo> getSMSList() {
        Cursor cursor = contentResolver.query(uri, projection, null, null, Telephony.Sms._ID);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    SMSInfo smsInfo = new SMSInfo();
                    smsInfo.setAddress(cursor.getString(cursor.getColumnIndex(projection[0])));
                    smsInfo.setBody(cursor.getString(cursor.getColumnIndex(projection[1])));
                    smsInfo.setType(cursor.getInt(cursor.getColumnIndex(projection[2])));
                    smsInfo.setDate(cursor.getString(cursor.getColumnIndex(projection[3])));
                    smsInfo.setDate_sent(cursor.getString(cursor.getColumnIndex(projection[4])));
                    list.add(smsInfo);
                } while (cursor.moveToNext());
                cursor.close();
            }
        }
        return list;
    }

    public void insertSMS(String jsonString) {
        List<SMSInfo> smsInfoList = JSONArray.parseArray(jsonString, SMSInfo.class);
        for (SMSInfo smsInfo : smsInfoList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(projection[0], smsInfo.getAddress());
            contentValues.put(projection[1], smsInfo.getBody());
            contentValues.put(projection[2], smsInfo.getType());
            contentValues.put(projection[3], smsInfo.getDate());
            contentValues.put(projection[4], smsInfo.getDate_sent());
            contentResolver.insert(uri, contentValues);
        }
    }

    public void backup(final BackupCallback backupCallback) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        String jsonString = jsonArray.toJSONString();
        BackupInfo backupInfo = new BackupInfo();
        userInfo = User.getInstance().getUserInfo();
        if (userInfo != null) {
            backupInfo.setUserName(userInfo.getUserName());
            backupInfo.setJsonValue(jsonString);
            AVCloudUtil.saveObject(backupInfo, new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        backupCallback.onSuccess();
                    } else {
                        backupCallback.onFailed();
                    }
                }
            });
        } else {
            backupCallback.onFailed();
        }

    }

    private String defaultPackage = "";

    public void restore(final BackupCallback backupCallback) {
        List<BackupInfo> restoreList = new ArrayList<>();
        userInfo = User.getInstance().getUserInfo();

        if (userInfo != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                defaultPackage = Telephony.Sms.getDefaultSmsPackage(context);
                if (!defaultPackage.equals("com.cuit.pcs")) {
                    Intent intent = new Intent();
                    intent.setAction(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                    intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, context.getPackageName());
                    context.startActivity(intent);
                    return;
                }
            }
            AVCloudUtil.findObject(BackupInfo.class, "userName", userInfo.getUserName(), 10, new AVCloudUtil.CloudFindCallBack() {
                @Override
                public <T> void done(List<T> list) {
                    if (list.size() > 0) {
                        for (BackupInfo backupInfo : (List<BackupInfo>) list) {
                            insertSMS(backupInfo.getJsonValue());
                        }
                        backupCallback.onSuccess();
                        if (defaultPackage.equals("com.cuit.pcs")) {
                            Intent intent = new Intent();
                            intent.setAction(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, defaultPackage);
                            context.startActivity(intent);
                        }
                    }
                }

                @Override
                public void failed() {
                    backupCallback.onSuccess();
                }
            });
        } else {
            backupCallback.onFailed();
        }

    }


    private BackupCallback backupCallback;

    public void setBackupCallback(BackupCallback backupCallback) {
        this.backupCallback = backupCallback;
    }

    public interface BackupCallback {
        public void onSuccess();

        public void onFailed();
    }
}
