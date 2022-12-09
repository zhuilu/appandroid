package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.MyGroupAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.MyGroupListBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.MyGroupListCallback;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/10/9.
 */

public class MyGroupActivity extends BaseActivity {
    @BindView(R.id.mrecycler)
    RecyclerView mrecycler;

    private List<MyGroupListBean> datas = new ArrayList<>();
    private int page = 1;
    private MyGroupAdapter adapter;

    public static void start(Context context){
        Intent intent = new Intent(context,MyGroupActivity.class);

        context.startActivity(intent);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mygroup;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {topStatusBar(true);
        ComUtils.addActivity(this);
        LinearLayoutManager manager = new LinearLayoutManager(MyGroupActivity.this,LinearLayoutManager.VERTICAL,false);
        mrecycler.setLayoutManager(manager);
        adapter = new MyGroupAdapter(R.layout.item_my_group,datas);
        mrecycler.setAdapter(adapter);

        loadDatas(page);

        adapter.setGoToGroupMessage(new MyGroupAdapter.goToGroupMessage() {
            @Override
            public void goToGroupMessage(int status,int groupId) {
                if (status == 0){
                    GroupDataActivity.start(MyGroupActivity.this,groupId);
                }else if (status == 1){
                    GroupMessageActivity.start(MyGroupActivity.this,groupId,"mygroup");
                }else if (status == 2){

                }
            }
        });


    }

    private void loadDatas(final int page) {
        RequestManager.getInstance().getMyGroupList(page, new MyGroupListCallback() {
            @Override
            public void onSuccess(List<MyGroupListBean> list) {
                if (page == 1){
                    datas.clear();
                }
                datas.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(MyGroupActivity.this,msg);
            }
        });

    }


    @OnClick({R.id.bt_finish, R.id.bcircle_addgroupRl, R.id.bcircle_creategroupRl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bcircle_addgroupRl:
                AddGroupActivity.start(MyGroupActivity.this);
                break;
            case R.id.bcircle_creategroupRl:
                CreateGroupActivity.startx(MyGroupActivity.this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==501&&resultCode == 500){
            loadDatas(page);
        }

    }
}
