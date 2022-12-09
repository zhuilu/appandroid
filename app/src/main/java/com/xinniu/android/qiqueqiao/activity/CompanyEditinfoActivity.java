package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.customs.ClearEditText;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/5/8.
 */

public class CompanyEditinfoActivity extends BaseActivity {
    @BindView(R.id.finish_img)
    RelativeLayout finishImg;
    @BindView(R.id.mcompany_editinfo_title)
    TextView mcompanyEditinfoTitle;
    @BindView(R.id.mcompanyinfo_edit)
    ClearEditText mcompanyinfoEdit;
    @BindView(R.id.ycompanyinfo_editRl)
    RelativeLayout ycompanyinfoEditRl;
    @BindView(R.id.mcompanyinfointro_edit)
    EditText mcompanyinfointroEdit;
    @BindView(R.id.ycompanyinfointro_editRl)
    RelativeLayout ycompanyinfointroEditRl;
    @BindView(R.id.commitTv)
    TextView commitTv;
    @BindView(R.id.offerNeedInfoIntro_edit)
    EditText offerNeedInfoIntroEdit;
    @BindView(R.id.yofferneedintro_editRl)
    RelativeLayout yofferneedintroEditRl;
    @BindView(R.id.mnetinfo_edit)
    ClearEditText mnetinfoEdit;
    @BindView(R.id.ynetinfo_editRl)
    RelativeLayout ynetinfoEditRl;
    private String type;
    private Intent intent;
    private InputMethodManager imm;

    public final static int THEREQUESTCODE = 1000;

    public final static int BRANDRESULT = 1001;
    public final static int INDUSTRYRESULT = 1002;
    public final static int INTRODUCERESULT = 1003;
    public final static int NETURLRESULT = 1004;
    public final static int NEEDRESULT = 1005;
    public final static int OFFERRESULT = 1006;
    private String str = "";
    private String imputtxt;
    private String content;

    public static void startCompanyInfo(Activity context, String type, String typeValue, String content) {
        Intent intent = new Intent(context, CompanyEditinfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(type, typeValue);
        bundle.putString("content", content);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, THEREQUESTCODE, bundle);
    }

    public static void startCompanyInfo(Activity context, String type, String typeValue, String ofneStr, String ofneInfo) {
        Intent intent = new Intent(context, CompanyEditinfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(type, typeValue);
        bundle.putString(ofneStr, ofneInfo);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, THEREQUESTCODE, bundle);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_company_editinfo;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        intent = getIntent();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            type = bundle.getString("accessCode");
            content = bundle.getString("content");
        }
        if (type != null) {
            if (type.equals("brandName")) {
                ShowUtils.showTextPerfect(mcompanyEditinfoTitle, "公司名称/产品名");
                ShowUtils.showViewVisible(ycompanyinfoEditRl, View.VISIBLE);
                ShowUtils.showViewVisible(ynetinfoEditRl, View.GONE);
                ShowUtils.showViewVisible(ycompanyinfointroEditRl, View.GONE);
                ShowUtils.showViewVisible(yofferneedintroEditRl, View.GONE);
                if (!TextUtils.isEmpty(content)) {
                    mcompanyinfoEdit.setText(content);
                    mcompanyinfoEdit.requestFocus();
                }
                imm.showSoftInput(mcompanyinfoEdit, 0);
            } else if (type.equals("industry")) {
                ShowUtils.showTextPerfect(mcompanyEditinfoTitle, "公司行业");
                ShowUtils.showViewVisible(ycompanyinfoEditRl, View.VISIBLE);
                ShowUtils.showViewVisible(ynetinfoEditRl, View.GONE);
                ShowUtils.showViewVisible(ycompanyinfointroEditRl, View.GONE);
                ShowUtils.showViewVisible(yofferneedintroEditRl, View.GONE);
                if (!TextUtils.isEmpty(content)) {
                    mcompanyinfoEdit.setText(content);
                    mcompanyinfoEdit.requestFocus();
                }
                imm.showSoftInput(mcompanyinfoEdit, 0);
            } else if (type.equals("introduce")) {
                ShowUtils.showTextPerfect(mcompanyEditinfoTitle, "公司介绍");
                ShowUtils.showViewVisible(ycompanyinfoEditRl, View.GONE);
                ShowUtils.showViewVisible(ynetinfoEditRl, View.GONE);
                ShowUtils.showViewVisible(ycompanyinfointroEditRl, View.VISIBLE);
                ShowUtils.showViewVisible(yofferneedintroEditRl, View.GONE);
                if (!TextUtils.isEmpty(content)) {
                    mcompanyinfointroEdit.setText(content);
                    mcompanyinfointroEdit.requestFocus();
                }
                imm.showSoftInput(mcompanyinfointroEdit, 0);
            } else if (type.equals("netUrl")) {
                ShowUtils.showTextPerfect(mcompanyEditinfoTitle, "公司官网");
                ShowUtils.showViewVisible(ycompanyinfoEditRl, View.GONE);
                ShowUtils.showViewVisible(ynetinfoEditRl, View.VISIBLE);
                ShowUtils.showViewVisible(ycompanyinfointroEditRl, View.GONE);
                ShowUtils.showViewVisible(yofferneedintroEditRl, View.GONE);
                if (!TextUtils.isEmpty(content)) {
                    mnetinfoEdit.setText(content);
                    mnetinfoEdit.requestFocus();
                }
                imm.showSoftInput(mnetinfoEdit, 0);
                mnetinfoEdit.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
            } else if (type.equals("needDetail")) {
                if (bundle != null) {
                    String needDetailStr = bundle.getString("needDetailInfo");
                    ShowUtils.showTextPerfect(offerNeedInfoIntroEdit, needDetailStr);
                }
                ShowUtils.showTextPerfect(mcompanyEditinfoTitle, "需求资源详情");
                ShowUtils.showViewVisible(ycompanyinfoEditRl, View.GONE);
                ShowUtils.showViewVisible(ynetinfoEditRl, View.GONE);
                ShowUtils.showViewVisible(ycompanyinfointroEditRl, View.GONE);
                ShowUtils.showViewVisible(yofferneedintroEditRl, View.VISIBLE);
            } else if (type.equals("offerDetail")) {
                if (bundle != null) {
                    String offerDetailStr = bundle.getString("offerDetailInfo");
                    ShowUtils.showTextPerfect(offerNeedInfoIntroEdit, offerDetailStr);
                }
                ShowUtils.showTextPerfect(mcompanyEditinfoTitle, "提供资源详情");
                ShowUtils.showViewVisible(ycompanyinfoEditRl, View.GONE);
                ShowUtils.showViewVisible(ynetinfoEditRl, View.GONE);
                ShowUtils.showViewVisible(ycompanyinfointroEditRl, View.GONE);
                ShowUtils.showViewVisible(yofferneedintroEditRl, View.VISIBLE);
            }
        }
        mcompanyinfoEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imputtxt = mcompanyinfoEdit.getText().toString();
                str = stringFilter(imputtxt);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(str)) {
                    String limtxt = getLimitSubstring(str);
                    if (!limtxt.equals(imputtxt)) {
                        mcompanyinfoEdit.setText(limtxt);
                        mcompanyinfoEdit.setSelection(limtxt.length());
                    }
                } else {

                }
            }
        });


    }


    @OnClick({R.id.finish_img, R.id.commitTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.finish_img:
                finish();
                break;
            case R.id.commitTv:
                Bundle bundle = new Bundle();
                if ("brandName".equals(type)) {
                    if (TextUtils.isEmpty(mcompanyinfoEdit.getText().toString())) {
                        ToastUtils.showCentetToast(CompanyEditinfoActivity.this, "请输入公司简称/产品名");
                        return;
                    }
                    if (mcompanyinfoEdit.getText().toString().length() > 6) {
                        ToastUtils.showCentetToast(CompanyEditinfoActivity.this, "公司简称/产品名最多只能输入6位");
                        return;
                    }
                    bundle.putString("mContent", mcompanyinfoEdit.getText().toString());
                    intent.putExtras(bundle);
                    setResult(BRANDRESULT, intent);

                } else if ("industry".equals(type)) {
                    if (TextUtils.isEmpty(mcompanyinfoEdit.getText().toString())) {
                        ToastUtils.showCentetToast(CompanyEditinfoActivity.this, "请输入公司行业");
                        return;
                    }
                    bundle.putString("mContent", mcompanyinfoEdit.getText().toString());
                    intent.putExtras(bundle);
                    setResult(INDUSTRYRESULT, intent);

                } else if ("introduce".equals(type)) {
                    if (TextUtils.isEmpty(mcompanyinfointroEdit.getText().toString())) {
                        ToastUtils.showCentetToast(CompanyEditinfoActivity.this, "请输入公司介绍");
                        return;
                    }
                    if (mcompanyinfointroEdit.getText().toString().length()>1000) {
                        ToastUtils.showCentetToast(CompanyEditinfoActivity.this, "公司介绍最多1000字");
                        return;
                    }
                    bundle.putString("mContent", mcompanyinfointroEdit.getText().toString());
                    intent.putExtras(bundle);
                    setResult(INTRODUCERESULT, intent);

                } else if ("netUrl".equals(type)) {
                    if (TextUtils.isEmpty(mnetinfoEdit.getText().toString())) {
                        ToastUtils.showCentetToast(CompanyEditinfoActivity.this, "请输入公司官网");
                        return;
                    }
                    bundle.putString("mContent", mnetinfoEdit.getText().toString());
                    intent.putExtras(bundle);
                    setResult(NETURLRESULT, intent);
                } else if ("needDetail".equals(type)) {
                    if (TextUtils.isEmpty(offerNeedInfoIntroEdit.getText().toString())) {
                        ToastUtils.showCentetToast(CompanyEditinfoActivity.this, "请输入需求资源详情");
                        return;
                    }
                    bundle.putString("mContent", offerNeedInfoIntroEdit.getText().toString());
                    intent.putExtras(bundle);
                    setResult(NEEDRESULT, intent);
                } else if ("offerDetail".equals(type)) {
                    if (TextUtils.isEmpty(offerNeedInfoIntroEdit.getText().toString())) {
                        ToastUtils.showCentetToast(CompanyEditinfoActivity.this, "请输入提供资源详情");
                        return;
                    }
                    bundle.putString("mContent", offerNeedInfoIntroEdit.getText().toString());
                    intent.putExtras(bundle);
                    setResult(OFFERRESULT, intent);
                }
                finish();
                break;
            default:

                break;
        }
    }

    //校验输入类型
    public static String stringFilter(String str) throws PatternSyntaxException {
        // 仅仅同意字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    //限制字符长度
    private String getLimitSubstring(String inputStr) {
        int orignLen = inputStr.length();
        int resultLen = 0;
        String temp = null;
        for (int i = 0; i < orignLen; i++) {
            temp = inputStr.substring(i, i + 1);
            try {// 3 bytes to indicate chinese word,1 byte to indicate english
                // word ,in utf-8 encode
                if (temp.getBytes("utf-8").length == 3) {
                    resultLen += 3;
                } else {
                    resultLen++;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d("==CreateGroupName", "resultLen:" + resultLen);
            if (resultLen > 18) {
                return inputStr.substring(0, i);
            } else {

            }
        }
        return inputStr;
    }

}
