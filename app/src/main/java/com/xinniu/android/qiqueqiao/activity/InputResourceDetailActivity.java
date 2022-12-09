package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class InputResourceDetailActivity extends BaseActivity {
    @BindView(R.id.mpublish_titletv)
    TextView mpublishTitletv;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.mpublish_needet)
    EditText mpublishNeedet;
    private static final String PARAM_TITLE = "PARAM_TITLE";
    private static final String PARAM_BT = "PARAM_BT";
    private static final String PARAM_TYPE_CHILD = "PARAM_TYPE_CHILD";
    InputMethodManager imm;

    public static void startSimpleEidtForResult(Activity context, int requestCode, String biaoti, String title, int type) {
        Intent starter = new Intent(context, InputResourceDetailActivity.class);
        starter.putExtra(PARAM_TYPE_CHILD, type);
        starter.putExtra(PARAM_BT, biaoti);
        starter.putExtra(PARAM_TITLE, title);
        context.startActivityForResult(starter, requestCode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_input_resource_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        if (!TextUtils.isEmpty(getIntent().getExtras().getString(PARAM_BT))) {
            ShowUtils.showTextPerfect(mpublishTitletv, getIntent().getExtras().getString(PARAM_BT));
        }

        if (!TextUtils.isEmpty(getIntent().getExtras().getString(PARAM_TITLE))) {
            ShowUtils.showTextPerfect(mpublishNeedet, getIntent().getExtras().getString(PARAM_TITLE));
            String[] text=getIntent().getExtras().getString(PARAM_TITLE).split("\r\n");
            mpublishNeedet.setSelection(text[0].length());

        }
        if(getIntent().getExtras().getInt(PARAM_TYPE_CHILD) == 3){
            mpublishNeedet.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});
        }




}

    @OnClick({R.id.bt_finish, R.id.tv_save})
    public void onClick(View view) {
        if (imm != null) {
            imm.hideSoftInputFromWindow(mpublishNeedet.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(mpublishNeedet.getText().toString())) {
                    if (getIntent().getExtras().getInt(PARAM_TYPE_CHILD) == 1) {
                        ToastUtils.showCentetImgToast(mContext, "请输入合作需求说明");
                    } else  if (getIntent().getExtras().getInt(PARAM_TYPE_CHILD) == 2){
                        ToastUtils.showCentetImgToast(mContext, "请输入资源说明");
                    }else  if (getIntent().getExtras().getInt(PARAM_TYPE_CHILD) == 3){
                        ToastUtils.showCentetImgToast(mContext, "请输入求助详情");
                    }
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("data", mpublishNeedet.getText().toString());
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
    }

    //监听返回键(有软键盘的情况下想直接返回，需要拦截KeyEvent.ACTION_UP事件)
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
        {
            finish();
        }
        return super.dispatchKeyEvent(event);
    }

}
