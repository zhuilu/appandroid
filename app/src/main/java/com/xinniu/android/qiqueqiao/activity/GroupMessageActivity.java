package com.xinniu.android.qiqueqiao.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.app.AppCompatDialog;
//import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.GroupMessageMemberAdapter;
import com.xinniu.android.qiqueqiao.adapter.RecommendGroupAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.GroupBean;
import com.xinniu.android.qiqueqiao.bean.GroupInfoBean;
import com.xinniu.android.qiqueqiao.bean.SimilarGroupBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLQRCodeDialog;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLShareDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetGroupInfoCallback;
import com.xinniu.android.qiqueqiao.request.callback.SimilarGroupCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 群组资料
 * Created by yuchance on 2018/10/9.
 */

public class GroupMessageActivity extends BaseActivity {

    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 1000;

    @BindView(R.id.mgroupNameTv)
    TextView mgroupNameTv;
    @BindView(R.id.mgroupCodeTv)
    TextView mgroupCodeTv;
    @BindView(R.id.mgroup_typeTv)
    TextView mgroupTypeTv;
    @BindView(R.id.mgroup_cityTv)
    TextView mgroupCityTv;
    @BindView(R.id.mgroupnoticeTv)
    TextView mgroupnoticeTv;
    @BindView(R.id.bmember_num)
    TextView bmemberNum;
    @BindView(R.id.mgroupmemberRecycler)
    NoScrollRecyclerView mgroupmemberRecycler;
    @BindView(R.id.mgrouprecycler)
    NoScrollRecyclerView mgrouprecycler;
    @BindView(R.id.mcoopnumtv)
    TextView mcoopnumtv;
    @BindView(R.id.msendTv)
    TextView msendTv;
    private long groupId;
    private List<GroupInfoBean.UserListBean> datas = new ArrayList<>();
    private GroupMessageMemberAdapter adapter;
    private List<SimilarGroupBean> groupList = new ArrayList<>();
    private RecommendGroupAdapter groupAdapter;
    private String groupName;
    private int identity;
    private String from;
    private int type;
    private String shareUrl;
    private String headUrl;
    private int groupNum;

    private AppCompatDialog qrCodeDialog;
    private AppCompatDialog shareDialog;
    private Bitmap mQRBitmap;
    private int userIdentity;
    private String groupNumber;

    public static void start(Context context, long groupId, String from) {
        Intent intent = new Intent(context, GroupMessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("groupId", groupId);
        bundle.putString("from", from);
        intent.putExtras(bundle);
        context.startActivity(intent);


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_group_message;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        ComUtils.addActivity(this);
        Intent intent = getIntent();
        Uri uridata = intent.getData();
        try {
            if (uridata != null) {
                if (!TextUtils.isEmpty(uridata.getQueryParameter("group_id"))) {
                    groupId = Long.parseLong(uridata.getQueryParameter("group_id"));
                }
            }
        } catch (NumberFormatException e) {

        }
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            if (bundle.getLong("groupId") != 0) {
                groupId = bundle.getLong("groupId");
            }
        }
        msendTv.setSelected(true);
        LinearLayoutManager manager = new LinearLayoutManager(GroupMessageActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mgroupmemberRecycler.setLayoutManager(manager);
        adapter = new GroupMessageMemberAdapter(GroupMessageActivity.this, datas);
        mgroupmemberRecycler.setAdapter(adapter);
        adapter.setSetOnClick(new GroupMessageMemberAdapter.setOnClick() {
            @Override
            public void setOnClick() {
                FriendLxActivity.startx(GroupMessageActivity.this, FriendLxActivity.INVITEGROUP, (int) groupId);

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(GroupMessageActivity.this, LinearLayoutManager.VERTICAL, false);
        mgrouprecycler.setLayoutManager(layoutManager);
        groupAdapter = new RecommendGroupAdapter(R.layout.item_recommend_group, groupList);
        mgrouprecycler.setAdapter(groupAdapter);
        groupAdapter.setGoToGroup(new RecommendGroupAdapter.goToGroup() {
            @Override
            public void goToGroup(long groupId) {
                GroupMessageActivity.start(GroupMessageActivity.this, groupId, "add");
            }
        });
        loadDatas();

    }

    private void loadSimilar(int category) {
        RequestManager.getInstance().getSimilarGroup(category, new SimilarGroupCallback() {
            @Override
            public void onSuccess(List<SimilarGroupBean> bean) {

                groupList.addAll(bean);
                groupAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailue(int code, String msg) {

            }
        });
    }

    private void loadDatas() {
        showBookingToast(1);
        RequestManager.getInstance().getGroupInformation(groupId, new GetGroupInfoCallback() {
            @Override
            public void onSuccess(GroupInfoBean bean) {
                dismissBookingToast();
                groupName = bean.getName();
                identity = bean.getUser_identity();
                type = bean.getIs_verify();
                shareUrl = bean.getShare_url();
                headUrl = bean.getHead_pic();
                groupNum = bean.getNum();
                groupNumber = bean.getGroup_number();
                ShowUtils.showTextPerfect(mgroupNameTv, bean.getName());
                ShowUtils.showTextPerfect(mgroupCityTv, bean.getCity_name());
                ShowUtils.showTextPerfect(mgroupCodeTv, "群号: " + bean.getGroup_number());
                ShowUtils.showTextPerfect(mgroupTypeTv, bean.getCategory_name());
                ShowUtils.showTextPerfect(mgroupnoticeTv, bean.getIntroduction());
                ShowUtils.showTextPerfect(bmemberNum, bean.getNum() + "人");
                ShowUtils.showTextPerfect(mcoopnumtv, bean.getResources_count() + "条");
                userIdentity = bean.getUser_identity();
                if (userIdentity == 3) {
                    from = "add";
                    msendTv.setText("加入群组");
                } else {
                    from = "mygroup";
                    msendTv.setText("发送信息");
                }
                loadSimilar(bean.getCategory());
                datas.clear();
                if (bean.getUserList().size() < 4) {
                    for (int i = 0; i < bean.getUserList().size(); i++) {
                        bean.getUserList().get(i).setItemType(GroupInfoBean.UserListBean.COMMON);
                    }
                } else {
                    bean.getUserList().remove(bean.getUserList().size() - 1);
                    for (int i = 0; i < bean.getUserList().size(); i++) {
                        bean.getUserList().get(i).setItemType(GroupInfoBean.UserListBean.COMMON);
                    }
                }
                if (userIdentity != 3) {
                    bean.getUserList().add(new GroupInfoBean.UserListBean(GroupInfoBean.UserListBean.INVITE));
                }
                datas.addAll(bean.getUserList());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(GroupMessageActivity.this, msg);
            }
        });


    }


    @OnClick({R.id.bt_finish, R.id.b_qrcode_img, R.id.bgoto_memberImg, R.id.bmember_num, R.id.coopRl, R.id.msendTv, R.id.bgroupshareRl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.b_qrcode_img:
                showQrDialog();
                break;
            case R.id.bgoto_memberImg:
                GroupMemberManageActivity.start(GroupMessageActivity.this, groupId, groupName, identity);
                break;
            case R.id.bmember_num:
                GroupMemberManageActivity.start(GroupMessageActivity.this, groupId, groupName, identity);
                break;
            case R.id.coopRl:
                CircleResourceActivity.start(GroupMessageActivity.this, groupId);
                break;
            case R.id.msendTv:
                if (("mygroup").equals(from)) {
                    ConversationActivity.start(
                            GroupMessageActivity.this,
                            groupId,
                            groupName, groupNum,
                            headUrl,
                            shareUrl);
                } else if (("add").equals(from)) {
//
                    if (type == 2) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(GroupMessageActivity.this);
                        builder.setTitle("此群组已被设置为禁止加入");
                        builder.setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    } else {
                        RequestManager.getInstance().applyGroup(groupId, groupName, 1, "", new AllResultDoCallback() {
                            @Override
                            public void onSuccess(String msg) {
                                if (type == 1) {
                                    EditGroupActivity.start(GroupMessageActivity.this, "apply", groupId, groupName);
                                } else {
                                    ToastUtils.showCentetToast(GroupMessageActivity.this, msg);
                                    ConversationActivity.start(GroupMessageActivity.this, groupId, groupName, groupNum, headUrl, shareUrl);
                                }
                            }

                            @Override
                            public void onFailed(int code, String msg) {
                                if (code == 311) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(GroupMessageActivity.this);
                                    builder.setTitle(msg);
                                    builder.setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.show();
                                } else {

                                    ToastUtils.showCentetToast(GroupMessageActivity.this, msg);
                                }
                            }
                        });
                    }


                }
                break;
            case R.id.bgroupshareRl:
                showShareDialog();
                break;
            default:
                break;
        }
    }

    private void showQrDialog() {
        String groupQr = shareUrl + "/" + UserInfoHelper.getIntance().getUserId();
        GroupQrcodeActivity.start(GroupMessageActivity.this, groupName, groupQr, headUrl, shareUrl);
//        if (qrCodeDialog != null) {
//            qrCodeDialog.show();
//            return;
//        }
//        qrCodeDialog = new QLQRCodeDialog.Builder(mContext)
//                .setTitle("【企鹊桥】" + groupName)//设置标题
//                .setImageHead(headUrl)
//                .setQRCodeString(shareUrl + "/" + UserInfoHelper.getIntance().getUserId())//设置二维码信息
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

    private void showShareDialog() {
        if (shareDialog != null) {
            shareDialog.show();
            return;
        }
        shareDialog = new QLShareDialog.Builder(mContext)
                .setWithQRCode(2)//设置是否伴随二维码分享
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

                        }
                    }
                }).build();
        shareDialog.show();
    }

    private void shareCircle(SHARE_MEDIA share_media) {

        if (headUrl == null) {
            return;
        }
        ShareUtils.shareWebUrl(
                GroupMessageActivity.this
                , headUrl,
                share_media,
                shareUrl + "/" + UserInfoHelper.getIntance().getUserId(),
                "邀请您加入群聊" + "『" + groupName + "』",
                "群号: " + groupNumber, new UMShareListener() {
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
}
