package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.CommunicationRecordTypeAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CommunicationRecordBean;
import com.xinniu.android.qiqueqiao.customs.TypeWindow;
import com.xinniu.android.qiqueqiao.customs.image.GlideSimpleLoader;
import com.xinniu.android.qiqueqiao.customs.image.ImageWatcherHelper;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetCommunicationRecordListCallback;
import com.xinniu.android.qiqueqiao.utils.StatusBarCompat;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CommunicationRecordActivity extends BaseActivity {


    @BindView(R.id.rlayout_type)
    RelativeLayout rlayoutType;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    @BindView(R.id.result_rv)
    RecyclerView resultRv;
    @BindView(R.id.searchSwipe)
    SmartRefreshLayout searchSwipe;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_type)
    TextView tvType;
    private int mType = 0;
    private TimePickerView pvCustomTime;
    private long time;
    private int mPage = 1;
    private List<CommunicationRecordBean> mDataNew = new ArrayList<>();
    private CommunicationRecordTypeAdapter mAdapter;
    private ImageWatcherHelper iwHelper;
    String lastTime = "";

    public static void start(Context context) {
        Intent intent = new Intent(context, CommunicationRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_communication_record;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        initCustomTimePicker();
        iwHelper = ImageWatcherHelper.with(this, new GlideSimpleLoader()) // ??????????????? ImageWatcher ???????????????????????????
                .setTranslucentStatus(StatusBarCompat.getStatusBarHeight(this));
        mAdapter = new CommunicationRecordTypeAdapter(CommunicationRecordActivity.this, R.layout.item_communication_record_type, mDataNew);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        resultRv.setLayoutManager(manager);
        resultRv.setAdapter(mAdapter);
        mAdapter.setSetOnClick(new CommunicationRecordTypeAdapter.setOnClick() {
            @Override
            public void setOnClick(ArrayList<String> list, int position, SparseArray<ImageView> data, ImageView v) {
                iwHelper.show(position, data, convert(list));
            }
        });
        searchSwipe.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                initDatas(mPage, false);
            }
        });
        searchSwipe.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 1;
                initDatas(mPage, false);
            }
        });
        initDatas(mPage, true);
    }

    @OnClick({R.id.bt_return, R.id.rlayout_time, R.id.rlayout_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_return:
                finish();
                break;
            case R.id.rlayout_time:
                if (pvCustomTime != null && !pvCustomTime.isShowing()) {
                    pvCustomTime.show(ll, false); //??????????????????????????????
                }

                break;
            case R.id.rlayout_type:
                showType();
                break;
        }
    }

    private void showType() {
        final TypeWindow typeWindow = new TypeWindow(CommunicationRecordActivity.this, mType);
        typeWindow.showAsDropDown(ll);
        typeWindow.setFinish(new TypeWindow.finish() {
            @Override
            public void setFinish(int mid, String name) {
                mType = mid;
                if (mid == 0) {
                    tvType.setText("??????");
                } else {
                    tvType.setText(name);
                }
                mPage = 1;
                initDatas(mPage, true);
                typeWindow.dismiss();
            }
        });
    }

    private void initCustomTimePicker() {

        /**
         * @description
         *
         * ???????????????
         * 1.?????????????????????id??? optionspicker ?????? timepicker ???????????????????????????????????????????????????????????????.
         * ???????????????demo ????????????????????????layout?????????
         * 2.????????????Calendar???????????????0-11???,?????????????????????Calendar???set?????????????????????,???????????????????????????0-11
         * setRangDate??????????????????????????????(?????????????????????????????????????????????1900-2100???????????????????????????)
         */
        Calendar selectedDate = Calendar.getInstance();//??????????????????
        Calendar startDate = Calendar.getInstance();
        startDate.set(2017, 0, 1);
        Calendar endDate = Calendar.getInstance();
        //??????????????? ??????????????????
        pvCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//??????????????????
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                String d=format.format(date);
                try {
                    time=   format.parse(d).getTime() / 1000;
                } catch (ParseException e) {
                    e.printStackTrace();
                    time=0;
                }
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy???MM???");
                tvTime.setText(format1.format(date));

            }
        })

                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        //????????????
                        TextView sureTv = (TextView) v.findViewById(R.id.view_source_sureTv);
                        //????????????
                        TextView reTv = (TextView) v.findViewById(R.id.view_source_retagTv);
                        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.llayout_root);
                        linearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                tvTime.setText("??????");

                                pvCustomTime.dismiss();
                                mPage = 1;
                                initDatas(mPage, true);
                            }
                        });
                        sureTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();

                                pvCustomTime.dismiss();
                                mPage = 1;
                                initDatas(mPage, true);

                            }
                        });
                        reTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tvTime.setText("??????");
                                time = 0;
                                pvCustomTime.dismiss();
                                mPage = 1;
                                initDatas(mPage, true);
                            }
                        });
                    }
                })
                .setContentTextSize(18)
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("", "???", "???", "???", "???", "???")
                .setLineSpacingMultiplier(1.2f)
                .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
                .setDividerColor(0xFFF5F5F5)
                .setTextColorCenter(0xFF333333)//????????????????????????
                .setLineSpacingMultiplier(2.0f)//????????????????????????????????????
                .isTimePop(true)
                .build();


    }

    private void initDatas(final int mPage, boolean isShow) {
        if (isShow) {
            showBookingToast(1);
        }
        RequestManager.getInstance().getCommunicationRecordList(mType, (int) time, mPage, new GetCommunicationRecordListCallback() {
            @Override
            public void onSuccess(CommunicationRecordBean.DataBean resultDO) {
                dismissBookingToast();
                if (mPage == 1) {
                    mDataNew.clear();
                } else {
                    lastTime = mDataNew.get(mDataNew.size() - 1).getTime();//??????????????????????????????????????????
                }

                List<CommunicationRecordBean.DataBean.ListBean> data = new ArrayList<>();
                data.addAll(resultDO.getList());
                //????????????
                List<String> times = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    String t = TimeUtils.time22ActTime(data.get(i).getCreate_time() * 1000);
                    if (!times.contains(t)) {
                        times.add(t);
                    }
                }
                for (int j = 0; j < times.size(); j++) {
                    CommunicationRecordBean recordBean = new CommunicationRecordBean();
                    recordBean.setTime(times.get(j));
                    CommunicationRecordBean.DataBean dataBean = new CommunicationRecordBean.DataBean();
                    List<CommunicationRecordBean.DataBean.ListBean> listBeans = new ArrayList<>();
                    for (int m = 0; m < data.size(); m++) {
                        if (times.get(j).equals(TimeUtils.time22ActTime(data.get(m).getCreate_time() * 1000))) {
                            listBeans.add(data.get(m));
                        }
                    }
                    //??????????????????????????????????????????????????????????????????
                    if (j == 0 && lastTime.equals(times.get(j))) {
                        for (int n = 0; n < listBeans.size(); n++) {
                            mDataNew.get(mDataNew.size() - 1).getData().getList().add(listBeans.get(n));
                        }

                    } else {
                        dataBean.setList(listBeans);
                        recordBean.setData(dataBean);
                        mDataNew.add(recordBean);
                    }
                }

                mAdapter.notifyDataSetChanged();
                finishSwipe();
                if (resultDO.getList() != null) {
                    if (mPage == 1) {
                        if (resultDO.getList().size() == 0) {
                            yperchRl.setVisibility(View.VISIBLE);
                            mAdapter.removeAllFooterView();
                            searchSwipe.setEnableLoadMore(false);
                        } else {
                            yperchRl.setVisibility(View.GONE);
                            if (resultDO.getHasMore() == 0) {
                                mAdapter.setFooterView(footView);
                                searchSwipe.setEnableLoadMore(false);
                            } else {
                                mAdapter.removeAllFooterView();
                                searchSwipe.setEnableLoadMore(true);
                            }
                        }
                    } else {
                        if (resultDO.getHasMore() == 0) {
                            mAdapter.setFooterView(footView);
                            searchSwipe.setEnableLoadMore(false);
                        } else {
                            mAdapter.removeAllFooterView();
                            searchSwipe.setEnableLoadMore(true);
                        }
                    }
                } else {
                    yperchRl.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                finishSwipe();
                ToastUtils.showCentetToast(CommunicationRecordActivity.this, msg);
            }
        });

    }

    private void finishSwipe() {
        if (searchSwipe != null) {
            if (searchSwipe.isEnableRefresh()) {
                searchSwipe.finishRefresh();
            }
            if (searchSwipe.isEnableLoadMore()) {
                searchSwipe.finishLoadMore();
            }
        }
    }

    private List<Uri> convert(List<String> data) {
        List<Uri> list = new ArrayList<>();
        for (String d : data) list.add(Uri.parse(d));
        return list;
    }

}
