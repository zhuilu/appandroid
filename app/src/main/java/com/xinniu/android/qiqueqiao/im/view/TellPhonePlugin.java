//package com.xinniu.android.qiqueqiao.im.view;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.core.app.ActivityCompat;
//import androidx.fragment.app.Fragment;
//
//import com.xinniu.android.qiqueqiao.R;
//import com.xinniu.android.qiqueqiao.activity.VipV4ListActivity;
//import com.xinniu.android.qiqueqiao.bean.SecretPhoneBean;
//import com.xinniu.android.qiqueqiao.request.RequestManager;
//import com.xinniu.android.qiqueqiao.request.callback.SecretPhoneCallback;
//import com.xinniu.android.qiqueqiao.utils.ToastUtils;
//
//import io.rong.imkit.RongExtension;
//import io.rong.imkit.plugin.IPluginModule;
//import pub.devrel.easypermissions.AfterPermissionGranted;
//import pub.devrel.easypermissions.EasyPermissions;
//
///**
// * Created by yuchance on 2018/4/20.
// */
//
//public class TellPhonePlugin implements IPluginModule{
//
//    private Context context;
//    private String targetId;
//    private String phoneNum;
//
//
//    @Override
//    public Drawable obtainDrawable(Context context) {
//        return context.getResources().getDrawable(R.drawable.selector_phonecall);
//    }
//
//    @Override
//    public String obtainTitle(Context context) {
//        return context.getString(R.string.tellphone_text);
//    }
//
//    @Override
//    public void onClick(final Fragment fragment, RongExtension rongExtension) {
//
//        context = fragment.getActivity().getApplicationContext();
//        targetId = rongExtension.getTargetId();
//        int toUserId = Integer.parseInt(targetId);
//
//
//        RequestManager.getInstance().getSecretPhone(toUserId, new SecretPhoneCallback() {
//            @Override
//            public void onSuccess(SecretPhoneBean bean) {
//                phoneNum = bean.getMobile();
//                goTocall(phoneNum,fragment);
//            }
//            @Override
//            public void onFailue(int code, String msg) {
//                if (code == 301){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
//                    builder.setMessage(msg);
//                    builder.setPositiveButton("去开通", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(context, VipV4ListActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("svip", "svip");
//                            intent.putExtras(bundle);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            context.startActivity(intent, bundle);
//                        }
//                    });
//                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    builder.show();
//
//                }else {
//                    ToastUtils.showToast(context,msg);
//                }
//
//
//            }
//        });
//
//
//
//
//
//
//    }
//    @AfterPermissionGranted(11111)
//    private void goTocall(String phoneNum,Fragment fragment) {
//            if (EasyPermissions.hasPermissions(fragment.getActivity(), new String[]{Manifest.permission.CALL_PHONE})) {
//                Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNum));
//                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                if (ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    ToastUtils.showCentetToast(fragment.getActivity(),"未获取电话权限");
//                    return;
//                }
//                context.startActivity(intent1);
//            } else {
//                EasyPermissions.requestPermissions(fragment.getActivity(),
//                        "系统需要获取您的电话权限",
//                        11111, new String[]{Manifest.permission.CALL_PHONE});
//            }
//    }
//
//    @Override
//    public void onActivityResult(int i, int i1, Intent intent) {
//
//    }
//
//}
