package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.utils.ComUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2019/2/18.
 */

public class PropStoreActivity extends BaseActivity {


    public static void start(Context context) {
        Intent intent = new Intent(context, PropStoreActivity.class);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_propstore;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        ComUtils.addActivity(PropStoreActivity.this);
    }


    @OnClick({R.id.bt_finish, R.id.btop_cardRl, R.id.bsuper_showRl, R.id.blx_limitRl,R.id.bcarding_showRl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.btop_cardRl:
                TopCardActivity.start(PropStoreActivity.this, "1");
                break;
            case R.id.bsuper_showRl:
                SuperExposureActivity.start(PropStoreActivity.this, "1");
                break;
            case R.id.blx_limitRl:
                DataPlusBuyActivity.start(PropStoreActivity.this);
                break;
            case R.id.bcarding_showRl:
                CardingCardActivity.start(PropStoreActivity.this,"1");
                break;
        }
    }
}
