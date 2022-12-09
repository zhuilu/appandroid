package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.HandlingFeeBean;
import com.xinniu.android.qiqueqiao.bean.ImageBean;
import com.xinniu.android.qiqueqiao.bean.OtherUserInfo;
import com.xinniu.android.qiqueqiao.bean.ServiceCategoryAndTag;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.RetrofitHelper;
import com.xinniu.android.qiqueqiao.request.callback.GetOtherUserInfoCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.richtexteditor.RichTextEditor;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.TokePhotoUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import retrofit2.Call;

public class LaunchTransactionAvtivity extends BaseActivity {
    @BindView(R.id.item_lx_headimg)
    CircleImageView itemLxHeadimg;
    @BindView(R.id.lx_nametv)
    TextView lxNametv;
    @BindView(R.id.lx_positiontv)
    TextView lxPositiontv;
    @BindView(R.id.tv_buyer)
    TextView tvBuyer;
    @BindView(R.id.tv_seller)
    TextView tvSeller;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.edit_price)
    EditText editPrice;
    @BindView(R.id.check)
    CheckBox check;
    @BindView(R.id.llayout_p)
    LinearLayout llayoutP;
    @BindView(R.id.rlayout_info)
    RelativeLayout rlayoutInfo;
    @BindView(R.id.rlayout_phone)
    RelativeLayout rlayoutPhone;
    private String userId;
    private int type = 1;//	1：发起人甲方,买家，2：发起人乙方卖家

    public static void start(Context context, String userId, int type) {
        Intent intent = new Intent(context, LaunchTransactionAvtivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        bundle.putInt("type", type);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_launching_secured_transaction;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        userId = getIntent().getStringExtra("userId");
        int type = getIntent().getExtras().getInt("type");
        if (type == 0) {
            rlayoutInfo.setVisibility(View.VISIBLE);
            rlayoutPhone.setVisibility(View.GONE);
            getUserInfo();

        } else {
            rlayoutInfo.setVisibility(View.GONE);
            rlayoutPhone.setVisibility(View.VISIBLE);
        }


        String[] text = editContent.getText().toString().split("\n");
        editContent.setSelection(text[0].length());


        editPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    double p = Double.parseDouble(editable.toString());
                    String p1 = "1000000";
                    if (p > 1000000) {
                        editPrice.setText("");
                        editPrice.setText(p1);
                        editPrice.setSelection(p1.length());
                    }
                }

            }
        });
    }

    private void getUserInfo() {
        RequestManager.getInstance().showUserInfo(Integer.parseInt(userId), new GetOtherUserInfoCallback() {
            @Override
            public void onSuccess(OtherUserInfo bean) {
                ImageLoader.loadAvter(bean.getHead_pic(), itemLxHeadimg);
                lxNametv.setText(bean.getRealname());
                String job = "";
                if (StringUtils.isEmpty(bean.getCompany()) && StringUtils.isEmpty(bean.getPosition())) {
                    job = "";
                } else {
                    if (StringUtils.isEmpty(bean.getCompany())) {
                        job = bean.getPosition();
                    } else if (StringUtils.isEmpty(bean.getPosition())) {
                        job = bean.getCompany();
                    } else {
                        job = bean.getCompany() + "|" + bean.getPosition();
                    }
                }
                lxPositiontv.setText(job);
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    @OnClick({R.id.img_complete, R.id.bt_back, R.id.tv_buyer, R.id.tv_seller, R.id.llayout_p, R.id.view1, R.id.tv_agreement, R.id.rlayout_phone, R.id.tv_exchange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_complete:


                if (StringUtils.isEmpty(editContent.getText().toString())) {
                    ToastUtils.showCentetImgToast(mContext, "请输入交易内容");
                    return;
                }
                if (StringUtils.isEmpty(editPrice.getText().toString())) {
                    ToastUtils.showCentetImgToast(mContext, "请输入担保金额");
                    return;
                }
                double price = Double.parseDouble(editPrice.getText().toString());
                if (price < 10) {
                    ToastUtils.showCentetImgToast(mContext, "担保金额不能小于10元");
                    return;
                }
                if (!check.isChecked()) {
                    ToastUtils.showCentetImgToast(mContext, "请阅读并同意担保交易协议");
                    return;
                }
                doSumbit(editContent.getText().toString(), editPrice.getText().toString());
                break;
            case R.id.bt_back:
                finish();
                break;
            case R.id.tv_buyer:
                type = 1;
                tvBuyer.setBackgroundResource(R.mipmap.t_b_check);
                tvSeller.setBackgroundResource(R.mipmap.bg_tran_uncheck);
                tvBuyer.setTextColor(getResources().getColor(R.color.white));
                tvSeller.setTextColor(getResources().getColor(R.color.blue_bg_418CFF));
                break;
            case R.id.tv_seller:
                type = 2;
                tvSeller.setBackgroundResource(R.mipmap.t_b_check);
                tvBuyer.setBackgroundResource(R.mipmap.bg_tran_uncheck);
                tvSeller.setTextColor(getResources().getColor(R.color.white));
                tvBuyer.setTextColor(getResources().getColor(R.color.blue_bg_418CFF));


                break;
            case R.id.llayout_p:
                if (check.isChecked()) {
                    check.setChecked(false);
                } else {
                    check.setChecked(true);

                }
                break;
            case R.id.view1:
                PersonCentetActivity.start(LaunchTransactionAvtivity.this, userId);
                break;
            case R.id.tv_agreement:
                //担保协议
                ApproveCardActivity.start(LaunchTransactionAvtivity.this, "vip", RetrofitHelper.API_URL + "/resource/pages/guarTran/introduce.html", "担保协议");
                break;

            case R.id.rlayout_phone:
                ExchangeTransPhoneActivity.startSimpleEidtForResult(LaunchTransactionAvtivity.this, 31);


                break;
            case R.id.tv_exchange:

                ExchangeTransPhoneActivity.startSimpleEidtForResult(LaunchTransactionAvtivity.this, 31);
                break;
        }
    }


    private void doSumbit(String s, String s1) {

        RequestManager.getInstance().initiateGuarantee(Integer.parseInt(userId), s, s1, type, new RequestCallback<String>() {
            @Override
            public void requestStart(Call call) {

                showBookingToast(2);
            }

            @Override
            public void onSuccess(String s) {
                //跳交易详情
                Gson gson = new Gson();
                HandlingFeeBean handlingFeeBean = gson.fromJson(s, HandlingFeeBean.class);
                TransactionDetailsActivity.start(LaunchTransactionAvtivity.this, handlingFeeBean.getId() + "");
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(mContext, msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 31) {//详情
                rlayoutInfo.setVisibility(View.VISIBLE);
                rlayoutPhone.setVisibility(View.GONE);
                String data1 = data.getStringExtra("data");
                Gson gson = new Gson();
                OtherUserInfo bean = gson.fromJson(data1, OtherUserInfo.class);
                userId = bean.getUser_id() + "";
                ImageLoader.loadAvter(bean.getHead_pic(), itemLxHeadimg);
                lxNametv.setText(bean.getRealname());
                String job = "";
                if (StringUtils.isEmpty(bean.getCompany()) && StringUtils.isEmpty(bean.getPosition())) {
                    job = "";
                } else {
                    if (StringUtils.isEmpty(bean.getCompany())) {
                        job = bean.getPosition();
                    } else if (StringUtils.isEmpty(bean.getPosition())) {
                        job = bean.getCompany();
                    } else {
                        job = bean.getCompany() + "|" + bean.getPosition();
                    }
                }
                lxPositiontv.setText(job);

            }
        }

    }
}
