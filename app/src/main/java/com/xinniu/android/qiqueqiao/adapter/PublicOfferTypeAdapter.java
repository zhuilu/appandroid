package com.xinniu.android.qiqueqiao.adapter;

import androidx.appcompat.app.AppCompatActivity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GetReleaseTemplateNewBean;
import com.xinniu.android.qiqueqiao.customs.NestedRecyclerView;
import com.xinniu.android.qiqueqiao.customs.label.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by yuchance on 2018/12/27.
 */

public class PublicOfferTypeAdapter extends BaseQuickAdapter<GetReleaseTemplateNewBean.ProvideCategoryBean.SystemCategoryBean, BaseViewHolder> {

    private AppCompatActivity context;
    private List<GetReleaseTemplateNewBean.ProvideCategoryBean.SystemCategoryBean> mData = new ArrayList<>();

    public PublicOfferTypeAdapter(AppCompatActivity context, int layoutResId, @Nullable List<GetReleaseTemplateNewBean.ProvideCategoryBean.SystemCategoryBean> data) {
        super(layoutResId, data);
        this.context = context;
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GetReleaseTemplateNewBean.ProvideCategoryBean.SystemCategoryBean item) {
        helper.setText(R.id.yofferTypetv, item.getTitle());

        PublicOfferTwoAdapter publicOfferTwoAdapter = new PublicOfferTwoAdapter(context, R.layout.item_label_typetv_new, item.getList());
        NestedRecyclerView recyclerView = helper.getView(R.id.type_recycler);
        recyclerView.setLayoutManager(new FlowLayoutManager());
        recyclerView.setAdapter(publicOfferTwoAdapter);
        publicOfferTwoAdapter.setSetAddOnClick(new PublicOfferTwoAdapter.setAddOnClick() {
            @Override
            public void setAddOnClick(int isType) {
                setAddOnClick.setAddOnClick(2);
            }
        });

    }

    public interface setAddOnClick {
        void setAddOnClick(int isType);
    }

    private setAddOnClick setAddOnClick;

    public void setSetAddOnClick(setAddOnClick setAddOnClick) {
        this.setAddOnClick = setAddOnClick;
    }
}