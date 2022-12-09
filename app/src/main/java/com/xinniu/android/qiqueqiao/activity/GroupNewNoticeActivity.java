package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.GroupNoticeBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GroupNoticeCallback;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/10/16.
 */

public class GroupNewNoticeActivity extends BaseActivity {
    @BindView(R.id.mmemberName)
    TextView mmemberName;
    @BindView(R.id.mmemberPostion)
    TextView mmemberPostion;
    @BindView(R.id.mgroupTimetv)
    TextView mgroupTimetv;
    @BindView(R.id.mgroupNoticeTv)
    TextView mgroupNoticeTv;
    @BindView(R.id.groupheadImg)
    CircleImageView groupheadImg;
    @BindView(R.id.beditTv)
    TextView beditTv;
    @BindView(R.id.groupRl)
    RelativeLayout groupRl;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.explainRl)
    RelativeLayout explainRl;
    @BindView(R.id.groupintroRl)
    RelativeLayout groupintroRl;
    @BindView(R.id.meditTv)
    TextView meditTv;
    private int groupId;
    private String groupName;
    private String content;
    private int identity;

    public static void start(Activity context, int groupId, String groupName, int identity) {
        Intent intent = new Intent(context, GroupNewNoticeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("groupId", groupId);
        bundle.putString("groupName", groupName);
        bundle.putInt("identity", identity);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, 401, bundle);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_group_newnotice;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            groupId = bundle.getInt("groupId");
            groupName = bundle.getString("groupName");
            identity = bundle.getInt("identity");
            if (identity == 1) {
                beditTv.setVisibility(View.VISIBLE);
                meditTv.setVisibility(View.GONE);
            } else if (identity == 2) {
                beditTv.setVisibility(View.VISIBLE);
                meditTv.setVisibility(View.GONE);
            } else {
                beditTv.setVisibility(View.GONE);
                meditTv.setVisibility(View.VISIBLE);
            }
            loadDatas();
        }
    }

    private void loadDatas() {
        RequestManager.getInstance().getNotice(groupId, new GroupNoticeCallback() {
            @Override
            public void onSuccess(GroupNoticeBean bean) {
                content = bean.getContent();
                ShowUtils.showTextPerfect(mmemberName, bean.getRealname());
                ShowUtils.showTextPerfect(mmemberPostion, " · " + bean.getCompany() + bean.getPosition());
                ShowUtils.showTextPerfect(mgroupTimetv, TimeUtils.getTimeStateNew(bean.getCreate_time() + ""));
                ImageLoader.loadAvter(bean.getHead_pic(), groupheadImg);
                ShowUtils.showTextPerfect(mgroupNoticeTv, content);
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(GroupNewNoticeActivity.this, msg);
            }
        });
    }


    @OnClick({R.id.bt_finish, R.id.beditTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_finish:
                setResult(402);
                finish();
                break;
            case R.id.beditTv:
                EditGroupActivity.start(GroupNewNoticeActivity.this, "groupannounce", groupId, groupName, content);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 701 && resultCode == 702) {
            loadDatas();
        }
    }

}
