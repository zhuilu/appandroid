package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xinniu.android.qiqueqiao.OtherUserInfoDao;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CircleFragmentAdapter;
import com.xinniu.android.qiqueqiao.adapter.base.MyCircleAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.base.BaseApp;
import com.xinniu.android.qiqueqiao.bean.CircleBean;
import com.xinniu.android.qiqueqiao.bean.MyCircleBean;
import com.xinniu.android.qiqueqiao.bean.OtherUserInfo;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetOtherUserInfoCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import retrofit2.Call;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * Created by qinlei
 * Created on 2017/12/18
 * Created description :
 */

public class MineCircleActivity extends BaseActivity {
    @BindView(R.id.tb_return)
    ImageView mTbReturn;
    @BindView(R.id.tb_title)
    TextView mTbTitle;
    @BindView(R.id.tb_menu)
    ImageView mTbMenu;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Call mCall;
    private MyCircleAdapter mAdapter;
    private List<MyCircleBean> mData = new ArrayList<>();


    public static void start(Context context) {
        Intent starter = new Intent(context, MineCircleActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_circle;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initTitleBar();
        initAdapter();
        getMyCircleList();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MyCircleAdapter(this, mData);
        recyclerView.setAdapter(mAdapter);
    }

    private void getMyCircleList() {
        RequestManager.getInstance().getMyCircleList(new RequestCallback<List<MyCircleBean>>() {
            @Override
            public void requestStart(Call call) {
                mCall = call;
                showBookingToast(1);
            }

            @Override
            public void onSuccess(List<MyCircleBean> circleBeen) {
                if (circleBeen.size() == 0) {
                    ToastUtils.showCentetImgToast(MineCircleActivity.this, "暂时未任何加入圈子");
                } else {
                    mData.clear();
                    mData.addAll(circleBeen);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(MineCircleActivity.this, msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });
        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                if (conversations == null){
                    return;
                }
                for (int i =0 ;i < conversations.size();i++){
                    final String targetId = conversations.get(i).getTargetId();
                    RequestManager.getInstance().showUserInfo(Integer.valueOf(targetId), new GetOtherUserInfoCallback() {
                        @Override
                        public void onSuccess(OtherUserInfo bean) {
//                            final OtherUserInfoDao dao = BaseApp.getApplication().getDaoSession().getOtherUserInfoDao();
//                            List<OtherUserInfo> list = dao.queryBuilder().offset(0).limit(1).orderAsc(OtherUserInfoDao.Properties.User_id)
//                                    .where(OtherUserInfoDao.Properties.User_id.eq(targetId)).build().list();
//                            if (list.size()>0){
//                                bean.setId(null);
//                                dao.update(bean);
//                            }else {
//                                bean.setId(null);
//                                dao.insert(bean);
//                            }
                        }

                        @Override
                        public void onFailed(int code, String msg) {

                        }
                    });
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Logger.i("lzq","onError: "+errorCode);
            }
        },Conversation.ConversationType.GROUP);
    }

    private void initTitleBar() {
        topStatusBar(true);
        mTbReturn.setImageResource(R.mipmap.chevron);
        mTbTitle.setText(R.string.mine_circle);
        mTbReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
