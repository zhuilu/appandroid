package com.xinniu.android.qiqueqiao.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialog;
//import android.support.v7.widget.AppCompatCheckBox;
//import android.support.v7.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CircleInfoAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CircleInfobean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLShareDialog;
import com.xinniu.android.qiqueqiao.dialog.CircleInfoMoreDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetCircleInfoCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
import com.xinniu.android.qiqueqiao.utils.QRCodeUtil;
import com.xinniu.android.qiqueqiao.utils.ShareUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;

/**
 * Created by lzq on 2018/1/31.
 * 圈子简介
 */

public class CircleInfoActivity extends BaseActivity implements View.OnClickListener,CircleInfoAdapter.OnCircleInfoItemClikListner {
    @BindView(R.id.circle_tip)
    LinearLayout linearLayout1;
    @BindView(R.id.circle_summary)
    LinearLayout linearLayout2;
    @BindView(R.id.circle_resource)
    LinearLayout linearLayout3;
    @BindView(R.id.head_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.circle_more)
    ImageView moreTv;
    @BindView(R.id.disturb)
    AppCompatCheckBox disturb;
    @BindView(R.id.add_to_top)
    AppCompatCheckBox addToTop;
    @BindView(R.id.circle_all_member)
    LinearLayout linearLayout4;
    @BindView(R.id.circle_no_talk)
    LinearLayout linearLayout5;
    @BindView(R.id.blacklist)
    LinearLayout linearLayout6;
    @BindView(R.id.qr_code)
    ImageView qrCode;
    @BindView(R.id.tool_bar_title)
    TextView circleNameTv;
    @BindView(R.id.bt_return)
    ImageView closeBt;
    @BindView(R.id.apply_new)
    LinearLayout applyNewLL;
    private int circleId;
    private String circleName;
    private CircleInfoAdapter adapter;
    private List<CircleInfobean.UserListBean> headList = new ArrayList<>();
//    public final static String ADD_MEMBER  = "ADD_MEMBER";
    public final static String DELETE_MEMBER  = "DELETE_MEMBER";
    private String introduction = "群简介";
    private CircleInfobean mCircleInfobean;
    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 1000;
    private Bitmap mQRBitmap;
    private Call mCall;
    private String shareUrl;
    @Override
    public int getLayoutId() {
        return R.layout.activity_circle_info;
    }

    public static void start(Context context,int circleId,String circleName,String shareUrl){
        Intent intent = new Intent(context,CircleInfoActivity.class);
        intent.putExtra("circleId",circleId);
        intent.putExtra("circleName",circleName);
        intent.putExtra("shareUrl",shareUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCircleInfo();
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        circleId = getIntent().getIntExtra("circleId",-1);
        circleName = getIntent().getStringExtra("circleName");
        shareUrl = getIntent().getStringExtra("shareUrl");
        circleNameTv.setText(circleName);
        adapter = new CircleInfoAdapter(CircleInfoActivity.this,headList);
        adapter.setOnCircleInfoItemClikListner(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,6));
        mRecyclerView.setAdapter(adapter);
        closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getCircleInfo(){
        showBookingToast(1);
        RequestManager.getInstance().getCircleInfo(circleId, new GetCircleInfoCallback() {
            @Override
            public void onSuccess(CircleInfobean item) {
                dismissBookingToast();
                mCircleInfobean = item;
                headList.clear();
                headList.addAll(item.getUserList());
//                CircleInfobean.UserListBean addMemberBean = new CircleInfobean.UserListBean();
//                addMemberBean.setHead_pic(ADD_MEMBER);
//                headList.add(addMemberBean);
                mQRBitmap = QRCodeUtil.createQRCodeBitmap(shareUrl+"/"+ UserInfoHelper.getIntance().getUserId(), 480, 480);
                if (item.getIs_admin() == 1){
                    CircleInfobean.UserListBean deleteMemberBean = new CircleInfobean.UserListBean();
                    deleteMemberBean.setHead_pic(DELETE_MEMBER);
                    headList.add(deleteMemberBean);
                    linearLayout5.setVisibility(View.VISIBLE);
                    linearLayout6.setVisibility(View.VISIBLE);
                    applyNewLL.setVisibility(View.VISIBLE);
                }else{
                    linearLayout5.setVisibility(View.GONE);
                    linearLayout6.setVisibility(View.GONE);
                    applyNewLL.setVisibility(View.GONE);
                }
                if (item.getIs_receive() == 1){
                    disturb.setChecked(true);
                }else{
                    disturb.setChecked(false);
                }
                disturb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            setNotification(1);
                        }else{
                            setNotification(0);
                        }
                    }
                });
                if (item.getIs_top() == 1){
                    addToTop.setChecked(true);
                }else{
                    addToTop.setChecked(false);
                }
                addToTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            setCircleTop(1);
                        }else{
                            setCircleTop(0);
                        }
                    }
                });
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(CircleInfoActivity.this,msg);
                dismissBookingToast();
            }
        });
    }
    @OnClick({R.id.circle_tip,R.id.circle_summary,R.id.circle_resource,R.id.circle_more,R.id.circle_all_member,R.id.circle_no_talk
    ,R.id.blacklist,R.id.apply_new})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.circle_tip){
            CircleNoticeListActivity.start(CircleInfoActivity.this,circleId);
        }
        if (id == R.id.circle_summary){
            if (mCircleInfobean == null){
                return;
            }
            if (!TextUtils.isEmpty(mCircleInfobean.getIntroduction())){
                CircleSuggestActivity.start(CircleInfoActivity.this,mCircleInfobean.getIntroduction());
            }else{
                CircleSuggestActivity.start(CircleInfoActivity.this,"未设置");
            }
        }
        if (id == R.id.circle_resource){
            CircleResourceActivity.start(CircleInfoActivity.this,circleId);
        }
        if (id == R.id.circle_more){
            showCircleInfoMoreDialog();
        }
        if (id == R.id.circle_all_member){
            if (mCircleInfobean == null){
                return;
            }
            GroupMemberActivity.start(CircleInfoActivity.this,circleId,mCircleInfobean.getIs_admin(),GroupMemberActivity.TYPE_NO_DELETE);
        }
        if (id == R.id.circle_no_talk){
            GroupMemberActivity.start(CircleInfoActivity.this,circleId,mCircleInfobean.getIs_admin(),GroupMemberActivity.TYPE_NO_TALK);
        }
        if (id == R.id.blacklist){
            GroupMemberActivity.start(CircleInfoActivity.this,circleId,mCircleInfobean.getIs_admin(),GroupMemberActivity.TYPE_REMOVE_FROM_BLACK_LIST);
        }
        if (id == R.id.apply_new){
            CircleNewApplyActivity.start(CircleInfoActivity.this,circleId);
        }
    }

    @Override
    public void onDelete() {
        if (mCircleInfobean == null){
            return;
        }
        GroupMemberActivity.start(CircleInfoActivity.this,circleId,mCircleInfobean.getIs_admin(),GroupMemberActivity.TYPE_NO_DELETE);
    }

    @Override
    public void onCommonItem(int position) {
        PersonCentetActivity.start(this,String.valueOf(headList.get(position).getUser_id()));
    }

    private void showCircleInfoMoreDialog(){
        final CircleInfoMoreDialog dialog = new CircleInfoMoreDialog(CircleInfoActivity.this,R.style.QLDialog);
        dialog.setOnItemClikListener(new CircleInfoMoreDialog.OnItemClikListener() {
            @Override
            public void onClick1() {
                dialog.dismiss();
                showShareDialog();
            }

            @Override
            public void onClick2() {
                quitCircle();
                dialog.dismiss();
            }

            @Override
            public void onClick3() {
                dialog.dismiss();
            }
        });
        dialog.show();
        if (mCircleInfobean.getIs_admin() == 1){
            dialog.setItemWithoutQuit();
        }else{
            dialog.setItemForRenovate();
        }
    }

    private void quitCircle(){
        RequestManager.getInstance().quitCircle(circleId, new RequestCallback<String>() {
            @Override
            public void requestStart(Call call) {
                showBookingToast(2);
                mCall = call;
            }

            @Override
            public void onSuccess(String s) {
                ToastUtils.showCentetImgToast(CircleInfoActivity.this,s);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(CircleInfoActivity.this,msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });
    }

    private void setNotification(int action){
        RequestManager.getInstance().setNotification(action, circleId, new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                ToastUtils.showCentetImgToast(CircleInfoActivity.this,resultDO.getMsg());
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(CircleInfoActivity.this,msg);
            }
        });
    }
    private void setCircleTop(int action){
        RequestManager.getInstance().setCircleTop(action, circleId, new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                ToastUtils.showCentetImgToast(CircleInfoActivity.this,resultDO.getMsg());
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(CircleInfoActivity.this,msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null){
            mCall.cancel();
        }
    }
    /**
     * 初始化并显示分享dialog
     */
    private void showShareDialog() {
        if (mCircleInfobean == null){
            return;
        }
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


        if (mCircleInfobean == null){
            return;
        }
        ShareUtils.shareWebUrl(
                CircleInfoActivity.this
                ,mCircleInfobean.getHead_pic(),
                share_media,
                shareUrl+"/"+ UserInfoHelper.getIntance().getUserId(),
                "【企鹊桥】"+circleName,
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
