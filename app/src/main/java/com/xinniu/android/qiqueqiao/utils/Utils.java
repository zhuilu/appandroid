package com.xinniu.android.qiqueqiao.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
//import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.xinniu.android.qiqueqiao.activity.AddPictruActivity;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipTwoDialog;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lzq on 2017/12/16.
 */

public class Utils {
    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    static WeakReference<Activity> sTopActivityWeakRef;
    static List<Activity> sActivityList = new LinkedList<>();

    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;
    private static final Canvas sCanvas = new Canvas();

    public static int dp2Px(int dp) {
        return Math.round(dp * DENSITY);
    }

    /**
     * 把View画到Bitmap上返回
     *
     * @param view
     * @return
     */
    public static Bitmap createBitmapFromView(View view) {


        if (view instanceof ImageView) {
            /**
             * Drawable是一个描述图片样子的类，只是描述，所以颜色等也可以是一个Drawable，若是ColorDrawable则需要创建bitmap，并把颜色绘制到bitmap上，
             * 下面只是简单处理了一下当时BitmapDrawable，并没有处理是ColorDrawable时。其实获取view的镜像也可以采用这种方法：
             *  view.setDrawingCacheEnabled(true);

             Bitmap bitmap=Bitmap.createBitmap(view.getDrawingCache());
             view.setDrawingCacheEnabled(false);

             return bitmap;
             *
             */
            Drawable drawable = ((ImageView) view).getDrawable();
            if (drawable != null && drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }
        }
        view.clearFocus();
        Bitmap bitmap = createBitmapSafely(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888, 1);
        if (bitmap != null) {
            synchronized (sCanvas) {
                Canvas canvas = sCanvas;
                canvas.setBitmap(bitmap);
                view.draw(canvas);//手动调用view的draw将导致view被绘制到了空白的bitmap上
                canvas.setBitmap(null);
            }
        }
        return bitmap;
    }

    /**
     * 当创建图片导致内存溢出时，并且尝试次数没达到retryCount时，回收内存，再次执行创建，
     *
     * @param width
     * @param height
     * @param config
     * @param retryCount
     * @return
     */
    public static Bitmap createBitmapSafely(int width, int height, Bitmap.Config config, int retryCount) {
        try {
            //创建指定width*height大小，并且颜色是config的空白图片
            return Bitmap.createBitmap(width, height, config);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (retryCount > 0) {
                System.gc();//建议虚拟机回收内存
                return createBitmapSafely(width, height, config, retryCount - 1);
            }
            return null;
        }
    }

    private static Application.ActivityLifecycleCallbacks mCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            sActivityList.add(activity);
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivityWeakRef(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            sActivityList.remove(activity);
        }
    };

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param app 应用
     */
    public static void init(@NonNull final Application app) {
        Utils.sApplication = app;
        app.registerActivityLifecycleCallbacks(mCallbacks);
    }

    /**
     * 获取 Application
     *
     * @return Application
     */
    public static Application getApp() {
        if (sApplication != null) return sApplication;
        throw new NullPointerException("u should init first");
    }

    private static void setTopActivityWeakRef(Activity activity) {
        if (sTopActivityWeakRef == null || !activity.equals(sTopActivityWeakRef.get())) {
            sTopActivityWeakRef = new WeakReference<>(activity);
        }
    }

    public static void copyContent(String content, Context context) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) sApplication.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(content);
        //  ToastUtils.showCentetImgToast(sApplication, "复制成功，可以发给朋友们了。");
//        AlertDialogUtils.AlertDialog(context, "打开微信,即可搜索添加好友", "取消", "打开微信", new AlertDialogUtils.setOnClick() {
//            @Override
//            public void setLeftOnClick(DialogInterface dialog) {
//                dialog.dismiss();
//            }
//
//            @Override
//            public void setRightOnClick(DialogInterface dialog) {
//                dialog.dismiss();
//                getWechatApi();
//            }
//        });
        new QLTipTwoDialog.Builder(context)
                .setCancelable(true)
                .setCancelableOnTouchOutside(true)
                .setTitle("复制成功")
                .setMessage("打开微信,即可搜索添加好友")
                .setPositiveButton("打开微信", new QLTipTwoDialog.IPositiveCallback() {
                    @Override
                    public void onClick() {

                        getWechatApi();
                    }
                })
                .setNegativeButton("取消", new QLTipTwoDialog.INegativeCallback() {
                    @Override
                    public void onClick() {

                    }
                })
                .show((Activity) context);

    }

    /**
     * 跳转到微信
     */
    private static void getWechatApi() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            sApplication.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // TODO: handle exception
            ToastUtils.showCentetImgToast(sApplication, "检查到您手机没有安装微信，请安装后使用该功能");
        }
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
