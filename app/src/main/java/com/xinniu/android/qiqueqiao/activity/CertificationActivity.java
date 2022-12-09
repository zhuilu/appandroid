package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.security.rp.RPSDK;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CenterBean;
import com.xinniu.android.qiqueqiao.bean.TokenBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetTokenCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

public class CertificationActivity extends BaseActivity {
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_id)
    EditText editId;
    @BindView(R.id.tv_next)
    TextView tvNext;
    private String mName = "", mCompany = "";//资料界面填写的姓名
    private int is_v = 0;
    private int mFrom = 0;

    public static void start(Context context, int flag) {
        Intent intent = new Intent(context, CertificationActivity.class);
        Bundle bundle = new Bundle();
//        bundle.putString("name", name);
//        bundle.putString("company", company);
        bundle.putInt("flag", flag);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_certification;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
//        mName = getIntent().getExtras().getString("name");
//        is_v = getIntent().getExtras().getInt("is_v");
//        mCompany = getIntent().getExtras().getString("company");
        mFrom = getIntent().getExtras().getInt("flag");
        editName.setFilters(new InputFilter[]{getInputFilter()});

    }


    @Override
    protected void onResume() {
        super.onResume();
        RequestManager.getInstance().center(new RequestCallback<CenterBean>() {
            @Override
            public void requestStart(Call call) {

            }

            @Override
            public void onSuccess(CenterBean centerBean) {
                mName = centerBean.getUsers().getRealname();
                mCompany = centerBean.getUsers().getCorporate_name();
                is_v = centerBean.getUsers().getIs_v();
            }

            @Override
            public void onFailed(int code, String msg) {

            }

            @Override
            public void requestEnd() {

            }
        });
    }

    @OnClick({R.id.bt_finish, R.id.tv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.tv_next:
                if (StringUtils.isEmpty(editName.getText().toString())) {
                    ToastUtils.showCentetImgToast(CertificationActivity.this, "请输入真实姓名");
                    return;
                }
                if (StringUtils.isEmpty(editId.getText().toString())) {
                    ToastUtils.showCentetImgToast(CertificationActivity.this, "请输入身份证号");
                    return;
                }

                if (!StringUtils.isID_Number(editId.getText().toString())) {
                    ToastUtils.showCentetImgToast(CertificationActivity.this, "请输入正确的身份证号");
                    return;
                }

                if (!mName.equals(editName.getText().toString())) {
                    new QLTipDialog.Builder(this)
                            .setCancelable(false)
                            .setCancelableOnTouchOutside(false)
                            .setMessage("您输入的姓名与个人资料中的姓名“" + mName + "”不一致")
                            .setNegativeButton("重新输入", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {
                                    editName.setText("");
                                    editName.requestFocus();

                                }
                            }).setPositiveButton("修改个人资料", new QLTipDialog.IPositiveCallback() {
                        @Override
                        public void onClick() {
                            MineInfoActivity.start(CertificationActivity.this);

                        }
                    })
                            .show(CertificationActivity.this);

                    return;
                }

                submit(editId.getText().toString(), editName.getText().toString());
                break;
        }
    }

    private void submit(String id, String name) {
        showBookingToast(1);
        RequestManager.getInstance().getToken(name, id, new GetTokenCallback() {
            @Override
            public void onSuccess(TokenBean item) {
                dismissBookingToast();
                String token = item.getToken();

                RPSDK.start(token, mContext,
                        new RPSDK.RPCompletedListener() {
                            @Override
                            public void onAuditResult(RPSDK.AUDIT audit, String s, String s1) {

//                                if (audit == RPSDK.AUDIT.AUDIT_PASS) { //认证通过
//                                    Log.i("实人认证结果===", "通过" + audit);
//
//
//                                } else if (audit == RPSDK.AUDIT.AUDIT_FAIL) { //认证不通过
//                                    Log.i("实人认证结果===", "不通过" + audit);
//
//                                } else if (audit == RPSDK.AUDIT.AUDIT_IN_AUDIT) { //认证中，通常不会出现，只有在认证审核系统内部出现超时，未在限定时间内返回认证结果时出现。此时提示用户系统处理中，稍后查看认证结果即可。
//                                    Log.i("实人认证结果===", "认证中" + audit);
//
//                                } else if (audit == RPSDK.AUDIT.AUDIT_NOT) { //未认证，用户取消
//                                    Log.i("实人认证结果===", "用户取消" + audit);
//
//                                } else
                                if (audit == RPSDK.AUDIT.AUDIT_EXCEPTION) { //系统异常
                                    Log.i("实人认证结果===", "系统异常" + audit);
                                    ToastUtils.showCentetImgToast(CertificationActivity.this, "系统异常，请稍后进行认证");


                                } else {
                                    RequestManager.getInstance().getStatusCode(new GetTokenCallback() {
                                        @Override
                                        public void onSuccess(TokenBean item) {
                                            int status = item.getStatus();//-1 未认证, 0 认证中, 1 认证通过, 2 认证不通过
                                            if (status == 1) {
                                                //跳职业认证,发布资源的不跳
                                                ToastUtils.showCentetImgToast(CertificationActivity.this, "实名认证通过");
                                                if (mFrom == 0) {
                                                    if (is_v == 1) {
                                                        AuthenticationSuccessActivity.start(mContext, mCompany, editName.getText().toString());
                                                    } else {
                                                        ApproveCardActivity.start(mContext, "approve");
                                                    }
                                                }
                                                finish();
                                            } else if (status == -1 || status == 2) {
                                                //跳职业认证
                                                editId.setText("");
                                                editName.setText("");
                                                editName.requestFocus();
                                                ToastUtils.showCentetImgToast(CertificationActivity.this, "认证失败，请重新认证");
                                            } else if (status == 0) {
                                                ToastUtils.showCentetImgToast(CertificationActivity.this, "正在认证中");
                                                finish();
                                            }

                                        }

                                        @Override
                                        public void onFailed(int code, String msg) {
                                            ToastUtils.showCentetImgToast(CertificationActivity.this, msg);
                                        }
                                    });


                                }

                            }


                        });
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(CertificationActivity.this, msg);

            }
        });
    }


    /**
     * EditText限制只能输入汉字
     */
    public InputFilter getInputFilter() {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if (TextUtils.isEmpty(source)) {
                    return "";
                }

                for (int i = start; i < end; i++) {
                    if (stringFilterChinese(source) && !source.toString().contains("。") && !source.toString().contains("，")) {
                        return "";
                    }
                }
                return null;
            }
        };
        return filter;
    }


    /**
     * 限制只能输入汉字，过滤非汉字
     *
     * @param str 输入值
     * @return true 非汉字；false 汉字
     */
    public boolean stringFilterChinese(CharSequence str) {
        //只允许汉字，正则表达式匹配出所有非汉字
        String regEx = "[^\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

}
