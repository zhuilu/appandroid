package com.xinniu.android.qiqueqiao.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.HandlingFeeBean;
import com.xinniu.android.qiqueqiao.bean.MyWalletBean;
import com.xinniu.android.qiqueqiao.bean.YzmBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.YzmCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CountDownTextViewTwo;
import com.xinniu.android.qiqueqiao.widget.PayPsdInputView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imlib.MD5;

public class CashWithdrawalActivity extends BaseActivity {
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.edit_price)
    EditText editPrice;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_fee)
    TextView tvFee;
    @BindView(R.id.check_01)
    CheckBox check01;
    @BindView(R.id.check_02)
    CheckBox check02;
    @BindView(R.id.edit_apliay)
    EditText editApliay;
    @BindView(R.id.llayout_01)
    LinearLayout llayout01;
    @BindView(R.id.edit_apliay_name)
    EditText editApliayName;
    @BindView(R.id.llayout_02)
    LinearLayout llayout02;
    @BindView(R.id.llayout_apilay)
    LinearLayout llayoutApilay;
    @BindView(R.id.edit_bank_accont)
    EditText editBankAccont;
    @BindView(R.id.edit_bank_kai)
    EditText editBankKai;
    @BindView(R.id.edit_bank_name)
    EditText editBankName;
    @BindView(R.id.llayout_bank)
    LinearLayout llayoutBank;
    @BindView(R.id.llayout_open_vip)
    LinearLayout llayoutOpenVip;
    MyWalletBean mBean;
    int account_type = 1;  //1:支付宝，2：银行卡
    private InputMethodManager imm;
    private int is_vip;

    public static void start(Context context, String data, int is_vip) {
        Intent intent = new Intent(context, CashWithdrawalActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("data", data);
        bundle.putInt("is_vip", is_vip);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cash_withdrawal;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        String data = getIntent().getStringExtra("data");
        Gson gson = new Gson();
        mBean = gson.fromJson(data, MyWalletBean.class);
        is_vip = getIntent().getIntExtra("is_vip", -1);
        tvFee.setText("余额￥" + mBean.getTotal_amount() + "，提现服务费" + mBean.getRate() + "%");
        if (mBean.getRecording() != null && !mBean.getRecording().equals("null")) {
            if (mBean.getRecording().getAccount_type() == 1) {//到账账户类型，1：支付宝 ，2 ：银行卡
                account_type = 1;
                check01.setChecked(true);
                check02.setChecked(false);
                llayoutApilay.setVisibility(View.VISIBLE);
                llayoutBank.setVisibility(View.GONE);
                editApliay.setText(mBean.getRecording().getAlipay_account());
                editApliayName.setText(mBean.getRecording().getAlipay_name());
            } else if (mBean.getRecording().getAccount_type() == 2) {
                account_type = 2;
                check01.setChecked(false);
                check02.setChecked(true);
                llayoutApilay.setVisibility(View.GONE);
                llayoutBank.setVisibility(View.VISIBLE);
                editBankAccont.setText(mBean.getRecording().getBank_account());
                editBankKai.setText(mBean.getRecording().getBank_account_name());
                editBankName.setText(mBean.getRecording().getBank());
            }
            if(is_vip==1||is_vip==2){
                llayoutOpenVip.setVisibility(View.GONE);
            }else{
                llayoutOpenVip.setVisibility(View.VISIBLE);
            }
        }

        editApliay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    for (int i = 0; i < s.length(); i++) {
                        char c = s.charAt(i);
                        if (c >= 0x4e00 && c <= 0X9fff) { // 根据字节码判断
                            // 如果是中文，则清除输入的字符，否则保留

                            s.delete(i, i + 1);
                        }
                    }
                }
            }
        });

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
                    String p1 = mBean.getTotal_amount();
                    double p2 = Double.parseDouble(p1);
                    if (p > p2) {
                        editPrice.setText("");
                        editPrice.setText(p1);
                        editPrice.setSelection(p1.length());
                    }
                }

            }
        });
    }

    @OnClick({R.id.bt_finish, R.id.check_01, R.id.check_02, R.id.tv_01, R.id.tv_02, R.id.btn_submit, R.id.llayout_open_vip, R.id.tv_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.check_01:
                account_type = 1;
                check01.setChecked(true);
                check02.setChecked(false);
                llayoutApilay.setVisibility(View.VISIBLE);
                llayoutBank.setVisibility(View.GONE);
                break;
            case R.id.check_02:
                account_type = 2;
                check01.setChecked(false);
                check02.setChecked(true);
                llayoutApilay.setVisibility(View.GONE);
                llayoutBank.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_01:
                account_type = 1;
                check01.setChecked(true);
                check02.setChecked(false);
                llayoutApilay.setVisibility(View.VISIBLE);
                llayoutBank.setVisibility(View.GONE);
                break;
            case R.id.tv_02:
                account_type = 2;
                check01.setChecked(false);
                check02.setChecked(true);
                llayoutApilay.setVisibility(View.GONE);
                llayoutBank.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_submit:

                if (StringUtils.isEmpty(editPrice.getText().toString())) {
                    ToastUtils.showCentetToast(CashWithdrawalActivity.this, "请输入提现金额");
                    return;
                }
                double price = Double.parseDouble(editPrice.getText().toString());
                if (price < 50) {
                    ToastUtils.showCentetToast(CashWithdrawalActivity.this, "提现金额不能低于50元");
                    return;
                }
                if (account_type == 1 && StringUtils.isEmpty(editApliay.getText().toString())) {
                    ToastUtils.showCentetToast(CashWithdrawalActivity.this, "请输入支付宝账号");
                    return;
                }

                if (account_type == 1 && StringUtils.isEmpty(editApliayName.getText().toString())) {
                    ToastUtils.showCentetToast(CashWithdrawalActivity.this, "请输入支付宝姓名");
                    return;
                }

                if (account_type == 2 && StringUtils.isEmpty(editBankAccont.getText().toString())) {
                    ToastUtils.showCentetToast(CashWithdrawalActivity.this, "请输入银行账号");
                    return;
                }
                if (account_type == 2 && StringUtils.isEmpty(editBankKai.getText().toString())) {
                    ToastUtils.showCentetToast(CashWithdrawalActivity.this, "请输入开户姓名");
                    return;
                }
                if (account_type == 2 && StringUtils.isEmpty(editBankName.getText().toString())) {
                    ToastUtils.showCentetToast(CashWithdrawalActivity.this, "请输入开户行");
                    return;
                }
                imm.hideSoftInputFromWindow(editPrice.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                doSubmit(editPrice.getText().toString());


                break;
            case R.id.llayout_open_vip:
                Intent intent = new Intent(CashWithdrawalActivity.this, VipV4ListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_all:
                //全部提现
                String p1 = mBean.getTotal_amount();
                editPrice.setText(p1);
                editPrice.setSelection(p1.length());
                break;
        }
    }

    /**
     * 计算服务费
     *
     * @param s
     */
    private void doSubmit(String s) {
        showBookingToast(1);
        RequestManager.getInstance().handlingFee(s, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                Gson gson = new Gson();
                HandlingFeeBean handlingFeeBean = gson.fromJson(msg, HandlingFeeBean.class);
                //发送短信验证码
                String userPhone = UserInfoHelper.getIntance().getUserName();
                goToGainYzm(userPhone, 3, handlingFeeBean.getHandling_fee(), 0);
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(CashWithdrawalActivity.this, msg);

            }
        });
    }


    //初始化并弹出对话框方法
    private void showDialog(final String handling_fee) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_cash_withdrawal, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        final PayPsdInputView payPsdInputView = view.findViewById(R.id.intput_view);
        TextView tvPrice = view.findViewById(R.id.tv_price);
        TextView tv_price_fee = view.findViewById(R.id.tv_price_fee);
        TextView tv_price_s = view.findViewById(R.id.tv_price_s);
        TextView tv_phone = view.findViewById(R.id.tv_phone);
        CountDownTextViewTwo countDownTextView = view.findViewById(R.id.bsendYzmtv);
        tvPrice.setText(editPrice.getText().toString());
        tv_price_fee.setText("-￥" + handling_fee);
        double price01 = Double.parseDouble(handling_fee);
        double price02 = Double.parseDouble(editPrice.getText().toString());
        double price03 = price02 - price01;
        tv_price_s.setText("￥" + price03);
        final String userPhone = UserInfoHelper.getIntance().getUserName();
        tv_phone.setText("验证码已发送到" + ComUtils.phone2star(userPhone));
        if (countDownTextView != null) {
            countDownTextView.setCountDownMillis(60000);
            countDownTextView.start();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imm.showSoftInput(payPsdInputView, InputMethodManager.SHOW_FORCED);
            }
        }, 500);
        payPsdInputView.setOnTextFinishListener(new PayPsdInputView.OnTextFinishListener() {
            @Override
            public void onTextFinish(CharSequence text, int length) {
                showBookingToast(2);
                imm.hideSoftInputFromWindow(payPsdInputView.getWindowToken(), 0);
                //提交提现
                String withdrawals_amount = editPrice.getText().toString();
                String alipay_account = editApliay.getText().toString();
                String alipay_name = editApliayName.getText().toString();
                String bank_account = editBankAccont.getText().toString();
                String bank_account_name = editBankKai.getText().toString();
                String bank = editBankName.getText().toString();
                RequestManager.getInstance().withdraw(text.toString(), userPhone, account_type, withdrawals_amount, alipay_account, alipay_name, bank_account, bank_account_name, bank, new AllResultDoCallback() {
                    @Override
                    public void onSuccess(String msg) {
                        Gson gson = new Gson();
                        HandlingFeeBean handlingFeeBean = gson.fromJson(msg, HandlingFeeBean.class);
                        dismissBookingToast();
                        dialog.dismiss();
                        finish();
                        WalletDetailActivity.start(CashWithdrawalActivity.this, handlingFeeBean.getId());

                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        dismissBookingToast();
                        ToastUtils.showCentetToast(CashWithdrawalActivity.this, msg);

                    }
                });
            }
        });

        view.findViewById(R.id.llayout_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        countDownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGainYzm(userPhone, 3, handling_fee, 1);
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    private void goToGainYzm(final String phone, int type, final String handling_fee, final int typeDialog) {
        String[] signArray = phone.split("");
        Arrays.sort(signArray);
        StringBuffer sign = new StringBuffer();
        for (int i = 0; i < signArray.length; i++) {
            sign.append(signArray[i]);
        }
        sign.append("QiQueqiao2018aySo08pks1k");
        // Log.d("===YzmHelper", sign.toString());
        String signx = MD5.encrypt(sign.toString(), true);
        //   Log.d("===YzmHelper", signx);
        RequestManager.getInstance().getYzm(phone, type, signx, new YzmCallback() {
            @Override
            public void onSuccess(YzmBean msg) {
                dismissBookingToast();
                if (typeDialog == 0) {//第一次弹窗
                    showDialog(handling_fee);
                }
            }

            @Override
            public void onFailed(String error, int code) {
                dismissBookingToast();
                ToastUtils.showCentetToast(CashWithdrawalActivity.this, error);
            }
        });
    }

}
