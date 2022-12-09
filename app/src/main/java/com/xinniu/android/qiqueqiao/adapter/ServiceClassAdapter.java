package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
//import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.ServiceCategoryAndTag;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/6/29.
 */

public class ServiceClassAdapter extends BaseQuickAdapter<ServiceCategoryAndTag, BaseViewHolder> {
    private List<ServiceCategoryAndTag> data = new ArrayList<>();
    private Context mContext;
    private int mAllImageWidth;


    public ServiceClassAdapter(Context context, int layoutResId, @Nullable List<ServiceCategoryAndTag> data) {
        super(layoutResId, data);
        this.data = data;
        this.mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, final ServiceCategoryAndTag item) {

//        if (item.getId() == -1) {
//            helper.getView(R.id.img).setBackgroundResource(R.mipmap.all_classes);
//
//        } else {
            ImageView imageView = helper.getView(R.id.img);
            ImageLoader.loadImageGQ(item.getImg(), imageView);
  //      }
        helper.setText(R.id.tv, item.getName());
        (helper.getView(R.id.llayout_root_service)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setAddOnClick(item);

            }
        });
    }

    public interface setOnClick {
        void setAddOnClick(ServiceCategoryAndTag item);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
