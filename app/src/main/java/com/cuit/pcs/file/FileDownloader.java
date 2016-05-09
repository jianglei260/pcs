package com.cuit.pcs.file;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.cuit.pcs.application.Config;
import com.cuit.pcs.application.MyApplication;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS-1 on 2015/9/9.
 */
public class FileDownloader {
    private static FileDownloader fileDownloader;
    private DownloadManager downloadManager;
    private static Context context = MyApplication.getInstance();
    private Map<Long, String> downloadMap;


    public FileDownloader() {
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadMap = new HashMap<>();
    }

    public static FileDownloader getInstance() {
        if (fileDownloader == null)
            fileDownloader = new FileDownloader();
        return fileDownloader;
    }

    public String getUrl(long id) {
        return downloadMap.get(id);
    }

    public void addNewDownload(String url, String name) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setAllowedOverRoaming(false);

        request.setMimeType(MimeTypeMap.getFileExtensionFromUrl(url));
        request.setShowRunningNotification(true);
        request.setVisibleInDownloadsUi(true);
        File file = new File(Config.EXTERNAL_DIR);
        if (!file.exists())
            file.mkdir();
        File apkFile = new File(Config.EXTERNAL_DIR + File.separator + name + ".apk");
        if (apkFile.exists())
            apkFile.delete();
        request.setDestinationInExternalPublicDir("/pcs/", name + ".apk");
        long download_id = downloadManager.enqueue(request);
        downloadMap.put(download_id, url);
    }
}
