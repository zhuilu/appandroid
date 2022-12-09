package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.base.BDRecyclerViewAdapter;
import com.xinniu.android.qiqueqiao.adapter.base.BDRecylerViewHolder;
import com.xinniu.android.qiqueqiao.bean.CircleBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.List;

/**
 * Created by qinlei
 * Created on 2017/12/12
 * Created description :
 */

public class CircleFragmentHeadAdapter extends BDRecyclerViewAdapter {
    private Context mContext;

    public CircleFragmentHeadAdapter(Context context, List<CircleBean.ZListBean> datas) {
        super(context, R.layout.circle_head_item, datas);
        this.mContext = context;
    }


    @Override
    public void convert(BDRecylerViewHolder holder, Object o) {
        CircleBean.ZListBean bean = (CircleBean.ZListBean) o;
        CircleImageView circleImageView = holder.getView(R.id.image_head);
        if (!TextUtils.isEmpty(bean.getHead_pic())) {
//            Picasso
//                    .with(BaseApp.getInstance())
//                    .load(bean.getHead_pic())
//                    .into(circleImageView);
            ImageLoader.loadImage(bean.getHead_pic(),circleImageView);
        }
    }

}
