package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.QRCodeUtil;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.WebViewScreenShotUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class ShareClassRoomActivity extends BaseActivity {
    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_biaoyu_2)
    TextView tvBiaoyu2;
    @BindView(R.id.img_ma)
    ImageView imgMa;
    @BindView(R.id.rlayout_bottom_bg)
    RelativeLayout rlayoutBottomBg;
    @BindView(R.id.llayout_root_view)
    LinearLayout llayoutRootView;
    @BindView(R.id.bwx_shareTv)
    TextView bwxShareTv;
    @BindView(R.id.bpyq_shareTv)
    TextView bpyqShareTv;
    @BindView(R.id.balc_shareTv)
    TextView balcShareTv;
    @BindView(R.id.bfinish_back)
    ImageView bfinishBack;
    @BindView(R.id.rlayout_user)
    RelativeLayout rlayoutUser;

    public static void start(Context context, String img, String title, int user_id, String head, String name, String position, String ma) {
        Intent intent = new Intent(context, ShareClassRoomActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("img", img);
        bundle.putString("title", title);
        bundle.putInt("user_id", user_id);
        bundle.putString("head", head);
        bundle.putString("name", name);
        bundle.putString("position", position);
        bundle.putString("ma", ma);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_classroom_share;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        Bundle bundle = getIntent().getExtras();
        String ma = bundle.getString("ma");
        String img = bundle.getString("img");
        String title = bundle.getString("title");
        int user_id = bundle.getInt("user_id");
        if (user_id == 0) {
            rlayoutUser.setVisibility(View.GONE);
        } else {
            rlayoutUser.setVisibility(View.VISIBLE);
            String head = bundle.getString("head");
            String name = bundle.getString("name");
            String position = bundle.getString("position");
            ImageLoader.loadAvter(head, imgHead);
            tvName.setText(name);
            tvPosition.setText(position);
        }
        ImageLoader.loadLocalImg(img,imgBg);
        Bitmap mQRBitmap = QRCodeUtil.createQRCodeBitmap(ma, 480, 480);
        imgMa.setImageBitmap(mQRBitmap);
        tvTitle.setText(title);
    }

    @OnClick({R.id.bwx_shareTv, R.id.bpyq_shareTv, R.id.balc_shareTv, R.id.bfinish_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bwx_shareTv:
                showBookingToast(2);
                Bitmap bitmap = WebViewScreenShotUtils.getViewBitmap(llayoutRootView);
//                if (bitmap!=null) {
//                    dissMissDialog();
//                }
                ShareUtils.ShareLongImg(ShareClassRoomActivity.this, SHARE_MEDIA.WEIXIN, bitmap, new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        dismissBookingToast();

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        dismissBookingToast();

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                    }
                });
                break;
            case R.id.bpyq_shareTv:
                showBookingToast(2);
                Bitmap bitmapX = WebViewScreenShotUtils.getViewBitmap(llayoutRootView);
//                if (bitmap!=null) {
//                    dissMissDialog();
//                }
                ShareUtils.ShareLongImg(ShareClassRoomActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE, bitmapX, new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        dismissBookingToast();

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        dismissBookingToast();

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        dismissBookingToast();
                    }
                });
                break;
            case R.id.balc_shareTv:
                Bitmap bitmapC = WebViewScreenShotUtils.getViewBitmap(llayoutRootView);
                boolean b = WebViewScreenShotUtils.isS(bitmapC, ShareClassRoomActivity.this);
                if (b) {
                    Toast.makeText(ShareClassRoomActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShareClassRoomActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bfinish_back:
                finish();
                break;
        }
    }
}
