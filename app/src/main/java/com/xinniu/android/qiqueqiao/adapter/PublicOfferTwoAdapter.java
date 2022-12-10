package com.xinniu.android.qiqueqiao.adapter;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GetReleaseTemplateNewBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yuchance on 2018/12/27.
 */

public class PublicOfferTwoAdapter extends BaseQuickAdapter<GetReleaseTemplateNewBean.ProvideCategoryBean.SystemCategoryBean.ListBean, BaseViewHolder> {

    private AppCompatActivity context;
    private List<GetReleaseTemplateNewBean.ProvideCategoryBean.SystemCategoryBean.ListBean> mData = new ArrayList<>();

    public PublicOfferTwoAdapter(AppCompatActivity context, int layoutResId, @Nullable List<GetReleaseTemplateNewBean.ProvideCategoryBean.SystemCategoryBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GetReleaseTemplateNewBean.ProvideCategoryBean.SystemCategoryBean.ListBean item) {

        helper.setText(R.id.item_label_type,item.getName());
        if (item.isCheck()){
            helper.getView(R.id.item_label_type).setSelected(true);
        }else {
            helper.getView(R.id.item_label_type).setSelected(false);
        }

        helper.getView(R.id.item_label_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isCheck()){
                    item.setCheck(false);
                }else {
                    List<Integer> check = new ArrayList<>();
                    for (int i = 0; i < mData.size(); i++) {
                        if (mData.get(i).isCheck()){
                            check.add(mData.get(i).getId());
                        }
                    }
                    if (check.size()>2){
                        setAddOnClick.setAddOnClick(2);
                        return;
                    }
                    item.setCheck(true);
                }
                notifyDataSetChanged();
            }
        });
    }

    public interface setAddOnClick{
        void setAddOnClick(int isType);
    }
    private setAddOnClick setAddOnClick;

    public void setSetAddOnClick(setAddOnClick setAddOnClick) {
        this.setAddOnClick = setAddOnClick;
    }
}
