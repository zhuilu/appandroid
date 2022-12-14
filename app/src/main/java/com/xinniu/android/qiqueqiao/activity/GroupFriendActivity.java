package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.GroupFriendAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.AddGroupBean;
import com.xinniu.android.qiqueqiao.bean.GroupFriendBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipTwoDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AddGroupCallback;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetGroupFriendListCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetGroupFriendListFristCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GroupFriendActivity extends BaseActivity {
    @BindView(R.id.expend_list)
    ExpandableListView expendList;
    private GroupFriendAdapter groupFriendAdapter;
    private List<GroupFriendBean> commentListx = new ArrayList<>();
    private InputMethodManager imm;
    private int mGroupPosition;
    private int mChildPosition;

    public static void start(Context context) {
        Intent intent = new Intent(context, GroupFriendActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_group_friend;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        groupFriendAdapter = new GroupFriendAdapter(commentListx, GroupFriendActivity.this);
        expendList.setAdapter(groupFriendAdapter);
        expendList.setGroupIndicator(null);
        initData();
        groupFriendAdapter.setSetOnClick(new GroupFriendAdapter.setOnClick() {
            @Override
            public void setOnClick(int type, final int groupPosition, int childPosition, String position) {
                mGroupPosition = groupPosition;
                mChildPosition = childPosition;
                if (type == 1) {
                    //????????????????????????????????????
                    moveGroup(0, commentListx.get(groupPosition).getUser_list().get(childPosition).getUser_id());

                } else if (type == 2) {
                    //????????????
                    addGroup("????????????", commentListx.get(groupPosition).getName(), 2, commentListx.get(groupPosition).getGroup_id());

                } else if (type == 3) {
                    //?????????
                    MoveGroupActivity.startSimpleEidtForResult(GroupFriendActivity.this, 32, commentListx.get(groupPosition).getGroup_id(), commentListx.get(groupPosition).getUser_list().get(childPosition).getUser_id(), commentListx.get(groupPosition).getUser_list().get(childPosition).getRealname(), position, commentListx.get(groupPosition).getUser_list().get(childPosition).getHead_pic());
                } else if (type == 4) {
                    //?????????
                    new QLTipTwoDialog.Builder(GroupFriendActivity.this)
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setTitle("????????????")
                            .setMessage("????????????????????????????????????????????????")
                            .setPositiveButton("????????????", new QLTipTwoDialog.IPositiveCallback() {
                                @Override
                                public void onClick() {
                                    delGroup(commentListx.get(groupPosition).getGroup_id());
                                }
                            })
                            .setNegativeButton("??????", new QLTipTwoDialog.INegativeCallback() {
                                @Override
                                public void onClick() {
                                    dissMissDialog();

                                }
                            })
                            .show(GroupFriendActivity.this);

                } else if (type == 5) {
                    //????????????????????? ????????????
                    if (expendList.isGroupExpanded(groupPosition)) {
                        expendList.collapseGroup(groupPosition);
                    } else {
                        expendList.expandGroup(groupPosition);
                    }
                }
            }
        });
    }


    private void moveGroup(int group_id, int user_id) {

        RequestManager.getInstance().moveGroup(group_id, user_id, new AddGroupCallback() {
            @Override
            public void onSuccess(AddGroupBean bean) {
                ToastUtils.showCentetImgToast(GroupFriendActivity.this, "??????????????????");
                //??????????????????
                // ?????????????????????????????????
                //???????????????????????????
                GroupFriendBean.UserListBean userListBean = commentListx.get(mGroupPosition).getUser_list().get(mChildPosition);
                //?????????????????????
                commentListx.get(mGroupPosition).getUser_list().remove(mChildPosition);
                int count = commentListx.get(mGroupPosition).getCount() - 1;
                commentListx.get(mGroupPosition).setCount(count);
                //????????????????????????
//                int count1 = commentListx.get(0).getCount() + 1;
                commentListx.get(0).getUser_list().add(userListBean);
                //               commentListx.get(0).setCount(count1);
                groupFriendAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {

                ToastUtils.showCentetImgToast(GroupFriendActivity.this, msg);
            }
        });
    }

    private void delGroup(int group_id) {
        RequestManager.getInstance().delGroup(group_id, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                commentListx.remove(mGroupPosition);
                ToastUtils.showCentetImgToast(GroupFriendActivity.this, msg);
                groupFriendAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {

                ToastUtils.showCentetImgToast(GroupFriendActivity.this, msg);
            }
        });
    }

    /**
     * ????????????
     *
     * @param trim ?????????
     */
    private void addGroupName(final String trim) {

        RequestManager.getInstance().createGroup(trim, new AddGroupCallback() {
            @Override
            public void onSuccess(AddGroupBean item) {

                //??????????????????
                // ?????????????????????????????????
                int userId = UserInfoHelper.getIntance().getUserId();
                List<GroupFriendBean.UserListBean> user_list = new ArrayList<>();
                GroupFriendBean groupFriendBean = new GroupFriendBean(item.getGroup_id(), userId, trim, 0,user_list);
                commentListx.add(groupFriendBean);
                groupFriendAdapter.notifyDataSetChanged();
                ToastUtils.showCentetImgToast(GroupFriendActivity.this, "??????????????????");

            }


            @Override
            public void onFailed(int code, String msg) {

                ToastUtils.showCentetImgToast(GroupFriendActivity.this, msg);
            }
        });
    }

    private void initData() {
        showBookingToast(1);
        RequestManager.getInstance().getGroupFriendListFrist(new GetGroupFriendListFristCallback() {
            @Override
            public void onSuccess(List<GroupFriendBean.UserListBean> bean) {

                commentListx.clear();
                int userId = UserInfoHelper.getIntance().getUserId();
                GroupFriendBean groupFriendBean = new GroupFriendBean(-1, userId, "????????????", bean.size());
                groupFriendBean.setUser_list(bean);
                commentListx.add(0, groupFriendBean);

                getData();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(GroupFriendActivity.this, msg);

            }
        });

    }

    private void getData() {
        RequestManager.getInstance().getGroupFriendList(new GetGroupFriendListCallback() {
            @Override
            public void onSuccess(List<GroupFriendBean> bean) {
                dismissBookingToast();
                commentListx.addAll(1, bean);
                groupFriendAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(GroupFriendActivity.this, msg);

            }
        });

    }

    @OnClick({R.id.bt_finish, R.id.add_group})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.add_group:
                //????????????
                addGroup("????????????", "?????????????????????", 1, -1);
                break;
        }
    }

    private void addGroup(String title, String content, final int type, final int group_id) {
        setPopWindow(R.layout.view_resource_type_other);
        final TextView tv_title = (TextView) popview.findViewById(R.id.view_type_tv);
        final EditText contentEt = (EditText) popview.findViewById(R.id.view_type_other_ed);
        TextView cancelTv = (TextView) popview.findViewById(R.id.view_other_cancelTv);
        TextView addTv = (TextView) popview.findViewById(R.id.view_other_addTv);
        tv_title.setText(title);
        if (type == 1) {
            contentEt.setHint("?????????????????????");
            addTv.setText("??????");
        } else {
            contentEt.setText(content);
            contentEt.setSelection(content.length());
            addTv.setText("??????");
        }
//                showSoftInputFromWindow(ResourceTypeActivity.this,contentEt);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispopwindow();
                if (imm != null) {
                    imm.hideSoftInputFromWindow(contentEt.getWindowToken(), 0);
                }
            }
        });
        addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(contentEt.getText().toString().trim())) {
                    ToastUtils.showToast(GroupFriendActivity.this, "??????????????????");
                    return;

                }
                if (contentEt.getText().toString().trim().length() > 20) {
                    ToastUtils.showToast(GroupFriendActivity.this, "?????????????????????20???");
                    return;
                }

                if (type == 1) {
                    addGroupName(contentEt.getText().toString().trim());
                } else {
                    //???????????????
                    updateGroupName(group_id, contentEt.getText().toString().trim());

                }
                if (imm != null) {
                    imm.hideSoftInputFromWindow(contentEt.getWindowToken(), 0);
                }

                dispopwindow();
            }
        });

    }

    private void updateGroupName(int group_id, final String trim) {


        RequestManager.getInstance().editGroupName(trim, group_id, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {

                commentListx.get(mGroupPosition).setName(trim);
                groupFriendAdapter.notifyDataSetChanged();
                ToastUtils.showCentetImgToast(GroupFriendActivity.this, msg);

            }

            @Override
            public void onFailed(int code, String msg) {

                ToastUtils.showCentetImgToast(GroupFriendActivity.this, msg);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == 32) {
                int old_group_id = data.getIntExtra("old_group_id", -1);
                int new_group_id = data.getIntExtra("new_group_id", -1);
                int user_id = data.getIntExtra("user_id", -1);
                //????????????
                //??????????????????
                // ?????????????????????????????????
                //???????????????????????????
                GroupFriendBean.UserListBean userListBean = commentListx.get(mGroupPosition).getUser_list().get(mChildPosition);

                if (old_group_id == 0) {
                    //?????????????????????
                } else {
                    //?????????????????????
                    int mPosition = 0;
                    int childPosition = 0;

                    for (int i = 0; i < commentListx.size(); i++) {
                        if (old_group_id == commentListx.get(i).getGroup_id()) {
                            mPosition = i;
                        }
                    }
                    for (int i = 0; i < commentListx.get(mPosition).getUser_list().size(); i++) {
                        if (user_id == commentListx.get(mPosition).getUser_list().get(i).getUser_id()) {
                            childPosition = i;
                        }
                    }


                    commentListx.get(mPosition).getUser_list().remove(childPosition);
                    int count = commentListx.get(mPosition).getCount() - 1;
                    commentListx.get(mPosition).setCount(count);
                }
                //??????????????????
                //????????????????????????
                if (new_group_id == 0) {
                    //???????????????????????????????????????
                } else {
                    int mPosition = 0;
                    for (int i = 0; i < commentListx.size(); i++) {
                        if (new_group_id == commentListx.get(i).getGroup_id()) {
                            mPosition = i;
                        }
                    }

                    int count1 = commentListx.get(mPosition).getCount() + 1;
                    commentListx.get(mPosition).getUser_list().add(userListBean);
                    commentListx.get(mPosition).setCount(count1);
                }
                groupFriendAdapter.notifyDataSetChanged();

            }
        }

    }

}
