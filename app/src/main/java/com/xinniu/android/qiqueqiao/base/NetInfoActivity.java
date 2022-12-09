package com.xinniu.android.qiqueqiao.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.common.WindowDialogService;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * token失效跳转至登录
 */
public class NetInfoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.tv_message)
    TextView tvMsg;
    String mFrom="0";
    @Override
    public int getLayoutId() {
        return R.layout.activity_net_info;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
             mFrom = getIntent().getExtras().getString("from");

        }
        if(mFrom.equals("1")){
            tvMsg.setText("登录信息已失效，请重新登录");
        }else {
            tvMsg.setText(WindowDialogService.DIALOG_MSG);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //跳转至登陆

            //结束服务
            stopService(new Intent(mContext, WindowDialogService.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.tvLogin})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvLogin) {
            if (BaseApp.allActivities != null) {
                synchronized (BaseApp.allActivities) {
                    for (Activity act : BaseApp.allActivities) {
                        act.finish();
                    }
                }
            }
            BaseApp.getInstance().Logout();


//            LoginActivity.start(NetInfoActivity.this);
        }
    }
}
