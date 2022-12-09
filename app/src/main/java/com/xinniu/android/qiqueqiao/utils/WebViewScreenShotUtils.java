package com.xinniu.android.qiqueqiao.utils;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class WebViewScreenShotUtils {

    //Default Page Max=6
    private static final int defaultPage = 6;


    public static Bitmap ActionScreenshot(Context context, WebView webView) {
        int screenHeight = getScreenHeight(context);
        int MAXScreenshotHeight = defaultPage * screenHeight;
        float scale = webView.getScale();
        int webViewHeight = (int) (webView.getContentHeight() * scale + 0.5);
        if (webViewHeight > MAXScreenshotHeight) {
            Log.d("xxxx", "screenHeight Very Big");
            webViewHeight = MAXScreenshotHeight;
        }
        Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(), webViewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        webView.draw(canvas);
        return bitmap;
    }


    public static Bitmap ActionScreenshot2(Context context, ScrollView scrollView) {
        Bitmap bitmap = null;
        try {
            int h = 0;
            for (int i = 0; i < scrollView.getChildCount(); i++) {
                h += scrollView.getChildAt(i).getHeight();
            }
            bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                    Bitmap.Config.RGB_565);
            final Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
            scrollView.draw(canvas);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }


    public static Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        v.draw(canvas);
        return bitmap;

    }
    public static boolean isS(Bitmap bitmap, Context context) {
        File realFile = null;
        boolean isWriteSuccess = false;
        String fileName = System.currentTimeMillis() + ".jpg";

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();

        File file = new File(path, "/iGTB");
        if (!file.exists()) {
            file.mkdirs();
        }
        realFile = new File(file, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(realFile);
            isWriteSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {

            //sendBroadcast
            if (isWriteSuccess && realFile.exists()) {
                scanFile(context, realFile.getAbsolutePath());
            }
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }


        return isWriteSuccess;
    }


    /**
     * getScreenHeight
     *
     * @param context
     * @return
     */
    private static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        return outMetrics.heightPixels;
    }

    private static void scanFile(Context context, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }
}
