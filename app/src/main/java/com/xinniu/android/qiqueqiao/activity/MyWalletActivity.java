package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.MyWalletBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetMyWalletCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MyWalletActivity extends BaseActivity {
    @BindView(R.id.tv_price)
    TextView tvPrice;
    MyWalletBean mBean;
    private int is_vip;


    public static void start(Context context, int is_vip) {
        Intent intent = new Intent(context, MyWalletActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("is_vip", is_vip);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        Intent intent = getIntent();
        is_vip = intent.getIntExtra("is_vip", -1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDatas();
    }

    private void getDatas() {
        RequestManager.getInstance().userAccount(new GetMyWalletCallback() {
            @Override
            public void onSuccess(MyWalletBean item) {
                tvPrice.setText(item.getTotal_amount());

                mBean = item;

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(MyWalletActivity.this, msg);

            }
        });
    }

    @OnClick({R.id.bt_return, R.id.tv_cash_withdrawal, R.id.rlayout_bill, R.id.rlayout_discount_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_return:
                finish();
                break;
            case R.id.tv_cash_withdrawal:
                //提现
//                double mPrice= Double.parseDouble(mBean.getTotal_amount());
//                if(mPrice>=50) {
                    Gson gson = new Gson();
                    String json = gson.toJson(mBean);
                    CashWithdrawalActivity.start(MyWalletActivity.this, json, is_vip);
//                }else{
//                    ToastUtils.showCentetToast(MyWalletActivity.this,"提现金额不足50元，不能提现");
//                }
                break;
            case R.id.rlayout_bill:
                //我的账单
                MyBillActivity.start(MyWalletActivity.this);
                break;
            case R.id.rlayout_discount_record:
                //提现记录
                MyDiscountRecordActivity.start(MyWalletActivity.this);
                break;
        }
    }
}
