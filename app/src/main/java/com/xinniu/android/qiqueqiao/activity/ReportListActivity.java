package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.ReportListAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetCategoryCallback;
import com.xinniu.android.qiqueqiao.request.callback.ReportCallback;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/5/31.
 */

public class ReportListActivity extends BaseActivity {
    @BindView(R.id.bback_title)
    RelativeLayout bbackTitle;
    @BindView(R.id.bcommit_btn)
    TextView bcommitBtn;
    @BindView(R.id.mreportRv)
    NoScrollRecyclerView mreportRv;
    @BindView(R.id.mreportEd)
    EditText mreportEd;
    @BindView(R.id.mreportTv)
    TextView mreportTv;
    private List<SelectCategory> datas = new ArrayList<>();
    private ReportListAdapter adapter;
    private int categotyId = 0;
    private int type;
    private int targetId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_report_list;
    }
    public static void start(Context context,int type,int targetId){
        Intent intent = new Intent(context,ReportListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        bundle.putInt("target_id", targetId);
        intent.putExtras(bundle);
        context.startActivity(intent,bundle);
    }
    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("type");
        targetId = bundle.getInt("target_id");
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mreportRv.setLayoutManager(manager);
        adapter = new ReportListAdapter(R.layout.item_report_list, datas);
        mreportRv.setAdapter(adapter);
        buildDatas();
        mreportEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mreportTv.setText(mreportEd.getText().length()+"/100");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void buildDatas() {
        RequestManager.getInstance().getScreen(10, new GetCategoryCallback() {
            @Override
            public void onSuccess(List<SelectCategory> list) {
                datas.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(ReportListActivity.this, msg);
            }
        });

    }


    @OnClick({R.id.bback_title, R.id.bcommit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bback_title:
                finish();
                break;
            case R.id.bcommit_btn:
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).isCheck()){
                        categotyId = datas.get(i).getId();
                    }
                }

                String content = mreportEd.getText().toString();
                if (categotyId != 0) {
                    commitData(targetId, type, content, categotyId);
                }else {
                    ToastUtils.showCentetToast(ReportListActivity.this,"请选择举报原因");
                }
                break;
        }
    }

    private void commitData(int targetId,int type,String content,int categotyId) {
        RequestManager.getInstance().goToReport(targetId, type, content, categotyId, new ReportCallback() {
            @Override
            public void onSuccess(String msg) {
                if (msg.equals("success")) {
                    ToastUtils.showCentetToast(ReportListActivity.this, "举报成功，尽快处理中~");
                }else {
                    ToastUtils.showCentetToast(ReportListActivity.this, msg);
                }
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(ReportListActivity.this,msg);
            }
        });
    }

}
