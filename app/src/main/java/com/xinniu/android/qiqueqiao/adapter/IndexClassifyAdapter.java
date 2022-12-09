package com.xinniu.android.qiqueqiao.adapter;

//import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.PublishSelectTypeActivity;
import com.xinniu.android.qiqueqiao.bean.GetReleaseTypeBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.List;

/**
 * Created by yuchance on 2019/1/10.
 */

public class IndexClassifyAdapter extends BaseQuickAdapter<GetReleaseTypeBean,BaseViewHolder> {


    public IndexClassifyAdapter(int layoutResId, @Nullable List<GetReleaseTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GetReleaseTypeBean item) {
        helper.setText(R.id.item_classify_tv,item.getName());
        ImageLoader.loadAvter(item.getImg(), (CircleImageView) helper.getView(R.id.item_classify_img));
        helper.getView(R.id.index_classify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setOnClick(item.getName(),item.getId());
            }
        });
    }
    public interface setOnClick{
        void setOnClick(String title,int f_id);
    }
    private setOnClick setOnClick;

    public void setSetOnClick(setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
