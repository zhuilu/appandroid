package com.xinniu.android.qiqueqiao.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialog;
//import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatRatingBar;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLShareDialog;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.QRCodeUtil;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by lzq on 2018/1/31.
 */

public class QrCodeActivity extends BaseActivity{

    String title;
    String shareUrl;
    int level;
    String headUrl;
    Bitmap mQRBitmap;
    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 1000;

    @BindView(R.id.image_head)
    CircleImageView circleImageView;
    @BindView(R.id.star_view)
    AppCompatRatingBar appCompatRatingBar;
    @BindView(R.id.tv_title)
    TextView titleTv;
    @BindView(R.id.tv_share)
    TextView shareTv;
    @BindView(R.id.tv_save)
    TextView saveTv;
    @BindView(R.id.image_qr_code)
    ImageView imageQrCodeIv;
    @BindView(R.id.tv_more)
    ImageView moreIv;
    @BindView(R.id.bt_return)
    ImageView returnIv;

    public static void start(Context context,String title,String shareUrl,int level,String headUrl){
        Intent intent = new Intent(context,QrCodeActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("shareUrl",shareUrl);
        intent.putExtra("level",level);
        intent.putExtra("headUrl",headUrl);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_qr;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        title = getIntent().getStringExtra("title");
        shareUrl = getIntent().getStringExtra("shareUrl");
        level = getIntent().getIntExtra("level",3);
        headUrl = getIntent().getStringExtra("headUrl");
        mQRBitmap = QRCodeUtil.createQRCodeBitmap(shareUrl+"/"+ UserInfoHelper.getIntance().getUserId(), 480, 480);
//        circleImageView.setImageBitmap(mBitmap);
        imageQrCodeIv.setImageBitmap(mQRBitmap);
        ImageLoader.loadAvter(headUrl,circleImageView);
        appCompatRatingBar.setRating(level);
        titleTv.setText(title);
        shareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareDialog();
            }
        });
        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });
        moreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShareDialog();
            }
        });
        returnIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化并显示分享dialog
     */
    private void showShareDialog() {
        AppCompatDialog shareDialog = new QLShareDialog.Builder(mContext)
                .setShareCallback(new QLShareDialog.ShareCallback() {
                    @Override
                    public void onClickShare(QLShareDialog.ShareType type) {
                        switch (type) {
                            case SHARE_WEIXIN:
                                shareCircle(SHARE_MEDIA.WEIXIN);
                                break;
                            case SHARE_CIRCLE:
                                shareCircle(SHARE_MEDIA.WEIXIN_CIRCLE);
                                break;
                            case SHARE_QQ:
                                shareCircle(SHARE_MEDIA.QQ);
                                break;
                            case SHARE_WEIBO:
                                shareCircle(SHARE_MEDIA.SINA);
                                break;
                            case SHARE_QRCODE:
                                break;
                        }
                    }
                }).build();
        shareDialog.show();
    }

    private void shareCircle(SHARE_MEDIA share_media) {
//        StringBuilder shareContent = new StringBuilder();
//        shareContent.append(UserInfoHelper.getIntance().getCenterBean().getUsers().getRealname())
//                .append(UserInfoHelper.getIntance().getCenterBean().getUsers().getCompany())
//                .append(UserInfoHelper.getIntance().getCenterBean().getUsers().getPosition())
//                .append("邀请你加入")
//                .append(title)
//
        ShareUtils.shareWebUrl(
                QrCodeActivity.this
                ,headUrl,
                share_media,
                shareUrl+"/"+UserInfoHelper.getIntance().getUserId(),
                "【企鹊桥】"+title,
                "比微信群更高净值，人数更多的合作对接圈子，邀您加入。", new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                });
    }
    /**
     * 获取权限保存图片
     */
    @AfterPermissionGranted(PERMISSION_WRITE_EXTERNAL_STORAGE)
    private void requestPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (mQRBitmap == null) {
                return;
            }
            BitmapUtils.saveImageToGallery(mContext, mQRBitmap);
            ToastUtils.showCentetImgToast(mContext, "保存图片成功");
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_need_save_bitmap),
                    PERMISSION_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }
}
