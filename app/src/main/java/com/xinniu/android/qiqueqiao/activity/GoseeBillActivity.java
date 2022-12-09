package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.GoseeBillBean;
import com.xinniu.android.qiqueqiao.bean.QrcodeBean;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.fragment.tab.IndexFragment;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetGoseeBillCallback;
import com.xinniu.android.qiqueqiao.request.callback.QrCallback;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.TokePhotoUtils;
import com.xinniu.android.qiqueqiao.zxing.activity.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by yuchance on 2019/1/9.
 */

public class GoseeBillActivity extends BaseActivity {


    @BindView(R.id.bgosee_actdetialtv)
    TextView bgoseeActdetialtv;
    @BindView(R.id.bgosee_unapplytv)
    TextView bgoseeUnapplytv;
    @BindView(R.id.mgosee_goseeRl)
    LinearLayout mgoseeGoseeRl;
    @BindView(R.id.mact_titletv)
    TextView mactTitletv;
    @BindView(R.id.mact_placetv)
    TextView mactPlacetv;
    @BindView(R.id.mact_timetv)
    TextView mactTimetv;
    @BindView(R.id.bgoto_scanimg)
    ImageView bgotoScanimg;
    @BindView(R.id.mact_codetv)
    TextView mactCodetv;
    @BindView(R.id.mact_nametv)
    TextView mactNametv;
    @BindView(R.id.msignin_states)
    TextView msigninStates;
    @BindView(R.id.mgosee_phonetv)
    TextView mgoseePhonetv;
    @BindView(R.id.mgosee_signin_timetv)
    TextView mgoseeSigninTimetv;
    @BindView(R.id.mgoto_text)
    TextView mgotoText;
    @BindView(R.id.bgotosee_actdetialtv)
    TextView bgotoseeActdetialtv;
    private int actId;
    public static int GOSEEREQUEST = 100;
    public static int GOSEERESULT = 102;

    //打开扫描界面请求码
    public final static int REQUEST_CODE = 0x01;
    private String actUrl;

    public static void start(Activity context, int actId) {
        Intent intent = new Intent(context, GoseeBillActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("actId", actId);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, GOSEEREQUEST, bundle);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_gosee_bill;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            actId = bundle.getInt("actId");
        }

        buildDatas(true);
    }

    private void buildDatas(boolean isShow) {
        if (isShow) {
            showBookingToast(1);
        }
        RequestManager.getInstance().getGoseeBill(actId, new GetGoseeBillCallback() {
            @Override
            public void onSuccess(GoseeBillBean bean) {

                dismissBookingToast();

                ShowUtils.showTextPerfect(mactTitletv, bean.getTitle());
                ShowUtils.showTextPerfect(mactPlacetv, "地点: " + bean.getAddress());
                ShowUtils.showTextPerfect(mactTimetv, "时间: " + TimeUtils.time2ActTime(bean.getStart_time()*1000) + " - " + TimeUtils.time2Hourmm(bean.getEnd_time()*1000));
                ShowUtils.showTextPerfect(mactCodetv, "票号: " + bean.getTicket_number());
                ShowUtils.showTextPerfect(mactNametv, "报名人: " + bean.getRealname());
                ShowUtils.showTextPerfect(mgoseePhonetv, "电话: " + bean.getMobile());
                ShowUtils.showTextPerfect(mgoseeSigninTimetv, "报名时间: " + TimeUtils.time2ActTime(bean.getSignUp_time()*1000));
                if (bean.getStatus() == 0) {
                    ShowUtils.showTextPerfect(msigninStates, "取消报名");
                } else if (bean.getStatus() == 1) {
                    ShowUtils.showTextPerfect(msigninStates, "未签到");
                    ShowUtils.showImageResource(bgotoScanimg, R.mipmap.goscan_img);
                    ShowUtils.showTextPerfect(mgotoText, "扫码签到验票");
                    ShowUtils.showTextColor(mgotoText, ContextCompat.getColor(GoseeBillActivity.this, R.color._666));
                    ShowUtils.showUnClick(bgotoScanimg, true);
                } else {
                    ShowUtils.showTextPerfect(msigninStates, "已签到");
                    ShowUtils.showImageResource(bgotoScanimg, R.mipmap.sign_insuccess);
                    ShowUtils.showTextPerfect(mgotoText, "签到成功");
                    ShowUtils.showTextColor(mgotoText, ContextCompat.getColor(GoseeBillActivity.this, R.color.colorPrimary));
                    ShowUtils.showUnClick(bgotoScanimg, false);
                    ShowUtils.showViewVisible(mgoseeGoseeRl, View.GONE);
                    ShowUtils.showViewVisible(bgotoseeActdetialtv,View.VISIBLE);
                }
                actUrl = bean.getEvent_url();


            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(GoseeBillActivity.this, msg);
            }
        });


    }


    @OnClick({R.id.bt_finish, R.id.bgosee_actdetialtv, R.id.bgosee_unapplytv, R.id.bgoto_scanimg, R.id.bshape_goseeRl,R.id.bgotosee_actdetialtv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bgosee_actdetialtv:
                ApproveCardActivity.start(GoseeBillActivity.this, "url", actUrl, "");
                break;
            case R.id.bgosee_unapplytv:
                AlertDialogUtils.AlertDialog(GoseeBillActivity.this, "确定取消报名", "再想想", "确定", new AlertDialogUtils.setOnClick() {
                    @Override
                    public void setLeftOnClick(DialogInterface dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void setRightOnClick(DialogInterface dialog) {
                        goUnapply();
                        dialog.dismiss();
                    }
                });


                break;
            case R.id.bgoto_scanimg:
                requestPermission();
                break;
            case R.id.bshape_goseeRl:
                ApproveCardActivity.start(GoseeBillActivity.this, "url", actUrl, "");
                break;
            case R.id.bgotosee_actdetialtv:
                ApproveCardActivity.start(GoseeBillActivity.this, "url", actUrl, "");
                break;
            default:
                break;
        }
    }

    private void goUnapply() {
        RequestManager.getInstance().goCancelSignUp(actId, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(GoseeBillActivity.this, msg);
                setResult(GOSEERESULT);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(GoseeBillActivity.this, msg);
            }
        });
    }

    @AfterPermissionGranted(TokePhotoUtils.PERMISSION_TOKE_PHOTO)
    public void requestPermission() {
        if (EasyPermissions.hasPermissions(GoseeBillActivity.this, TokePhotoUtils.TOKE_PHOTO)) {
            Intent intent2 = new Intent(GoseeBillActivity.this, CaptureActivity.class);
            startActivityForResult(intent2, REQUEST_CODE);
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.permission_need_toke_photo),
                    TokePhotoUtils.PERMISSION_TOKE_PHOTO, TokePhotoUtils.TOKE_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == IndexFragment.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
            // TODO: 2018/1/23
            scanQrcode(scanResult);
        }
    }

    private void scanQrcode(String scanResult) {
        RequestManager.getInstance().scanQrcode(scanResult, new QrCallback() {
            @Override
            public void onSuccess(QrcodeBean bean) {
                if (bean.getType().equals("activity")) {
                    ToastUtils.showCentetToast(GoseeBillActivity.this, "签到成功");
                    buildDatas(false);
                }
            }

            @Override
            public void onFailed(int code, String msg, String data) {
                if (code == 202) {
                    ToastUtils.showCentetToast(GoseeBillActivity.this, msg);
                } else if (code == 205) {
                    ToastUtils.showCentetToast(GoseeBillActivity.this, msg + data);
                } else {
                    ToastUtils.showCentetToast(GoseeBillActivity.this, msg);
                }
            }
        });


    }

    @OnClick()
    public void onViewClicked() {
    }
}
