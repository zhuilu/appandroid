package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.AppointmentBean;
import com.xinniu.android.qiqueqiao.bean.TimeBean;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.customs.calendarview.bean.DateBean;
import com.xinniu.android.qiqueqiao.customs.calendarview.listener.OnMultiChooseListener;
import com.xinniu.android.qiqueqiao.customs.calendarview.listener.OnPagerChangeListener;
import com.xinniu.android.qiqueqiao.customs.calendarview.utils.CalendarUtil;
import com.xinniu.android.qiqueqiao.customs.calendarview.weiget.CalendarView;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetApptFixedCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

public class AppointmentTimeActivity extends BaseActivity {
    @BindView(R.id.bt_back)
    LinearLayout btBack;
    @BindView(R.id.tool_bar_title)
    TextView toolBarTitle;
    @BindView(R.id.tool_bar)
    RelativeLayout toolBar;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.rlayout_sure)
    RelativeLayout rlayoutSure;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.img_left)
    ImageView imgLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.calendar)
    CalendarView calendarView;
    @BindView(R.id.tv_car_num)
    TextView tvCarNum;
    private int p_id;
    private int resource_id;
    private int[] cDate;
    private long time;//选择中的时间戳
    private Call mCall;
    private List<Integer> mTimes = new ArrayList<>();
    private int fixedNum;
    private int mPosition;

    public static void start(Activity context, int p_id, String p_name, int resource_id, int fixedNum, int position, int requestCode) {
        Intent starter = new Intent(context, AppointmentTimeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("p_id", p_id);
        bundle.putString("p_name", p_name);
        bundle.putInt("resource_id", resource_id);
        bundle.putInt("fixedNum", fixedNum);
        bundle.putInt("position", position);
        starter.putExtras(bundle);
        context.startActivityForResult(starter, requestCode);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_appointment_time;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        p_id = getIntent().getExtras().getInt("p_id");
        resource_id = getIntent().getExtras().getInt("resource_id");
        String p_name = getIntent().getExtras().getString("p_name");
        fixedNum = getIntent().getExtras().getInt("fixedNum");
        mPosition = getIntent().getExtras().getInt("position");
        Constants.mFixNum = fixedNum;
        tvCarNum.setText(fixedNum + "张");
        tvType.setText(p_name);
        showBookingToast(1);
        RequestManager.getInstance().apptFixedTop(p_id, new GetApptFixedCallback() {
            @Override
            public void onSuccess(TimeBean list) {
                String time = CalendarUtil.timeStamp2Date(list.getToday_time() * 1000, "");
                Date date = CalendarUtil.parseServerTime(time, "");
                String time1 = CalendarUtil.getOldDateByDay(date, 30, "");
                String time2 = time1.replaceAll("-", ".");
                String time3 = time.replaceAll("-", ".");
                cDate = CalendarUtil.strToArray(time3);
                HashMap<String, String> map = new HashMap<>();
                for (int i = 0; i < list.getList().size(); i++) {
                    if (list.getList().get(i).getCount() == 2) {
                        String time4 = CalendarUtil.timeStamp2Date(list.getList().get(i).getReservation_time() * 1000, "");
                        String time5 = time4.replaceAll("-", ".");
                        map.put(time5, "满");
                    }
                }
                calendarView
                        .setMan(map)//将显示农历的区域替换成指定文字
                        .setStartEndDate(time3, time2)//设置日历开始、结束年月
                        .setDisableStartEndDate(time3, time2)//设置日历的禁用日期范围（小于startDate、大于endDate禁用）
                        .setInitDate(cDate[0] + "." + cDate[1])//设置日历的初始显示年月
//                        .setSingleDate(time3)//设置单选时初始选中的日期（不设置则不默认选中）
                        .init();
                title.setText(cDate[0] + "年" + cDate[1] + "月");
                initImag();
                dismissBookingToast();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
                ToastUtils.showCentetToast(AppointmentTimeActivity.this, msg);
            }

        });

        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "年" + date[1] + "月");
            }
        });

//        calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
//            @Override
//            public void onSingleChoose(View view, DateBean date) {
//                title.setText(date.getSolar()[0] + "年" + date.getSolar()[1] + "月");
//                if (date.getType() == 1) {
//                    tvTime.setText(date.getSolar()[0] + "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日");
//                    try {
//                        time = CalendarUtil.dateToStamp(date.getSolar()[0] + "-" + date.getSolar()[1] + "-" + date.getSolar()[2]);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
        calendarView.setOnMultiChooseListener(new OnMultiChooseListener() {
            @Override
            public void onMultiChoose(View view, DateBean date, boolean flag) {

                List<DateBean> dateBeans = calendarView.getMultiDate();
                int num = fixedNum - dateBeans.size();
                tvCarNum.setText(num + "张");
                mTimes.clear();
                String timeString = "";
                for (int i = 0; i < dateBeans.size(); i++) {
                    timeString = timeString + "、" + dateBeans.get(i).getSolar()[1] + "月" + dateBeans.get(i).getSolar()[2] + "日";

                    try {
                        long times = CalendarUtil.dateToStamp(dateBeans.get(i).getSolar()[0] + "-" + dateBeans.get(i).getSolar()[1] + "-" + dateBeans.get(i).getSolar()[2]);
                        mTimes.add((int) (times / 1000));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                tvTime.setText(timeString.replaceFirst("、", ""));
                // Log.i("0000000====", mTimes.toString());
            }
        });
    }

    @OnClick({R.id.bt_back, R.id.tv_sure, R.id.img_left, R.id.img_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_back:
                finish();
                break;
            case R.id.tv_sure:
                if (StringUtils.isEmpty(tvTime.getText().toString())) {
                    ToastUtils.showCentetToast(AppointmentTimeActivity.this, "请选择预约日期");
                    return;
                }
                fixed();

                //预约
                break;
            case R.id.img_left:
                calendarView.lastMonth();
                initImag();
                break;
            case R.id.img_right:
                calendarView.nextMonth();
                initImag();
                break;
        }
    }

    private void initImag() {
        if (calendarView.hasLast()) {
            imgLeft.setVisibility(View.VISIBLE);
        } else {
            imgLeft.setVisibility(View.INVISIBLE);
        }


        if (calendarView.hasNext()) {
            imgRight.setVisibility(View.VISIBLE);
        } else {
            imgRight.setVisibility(View.INVISIBLE);
        }
    }

    private void fixed() {
        RequestManager.getInstance().fixedTop(resource_id, p_id, mTimes.toString(), new RequestCallback<String>() {
            @Override
            public void requestStart(Call call) {
                showBookingToast(2);
                mCall = call;
            }

            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                AppointmentBean appointmentBean = gson.fromJson(s, AppointmentBean.class);
                int n = fixedNum - appointmentBean.getFixed_top_card_num();

                ToastUtils.showCentetImgToast(AppointmentTimeActivity.this, "预约成功，扣除置顶卡" + n + "张");
                Bundle bundle = new Bundle();
                Intent intent = new Intent();
                bundle.putString("data", s);
                bundle.putInt("position", mPosition);
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailed(int code, String msg) {

                ToastUtils.showCentetImgToast(AppointmentTimeActivity.this, msg);

            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
