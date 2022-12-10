package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.GreentingsAdapter;
import com.xinniu.android.qiqueqiao.adapter.MyPushAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.AddGroupBean;
import com.xinniu.android.qiqueqiao.bean.GreentingsBean;
import com.xinniu.android.qiqueqiao.bean.GroupFriendBean;
import com.xinniu.android.qiqueqiao.customs.qldialog.InputCommentLanguageDialog;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AddGroupCallback;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetGreentsCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GreetingsListActivity extends BaseActivity {

    @BindView(R.id.mlxlisttitle)
    TextView mlxlisttitle;
    @BindView(R.id.add_group)
    TextView addGroup;
    @BindView(R.id.rcy)
    RecyclerView rcy;
    private List<GreentingsBean.SystemBean> mDatas = new ArrayList<>();
    private GreentingsAdapter greentingsAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, GreetingsListActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_greetings;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        rcy.setLayoutManager(new LinearLayoutManager(this));
        greentingsAdapter = new GreentingsAdapter(this, mDatas);
        rcy.setAdapter(greentingsAdapter);
        greentingsAdapter.setCallback(new GreentingsAdapter.Callback() {
            @Override
            public void onEdit(int position, int type) {
                if (type == 0) {
                    //选择招呼语
                    if (mDatas.get(position).getStatus() == 1) {
                        return;
                    }

                    selectedGreetings(position, mDatas.get(position).getId());
                } else if (type == 1) {
                    //删除
                    if (mDatas.get(position).getStatus() == 1) {
                        //默认的不能删除
                        ToastUtils.showCentetToast(GreetingsListActivity.this, "您目前正在使用此打招呼语，不可删除");
                    } else {
                        delGreetings(position, mDatas.get(position).getId());
                    }
                }

            }
        });
        initData();
    }

    private void selectedGreetings(int position, final int id) {
        showBookingToast(2);
        RequestManager.getInstance().selectedGreetings(id, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                for (int i = 0; i < mDatas.size(); i++) {
                    mDatas.get(i).setStatus(0);
                    if (id == mDatas.get(i).getId()) {
                        mDatas.get(i).setStatus(1);
                    }
                }
                ToastUtils.showCentetImgToast(GreetingsListActivity.this, msg);
                greentingsAdapter.notifyDataSetChanged();
                dismissBookingToast();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetImgToast(GreetingsListActivity.this, msg);
            }
        });

    }

    private void delGreetings(final int position, int id) {
        RequestManager.getInstance().delGreetings(id, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                mDatas.remove(position);
                ToastUtils.showCentetImgToast(GreetingsListActivity.this, msg);
                greentingsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(GreetingsListActivity.this, msg);
            }
        });
    }

    private void initData() {
        showBookingToast(1);
        RequestManager.getInstance().getGreetingsList(new GetGreentsCallback() {
            @Override
            public void onSuccess(GreentingsBean item) {
                mDatas.clear();
                List<GreentingsBean.SystemBean> list01 = new ArrayList<GreentingsBean.SystemBean>();
                list01.addAll(item.getSystem());//系统招呼语
                for (int i = 0; i < list01.size(); i++) {
                    list01.get(i).setItemType(1);
                }
                mDatas.addAll(list01);

                for (int i = 0; i < item.getCustomize().size(); i++) {
                    GreentingsBean.CustomizeBean customizeBean = item.getCustomize().get(i);
                    GreentingsBean.SystemBean systemBean = new GreentingsBean.SystemBean(customizeBean.getId(), customizeBean.getUser_id(), customizeBean.getTitle(), customizeBean.getStatus(), 2);
                    mDatas.add(systemBean);
                }
                greentingsAdapter.notifyDataSetChanged();
                dismissBookingToast();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(GreetingsListActivity.this, msg);
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

                //添加
                InputCommentLanguageDialog inputCommentLanguageDialog = new InputCommentLanguageDialog(GreetingsListActivity.this, R.style.QLDialog1, "招呼语");
                inputCommentLanguageDialog.setmShareCallback(new InputCommentLanguageDialog.SaveCallback() {
                    @Override
                    public void onSaveCall(String str) {
                        //添加
                        addGreetings(str);
                    }
                });
                inputCommentLanguageDialog.show();

                break;
        }
    }

    private void addGreetings(final String str) {
        RequestManager.getInstance().addGreetings(str, new AddGroupCallback() {
            @Override
            public void onSuccess(AddGroupBean bean) {
                ToastUtils.showCentetImgToast(GreetingsListActivity.this, "添加成功");
                //本地数据更新
                int userId = UserInfoHelper.getIntance().getUserId();
                GreentingsBean.SystemBean systemBean = new GreentingsBean.SystemBean(bean.getId(), userId, str, 0, 2);
                mDatas.add(systemBean);
                greentingsAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailed(int code, String msg) {

                ToastUtils.showCentetImgToast(GreetingsListActivity.this, msg);
            }
        });

    }
}
