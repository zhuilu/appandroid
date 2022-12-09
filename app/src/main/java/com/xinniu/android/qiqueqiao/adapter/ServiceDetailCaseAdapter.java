package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
//import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CaseDetailActivity;
import com.xinniu.android.qiqueqiao.activity.ViewpagerImageActivity;
import com.xinniu.android.qiqueqiao.bean.ServiceDetailBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/3/31.
 */

public class ServiceDetailCaseAdapter extends BaseQuickAdapter<ServiceDetailBean.ServiceCaseBean, BaseViewHolder> {

    Context context;
    ArrayList<ServiceDetailBean.ServiceCaseBean> data = new ArrayList<>();

    public ServiceDetailCaseAdapter(int layoutResId, @Nullable List<ServiceDetailBean.ServiceCaseBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
        this.data = (ArrayList<ServiceDetailBean.ServiceCaseBean>) data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ServiceDetailBean.ServiceCaseBean item) {
        ImageLoader.loadLocalImg(item.getImages(), (ImageView) helper.getView(R.id.item_photoImg));
        helper.setText(R.id.tv_name, item.getTitle());

        (helper.getView(R.id.rlayout_root)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //案例详情页面
                CaseDetailActivity.start(mContext,item.getId());

            }
        });

    }
}
