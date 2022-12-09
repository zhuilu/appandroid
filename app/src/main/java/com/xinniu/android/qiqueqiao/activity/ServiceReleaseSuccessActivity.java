package com.xinniu.android.qiqueqiao.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ServiceReleaseSuccessActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.titlex)
    TextView titlex;
    @BindView(R.id.bBack_Rl)
    RelativeLayout bBackRl;
    @BindView(R.id.new_tool_bar)
    RelativeLayout newToolBar;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.msuccess_title)
    TextView msuccessTitle;
    @BindView(R.id.bsuccessbtn)
    TextView bsuccessbtn;
    private int serviceId;
    private String from;

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_release_success;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        serviceId = getIntent().getIntExtra("id", 0);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("from"))) {
            from = getIntent().getStringExtra("from");
        }
        if ("push".equals(from)) {
            titlex.setText("发布成功");
            title.setText("发布成功");
            msuccessTitle.setVisibility(View.VISIBLE);
        } else if ("case".equals(from)) {
            titlex.setText("发布成功");
            title.setText("发布成功");
            msuccessTitle.setVisibility(View.GONE);
        } else {
            titlex.setText("修改成功");
            title.setText("修改成功");
            msuccessTitle.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.bBack_Rl, R.id.bsuccessbtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bBack_Rl:
                finish();
                break;
            case R.id.bsuccessbtn:
                if (from.equals("case")) {
                    //案例详情
                    CaseDetailActivity.start(ServiceReleaseSuccessActivity.this, serviceId);
                } else {
                    ServiceDetailActivity.start(ServiceReleaseSuccessActivity.this, serviceId);
                }
                finish();
                break;
        }
    }


}
