package com.cuit.pcs.sys.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.cuit.pcs.apkloader.AppManager;
import com.cuit.pcs.file.FileDownloader;

/**
 * Created by ASUS-1 on 2015/9/18.
 */
public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
            String url = FileDownloader.getInstance().getUrl(intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0));
            AppManager.getInstance().onAppDownloaded(url);
        }
    }
}
