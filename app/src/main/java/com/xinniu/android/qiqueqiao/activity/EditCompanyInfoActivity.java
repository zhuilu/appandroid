package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.EditResourceBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.bean.UploadBean;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLBottomDialog;
import com.xinniu.android.qiqueqiao.dialog.SelectDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.TokePhotoUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;

/**
 * Created by lzq on 2018/2/28.
 */

public class EditCompanyInfoActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, View.OnClickListener {
    @BindView(R.id.ll_stage_of_development)
    LinearLayout stageOfEevelopmentLL;
    @BindView(R.id.tv_stage_of_development)
    TextView stageOfEevelopmentTv;
    @BindView(R.id.ll_about)
    LinearLayout aboutLL;
    @BindView(R.id.tv_about)
    TextView aboutTv;
    @BindView(R.id.ll_member_number)
    LinearLayout memberNumberLL;
    @BindView(R.id.tv_member_number)
    TextView memberNumberTv;
    @BindView(R.id.ll_url)
    LinearLayout urlLL;
    @BindView(R.id.tv_url)
    TextView urlTv;
    @BindView(R.id.ll_company_info_pic)
    LinearLayout companyInfoPicLL;
    @BindView(R.id.ll_head)
    LinearLayout headLL;
    @BindView(R.id.image_head)
    CircleImageView headIv;
    @BindView(R.id.tv_save)
    TextView saveTv;
    @BindView(R.id.img_number)
    TextView imgNumberTv;
    @BindView(R.id.help_tv)
    TextView helpTv;

    @BindView(R.id.bt_return)
    ImageView bt_return;

    private String logo;
    private String imgBanner;
    private String url;
    private int num;
    private int stageId;
    private String introduce;
    private int tokeType = -1;
    private Call mCall;
    private TokePhotoUtils tokePhotoUtils;
    public final static String TAG_Id = "TAG_Id";
    private int companyId;


    public static void start(Context context, int companyId) {
        Intent starter = new Intent(context, EditCompanyInfoActivity.class);

        starter.putExtra(TAG_Id, companyId);

        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_company_edit_info;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        Intent intent = getIntent();
        companyId = intent.getIntExtra(TAG_Id, -1);
//        url = intent.getStringExtra(TAG_YRL);
//        introduce = intent.getStringExtra(TAG_INTRODUCE);
//
//        imgNumberTv.setText(""+intent.getIntExtra(TAG_PICNUMBER,0));
//        memberNumberTv.setText(""+intent.getIntExtra(TAG_NUMBER,0));
//        urlTv.setText(url);
//        aboutTv.setText(introduce);
//        stageOfEevelopmentTv.setText(intent.getStringExtra(TAG_STAGE));
        tokePhotoUtils = TokePhotoUtils.getInstance(this);
    }

    @OnClick({R.id.ll_stage_of_development, R.id.ll_member_number, R.id.ll_about, R.id.ll_url, R.id.ll_company_info_pic
            , R.id.ll_head, R.id.tv_save, R.id.help_tv, R.id.bt_return})
    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.ll_stage_of_development) {
            SelectDialog selectDialog = new SelectDialog(this, R.style.QLDialog, Constants.TYPE_STATE);
            selectDialog.setOnSelectItemListener(new SelectDialog.OnSelectItemListener() {
                @Override
                public void onSelected(int type, SelectCategory item) {
                    stageId = item.getId();
                    stageOfEevelopmentTv.setText(item.getName());
                }
            });
            selectDialog.show();
        }
        if (id == R.id.ll_member_number) {
            SelectDialog selectDialog = new SelectDialog(this, R.style.QLDialog, Constants.TYPE_MEMBER_NUMBER);
            selectDialog.setOnSelectItemListener(new SelectDialog.OnSelectItemListener() {
                @Override
                public void onSelected(int type, SelectCategory item) {
                    num = item.getId();
                    memberNumberTv.setText(item.getName());
                }
            });
            selectDialog.show();
        }
        if (id == R.id.ll_about) {
            EditCompanyChildActivity.startForResult(this, EditCompanyChildActivity.PARAM_TYPE_INTRODUCE, "");
        }
        if (id == R.id.ll_url) {
            EditCompanyChildActivity.startForResult(this, EditCompanyChildActivity.PARAM_TYPE_URL, "");
        }
        if (id == R.id.ll_company_info_pic) {
            EditCompanyCenterImgActivity.startForResult(this, EditCompanyCenterImgActivity.PARAM_TYPE_CENTER_IMG);
//            startActivity(new Intent(EditCompanyInfoActivity.this,EditCompanyCenterImgActivity.class));
        }
        if (id == R.id.help_tv) {
            startActivity(new Intent(EditCompanyInfoActivity.this, HelpAndFeedbackActivity.class));
        }
        if (id == R.id.ll_head) {
            new QLBottomDialog.Builder(this)
                    .setNormalColor(R.color._999)
                    .setStrOne("拍照")
                    .setStrTwo("从手机相册选择")
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
                    }).show(EditCompanyInfoActivity.this);

        }
        if (id == R.id.tv_save) {
            corporateEdit();
        }
        if (id == R.id.bt_return) {
            finish();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            String info = data.getStringExtra("data");
            if (requestCode == EditCompanyChildActivity.PARAM_TYPE_INTRODUCE) {
                introduce = info;
                aboutTv.setText(StringUtils.hintString(introduce, 11));
            }
            if (requestCode == EditCompanyChildActivity.PARAM_TYPE_URL) {
                if (!TextUtils.isEmpty(info)) {
                    url = info;
                    urlTv.setText(info);
                }
            }
            if (requestCode == EditCompanyCenterImgActivity.PARAM_TYPE_CENTER_IMG) {
                imgBanner = info;
                int number = data.getIntExtra("number", 0);
                imgNumberTv.setText("已选" + number + "张");
            }
            switch (requestCode) {
                case TokePhotoUtils.CAMERA_REQUEST:
                    tokePhotoUtils.cropRawPhoto(this, tokePhotoUtils.getImgUri(), TokePhotoUtils.CAMERA_REQUEST);
                    break;
                case TokePhotoUtils.GALLERY_REQUEST:
                    tokePhotoUtils.cropRawPhoto(this, data.getData(), TokePhotoUtils.GALLERY_REQUEST);
                    break;
                case TokePhotoUtils.CROP_REQUEST:
                    updateHead();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 上传头像
     */
    private void updateHead() {
        Bitmap bm = BitmapUtils.compressScale(tokePhotoUtils.getImgFile().getAbsolutePath());
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
                    logo = uploadBean.getPath();
                    ImageLoader.loadImage(uploadBean.getPath(), headIv);
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

    private void corporateEdit() {
        if (!TextUtils.isEmpty(url)) {
            if (!url.startsWith("http")) {
                url = "http://" + url;
            }
        }
        RequestManager.getInstance().corporateEdit(companyId, logo, imgBanner, url, num, stageId, introduce, new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                ToastUtils.showCentetImgToast(EditCompanyInfoActivity.this, "修改成功");
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(EditCompanyInfoActivity.this, msg);
            }
        });
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
}
