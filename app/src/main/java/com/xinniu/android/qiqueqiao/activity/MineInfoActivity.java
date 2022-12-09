package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.DetailedUserInfoBean;
import com.xinniu.android.qiqueqiao.bean.UploadBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLBottomDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.fragment.edit.PhoneEditFragment;
import com.xinniu.android.qiqueqiao.fragment.edit.SimpleEditFragment;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.TokePhotoUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;
import com.xinniu.android.qiqueqiao.zxing.decoding.RGBLuminanceSource;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;

/**
 * Created by qinlei
 * Created on 2017/12/18
 * Created description :
 */

public class MineInfoActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {


    @BindView(R.id.image_head)
    CircleImageView imageHead;
    @BindView(R.id.mtv_name)
    TextView mtvName;
    @BindView(R.id.mtv_phone)
    TextView mtvPhone;
    @BindView(R.id.mtv_weixin)
    TextView mtvWeixin;
    @BindView(R.id.mtv_company_name)
    TextView mtvCompanyName;
    @BindView(R.id.mtv_position)
    TextView mtvPosition;
    private Call mCall;
    //    private int isJoin;
//    public static final String IS_JOIN = "isJoin";
    private DetailedUserInfoBean userDetailInfoBean;//网络加载的用户详细数据

    private int tokeType = -1;
    private TokePhotoUtils tokePhotoUtils;

    public final static int SELECTED_CITY_SUCCESS = 501;
    private boolean isShowEditDialog = false;
    private List<String> industryList = new ArrayList<>();
    private List<Integer> industryId = new ArrayList<>();
    private OptionsPickerView industryPv;
    private OptionsPickerView sexPv;
    private int option = 0;
    private String headPic;

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

    public static void start(Context context) {
        Intent starter = new Intent(context, MineInfoActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_info;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
//        isJoin = getIntent().getIntExtra(IS_JOIN, -1);

        getUserInfo();
        tokePhotoUtils = TokePhotoUtils.getInstance(this);


    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        RequestManager.getInstance().getUserInfo(new RequestCallback<DetailedUserInfoBean>() {
            @Override
            public void requestStart(Call call) {
                showBookingToast(1);
                mCall = call;
            }

            @Override
            public void onSuccess(DetailedUserInfoBean userDetailInfoBean) {
                MineInfoActivity.this.userDetailInfoBean = userDetailInfoBean;
                setDataToView();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(mContext, msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });
    }

    /**
     * 设置用户信息
     */
    private void setDataToView() {
        if (userDetailInfoBean == null) {
            return;
        }
        if (!TextUtils.isEmpty(userDetailInfoBean.getHead_pic())) {
            headPic = userDetailInfoBean.getHead_pic();
            ImageLoader.loadAvter(headPic, imageHead);
        }
        if (!TextUtils.isEmpty(userDetailInfoBean.getRealname())) {
            mtvName.setText(userDetailInfoBean.getRealname());
        }

        if (!TextUtils.isEmpty(userDetailInfoBean.getWechat())) {
            mtvWeixin.setText(userDetailInfoBean.getWechat());
        }
        if (!TextUtils.isEmpty(userDetailInfoBean.getMobile())) {
            mtvPhone.setText(userDetailInfoBean.getMobile());
        }
//
        if (!TextUtils.isEmpty(userDetailInfoBean.getPosition())) {
            mtvPosition.setText(userDetailInfoBean.getPosition());
        }
        if (!TextUtils.isEmpty(userDetailInfoBean.getCorporate_name())) {
            mtvCompanyName.setText(userDetailInfoBean.getCorporate_name());
        }
    }

    @OnClick({R.id.bll_company_name, R.id.image_head, R.id.bll_name, R.id.bll_weixin, R.id.tv_save, R.id.bll_phone, R.id.bll_position, R.id.bt_finish})
    public void onViewClicked(View view) {


        click(view.getId());

    }

    private void click(int id) {
        switch (id) {
            case R.id.bt_finish:
                if (!userDetailInfoBean.getRealname().equals(mtvName.getText().toString()) || !userDetailInfoBean.getCorporate_name().equals(mtvCompanyName.getText().toString()) || !userDetailInfoBean.getPosition().equals(mtvPosition.getText().toString())) {
                    AlertDialogUtils.AlertDialog(MineInfoActivity.this, "放弃对资料的修改？", "编辑", "放弃", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(DialogInterface dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {
                            finish();
                            dialog.dismiss();
                        }
                    });
                    return;
                }
                finish();


                break;
            case R.id.image_head:
                new QLBottomDialog.Builder(this)
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
                        }).show(MineInfoActivity.this);

                break;
            case R.id.bll_name:
                if (userDetailInfoBean == null) {
                    return;
                }
                EditMineInfoActivity.startSimpleEidtForResult(this, 30, "真实姓名", "请输入真实姓名",
                        userDetailInfoBean.getRealname(), SimpleEditFragment.SIMPLE_EDIT_NAME);
                break;
            case R.id.bll_company_name:
                if (userDetailInfoBean == null) {
                    return;
                }
                Intent in = new Intent(MineInfoActivity.this, CompanyNameInputActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("companyName", userDetailInfoBean.getCorporate_name());
                in.putExtras(bundle);
                startActivityForResult(in, 33, bundle);
                break;
            case R.id.bll_weixin:
                EditMineInfoActivity.startSimpleEidtForResult(this, 31, "请输入微信号", "请输入微信号",
                        userDetailInfoBean.getWechat(), SimpleEditFragment.SIMPLE_EDIT_WEIXIN);
                break;
            case R.id.bll_position:
                if (userDetailInfoBean == null) {
                    return;
                }
                EditMineInfoActivity.startSimpleEidtForResult(this, 34, "职位", "请输入职位",
                        userDetailInfoBean.getPosition(), SimpleEditFragment.SIMPLE_EDIT_POSITION);
                break;
            case R.id.tv_save:

                if (!userDetailInfoBean.getRealname().equals(mtvName.getText().toString())) {
                    if (userDetailInfoBean.getIs_cloud_auth() == 1 && userDetailInfoBean.getIs_v() == 1) {

                        new QLTipDialog.Builder(MineInfoActivity.this)
                                .setCancelable(true)
                                .setCancelableOnTouchOutside(true)
                                .setMessage("您变更了姓名、公司等信息，实名职位认证将被取消，确认修改吗？")
                                .setNegativeButton("取消", new QLTipDialog.INegativeCallback() {
                                    @Override
                                    public void onClick() {

                                    }
                                }).setPositiveButton("确认", new QLTipDialog.IPositiveCallback() {
                            @Override
                            public void onClick() {
                                save();

                            }
                        })
                                .show(MineInfoActivity.this);

                    } else {
                        if (userDetailInfoBean.getIs_cloud_auth() == 1) {

                            new QLTipDialog.Builder(MineInfoActivity.this)
                                    .setCancelable(true)
                                    .setCancelableOnTouchOutside(true)
                                    .setMessage("您变更了姓名、公司等信息，实名认证将被取消，确认修改吗？")
                                    .setNegativeButton("取消", new QLTipDialog.INegativeCallback() {
                                        @Override
                                        public void onClick() {

                                        }
                                    }).setPositiveButton("确认", new QLTipDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {
                                    save();

                                }
                            })
                                    .show(MineInfoActivity.this);
                        } else if (userDetailInfoBean.getIs_v() == 1) {
                            new QLTipDialog.Builder(MineInfoActivity.this)
                                    .setCancelable(true)
                                    .setCancelableOnTouchOutside(true)
                                    .setMessage("您变更了姓名、公司等信息，职位认证将被取消，确认修改吗？")
                                    .setNegativeButton("取消", new QLTipDialog.INegativeCallback() {
                                        @Override
                                        public void onClick() {

                                        }
                                    }).setPositiveButton("确认", new QLTipDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {
                                    save();

                                }
                            })
                                    .show(MineInfoActivity.this);
                        } else {
                            save();
                        }

                    }

                } else {
                    if (userDetailInfoBean.getIs_v() == 1) {
                        if (!userDetailInfoBean.getRealname().equals(mtvName.getText().toString()) || !userDetailInfoBean.getCorporate_name().equals(mtvCompanyName.getText().toString()) || !userDetailInfoBean.getPosition().equals(mtvPosition.getText().toString())) {

                            new QLTipDialog.Builder(MineInfoActivity.this)
                                    .setCancelable(true)
                                    .setCancelableOnTouchOutside(true)
                                    .setMessage("您变更了姓名、公司等信息，职位认证将被取消，确认修改吗？")
                                    .setNegativeButton("取消", new QLTipDialog.INegativeCallback() {
                                        @Override
                                        public void onClick() {

                                        }
                                    }).setPositiveButton("确认", new QLTipDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {
                                    save();

                                }
                            })
                                    .show(MineInfoActivity.this);
                            return;
                        } else {
                            save();
                        }
                        return;
                    }
                    save();

                }


                break;
            case R.id.bll_phone:
                EditMineInfoActivity.startPhoneEdit(this, "更换手机号");
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
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == SELECTED_CITY_SUCCESS) {
//            tvLocation.setText(data.getStringExtra(SelectCityActivity.CITY_NAME));
//            userDetailInfoBean.setCity(data.getIntExtra(SelectCityActivity.CITY_ID, 0));
//        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TokePhotoUtils.CAMERA_REQUEST:
                    tokePhotoUtils.cropRawPhoto(this, tokePhotoUtils.getImgUri(), TokePhotoUtils.CAMERA_REQUEST);
                    break;
                case TokePhotoUtils.GALLERY_REQUEST:
                    tokePhotoUtils.cropRawPhoto(this, data.getData(), TokePhotoUtils.GALLERY_REQUEST);
                    break;
                case TokePhotoUtils.CROP_REQUEST:
                    //  updateHead();
                    checkImage();
                    break;
                case 30://真实姓名
                    String name = data.getStringExtra("data");
                    Log.d(TAG, "onActivityResult: " + name);
                    if (userDetailInfoBean != null) {

                        mtvName.setText(name);
                    }
                    break;
                case 31://微信号
                    String weixin = data.getStringExtra("data");
                    Log.d(TAG, "onActivityResult: " + weixin);
                    if (userDetailInfoBean != null) {
                        userDetailInfoBean.setWechat(weixin);
                        mtvWeixin.setText(weixin);
                    }
                    break;
//                case 32://品牌
//                    String brand = data.getStringExtra("data");
//                    Log.d(TAG, "onActivityResult: " + brand);
//                    if (userDetailInfoBean != null) {
//                        userDetailInfoBean.setCompany(brand);
//                        tvBrand.setText(brand);
//                    }
//                    break;
                case 34://职位
                    String position = data.getStringExtra("data");
                    Log.d(TAG, "onActivityResult: " + position);
                    if (userDetailInfoBean != null) {
                        mtvPosition.setText(position);
                    }
                    break;
            }
        }
        if (requestCode == 33 && resultCode == 1033) {
            String companyName = data.getStringExtra("data");
            Log.d(TAG, "onActivityResult: " + companyName);
            if (userDetailInfoBean != null) {

                mtvCompanyName.setText(companyName);
            }
        }
        if (requestCode == EditMineInfoActivity.EDIT_PHONE && resultCode == PhoneEditFragment.EDIT_PHONE_RESULT) {
            getUserInfo();
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
                    userDetailInfoBean.setHead_pic(uploadBean.getPath());
                    userDetailInfoBean.setThumb_img(uploadBean.getThumb_img());
//                    Picasso.with(BaseApp.getInstance())
//                            .load(uploadBean.getPath())
//                            .skipMemoryCache()
//                            .into(imageHead);
                    imageHead.setImageBitmap(bm);
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

    private void save() {
        if (userDetailInfoBean == null) {
            return;
        }
        userDetailInfoBean.setRealname(mtvName.getText().toString());
        userDetailInfoBean.setCorporate_name(mtvCompanyName.getText().toString());
        userDetailInfoBean.setPosition(mtvPosition.getText().toString());
        RequestManager.getInstance().editUserInfo(1, userDetailInfoBean, new RequestCallback<String>() {
            @Override
            public void requestStart(Call call) {
                mCall = call;
                showBookingToast(2);
            }

            @Override
            public void onSuccess(String s) {
                ToastUtils.showCentetImgToast(mContext, "保存成功");
                UserInfoHelper.getIntance().setHeadUrl(userDetailInfoBean.getHead_pic());
                UserInfoHelper.getIntance().setInfoRealname(userDetailInfoBean.getRealname());
                UserInfoHelper.getIntance().setInfoPosition(userDetailInfoBean.getPosition());
                UserInfoHelper.getIntance().setInfoNickName(userDetailInfoBean.getRealname());
                UserInfoHelper.getIntance().setInfoCompany(userDetailInfoBean.getCorporate_name());
                finish();

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(mContext, msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
    }

//    private void showEditInfoDialog(final int id) {
//        if (userDetailInfoBean == null) {
//            return;
//        }
//        if (isJoin == 1 && userDetailInfoBean.getIs_v() == 1) {
//            if (id == R.id.bll_company_name) {
//                new QLTipDialog.Builder(MineInfoActivity.this)
//                        .setCancelable(false)
//                        .setCancelableOnTouchOutside(false)
//                        .setMessage("此项信息认证后不能修改，如若修改请联系专属客户")
//                        .setPositiveButton("去联系", new QLTipDialog.IPositiveCallback() {
//                            @Override
//                            public void onClick() {
//                                //IMUtils.singleChat(MineInfoActivity.this, String.valueOf(UserInfoHelper.getIntance().getCenterBean().getUsers().getF_id()), "客服", "3", "");
//                            }
//                        })
//                        .setNegativeButton("取消", new QLTipDialog.INegativeCallback() {
//                            @Override
//                            public void onClick() {
//                            }
//                        })
//                        .show();
//                return;
//            }
//        }
//        if (isShowEditDialog) {
//            click(id);
//            return;
//        }
//        isShowEditDialog = true;
//        int isV = userDetailInfoBean.getIs_v();
//        String content = "请您认真填写，填写后不能随意修改";
//        if (isV == Constants.IS_ADD_V && (id == R.id.ll_head || id == R.id.bll_name || id == R.id.bll_weixin || id == R.id.bll_position)) {
//            content = "您变更了姓名、职位等信息，认证权益将被取消，需重新认证，确认要修改吗？";
//        }
//
//        new QLTipDialog.Builder(MineInfoActivity.this)
//                .setCancelable(true)
//                .setCancelableOnTouchOutside(true)
//                .setMessage(content)
//                .setPositiveButton("确认", new QLTipDialog.IPositiveCallback() {
//                    @Override
//                    public void onClick() {
//                        click(id);
//                    }
//                })
//                .show();
//    }

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

}
