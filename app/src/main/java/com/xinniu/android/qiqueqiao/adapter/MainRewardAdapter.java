package com.xinniu.android.qiqueqiao.adapter;

import android.app.Activity;
//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.activity.RewardDetailActivity;
import com.xinniu.android.qiqueqiao.bean.MainBean;
import com.xinniu.android.qiqueqiao.bean.RewardListBean;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.List;

/**
 * Created by yuchance on 2018/3/30.
 */

public class MainRewardAdapter extends BaseQuickAdapter<MainBean.RewardBean, BaseViewHolder> {
    private Activity context;

    public MainRewardAdapter(Activity context, int layoutResId, @Nullable List<MainBean.RewardBean> data) {
        super(layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, final MainBean.RewardBean item) {

        if (StringUtils.isEmpty(item.getCompany()) && StringUtils.isEmpty(item.getPosition())) {
            helper.setText(R.id.item_index_position, "");

        } else {
            if (StringUtils.isEmpty(item.getCompany())) {
                helper.setText(R.id.item_index_position, " · " + item.getPosition());

            } else if (StringUtils.isEmpty(item.getPosition())) {
                helper.setText(R.id.item_index_position, " · " + item.getCompany());

            } else {
                helper.setText(R.id.item_index_position, " · " + item.getCompany() + item.getPosition());

            }
        }
        helper.setText(R.id.item_index_nameTv, item.getRealname());
        ImageLoader.loadAvter(item.getHead_pic(), (CircleImageView) helper.getView(R.id.item_index_recycler_img));
        if (item.isU()) {
            helper.getView(R.id.view).setVisibility(View.VISIBLE);

        } else {
            helper.getView(R.id.view).setVisibility(View.GONE);

        }
        TextView nameTv = helper.getView(R.id.item_index_nameTv);
        View view = helper.getView(R.id.view);
        if (item.getIs_vip() == 1) {

            nameTv.setTextColor(ContextCompat.getColor(context, R.color.king_color));
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.king_color));
        } else if (item.getIs_vip() == 0) {

            nameTv.setTextColor(ContextCompat.getColor(context, R.color._333));
            view.setBackgroundColor(ContextCompat.getColor(context, R.color._333));

        } else if (item.getIs_vip() == 2) {

            nameTv.setTextColor(ContextCompat.getColor(context, R.color.king_color));
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.king_color));

        }


        helper.setText(R.id.tv_title, item.getTitle());

        if (item.getAmount().contains(".")) {
            String[] pricr01 = item.getAmount().split("\\.");
            helper.setText(R.id.tv_price, pricr01[0]);
        } else {
            helper.setText(R.id.tv_price, item.getAmount());
        }
        helper.getView(R.id.rlayout_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonCentetActivity.start(context, item.getUser_id() + "");
            }
        });

        helper.getView(R.id.llayout_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isLogin()) {
                    return;
                }
                RewardDetailActivity.start(context, item.getOrder_sn());
            }
        });
    }

    private boolean isLogin() {
        if (!UserInfoHelper.getIntance().isLogin()) {
            // TODO: 2017/12/20
//            ToastUtils.showCentetImgToast(getContext(), "还未登录");
            LoginNewActivity.start(mContext);
        }
        return UserInfoHelper.getIntance().isLogin();
    }
}
