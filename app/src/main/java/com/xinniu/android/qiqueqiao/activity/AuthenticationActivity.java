package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthenticationActivity extends BaseActivity {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_status_01)
    TextView tvStatus01;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_status_02)
    TextView tvStatus02;
    @BindView(R.id.llayout_a)
    LinearLayout llayoutA;

    private int is_cloud_auth;
    private int is_v;
    private int is_join;
    private String realname;
    private  String company;

    public static void start(Context context, int is_cloud_auth, int is_v, String company, String identification_name, int is_join, String realname) {
        Intent starter = new Intent(context, AuthenticationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("realname", realname);
        bundle.putString("identification_name", identification_name);
        bundle.putString("company", company);
        bundle.putInt("is_cloud_auth", is_cloud_auth);
        bundle.putInt("is_v", is_v);
        bundle.putInt("is_join", is_join);
        starter.putExtras(bundle);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_authentication;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        is_join = getIntent().getExtras().getInt("is_join");
        is_cloud_auth = getIntent().getExtras().getInt("is_cloud_auth");
        is_v = getIntent().getExtras().getInt("is_v");//用户是否上传名片（ 0：未上传 1：上传2：正在审核中 3：审核失败）
        realname = getIntent().getExtras().getString("realname");
         company = getIntent().getExtras().getString("company");
        String identification_name = getIntent().getExtras().getString("identification_name");
        if ((is_cloud_auth == 0 && is_v == 0) || (is_cloud_auth == 0 && is_v == 3)) {
            //两个都没有认证
            llayoutA.setVisibility(View.INVISIBLE);
        } else {
            llayoutA.setVisibility(View.VISIBLE);
            String name=identification_name.substring(1,identification_name.length());
            String name1=identification_name.substring(0,1);
            for(int i=0;i<name.length();i++){
                name1=name1+"*";

            }
            tvName.setText(name1);
            tvCompanyName.setText(company);
        }

    }


    @OnClick({R.id.bt_finish, R.id.img_rz})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.img_rz:
                //认证

                if ((is_cloud_auth == 0 && is_v == 0) || (is_cloud_auth == 0 && is_v == 3)) {
                    //两个都没有认证
                    CertificationActivity.start(AuthenticationActivity.this, 0);
                } else {
                    ApproveCardActivity.start(mContext, "approve");
                }
                finish();

                break;
        }
    }
}
