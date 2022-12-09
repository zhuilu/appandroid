package com.xinniu.android.qiqueqiao.adapter.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.MyCircleBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.List;

/**
 * Created by qinlei
 * Created on 2017/12/12
 * Created description :
 */

public class MyCircleAdapter extends BDRecyclerViewAdapter {
    private Context mContext;

    public MyCircleAdapter(Context context, List<MyCircleBean> datas) {
        super(context, R.layout.circle_mine_item, datas);
        this.mContext = context;
    }


    @Override
    public void convert(BDRecylerViewHolder holder, Object o) {
        final MyCircleBean myCircleBean = (MyCircleBean) o;
        FrameLayout layout = holder.getView(R.id.item_view);
        CircleImageView imagehead = holder.getView(R.id.image_head);
        TextView tvName = holder.getView(R.id.tv_name);
        TextView tvTime = holder.getView(R.id.tv_time);
        TextView tvNum = holder.getView(R.id.tv_num);
        RatingBar starView = holder.getView(R.id.star_view);
        ImageView imageTip = holder.getView(R.id.image_tip);

//        Picasso.with(BaseApp.getInstance()).load(myCircleBean.getHead_pic()).into(imagehead);
        ImageLoader.loadImage(myCircleBean.getHead_pic(),imagehead);
        if (!TextUtils.isEmpty(myCircleBean.getName())) {
            tvName.setText(myCircleBean.getName());
        }
        tvTime.setText(TimeUtils.millis2String(myCircleBean.getCreate_time()));
        if (!TextUtils.isEmpty(myCircleBean.getNum() + "")) {
            tvNum.setText(myCircleBean.getNum() + "");
        }

        starView.setNumStars(myCircleBean.getLevel());
        starView.setRating(myCircleBean.getLevel());

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ConversationActivity.start(mContext,myCircleBean.getId(),myCircleBean.getName(),myCircleBean.getHead_pic(),
//                        myCircleBean.getLevel(),myCircleBean.getNum(),myCircleBean.getNotice(),myCircleBean.getShare_url());
            }
        });
        // TODO: 2017/12/19 红点的显示隐藏
    }
}
