package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CaseDetailActivity;
import com.xinniu.android.qiqueqiao.activity.IndexCellActivity;
import com.xinniu.android.qiqueqiao.activity.IndexClassifyActivity;
import com.xinniu.android.qiqueqiao.bean.GetConfigBean;
import com.xinniu.android.qiqueqiao.bean.ServiceCaseBean;
import com.xinniu.android.qiqueqiao.bean.ServiceCategoryAndTag;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.widget.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/6/22.
 */

public class IndexCaseAdapter extends BaseQuickAdapter<ServiceCaseBean, BaseViewHolder> {

    private List<ServiceCaseBean> datas = new ArrayList<>();
    private Context mContext;

    public IndexCaseAdapter(Context mContext, int layoutResId, @Nullable List<ServiceCaseBean> data) {
        super(layoutResId, data);
        this.datas = data;
        this.mContext = mContext;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ServiceCaseBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        ImageLoader.loadImageGQ(item.getThumb_img(), (ImageView) helper.getView(R.id.img));
        final RelativeLayout itemRl = helper.getView(R.id.rlayout_root);
        itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //详情
                CaseDetailActivity.start(mContext,item.getId());
            }
        });
        helper.getView(R.id.delete_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除
                setOnClick.setAddOnClick(helper.getAdapterPosition());
            }
        });
        SwipeMenuLayout swipeMenuLayout=helper.getView(R.id.swipe_content);
        swipeMenuLayout.addListener(new SwipeMenuLayout.SwipeListener() {
            @Override
            public void onUpdate(SwipeMenuLayout layout, float offset) {

            }

            @Override
            public void onOpened(SwipeMenuLayout layout) {

            }

            @Override
            public void onClosed(SwipeMenuLayout layout) {

            }

            @Override
            public void onClick(SwipeMenuLayout layout) {

            }
        });

    }
    public interface setOnClick {
        void setAddOnClick(int position);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }

}
