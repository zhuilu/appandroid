package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.EditResouceAdapter;
import com.xinniu.android.qiqueqiao.adapter.MoveGroupAdapter;
import com.xinniu.android.qiqueqiao.adapter.RewardTypeAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.PublicRewardBean;
import com.xinniu.android.qiqueqiao.bean.RewardTypeBean;
import com.xinniu.android.qiqueqiao.customs.NestedRecyclerView;
import com.xinniu.android.qiqueqiao.customs.label.FlowLayoutManager;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetRewardTypeCallback;
import com.xinniu.android.qiqueqiao.request.callback.PublicRewardCallback;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PublicRewardAvtivity extends BaseActivity {
    @BindView(R.id.edit_title)
    EditText editTitle;
    @BindView(R.id.moffer_recycler)
    NestedRecyclerView mofferRecycler;
    @BindView(R.id.rlayout_01)
    RelativeLayout rlayout01;
    @BindView(R.id.mpublish_offeret)
    TextView mpublishOfferet;
    @BindView(R.id.yofferEtRl)
    RelativeLayout yofferEtRl;
    @BindView(R.id.tv_price)
    EditText tvPrice;
    @BindView(R.id.tv_number)
    EditText tvNumber;
    private RewardTypeAdapter rewardTypeAdapter;
    private List<String> mDatas = new ArrayList<>();
    private String mType = "";

    public static void start(Context context) {
        Intent starter = new Intent(context, PublicRewardAvtivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_offer_reward;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        ComUtils.addActivity(PublicRewardAvtivity.this);
        mpublishOfferet.setMovementMethod(ScrollingMovementMethod.getInstance());
        mpublishOfferet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    // 通知ScrollView控件不要干扰
                    mpublishOfferet.setBackgroundColor(getResources().getColor(R.color.white));
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    // 通知ScrollView控件不要干扰
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    mpublishOfferet.setBackgroundColor(getResources().getColor(R.color.bg_gray_F8));
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });

        FlowLayoutManager flowLayoutManager = new FlowLayoutManager() {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mofferRecycler.setLayoutManager(flowLayoutManager);
        rewardTypeAdapter = new RewardTypeAdapter(PublicRewardAvtivity.this, R.layout.item_label_typetv_four, mDatas);
        mofferRecycler.setAdapter(rewardTypeAdapter);

        rewardTypeAdapter.setSetOnClick(new RewardTypeAdapter.setOnClick() {
            @Override
            public void setOnClick(String group_name) {
                mType = group_name;

            }
        });
        getType();
    }

    private void getType() {
        RequestManager.getInstance().getTypeName(new GetRewardTypeCallback() {
            @Override
            public void onSuccess(RewardTypeBean item) {
                mDatas.clear();
                mDatas.addAll(item.getType_name());
                rewardTypeAdapter.notifyDataSetChanged();
                rewardTypeAdapter.getSparseBooleanArray().clear();
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    @OnClick({R.id.bt_return, R.id.btn_submit, R.id.mpublish_offeret})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_return:
                finish();
                break;
            case R.id.btn_submit:
                String title = editTitle.getText().toString();
                String memo = mpublishOfferet.getText().toString();
                String price = tvPrice.getText().toString();
                String number = tvNumber.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    ToastUtils.showCentetToast(PublicRewardAvtivity.this, "请输入悬赏标题");
                    return;
                }
                if (TextUtils.isEmpty(mType)) {
                    ToastUtils.showCentetToast(PublicRewardAvtivity.this, "请选择悬赏类型");
                    return;
                }
                if (TextUtils.isEmpty(memo)) {
                    ToastUtils.showCentetToast(PublicRewardAvtivity.this, "请输入求助详情");
                    return;
                }
                if (TextUtils.isEmpty(price)) {
                    ToastUtils.showCentetToast(PublicRewardAvtivity.this, "请输入悬赏金额");
                    return;
                }
                if (Integer.parseInt(price) < 1 || Integer.parseInt(price) > 10000) {
                    ToastUtils.showCentetToast(PublicRewardAvtivity.this, "悬赏金额在1-10000之间");
                    return;
                }
                if (TextUtils.isEmpty(number)) {
                    ToastUtils.showCentetToast(PublicRewardAvtivity.this, "请输入赏金份数");
                    return;
                }
                if (Integer.parseInt(number) <= 0 || Integer.parseInt(number) > 100) {
                    ToastUtils.showCentetToast(PublicRewardAvtivity.this, "悬赏份数在1-100之间");
                    return;
                }

                doSumbit(title, memo, price, Integer.parseInt(number));

                break;
            case R.id.mpublish_offeret:
                InputResourceDetailActivity.startSimpleEidtForResult(this, 31, "求助说明",
                        mpublishOfferet.getText().toString(), 3);
                break;
        }
    }

    private void doSumbit(String title, String memo, final String price, final int number) {
        showBookingToast(2);
        RequestManager.getInstance().releaseReward(mType, title, memo, price, number, new PublicRewardCallback() {
            @Override
            public void onSuccess(PublicRewardBean item) {
                dismissBookingToast();
                if (item.getIs_pay() == 1) {//	0：未支付，1：已支付
                    RewardDetailActivity.start(mContext, item.getOrder_sn());
                    finish();
                } else {
                    int p = Integer.parseInt(price) * number;
                    PayRewardActivity.start(mContext, p+"", item.getId(), item.getOrder_sn());
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(PublicRewardAvtivity.this, msg);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 31) {
            String data1 = data.getStringExtra("data");
            mpublishOfferet.setText(data1);
        }


    }

}
