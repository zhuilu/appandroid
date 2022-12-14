package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.TransactionDetailsActivity;
import com.xinniu.android.qiqueqiao.bean.CashWithdrawalBean;
import com.xinniu.android.qiqueqiao.bean.GuaranteeOrderBean;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/6/22.
 */

public class MyTransactionOrdersAdapter extends BaseQuickAdapter<GuaranteeOrderBean.ListBean, BaseViewHolder> {

    private List<GuaranteeOrderBean.ListBean> datas = new ArrayList<>();
    private Context mContext;

    public MyTransactionOrdersAdapter(Context mContext, int layoutResId, @Nullable List<GuaranteeOrderBean.ListBean> data) {
        super(layoutResId, data);
        this.datas = data;
        this.mContext = mContext;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GuaranteeOrderBean.ListBean item) {
        helper.setText(R.id.tv_time, TimeUtils.time2ActTime(item.getCreate_time() * 1000));
        helper.setText(R.id.tv_name, item.getObj_user_name());
        helper.setText(R.id.tv_name_1, item.getObj_user_name());
        if (item.getEstimated_amount().contains(".")) {
            String[] pricr01 = item.getEstimated_amount().split("\\.");
            helper.setText(R.id.tv_price_d,  pricr01[0]);
        } else {
            helper.setText(R.id.tv_price_d, item.getEstimated_amount());
        }
        if (item.getSettlement_amount().contains(".")) {
            String[] pricr01 = item.getSettlement_amount().split("\\.");
            helper.setText(R.id.tv_price_j,  pricr01[0]);
        } else {
            helper.setText(R.id.tv_price_j, item.getSettlement_amount());
        }

        //0?????????????????????????????????????????????
        // 1?????????????????????
        // 2????????????????????????
        // 3??????????????????????????????
        // 4????????????????????????????????????
        // 5?????????????????????????????????
        // 6???????????????????????????????????????
        // 7???????????????????????????
        //8?????????????????????
        //9??????????????????????????????
        int status = item.getStatus();
        int is_initiate = item.getIs_initiate();   //???????????????
        int party_a_user_id = item.getParty_a_user_id();//??????UID ??????
        int mUserId = UserInfoHelper.getIntance().getUserId();//?????????????????????id
        boolean isBuyer;//???????????????????????????
        if (party_a_user_id == mUserId) {
            isBuyer = true;
        } else {
            isBuyer = false;
        }
        RelativeLayout rlayout_price_d = helper.getView(R.id.rlayout_price_d);
        RelativeLayout rlayout_price_j = helper.getView(R.id.rlayout_price_j);
        LinearLayout llayout_button = helper.getView(R.id.llayout_button);
        final TextView btn_01 = helper.getView(R.id.btn_01);
        final TextView btn_02 = helper.getView(R.id.btn_02);
        TextView tv_person = helper.getView(R.id.tv_person);
        TextView tv_name = helper.getView(R.id.tv_name);

        if (status == 0) {
            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.GONE);
            llayout_button.setVisibility(View.VISIBLE);
            tv_person.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            if (is_initiate == 1) {
                helper.setText(R.id.tv_status, "?????????????????????");
                btn_02.setVisibility(View.GONE);
                btn_01.setVisibility(View.VISIBLE);
                btn_01.setText("????????????");
                btn_01.setBackgroundResource(R.drawable.bg_trans_gray);
                btn_01.setTextColor(mContext.getResources().getColor(R.color._333));

            } else {
                helper.setText(R.id.tv_status, "?????????");
                btn_02.setVisibility(View.VISIBLE);
                btn_01.setVisibility(View.VISIBLE);
                btn_01.setText("????????????");
                btn_01.setBackgroundResource(R.drawable.bg_trans_gray);
                btn_01.setTextColor(mContext.getResources().getColor(R.color._333));
                btn_02.setText("????????????");
                btn_02.setBackgroundResource(R.drawable.bg_trans_blue);
                btn_02.setTextColor(mContext.getResources().getColor(R.color.blue_bg_4B96F3));

            }

        } else if (status == 1) {
            rlayout_price_d.setVisibility(View.VISIBLE);
            llayout_button.setVisibility(View.GONE);
            if (item.getRefund_status() == 0) {
                helper.setText(R.id.tv_status, "????????????");
                rlayout_price_j.setVisibility(View.GONE);
                tv_person.setVisibility(View.VISIBLE);
                tv_name.setVisibility(View.VISIBLE);
            } else if (item.getRefund_status() == 2) {
                helper.setText(R.id.tv_status, "????????????");
                rlayout_price_j.setVisibility(View.VISIBLE);
                tv_person.setVisibility(View.INVISIBLE);
                tv_name.setVisibility(View.INVISIBLE);
            }


        } else if (status == 2) {
            // 2????????????????????????
            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.GONE);
            llayout_button.setVisibility(View.VISIBLE);
            tv_person.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            btn_02.setVisibility(View.GONE);
            btn_01.setVisibility(View.VISIBLE);
            if (isBuyer) {
                //????????????,??????
                helper.setText(R.id.tv_status, "?????????");
                btn_01.setText("?????????");
                btn_01.setBackgroundResource(R.drawable.bg_trans_blue);
                btn_01.setTextColor(mContext.getResources().getColor(R.color.blue_bg_4B96F3));

            } else {
                //???????????????????????????
                helper.setText(R.id.tv_status, "?????????????????????");
                btn_01.setText("????????????");
                btn_01.setBackgroundResource(R.drawable.bg_trans_gray);
                btn_01.setTextColor(mContext.getResources().getColor(R.color._333));

            }

        } else if (status == 3) {
            //???????????????????????????
            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.GONE);
            llayout_button.setVisibility(View.VISIBLE);
            tv_person.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            btn_02.setVisibility(View.GONE);
            btn_01.setVisibility(View.VISIBLE);
            if (isBuyer) {
                //????????????,??????
                helper.setText(R.id.tv_status, "?????????");
                btn_01.setText("????????????");
                btn_01.setBackgroundResource(R.drawable.bg_trans_organe);
                btn_01.setTextColor(mContext.getResources().getColor(R.color.orange));

            } else {
                //???????????????????????????
                helper.setText(R.id.tv_status, "?????????????????????");
                btn_01.setText("????????????");
                btn_01.setBackgroundResource(R.drawable.bg_trans_gray);
                btn_01.setTextColor(mContext.getResources().getColor(R.color._333));

            }

        } else if (status == 4) {
            //4?????????????????????????????????
            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.VISIBLE);
            llayout_button.setVisibility(View.VISIBLE);
            tv_person.setVisibility(View.INVISIBLE);
            tv_name.setVisibility(View.INVISIBLE);

            if (isBuyer) {
                helper.setText(R.id.tv_status, "?????????");
                btn_02.setVisibility(View.VISIBLE);
                btn_01.setVisibility(View.VISIBLE);
                btn_01.setText("????????????");
                btn_01.setBackgroundResource(R.drawable.bg_trans_blue);
                btn_01.setTextColor(mContext.getResources().getColor(R.color.blue_bg_4B96F3));
                btn_02.setText("????????????");
                btn_02.setBackgroundResource(R.drawable.bg_trans_blue);
                btn_02.setTextColor(mContext.getResources().getColor(R.color.blue_bg_4B96F3));
                if (item.getRefund_status() == 1) {
                    //?????????????????????
                    btn_02.setVisibility(View.GONE);
                    btn_01.setVisibility(View.GONE);
                    helper.setText(R.id.tv_status, "?????????");
                } else {

                }
            } else {
                helper.setText(R.id.tv_status, "???????????????");
                btn_02.setVisibility(View.GONE);
                btn_01.setVisibility(View.VISIBLE);
                btn_01.setText("????????????");
                btn_01.setBackgroundResource(R.drawable.bg_trans_blue);
                btn_01.setTextColor(mContext.getResources().getColor(R.color.blue_bg_4B96F3));
                if (item.getRefund_status() == 1) {
                    //?????????????????????
                    btn_02.setVisibility(View.GONE);
                    btn_01.setVisibility(View.GONE);
                    helper.setText(R.id.tv_status, "?????????");
                } else {

                }

            }

        } else if (status == 5) {
            //???????????????????????????????????????
            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.GONE);
            llayout_button.setVisibility(View.GONE);
            tv_person.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_status, "???????????????");

        } else if (status == 6) {
            //?????????????????????????????????????????????????????????   ?????????cancel_user_id????????????
            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.GONE);
            llayout_button.setVisibility(View.GONE);
            tv_person.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_status, "???????????????");
        } else if (status == 7) {
            //??????????????????????????????
            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.GONE);
            llayout_button.setVisibility(View.GONE);
            tv_person.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_status, "???????????????");
        } else if (status == 8) {
            //?????????????????????
            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.GONE);
            llayout_button.setVisibility(View.GONE);
            tv_person.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_status, "???????????????");
        } else if (status == 9) {
            //???????????????????????????????????????

            rlayout_price_d.setVisibility(View.VISIBLE);
            rlayout_price_j.setVisibility(View.VISIBLE);
            llayout_button.setVisibility(View.GONE);
            tv_person.setVisibility(View.INVISIBLE);
            tv_name.setVisibility(View.INVISIBLE);
            helper.setText(R.id.tv_status, "????????????");

        }

        btn_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setOnClick != null) {
                    setOnClick.setOnClickListeren(item, btn_01.getText().toString());
                }
            }
        });

        btn_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setOnClick != null) {
                    setOnClick.setOnClickListeren(item, btn_02.getText().toString());
                }
            }
        });
        helper.getView(R.id.llayout_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setOnClick != null) {
                    setOnClick.setOnClickListeren(item, "1");
                }


            }
        });
    }


    public interface setOnClick {
        void setOnClickListeren(GuaranteeOrderBean.ListBean listeren, String type);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(MyTransactionOrdersAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
