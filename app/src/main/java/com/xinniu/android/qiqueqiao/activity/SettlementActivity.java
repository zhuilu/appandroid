package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipTwoDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SettlementActivity extends BaseActivity {
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_price_two)
    EditText tvPriceTwo;
    private int mId;
    private String price;

    public static void startSimpleEidtForResult(AppCompatActivity context, int id, String price, int requestCode) {
        Intent intent = new Intent(context, SettlementActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("price", price);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_settlement;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Intent intent = getIntent();
        mId = intent.getIntExtra("id",-1);
        price = intent.getStringExtra("price");

        if (price.contains(".")) {
            String[] pricr01 = price.split("\\.");
            tvPrice.setText(pricr01[0]);
        } else {
            tvPrice.setText(price);
        }

        SpannableString ss = new SpannableString("请输入结算金额");//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(17, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new StyleSpan(Typeface.NORMAL), 0, ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPriceTwo.setHint(new SpannedString(ss));

        tvPriceTwo.addTextChangedListener(new TextWatcher() {
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
                    double p1 = Double.parseDouble(price);
                    if (p > p1) {
                        tvPriceTwo.setText("");
                        tvPriceTwo.setText(tvPrice.getText().toString());
                        tvPriceTwo.setSelection(tvPrice.getText().toString().length());
                    }
                }

            }
        });
    }

    @OnClick({R.id.bt_return, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_return:
                finish();
                break;
            case R.id.btn_submit:
                if (StringUtils.isEmpty(tvPriceTwo.getText().toString())) {
                    ToastUtils.showCentetToast(mContext, "请输入结算金额");
                    return;
                }
                double price1 = Double.parseDouble(tvPriceTwo.getText().toString());
                if (price1 <= 0) {
                    ToastUtils.showCentetImgToast(mContext, "结算金额不能小于等于0元");
                    return;
                }
                double price2 = Double.parseDouble(price);
                if (price1 == price2) {
                    new QLTipTwoDialog.Builder(SettlementActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setTitle("确认结算剩余全部担保金")
                            .setMessage("全额结算后，交易将会关闭")
                            .setPositiveButton("确认结算", new QLTipTwoDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {

                                    doSettlement();
                                }
                            })
                            .setNegativeButton("取消", new QLTipTwoDialog.INegativeCallback() {
                                @Override
                                public void onClick() {
                                    dissMissDialog();

                                }
                            })
                            .show(SettlementActivity.this);


                } else {
                    new QLTipTwoDialog.Builder(SettlementActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setTitle("确认结算")
                            .setMessage("结算后，对方将收到交易款项")
                            .setPositiveButton("确认结算", new QLTipTwoDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {

                                    doSettlement();
                                }
                            })
                            .setNegativeButton("取消", new QLTipTwoDialog.INegativeCallback() {
                                @Override
                                public void onClick() {
                                    dissMissDialog();

                                }
                            })
                            .show(SettlementActivity.this);


                }
                break;
        }
    }
    /**
     * 买家申请结算
     */
    private void doSettlement() {
        showBookingToast(2);
        RequestManager.getInstance().guaranteeSettlement(mId,tvPriceTwo.getText().toString(), new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(SettlementActivity.this, msg);
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(SettlementActivity.this, msg);
            }
        });
    }


}
