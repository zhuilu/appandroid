package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.WelfareAgencyAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.ServiceCaseBean;
import com.xinniu.android.qiqueqiao.bean.WelfareAgencyBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetCaseListCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetWelfareClubListCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WelfareAgencyAvtivity extends BaseActivity {
    @BindView(R.id.mcollect_my_rv)
    RecyclerView mcollectMyRv;
    private List<WelfareAgencyBean> datas = new ArrayList<>();
    private WelfareAgencyAdapter welfareAgencyAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, WelfareAgencyAvtivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welfare_agency;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);

        LinearLayoutManager manager = new LinearLayoutManager(WelfareAgencyAvtivity.this, LinearLayoutManager.VERTICAL, false);
        mcollectMyRv.setLayoutManager(manager);
        welfareAgencyAdapter = new WelfareAgencyAdapter(WelfareAgencyAvtivity.this, R.layout.item_welfare_agency, datas);
        mcollectMyRv.setAdapter(welfareAgencyAdapter);
        showBookingToast(1);
        buildData();
    }

    @OnClick(R.id.bt_finish)
    public void onClick() {
        finish();
    }

    private void buildData() {
        RequestManager.getInstance().getWelfareClubList(new GetWelfareClubListCallback() {
            @Override
            public void onSuccess(List<WelfareAgencyBean> item) {
                dismissBookingToast();
                datas.clear();
                datas.addAll(item);
                welfareAgencyAdapter.notifyDataSetChanged();
            }


            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(WelfareAgencyAvtivity.this, msg);
            }
        });


    }
}
