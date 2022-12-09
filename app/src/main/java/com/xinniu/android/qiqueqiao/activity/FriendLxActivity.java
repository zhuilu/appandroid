package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.FriendLxAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.GetFriendListBean;
import com.xinniu.android.qiqueqiao.customs.ClearEditText;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.divider.FriendSuspensionDecoration;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetFriendListCallback;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.IndexBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imlib.model.Conversation;

/**
 * Created by yuchance on 2018/11/30.
 */

public class FriendLxActivity extends BaseActivity {
    @BindView(R.id.mlxlisttitle)
    TextView mlxlisttitle;
    @BindView(R.id.msearchet)
    ClearEditText msearchet;
    @BindView(R.id.mfriendlx_recycler)
    RecyclerView mfriendlxRecycler;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    @BindView(R.id.mfinishImg)
    ImageView mfinishImg;
    @BindView(R.id.mfinishTv)
    TextView mfinishTv;
    @BindView(R.id.bmessage_group)
    TextView bmessageGroup;

    private FriendSuspensionDecoration mDecoration;

    private List<GetFriendListBean.GroupBean> datas = new ArrayList<>();
    private List<GetFriendListBean.FBean> fdatas = new ArrayList<>();
    private String keywords;
    private FriendLxAdapter adapter;
    public final static int FRIENDLIST = 1;
    public final static int CHATACT = 2;
    public final static int COOPACT = 3;
    public final static int INVITEGROUP = 4;
    public final static int COOPPHOTO = 5;
    public final static String FROM_TYPE = "fromType";
    public final static String TARGET_ID = "targetId";
    public final static String RESOURCE_ID = "resourceId";

    public final static String GROUPID = "groupId";
    public final static String SENDPHOTO = "sendPhoto";

    private int fromType;
    private String targetId;
    private LinearLayoutManager layoutManager;
    private int resourceId;
    private int groupId;
    private String sendPhoto;


    public static void start(Context context, int type) {
        Intent intent = new Intent(context, FriendLxActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(FROM_TYPE, type);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void start(Context context, int type, String targetId) {
        Intent intent = new Intent(context, FriendLxActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(FROM_TYPE, type);
        bundle.putString(TARGET_ID, targetId);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void start(Context context, int type, int resourceId) {
        Intent intent = new Intent(context, FriendLxActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(FROM_TYPE, type);
        bundle.putInt(RESOURCE_ID, resourceId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void startx(Context context, int type, int groupId) {
        Intent intent = new Intent(context, FriendLxActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(FROM_TYPE, type);
        bundle.putInt(GROUPID, groupId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void startx(Context context, int type, String photoUri) {
        Intent intent = new Intent(context, FriendLxActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(FROM_TYPE, type);
        bundle.putString(SENDPHOTO, photoUri);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_friendlx;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initTitle();
        buildDatas(keywords, true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fromType = bundle.getInt(FROM_TYPE);
            if (bundle.getString(TARGET_ID) != null) {
                targetId = bundle.getString(TARGET_ID);
            }
            if (bundle.getInt(RESOURCE_ID) != 0) {
                resourceId = bundle.getInt(RESOURCE_ID);
            }
            if (bundle.getInt(GROUPID) != 0) {
                groupId = bundle.getInt(GROUPID);
            }
            if (!TextUtils.isEmpty(bundle.getString(SENDPHOTO))) {
                sendPhoto = bundle.getString(SENDPHOTO);
            }

        }
        if (fromType == FRIENDLIST) {
            mfinishImg.setVisibility(View.VISIBLE);
            mfinishTv.setVisibility(View.GONE);
        } else if (fromType == CHATACT) {
            mfinishImg.setVisibility(View.GONE);
            mfinishTv.setVisibility(View.VISIBLE);
        } else if (fromType == COOPACT) {
            mfinishImg.setVisibility(View.GONE);
            mfinishTv.setVisibility(View.VISIBLE);
        } else if (fromType == INVITEGROUP) {
            mfinishImg.setVisibility(View.GONE);
            mfinishTv.setVisibility(View.VISIBLE);
        } else if (fromType == COOPPHOTO) {
            mfinishImg.setVisibility(View.GONE);
            mfinishTv.setVisibility(View.VISIBLE);
        }
        msearchet.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keywords = msearchet.getText().toString();
                    if (TextUtils.isEmpty(keywords)) {
                        ToastUtils.showCentetImgToast(FriendLxActivity.this, "请输入搜索内容");
                    } else {
                        buildDatas(keywords, false);
                        showBookingToast(2);
                        return true;
                    }
                }
                return false;
            }
        });
        msearchet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keywords = msearchet.getText().toString();
                if (TextUtils.isEmpty(keywords)) {
                    buildDatas(keywords, false);
                }
            }
        });

        adapter.setSetOnClick(new FriendLxAdapter.setOnClick() {
            @Override
            public void setOnClick(final int userId, String userName) {
                if (fromType == FRIENDLIST) {
                    PersonCentetActivity.start(FriendLxActivity.this, userId + "");
                } else if (fromType == CHATACT) {
                    sendPersonCard(targetId, userId);
                } else if (fromType == COOPACT) {
                    sendResource(resourceId, userId);
                } else if (fromType == INVITEGROUP) {
                    AlertDialogUtils.AlertDialog(FriendLxActivity.this, "确定发送", "确定发送邀请信息给“" + userName + "”", "取消", "确定", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(DialogInterface dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {
                            inviteGroup(groupId, userId);
                            dialog.dismiss();
                        }
                    });


                } else if (fromType == COOPPHOTO) {
                    AlertDialogUtils.AlertDialog(FriendLxActivity.this, "确定发送", "确定将此图片发送给“" + userName + "”", "取消", "确定", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(DialogInterface dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {
                            sendToPhoto(userId + "", sendPhoto);
                            dialog.dismiss();
                        }
                    });

                }
            }
        });
        indexBar.setmPressedShowTextView(tvSideBarHint).setNeedRealIndex(false).setmLayoutManager(layoutManager);

    }

    private void sendToPhoto(String targetId, String sendPhoto) {
        Uri uri = ImageLoader.getImageUrl2Uri(sendPhoto);
        //IMUtils.sendImageView(Conversation.ConversationType.PRIVATE, targetId, uri, uri, new IMUtils.SendResult() {
            @Override
            public void sendResult(String msg) {
                ToastUtils.showCentetToast(FriendLxActivity.this, msg);
                finish();
            }
        });


    }

    private void inviteGroup(int groupId, int userId) {
        showBookingToast(2);
        RequestManager.getInstance().inviteToJoin(userId, groupId, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(FriendLxActivity.this, msg);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(FriendLxActivity.this, msg);
            }
        });


    }

    private void sendResource(int resourceId, int userId) {
        RequestManager.getInstance().shareResource(userId, resourceId, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(FriendLxActivity.this, msg);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(FriendLxActivity.this, msg);
            }
        });
    }

    private void sendPersonCard(final String targetId, int userId) {
        RequestManager.getInstance().shareCard(Integer.parseInt(targetId), userId, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(FriendLxActivity.this, msg);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(FriendLxActivity.this, msg);
            }
        });
    }

    private void buildDatas(String keywords, final boolean isManager) {
        RequestManager.getInstance().getFriendList(keywords, new GetFriendListCallback() {
            @Override
            public void onSuccess(GetFriendListBean bean) {
                dismissBookingToast();
                datas.clear();
                if (bean.getIs_internal() == 1) {
                    bmessageGroup.setVisibility(View.VISIBLE);
                } else {
                    bmessageGroup.setVisibility(View.GONE);
                }
                if (isManager) {
                    Log.d("==FriendLxActivity", "bean.getGroup().size():" + bean.getGroup().size());
                    List<GetFriendListBean.GroupBean.ListBean> managerList = new ArrayList<>();
                    managerList.add(new GetFriendListBean.GroupBean.ListBean(bean.getF().getUser_id(), bean.getF().getRealname(), bean.getF().getCompany(), bean.getF().getPosition(), bean.getF().getHead_pic(),bean.getF().getIs_vip()));
                    bean.getGroup().add(0, new GetFriendListBean.GroupBean("服", managerList));
                    adapter.setManager(true);
                } else {
                    adapter.setManager(false);
                }
                datas.addAll(bean.getGroup());

                mDecoration = new FriendSuspensionDecoration(FriendLxActivity.this, datas);
                mDecoration.setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics()));
                if (isManager) {
                    mfriendlxRecycler.addItemDecoration(mDecoration);
                }
                mDecoration.setmDatas(datas);
                if (mDecoration != null) {
                    indexBar.setmSourceDatax(datas, "friend").invalidate();
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(FriendLxActivity.this, msg);
            }
        });


    }

    private void initTitle() {
        topStatusBar(false);
        layoutManager = new LinearLayoutManager(FriendLxActivity.this, LinearLayoutManager.VERTICAL, false);
        mfriendlxRecycler.setLayoutManager(layoutManager);
        adapter = new FriendLxAdapter(FriendLxActivity.this, R.layout.item_friendlx, datas);
        mfriendlxRecycler.setAdapter(adapter);


    }


    @OnClick({R.id.bt_finish, R.id.bmessage_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bmessage_group:
                //分组
                GroupFriendActivity.start(mContext);
                break;
            default:
                break;
        }
    }

}
