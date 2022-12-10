package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.AddGroupClassifyAdapter;
import com.xinniu.android.qiqueqiao.adapter.AddGroupListAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.AddGroupClassifyBean;
import com.xinniu.android.qiqueqiao.bean.AddGroupListBean;
import com.xinniu.android.qiqueqiao.customs.ClearEditText;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AddGroupClassifyCallback;
import com.xinniu.android.qiqueqiao.request.callback.AddGroupListCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/9/27.
 * 群组列表(加入群组)
 */

public class AddGroupActivity extends BaseActivity {

    @BindView(R.id.mSearch_groupEt)
    ClearEditText mSearchGroupEt;
    @BindView(R.id.mclassifyRecycler)
    RecyclerView mclassifyRecycler;
    @BindView(R.id.mgroupRecycler)
    RecyclerView mgroupRecycler;
    @BindView(R.id.addgroupSwipe)
    SmartRefreshLayout addgroupSwipe;
    @BindView(R.id.classifyRecyclerRl)
    RelativeLayout classifyRecyclerRl;
    @BindView(R.id.bcanceltv)
    TextView bcanceltv;
    private List<AddGroupClassifyBean> classifyList = new ArrayList<>();
    private List<AddGroupListBean.ListBean> groupList = new ArrayList<>();
    private String keyword = "";
    private int category = 0;
    private int page = 1;
    private AddGroupClassifyAdapter groupClassifyAdapter;
    private AddGroupListAdapter groupListAdapter;


    public static void start(Context context) {
        Intent intent = new Intent(context, AddGroupActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_addgroup;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        initModule();
        loadTypes();
        loadDatas(keyword, category, page, 1);
        mSearchGroupEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    final String tvName = mSearchGroupEt.getText().toString();
                    if (TextUtils.isEmpty(tvName)) {
                        ToastUtils.showCentetImgToast(AddGroupActivity.this, "请输入搜索内容");
                    } else {
                        page = 1;
                        loadDatas(mSearchGroupEt.getText().toString().trim(), category, page, 2);
                        bcanceltv.setVisibility(View.VISIBLE);
                        classifyRecyclerRl.setVisibility(View.GONE);
                        showBookingToast(2);
                        return true;
                    }
                }
                return false;
            }
        });
        addgroupSwipe.setEnableRefresh(false);
        addgroupSwipe.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                loadDatas(keyword, category, page, 1);
            }
        });
    }

    private void initModule() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        groupClassifyAdapter = new AddGroupClassifyAdapter(classifyList);
        mclassifyRecycler.setLayoutManager(layoutManager);
        mclassifyRecycler.setAdapter(groupClassifyAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        groupListAdapter = new AddGroupListAdapter(R.layout.item_addgroup_list, groupList);
        groupListAdapter.setGoToGroup(new AddGroupListAdapter.goToGroup() {
            @Override
            public void goToGroup(int groupId) {
                GroupMessageActivity.start(AddGroupActivity.this, groupId, "add");
            }
        });
        groupClassifyAdapter.setSetGroupClassifyId(new AddGroupClassifyAdapter.setGroupClassifyId() {
            @Override
            public void setGroupClassifyId(int groupClassifyId, int position) {
                category = groupClassifyId;
                for (int i = 0; i < classifyList.size(); i++) {
                    classifyList.get(i).setCheck(false);
                }
                classifyList.get(position).setCheck(true);

                loadDatas(keyword, category, page, 2);
                groupClassifyAdapter.notifyDataSetChanged();
            }
        });
        mgroupRecycler.setLayoutManager(manager);
        mgroupRecycler.setAdapter(groupListAdapter);
    }

    private void loadTypes() {
        RequestManager.getInstance().getCategoryList(4, new AddGroupClassifyCallback() {
            @Override
            public void onSuccess(List<AddGroupClassifyBean> item) {
                if (item != null && item.size() > 0) {
                    classifyList.clear();
                    classifyList.addAll(item);
                    for (int i = 0; i < classifyList.size(); i++) {
                        classifyList.get(i).setItemType(AddGroupClassifyBean.COMMMON);
                    }
                    if (classifyList!=null&&classifyList.size()>0&&classifyList.get(0).getCategory()!=0) {
                        classifyList.add(0, new AddGroupClassifyBean(0, "推荐", AddGroupClassifyBean.RECOMMEND, true));
                    }

                    groupClassifyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(AddGroupActivity.this, msg);
            }
        });
    }

    private void loadDatas(String keyword, int category, final int page, int isSeleced) {
        if (isSeleced==1) {
            showBookingToast(1);
        } else if (isSeleced==2){
            showBookingToast(2);
        }else {

        }
        RequestManager.getInstance().getGroupList(keyword, category, page, new AddGroupListCallback() {
            @Override
            public void onSuccess(AddGroupListBean item) {
                dismissBookingToast();
                if (item != null) {
                    if (page == 1) {
                        groupList.clear();
                    }
                    groupList.addAll(item.getList());
                    groupListAdapter.notifyDataSetChanged();
                }
                if (page == 1) {
                    groupList.clear();
                    if (item.getList().size() == 0) {
//                        yperchRl.setVisibility(View.VISIBLE);
                        groupListAdapter.removeAllFooterView();
                        addgroupSwipe.setEnableLoadMore(false);
                    } else {
//                        yperchRl.setVisibility(View.GONE);
                        if (item.getHasMore() == 0) {
                            groupListAdapter.setFooterView(footView);
                            addgroupSwipe.setEnableLoadMore(false);
                        } else {
                            groupListAdapter.removeAllFooterView();
                            addgroupSwipe.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (item.getHasMore() == 0) {
                        groupListAdapter.setFooterView(footView);
                        addgroupSwipe.setEnableLoadMore(false);
                    } else {
                        groupListAdapter.removeAllFooterView();
                        addgroupSwipe.setEnableLoadMore(true);
                    }
                }
                groupList.addAll(item.getList());
                groupListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(AddGroupActivity.this, msg);
            }
        });

    }


    @OnClick({R.id.bt_finish,R.id.bcanceltv})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bcanceltv:
                bcanceltv.setVisibility(View.GONE);
                classifyRecyclerRl.setVisibility(View.VISIBLE);
                keyword = "";
                category = 0;
                page = 1;
                loadTypes();
                mSearchGroupEt.setText(keyword);
                loadDatas(keyword, category, page, 3);
                break;
            default:
                break;
        }

    }


}
