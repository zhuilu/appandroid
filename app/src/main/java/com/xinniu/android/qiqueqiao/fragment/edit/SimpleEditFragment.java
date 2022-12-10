package com.xinniu.android.qiqueqiao.fragment.edit;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.customs.ClearEditText;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by qinlei
 * Created on 2017/12/18
 * Created description :
 */

public class SimpleEditFragment extends LazyBaseFragment {
    private static final String PARAM_HINT = "PARAM_HINT";
    private static final String PARAM_STR_EDITTEXT = "PARAM_STR_EDITTEXT";
    private static final String PARAM_TYPE = "PARAM_TYPE";
    private static final String PARAM_TITLE = "PARAM_TITLE";

    public static final int SIMPLE_EDIT_NAME = 11;
    public static final int SIMPLE_EDIT_WEIXIN = 12;
    public static final int SIMPLE_EDIT_BRAND = 13;
    public static final int SIMPLE_EDIT_INDUSTRY = 14;
    public static final int SIMPLE_EDIT_POSITION = 15;
    public static final int SIMPLE_EDIT_COMPANY = 16;

    @BindView(R.id.edit_text)
    ClearEditText editText;
    @BindView(R.id.button)
    TextView button;
    @BindView(R.id.mtool_bar_title)
    TextView mtoolBarTitle;


    public static SimpleEditFragment newInstance(String hint, String strEdittext,String title ,int type) {
        Bundle args = new Bundle();
        args.putString(PARAM_HINT, hint);
        args.putString(PARAM_STR_EDITTEXT, strEdittext);
        args.putInt(PARAM_TYPE, type);
        args.putString(PARAM_TITLE,title);
        SimpleEditFragment fragment = new SimpleEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_simple;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        setDataToView();
    }

    private void setDataToView() {

        if (getArguments() == null) {
            return;
        }
        button.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(getArguments().getString(PARAM_TITLE))){
            ShowUtils.showTextPerfect(mtoolBarTitle,getArguments().getString(PARAM_TITLE));
        }

            if (getArguments().getInt(PARAM_TYPE)==SIMPLE_EDIT_NAME){
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
            }else if (getArguments().getInt(PARAM_TYPE)==SIMPLE_EDIT_POSITION){
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
            }else {
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
            }

        if (!TextUtils.isEmpty(getArguments().getString(PARAM_HINT))) {
            editText.setHint(getArguments().getString(PARAM_HINT));
        }
        if (!TextUtils.isEmpty(getArguments().getString(PARAM_STR_EDITTEXT))) {
            editText.setText(getArguments().getString(PARAM_STR_EDITTEXT));
            editText.requestFocus();
        }
    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.bt_finish,R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.bt_finish:
                getActivity().finish();
                break;
            case R.id.button:

        if (getArguments() == null) {
            return;
        }
        switch (getArguments().getInt(PARAM_TYPE)) {
            case SIMPLE_EDIT_NAME:
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    ToastUtils.showCentetImgToast(mContext, "请输入真实姓名");
                    return;
                }
                if (!StringUtils.stringFilter(editText.getText().toString())) {
                    ToastUtils.showCentetImgToast(mContext, "只能输入中文、字母和空格");
                    return;
                }
                commit();
                break;
            case SIMPLE_EDIT_WEIXIN:
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    ToastUtils.showCentetImgToast(mContext, "请输入微信号");
                    return;
                }
                commit();
                break;
            case SIMPLE_EDIT_BRAND:
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    ToastUtils.showCentetImgToast(mContext, "请输入品牌名称");
                    return;
                }
                commit();
                break;
            case SIMPLE_EDIT_INDUSTRY:
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    ToastUtils.showCentetImgToast(mContext, "请输入行业");
                    return;
                }
                commit();
                break;
            case SIMPLE_EDIT_POSITION:
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    ToastUtils.showCentetImgToast(mContext, "请输入职位");
                    return;
                }
                commit();
                break;
            case SIMPLE_EDIT_COMPANY:
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    ToastUtils.showCentetImgToast(mContext, "请输入公司全称");
                    return;
                }
                commit();
                break;

            default:
                break;
        }
        break;
        default:
            break;
        }
    }

    private void commit() {
        Intent intent = new Intent();
        intent.putExtra("data", editText.getText().toString());
        getActivity().setResult(AppCompatActivity.RESULT_OK, intent);
        getActivity().finish();
    }

}
