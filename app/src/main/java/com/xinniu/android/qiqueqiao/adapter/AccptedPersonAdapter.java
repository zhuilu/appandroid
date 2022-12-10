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

        int is_settlement = item.getIs_settlement();//是否结算，1：结算，0：未结算
        int received_status = item.getStatus();//0:接单中，1：完成，2：取消
        int is_cancel = item.getIs_cancel();//发布人取消接单，0：未取消，1：取消，2：取消中
        int is_service = item.getIs_service();//	是否客服介入 0：未，1：介入中，2：介入完成
        if (is_settlement == 1) {
            //已结算
            helper.setText(R.id.tv_status, "已结算");
            helper.getView(R.id.tv_01).setVisibility(View.GONE);
            helper.getView(R.id.tv_02).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_04).setVisibility(View.GONE);
            //判断是否申请客服介入
            if (is_service == 0) {
                //未介入
                helper.getView(R.id.tv_03).setVisibility(View.GONE);

            } else {
                //介入中或者结束
                helper.getView(R.id.tv_03).setVisibility(View.VISIBLE);
            }
        } else {
            if (received_status == 2) {
                //对方取消订单
                helper.setText(R.id.tv_status, "已取消");
                helper.getView(R.id.tv_01).setVisibility(View.GONE);
                helper.getView(R.id.tv_02).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_03).setVisibility(View.GONE);
                helper.getView(R.id.tv_04).setVisibility(View.GONE);
            } else if (received_status == 0) {
                //接单中
                //自己是否申请取消
                if (is_cancel == 1) {
                    //已取消
                    helper.setText(R.id.tv_status, "已取消");
                    helper.getView(R.id.tv_01).setVisibility(View.GONE);
                    helper.getView(R.id.tv_02).setVisibility(View.VISIBLE);
                    helper.getView(R.id.tv_03).setVisibility(View.GONE);
                    helper.getView(R.id.tv_04).setVisibility(View.GONE);
                } else if (is_cancel == 2) {
                    //取消中
                    helper.getView(R.id.tv_01).setVisibility(View.VISIBLE);
                    helper.getView(R.id.tv_02).setVisibility(View.VISIBLE);
                    helper.getView(R.id.tv_04).setVisibility(View.GONE);
                    //判断是否申请客服介入
                    if (is_service == 0) {
                        //未介入
                        helper.setText(R.id.tv_status, "等待对方同意");
                        helper.getView(R.id.tv_03).setVisibility(View.GONE);

                    } else {
                        //介入中或者结束
                        helper.setText(R.id.tv_status, "申请客服介入中");
                        helper.getView(R.id.tv_03).setVisibility(View.VISIBLE);
                    }

                } else if (is_cancel == 0) {
                    //判断是否申请客服介入
                    //正常状态

                    if (is_service == 0) {
                        //未介入
                        helper.setText(R.id.tv_status, "已接单");
                        helper.getView(R.id.tv_01).setVisibility(View.VISIBLE);
                        helper.getView(R.id.tv_02).setVisibility(View.VISIBLE);
                        helper.getView(R.id.tv_03).setVisibility(View.GONE);
                        helper.getView(R.id.tv_04).setVisibility(View.VISIBLE);
                    } else {
                        helper.setText(R.id.tv_status, "申请客服介入中");
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
