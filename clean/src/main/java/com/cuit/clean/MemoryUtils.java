package com.cuit.clean;

import android.app.ActivityManager;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by ASUS-1 on 2015/9/12.
 */
public class MemoryUtils {
    private Context context;
    private ProgressCallBack progressCallBack;
    private ActivityManager activityManager;

    public MemoryUtils(Context context) {
        this.context = context;
        activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }

    public long getAvilableMemory() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem / (1024 * 1024);
    }

    public long getTotalMemory() {
        String path = "/proc/meminfo";
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader, 8192);
            String sizeValue = bufferedReader.readLine();
            String[] arrayString = sizeValue.split("\\s+");
            bufferedReader.close();
            return Integer.valueOf(arrayString[1]).intValue() / 1024;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void cleanMemory() {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessInfoList) {
            if (runningAppProcessInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE) {
                for (String pkgInfo : runningAppProcessInfo.pkgList) {
                    activityManager.killBackgroundProcesses(pkgInfo);
                }
                if (this.progressCallBack != null) {
                    progressCallBack.onProgress(getAvilableMemory());
                }
            }
        }
    }

    public interface ProgressCallBack {
        public void onProgress(long avilableMemory);
    }

    public void setProgressCallBack(ProgressCallBack progressCallBack) {
        this.progressCallBack = progressCallBack;
    }
}
