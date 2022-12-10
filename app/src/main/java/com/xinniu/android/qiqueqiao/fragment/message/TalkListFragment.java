package com.xinniu.android.qiqueqiao.fragment.message;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;

import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.FriendLxActivity;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;

import butterknife.OnClick;
import io.rong.imlib.model.Conversation;

/**
 * Created by BDXK on 2017/11/7.
 * 主界面单元(消息界面)
 */

public class TalkListFragment extends LazyBaseFragment {

    //        @BindView(R.id.talk_list_rv)
//    RecyclerView mRecyclerView;
//    @BindView(R.id.empty_tv)
//    TextView emptyTv;
    QQQConversationListFragment fragment;

    @Override
    public void onResume() {
        super.onResume();
        initView();

    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_message_talklist;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        initView();

    }


    public void initView() {
        fragment = new QQQConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                //设置私聊会话，该会话聚合显示
                .build();
//        fragment.setUri(uri);//设置 ConverssationListFragment 的显示属性
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rong_content, fragment);
        transaction.commit();
    }

    @Override
    protected void lazyLoad() {
        initView();
    }

    public void srcollToUnReadMsg() {
        if (fragment != null) {
            fragment.srcollToUnReadMsg();
        }
    }

    public void setNumFragment(String systemnum, String intactnum) {
        if (fragment != null) {
            fragment.setNumFragment(systemnum, intactnum);
        }
    }


    @OnClick({R.id.bmessage_friendtv})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.bmessage_friendtv:
                MobclickAgent.onEvent(getContext(), "message_friendList");//统计点击次数
                FriendLxActivity.start(mContext, FriendLxActivity.FRIENDLIST);
                break;
            default:
                break;
        }
    }
}
