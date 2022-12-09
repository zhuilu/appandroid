package com.xinniu.android.qiqueqiao.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.base.BaseApp;
import com.xinniu.android.qiqueqiao.bean.CenterBean;
import com.xinniu.android.qiqueqiao.bean.DetailedUserInfoBean;
import com.xinniu.android.qiqueqiao.bean.UploadBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLBottomDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.callback.ResultCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.TokePhotoUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;
import com.xinniu.android.qiqueqiao.zxing.activity.CaptureActivity;
import com.xinniu.android.qiqueqiao.zxing.decoding.RGBLuminanceSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import io.rong.imlib.RongIMClient;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;

/**
 * Created by yuchance on 2018/5/2.
 */

public class CompleteInfoActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.bcominfo_finishImg)
    ImageView bcominfoFinishImg;
    @BindView(R.id.mcominfo_name_et)
    EditText mcominfoNameEt;
    @BindView(R.id.mcominfo_company_et)
    TextView mcominfoCompanyet;
    @BindView(R.id.mcominfo_position_et)
    EditText mcominfopositionet;
    @BindView(R.id.bcominfo_complete)
    TextView bcominfoComplete;
    @BindView(R.id.bcominfo_circleimg)
    CircleImageView bcominfoCircleimg;
    private int tokeType = -1;
    private TokePhotoUtils tokePhotoUtils;
    private Call mCall;
    private String headpic;
    private String thumbImg;
    private String token;
    private int userId;
    private String rongToken;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //do something,refresh UI;
                    updateHead();
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_complete_info;
    }


    public static void start(Context context, String token, int userId, String rongToken) {
        Intent intent = new Intent(context, CompleteInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        bundle.putInt("userId", userId);
        bundle.putString("rongToken", rongToken);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        ComUtils.addActivity(CompleteInfoActivity.this);
        tokePhotoUtils = TokePhotoUtils.getInstance(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            token = bundle.getString("token");
            userId = bundle.getInt("userId");
            rongToken = bundle.getString("rongToken");
        }
        RequestManager.getInstance().getUserInfo(userId, token, new RequestCallback<DetailedUserInfoBean>() {
            @Override
            public void requestStart(Call call) {
                mCall = call;
                showBookingToast(1);
            }

            @Override
            public void onSuccess(DetailedUserInfoBean detailedUserInfoBean) {
                if (detailedUserInfoBean != null) {
                    if (!TextUtils.isEmpty(detailedUserInfoBean.getThumb_img())) {
                        thumbImg = detailedUserInfoBean.getThumb_img();
                    }
                    if (!TextUtils.isEmpty(detailedUserInfoBean.getHead_pic())) {
                        headpic = detailedUserInfoBean.getHead_pic();
                    }

                    if (!TextUtils.isEmpty(detailedUserInfoBean.getThumb_img())) {
                        ShowUtils.showImgPerfect(bcominfoCircleimg, detailedUserInfoBean.getThumb_img(), 1);
                    } else {
                        if (!TextUtils.isEmpty(detailedUserInfoBean.getHead_pic())) {
                            ShowUtils.showImgPerfect(bcominfoCircleimg, detailedUserInfoBean.getHead_pic(), 1);
                        }
                    }
                    if (!TextUtils.isEmpty(detailedUserInfoBean.getRealname())) {
                        ShowUtils.showTextPerfect(mcominfoNameEt, detailedUserInfoBean.getRealname());
                        mcominfoNameEt.setSelection(detailedUserInfoBean.getRealname().length());
                    }
                    mcominfoNameEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                    if (!TextUtils.isEmpty(detailedUserInfoBean.getCorporate_name())) {
                        ShowUtils.showTextPerfect(mcominfoCompanyet, detailedUserInfoBean.getCorporate_name());
                    }
                    if (!TextUtils.isEmpty(detailedUserInfoBean.getPosition())) {
                        ShowUtils.showTextPerfect(mcominfopositionet, detailedUserInfoBean.getPosition());
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {

            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });


    }


    @OnClick({R.id.bcominfo_finishImg, R.id.bcominfo_complete, R.id.bcominfo_circleimg, R.id.mcominfo_company_et})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bcominfo_finishImg:
                finish();
                break;
            case R.id.bcominfo_complete:
                String name = mcominfoNameEt.getText().toString();
                String companyName = mcominfoCompanyet.getText().toString();
                String position = mcominfopositionet.getText().toString();
                if (TextUtils.isEmpty(headpic)) {
                    ToastUtils.showCentetToast(CompleteInfoActivity.this, "请上传头像");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showCentetToast(CompleteInfoActivity.this, "请输入真实姓名");
                    return;
                }
                if (name.length() > 5) {
                    ToastUtils.showCentetToast(CompleteInfoActivity.this, "真实姓名不能超过5位");
                    return;
                }
                if (TextUtils.isEmpty(companyName)) {
                    ToastUtils.showCentetToast(CompleteInfoActivity.this, "请输入公司名称");
                    return;
                }
                if (TextUtils.isEmpty(position)) {
                    ToastUtils.showCentetToast(CompleteInfoActivity.this, "请输入您的职位");
                    return;
                }
                completeData(name, companyName, position);
                break;
            case R.id.bcominfo_circleimg:
                new QLBottomDialog.Builder(CompleteInfoActivity.this)
                        .setNormalColor(R.color._999)
                        .setStrOne("拍照")
                        .setStrTwo("从手机相册选择")
                        .setStrCancel("取消")
                        .setBottomDialogItemCallback(new QLBottomDialog.BottomDialogItemCallback() {
                            @Override
                            public void onClick(int position) {
                                switch (position) {
                                    case 0:
                                        tokeType = 0;
                                        break;
                                    case 1:
                                        tokeType = 1;
                                        break;
                                }
                                requestPermission();
                            }
                        }).show(CompleteInfoActivity.this);
                break;
            case R.id.mcominfo_company_et:
                Intent in = new Intent(CompleteInfoActivity.this, CompanyNameInputActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("companyName", "");
                in.putExtras(bundle);
                startActivityForResult(in, 33, bundle);
                break;
            default:
                break;
        }
    }

    /**
     * 获取权限保存图片
     */
    @AfterPermissionGranted(TokePhotoUtils.PERMISSION_TOKE_PHOTO)
    public void requestPermission() {
        if (EasyPermissions.hasPermissions(CompleteInfoActivity.this, TokePhotoUtils.TOKE_PHOTO)) {
            switch (tokeType) {
                case 0://拍照
                    tokePhotoUtils.tokePhoto(CompleteInfoActivity.this);
                    break;
                case 1://图册
                    tokePhotoUtils.chooseGallary(CompleteInfoActivity.this);
                    break;
            }
        } else {
            EasyPermissions.requestPermissions(
                    CompleteInfoActivity.this,
                    getString(R.string.permission_need_toke_photo),
                    TokePhotoUtils.PERMISSION_TOKE_PHOTO, TokePhotoUtils.TOKE_PHOTO);
        }
    }

    private void completeData(String name, String companyName, String position) {
        showBookingToast(2);
        final DetailedUserInfoBean bean = new DetailedUserInfoBean();
        bean.setRealname(name);
        bean.setCorporate_name(companyName);
        bean.setPosition(position);
        bean.setHead_pic(headpic);
        bean.setThumb_img(thumbImg);
        RequestManager.getInstance().editUserInfo(token, userId, 0, bean, new AllResultDoCallback() {

            @Override
            public void onSuccess(String s) {
//                startActivity(new Intent(RegisterDownActivity.this,LoginNewActivity.class));
//                    finish();
//                Logger.i("lzq","phone : "+ UserInfoHelper.getIntance().getUserName());
//                Logger.i("lzq","password : "+UserInfoHelper.getIntance().getPassword());
//                login(UserInfoHelper.getIntance().getUserName(),UserInfoHelper.getIntance().getPassword());
//                final String rong_token = UserInfoHelper.getIntance().getRongyunToken();
                //IMUtils.connectIM(rongToken, true, new ResultCallback<String>() {

                    @Override
                    public void onSuccess(String s) {
                        UserInfoHelper.getIntance().setUserId(userId);
                        UserInfoHelper.getIntance().setToken(token);
                        UserInfoHelper.getIntance().setRongyunToken(rongToken);

                        if (JPushInterface.isPushStopped(getApplicationContext())) {
                            Set<String> PushArray = new HashSet<>();
                            int userId = UserInfoHelper.getIntance().getUserId();
                            PushArray.add(userId + "");
                            JPushInterface.resumePush(getApplicationContext());
                            JPushInterface.setAlias(getApplicationContext(), 0, userId + "");
                            JPushInterface.setTags(getApplicationContext(), 0, PushArray);
                        }
                        Intent intent = new Intent(CompleteInfoActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("flag", 1);
                        intent.putExtras(bundle);

                        startActivity(intent);
                        ComUtils.finishshortAll();
                        dismissBookingToast();
                    }

                    @Override
                    public void onFail(int errorCode) {
                        ToastUtils.showCentetToast(CompleteInfoActivity.this, "聊天服务器连接失败");
                        dismissBookingToast();
                    }


                });
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                JSONObject object = new JSONObject();
                try {
                    object.put("userId", userId);
                    object.put("type", 0);
                    object.put("bean", bean);
                    RequestManager.getInstance().saveLog(object);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                ToastUtils.showCentetImgToast(CompleteInfoActivity.this, msg);
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TokePhotoUtils.CAMERA_REQUEST:
                    tokePhotoUtils.cropRawPhoto(CompleteInfoActivity.this, tokePhotoUtils.getImgUri(), TokePhotoUtils.CAMERA_REQUEST);
                    break;
                case TokePhotoUtils.GALLERY_REQUEST:
                    tokePhotoUtils.cropRawPhoto(CompleteInfoActivity.this, data.getData(), TokePhotoUtils.GALLERY_REQUEST);
                    break;
                case TokePhotoUtils.CROP_REQUEST:
                    // updateHead();
                    checkImage();
                    break;
            }
        }
        if (requestCode == 33 && resultCode == 1033) {
            String companyName = data.getStringExtra("data");
            Log.d(TAG, "onActivityResult: " + companyName);
            mcominfoCompanyet.setText(companyName);

        }
    }

    private void checkImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Result result = scanningImage(tokePhotoUtils.getImgFile().getAbsolutePath());
                if (result != null) {
                    ToastUtils.showCentetImgToast(mContext, "头像中不能存在二维码信息");

                } else {
                    mHandler.sendEmptyMessage(0);

                }
            }
        }).start();
    }

    /**
     * 上传头像
     */
    private void updateHead() {

        final Bitmap bm = BitmapUtils.compressScale(tokePhotoUtils.getImgFile().getAbsolutePath());
        String base64bm = BitmapUtils.bitmapToBase64(bm);
        Log.d(TAG, base64bm);
        RequestManager.getInstance().update(base64bm, new RequestCallback<UploadBean>() {
            @Override
            public void requestStart(Call call) {
                mCall = call;
                showBookingToast(2);
            }

            @Override
            public void onSuccess(UploadBean uploadBean) {
                if (!TextUtils.isEmpty(uploadBean.getPath())) {
                    Log.d(TAG, "onSuccess: " + uploadBean.getPath());
//                    userDetailInfoBean.setHead_pic(uploadBean.getPath());
//                    userDetailInfoBean.setThumb_img(uploadBean.getThumb_img());
                    headpic = uploadBean.getPath();
                    thumbImg = uploadBean.getThumb_img();
//                    Picasso.with(BaseApp.getInstance())
//                            .load(uploadBean.getPath())
//                            .skipMemoryCache()
//                            .into(imageHead);
                    bcominfoCircleimg.setImageBitmap(bm);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                Log.d(TAG, "onFailed: " + code);
                ToastUtils.showCentetImgToast(mContext, msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(CompleteInfoActivity.this, perms)) {
            new AppSettingsDialog.Builder(CompleteInfoActivity.this)
                    .setTitle(R.string.need_permission_setting_title)
                    .setRationale(R.string.need_permission_setting_content)
                    .build()
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
    }

    private Bitmap scanBitmap;

    /**
     * 扫描二维码图片的方法
     *
     * @param path
     * @return
     */
    public Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); //设置二维码内容的编码

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }

}
