package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.base.BDRecyclerViewAdapter;
import com.xinniu.android.qiqueqiao.adapter.base.BDRecylerViewHolder;
import com.xinniu.android.qiqueqiao.bean.CircleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qinlei
 * Created on 2017/12/12
 * Created description :
 */

public class CircleFragmentAdapter extends BDRecyclerViewAdapter {
    private Context mContext;

    public CircleFragmentAdapter(Context context, List<CircleBean> datas) {
        super(context, R.layout.circle_item, datas);
        this.mContext = context;
    }


    @Override
    public void convert(BDRecylerViewHolder holder, Object o) {
        CircleBean circleBean = (CircleBean) o;

        //设置背景图
        ImageView imageView = holder.getView(R.id.image_circle);
        imageView.setImageResource(getCircleResId(circleBean.getLevel()));
        ((TextView) holder.getView(R.id.circle_title)).setText(circleBean.getName());
        if (circleBean.getLevel() == 1){
            ((TextView) holder.getView(R.id.circle_title)).setTextColor(Color.parseColor("#b0b0b1"));
        }
        if (circleBean.getLevel() == 2){
            ((TextView) holder.getView(R.id.circle_title)).setTextColor(Color.parseColor("#757785"));

        }
        if (circleBean.getLevel() == 3){
            ((TextView) holder.getView(R.id.circle_title)).setTextColor(Color.parseColor("#a68b6a"));
        }
        //设置头像列表
        RecyclerView recyclerView = holder.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
        List<CircleBean.ZListBean> listBeen = new ArrayList<>();
        if (circleBean.getZ_list() != null && circleBean.getZ_list().size() > 0) {
            for (int i = 0; i < (circleBean.getZ_list().size() > 4 ? 4 : circleBean.getZ_list().size()); i++) {
                listBeen.add(circleBean.getZ_list().get(i));
            }
        }
        CircleFragmentHeadAdapter circleHeadAdapter = new CircleFragmentHeadAdapter(mContext, listBeen);
        recyclerView.setAdapter(circleHeadAdapter);

        //设置人数
        holder.setText(R.id.tv_num_people, circleBean.getNum() + "人");
        if (circleBean.getUnReadMesCount() > 0 && circleBean.getUnReadMesCount()<=99){
            holder.getView(R.id.msg_red_point).setVisibility(View.VISIBLE);
            holder.setText(R.id.msg_red_point,""+circleBean.getUnReadMesCount());
        }else if(circleBean.getUnReadMesCount()<=0){
            holder.getView(R.id.msg_red_point).setVisibility(View.GONE);
        }else{
            holder.getView(R.id.msg_red_point).setVisibility(View.VISIBLE);
            holder.setText(R.id.msg_red_point,""+circleBean.getUnReadMesCount()+"+");
        }
    }

    private int getCircleResId(int level) {
        switch (level) {
            case 1:
                return R.mipmap.bg_circle_level_3;
            case 2:
                return R.mipmap.bg_circle_level_2;
            case 3:
                return R.mipmap.bg_circle_level_1;
            default:
                return -1;
        }
    }
}
