package com.xinniu.android.qiqueqiao.adapter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.ServiceCaseIndexActivity;
import com.xinniu.android.qiqueqiao.activity.ServiceDetailActivity;
import com.xinniu.android.qiqueqiao.bean.MyServicePushBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.RoundImageView;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.List;
//import android.support.annotation.RequiresApi;
//import android.support.v4.content.ContextCompat;

/**
 * Created by qinlei
 * Created on 2017/12/12
 * Created description :
 */

public class MyServiceAdapter extends BaseQuickAdapter<MyServicePushBean.ListBean, BaseViewHolder> {
    private Callback callback;
    private AppCompatActivity mContext;

    public MyServiceAdapter(AppCompatActivity context, int layoutResId, @Nullable List<MyServicePushBean.ListBean> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MyServicePushBean.ListBean item) {
        final TextView stateTv = helper.getView(R.id.tv_status);
        ImageLoader.loadAvter(item.getP_img(), (CircleImageView) helper.getView(R.id.item_typeimg));
        helper.setText(R.id.item_typenametv, item.getP_name());
        helper.setText(R.id.tv_title, item.getTitle());
        String[] sourceStrArray = item.getImages().split(",");
        ImageLoader.loadLocalImg(sourceStrArray[0], (RoundImageView) helper.getView(R.id.mcoop_detail_companyImg));
        String[] tags = item.getRemark().split(",");
        if (tags.length == 3) {
            helper.setVisible(R.id.tv_tag_1, true);
            helper.setVisible(R.id.tv_tag_2, true);
            helper.setVisible(R.id.tv_tag_3, true);
            helper.setText(R.id.tv_tag_1, tags[0]);
            helper.setText(R.id.tv_tag_2, tags[1]);
            helper.setText(R.id.tv_tag_3, tags[2]);
        } else if (tags.length == 2) {
            helper.setVisible(R.id.tv_tag_1, true);
            helper.setVisible(R.id.tv_tag_2, true);
            helper.setVisible(R.id.tv_tag_3, false);
            helper.setText(R.id.tv_tag_1, tags[0]);
            helper.setText(R.id.tv_tag_2, tags[1]);
        } else if (tags.length == 1) {
            helper.setVisible(R.id.tv_tag_1, true);
            helper.setVisible(R.id.tv_tag_2, false);
            helper.setVisible(R.id.tv_tag_3, false);
            helper.setText(R.id.tv_tag_1, tags[0]);
        }
        helper.setText(R.id.item_index_recycler_see, item.getView() + "");

        TextView editTv = helper.getView(R.id.bresource_editTv);
        final TextView refresh = helper.getView(R.id.bresource_refreshTv);
        TextView top = helper.getView(R.id.bresource_up);
        TextView downTv = helper.getView(R.id.bresource_downTv);
        TextView deleteTv = helper.getView(R.id.bresource_deleteTv);
        if (item.getIs_verify() == 0) {
            stateTv.setText("审核中");
            stateTv.setTextColor(ContextCompat.getColor(mContext, R.color.king_color));
            //编辑，删除
            editTv.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            top.setVisibility(View.GONE);
            downTv.setVisibility(View.GONE);
        } else if (item.getIs_verify() == 1) {
            if (item.getStatus() == 0) {
                stateTv.setText("已下架");
                stateTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_gray_color2));
                //编辑，删除,上架
                editTv.setVisibility(View.VISIBLE);
                refresh.setVisibility(View.GONE);
                top.setVisibility(View.VISIBLE);
                downTv.setVisibility(View.GONE);

            } else {
                stateTv.setText("上架中");
                stateTv.setTextColor(ContextCompat.getColor(mContext, R.color.bg_color_green));
                //删除，下架，编辑,刷新
                editTv.setVisibility(View.VISIBLE);
                refresh.setVisibility(View.VISIBLE);
                top.setVisibility(View.GONE);
                downTv.setVisibility(View.VISIBLE);
            }
        } else if (item.getIs_verify() == 2) {
            stateTv.setText("审核未通过");
            stateTv.setTextColor(ContextCompat.getColor(mContext, R.color.king_color));
            //编辑，删除
            editTv.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            top.setVisibility(View.GONE);
            downTv.setVisibility(View.GONE);
        }

        helper.setText(R.id.bresource_case, "服务案例 (" + item.getCase_count() + ")");
        helper.getView(R.id.bresource_case).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //服务案例
                Intent intent = new Intent(mContext, ServiceCaseIndexActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.getId());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        helper.getView(R.id.rlayout_root).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", item.getId());
                Intent intent = new Intent(mContext, ServiceDetailActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);

            }
        });
        editTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onMore(helper.getAdapterPosition(), "1");//编辑
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onMore(helper.getAdapterPosition(), "2");//刷新
            }
        });
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onMore(helper.getAdapterPosition(), "3");//上架
            }
        });
        downTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onMore(helper.getAdapterPosition(), "4");//下架
            }
        });
        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onMore(helper.getAdapterPosition(), "5");//删除
            }
        });

        //       final LinearLayout ll = helper.getView(R.id.llayout_view);
//        helper.getView(R.id.llayout_view).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (stateTv.getText().equals("已上架")) {
//                    //更多操作
//                    callback.onMore(helper.getAdapterPosition(), "1", ll);
//                } else if (stateTv.getText().toString().equals("已下架")) {
//                    callback.onMore(helper.getAdapterPosition(), "3", ll);
//                } else {
//                    callback.onMore(helper.getAdapterPosition(), "2", ll);
//                }
//            }
//        });
    }

    public interface Callback {

        void onMore(int position, String isUp);
    }
}
