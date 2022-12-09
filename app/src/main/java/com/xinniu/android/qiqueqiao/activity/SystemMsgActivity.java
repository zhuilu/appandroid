package com.xinniu.android.qiqueqiao.activity;

import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.SystemMsgAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.NewsV2Bean;
import com.xinniu.android.qiqueqiao.bean.SystemMsgBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetNewsV2Callback;
import com.xinniu.android.qiqueqiao.request.callback.GetSystemMsgCallback;
import com.xinniu.android.qiqueqiao.request.callback.GroupHandleCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lzq on 2017/12/25.
 * 系统消息页面
 */

public class SystemMsgActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.bt_return)
    ImageView closeBt;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.system_smartrefresh)
    SmartRefreshLayout systemSmartrefresh;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    private int page = 1;
    private int newnum = 0;

    private List<SystemMsgBean> mList = new ArrayList<>();

    private SystemMsgAdapter mSystemMsgAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_system_msg;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        mSystemMsgAdapter = new SystemMsgAdapter(SystemMsgActivity.this, mList);
        mSystemMsgAdapter.setGoToOperation(new SystemMsgAdapter.goToOperation() {
            @Override
            public void goToAgree(int id, int position) {
                goToOperation(id, 1, position);
            }

            @Override
            public void goToRefuse(int id, int position) {
                goToOperation(id, 2, position);
            }

            @Override
            public void goToGroupDetail(int groupId, int type) {
                if (type == 1) {
                    GroupMessageActivity.start(SystemMsgActivity.this, groupId, "mygroup");
                } else {
                    GroupDataActivity.start(SystemMsgActivity.this, groupId);
                }
            }

            @Override
            public void goToPerson(String uid) {
                PersonCentetActivity.start(SystemMsgActivity.this, uid);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(SystemMsgActivity.this, LinearLayoutManager.VERTICAL, false);
        manager.setAutoMeasureEnabled(true);
        rv.setLayoutManager(manager);
        rv.setAdapter(mSystemMsgAdapter);
        RequestManager.getInstance().getNewsV2(new GetNewsV2Callback() {
            @Override
            public void onSuccess(NewsV2Bean bean) {
                newnum = bean.getSystem().getNum();
                buildData(page);
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });


        systemSmartrefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
                                                     @Override
                                                     public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                                                         page++;
                                                         buildData(page);
                                                     }
                                                 }

        );
    }

    private void goToOperation(int joinId, int type, final int position) {
        RequestManager.getInstance().grouphandle(joinId, type, "", new GroupHandleCallback() {
            @Override
            public void onSuccess(SystemMsgBean data, String msg) {
                ToastUtils.showCentetToast(SystemMsgActivity.this, msg);
                data.setItemType(SystemMsgBean.GROUPTYPE);
                mList.set(position, data);
                mSystemMsgAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(SystemMsgActivity.this, msg);
            }
        });
    }

    private void buildData(final int page) {
        systemSmartrefresh.setEnableRefresh(false);
        RequestManager.getInstance().getMessageList(page, new GetSystemMsgCallback() {
            @Override
            public void onSuccess(List<SystemMsgBean> list) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getType() == 4 || list.get(i).getType() == 5) {
                        list.get(i).setItemType(SystemMsgBean.GROUPTYPE);
                    }  else {
                        list.get(i).setItemType(SystemMsgBean.COMMMON);
                    }
                }
                if (page == 1) {
                    mList.clear();
                    mList.addAll(list);
                    if (list.size() == 0) {
                        yperchRl.setVisibility(View.VISIBLE);
                    } else {
                        yperchRl.setVisibility(View.GONE);
                    }
                } else {
                    mList.addAll(list);
                }

                for (int i = 0; i < mList.size(); i++) {
                    if (i < newnum) {
                        mList.get(i).setNew(true);
                    } else {
                        mList.get(i).setNew(false);
                    }
                }


                stopRefresh();
                mSystemMsgAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }


    @OnClick({R.id.bt_return})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_return) {
            finish();
        }
    }

    private void stopRefresh() {
        if (systemSmartrefresh.isEnableLoadMore()) {
            systemSmartrefresh.finishLoadMore(true);
        }
    }


}
