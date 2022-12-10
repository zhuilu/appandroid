package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.GroupMemberManageAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.GroupMemberManageBean;
import com.xinniu.android.qiqueqiao.customs.ClearEditText;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLGroupManageDialog;
import com.xinniu.android.qiqueqiao.dialog.MakeoverCodeDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetUserListCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 群组成员
 * Created by yuchance on 2018/10/10.
 */

public class GroupMemberManageActivity extends BaseActivity {
    @BindView(R.id.mgroupsearchEdit)
    ClearEditText mgroupsearchEdit;
    @BindView(R.id.mmemberRecycler)
    RecyclerView mmemberRecycler;
    @BindView(R.id.bgroupmanage)
    TextView bgroupmanage;
    @BindView(R.id.bcanceltv)
    TextView bcanceltv;
    private long groupId;
    private String keyword;
    private List<GroupMemberManageBean> datas = new ArrayList<>();
    private GroupMemberManageAdapter adapter;
    private boolean isManage = false;
    private String cname;
    private MakeoverCodeDialog dialog;
    private AppCompatDialog managerDialog;
    private int identity;

    public static void start(Context context, long groupId, String Cname, int identity) {
        Intent intent = new Intent(context, GroupMemberManageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("identity", identity);
        bundle.putLong("groupId", groupId);
        bundle.putString("Cname", Cname);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_groupmember_manage;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            groupId = bundle.getLong("groupId");
            cname = bundle.getString("Cname");
            identity = bundle.getInt("identity");
            if (identity == 0) {
                ShowUtils.showViewVisible(bgroupmanage, View.GONE);
            } else if (identity == 1) {
                ShowUtils.showViewVisible(bgroupmanage, View.VISIBLE);
            } else if (identity == 2) {
                ShowUtils.showViewVisible(bgroupmanage, View.VISIBLE);
            } else {
                ShowUtils.showViewVisible(bgroupmanage, View.GONE);
            }
        }
        LinearLayoutManager manager = new LinearLayoutManager(GroupMemberManageActivity.this, LinearLayoutManager.VERTICAL, false);
        mmemberRecycler.setLayoutManager(manager);
        adapter = new GroupMemberManageAdapter(R.layout.item_groupmember_manage, datas);
        mmemberRecycler.setAdapter(adapter);

        mgroupsearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    final String tvName = mgroupsearchEdit.getText().toString();
                    if (TextUtils.isEmpty(tvName)) {
                        ToastUtils.showCentetImgToast(GroupMemberManageActivity.this, "请输入搜索内容");
                    } else {
                        loadDatas(groupId, tvName);
                        bcanceltv.setVisibility(View.VISIBLE);
                        showBookingToast(2);
                        return true;
                    }
                }
                return false;
            }
        });
        adapter.setGoManage(new GroupMemberManageAdapter.goManage() {
            @Override
            public void setGoMange(int userId, int identity, int isBlack) {
                setPop(userId, identity, isBlack);

            }

            @Override
            public void goToManage(int userId) {
                PersonCentetActivity.start(GroupMemberManageActivity.this, userId + "");
            }
        });
        loadDatas(groupId, mgroupsearchEdit.getText().toString().trim());
    }

    private void setPop(final int userId, final int identix, int isBlack) {
        managerDialog = new QLGroupManageDialog.Builder(mContext)
                .setIsManager(identix)
                .setIsBanner(isBlack)
                .setUserManager(identity)
                .setmShareCallback(new QLGroupManageDialog.ShareNewCallback() {
                    @Override
                    public void onClickShare(QLGroupManageDialog.ShareType type) {
                        switch (type) {
                            case DOMANAGER:
                                goMemberMange(userId, identity, 1, 0, "");
                                break;
                            case MAKEOVER:
                                dialog = new MakeoverCodeDialog(GroupMemberManageActivity.this);
                                dialog.setMakeoverGroup(new MakeoverCodeDialog.makeoverGroup() {
                                    @Override
                                    public void makeoverGroup(String code, String mobile) {
                                        goMemberMange(userId, identity, 2, Integer.parseInt(code), mobile);
                                    }
                                });
                                dialog.show(getSupportFragmentManager(), "group");
                                break;
                            case BANNER1:
                                goMemberMange(userId, identity, 3, 0, "");
                                break;
                            case BANNER3:
                                goMemberMange(userId, identity, 4, 0, "");
                                break;
                            case DELETE:
                                goMemberMange(userId, identity, 5, 0, "");
                                break;
                            case NOBANNER:
                                goMemberMange(userId, identity, 6, 0, "");
                                break;
                            case NOMANAGER:
                                goMemberMange(userId, identity, 7, 0, "");
                                break;
                            default:
                                break;


                        }
                    }
                }).build();
        managerDialog.show();


    }

    private void goMemberMange(int toUserId, int identityType, int type, int code, String mobile) {
        RequestManager.getInstance().goMemberManage(toUserId, groupId, identityType, cname, type, code, mobile, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                try {
                    if (dialog.getDialog().isShowing()) {
                        dialog.dismiss();
                    }
                } catch (Exception e) {//在activity销毁的时候，可能出现异常
                    e.printStackTrace();
                }
                bgroupmanage.setText("管理");
                isManage = false;
                ToastUtils.showCentetToast(GroupMemberManageActivity.this, msg);
                loadDatas(groupId, mgroupsearchEdit.getText().toString().trim());
                managerDialog.dismiss();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(GroupMemberManageActivity.this, msg);
            }
        });
    }

    private void loadDatas(long groupId, String keyword) {
        showBookingToast(1);
        RequestManager.getInstance().getUserList(groupId, keyword, new GetUserListCallback() {
            @Override
            public void onSuccess(List<GroupMemberManageBean> list) {
                dismissBookingToast();
                datas.clear();
                datas.addAll(list);

                for (int i = 0; i < datas.size(); i++) {
                    datas.get(i).setManage(false);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(GroupMemberManageActivity.this, msg);
            }
        });
    }

    List<GroupMemberManageBean> memberList = new ArrayList<>();

    @OnClick({R.id.bt_finish, R.id.bgroupmanage, R.id.bcanceltv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.bgroupmanage:

                if (!isManage) {
                    if (identity == 2) {

                        int userId = UserInfoHelper.getIntance().getUserId();
                        for (int i = 0; i < datas.size(); i++) {
                            datas.get(i).setManage(true);
                            if (datas.get(i).getUser_id() == userId) {
                                memberList.add(datas.get(i));
                            }
                        }
                        datas.removeAll(memberList);
                    } else if (identity == 1) {
                        for (int i = 0; i < datas.size(); i++) {
                            datas.get(i).setManage(true);
                            if (datas.get(i).getIdentity() == 1 || datas.get(i).getIdentity() == 2) {
                                memberList.add(datas.get(i));
                            }
                        }
                        datas.removeAll(memberList);
                    }
                    adapter.notifyDataSetChanged();
                    bgroupmanage.setText("完成");
                    isManage = true;
                } else {
                    datas.addAll(0, memberList);
                    for (int i = 0; i < datas.size(); i++) {
                        datas.get(i).setManage(false);
                    }
                    adapter.notifyDataSetChanged();
                    memberList.clear();
                    bgroupmanage.setText("管理");
                    isManage = false;

                }
                break;
            case R.id.bcanceltv:
                keyword = "";
                mgroupsearchEdit.setText(keyword);
                loadDatas(groupId, keyword);
                bcanceltv.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
