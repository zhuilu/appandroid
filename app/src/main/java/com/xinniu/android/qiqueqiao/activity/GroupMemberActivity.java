package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v4.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.GroupMemerAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CompanyUsersBean;
import com.xinniu.android.qiqueqiao.bean.MemberInfoBean;
import com.xinniu.android.qiqueqiao.divider.DividerItemDecoration;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.CompanyUsersCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetGroupMemberCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lzq on 2018/2/1.
 */

public class GroupMemberActivity extends BaseActivity implements View.OnClickListener {
    private List<MemberInfoBean> memberList = new ArrayList<>();
    private List<MemberInfoBean> searchMemberList = new ArrayList<>();
    private int circleId;
    private GroupMemerAdapter mGroupMemerAdapter;
    private GroupMemerAdapter mSearchGroupMemerAdapter;
    private String targetId = "";
    private int isAdmin;
    private int type;
    public final static int TYPE_NO_TALK = 1;
    public final static int TYPE_NO_DELETE = 0;
    public final static int TYPE_REMOVE_FROM_BLACK_LIST = 2;
    public final static int TYPE_COMPANY = 3;
    private boolean isSearch = false;
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.delete)
    TextView deleteTv;
    @BindView(R.id.bt_return)
    ImageView closeTv;
    @BindView(R.id.tool_bar_title)
    TextView titleTv;
    @BindView(R.id.searchEdit)
    SearchView mSearchView;
    @BindView(R.id.search_rv)
    RecyclerView searchRv;

    @BindView(R.id.m_SwipeRefreshLayout)
    SmartRefreshLayout mSwipeRefreshLayout;


    @Override
    public int getLayoutId() {
        return R.layout.activity_group_member;
    }

    public static void start(Context context, int circleId, int isAdmin, int type) {
        Intent intent = new Intent(context, GroupMemberActivity.class);
        intent.putExtra("circleId", circleId);
        intent.putExtra("isAdmin", isAdmin);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

//    public static void start(Context context, int circleId,int isAdmin){
//        Intent intent = new Intent(context,GroupMemberActivity.class);
//        intent.putExtra("circleId",circleId);
//        intent.putExtra("isAdmin",isAdmin);
//        context.startActivity(intent);
//    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        circleId = getIntent().getIntExtra("circleId", -1);
        isAdmin = getIntent().getIntExtra("isAdmin", 0);
        type = getIntent().getIntExtra("type", 0);
        if (isAdmin == 1) {
            deleteTv.setVisibility(View.VISIBLE);
        } else {
            deleteTv.setVisibility(View.GONE);
        }
        if (type == TYPE_NO_TALK) {
            deleteTv.setText("禁言");
            titleTv.setText("全部成员");
            getGroupMember(circleId);
        }
        if (type == TYPE_REMOVE_FROM_BLACK_LIST) {
            deleteTv.setText("恢复");
            titleTv.setText("禁言列表");
            getBlackList(circleId);
        }
        if (type == TYPE_NO_DELETE) {
            deleteTv.setText("删除");
            titleTv.setText("全部成员");
            getGroupMember(circleId);
        }
        if (type == TYPE_COMPANY) {
            titleTv.setText("公司成员");
            deleteTv.setVisibility(View.GONE);
            getCompanyUsers(circleId);
        }
        mSearchGroupMemerAdapter = new GroupMemerAdapter(GroupMemberActivity.this, searchMemberList);
        mSearchGroupMemerAdapter.setIsAdmin(isAdmin);
        searchRv.setLayoutManager(new LinearLayoutManager(GroupMemberActivity.this));
        searchRv.addItemDecoration(new DividerItemDecoration(GroupMemberActivity.this, DividerItemDecoration.VERTICAL_LIST));
        searchRv.setAdapter(mSearchGroupMemerAdapter);

        mGroupMemerAdapter = new GroupMemerAdapter(GroupMemberActivity.this, memberList);
        mGroupMemerAdapter.setIsAdmin(isAdmin);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(GroupMemberActivity.this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(GroupMemberActivity.this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mGroupMemerAdapter);
        mSwipeRefreshLayout.setEnableLoadMore(false);
        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (type == TYPE_NO_TALK) {
                    getGroupMember(circleId);
                }
                if (type == TYPE_REMOVE_FROM_BLACK_LIST) {
                    getBlackList(circleId);
                }
                if (type == TYPE_NO_DELETE) {
                    getGroupMember(circleId);
                }
                if (type == TYPE_COMPANY) {
                    getCompanyUsers(circleId);
                }
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                for (int i =0 ;i < memberList.size();i++){
//                    if (!TextUtils.isEmpty(memberList.get(i).getRealname()) && !memberList.get(i).getRealname().contains(query)){
//                        memberList.remove(i);
//                    }
//                }
                if (!TextUtils.isEmpty(query)) {
                    searchMemberList.clear();
                    searchMemberList.addAll(mSearchGroupMemerAdapter.filter(memberList, query));
                    mSearchGroupMemerAdapter.notifyDataSetChanged();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                mGroupMemerAdapter.filter(memberList,newText);
                if (TextUtils.isEmpty(newText)) {
                    mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    searchRv.setVisibility(View.GONE);
                    isSearch = false;
                } else {
                    isSearch = true;
                    searchRv.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setVisibility(View.GONE);
                }
                return true;
            }
        });

    }

    private void getCompanyUsers(int circleId) {
        showBookingToast(1);
        RequestManager.getInstance().getCompanyUsers(circleId, new CompanyUsersCallback() {
            @Override
            public void onSuccess(List<MemberInfoBean> bean) {
                dismissBookingToast();
                finishSwipe();
                memberList.clear();
                memberList.addAll(bean);
                mGroupMemerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailue(int code, String msg) {
                finishSwipe();
                dismissBookingToast();
                ToastUtils.showCentetImgToast(GroupMemberActivity.this, msg);
            }
        });

    }

    private void getBlackList(int circleId) {
        showBookingToast(1);
        RequestManager.getInstance().getblackList(circleId, new GetGroupMemberCallback() {
            @Override
            public void onSuccess(List<MemberInfoBean> list) {
                finishSwipe();
                dismissBookingToast();
                memberList.clear();
                memberList.addAll(list);
                mGroupMemerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                finishSwipe();
                dismissBookingToast();
                ToastUtils.showCentetImgToast(GroupMemberActivity.this, msg);
            }
        });
    }

    private void getGroupMember(int circleId) {
        showBookingToast(1);
        RequestManager.getInstance().getGroupMember(circleId, new GetGroupMemberCallback() {
            @Override
            public void onSuccess(List<MemberInfoBean> list) {
                finishSwipe();
                dismissBookingToast();
                memberList.clear();
                memberList.addAll(list);
                mGroupMemerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                finishSwipe();
                dismissBookingToast();
                ToastUtils.showCentetImgToast(GroupMemberActivity.this, msg);
            }
        });
    }

    private void finishSwipe() {
        if (mSwipeRefreshLayout != null) {
            if (mSwipeRefreshLayout.isEnableRefresh()) {
                mSwipeRefreshLayout.finishRefresh();
            }

        }
    }

    private void bootCircle(String targetId) {
        showBookingToast(2);
        RequestManager.getInstance().bootCircle(targetId, circleId, new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                dismissBookingToast();
//                for (int i = 0 ; i< memberList.size();i++){
//                    if (memberList.get(i).isCheck()){
//                        memberList.remove(i);
//                    }
//                }
//                mGroupMemerAdapter.notifyDataSetChanged();
                getGroupMember(circleId);
                ToastUtils.showCentetImgToast(GroupMemberActivity.this, "删除成功");
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(GroupMemberActivity.this, msg);
            }
        });
    }

    @OnClick({R.id.delete, R.id.bt_return})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.delete) {
            String targetId = "";
            boolean isFirst = true;
            if (isSearch) {
                for (int i = 0; i < searchMemberList.size(); i++) {
                    if (searchMemberList.get(i).isCheck()) {
                        if (isFirst) {
                            targetId = targetId + searchMemberList.get(i).getUser_id();
                            isFirst = false;
                        } else {
                            targetId = targetId + ",";
                            targetId = targetId + searchMemberList.get(i).getUser_id();
                        }
                    }
                }
            } else {
                for (int i = 0; i < memberList.size(); i++) {
                    if (memberList.get(i).isCheck()) {
                        if (isFirst) {
                            targetId = targetId + memberList.get(i).getUser_id();
                            isFirst = false;
                        } else {
                            targetId = targetId + ",";
                            targetId = targetId + memberList.get(i).getUser_id();
                        }
                    }
                }
            }
            if (TextUtils.isEmpty(targetId)) {
                ToastUtils.showCentetImgToast(GroupMemberActivity.this, "请选择操作对象");
                return;
            }
            if (type == TYPE_NO_TALK) {
                noTalk(targetId, 1);
            }
            if (type == TYPE_REMOVE_FROM_BLACK_LIST) {
                noTalk(targetId, 0);
            }
            if (type == TYPE_NO_DELETE) {
                bootCircle(targetId);
            }
        }
        if (id == R.id.bt_return) {
            finish();
        }
    }

    //action 1 禁言 action 0 恢复
    private void noTalk(String targetId, int action) {
        showBookingToast(1);
        RequestManager.getInstance().setBlack(action, targetId, circleId, new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                dismissBookingToast();
                if (type == TYPE_NO_TALK) {
                    getGroupMember(circleId);
                }
                if (type == TYPE_REMOVE_FROM_BLACK_LIST) {
                    getBlackList(circleId);
                }
                if (type == TYPE_NO_DELETE) {
                    getGroupMember(circleId);
                }
                ToastUtils.showCentetImgToast(GroupMemberActivity.this, "操作成功");
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(GroupMemberActivity.this, msg);
            }
        });
    }


}
