package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.CellTagsBean;
import com.xinniu.android.qiqueqiao.customs.NestedRecyclerView;
import com.xinniu.android.qiqueqiao.customs.label.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/12/17.
 */

public class ScreenType1Adapter extends BaseQuickAdapter<CellTagsBean.NeedCategoryBean, BaseViewHolder> {

    private List<CellTagsBean.NeedCategoryBean> data = new ArrayList<>();
    private Context mContext;

    public ScreenType1Adapter(Context context, int layoutResId, @Nullable List<CellTagsBean.NeedCategoryBean> data) {
        super(layoutResId, data);
        this.data = data;
        this.mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, final CellTagsBean.NeedCategoryBean item) {

        helper.setText(R.id.yofferTypetv, item.getTitle());

        ScreenNeedTwoAdapter publicOfferTwoAdapter = new ScreenNeedTwoAdapter(mContext, R.layout.item_source_screen_cell, item.getList());
        NestedRecyclerView recyclerView = helper.getView(R.id.type_recycler);
        recyclerView.setLayoutManager(new FlowLayoutManager());
        recyclerView.setAdapter(publicOfferTwoAdapter);
    }
}
