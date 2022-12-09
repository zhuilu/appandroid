package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
//import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.UploadBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLBottomDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
import com.xinniu.android.qiqueqiao.utils.GetPathFromUri;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.TokePhotoUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

public class PayDespositActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.check_01)
    CheckBox check01;
    @BindView(R.id.check_02)
    CheckBox check02;
    @BindView(R.id.tv_account_name)
    TextView tvAccountName;
    @BindView(R.id.tv_account_number)
    TextView tvAccountNumber;
    @BindView(R.id.tv_copy_02)
    TextView tvCopy02;
    @BindView(R.id.tv_opening_bank)
    TextView tvOpeningBank;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.rlayout_img)
    RelativeLayout rlayoutImg;
    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    @BindView(R.id.llayout_add)
    LinearLayout llayoutAdd;
    @BindView(R.id.llayout_dui)
    LinearLayout llayoutDui;
    @BindView(R.id.tv_011)
    TextView tv011;
    @BindView(R.id.tv_account_name1)
    TextView tvAccountName1;
    @BindView(R.id.tv_copy_011)
    TextView tvCopy011;
    @BindView(R.id.tv_033)
    TextView tv033;
    @BindView(R.id.tv_zhifubao)
    TextView tvZhifubao;
    @BindView(R.id.tv_copy_033)
    TextView tvCopy033;
    @BindView(R.id.llayout_zhifubao)
    LinearLayout llayoutZhifubao;
    private int mId;
    private int mType = 1;//1：公账，2：支付宝

    private TokePhotoUtils tokePhotoUtils;
    private int tokeType = -1;
    private String imgUrl = "";


    public static void startSimpleEidtForResult(Activity context, int id, String price, int requestCode) {
        Intent intent = new Intent(context, PayDespositActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("price", price);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_deposit;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Intent intent = getIntent();
        mId = intent.getIntExtra("id", -1);
        String price = intent.getStringExtra("price");
        if (price.contains(".")) {
            String[] pricr01 = price.split("\\.");
            tvPrice.setText(pricr01[0]);
        } else {
            tvPrice.setText(price);
        }


        tokePhotoUtils = TokePhotoUtils.getInstance(this);
    }

    @OnClick({R.id.bt_return, R.id.rlayout_01, R.id.rlayout_02, R.id.tv_copy_01, R.id.tv_copy_02, R.id.tv_copy_03, R.id.img_delete, R.id.rlayout_img, R.id.btn_submit, R.id.tv_copy_011, R.id.tv_copy_033})
    public void onClick(View view) {
        //获取剪贴板管理器
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        switch (view.getId()) {
            case R.id.bt_return:
                finish();
                break;
            case R.id.rlayout_01:
                mType = 1;
                check01.setChecked(true);
                check02.setChecked(false);
                llayoutDui.setVisibility(View.VISIBLE);
                llayoutZhifubao.setVisibility(View.GONE);
                break;
            case R.id.rlayout_02:
                mType = 2;
                check01.setChecked(false);
                check02.setChecked(true);
                llayoutDui.setVisibility(View.GONE);
                llayoutZhifubao.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_copy_01:

                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText(null, tvAccountName.getText().toString());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ToastUtils.showCentetToast(mContext, "复制成功");

                break;
            case R.id.tv_copy_02:
                // 创建普通字符型ClipData
                ClipData mClipData01 = ClipData.newPlainText(null, tvAccountNumber.getText().toString());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData01);
                ToastUtils.showCentetToast(mContext, "复制成功");
                break;
            case R.id.tv_copy_03:
                // 创建普通字符型ClipData
                ClipData mClipData02 = ClipData.newPlainText(null, tvOpeningBank.getText().toString());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData02);
                ToastUtils.showCentetToast(mContext, "复制成功");
                break;
            case R.id.img_delete:
                //删除图片
                img.setImageBitmap(null);
                llayoutAdd.setVisibility(View.VISIBLE);
                imgDelete.setVisibility(View.GONE);
                break;
            case R.id.rlayout_img:
                //添加图片
//                new QLBottomDialog.Builder(this)
//                        .setNormalColor(R.color._999)
//                        .setStrOne("拍照")
//                        .setStrTwo("从手机相册选择")
//                        .setStrCancel("取消")
//                        .setBottomDialogItemCallback(new QLBottomDialog.BottomDialogItemCallback() {
//                            @Override
//                            public void onClick(int position) {
//                                switch (position) {
//                                    case 0:
//                                        tokeType = 0;
//                                        break;
//                                    case 1:
//                                        tokeType = 1;
//                                        break;
//                                }
                tokeType = 1;
                requestPermission();
//                            }
//                        }).show();
                break;
            case R.id.btn_submit:
                if (StringUtils.isEmpty(imgUrl)) {
                    ToastUtils.showCentetToast(mContext, "请添加转账截图");
                    return;
                }
                showBookingToast(2);
                updateImg();
                break;
            case R.id.tv_copy_011:
                // 创建普通字符型ClipData
                ClipData mClipData4 = ClipData.newPlainText(null, tvAccountName1.getText().toString());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData4);
                ToastUtils.showCentetToast(mContext, "复制成功");
                break;


            case R.id.tv_copy_033:
                // 创建普通字符型ClipData
                ClipData mClipData05 = ClipData.newPlainText(null, tvZhifubao.getText().toString());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData05);
                ToastUtils.showCentetToast(mContext, "复制成功");
                break;
        }
    }

    private void updateImg() {
        Luban.with(PayDespositActivity.this).load(imgUrl).ignoreBy(300).setTargetDir(getPath()).filter(new CompressionPredicate() {
            @Override
            public boolean apply(String path) {


                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
            }
        }).setRenameListener(new OnRenameListener() {
            @Override
            public String rename(String filePath) {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(filePath.getBytes());
                    return new BigInteger(1, md.digest()).toString(32);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                return "";
            }
        }).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(File file) {
                RequestManager.getInstance().updateBase64(file.getAbsolutePath(), new RequestCallback<UploadBean>() {
                    @Override
                    public void requestStart(Call call) {

                    }

                    @Override
                    public void onSuccess(UploadBean uploadBean) {

                        pay(uploadBean.getPath(), uploadBean.getThumb_img());


                    }

                    @Override
                    public void onFailed(int code, String msg) {

                    }

                    @Override
                    public void requestEnd() {

                    }
                });
            }

            @Override
            public void onError(Throwable e) {
                dismissBookingToast();
                ToastUtils.showCentetToast(PayDespositActivity.this, "图片压缩失败，请重新选择图片");
            }
        }).launch();

    }

    private void pay(String path, String thumb_img) {
        RequestManager.getInstance().payGuarantee(mId, mType, path, 1, "0", new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(PayDespositActivity.this, msg);
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(PayDespositActivity.this, msg);

            }
        });
    }

    /**
     * 获取权限保存图片
     */
    @AfterPermissionGranted(TokePhotoUtils.PERMISSION_TOKE_PHOTO)
    public void requestPermission() {
        if (EasyPermissions.hasPermissions(this, TokePhotoUtils.TOKE_PHOTO)) {
            switch (tokeType) {
                case 0://拍照
                    tokePhotoUtils.tokePhoto(this);
                    break;
                case 1://图册
                    tokePhotoUtils.chooseGallary(this);
                    break;
            }
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.permission_need_toke_photo),
                    TokePhotoUtils.PERMISSION_TOKE_PHOTO, TokePhotoUtils.TOKE_PHOTO);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
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
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TokePhotoUtils.CAMERA_REQUEST:
                    imgUrl = tokePhotoUtils.getImgFile().getAbsolutePath();
                    Bitmap bm3 = BitmapUtils.compressScale(imgUrl);
                    img.setImageBitmap(bm3);
                    llayoutAdd.setVisibility(View.GONE);
                    imgDelete.setVisibility(View.VISIBLE);
                    break;
                case TokePhotoUtils.GALLERY_REQUEST:

                    Uri photoUri0 = data.getData();
                    imgUrl = GetPathFromUri.getPathFromUri(mContext, photoUri0);
                    //显示照片
                    final Bitmap bm2 = BitmapUtils.compressScale(imgUrl);
                    img.setImageBitmap(bm2);
                    llayoutAdd.setVisibility(View.GONE);
                    imgDelete.setVisibility(View.VISIBLE);
                    break;


            }

        }
    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    protected Bitmap decodeFile(Uri uri) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, o);

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 1280;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp < REQUIRED_SIZE
                        || height_tmp < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;

            return BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

}
