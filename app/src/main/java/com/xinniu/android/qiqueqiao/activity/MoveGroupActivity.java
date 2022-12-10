package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.MoveGroupAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.AddGroupBean;
import com.xinniu.android.qiqueqiao.bean.GroupFriendBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AddGroupCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetGroupFriendListCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MoveGroupActivity extends BaseActivity {
    @BindView(R.id.item_lx_headimg)
    CircleImageView itemLxHeadimg;
    @BindView(R.id.lx_nametv)
    TextView lxNametv;
    @BindView(R.id.lx_positiontv)
    TextView lxPositiontv;
    @BindView(R.id.rcy)
    RecyclerView rcy;
    private List<GroupFriendBean> mDatas = new ArrayList<>();
    private MoveGroupAdapter moveGroupAdapter;
    private int mGroupId;//当前组的id
    private int user_id;

    public static void startSimpleEidtForResult(AppCompatActivity context, int requestCode, int group_id, int user_id, String name, String position, String img_url) {
        Intent starter = new Intent(context, MoveGroupActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("group_id", group_id);
        bundle.putInt("user_id", user_id);
        bundle.putString("name", name);
        bundle.putString("position", position);
        bundle.putString("img_url", img_url);
        starter.putExtras(bundle);
        context.startActivityForResult(starter, requestCode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_move_group;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        mGroupId = getIntent().getExtras().getInt("group_id");
        user_id = getIntent().getExtras().getInt("user_id");
        String name = getIntent().getExtras().getString("name");
        String position = getIntent().getExtras().getString("position");
        String img_url = getIntent().getExtras().getString("img_url");
        ImageLoader.loadAvter(img_url, itemLxHeadimg);
        lxNametv.setText(name);
        lxPositiontv.setText(position);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcy.setLayoutManager(manager);
        moveGroupAdapter = new MoveGroupAdapter(MoveGroupActivity.this, R.layout.item_move_group, mDatas);
        rcy.setAdapter(moveGroupAdapter);
        moveGroupAdapter.setSetOnClick(new MoveGroupAdapter.setOnClick() {
            @Override
            public void setOnClick(String group_name, int group_id) {
                if (group_id == -1) {
                    group_id = 0;
                }
                moveGroup(group_id, user_id);
            }
        });
        initData();
    }

    private void initData() {
        showBookingToast(1);
        RequestManager.getInstance().getGroupFriendList(new GetGroupFriendListCallback() {
            @Override
            public void onSuccess(List<GroupFriendBean> bean) {
                dismissBookingToast();
                mDatas.clear();
                int user_Id = UserInfoHelper.getIntance().getUserId();
                GroupFriendBean groupFriendBean = new GroupFriendBean(-1, user_Id, "未分组", 0);
                mDatas.add(0, groupFriendBean);
                mDatas.addAll(1, bean);
                moveGroupAdapter.getSparseBooleanArray().clear();
                for (int i = 0; i < mDatas.size(); i++) {
                    if (mGroupId == mDatas.get(i).getGroup_id()) {
                        moveGroupAdapter.getSparseBooleanArray().put(i, true);
                    }
                }
                moveGroupAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(MoveGroupActivity.this, msg);

            }
        });
    }


    private void moveGroup(int group_id, final int user_id) {

        RequestManager.getInstance().moveGroup(group_id, user_id, new AddGroupCallback() {
            @Override
            public void onSuccess(AddGroupBean bean) {
                ToastUtils.showCentetImgToast(MoveGroupActivity.this, "修改分组成功");
                Intent intent = new Intent();
                intent.putExtra("old_group_id", bean.getOld_group_id());
                intent.putExtra("new_group_id", bean.getNew_group_id());
                intent.putExtra("user_id",user_id);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {

                ToastUtils.showCentetImgToast(MoveGroupActivity.this, msg);
            }
        });
    }

    @OnClick({R.id.bt_finish, R.id.rlayout_data})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                finish();
                break;
            case R.id.rlayout_data:
                PersonCentetActivity.start(MoveGroupActivity.this, user_id + "");
                break;
        }
    }
}
