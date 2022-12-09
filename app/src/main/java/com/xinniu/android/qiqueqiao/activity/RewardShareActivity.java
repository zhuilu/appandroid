package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.RewardDetailBean;
import com.xinniu.android.qiqueqiao.utils.QRCodeUtil;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.WebViewScreenShotUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class RewardShareActivity extends BaseActivity {

    @BindView(R.id.image_head)
    CircleImageView imageHead;
    @BindView(R.id.mimage_isv)
    ImageView mimageIsv;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.mine_vipImg)
    ImageView mineVipImg;
    @BindView(R.id.tv_mine_position)
    TextView tvMinePosition;
    @BindView(R.id.ll_mine_info)
    LinearLayout llMineInfo;
    @BindView(R.id.view_01)
    View view01;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_01)
    ImageView img01;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.img_02)
    ImageView img02;
    @BindView(R.id.tv_01)
    TextView tv01;
    @BindView(R.id.tv_biaoyu_2)
    TextView tvBiaoyu2;
    @BindView(R.id.img_ma)
    ImageView imgMa;
    @BindView(R.id.rlayout_bottom_bg)
    RelativeLayout rlayoutBottomBg;
    @BindView(R.id.llayout_root_view)
    RelativeLayout llayoutRootView;
    @BindView(R.id.bwx_shareTv)
    TextView bwxShareTv;
    @BindView(R.id.bpyq_shareTv)
    TextView bpyqShareTv;
    @BindView(R.id.balc_shareTv)
    TextView balcShareTv;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.mshareWeb)
    ScrollView mshareWeb;
    @BindView(R.id.rlayout_root)
    RelativeLayout rlayoutRoot;
    private RewardDetailBean mBean;

    public static void start(Context mContext, String json) {
        Intent intent = new Intent(mContext, RewardShareActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("data", json);
        intent.putExtras(bundle);
        mContext.startActivity(intent, bundle);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reward_share;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString("data");
        Gson gson = new Gson();
        mBean = gson.fromJson(data, RewardDetailBean.class);
        ShowUtils.showImgPerfect(imageHead, mBean.getHead_pic(), 1);
        ShowUtils.showTextPerfect(tvMinePosition, mBean.getCompany() + mBean.getPosition());
        ShowUtils.showTextPerfect(tvMineName, mBean.getRealname());
        if (mBean.getIs_v() == 1) {
            ShowUtils.showViewVisible(mimageIsv, View.VISIBLE);
        } else {
            ShowUtils.showViewVisible(mimageIsv, View.GONE);
        }
        int isVip = mBean.getIs_vip();
        if (isVip == 0) {
            ShowUtils.showViewVisible(mineVipImg, View.GONE);
            ShowUtils.showTextColor(tvMineName, ContextCompat.getColor(RewardShareActivity.this, R.color._333));

        } else if (isVip == 1) {
            ShowUtils.showBackgroud(mineVipImg, ContextCompat.getDrawable(RewardShareActivity.this, R.mipmap.vip_iconx));
            ShowUtils.showTextColor(tvMineName, ContextCompat.getColor(RewardShareActivity.this, R.color.king_color));

        } else if (isVip == 2) {
            ShowUtils.showBackgroud(mineVipImg, ContextCompat.getDrawable(RewardShareActivity.this, R.mipmap.svip_iconx));
            ShowUtils.showTextColor(tvMineName, ContextCompat.getColor(RewardShareActivity.this, R.color.king_color));

        }

        tvTitle.setText(mBean.getTitle());
        tvContent.setText("要求:" + mBean.getDetail());
        if (mBean.getAmount().contains(".")) {
            String[] pricr01 = mBean.getAmount().split("\\.");
            tvPrice.setText(pricr01[0]);
        } else {
            tvPrice.setText(mBean.getAmount());
        }
        Bitmap mQRBitmap = QRCodeUtil.createQRCodeBitmap(mBean.getShare_url(), 480, 480);
        imgMa.setImageBitmap(mQRBitmap);
    }

    @OnClick({R.id.bwx_shareTv, R.id.bpyq_shareTv, R.id.balc_shareTv, R.id.bfinish_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bwx_shareTv:
                showBookingToast(2);
                Bitmap bitmap = WebViewScreenShotUtils.createViewBitmap(rlayoutRoot);
//                if (bitmap!=null) {
//                    dissMissDialog();
//                }
                ShareUtils.ShareLongImg(RewardShareActivity.this, SHARE_MEDIA.WEIXIN, bitmap, new UMShareListener() {
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
                Bitmap bitmapX = WebViewScreenShotUtils.createViewBitmap(rlayoutRoot);

                ShareUtils.ShareLongImg(RewardShareActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE, bitmapX, new UMShareListener() {
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
                Bitmap bitmapC =WebViewScreenShotUtils.createViewBitmap(rlayoutRoot);
                boolean b = WebViewScreenShotUtils.isS(bitmapC, RewardShareActivity.this);
                if (b) {
                    Toast.makeText(RewardShareActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RewardShareActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bfinish_back:
                finish();
                break;
        }
    }
}
