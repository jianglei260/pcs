package com.cuit.pcs.sys.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.media.ThumbnailUtils;
import android.util.Log;
import android.util.TypedValue;

import com.cuit.pcs.application.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ASUS-1 on 2015/8/12.
 */
public class Utils {
    private static long fileLenth;

    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    public static byte[] getScaledBitmapBytes(String path, int width, int length) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Bitmap scaledBitmap = ThumbnailUtils.extractThumbnail(bitmap, width, length);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        return outputStream.toByteArray();
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.argb(0, 0, 0, 0));
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    public static byte[] bitmapTobytes(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }

    public static long getCacheSizse() {
        File cacheFile = MyApplication.getInstance().getExternalCacheDir();
        fileLenth = 0;
        getFileSize(cacheFile);
        Log.d("path", cacheFile.getAbsolutePath());
        return fileLenth;
    }

    public static void deleteCache() {
        File cacheFile = MyApplication.getInstance().getExternalCacheDir();
        deleteFile(cacheFile);
    }

    public static void deleteFile(File parentFile) {
        if (parentFile.exists()) {
            if (parentFile.isDirectory()) {
                File[] files = parentFile.listFiles();
                for (File file : files) {
                    deleteFile(file);
                }
                parentFile.delete();
            } else {
                parentFile.delete();
            }
        }
    }

    private static void getFileSize(File parentFile) {
        if (parentFile.isDirectory()) {
            File[] files = parentFile.listFiles();
            for (File file : files) {
                getFileSize(file);
            }
        } else {
            fileLenth += parentFile.length();
        }
    }

    public static boolean copyFile(String src, String dst) {
        File srcFile = new File(src);
        File dstFile = new File(dst);
        if (!srcFile.exists())
            return false;
        try {
            FileInputStream fileInputStream = new FileInputStream(srcFile);
            FileOutputStream fileOutputStream = new FileOutputStream(dstFile);
            byte[] buff = new byte[1024];
            int count = 0;
            while ((count = fileInputStream.read(buff)) > 0) {
                fileOutputStream.write(buff, 0, count);
            }
            fileInputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean moveFile(String src, String dst) {
        File srcFile = new File(src);
        File dstFile = new File(dst);
        if (!srcFile.exists())
            return false;
        try {
            FileInputStream fileInputStream = new FileInputStream(srcFile);
            FileOutputStream fileOutputStream = new FileOutputStream(dstFile);
            byte[] buff = new byte[1024];
            int count = 0;
            while ((count = fileInputStream.read(buff)) > 0) {
                fileOutputStream.write(buff, 0, count);
            }
            fileInputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        srcFile.delete();
        return true;
    }
}
