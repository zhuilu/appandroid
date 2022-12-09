package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
//import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.MyPushBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.List;

/**
 * Created by yuchance on 2019/2/23.
 */

public class SendCoopInfoAdapter extends BaseQuickAdapter<MyPushBean.ListBean,BaseViewHolder> {

    private Context context;
    private String company;

    public void setCompany(String company) {
        this.company = company;
    }

    public SendCoopInfoAdapter(Context context, int layoutResId, @Nullable List<MyPushBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyPushBean.ListBean item) {

        ImageLoader.loadAvter(item.getP_img(), (ImageView) helper.getView(R.id.msend_coopimg));

        helper.setText(R.id.msend_typetv,item.getP_name());
        helper.setText(R.id.msend_cooptitletv,"【"+company+"】"+item.getTitle());
        helper.getView(R.id.bitem_sendLl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setOnClick(item.getId());
            }
        });



    }

    public interface setOnClick{
        void setOnClick(int id);
    }
    private setOnClick setOnClick;

    public void setSetOnClick(SendCoopInfoAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
