package com.xinniu.android.qiqueqiao.utils;

import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.AcceptedOrdersPersonActivity;
import com.xinniu.android.qiqueqiao.activity.ApproveCardActivity;
import com.xinniu.android.qiqueqiao.activity.CardingCardActivity;
import com.xinniu.android.qiqueqiao.activity.ClassRoomDetailActivity;
import com.xinniu.android.qiqueqiao.activity.CoopDetailActivity;
import com.xinniu.android.qiqueqiao.activity.DataPlusBuyActivity;
import com.xinniu.android.qiqueqiao.activity.HotResourceActivity;
import com.xinniu.android.qiqueqiao.activity.InteractInformActivity;
import com.xinniu.android.qiqueqiao.activity.InviteFriendActivity;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.activity.NewResourceActivity;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.activity.RewardDetailActivity;
import com.xinniu.android.qiqueqiao.activity.RewardOrderDetailActivity;
import com.xinniu.android.qiqueqiao.activity.SelectionResourceActivity;
import com.xinniu.android.qiqueqiao.activity.ServiceDetailActivity;
import com.xinniu.android.qiqueqiao.activity.SuperExposureActivity;
import com.xinniu.android.qiqueqiao.activity.SystemMsgActivity;
import com.xinniu.android.qiqueqiao.activity.TopCardActivity;
import com.xinniu.android.qiqueqiao.activity.VipV4ListActivity;
import com.xinniu.android.qiqueqiao.activity.WalletDetailActivity;
import com.xinniu.android.qiqueqiao.bean.CenterBean;
import com.xinniu.android.qiqueqiao.customs.FullScreenDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

//import android.support.annotation.RequiresApi;
//import android.support.v4.app.NotificationManagerCompat;
//import android.support.v4.content.ContextCompat;


/**
 * Created by yuchance on 2018/5/3.
 *
 * @author -Yu
 */

public class ComUtils {

    public static List<AppCompatActivity> activities = new ArrayList<>();

    public static boolean isLoginCheckPass(Context context, EditText usernameEt, EditText pwdEt) {
        if (StringUtils.isEmpty(usernameEt.getText())) {
            ToastUtils.showCentetImgToast(context, "?????????????????????");
            return false;
        }
        if (StringUtils.isEmpty(pwdEt.getText())) {
            ToastUtils.showCentetToast(context, "??????????????????");
            return false;
        }
        if (usernameEt.getText().toString().length() != 11) {
            ToastUtils.showCentetToast(context, "????????????????????????");
            return false;
        }
        return true;
    }

    public static boolean isLoginCheckPass(Context context, String usernameEt, EditText pwdEt) {
        if (StringUtils.isEmpty(usernameEt)) {
            ToastUtils.showCentetImgToast(context, "?????????????????????");
            return false;
        }
        if (StringUtils.isEmpty(pwdEt.getText())) {
            ToastUtils.showCentetToast(context, "??????????????????");
            return false;
        }
        if (usernameEt.length() != 11) {
            ToastUtils.showCentetToast(context, "????????????????????????");
            return false;
        }
        return true;
    }

    public static boolean isPhoneheckPass(Context context, EditText mbindpAccountEt) {
        String username = mbindpAccountEt.getText().toString();
        if (username.length() != 11) {
            ToastUtils.showCentetToast(context, "????????????????????????");
            return false;
        }
        if (StringUtils.isEmpty(mbindpAccountEt.getText())) {
            ToastUtils.showCentetImgToast(context, "?????????????????????");
            return false;
        }
        return true;
    }

    public static boolean isPhoneheckPass(Context context, String username) {
        if (username.length() != 11) {
            ToastUtils.showCentetToast(context, "????????????????????????");
            return false;
        }
        if (StringUtils.isEmpty(username)) {
            ToastUtils.showCentetImgToast(context, "?????????????????????");
            return false;
        }
        return true;
    }

    public static boolean isLoginCheckPhoneCode(Context context, EditText usernameEt, EditText pswEt) {
        if (StringUtils.isEmpty(usernameEt.getText())) {
            ToastUtils.showCentetImgToast(context, "?????????????????????");
            return false;
        }
        if (StringUtils.isEmpty(pswEt.getText())) {
            ToastUtils.showCentetToast(context, "?????????????????????");
            return false;
        }
        if (usernameEt.getText().toString().length() != 11) {
            ToastUtils.showCentetToast(context, "????????????????????????");
            return false;
        }
        return true;
    }

    public static boolean isLoginCheckPhoneCode(Context context, String usernameEt, String pswEt) {
        if (StringUtils.isEmpty(usernameEt)) {
            ToastUtils.showCentetImgToast(context, "?????????????????????");
            return false;
        }
        if (StringUtils.isEmpty(pswEt)) {
            ToastUtils.showCentetToast(context, "?????????????????????");
            return false;
        }
        if (usernameEt.length() != 11) {
            ToastUtils.showCentetToast(context, "????????????????????????");
            return false;
        }
        return true;
    }

    public static void addActivity(AppCompatActivity activity) {
        activities.add(activity);
    }

    public void removeActivity(AppCompatActivity activity) {
        activities.remove(activity);
    }

    public static void finishshortAll() {
        for (AppCompatActivity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }

    public static int countWindowTop(View view) {
        int[] locationx = new int[2];
        view.getLocationInWindow(locationx);
        int y = locationx[1];
        int py = DensityUtil.dp2px(y);
        return py;
    }

    public static int countScreenTop(View view) {
        int[] locationx = new int[2];
        view.getLocationOnScreen(locationx);
        int y = locationx[1];
        int py = DensityUtil.dp2px(y);
        return py;

    }


    public static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // ????????????View????????????????????????????????????
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // ?????????????????????
        final int screenHeight = getScreenHeight(anchorView.getContext());
        final int screenWidth = getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // ??????contentView?????????
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // ????????????????????????????????????????????????
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }

    /**
     * ??????????????????(px)
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * ??????????????????(px)
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static void setWindowStatusBarColor(AppCompatActivity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(activity, colorResId));

                //???????????????
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setWindowStatusBarColor(Dialog dialog, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = dialog.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(dialog.getContext().getResources().getColor(colorResId));

                //???????????????
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goToBannerNext(final Context mContext, String url, boolean isBanner) {
        if (TextUtils.isEmpty(url)) {
            ToastUtils.showCentetToast(mContext, "????????????,???????????????");
            return;
        }

        if (url.startsWith("qiqueqiao://www.xinniu.com")) {
            if (url.endsWith("invitation")) {
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                InviteFriendActivity.start(mContext);
            }

            if (url.startsWith("qiqueqiao://www.xinniu.com/sourceDetail")) {
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                Map<String, String> typeMap = new HashMap<>();
                Uri uri = Uri.parse(url);
                String queryStr = uri.getQuery();
                String[] ids = queryStr.split("&");
                for (int i = 0; i < ids.length; i++) {
                    String[] type = ids[i].split("=");
                    if (type.length > 0) {
                        typeMap.put(type[0], type[1]);
                    }
                }
                String sourceid = typeMap.get("sourceid");
                CoopDetailActivity.start(mContext, Integer.parseInt(sourceid));

            }

            if (url.startsWith("qiqueqiao://www.xinniu.com/differentIndustryClassDetail")) {//????????????
                Map<String, String> typeMap = new HashMap<>();
                Uri uri = Uri.parse(url);
                String queryStr = uri.getQuery();
                String[] ids = queryStr.split("&");
                for (int i = 0; i < ids.length; i++) {
                    String[] type = ids[i].split("=");
                    if (type.length > 0) {
                        typeMap.put(type[0], type[1]);
                    }
                }
                String id = typeMap.get("id");
                String videoId = typeMap.get("videoId");
                ClassRoomDetailActivity.start(mContext, Integer.parseInt(id), videoId);

            }
            if (url.endsWith("vipCenter")) {
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                mContext.startActivity(new Intent(mContext, VipV4ListActivity.class));
            }
            if (url.endsWith("taskCenter")) {
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                ApproveCardActivity.start(mContext, "task");
            }
            if (url.endsWith("newResource")) {
                NewResourceActivity.start(mContext);
            }
            if (url.endsWith("systemMessage")) {
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                Intent intent = new Intent(mContext, SystemMsgActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
            if (url.endsWith("serviceManagerChat")) {
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                Map<String, String> typeMap = new HashMap<>();
                Uri uri = Uri.parse(url);
                String queryStr = uri.getQuery();
                String[] ids = queryStr.split("&");
                for (int i = 0; i < ids.length; i++) {
                    String[] type = ids[i].split("=");
                    if (type.length > 0) {
                        typeMap.put(type[0], type[1]);
                    }
                }
                final String chatMessage = typeMap.get("chatMessage");
                RequestManager.getInstance().center(new RequestCallback<CenterBean>() {
                    @Override
                    public void requestStart(Call call) {

                    }

                    @Override
                    public void onSuccess(CenterBean centerBean) {
                        CenterBean mCenterBean = centerBean;
                        //IMUtils.singleChat(mContext, String.valueOf(mCenterBean.getUsers().getF_id()), "??????", "4", chatMessage);
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        ToastUtils.showCentetImgToast(mContext, msg);
                    }

                    @Override
                    public void requestEnd() {

                    }
                });
            }
            if (url.endsWith("interactiveMessageList")) {
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                InteractInformActivity.start(mContext);
            }
            if (url.contains("userCenterInfo")) {
                String[] urls = url.split("=");
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                if (urls.length > 1) {
                    String targetId = urls[1];
                    PersonCentetActivity.start(mContext, targetId, "1");
                }

            }
            if (url.contains("featuredResources")) {
                SelectionResourceActivity.start(mContext);
            }
            if (url.contains("dailyHotResources")) {
                HotResourceActivity.start(mContext);
            }
            if (url.startsWith("qiqueqiao://www.xinniu.com/serviceDetail")) {
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                Map<String, String> typeMap = new HashMap<>();
                Uri uri = Uri.parse(url);
                String queryStr = uri.getQuery();
                String[] ids = queryStr.split("&");
                for (int i = 0; i < ids.length; i++) {
                    String[] type = ids[i].split("=");
                    if (type.length > 0) {
                        typeMap.put(type[0], type[1]);
                    }
                }
                String sourceid = typeMap.get("serviceid");
                ServiceDetailActivity.start(mContext, Integer.parseInt(sourceid));

            }

            if (url.startsWith("qiqueqiao://www.xinniu.com/accountBillDetail")) {
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                Map<String, String> typeMap = new HashMap<>();
                Uri uri = Uri.parse(url);
                String queryStr = uri.getQuery();
                String[] ids = queryStr.split("&");
                for (int i = 0; i < ids.length; i++) {
                    String[] type = ids[i].split("=");
                    if (type.length > 0) {
                        typeMap.put(type[0], type[1]);
                    }
                }
                String sourceid = typeMap.get("id");
                WalletDetailActivity.start(mContext, Integer.parseInt(sourceid));

            }

            if (url.startsWith("qiqueqiao://www.xinniu.com/RewardDetail")) {
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                Map<String, String> typeMap = new HashMap<>();
                Uri uri = Uri.parse(url);
                String queryStr = uri.getQuery();
                String[] ids = queryStr.split("&");
                for (int i = 0; i < ids.length; i++) {
                    String[] type = ids[i].split("=");
                    if (type.length > 0) {
                        typeMap.put(type[0], type[1]);
                    }
                }
                String id = typeMap.get("id");

                RewardDetailActivity.start(mContext, id);

            }

            if (url.startsWith("qiqueqiao://www.xinniu.com/RewardReceivedOrder")) {
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                Map<String, String> typeMap = new HashMap<>();
                Uri uri = Uri.parse(url);
                String queryStr = uri.getQuery();
                String[] ids = queryStr.split("&");
                for (int i = 0; i < ids.length; i++) {
                    String[] type = ids[i].split("=");
                    if (type.length > 0) {
                        typeMap.put(type[0], type[1]);
                    }
                }
                String id = typeMap.get("id");
                String order_sn = typeMap.get("order_sn");
                RewardOrderDetailActivity.start(mContext, order_sn, Integer.parseInt(id));

            }
            if (url.startsWith("qiqueqiao://www.xinniu.com/RewardReceivedOrderPeople")) {
                String[] urls = url.split("=");
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                if (urls.length > 1) {
                    String order_sn = urls[1];
                    AcceptedOrdersPersonActivity.start(mContext, order_sn);
                }
            }

            if (url.startsWith("qiqueqiao://www.xinniu.com/PropsMallList")) {
//                qiqueqiao://www.xinniu.com/PropsMallList?mall_type=1
//                ?????????
//                ?????????
//                ?????????
//                ?????????
                String[] urls = url.split("=");
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                if (urls.length > 1) {
                    String type = urls[1];
                    if (type.equals("1")) {
                        TopCardActivity.start(mContext, "1");
                    } else if (type.equals("2")) {
                        SuperExposureActivity.start(mContext, "1");
                    } else if (type.equals("3")) {
                        CardingCardActivity.start(mContext,"1");
                    } else if (type.equals("4")) {
                        DataPlusBuyActivity.start(mContext);
                    }
                }
            }
        } else {
            //     if (isBanner) {
            if (url.contains("needLogin=1")) {
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
            }
            Intent intent = new Intent(mContext, ApproveCardActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("theUrl", url);
            bundle.putString("thetitle", "????????????");
            bundle.putString("webType", "Event");
            intent.putExtras(bundle);
            mContext.startActivity(intent, bundle);
//            } else {
//                if (url.equals("https://q.qiqueqiao.com/resource/pages/luckyDraw/luckyDraw.html")) {
//                    Intent intent = new Intent(mContext, ApproveCardActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("theUrl", url);
//                    bundle.putString("thetitle", "????????????");
//                    bundle.putString("webType", "Event");
//                    intent.putExtras(bundle);
//                    mContext.startActivity(intent, bundle);
//                } else {
//
//                    Intent intent1 = new Intent(mContext, MainActivity.class);
//                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    mContext.startActivity(intent1);
//                }
            //  }
        }
    }

//?????????app?????????????????????
    /**
     * ????????????NotificationManagerCompat ?????? areNotificationsEnabled()????????????????????????????????????NotificationManagerCompat ??? android.support.v4.app????????????API 22.1.0 ?????????????????? areNotificationsEnabled()????????? API 24.1.0??????????????????
     * areNotificationsEnabled ?????? API 19 ??????????????????????????????API 19 ???????????????true
     * */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isNotificationEnabled(Context context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            boolean areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled();
            return areNotificationsEnabled;
        }
        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass = null;
        /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isShowActivityEnabled(Context context) {


        return false;
    }


    //????????????????????????
    public static void goToSet(Context context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
//            // ????????????????????????????????????
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            intent.setData(Uri.parse("package:com.xinniu.android.qiqueqiao"));
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//            return;
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// ???????????????5.x????????????
//            // ????????????????????????????????????
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            intent.setData(Uri.parse("package:com.xinniu.android.qiqueqiao"));
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//            return;
//        }
        try {
            // ??????isOpened?????????????????????????????????????????????AppInfo??????????????????App????????????
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            //????????????????????? API 26, ???8.0??????8.0??????????????????
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, context.getApplicationInfo().uid);

            //????????????????????? API21??????25?????? 5.0??????7.1 ???????????????????????????
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);

            // ??????6 -MIUI9.6-8.0.0??????????????????????????????????????????????????????"????????????????????????"??????????????????????????????????????????????????????????????????I'm not ok!!!
            //  if ("MI 6".equals(Build.MODEL)) {
            //      intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            //      Uri uri = Uri.fromParts("package", getPackageName(), null);
            //      intent.setData(uri);
            //      // intent.setAction("com.android.settings/.SubSettings");
            //  }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // ?????????????????????????????????????????????????????????3??????OC105 API25
            Intent intent = new Intent();

            //??????????????????????????????????????????????????????????????????
            //https://blog.csdn.net/ysy950803/article/details/71910806
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            context.startActivity(intent);
        }
    }

    protected static FullScreenDialog mDialog;

    public static void showBookingToast(Context context, int type) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (type == 1) {
            mDialog = new FullScreenDialog(context, R.style.Them_dialog, R.layout.dialog_test_fullscreen);
        } else if (type == 2) {
            mDialog = new FullScreenDialog(context, R.style.Them_dialog, R.layout.dialog_load_fullscreen);
        } else {
            mDialog = new FullScreenDialog(context, R.style.Them_dialog, R.layout.dialog_new_fullscreen);
        }
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    public static void dismissBookingToast() {

        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    //????????????????????????
    public static String readWebUrl(String Url) {
        if (!StringUtils.isEmpty(Url)) {
            if (Url.startsWith("http://") || Url.startsWith("https://")) {
                return Url;
            } else {
                return "http://" + Url;
            }
        }
        return Url;
    }

    //?????????????????????????????????
    public static String phone2star(String phoneNum) {
        StringBuffer stringBuffer = new StringBuffer(phoneNum);
        stringBuffer.replace(3, 7, "****");
        return stringBuffer.toString();
    }


    //?????????????????????String???????????????
    public static void StringPositionClick(TextView strTv, String str, int start, int end, final phoneCallback phoneCallback) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                phoneCallback.setPhoneCall();
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        strTv.append(spannableString);
        strTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    //?????????????????????String???????????????
    public static void StringPositionClick(Context context, TextView strTv, String str, int start, int end, final int color, final phoneCallback phoneCallback) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                phoneCallback.setPhoneCall();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(color);
                ds.setUnderlineText(false);
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        strTv.setText(spannableString);
        strTv.setHighlightColor(ContextCompat.getColor(context, R.color.white));
        strTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public interface phoneCallback {
        void setPhoneCall();
    }


    //???String???????????????
    public static String StringPositionColor(String str, int start, int end, int color) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString.toString();
    }

    //???String???????????????
    public static SpannableString strToSpannableStr(String content, String keyWord, String color) {
        if (content == null) {
            return new SpannableString("");
        }
        if (content.contains(keyWord)) {
            SpannableString realNameStr = new SpannableString(content);
            realNameStr.setSpan(new ForegroundColorSpan(Color.parseColor(color)), content.indexOf(keyWord), content.indexOf(keyWord) + keyWord.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return realNameStr;
        } else {
            return new SpannableString(content);
        }
    }


}
