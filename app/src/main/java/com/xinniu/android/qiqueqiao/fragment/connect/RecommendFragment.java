package com.xinniu.android.qiqueqiao.fragment.connect;

import android.content.DialogInterface;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.adapter.RecommendAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.GoFriendApplyBean;
import com.xinniu.android.qiqueqiao.bean.RecommendBean;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetRecommendListCallback;
import com.xinniu.android.qiqueqiao.request.callback.GoFriendApplyCallback;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 消息-人脉-推荐的人
 * Created by yuchance on 2019/2/12.
 */

public class RecommendFragment extends LazyBaseFragment {

    @BindView(R.id.mrecycler)
    RecyclerView mrecycler;

    private List<RecommendBean> datas = new ArrayList<>();
    private RecommendAdapter adapter;


    public static RecommendFragment newInstance() {

        Bundle args = new Bundle();

        RecommendFragment fragment = new RecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mrecycler.setLayoutManager(manager);
        adapter = new RecommendAdapter(mContext, R.layout.item_connect_person, datas);
        mrecycler.setAdapter(adapter);
        buildData();
        adapter.setSetOnClick(new RecommendAdapter.setOnClick() {

            @Override
            public void setOnClick(final int id, final String name, final String position, final String headPic, final int isVip) {
                AlertDialogUtils.AlertDialog(mContext, "添加好友", "添加好友将消耗1次沟通权限，加为好友即可无限制沟通", "取消", "添加好友", new AlertDialogUtils.setOnClick() {
                    @Override
                    public void setLeftOnClick(DialogInterface dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void setRightOnClick(DialogInterface dialog) {
                        goToAddFriend(id, name, position, headPic, isVip);
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void setItemOnClick(int id) {
                PersonCentetActivity.start(mContext, id + "");
            }
        });
    }

    private void buildData() {
        RequestManager.getInstance().getRecommendList(new GetRecommendListCallback() {
            @Override
            public void onSuccess(List<RecommendBean> bean) {
                datas.clear();
                datas.addAll(bean);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(mContext, msg);
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    private void goToAddFriend(final int toUserId, final String name, final String position, final String headPic, final int isVip) {
        RequestManager.getInstance().goFriendApply(toUserId, 2, new GoFriendApplyCallback() {
            @Override
            public void onSuccess(GoFriendApplyBean data, String msg) {
                buildData();
                //IMUtils.singleChatForResource(getActivity(), String.valueOf(toUserId), name, 0, position, headPic, isVip + "");

                ToastUtils.showCentetToast(mContext, msg);

            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(mContext, msg);
            }
        });
    }


}
