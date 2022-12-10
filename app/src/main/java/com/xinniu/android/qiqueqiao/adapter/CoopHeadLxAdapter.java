package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CoopCommunicationActivity;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.bean.CoopDetailBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.List;

/**
 * Created by yuchance on 2018/10/24.
 */

public class CoopHeadLxAdapter extends BaseMultiItemQuickAdapter<CoopDetailBean.ZListBean,BaseViewHolder>{

    private Context context;
    private int resourceId;

    public CoopHeadLxAdapter(Context context, List<CoopDetailBean.ZListBean> data) {
        super(data);
        this.context = context;
        this.resourceId = resourceId;
        addItemType(CoopDetailBean.ZListBean.COMMON, R.layout.item_coop_headlx);
        addItemType(CoopDetailBean.ZListBean.MORE, R.layout.item_coop_headlx);
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CoopDetailBean.ZListBean item) {
        switch (helper.getItemViewType()){
            case CoopDetailBean.ZListBean.COMMON:
                ImageLoader.loadAvter(item.getHead_pic(), (CircleImageView) helper.getView(R.id.mcoop_headImg));
                helper.getView(R.id.mcoop_headImg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PersonCentetActivity.start(context,item.getUser_id()+"",true);
                    }
                });
                break;
            case CoopDetailBean.ZListBean.MORE:
                ((CircleImageView) helper.getView(R.id.mcoop_headImg)).setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.coop_detail_andsoon));
                helper.getView(R.id.mcoop_headImg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CoopCommunicationActivity.start(context, resourceId);
                    }
                });

                break;


        }


    }
}
