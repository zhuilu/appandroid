package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.umeng.analytics.MobclickAgent;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.MyActListBean;
import com.xinniu.android.qiqueqiao.bean.WelfareAgencyBean;
import com.xinniu.android.qiqueqiao.utils.ComUtils;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.List;

/**
 * Created by yuchance on 2019/1/8.
 */

public class WelfareAgencyAdapter extends BaseQuickAdapter<WelfareAgencyBean, BaseViewHolder> {

    private Context context;

    public WelfareAgencyAdapter(Context context, int layoutResId, @Nullable List<WelfareAgencyBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final WelfareAgencyBean item) {
        ImageLoader.loadImageGQ(item.getImg(), (ImageView) helper.getView(R.id.img));
        helper.getView(R.id.item_index_cardview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(mContext, "welfareclub_list", item.getUrl());//统计点击次数
                if (!TextUtils.isEmpty(item.getUrl())) {

                    ComUtils.goToBannerNext(mContext, item.getUrl(), false);
                }
            }
        });

    }


}
