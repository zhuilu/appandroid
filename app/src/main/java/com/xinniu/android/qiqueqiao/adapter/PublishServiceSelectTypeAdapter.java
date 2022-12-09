package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
//import android.support.annotation.Nullable;
import android.util.SparseBooleanArray;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GetReleaseTypeBean;
import com.xinniu.android.qiqueqiao.bean.ServiceCategoryAndTag;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.List;

/**
 * 发布-选择资源类型
 * Created by yuchance on 2018/12/12.
 */

public class PublishServiceSelectTypeAdapter extends BaseQuickAdapter<ServiceCategoryAndTag, BaseViewHolder> {

    private Context context;
    private SparseBooleanArray sparseBooleanArray;

    public PublishServiceSelectTypeAdapter(Context context, int layoutResId, @Nullable List<ServiceCategoryAndTag> data) {
        super(layoutResId, data);
        this.context = context;
        sparseBooleanArray = new SparseBooleanArray();
    }

    public SparseBooleanArray getSparseBooleanArray() {
        return sparseBooleanArray;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ServiceCategoryAndTag item) {
        if (sparseBooleanArray.get(helper.getAdapterPosition())) {
            helper.getView(R.id.img_check).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.img_check).setVisibility(View.GONE);
        }


        helper.setText(R.id.mselecttypetv, item.getName());
        helper.getView(R.id.item_publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sparseBooleanArray.clear();
                sparseBooleanArray.put(helper.getAdapterPosition(), true);
                notifyDataSetChanged();
                setOnClick.setOnClick(item.getName(), item.getId(), item.getZlist());
            }
        });

    }

    public interface setOnClick {
        void setOnClick(String title, int typeId, List<ServiceCategoryAndTag.ZlistBean> data);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(PublishServiceSelectTypeAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
