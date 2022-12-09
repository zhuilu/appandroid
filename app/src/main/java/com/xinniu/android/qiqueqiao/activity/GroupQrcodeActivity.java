package com.xinniu.android.qiqueqiao.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.QRCodeUtil;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by yuchance on 2019/1/17.
 */

public class GroupQrcodeActivity extends BaseActivity {
    @BindView(R.id.bt_finish)
    RelativeLayout btFinish;
    @BindView(R.id.mgroup_headImg)
    ImageView mgroupHeadImg;
    @BindView(R.id.mgroup_nametv)
    TextView mgroupNametv;
    @BindView(R.id.mgroup_qrcodeImg)
    ImageView mgroupQrcodeImg;
    @BindView(R.id.mgroup_qrcodeRl)
    RelativeLayout mgroupQrcodeRl;
    @BindView(R.id.mthescroll)
    ScrollView mthescroll;
    @BindView(R.id.mgroup_imgLl)
    LinearLayout mgroupImgLl;
    private String groupName;
    private String groupQrcode;
    private String groupHead;
    private String shareUrl;

    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 1000;
    private Bitmap mQRBitmap = null;
    private Bitmap bp;


    public static void start(Context context, String groupName, String groupQr, String groupHead, String shareUrl) {
        Intent intent = new Intent(context, GroupQrcodeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("groupName", groupName);
        bundle.putString("groupQr", groupQr);
        bundle.putString("groupHead", groupHead);
        bundle.putString("shareUrl", shareUrl);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_group_qrcode;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            groupName = bundle.getString("groupName");
            groupQrcode = bundle.getString("groupQr");
            groupHead = bundle.getString("groupHead");
            shareUrl = bundle.getString("shareUrl");
        }
        ShowUtils.showTextPerfect(mgroupNametv, groupName);
        mQRBitmap = QRCodeUtil.createQRCodeBitmap(groupQrcode, 480, 480);
        mgroupQrcodeImg.setImageBitmap(mQRBitmap);
        ImageLoader.loadAvter(groupHead, mgroupHeadImg);
        //开启图像缓存
        mgroupImgLl.setDrawingCacheEnabled(true);
        //测量view的大小
        mgroupImgLl.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //发送位置和尺寸到view及其所有的子view
        mgroupImgLl.layout(0, 0, mgroupImgLl.getMeasuredWidth(), mgroupImgLl.getMeasuredHeight());
        bp = mgroupImgLl.getDrawingCache();
        //获得可视组件的截图
//        bp = ImageLoader.getScrollViewBitmap(mthescroll);
    }


    @OnClick({R.id.bshare_wechat, R.id.bshare_circle, R.id.bshare_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bshare_wechat:
                shareCircle(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.bshare_circle:
                shareCircle(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.bshare_photo:
                requestPermission();
                break;
        }
    }

    private void shareCircle(SHARE_MEDIA share_media) {


        ShareUtils.ShareLongImg(
                GroupQrcodeActivity.this,
                share_media,
                bp,
                new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        ToastUtils.showCentetImgToast(GroupQrcodeActivity.this, "分享成功");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                });

    }

    @AfterPermissionGranted(PERMISSION_WRITE_EXTERNAL_STORAGE)
    private void requestPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (bp == null) {
                return;
            }
            BitmapUtils.saveImageToGallery(mContext, bp);
            ToastUtils.showCentetImgToast(mContext, "保存图片成功");
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_need_save_bitmap),
                    PERMISSION_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bp!=null&&!bp.isRecycled()){
            bp.recycle();
        }
        if (mQRBitmap!=null&&!bp.isRecycled()){
            mQRBitmap.recycle();
        }
    }
}
