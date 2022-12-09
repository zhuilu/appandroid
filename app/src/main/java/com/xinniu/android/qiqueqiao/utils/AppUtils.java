package com.xinniu.android.qiqueqiao.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.xinniu.android.qiqueqiao.base.BaseApp;

/**
 * Created by wkchen on 2017/1/13.
 */

public class AppUtils {

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersionCode() {
        try {
            PackageManager manager = BaseApp.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BaseApp.getInstance().getPackageName(), 0);
            String version = info.versionCode + "";
            return version + "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取版本Name
     *
     * @return 当前应用的版本号
     */
    public static String getVersionName() {
        try {
            PackageManager manager = BaseApp.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BaseApp.getInstance().getPackageName(), 0);
            String version = info.versionName + "";
            return version + "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
