package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by lzq on 2018/2/28.
 */

public class EditCompanyChildActivity extends BaseActivity{

    private static final String PARAM_HINT = "PARAM_HINT";
    private static final String PARAM_TYPE = "PARAM_TYPE";
    public static final int PARAM_TYPE_INTRODUCE = 1;
    public static final int PARAM_TYPE_URL = 2;
    private int fromType = 0;
    private String hint;
    private int requestCode;
    @BindView(R.id.edit_text)
    EditText mEditText;
    @BindView(R.id.button)
    TextView button;
    @BindView(R.id.bt_return)
    ImageView closeIv;
    @Override
    public int getLayoutId() {
        fromType = getIntent().getIntExtra(PARAM_TYPE,0);
        hint = getIntent().getStringExtra(PARAM_HINT);
        if(fromType == PARAM_TYPE_INTRODUCE){
            return R.layout.activity_company_edit_introduce;
        }
        if(fromType == PARAM_TYPE_URL){
            return R.layout.activity_company_url;
        }
        return R.layout.activity_company_url;
    }
    public static void startForResult(Activity context, int requestCode,String hint) {
        Intent starter = new Intent(context, EditCompanyChildActivity.class);
        starter.putExtra(PARAM_TYPE, requestCode);
        starter.putExtra(PARAM_HINT, hint);
        context.startActivityForResult(starter, requestCode);
    }
    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        if (!TextUtils.isEmpty(hint)){
            mEditText.setText(hint);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        requestCode = getIntent().getIntExtra(PARAM_TYPE,0);
    }
    private void commit() {
        Intent intent = new Intent();
        intent.putExtra("data", mEditText.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
