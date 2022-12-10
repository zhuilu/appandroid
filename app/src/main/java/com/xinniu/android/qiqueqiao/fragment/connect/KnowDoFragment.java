package com.xinniu.android.qiqueqiao.fragment.connect;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.adapter.KnowDoAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.AddressListBean;
import com.xinniu.android.qiqueqiao.bean.ConnectPersonBean;
import com.xinniu.android.qiqueqiao.bean.GoFriendApplyBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetAddressListCallback;
import com.xinniu.android.qiqueqiao.request.callback.GoFriendApplyCallback;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 人脉-认识的人
 * Created by yuchance on 2019/2/12.
 */

public class KnowDoFragment extends LazyBaseFragment implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.mrecycler)
    RecyclerView mrecycler;
    @BindView(R.id.mrefresh)
    SmartRefreshLayout mrefresh;
    @BindView(R.id.mknow_whtext)
    TextView mknowWhtext;
    @BindView(R.id.yperchRl)
    RelativeLayout yperchRl;
    private static int KNOWREQUESTCODE = 101;
    @BindView(R.id.bknowdo_addtv)
    TextView bknowdoAddtv;
    @BindView(R.id.bgoto_setting)
    TextView bgotoSetting;

    //    private StringBuffer stringBuffer;
    private List<ConnectPersonBean> mData = new ArrayList<>();
    private int page = 1;

    private List<AddressListBean.ListBean> datas = new ArrayList<>();
    private KnowDoAdapter adapter;
    private String uidData = "";
    private String phoneStr = "";
    public final static int PERMISSION_ADDRESS = 500;
    private boolean isLimit = false;
    private String Timestr;


    public static KnowDoFragment newInstance() {

        Bundle args = new Bundle();

        KnowDoFragment fragment = new KnowDoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_knowdo;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(System.currentTimeMillis());
        Timestr = format.format(date);
        mknowWhtext.setText(ComUtils.strToSpannableStr("企鹊桥无法访问您的通讯录，无法帮您推荐好友，请在“系统设置-应用管理-企鹊桥-权限”里允许企鹊桥访问您的通讯录", "系统设置-应用管理-企鹊桥-权限", "#4B96F3"));
        adapter = new KnowDoAdapter(mContext, R.layout.item_connect_person, datas);
        mrecycler.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mrecycler.setLayoutManager(manager);
        mrefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                initData(page);
            }
        }).setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initData(page);
            }
        });
        adapter.setSetOnClick(new KnowDoAdapter.setOnClick() {
            @Override
            public void setOnClick(int id, AddressListBean.ListBean item, int layoutPosition) {
                if (item.getType() == 1) {
                    if (item.getFriend_status() == 0) {
                        goToAddFriend(id, item, layoutPosition);
                        ToastUtils.showCentetToast(mContext, "加好友");
                    }
                } else {
                    //邀请
                    if (item.getIs_invite() == 0) {
                        inviteConnections(item.getMobile(), item.getRealname(), item, layoutPosition);
                    }

                }
            }

            @Override
            public void setItemOnClick(int id) {
                PersonCentetActivity.start(mContext, id + "");
            }
        });
        requestPermission();
    }

    //邀请好友
    private void inviteConnections(String mobile, String realname, final AddressListBean.ListBean item, final int position) {
        RequestManager.getInstance().inviteConnections(mobile, realname, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(mContext, msg);
                item.setIs_invite(1);
                adapter.notifyItemChanged(position, item);
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(mContext, msg);
            }
        });
    }

    String[] goAddress = {Manifest.permission.READ_CONTACTS};

    @AfterPermissionGranted(PERMISSION_ADDRESS)
    private void requestPermission() {
        if (EasyPermissions.hasPermissions(getContext(),
                goAddress)) {
            isLimit = true;
            initdate();

        } else {
            isLimit = false;
            int laseTime = UserInfoHelper.getIntance().getKnowDoTime();
            try {
                int curTime = Integer.parseInt(Timestr);
                if (curTime - laseTime > 1) {
                    EasyPermissions.requestPermissions(
                            this,
                            getString(R.string.read_contacts),
                            PERMISSION_ADDRESS, goAddress);
                }
            } catch (NumberFormatException e) {

            }
            yperchRl.setVisibility(View.VISIBLE);
            bgotoSetting.setVisibility(View.VISIBLE);
            mknowWhtext.setText(ComUtils.strToSpannableStr("企鹊桥无法访问您的通讯录，无法帮您推荐好友，请在“系统设置-应用管理-企鹊桥-权限”里允许企鹊桥访问您的通讯录", "系统设置-应用管理-企鹊桥-权限", "#4B96F3"));
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        initData(page);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        try {
            int time = Integer.parseInt(Timestr);
            UserInfoHelper.getIntance().setKnowDoTime(time);
        } catch (NumberFormatException e) {
            UserInfoHelper.getIntance().setKnowDoTime(0);
        }
        initData(page);

    }

    private void goToAddFriend(int toUserId, final AddressListBean.ListBean item, final int position) {
        RequestManager.getInstance().goFriendApply(toUserId, 1, new GoFriendApplyCallback() {
            @Override
            public void onSuccess(GoFriendApplyBean data, String msg) {
                ToastUtils.showCentetToast(mContext, msg);
                item.setFriend_status(1);
                adapter.notifyItemChanged(position, item);


            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(mContext, msg);
            }
        });
    }

    private void initdate() {
        //  stringBuffer = new StringBuffer();
        //这个是根据参数传输所以他需要一个数组第一个是电话的联系人第二个是电话的号码
        String projerct[] = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
        //获取resolver对象第一个是谷歌底层的Uri 第二个就是我们的数组 后来三个并没有什么用
        ContentResolver resolver = mContext.getContentResolver();
        //之后调用查的方法
        Cursor query = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projerct, null, null, null);
        //查到之后循环放入集合中 这样就有了数据源 然后放入adapter中 listview绑定adapter
        while (query.moveToNext()) {
            String number = query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String name = query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            if (number.startsWith("+86")) {
                number = number.replace("+86", "");
            }
            if (number.contains("-")) {
                number = number.replace("-", "");
            }
            if (number.contains("(")) {
                number = number.replace("(", "");
            }
            if (number.contains(")")) {
                number = number.replace(")", "");
            }
            if (number.contains(" ")) {
                number = number.replace(" ", "");
            }
            //   stringBuffer.append(number).append(",");
            ConnectPersonBean connectPersonBean = new ConnectPersonBean(name, number);
            mData.add(connectPersonBean);
        }
//        if (stringBuffer.length() > 1) {
//            stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
//        }

        phoneStr = new Gson().toJson(mData);
    }

    @Override
    protected void lazyLoad() {
        page = 1;

        initData(page);

    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
        initData(page);
    }

    private void initData(final int page) {
        RequestManager.getInstance().getAddressList(phoneStr, page, new GetAddressListCallback() {
            @Override
            public void onSuccess(AddressListBean list) {
                if (page == 1) {
                    datas.clear();
                    if (list.getList().size() == 0) {
                        if (isLimit) {
                            yperchRl.setVisibility(View.VISIBLE);
                            bgotoSetting.setVisibility(View.GONE);
                            mknowWhtext.setText("暂无认识的人");
                        }
                        adapter.removeAllFooterView();
                        mrefresh.setEnableLoadMore(false);
                    } else {
                        yperchRl.setVisibility(View.GONE);
                        if (list.getHasMore() == 0) {
                            adapter.setFooterView(gfootView);
                            mrefresh.setEnableLoadMore(false);
                        } else {
                            adapter.removeAllFooterView();
                            mrefresh.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (list.getHasMore() == 0) {
                        adapter.setFooterView(footView);
                        mrefresh.setEnableLoadMore(false);
                    } else {
                        adapter.removeAllFooterView();
                        mrefresh.setEnableLoadMore(true);
                    }
                }
                uidData = list.getUid_data();
                if (TextUtils.isEmpty(uidData)) {
                    bknowdoAddtv.setVisibility(View.GONE);
                } else {
                    bknowdoAddtv.setVisibility(View.VISIBLE);
                }
                datas.addAll(list.getList());
                adapter.notifyDataSetChanged();
                finishSwipe();
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(mContext, msg);
            }
        });
    }

    private void finishSwipe() {
        if (mrefresh != null) {
            if (mrefresh.isEnableRefresh()) {
                mrefresh.finishRefresh(200);
            }
            if (mrefresh.isEnableLoadMore()) {
                mrefresh.finishLoadMore(200);
            }
        }
    }

    @OnClick({R.id.bknowdo_addtv, R.id.bgoto_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bknowdo_addtv:
                goAddFriend();
                break;
            case R.id.bgoto_setting:
                Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                startActivityForResult(intent, KNOWREQUESTCODE);
                break;
            default:

                break;
        }
    }

    private void goAddFriend() {
        RequestManager.getInstance().goBatchAdd(uidData, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                ToastUtils.showCentetToast(mContext, msg);
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetToast(mContext, msg);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == KNOWREQUESTCODE) {
            page = 1;
            requestPermission();
            initData(page);

        }
    }

}
