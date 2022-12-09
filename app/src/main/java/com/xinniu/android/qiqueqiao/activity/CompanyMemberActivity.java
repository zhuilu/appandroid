package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CircleInfoAdapter;
//import com.xinniu.android.qiqueqiao.adapter.CompanyAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CircleInfobean;
import com.xinniu.android.qiqueqiao.bean.MyCompanyBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetMyCompanyCallback;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lzq on 2018/3/2.
 */

public class CompanyMemberActivity extends BaseActivity implements CircleInfoAdapter.OnCircleInfoItemClikListner {
    @BindView(R.id.member_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.bt_close)
    ImageView closeBt;

    private CircleInfoAdapter adapter;
    private List<CircleInfobean.UserListBean> headList = new ArrayList<>();

    public final static String TAG_Id = "TAG_Id";
    private int companyId;
//    public final static String TAG_PICNUMBER = "picNumber";
//    public final static String TAG_YRL = "url";
//    public final static String TAG_NUMBER = "number";
//    public final static String TAG_STAGE = "stage";
//    public final static String TAG_INTRODUCE = "introduce";

    public static void start(Context context, int companyId) {
        Intent starter = new Intent(context, CompanyMemberActivity.class);
//        starter.putExtra(TAG_LOGON, logo);
//        starter.putExtra(TAG_PICNUMBER, picNumber);
//        starter.putExtra(TAG_YRL, url);
        starter.putExtra(TAG_Id, companyId);
//        starter.putExtra(TAG_STAGE, stage);
//        starter.putExtra(TAG_INTRODUCE, introduce);
        context.startActivity(starter);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_company_member;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        Intent intent = getIntent();
        companyId = intent.getIntExtra(TAG_Id,-1);
        adapter = new CircleInfoAdapter(CompanyMemberActivity.this,headList);
        adapter.setOnCircleInfoItemClikListner(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,6));
        mRecyclerView.setAdapter(adapter);
        loadDate();
        closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadDate(){
        showBookingToast(1);
        RequestManager.getInstance().getCorporateInfo(companyId, new GetMyCompanyCallback() {
            @Override
            public void onSuccess(MyCompanyBean bean) {
                dismissBookingToast();
                headList.clear();
                for (MyCompanyBean.UsersBean item : bean.getUsers()){
                    CircleInfobean.UserListBean bean1 = new CircleInfobean.UserListBean();
                    bean1.setUser_id(item.getUser_id());
                    bean1.setHead_pic(item.getHead_pic());
                    bean1.setRealname(item.getRealname());
                    headList.add(bean1);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
            }
        });
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onCommonItem(int position) {
        PersonCentetActivity.start(this,String.valueOf(headList.get(position).getUser_id()));
    }
}
