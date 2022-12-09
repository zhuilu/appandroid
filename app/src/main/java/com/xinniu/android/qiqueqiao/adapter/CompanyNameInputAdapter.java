package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.CompanyNameBean;
import com.xinniu.android.qiqueqiao.bean.CompanyNameNewBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/5/15.
 */

public class CompanyNameInputAdapter extends BaseQuickAdapter<CompanyNameNewBean.ResultBean.ItemsBean,BaseViewHolder> {

    private List<CompanyNameNewBean.ResultBean.ItemsBean> data = new ArrayList<>();
    private Context mContext;

    public CompanyNameInputAdapter(Context context,int layoutResId, @Nullable List<CompanyNameNewBean.ResultBean.ItemsBean> data) {
        super(layoutResId, data);
        this.data = data;
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CompanyNameNewBean.ResultBean.ItemsBean item) {

        helper.setText(R.id.item_company_nameTv,item.getName().replaceAll("<em>","").replaceAll("</em>",""));
        if (helper.getAdapterPosition()==data.size()){
            helper.setVisible(R.id.item_company_line,false);
        }else {
            helper.setVisible(R.id.item_company_line,true);
        }
        (helper.getView(R.id.bitem_companyRl)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCompanyName.setCompanyName(item.getName().replaceAll("<em>","").replaceAll("</em>",""));
            }
        });

    }

    private SetCompanyName setCompanyName;


    public interface SetCompanyName{
     void setCompanyName(String company);
    }

    public void setSetCompanyName(SetCompanyName setCompanyName) {
        this.setCompanyName = setCompanyName;
    }
}
