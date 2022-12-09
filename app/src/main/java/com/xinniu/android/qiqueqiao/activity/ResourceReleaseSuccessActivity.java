package com.xinniu.android.qiqueqiao.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
//import android.support.v7.app.AppCompatDialog;
//import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.IndexNewAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLQRCodeDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetRecommendCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.FullyLinearLayoutManager;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by lzq on 2017/12/20.
 */

public class ResourceReleaseSuccessActivity extends BaseActivity implements View.OnClickListener {

    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 1000;
    @BindView(R.id.ypush_successRl)
    RelativeLayout ypushSuccessRl;
    @BindView(R.id.msuccess_title)
    TextView msuccessTitle;
    @BindView(R.id.msuccessRecycler)
    NoScrollRecyclerView msuccessRecycler;
    @BindView(R.id.yaudit_releaseFl)
    RelativeLayout yauditReleaseFl;
    @BindView(R.id.titlex)
    TextView titlex;


    private Bitmap mQRBitmap;//二维码图像
    private String shareUrl;
    private String offer;
    @BindView(R.id.bt_close)
    ImageView closeIv;
    @BindView(R.id.share_item_qr)
    LinearLayout qr;
    @BindView(R.id.share_item_qq)
    LinearLayout qq;
    @BindView(R.id.share_item_wechat)
    LinearLayout wechat;
    @BindView(R.id.share_item_wb)
    LinearLayout wb;
    @BindView(R.id.share_item_friend)
    LinearLayout friend;
    private String isVerify;
    private int resouceId;
    private String from;
    private String wechatUrl;
    private List<IndexNewBean.ListBean> datas = new ArrayList<>();

    private String providerattr;
    private String headUrl;
    private String titleString;
    private String builder;
    private String qrCodeUrl;
    private int p_id;
    private String p_name;

    @Override
    public int getLayoutId() {
        return R.layout.activity_resource_release_success;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("shareUrl"))) {
            shareUrl = getIntent().getStringExtra("shareUrl");
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("offer"))) {
            offer = getIntent().getStringExtra("offer");
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("is_verify"))) {
            isVerify = getIntent().getStringExtra("is_verify");
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("from"))) {
            from = getIntent().getStringExtra("from");
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("query_id"))) {
            providerattr = getIntent().getStringExtra("query_id");
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("wechatUrl"))) {
            wechatUrl = getIntent().getStringExtra("wechatUrl");
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra("qrcode"))) {
            qrCodeUrl = getIntent().getStringExtra("qrcode");
        }
        resouceId = getIntent().getIntExtra("resources_id", 0);
        p_id = getIntent().getIntExtra("p_id", 0);
        p_name = getIntent().getStringExtra("p_name");
        if ("1".equals(isVerify)) {
            yauditReleaseFl.setVisibility(View.VISIBLE);
            ypushSuccessRl.setVisibility(View.GONE);
            buildRecommend();
        } else {
            yauditReleaseFl.setVisibility(View.GONE);

            ypushSuccessRl.setVisibility(View.VISIBLE);
        }
        if ("push".equals(from)) {
            titlex.setText("发布成功");
        } else {
            titlex.setText("修改成功");
        }


    }

    private void buildRecommend() {
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(ResourceReleaseSuccessActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        msuccessRecycler.setLayoutManager(manager);
        final IndexNewAdapter adapter = new IndexNewAdapter(ResourceReleaseSuccessActivity.this, R.layout.item_index_new, datas, 0,1);
        msuccessRecycler.setAdapter(adapter);
        RequestManager.getInstance().getRecommend(providerattr, new GetRecommendCallback() {
            @Override
            public void onSuccess(List<IndexNewBean.ListBean> bean) {
                datas.addAll(bean);
                if (datas.size() == 0) {
                    adapter.removeAllFooterView();
                } else {
                    adapter.setFooterView(footView);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                Log.i("vvvvv3", msg);
                ToastUtils.showCentetToast(ResourceReleaseSuccessActivity.this, msg);
            }
        });


    }

    @OnClick({R.id.editbtn, R.id.bt_close, R.id.share_item_friend, R.id.share_item_wb,
            R.id.share_item_wechat, R.id.share_item_qq, R.id.share_item_qr,
            R.id.bBack_Rl, R.id.bsuccessbtn,
            R.id.bshare_wxtv, R.id.bshare_circletv, R.id.bshare_photolantv})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_close) {
            setResult(MainActivity.RELEASE_SUCCESS, null);
            finish();
        }
        if (id == R.id.share_item_friend) {
            shareCircle(SHARE_MEDIA.WEIXIN_CIRCLE);
        }
        if (id == R.id.share_item_wb) {
            shareCircle(SHARE_MEDIA.SINA);
        }
        if (id == R.id.share_item_wechat) {
            shareCircle(SHARE_MEDIA.WEIXIN);
        }
        if (id == R.id.share_item_qq) {
            shareCircle(SHARE_MEDIA.QQ);
        }
        if (id == R.id.share_item_qr) {
            showQrDialog();
        }
        if (id == R.id.bBack_Rl) {
            finish();
        }
        if (id == R.id.bsuccessbtn) {
            gotoCoop();
        }
        if (id == R.id.editbtn) {
            //编辑
            PublishNewActivity.start(ResourceReleaseSuccessActivity.this, resouceId, p_name, p_id, 1000);
            finish();
        }
        if (id == R.id.bshare_wxtv) {
            shareCircle(SHARE_MEDIA.WEIXIN);
        }
        if (id == R.id.bshare_circletv) {
            shareCircle(SHARE_MEDIA.WEIXIN_CIRCLE);
        }
        if (id == R.id.bshare_photolantv) {
            ShareNewActivity.start(ResourceReleaseSuccessActivity.this, qrCodeUrl, resouceId + "", "coop");
        }
    }

    private void gotoCoop() {
        CoopDetailActivity.start(ResourceReleaseSuccessActivity.this, resouceId);
        ComUtils.finishshortAll();
        finish();
    }

    /**
     * 初始化并显示QRCodeDialog
     */
    private void showQrDialog() {

        AppCompatDialog qrCodeDialog = new QLQRCodeDialog.Builder(mContext)
                .setTitle("【企鹊桥】强烈推荐合作！")//设置标题
                .setImageHead(UserInfoHelper.getIntance().getCenterBean().getUsers().getHead_pic())
                .setRating(3)//设置星级
                .setQRCodeString(shareUrl + "/" + UserInfoHelper.getIntance().getUserId())//设置二维码信息
                .setShareCallback(new QLQRCodeDialog.ShareCallback() {
                    @Override
                    public void onClickShare(Bitmap bitmap) {
                        mQRBitmap = bitmap;
                        requestPermission();
                    }
                }).build();
        qrCodeDialog.show();

//        AppCompatDialog qrCodeDialog = new QLQRCodeDialog.Builder(mContext)
//                .setTitle("分享资源")//设置标题
//                .setImageHead("")
//                .setRating(1)//设置星级
//                .setQRCodeString(shareUrl)//设置二维码信息
//                .setShareCallback(new QLQRCodeDialog.ShareCallback() {
//                    @Override
//                    public void onClickShare(Bitmap bitmap) {
//                        mQRBitmap = bitmap;
//                        requestPermission();
//                    }
//                }).build();
//        qrCodeDialog.show();
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

    private void shareCircle(SHARE_MEDIA share_media) {
        if (share_media == SHARE_MEDIA.WEIXIN) {
            StringBuilder builder = new StringBuilder();
            StringBuilder titleString = new StringBuilder();
            titleString.append(UserInfoHelper.getIntance().getCenterBean().getUsers().getCompany())
                    .append(UserInfoHelper.getIntance().getCenterBean().getUsers().getPosition())
                    .append(UserInfoHelper.getIntance().getCenterBean().getUsers().getRealname())
                    .append("正在企鹊桥寻求合作，快来围观！");
            builder.append("提供")
                    .append(offer)
                    .append("，等您来聊！");
            ShareUtils.shareWxMini(ResourceReleaseSuccessActivity.this, share_media, shareUrl, titleString.toString(), builder.toString(), wechatUrl, new UMShareListener() {
                @Override
                public void onStart(SHARE_MEDIA share_media) {

                }

                @Override
                public void onResult(SHARE_MEDIA share_media) {
                    ToastUtils.showCentetImgToast(ResourceReleaseSuccessActivity.this, "分享成功");
                }

                @Override
                public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                }

                @Override
                public void onCancel(SHARE_MEDIA share_media) {

                }
            });


        } else {
            StringBuilder builder = new StringBuilder();
            StringBuilder titleString = new StringBuilder();
            titleString.append(UserInfoHelper.getIntance().getCenterBean().getUsers().getCompany())
                    .append(UserInfoHelper.getIntance().getCenterBean().getUsers().getPosition())
                    .append(UserInfoHelper.getIntance().getCenterBean().getUsers().getRealname())
                    .append("正在企鹊桥寻求合作，快来围观！");
            builder.append("提供")
                    .append(offer)
                    .append("，等您来聊！");
            ShareUtils.shareWebUrl(
                    ResourceReleaseSuccessActivity.this,
                    UserInfoHelper.getIntance().getCenterBean().getUsers().getHead_pic(),
                    share_media,
                    shareUrl,
                    titleString.toString(),
                    builder.toString(), new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {

                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {
                            ToastUtils.showCentetImgToast(ResourceReleaseSuccessActivity.this, "分享成功");
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {

                        }
                    });
        }
    }

    private void shareCirclex(SHARE_MEDIA share_media) {

        ShareUtils.shareWebUrl(
                this,
                headUrl
                , share_media,
                shareUrl,
                titleString.toString(),
                builder.toString(), new UMShareListener() {
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
//        }
    }


}
