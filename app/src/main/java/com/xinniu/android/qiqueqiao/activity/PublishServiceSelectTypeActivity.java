package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.PublishServiceSelectTypeAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.ServiceCategoryAndTag;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetServiceCategoryAndTagCallback;
import com.xinniu.android.qiqueqiao.utils.ComUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 发布服务-选择资源类型
 * Created by yuchance on 2018/12/12.
 */

public class PublishServiceSelectTypeActivity extends BaseActivity {
    @BindView(R.id.mrecycler)
    RecyclerView mrecycler;
    private List<ServiceCategoryAndTag> datas = new ArrayList<>();
    private PublishServiceSelectTypeAdapter adapter;
    private int mTypeId;


    public static void startSimpleEidtForResult(AppCompatActivity context, int requestCode, int id) {
        Intent starter = new Intent(context, PublishServiceSelectTypeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        starter.putExtras(bundle);
        context.startActivityForResult(starter, requestCode);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_service_selecttype;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        ComUtils.addActivity(this);
        mTypeId=getIntent().getExtras().getInt("id");
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mrecycler.setLayoutManager(manager);
        adapter = new PublishServiceSelectTypeAdapter(PublishServiceSelectTypeActivity.this, R.layout.item_publish_service_type, datas);

        mrecycler.setAdapter(adapter);
        buildData();
        adapter.setSetOnClick(new PublishServiceSelectTypeAdapter.setOnClick() {
            @Override
            public void setOnClick(String title, int typeId, List<ServiceCategoryAndTag.ZlistBean> data) {
                Intent intent = new Intent();
                intent.putExtra("data", title);
                intent.putExtra("id", typeId);
                Gson g = new Gson();
                String jsonString = g.toJson(data);
                intent.putExtra("tags", jsonString);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }

    private void buildData() {
        showBookingToast(1);
        RequestManager.getInstance().getCategoryAndTag(new GetServiceCategoryAndTagCallback() {

            @Override
            public void onSuccess(List<ServiceCategoryAndTag> item) {
                dismissBookingToast();
                datas.addAll(item);
                if(mTypeId==-1){
                    adapter.getSparseBooleanArray().clear();
                }else{
                    for(int i=0;i<datas.size();i++){
                        if(mTypeId==datas.get(i).getId()){
                            adapter.getSparseBooleanArray().clear();
                            adapter.getSparseBooleanArray().put(i, true);//默认选择第一条
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
            }
        });
    }


    @OnClick(R.id.bt_finish)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            default:
                break;
        }


    }
}
