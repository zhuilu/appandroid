package com.xinniu.android.qiqueqiao.adapter;

import android.app.Activity;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GetReleaseTemplateNewBean;
import com.xinniu.android.qiqueqiao.customs.NestedRecyclerView;
import com.xinniu.android.qiqueqiao.customs.label.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/12/27.
 */

public class PublicNeedTypeAdapter extends BaseQuickAdapter<GetReleaseTemplateNewBean.NeedCategoryBean.SystemCategoryBeanX, BaseViewHolder> {

    private Activity context;
    private List<GetReleaseTemplateNewBean.NeedCategoryBean.SystemCategoryBeanX> mData = new ArrayList<>();

    public PublicNeedTypeAdapter(Activity context, int layoutResId, @Nullable List<GetReleaseTemplateNewBean.NeedCategoryBean.SystemCategoryBeanX> data) {
        super(layoutResId, data);
        this.context = context;
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GetReleaseTemplateNewBean.NeedCategoryBean.SystemCategoryBeanX item) {
        helper.setText(R.id.yofferTypetv, item.getTitle());

        PublicNeedTwoAdapter publicNeedTwoAdapter = new PublicNeedTwoAdapter(context, R.layout.item_label_typetv_new, item.getList());
        NestedRecyclerView recyclerView = helper.getView(R.id.type_recycler);
        recyclerView.setLayoutManager(new FlowLayoutManager());
        recyclerView.setAdapter(publicNeedTwoAdapter);
        publicNeedTwoAdapter.setSetAddOnClick(new PublicNeedTwoAdapter.setAddOnClick() {
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