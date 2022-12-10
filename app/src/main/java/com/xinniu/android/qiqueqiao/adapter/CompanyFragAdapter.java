package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CompanyInfoActivity;
import com.xinniu.android.qiqueqiao.bean.CompanyListsBean;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.List;

/**
 * Created by yuchance on 2018/5/7.
 */

public class CompanyFragAdapter extends BaseQuickAdapter<CompanyListsBean.ListBean,BaseViewHolder> {

    private Context mContext;


    public CompanyFragAdapter(Context context,int layoutResId, @Nullable List<CompanyListsBean.ListBean> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CompanyListsBean.ListBean item) {
        (helper.getView(R.id.bitem_company_go)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(mContext, "company_list");//统计banner点击次数
                CompanyInfoActivity.start(mContext,item.getId());
            }
        });
        ImageLoader.loadCompanyHead(item.getLogo(), (ImageView) helper.getView(R.id.mitem_company_cell_headImg));

        helper.setText(R.id.mitem_company_cell_nameTv,item.getBrand());
        helper.setText(R.id.mitem_company_cell_companyTv,item.getName());
      //  helper.setText(R.id.mnum_coop,item.getResources_count()+"条合作信息"+" · "+item.getUser_num()+"人"+" · "+item.getCompany_name());

        helper.setText(R.id.mnum_coop_num,item.getResources_count()+"");
        helper.setText(R.id.mnum_coop_person,item.getUser_num()+"");
        helper.setText(R.id.mnum_coop,item.getCompany_name());

    }

}
