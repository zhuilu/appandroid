package com.xinniu.android.qiqueqiao.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.bean.AcceptedOrdersPersonBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.StringUtils;

import java.util.List;
//import android.support.v4.content.ContextCompat;

/**
 * Created by yuchance on 2018/3/30.
 */

public class AccptedPersonAdapter extends BaseQuickAdapter<AcceptedOrdersPersonBean.TakingOrderListBean, BaseViewHolder> {
    private AppCompatActivity context;
    private Callback callback;

    public AccptedPersonAdapter(AppCompatActivity context, int layoutResId, @Nullable List<AcceptedOrdersPersonBean.TakingOrderListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AcceptedOrdersPersonBean.TakingOrderListBean item) {

        if (StringUtils.isEmpty(item.getCompany()) && StringUtils.isEmpty(item.getPosition())) {
            helper.setText(R.id.lx_positiontv, "");

        } else {
            if (StringUtils.isEmpty(item.getCompany())) {
                helper.setText(R.id.lx_positiontv, item.getPosition());

            } else if (StringUtils.isEmpty(item.getPosition())) {
                helper.setText(R.id.lx_positiontv, item.getCompany());

            } else {
                helper.setText(R.id.lx_positiontv, item.getCompany() + "|" + item.getPosition());

            }
        }
        helper.setText(R.id.lx_nametv, item.getRealname());
        ImageLoader.loadAvter(item.getHead_pic(), (ImageView) helper.getView(R.id.item_lx_headimg));
        TextView nameTv = helper.getView(R.id.lx_nametv);

        if (item.getIs_vip() == 1) {
            helper.getView(R.id.item_index_vipImg).setVisibility(View.VISIBLE);
            helper.setBackgroundRes(R.id.item_index_vipImg, R.mipmap.vip_iconx);
            nameTv.setTextColor(ContextCompat.getColor(context, R.color.king_color));

        } else if (item.getIs_vip() == 0) {
            helper.getView(R.id.item_index_vipImg).setVisibility(View.GONE);
            nameTv.setTextColor(ContextCompat.getColor(context, R.color._333));

        } else if (item.getIs_vip() == 2) {
            helper.getView(R.id.item_index_vipImg).setVisibility(View.VISIBLE);
            helper.setBackgroundRes(R.id.item_index_vipImg, R.mipmap.svip_iconx);
            nameTv.setTextColor(ContextCompat.getColor(context, R.color.king_color));

        }

        int is_settlement = item.getIs_settlement();//???????????????1????????????0????????????
        int received_status = item.getStatus();//0:????????????1????????????2?????????
        int is_cancel = item.getIs_cancel();//????????????????????????0???????????????1????????????2????????????
        int is_service = item.getIs_service();//	?????????????????? 0?????????1???????????????2???????????????
        if (is_settlement == 1) {
            //?????????
            helper.setText(R.id.tv_status, "?????????");
            helper.getView(R.id.tv_01).setVisibility(View.GONE);
            helper.getView(R.id.tv_02).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_04).setVisibility(View.GONE);
            //??????????????????????????????
            if (is_service == 0) {
                //?????????
                helper.getView(R.id.tv_03).setVisibility(View.GONE);

            } else {
                //?????????????????????
                helper.getView(R.id.tv_03).setVisibility(View.VISIBLE);
            }
        } else {
            if (received_status == 2) {
                //??????????????????
                helper.setText(R.id.tv_status, "?????????");
                helper.getView(R.id.tv_01).setVisibility(View.GONE);
                helper.getView(R.id.tv_02).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_03).setVisibility(View.GONE);
                helper.getView(R.id.tv_04).setVisibility(View.GONE);
            } else if (received_status == 0) {
                //?????????
                //????????????????????????
                if (is_cancel == 1) {
                    //?????????
                    helper.setText(R.id.tv_status, "?????????");
                    helper.getView(R.id.tv_01).setVisibility(View.GONE);
                    helper.getView(R.id.tv_02).setVisibility(View.VISIBLE);
                    helper.getView(R.id.tv_03).setVisibility(View.GONE);
                    helper.getView(R.id.tv_04).setVisibility(View.GONE);
                } else if (is_cancel == 2) {
                    //?????????
                    helper.getView(R.id.tv_01).setVisibility(View.VISIBLE);
                    helper.getView(R.id.tv_02).setVisibility(View.VISIBLE);
                    helper.getView(R.id.tv_04).setVisibility(View.GONE);
                    //??????????????????????????????
                    if (is_service == 0) {
                        //?????????
                        helper.setText(R.id.tv_status, "??????????????????");
                        helper.getView(R.id.tv_03).setVisibility(View.GONE);

                    } else {
                        //?????????????????????
                        helper.setText(R.id.tv_status, "?????????????????????");
                        helper.getView(R.id.tv_03).setVisibility(View.VISIBLE);
                    }

                } else if (is_cancel == 0) {
                    //??????????????????????????????
                    //????????????

                    if (is_service == 0) {
                        //?????????
                        helper.setText(R.id.tv_status, "?????????");
                        helper.getView(R.id.tv_01).setVisibility(View.VISIBLE);
                        helper.getView(R.id.tv_02).setVisibility(View.VISIBLE);
                        helper.getView(R.id.tv_03).setVisibility(View.GONE);
                        helper.getView(R.id.tv_04).setVisibility(View.VISIBLE);
                    } else {
                        helper.setText(R.id.tv_status, "?????????????????????");
                        helper.getView(R.id.tv_01).setVisibility(View.VISIBLE);
                        helper.getView(R.id.tv_02).setVisibility(View.VISIBLE);
                        helper.getView(R.id.tv_03).setVisibility(View.VISIBLE);
                        helper.getView(R.id.tv_04).setVisibility(View.GONE);
                    }
                }


            }


        }

        helper.getView(R.id.tv_01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onEdit(helper.getAdapterPosition(), "1", item);
                }
            }
        });
        helper.getView(R.id.tv_02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onEdit(helper.getAdapterPosition(), "2", item);
                }

            }
        });
        helper.getView(R.id.tv_03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onEdit(helper.getAdapterPosition(), "3", item);
                }
            }
        });
        helper.getView(R.id.tv_04).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onEdit(helper.getAdapterPosition(), "4", item);
                }
            }
        });
        helper.getView(R.id.rlayout_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonCentetActivity.start(mContext, item.getUser_id() + "");
            }
        });
    }

    public interface Callback {
        void onEdit(int position, String isUp, AcceptedOrdersPersonBean.TakingOrderListBean item);
    }
}
