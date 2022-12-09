package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthenticationSuccessActivity extends BaseActivity {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;

    public static void start(Context context, String company, String identification_name) {
        Intent starter = new Intent(context, AuthenticationSuccessActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("identification_name", identification_name);
        bundle.putString("company", company);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_authentication_success;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        String company = getIntent().getExtras().getString("company");
        String identification_name = getIntent().getExtras().getString("identification_name");
        String name=identification_name.substring(1,identification_name.length());
        String name1=identification_name.substring(0,1);
        for(int i=0;i<name.length();i++){
            name1=name1+"*";

        }
        tvName.setText(name1);
        tvCompanyName.setText(company);
    }

    @OnClick({R.id.bt_finish, R.id.tv_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.tv_sure:
                finish();
                break;
        }
    }
}
