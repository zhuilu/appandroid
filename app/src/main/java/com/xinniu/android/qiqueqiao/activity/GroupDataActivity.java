package com.xinniu.android.qiqueqiao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.GroupInfoBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetGroupInfoCallback;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/10/9.
 */

public class GroupDataActivity extends BaseActivity {
    @BindView(R.id.mremindTv)
    TextView mremindTv;
    @BindView(R.id.mremindRl)
    RelativeLayout mremindRl;
    @BindView(R.id.mgroupName)
    TextView mgroupName;
    @BindView(R.id.mgroupCodeTv)
    TextView mgroupCodeTv;
    @BindView(R.id.mgroup_typeTv)
    TextView mgroupTypeTv;
    @BindView(R.id.mgroup_cityTv)
    TextView mgroupCityTv;
    @BindView(R.id.mgroup_pernumtv)
    TextView mgroupPernumtv;
    @BindView(R.id.mgroupnoticeTv)
    TextView mgroupnoticeTv;
    @BindView(R.id.bgroup_cacelcreate)
    TextView bgroupCacelcreate;
    private int groupId;

    public static void start(AppCompatActivity context, int groupId) {

        Intent intent = new Intent(context, GroupDataActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("groupId", groupId);
        intent.putExtras(bundle);
        context.startActivityForResult(intent,501, bundle);


    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_group_data;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {topStatusBar(true);
        bgroupCacelcreate.setSelected(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            groupId = bundle.getInt("groupId");
        }

        loadDatas();


    }

    private void loadDatas() {
        showBookingToast(1);
        RequestManager.getInstance().getGroupInformation(groupId, new GetGroupInfoCallback() {
            @Override
            public void onSuccess(GroupInfoBean bean) {
                dismissBookingToast();
                ShowUtils.showTextPerfect(mgroupName, bean.getName());
                ShowUtils.showTextPerfect(mgroupCityTv, bean.getCity_name());
                ShowUtils.showTextPerfect(mgroupPernumtv, bean.getNumber() + "人群");
                ShowUtils.showTextPerfect(mgroupCodeTv, "群号:  " + bean.getGroup_number());
                ShowUtils.showTextPerfect(mgroupTypeTv, bean.getCategory_name());
                ShowUtils.showTextPerfect(mgroupnoticeTv, bean.getIntroduction());
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(GroupDataActivity.this,msg);
            }
        });

    }


    @OnClick({R.id.bt_finish, R.id.bgroup_cacelcreate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bgroup_cacelcreate:
                cacelCreate();
                break;
            default:
                break;
        }
    }

    private void cacelCreate() {
        RequestManager.getInstance().groupCancle(groupId, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(GroupDataActivity.this,msg);
                setResult(500);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(GroupDataActivity.this,msg);
            }
        });

    }

}
