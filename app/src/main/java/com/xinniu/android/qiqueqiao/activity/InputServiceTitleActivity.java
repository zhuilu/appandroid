package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.customs.ClearEditText;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class InputServiceTitleActivity extends BaseActivity {
    @BindView(R.id.edit_title)
    ClearEditText editTitle;
    private static final String PARAM_TITLE = "PARAM_TITLE";
    private static final String PARAM_BT = "PARAM_BT";
    private static final String PARAM_HINT = "PARAM_HINT";
    private static final String PARAM_TYPE_CHILD = "PARAM_TYPE_CHILD";
    @BindView(R.id.mpublish_titletv)
    TextView mpublishTitletv;

    public static void startSimpleEidtForResult(Activity context, int requestCode, String biaoti, String title, String hint, int type) {
        Intent starter = new Intent(context, InputServiceTitleActivity.class);
        starter.putExtra(PARAM_TYPE_CHILD, type);
        starter.putExtra(PARAM_BT, biaoti);
        starter.putExtra(PARAM_TITLE, title);
        starter.putExtra(PARAM_HINT, hint);
        context.startActivityForResult(starter, requestCode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_title;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        if (!TextUtils.isEmpty(getIntent().getExtras().getString(PARAM_BT))) {
            ShowUtils.showTextPerfect(mpublishTitletv, getIntent().getExtras().getString(PARAM_BT));
        }
        if (!TextUtils.isEmpty(getIntent().getExtras().getString(PARAM_HINT))) {
            editTitle.setHint(getIntent().getExtras().getString(PARAM_HINT));
        }

        if (!TextUtils.isEmpty(getIntent().getExtras().getString(PARAM_TITLE))) {
            ShowUtils.showTextPerfect(editTitle, getIntent().getExtras().getString(PARAM_TITLE));
            editTitle.setSelection(getIntent().getExtras().getString(PARAM_TITLE).length());
        }

    }

    @OnClick({R.id.bt_finish, R.id.tv_save})
    public void onClick(View view) {
        View viewFocus = this.getCurrentFocus();
        if (viewFocus != null) {
            InputMethodManager imManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imManager.hideSoftInputFromWindow(viewFocus.getWindowToken(), 0);
        }
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(editTitle.getText().toString())) {
                    if (getIntent().getExtras().getInt(PARAM_TYPE_CHILD) == 1) {
                        ToastUtils.showCentetImgToast(mContext, "请输入服务标题");
                    } else {
                        ToastUtils.showCentetImgToast(mContext, "请输入案例标题");
                    }
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("data", editTitle.getText().toString());
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
