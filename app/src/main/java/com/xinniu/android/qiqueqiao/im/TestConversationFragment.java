//package com.xinniu.android.qiqueqiao.im;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
////import android.support.v7.widget.LinearLayoutManager;
////import androidx.recyclerview.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.xinniu.android.qiqueqiao.R;
//import com.xinniu.android.qiqueqiao.activity.AdsActivity;
//import com.xinniu.android.qiqueqiao.activity.CircleInfoActivity;
//import com.xinniu.android.qiqueqiao.activity.CommentLanguageActivity;
//import com.xinniu.android.qiqueqiao.activity.SplashTwoActivity;
//import com.xinniu.android.qiqueqiao.adapter.SimpleText2Adapter;
//import com.xinniu.android.qiqueqiao.bean.LLSimpleTextBean;
//import com.xinniu.android.qiqueqiao.customs.qldialog.InputCommentLanguageDialog;
//import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
//import com.xinniu.android.qiqueqiao.dialog.CircleInfoMoreDialog;
//import com.xinniu.android.qiqueqiao.dialog.PushNotifyDialog;
//import com.xinniu.android.qiqueqiao.dialog.PushpokeTimeDialog;
//import com.xinniu.android.qiqueqiao.request.RequestManager;
//import com.xinniu.android.qiqueqiao.request.callback.GetCommlySentenceCallback;
//import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
//import com.xinniu.android.qiqueqiao.utils.ComUtils;
//import com.xinniu.android.qiqueqiao.utils.PreferenceHelper;
//import com.xinniu.android.qiqueqiao.utils.TimeUtils;
//import com.xinniu.android.qiqueqiao.utils.ToastUtils;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.List;
//
//
//import io.rong.imkit.conversation.ConversationFragment;
//import io.rong.imkit.conversation.extension.RongExtension;
//
//
///**
// * Created by Z.LL on 2019/3/8.
// * project : newbridge--- android xs
// */
//
//public class TestConversationFragment extends ConversationFragment {
//    private RongExtension rongExtension;
//    private TextView leftSessionBtn;//常用语按钮
//    private RecyclerView myListView;//常用语
//    private EditText editText;//输入框
//    private View mEditTextLayout;//输入框布局view
//    private LinearLayout mRlayoutCommonLanguage;//常用语布局
//    private LinearLayout mImageManage;
//    private TextView mTvAdd;
//    private View view_bg;
//    private SimpleText2Adapter adapter;
//    private AutoRefreshListView autoRefreshListView;
//    private PushNotifyDialog notifyDialog;
//    public Handler handler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 101://相应常用语的点击事件
//                    if (editText != null) {
//                        editText.setText("" + msg.obj);
//                        editText.requestFocus();
//                        editText.setSelection(editText.getText().length());
//                        leftSessionBtn.setText("常用语");
//                        leftSessionBtn.setBackgroundResource(R.drawable.bg_rc_comment_language);
//                        mRlayoutCommonLanguage.setVisibility(View.GONE);
//                        Class<RongExtension> cls = RongExtension.class;
//
//                        try {
//                            Method method = cls.getDeclaredMethod("showInputKeyBoard");
//                            method.setAccessible(true);
//                            method.invoke(rongExtension);
//
//                        } catch (NoSuchMethodException e) {
//                            e.printStackTrace();
//                        } catch (IllegalAccessException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        } catch (IllegalArgumentException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        } catch (InvocationTargetException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                    }
//            }
//        }
//    };
//    View pluginToggle;
//    Button audioInputToggle;
//    List<LLSimpleTextBean> mList = new ArrayList<LLSimpleTextBean>();//常用语
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = super.onCreateView(inflater, container, savedInstanceState);
//        notifyDialog = new PushNotifyDialog(getActivity(), R.style.QLDialog);
//        rongExtension = (RongExtension) v.findViewById(io.rong.imkit.R.id.rc_extension);
//        view_bg = (View) v.findViewById(io.rong.imkit.R.id.view_bg);
//        autoRefreshListView = (AutoRefreshListView) v.findViewById(io.rong.imkit.R.id.rc_list);
//        //  rc_container_layout    rc_ext_extension_bar  EditText  rc_edit_text  rc_plugin_toggle
//        myListView = (RecyclerView) v.findViewById(R.id.myListView);
//        mRlayoutCommonLanguage = (LinearLayout) v.findViewById(R.id.rlayout_common_language);
//        mImageManage = (LinearLayout) v.findViewById(R.id.llayout_manage);
//        mTvAdd = (TextView) v.findViewById(R.id.tv_add);
//        audioInputToggle = (Button) v.findViewById(R.id.rc_audio_input_toggle); //按住说话按钮
//
//        pluginToggle = v.findViewById(R.id.rc_plugin_toggle);
//        final ImageView voiceToggle = (ImageView) v.findViewById(R.id.rc_voice_toggle);
//        final ImageView emoticonToggle = (ImageView) v.findViewById(R.id.rc_emoticon_toggle);
//        editText = (EditText) v.findViewById(R.id.rc_edit_text);
//
//        adapter = new SimpleText2Adapter(getActivity(), handler, mList);
//        myListView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        //  myListView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
//        myListView.setAdapter(adapter);
//        //常用语
//        mRlayoutCommonLanguage.setVisibility(View.GONE);
//        leftSessionBtn = (TextView) v.findViewById(R.id.rc_session_saved);
//        leftSessionBtn.setText("常用语");
//        leftSessionBtn.setBackgroundResource(R.drawable.bg_rc_comment_language);
//
//        //常用语按钮
//        leftSessionBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (leftSessionBtn.getText().toString().equals("常用语")) {
//                    leftSessionBtn.setText("");
//                    leftSessionBtn.setBackgroundResource(R.mipmap.comment_language_ex);
//                    //目的：将录入框设置为输入状态
//                    //清空文本输入框
//                    editText.setText("");
//                    //收起扩展框
//                    rongExtension.collapseExtension();
//                    autoRefreshListView.post(new Runnable() {
//                        public void run() {
//                            // Log.i("oooooooo999", autoRefreshListView.getAdapter().getCount()-1 + "");
//                            autoRefreshListView.setSelection(autoRefreshListView.getAdapter().getCount() - 1);
//                        }
//                    });
//                    //切换成输入框状态,利用反射
//                    Class<RongExtension> cls = RongExtension.class;
//                    Method setShowSoftInputOnFocus;
//                    try {
//                        setShowSoftInputOnFocus = cls.getDeclaredMethod("hideVoiceInputToggle");
//                        setShowSoftInputOnFocus.setAccessible(true);
//                        setShowSoftInputOnFocus.invoke(rongExtension);
//                        Field declaredFields = cls.getDeclaredField("mEditTextLayout");
//                        declaredFields.setAccessible(true);
//                        mEditTextLayout = (View) declaredFields.get(rongExtension);
//                        mEditTextLayout.setVisibility(View.VISIBLE);
//                        //处理表情
//
//
//                    } catch (NoSuchMethodException e) {
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    } catch (IllegalArgumentException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    } catch (InvocationTargetException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    } catch (NoSuchFieldException e) {
//                        e.printStackTrace();
//                    }
//                    //显示常用语
//                    mRlayoutCommonLanguage.setVisibility(View.VISIBLE);
//
//                } else {
//                    //收起
//                    leftSessionBtn.setText("常用语");
//                    leftSessionBtn.setBackgroundResource(R.drawable.bg_rc_comment_language);
//                    mRlayoutCommonLanguage.setVisibility(View.GONE);
//                    editText.setText("");
//                }
//
//            }
//
//
//        });
//
//        voiceToggle.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                //目的：隐藏常用语
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    if (leftSessionBtn.getText().toString().equals("常用语")) {
//
//                    } else {
//                        //因为无法监听voiceToggle的点击状态，又不能取代原因click事件,只能通过OnTouch间接处理
//                        leftSessionBtn.setText("常用语");
//                        leftSessionBtn.setBackgroundResource(R.drawable.bg_rc_comment_language);
//                        mRlayoutCommonLanguage.setVisibility(View.GONE);
//                        editText.setText("");
//                    }
//
//                }
//                return false;
//            }
//        });
//        emoticonToggle.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                //目的：隐藏常用语
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    if (leftSessionBtn.getText().toString().equals("常用语")) {
//
//                    } else {
//                        //因为无法监听voiceToggle的点击状态，又不能取代原因click事件,只能通过OnTouch间接处理
//                        leftSessionBtn.setText("常用语");
//                        leftSessionBtn.setBackgroundResource(R.drawable.bg_rc_comment_language);
//                        mRlayoutCommonLanguage.setVisibility(View.GONE);
//                        editText.setText("");
//                    }
//
//                }
//                return false;
//            }
//        });
//        mImageManage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //管理
//                getContext().startActivity(new Intent(getContext(), CommentLanguageActivity.class));
//            }
//        });
//
//        //触摸聊天界面隐藏常用语界面
//        view_bg.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (mRlayoutCommonLanguage != null && mRlayoutCommonLanguage.getVisibility() == View.VISIBLE) {
//                    leftSessionBtn.setText("常用语");
//                    leftSessionBtn.setBackgroundResource(R.drawable.bg_rc_comment_language);
//                    mRlayoutCommonLanguage.setVisibility(View.GONE);
//                }
//                return false;
//            }
//        });
//
//
//        mTvAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //添加
//                InputCommentLanguageDialog inputCommentLanguageDialog = new InputCommentLanguageDialog(getContext(), R.style.QLDialog1, "");
//                inputCommentLanguageDialog.setmShareCallback(new InputCommentLanguageDialog.SaveCallback() {
//                    @Override
//                    public void onSaveCall(String str) {
//
//                        LLSimpleTextBean llSimpleTextBean = new LLSimpleTextBean(str);
//                        mList.add(0, llSimpleTextBean);
//                        Gson gson = new Gson();
//                        String json = gson.toJson(mList);
//                        UserInfoHelper.getIntance().setCL(json);
//                        adapter.notifyDataSetChanged();
//                    }
//                });
//                inputCommentLanguageDialog.show();
//            }
//        });
//
//        return v;
//    }
//
//    @Override
//    public void onEditTextClick(EditText editText) {
//        super.onEditTextClick(editText);
//        if (mRlayoutCommonLanguage != null && mRlayoutCommonLanguage.getVisibility() == View.VISIBLE) {
//            leftSessionBtn.setText("常用语");
//            leftSessionBtn.setBackgroundResource(R.drawable.bg_rc_comment_language);
//            mRlayoutCommonLanguage.setVisibility(View.GONE);
//        }
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (UserInfoHelper.getIntance().getClList().length() > 0) {
//            //判断有缓存
//            String json = UserInfoHelper.getIntance().getClList();
//            Gson gson = new Gson();
//            List<LLSimpleTextBean> list = gson.fromJson(json, new TypeToken<List<LLSimpleTextBean>>() {
//            }.getType());
//            mList.clear();
//            mList.addAll(list);
//            adapter.notifyDataSetChanged();
//        } else {
//            RequestManager.getInstance().getCommonlySentenceList(new GetCommlySentenceCallback() {
//                @Override
//                public void onSuccess(List<LLSimpleTextBean> list) {
//                    Gson gson = new Gson();
//                    String json = gson.toJson(list);
//                    UserInfoHelper.getIntance().setCL(json);
//                    mList.clear();
//                    mList.addAll(list);
//                    adapter.notifyDataSetChanged();
//                }
//
//                @Override
//                public void onFailed(int code, String msg) {
//                    ToastUtils.showCentetToast(getContext(), msg);
//                }
//            });
//        }
//    }
//
//
//    /**
//     * 点击 “+” 号区域, 回调中携带 ViewGroup
//     *
//     * @param v              “+” 号 view 实例
//     * @param extensionBoard 用于展示 plugin 的 ViewGroup
//     */
//    @Override
//    public void onPluginToggleClick(View v, ViewGroup extensionBoard) {
//        super.onPluginToggleClick(v, extensionBoard);
//
//        //当“+” 号区域点击的时候，隐藏常用语
//        if (mRlayoutCommonLanguage != null && mRlayoutCommonLanguage.getVisibility() == View.VISIBLE) {
//            leftSessionBtn.setText("常用语");
//            leftSessionBtn.setBackgroundResource(R.drawable.bg_rc_comment_language);
//            editText.setText("");
//            mRlayoutCommonLanguage.setVisibility(View.GONE);
//
//        }
//        //       mVoiceToggle.setVisibility(View.GONE);
//    }
//
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        //防止选择常用语没有发送再次进去显示内容
//        editText.setText("");
//    }
//
//    @Override
//    public void onSendToggleClick(View v, String text) {
//        super.onSendToggleClick(v, text);
//        //判断是否开启权限，一天只能弹一次
//        if (!ComUtils.isNotificationEnabled(getActivity())) {//没有开启权限
//            int count = PreferenceHelper.readInt(getActivity(), "chat_prefrence",
//                    "COUNT", 0);
//            long time = PreferenceHelper.readLong(getActivity(), "chat_prefrence",
//                    "TIME", 0);
//            if (count == 0 && time == 0) {
//                //弹窗
//                if (notifyDialog != null && !notifyDialog.isShowing()) {
//                    notifyDialog.show();
//                }
//                //这里重新生成一个sp，是因为原来有的sp在退出登录时会清空；
//                PreferenceHelper.write(getActivity(), "chat_prefrence",
//                        "COUNT", count + 1);
//                PreferenceHelper.write(getActivity(), "chat_prefrence",
//                        "TIME", System.currentTimeMillis());
//            } else {
//                if (count >= 1) {
//                    if (TimeUtils.differentDaysByMillisecond(System.currentTimeMillis(), time) <= 1) {
//                        //是今天已经弹窗了
//                    } else {
//                        //不是今天
//                        //弹窗
//                        if (notifyDialog != null && !notifyDialog.isShowing()) {
//                            notifyDialog.show();
//                        }
//                        PreferenceHelper.write(getActivity(), "chat_prefrence",
//                                "COUNT", count + 1);
//                        PreferenceHelper.write(getActivity(), "chat_prefrence",
//                                "TIME", System.currentTimeMillis());
//                    }
//                }
//
//
//            }
//        }
//    }
//
//
//}
