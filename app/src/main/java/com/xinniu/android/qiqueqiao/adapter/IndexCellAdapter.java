package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.IndexCellActivity;
import com.xinniu.android.qiqueqiao.activity.IndexClassifyActivity;
import com.xinniu.android.qiqueqiao.bean.GetConfigBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/6/22.
 */

public class IndexCellAdapter extends BaseQuickAdapter<GetConfigBean.NavBean,BaseViewHolder> {

    private List<GetConfigBean.NavBean> datas = new ArrayList<>();
    private Context mContext;

    public IndexCellAdapter(Context mContext,int layoutResId, @Nullable List<GetConfigBean.NavBean> data) {
        super(layoutResId, data);
        this.datas = data;
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GetConfigBean.NavBean item) {
        helper.setText(R.id.mtvItemCell,item.getName());
        ImageLoader.loadImageGQ(item.getImg(), (ImageView) helper.getView(R.id.mimgItemCell));
        RelativeLayout itemRl = helper.getView(R.id.item_index_Rl);
        itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getIs_all()==0){
                    IndexCellActivity.start(mContext, item.getId(), item.getName());
                }else {
                    IndexClassifyActivity.start(mContext);
                }
            }
        });

    }
}
