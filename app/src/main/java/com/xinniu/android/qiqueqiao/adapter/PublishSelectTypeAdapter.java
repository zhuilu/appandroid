package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
//import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GetReleaseTypeBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.List;

/**
 * 发布-选择资源类型
 * Created by yuchance on 2018/12/12.
 */

public class PublishSelectTypeAdapter extends BaseQuickAdapter<GetReleaseTypeBean,BaseViewHolder>{

    private Context context;

    public PublishSelectTypeAdapter(Context context,int layoutResId, @Nullable List<GetReleaseTypeBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GetReleaseTypeBean item) {
        helper.setText(R.id.mselecttypetv,item.getCategory_name());
        helper.setText(R.id.mselectcontenttv,item.getIntroduction());
        ImageLoader.loadAvter(item.getImg(), (CircleImageView) helper.getView(R.id.item_headImg));
        helper.getView(R.id.item_publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setOnClick(item.getName(),item.getId());
            }
        });

    }

    public interface setOnClick{
        void setOnClick(String title,int typeId);
    }
    private setOnClick setOnClick;

    public void setSetOnClick(PublishSelectTypeAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
