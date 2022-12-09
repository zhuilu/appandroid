package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CreateGroupClassifyAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.AddGroupClassifyBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AddGroupClassifyCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.FullyGridLayoutManager;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/9/28.
 */

public class CreateGroupActivity extends BaseActivity {
    @BindView(R.id.gridrecycler)
    NoScrollRecyclerView gridrecycler;
    @BindView(R.id.mvipcontent)
    TextView mvipcontent;
    @BindView(R.id.bgoTo_vip)
    TextView bgoToVip;
    private List<AddGroupClassifyBean> datas = new ArrayList<>();
    private CreateGroupClassifyAdapter adapter;

    public static void start(Context context){
        Intent intent = new Intent(context,CreateGroupActivity.class);
        context.startActivity(intent);
    }
    public static void startx(Activity context){
        Intent intent = new Intent(context,CreateGroupActivity.class);
        context.startActivityForResult(intent,501);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_creategroup;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {topStatusBar(true);
        ComUtils.addActivity(CreateGroupActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(CreateGroupActivity.this,4);
        gridrecycler.setLayoutManager(gridLayoutManager);
        adapter = new CreateGroupClassifyAdapter(CreateGroupActivity.this,R.layout.item_creategroup_classify,datas);
        adapter.setGoToNext(new CreateGroupClassifyAdapter.goToNext() {
            @Override
            public void goToNext(int groupId) {
                Intent intent = new Intent(CreateGroupActivity.this, CreateGroupNameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("groupId",groupId);
                intent.putExtras(bundle);
                startActivityForResult(intent,501,bundle);
            }
        });
        gridrecycler.setAdapter(adapter);
        loadDatas();
        RequestManager.getInstance().center(null);
        int isVip = UserInfoHelper.getIntance().getCenterBean().getUsers().getIs_vip();
        switch (isVip){
            case 0:
                ShowUtils.showTextPerfect(mvipcontent, R.string.novip_stringcontent);
                break;
            case 1:
                ShowUtils.showTextPerfect(mvipcontent, R.string.vip_stringcontent);
                break;
            case 2:
                ShowUtils.showTextPerfect(mvipcontent, R.string.svip_stringcontent);
                ShowUtils.showViewVisible(bgoToVip,View.GONE);
                break;
            default:
                break;

        }

    }

    private void loadDatas() {
        showBookingToast(1);
        RequestManager.getInstance().getCategoryList(4, new AddGroupClassifyCallback() {
            @Override
            public void onSuccess(List<AddGroupClassifyBean> item) {
                dismissBookingToast();
                datas.addAll(item);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(CreateGroupActivity.this,msg);
            }
        });
    }

    @OnClick({R.id.bt_finish, R.id.bgoTo_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bgoTo_vip:
                startActivity(new Intent(CreateGroupActivity.this, VipV4ListActivity.class));
                break;
        }
    }

}
