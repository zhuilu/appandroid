package com.xinniu.android.qiqueqiao.fragment.message;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
//import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.AddGroupActivity;
import com.xinniu.android.qiqueqiao.activity.CircleActivity;
import com.xinniu.android.qiqueqiao.activity.CircleNewActivity;
import com.xinniu.android.qiqueqiao.activity.CommunicationRecordActivity;
import com.xinniu.android.qiqueqiao.activity.ConnectionActivity;
import com.xinniu.android.qiqueqiao.activity.InteractInformActivity;
import com.xinniu.android.qiqueqiao.activity.SystemMsgActivity;
import com.xinniu.android.qiqueqiao.adapter.QQQConversationListAdapter;
import com.xinniu.android.qiqueqiao.bean.NewsBean;
import com.xinniu.android.qiqueqiao.bean.NewsV2Bean;
import com.xinniu.android.qiqueqiao.receiver.JGPushReceiver;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetNewsV2Callback;
import com.xinniu.android.qiqueqiao.request.callback.NewsCallback;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.ReflectUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.utils.TokePhotoUtils;
import com.xinniu.android.qiqueqiao.zxing.activity.CaptureActivity;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.widget.AutoRefreshListView;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.push.RongPushClient;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by lzq on 2017/12/18.
 */

public class QQQConversationListFragment extends ConversationListFragment {
    private ListView mList;
    //    private View view;
//    private View result;
//    TextView content;
//    TextView time;
    private Context context;
    QQQConversationListAdapter mAdapter;
    public TextView redTv;
    public TextView interactTv;

    @Override
    public ConversationListAdapter onResolveAdapter(Context context) {
        mAdapter = new QQQConversationListAdapter(context);
        this.context = context;
        return new QQQConversationListAdapter(context);
    }

    public void srcollToUnReadMsg() {
        mList.smoothScrollToPosition(mAdapter.srcollToUnReadMsg());
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mList = (ListView) view.findViewById(io.rong.imkit.R.id.rc_list);
        LinearLayout fcLl = (LinearLayout) view.findViewById(io.rong.imkit.R.id.rc_conversation_list_empty_layout);
        fcLl.setVisibility(View.GONE);
        return view;
    }

    @Override
    protected List<View> onAddHeaderView() {
        List<View> headerViews = super.onAddHeaderView();
        SetNewsBroadCast broadCast = new SetNewsBroadCast();
        //为聊天listview添加头
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.view_message_head, null);
        RelativeLayout systemRl = (RelativeLayout) headView.findViewById(R.id.system_head_Rl);

        redTv = (TextView) headView.findViewById(R.id.view_headred_point);
        interactTv = (TextView) headView.findViewById(R.id.view_interact_point);
        final TextView headrpoint = (TextView) headView.findViewById(R.id.view_headr_point);
//
//        RongIM.getInstance().getUnreadCount(new RongIMClient.ResultCallback<Integer>() {
//            @Override
//            public void onSuccess(Integer integer) {
//                if (integer == 0){
//                    headrpoint.setVisibility(View.GONE);
//                }else {
//                    headrpoint.setVisibility(View.VISIBLE);
//                    headrpoint.setText(integer+"");
//                }
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//
//            }
//        }, Conversation.ConversationType.GROUP);
        RelativeLayout isPushRl = (RelativeLayout) headView.findViewById(R.id.is_push);
        TextView goToOpen = (TextView) headView.findViewById(R.id.bopen_push);

        if (ComUtils.isNotificationEnabled(getContext())) {
            ShowUtils.showViewVisible(isPushRl, View.GONE);
        } else {
            ShowUtils.showViewVisible(isPushRl, View.VISIBLE);
        }
        goToOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ComUtils.goToSet(context);
            }
        });


        systemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getContext(), "message_systemNews");//统计点击次数
                startActivity(new Intent(getContext(), SystemMsgActivity.class));
            }
        });
        RelativeLayout circleRl = (RelativeLayout) headView.findViewById(R.id.circle_head_Rl);
        circleRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RequestManager.getInstance().checkMyGroup(new AllResultDoCallback() {
//                    @Override
//                    public void onSuccess(String msg) {
//                        MobclickAgent.onEvent(getContext(), "message_groupNews");//统计点击次数
//                        Intent intent = new Intent(getContext(), CircleNewActivity.class);
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onFailed(int code, String msg) {
//                        AddGroupActivity.start(getContext());
//                    }
//                });
                //沟通记录
                CommunicationRecordActivity.start(getContext());


            }
        });
        RelativeLayout informRl = (RelativeLayout) headView.findViewById(R.id.inform_head_Rl);
        informRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getContext(), "message_activeNews");//统计点击次数
                InteractInformActivity.start(getContext());
            }
        });
        RelativeLayout headRl = (RelativeLayout) headView.findViewById(R.id.connect_head_Rl);
        headRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(getContext(), "message_people");//统计点击次数
                ConnectionActivity.start(getContext());
//

            }
        });

        broadCast.setSetNumCall(new SetNewsBroadCast.SetNumCall() {
            @Override
            public void setSysNum(String systemnum) {
                if (!TextUtils.isEmpty(systemnum)) {
                    if (systemnum.equals("0")) {
                        ShowUtils.showViewVisible(redTv, View.GONE);
                    } else {
                        ShowUtils.showViewVisible(redTv, View.VISIBLE);
                        ShowUtils.showTextPerfect(redTv, systemnum);
                    }
                }

            }

            @Override
            public void setIntNum(String intactnum) {
                if (!TextUtils.isEmpty(intactnum)) {
                    if (intactnum.equals("0")) {
                        ShowUtils.showViewVisible(interactTv, View.GONE);
                    } else {
                        ShowUtils.showViewVisible(interactTv, View.VISIBLE);
                        ShowUtils.showTextPerfect(interactTv, intactnum);
                    }
                }
            }
        });


        headerViews.add(headView);

        return headerViews;
    }


    public void setNumFragment(String systemnum, String intactnum) {
        if (!TextUtils.isEmpty(systemnum)) {
            if (systemnum.equals("0")) {
                ShowUtils.showViewVisible(redTv, View.GONE);
            } else {
                ShowUtils.showViewVisible(redTv, View.VISIBLE);
                ShowUtils.showTextPerfect(redTv, systemnum);
            }
        }
        if (!TextUtils.isEmpty(intactnum)) {
            if (intactnum.equals("0")) {
                ShowUtils.showViewVisible(interactTv, View.GONE);
            } else {
                ShowUtils.showViewVisible(interactTv, View.VISIBLE);
                ShowUtils.showTextPerfect(interactTv, intactnum);
            }
        }
    }


    public static class SetNewsBroadCast extends BroadcastReceiver {
        public interface SetNumCall {
            void setSysNum(String systemnum);

            void setIntNum(String intactnum);
        }

        private static SetNumCall setNumCall;

        public void setSetNumCall(SetNumCall setNumCall) {
            SetNewsBroadCast.setNumCall = setNumCall;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String systemnum = intent.getStringExtra(JGPushReceiver.SYSTEMNUM);
            String intactnum = intent.getStringExtra(JGPushReceiver.INTACTNUM);
            if (!TextUtils.isEmpty(systemnum)) {
                if (setNumCall != null) {
                    setNumCall.setSysNum(systemnum);
                }
            }
            if (!TextUtils.isEmpty(intactnum)) {
                if (setNumCall != null) {
                    setNumCall.setIntNum(intactnum);
                }
            }


        }

    }


}
