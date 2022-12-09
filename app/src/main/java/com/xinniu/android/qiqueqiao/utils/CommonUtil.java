package com.xinniu.android.qiqueqiao.utils;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;

/**
 * @className: CommonUtil
 * @classDescription: 通用工具类
 * @author: miao
 * @createTime: 2017年2月10日
 */
public class CommonUtil {

    /**
     * @author miao
     * @createTime 2017年2月10日
     * @lastModify 2017年2月10日
     * @param
     * @return
     */
    public static boolean isCameraCanUse() {
            boolean canUse = true;
            Camera mCamera = null;
            try {
                mCamera = Camera.open();
            } catch (Exception e) {
                canUse = false;
            }
            if (canUse) {
                if (mCamera != null)
                    mCamera.release();
                mCamera = null;
            }
            return canUse;
    }

    public static void startExeplore(Context context,String url){
        if (url.startsWith("http")){
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }else{
            Uri uri = Uri.parse("http://"+url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }

    }





}
