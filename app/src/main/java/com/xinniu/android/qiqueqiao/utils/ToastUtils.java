package com.xinniu.android.qiqueqiao.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
//import android.support.annotation.ColorInt;
//import android.support.annotation.DrawableRes;
//import android.support.annotation.LayoutRes;
//import android.support.annotation.NonNull;
//import android.support.annotation.StringRes;
//import android.support.v4.view.ViewCompat;
//import android.support.v4.widget.TextViewCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;

import com.xinniu.android.qiqueqiao.R;

import java.lang.ref.WeakReference;


/**

 */
public class ToastUtils
{
    public static int LENGTH_LONG = Toast.LENGTH_LONG;
    public static int LENGTH_SHORT = Toast.LENGTH_SHORT;
    private static Toast mToast;

    private static final int     COLOR_DEFAULT = 0xFEFFFFFF;
    private static final Handler HANDLER       = new Handler(Looper.getMainLooper());

    private static Toast               sToast;
    private static WeakReference<View> sViewWeakReference;
    private static int sLayoutId  = -1;
    private static int gravity    = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private static int xOffset    = 0;
    private static int yOffset    = (int) (64 * Utils.getApp().getResources().getDisplayMetrics().density + 0.5);
    private static int bgColor    = COLOR_DEFAULT;
    private static int bgResource = -1;
    private static int msgColor   = COLOR_DEFAULT;

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    
    /**
     * 
     * Methods Name: showToast
     * @author FU ZHIXUE
     * Description: 普通文本消息提示
     * @param context
     * @param text
     * @param duration
     * Comments:
     */
    public static void showToast(Context context,CharSequence text,int duration){
        mToast = makeText(context, text, duration);
        if(mToast!=null){
            mToast.show();
        }
    }

    /**
     * HUANG JUNHAO 2015-2-10
     * @param context
     * @param resourceId 资源文件中的id
     */
    public static void showToast(Context context,int resourceId)
    {
        mToast = makeText(context, resourceId, Toast.LENGTH_SHORT);
        if(mToast!=null){
            mToast.show();
        }
    }
    
    private static Toast makeText(Context context,CharSequence text,int duration){
        //创建一个Tost提示消息
        Toast toast = null;
        if(null != context && !TextUtils.isEmpty(text))
        {
            toast = Toast.makeText(context, text, duration);
        }
        return toast;
    }

    private static Toast makeText(Context context,int resourceId,int duration) {
        //创建一个Tost提示消息
        Toast toast = null;
        if(null != context){
            toast = Toast.makeText(context, resourceId, duration);
        }
        return toast;
    }

    public static void showToast(Context context,String text) {  
    	   if(mToast == null) {  
    	       mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);  
    	    } else {  
    	    	mToast.setText(text);       
    	    	mToast.setDuration(Toast.LENGTH_SHORT);  
            }
    	    mToast.show();  
    	} 

    public static void showCentetToast(Context context,String msg){
        showCentetImgToast(context,msg);
//        if (msg == null){
//            return;
//        }
//
//        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER, 0, 0);
////        LinearLayout toastView = (LinearLayout) toast.getView();
////        ImageView imageCodeProject = new ImageView(context);
////        imageCodeProject.setImageResource(R.mipmap.ic_launcher);
////        toastView.addView(imageCodeProject, 0);
//        toast.show();
    }
    public static void showCentetImgToast(Context context,String msg){
        if (msg == null){
            return;
        }
        setGravity(Gravity.CENTER, 0, 0);
        final View view = getView(R.layout.toast_no_data);
        TextView msgTv = (TextView) view.findViewById(R.id.toast_msg);
        msgTv.setText(msg);
     //   show(view, Toast.LENGTH_SHORT);
        show(view, 1600);
    }
    public static void showSuccessToast(Context context,String msg){
        if (msg == null){
            return;
        }
        setGravity(Gravity.CENTER, 0, 0);
        final View view = getView(R.layout.toast_request_success);
        TextView msgTv = (TextView) view.findViewById(R.id.toast_msg);
        msgTv.setText(msg);
        show(view, Toast.LENGTH_SHORT);
    }
    public static void showSuccessfulToast(Context context,String msg){
        if (msg == null){
            return;
        }
        setGravity(Gravity.CENTER, 0, 0);
        final View view = getView(R.layout.toast_success_data);
        TextView msgTv = (TextView) view.findViewById(R.id.toast_msg);
        msgTv.setText(msg);
//        show(view, Toast.LENGTH_SHORT);
        show(view, 1600);
    }


    /**
     * 设置吐司位置
     *
     * @param gravity 位置
     * @param xOffset x 偏移
     * @param yOffset y 偏移
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset) {
        ToastUtils.gravity = gravity;
        ToastUtils.xOffset = xOffset;
        ToastUtils.yOffset = yOffset;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor 背景色
     */
    public static void setBgColor(@ColorInt final int backgroundColor) {
        ToastUtils.bgColor = backgroundColor;
    }

    /**
     * 设置背景资源
     *
     * @param bgResource 背景资源
     */
    public static void setBgResource(@DrawableRes final int bgResource) {
        ToastUtils.bgResource = bgResource;
    }

    /**
     * 设置消息颜色
     *
     * @param msgColor 颜色
     */
    public static void setMsgColor(@ColorInt final int msgColor) {
        ToastUtils.msgColor = msgColor;
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showShort(@NonNull final CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源 Id
     */
    public static void showShort(@StringRes final int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源 Id
     * @param args  参数
     */
    public static void showShort(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 安全地显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShort(final String format, final Object... args) {
        show(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text 文本
     */
    public static void showLong(@NonNull final CharSequence text) {
        show(text, Toast.LENGTH_LONG);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源 Id
     */
    public static void showLong(@StringRes final int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源 Id
     * @param args  参数
     */
    public static void showLong(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 安全地显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLong(final String format, final Object... args) {
        show(format, Toast.LENGTH_LONG, args);
    }

    /**
     * 安全地显示短时自定义吐司
     */
    public static View showCustomShort(@LayoutRes final int layoutId) {
        final View view = getView(layoutId);
        show(view, Toast.LENGTH_SHORT);
        return view;
    }

    /**
     * 安全地显示长时自定义吐司
     */
    public static View showCustomLong(@LayoutRes final int layoutId) {
        final View view = getView(layoutId);
        show(view, Toast.LENGTH_LONG);
        return view;
    }

    /**
     * 取消吐司显示
     */
    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }

    private static void show(@StringRes final int resId, final int duration) {
        show(Utils.getApp().getResources().getText(resId).toString(), duration);
    }

    private static void show(@StringRes final int resId, final int duration, final Object... args) {
        show(String.format(Utils.getApp().getResources().getString(resId), args), duration);
    }

    private static void show(final String format, final int duration, final Object... args) {
        show(String.format(format, args), duration);
    }

    private static void show(final CharSequence text, final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                sToast = Toast.makeText(Utils.getApp(), text, duration);
                // solve the font of toast
                TextView tvMessage = (TextView) sToast.getView().findViewById(android.R.id.message);
                TextViewCompat.setTextAppearance(tvMessage, android.R.style.TextAppearance);
                tvMessage.setTextColor(msgColor);
                sToast.setGravity(gravity, xOffset, yOffset);
                setBg(tvMessage);
                sToast.show();
            }
        });
    }

    private static void show(final View view, final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                sToast = new Toast(Utils.getApp());
                sToast.setView(view);
                sToast.setDuration(duration);
                sToast.setGravity(gravity, xOffset, yOffset);
                setBg();
                sToast.show();
            }
        });
    }

    private static void setBg() {
        View toastView = sToast.getView();
        if (bgResource != -1) {
            toastView.setBackgroundResource(bgResource);
        } else if (bgColor != COLOR_DEFAULT) {
            Drawable background = toastView.getBackground();
            if (background != null) {
                background.setColorFilter(new PorterDuffColorFilter(bgColor, PorterDuff.Mode.SRC_IN));
            } else {
                ViewCompat.setBackground(toastView, new ColorDrawable(bgColor));
            }
        }
    }

    private static void setBg(final TextView tvMessage) {
        View toastView = sToast.getView();
        if (bgResource != -1) {
            toastView.setBackgroundResource(bgResource);
            tvMessage.setBackgroundColor(Color.TRANSPARENT);
        } else if (bgColor != COLOR_DEFAULT) {
            Drawable tvBg = toastView.getBackground();
            Drawable messageBg = tvMessage.getBackground();
            if (tvBg != null && messageBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(bgColor, PorterDuff.Mode.SRC_IN));
                tvMessage.setBackgroundColor(Color.TRANSPARENT);
            } else if (tvBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(bgColor, PorterDuff.Mode.SRC_IN));
            } else if (messageBg != null) {
                messageBg.setColorFilter(new PorterDuffColorFilter(bgColor, PorterDuff.Mode.SRC_IN));
            } else {
                toastView.setBackgroundColor(bgColor);
            }
        }
    }

    private static View getView(@LayoutRes final int layoutId) {
        if (sLayoutId == layoutId) {
            if (sViewWeakReference != null) {
                final View toastView = sViewWeakReference.get();
                if (toastView != null) {
                    return toastView;
                }
            }
        }
        LayoutInflater inflate = (LayoutInflater) Utils.getApp().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View toastView = inflate.inflate(layoutId, null);
        sViewWeakReference = new WeakReference<>(toastView);
        sLayoutId = layoutId;
        return toastView;
    }
}
