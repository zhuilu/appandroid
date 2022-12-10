package com.xinniu.android.qiqueqiao.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.ReleaseStepHelper;
import com.xinniu.android.qiqueqiao.adapter.CoopWayAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetCategoryCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/3/29.
 */

public class CoopWayActivity extends BaseActivity {
    @BindView(R.id.bt_close)
    ImageView btClose;
    @BindView(R.id.activity_coop_way_recycler)
    RecyclerView activityCoopWayRecycler;
    @BindView(R.id.tool_bar)
    RelativeLayout toolBar;
    private CoopWayAdapter adapter;
    public static int COOPWAY_RESULT = 201;
    public static String NAME = "name";


    private List<SelectCategory> mSelectCategoryList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_coop_way;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        LinearLayoutManager mManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        activityCoopWayRecycler.setLayoutManager(mManager);
        adapter = new CoopWayAdapter(R.layout.item_coopway,mSelectCategoryList);
        initData();
        activityCoopWayRecycler.setAdapter(adapter);
        itemOclick();
    }



    private void initData() {
        showBookingToast(1);
        RequestManager.getInstance().getScreen(Constants.TYPE_SELECT_JOIN, new GetCategoryCallback() {
            @Override
            public void onSuccess(List<SelectCategory> list) {
                dismissBookingToast();
                mSelectCategoryList.clear();
                mSelectCategoryList.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(CoopWayActivity.this,msg);
            }
        });
    }
    private void itemOclick() {
     adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
         @Override
         public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            String name = mSelectCategoryList.get(position).getName();
            int id = mSelectCategoryList.get(position).getId();
             Intent intent = getIntent();
             Bundle bundle = new Bundle();
             bundle.putString(NAME,name);
             bundle.putInt("id",id);
             intent.putExtras(bundle);
            setResult(COOPWAY_RESULT,intent);
            finish();

         }
     });


    }
    @OnClick({R.id.bt_close, R.id.activity_coop_way_recycler})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_close:
                finish();
                break;
            case R.id.activity_coop_way_recycler:

                break;
        }
    }
}
