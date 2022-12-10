package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.ImageBean;
import com.xinniu.android.qiqueqiao.bean.ReleaseServiceSuccessBean;
import com.xinniu.android.qiqueqiao.bean.UploadBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLBottomDialog;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.ReleaseServiceSuccessCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.richtexteditor.RichTextEditor;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.TokePhotoUtils;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

public class AddCaseActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.bt_finish)
    RelativeLayout btFinish;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    private TokePhotoUtils tokePhotoUtils;
    private int tokeType = -1;
    private List<ImageBean> pictureList = new ArrayList<>();
    private List<RichTextEditor.EditData> editList = new ArrayList<>();//原输入的内容，用来替换上传成功的照片
    private String imgUrl = "";
    private List<ImageBean> imgListDetail = new ArrayList<>();
    private int serviceId;

    public static void start(Context context, int serviceId) {
        Intent starter = new Intent(context, AddCaseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("serviceId", serviceId);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_case;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        Bundle bundle = getIntent().getExtras();
        serviceId = bundle.getInt("serviceId");
        tokePhotoUtils = TokePhotoUtils.getInstance(this);
    }

    @OnClick({R.id.bt_finish, R.id.rlayout_img, R.id.rlayout_title, R.id.rlayout_detail, R.id.tv_publich})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.rlayout_img:
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
                        }).show(AddCaseActivity.this);

                break;
            case R.id.rlayout_title:
                InputServiceTitleActivity.startSimpleEidtForResult(this, 30, "案例标题",
                        tvTitle.getText().toString(), "请输入案例标题", 2);
                break;
            case R.id.rlayout_detail:
                ServiceDetailDescriptioActivity.startSimpleEidtForResult(this, 31, tvDetail.getText().toString(), "案例详情", "1");
                break;
            case R.id.tv_publich:
                if (StringUtils.isEmpty(imgUrl)) {
                    showDialogView("请添加主图");
                    return;
                }
                if (TextUtils.isEmpty(tvTitle.getText().toString())) {
                    showDialogView("请填写案例标题");
                    return;
                }

                if (TextUtils.isEmpty(tvDetail.getText().toString())) {
                    showDialogView("请填写案例详情");
                    return;
                }
                showBookingToast(2);
                updateImg();
                break;
        }
    }

    private void updateImg() {
        Luban.with(AddCaseActivity.this).load(imgUrl).ignoreBy(300).setTargetDir(getPath()).filter(new CompressionPredicate() {
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
                        if (pictureList.size() > 0) {
                            //上传详情里面的图片
                            uploadDetailImag(pictureList, uploadBean.getPath(), uploadBean.getThumb_img());
                        } else {
                            //发布,详情里没有图片
                            goToSend(uploadBean.getPath(), uploadBean.getThumb_img());
                        }


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
                ToastUtils.showCentetToast(AddCaseActivity.this, "图片压缩失败，请重新选择图片");
            }
        }).launch();

    }

    private void uploadDetailImag(List<ImageBean> pictureList, final String img_path, final String thumb_img) {
        imgListDetail.clear();
        for (final ImageBean m : pictureList) {

            Luban.with(AddCaseActivity.this).load(m.getName()).ignoreBy(300).setTargetDir(getPath()).filter(new CompressionPredicate() {
                @Override
                public boolean apply(final String path) {


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

                            ImageBean imageBean = new ImageBean(m.getId(), uploadBean.getPath());
                            imgListDetail.add(imageBean);
                            //发布
                            goToSend(img_path, thumb_img);

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
                    ToastUtils.showCentetToast(AddCaseActivity.this, "图片压缩失败，请重新选择图片");
                }
            }).launch();

        }

    }

    String detailNew = "";

    private void goToSend(String path, String thumb_img) {
        if (isUpdateSuccess()) {
            detailNew = getEditData();
            RequestManager.getInstance().sendServiceCase(serviceId, tvTitle.getText().toString(), detailNew, path, thumb_img, new ReleaseServiceSuccessCallback() {
                @Override
                public void onSuccess(ReleaseServiceSuccessBean item) {
                    dismissBookingToast();
                    //发布
//                    Intent intent = new Intent(AddCaseActivity.this, ServiceReleaseSuccessActivity.class);
//                    intent.putExtra("id", item.getId());
//                    intent.putExtra("from", "case");
//                    startActivity(intent);
                    ToastUtils.showCentetImgToast(AddCaseActivity.this, "发布成功");
                    finish();
                }

                @Override
                public void onFailed(int code, String msg) {
                    dismissBookingToast();
                    ToastUtils.showCentetImgToast(AddCaseActivity.this, msg);
                }

            });
        }


    }

    /**
     * 负责处理编辑数据提交等事宜，请自行实现
     */
    private String getEditData() {
        for (ImageBean m : pictureList) {
            for (ImageBean n : imgListDetail) {
                if (m.getId().equals(n.getId())) {
                    //把上传成功的照片地址替换本地地址
                    m.setName(n.getName());
                }

            }


        }
        StringBuffer content = new StringBuffer();
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null) {
                if (itemData.inputStr.contains("\n")) {
                    //所以换行符改成html的换行
                    content.append("<div>").append(itemData.inputStr.replaceAll("\n", "</br>")).append("</div>");
                } else {
                    content.append("<div>").append(itemData.inputStr).append("</div>");

                }
            } else if (itemData.imagePath != null) {
                for (ImageBean m : pictureList) {

                    if (m.getId().equals(itemData.imagePath)) {
                        //把上传成功的照片地址替换本地地址
                        content.append("<img src=\"").append(m.getName()).append("\"/>");
                    }


                }

            }
        }
        return content.toString();
    }

    public boolean isUpdateSuccess() {
        if (imgListDetail.size() == pictureList.size()) {
            return true;
        } else {
            return false;
        }

    }

    public void showDialogView(String msg) {
        AlertDialogUtils.AlertDialog(AddCaseActivity.this, msg, "知道了", "", new AlertDialogUtils.setOnClick() {
            @Override
            public void setLeftOnClick(DialogInterface dialog) {
                dialog.dismiss();
            }

            @Override
            public void setRightOnClick(DialogInterface dialog) {

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
                    tokePhotoUtils.cropRawPhotoTwo(this, tokePhotoUtils.getImgUri(), TokePhotoUtils.CAMERA_REQUEST);
                    break;
                case TokePhotoUtils.GALLERY_REQUEST:

                    tokePhotoUtils.cropRawPhotoTwo(this, data.getData(), TokePhotoUtils.GALLERY_REQUEST);

                    break;
                case TokePhotoUtils.CROP_REQUEST:
                    imgUrl = tokePhotoUtils.getImgFile().getAbsolutePath();
                    final Bitmap bm = BitmapUtils.compressScale(tokePhotoUtils.getImgFile().getAbsolutePath());
                    img.setImageBitmap(bm);
                    imgAdd.setImageResource(R.mipmap.add_case_select);
                    tvAdd.setText("重新选择");
                    tvAdd.setTextColor(getResources().getColor(R.color.white));
                    break;
                case 30:
                    String name = data.getStringExtra("data");
                    tvTitle.setText(name);
                    break;

                case 31://详情
                    String data1 = data.getStringExtra("data");
                    String picture = data.getStringExtra("picture");
                    String originData = data.getStringExtra("originData");
                    Gson gson = new Gson();
                    if (picture != null && picture.length() > 0) {

                        pictureList = gson.fromJson(picture, new TypeToken<List<ImageBean>>() {
                        }.getType());
                    }
                    if (originData != null && originData.length() > 0) {

                        editList = gson.fromJson(originData, new TypeToken<List<RichTextEditor.EditData>>() {
                        }.getType());
                    }

                    Log.d(TAG, "onActivityResult: " + data1 + ",,,,,,,,,," + picture);
                    String text = "";
                    List<String> textList = StringUtils.cutStringByImgTag(data1);
                    for (int i = 0; i < textList.size(); i++) {
                        if (textList.get(i).contains("<img")) {
                            text = text + "[图片]";
                        } else if (textList.get(i).contains("<div>")) {
                            String regMatchEnter = "<div>|</div>";
                            String text01 = textList.get(i).replaceAll(regMatchEnter, "");
                            if (text01.contains("</br>")) {
                                text01 = text01.replaceAll("</br>", "");
                            }
                            text = text + text01;
                        }
                    }
                    tvDetail.setText(text);
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
}
