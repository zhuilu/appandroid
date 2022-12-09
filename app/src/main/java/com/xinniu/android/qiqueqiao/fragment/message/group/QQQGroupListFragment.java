package com.xinniu.android.qiqueqiao.fragment.message.group;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import io.rong.imkit.conversationlist.ConversationListAdapter;
import io.rong.imkit.conversationlist.ConversationListFragment;
//import io.rong.imkit.fragment.ConversationListFragment;
//import io.rong.imkit.widget.adapter.ConversationListAdapter;

/**
 * Created by yuchance on 2018/9/26.
 */

public class QQQGroupListFragment extends ConversationListFragment {
    private ListView mList;
    //    private View view;
//    private View result;
//    TextView content;
//    TextView time;
    private Context context;
    QQQGroupListAdapter mAdapter;

//    @Override
//    public ConversationListAdapter onResolveAdapter(Context context) {
//        mAdapter = new QQQGroupListAdapter(context);
//        this.context = context;
//        return new QQQGroupListAdapter(context);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mList = (ListView) view.findViewById(io.rong.imkit.R.id.rc_list);
//        LinearLayout fcLl = (LinearLayout) view.findViewById(io.rong.imkit.R.id.rc_conversation_list_empty_layout);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//        fcLl.setLayoutParams(params);

//        fcLl.setVisibility(View.GONE);
        return view;
    }
}
