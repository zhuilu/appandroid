package com.xinniu.android.qiqueqiao.activity;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.MemberInfoBean;
import com.xinniu.android.qiqueqiao.bean.UnReadBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLQRCodeDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLShareDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetGroupMemberCallback;
import com.xinniu.android.qiqueqiao.request.callback.UnReadCircleCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by qinlei
 * Created on 2017/12/12
 * Created description :
 */

public class ConversationActivity extends BaseActivity {
    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 1000;
    private static final String TARAGETIDS = "targetIds";
    private static final String TITLE = "title";
    private static final String HEAD = "HEAD";
    private static final String SHARE_URL = "SHARE_URL";
    private static final String GROUP_NUM = "GROUP_NUM";
    @BindView(R.id.mgroup_num)
    TextView mgroupNum;

    private String targetIds;
    private String title;
    private String headUrl;


    private String shareUrl;

    @BindView(R.id.tb_member_red)
    ImageView tbMemberRedIv;
    @BindView(R.id.tb_return)
    ImageView mTbReturn;
    @BindView(R.id.tb_title)
    TextView mTbTitle;
    @BindView(R.id.tb_member)
    ImageView mMember;


//    private AutoRefreshListView autoRefreshListView;//?????????listview

    private AppCompatDialog shareDialog;
    private AppCompatDialog qrCodeDialog;
    private AppCompatDialog joinDialog;
    private Bitmap mQRBitmap;//???????????????
    int groupId;
    private int groupNum;

    public static void start(Context context,
                             long circleId, String title, int groupNum, String headUrl,
                             String shreUrl) {
//        Intent starter = new Intent(context, ConversationActivity.class);
//        starter.putExtra(TARAGETIDS, String.valueOf(circleId));
//        starter.putExtra(TITLE, title);
//        starter.putExtra(HEAD, headUrl);
//        starter.putExtra(GROUP_NUM, groupNum);
//        starter.putExtra(SHARE_URL, shreUrl);
//        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_circle_detail;
    }

    private void getGroupMember(int circleId) {
        RequestManager.getInstance().getGroupMember(circleId, new GetGroupMemberCallback() {
            @Override
            public void onSuccess(List<MemberInfoBean> list) {
            }

            @Override
            public void onFailed(int code, String msg) {
            }
        });
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ComUtils.addActivity(this);
//        RongIM.getInstance().setGroupMembersProvider(new RongIM.IGroupMembersProvider() {
//            @Override
//            public void getGroupMembers(String groupId, final RongIM.IGroupMemberCallback callback) {
//                RequestManager.getInstance().getGroupMember(Integer.valueOf(targetIds), new GetGroupMemberCallback() {
//                    @Override
//                    public void onSuccess(List<MemberInfoBean> list) {
//                        List<UserInfo> mList = new ArrayList<>();
//                        for (MemberInfoBean item : list) {
//                            if (!TextUtils.isEmpty(item.getHead_pic()) && !TextUtils.isEmpty(item.getRealname())) {
//                                UserInfo userInfo = new UserInfo(String.valueOf(item.getUser_id()), item.getRealname(), Uri.parse(item.getHead_pic()));
//                                mList.add(userInfo);
//                            }
//                        }
//                        callback.onGetGroupMembersResult(mList); // ?????? callback ??? onGetGroupMembersResult ??????????????????
//                    }
//
//                    @Override
//                    public void onFailed(int code, String msg) {
//                    }
//                });
//            }
//        });
//        IMUtils.registerMessageType(new ServiceMessageProvider());
//        IMUtils.registerMessageType(new HeadMessageProvider());
//        IMUtils.registerMessageType(new SecretPhoneNumProvider());
//
//        getIntentData();
//        initTitleBar();
////        unReadCircle();
//
//        TestConversationFragment fragment = new TestConversationFragment();
//        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
//                .appendPath("conversation").appendPath(Conversation.ConversationType.GROUP.getName())
//                .appendQueryParameter("targetId", targetIds).build();
//
//        fragment.setUri(uri);
//        /* ?????? ConversationFragment */
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.rong_content, fragment);
//        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitleEffect();//??????titleBar???????????????
        isVipIdentity();
//        isJoin();
        unReadCircle();
    }

    /**
     * ??? Intent ???????????????
     */
    private void getIntentData() {
        targetIds = getIntent().getStringExtra(TARAGETIDS);
        title = getIntent().getStringExtra(TITLE);
        headUrl = getIntent().getStringExtra(HEAD);
        shareUrl = getIntent().getStringExtra(SHARE_URL);
        groupNum = getIntent().getIntExtra(GROUP_NUM, 0);
    }

    /**
     * ?????????????????????
     */
    private void initTitleBar() {
        mTbReturn.setImageResource(R.mipmap.chevron);
        mTbReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mTbTitle.setText(StringUtils.hintString(title, 11));
        mgroupNum.setText(groupNum + "???");

        mMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showCentetImgToast(ConversationActivity.this,"member");
//                CircleInfoActivity.start(ConversationActivity.this,Integer.valueOf(targetIds),title,shareUrl);
                groupId = Integer.parseInt(targetIds);
                GroupMessagemActivity.start(ConversationActivity.this, groupId);
            }
        });


    }

    private boolean isVip() {
        int vip = UserInfoHelper.getIntance().getCenterBean().getUsers().getIs_vip();
        if (vip == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ???????????????????????????
     */
    private void isVipIdentity() {

    }


    /**
     * ????????????????????????
     */
    @AfterPermissionGranted(PERMISSION_WRITE_EXTERNAL_STORAGE)
    private void requestPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (mQRBitmap == null) {
                return;
            }
            BitmapUtils.saveImageToGallery(mContext, mQRBitmap);
            ToastUtils.showCentetImgToast(mContext, "??????????????????");
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_need_save_bitmap),
                    PERMISSION_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * ????????????????????????dialog
     */
    private void showShareDialog() {
        if (shareDialog != null) {
            shareDialog.show();
            return;
        }
        shareDialog = new QLShareDialog.Builder(mContext)
                .setWithQRCode(1)//?????????????????????????????????
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
                                showQrDialog();
                                break;
                        }
                    }
                }).build();
        shareDialog.show();
    }


    /**
     * ??????????????????QRCodeDialog
     */
    private void showQrDialog() {
        if (qrCodeDialog != null) {
            qrCodeDialog.show();
            return;
        }
        qrCodeDialog = new QLQRCodeDialog.Builder(mContext)
                .setTitle("???????????????" + title)//????????????
                .setImageHead(headUrl)
                .setQRCodeString(shareUrl + "/" + UserInfoHelper.getIntance().getUserId())//?????????????????????
                .setShareCallback(new QLQRCodeDialog.ShareCallback() {
                    @Override
                    public void onClickShare(Bitmap bitmap) {
                        mQRBitmap = bitmap;
                        requestPermission();
                    }
                }).build();
        qrCodeDialog.show();
    }

    /**
     * ??????titlebar???????????????
     */
    private void setTitleEffect() {
//        @SuppressLint("RestrictedApi") TestConversationFragment conversationFragment = ((TestConversationFragment) getSupportFragmentManager().getFragments().get(0));
//        autoRefreshListView = (AutoRefreshListView) ReflectUtils.getFieldValue(conversationFragment, "autoRefreshListView");
//        if (autoRefreshListView != null) {
//            final boolean[] focus = {false};
//            autoRefreshListView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View view, boolean b) {
//                    focus[0] = b;
//                }
//            });
//            autoRefreshListView.setClipToPadding(false);
//            autoRefreshListView.addOnScrollListener(new AbsListView.OnScrollListener() {
//                private int mLastFirstVisibleItem;
//                private int mTotalItemCount;
//
//                @Override
//                public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//                }
//
//                @Override
//                public void onScroll(AbsListView view, int firstVisibleItem,
//                                     int visibleItemCount, int totalItemCount) {
//                    if (mTotalItemCount == 0) {
//                        mTotalItemCount = totalItemCount;
//                    }
//                    if (mTotalItemCount == totalItemCount) {
//                        if (mLastFirstVisibleItem < firstVisibleItem && focus[0]) {
//                            Log.i("SCROLLING DOWN", "TRUE");
//
//                        }
//                        if (mLastFirstVisibleItem > firstVisibleItem && focus[0]) {
//                            Log.i("SCROLLING UP", "TRUE");
//
//                        }
//                        mLastFirstVisibleItem = firstVisibleItem;
//                    } else {
//                        mLastFirstVisibleItem = firstVisibleItem;
//                        mTotalItemCount = totalItemCount;
//                    }
//                }
//            });
//        }
    }

    private void shareCircle(SHARE_MEDIA share_media) {

        if (headUrl == null) {
            return;
        }
        ShareUtils.shareWebUrl(
                ConversationActivity.this
                , headUrl,
                share_media,
                shareUrl + "/" + UserInfoHelper.getIntance().getUserId(),
                "???????????????" + title,
                "??????????????????????????????????????????????????????????????????????????????", new UMShareListener() {
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

    private void unReadCircle() {
        RequestManager.getInstance().unReadCircle(Integer.valueOf(targetIds), new UnReadCircleCallback() {

            @Override
            public void onSuccess(UnReadBean item) {
                if (item.getStatus() == 1) {
                    tbMemberRedIv.setVisibility(View.VISIBLE);
                }
                if (item.getStatus() == 0) {
                    tbMemberRedIv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailue(int code, String msg) {
                if (tbMemberRedIv != null) {
                    tbMemberRedIv.setVisibility(View.GONE);
                }

            }
        });
    }

}
