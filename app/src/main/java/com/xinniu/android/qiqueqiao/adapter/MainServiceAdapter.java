package com.xinniu.android.qiqueqiao.adapter;

import android.app.Activity;
//import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.ServiceDetailActivity;
import com.xinniu.android.qiqueqiao.bean.IndexServiceBean;
import com.xinniu.android.qiqueqiao.bean.MainBean;
import com.xinniu.android.qiqueqiao.customs.NiceImageView;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.RoundImageView;

import java.util.List;

/**
 * Created by yuchance on 2018/3/30.
 */

public class MainServiceAdapter extends BaseQuickAdapter<MainBean.ServiceProviderBean, BaseViewHolder> {


    private Activity context;

    public MainServiceAdapter(Activity context, int layoutResId, @Nullable List<MainBean.ServiceProviderBean> data) {
        super(layoutResId, data);
        this.context = context;

    }


    @Override
    protected void convert(BaseViewHolder helper, final MainBean.ServiceProviderBean item) {
        if(helper.getAdapterPosition()==0){
            helper.getView(R.id.v).setVisibility(View.VISIBLE);
        }else{
            helper.getView(R.id.v).setVisibility(View.GONE);
        }
        String[] sourceStrArray = item.getImages().split(",");
        RoundImageView roundImageView=  helper.getView(R.id.service_img);
        roundImageView.setmBorderRadius(2);
        ImageLoader.loadLocalImg(sourceStrArray[0], (RoundImageView) helper.getView(R.id.service_img));
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_company_name, item.getBrand());
        String[] tags = item.getRemark().split(",");
        helper.setText(R.id.tv_type, tags[0]);
        ImageLoader.loadCompanyHead(item.getLogo(), (NiceImageView) helper.getView(R.id.company_img));

        helper.getView(R.id.rlayout_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceDetailActivity.start(context, item.getId());
            }
        });

    }


}
