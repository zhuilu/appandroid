package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.SelectCityActivity;
import com.xinniu.android.qiqueqiao.adapter.base.BDBaseAdapter;
import com.xinniu.android.qiqueqiao.adapter.base.BDViewHolder;
import com.xinniu.android.qiqueqiao.bean.CityListBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;

import java.util.List;

/**
 * Created by lzq on 2017/12/22.
 */

public class SelectCityGridAdapter extends BDBaseAdapter {
    public SelectCityGridAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(BDViewHolder helper, Object item, int position) {
        final CityListBean.GroupBean.ListBean child = (CityListBean.GroupBean.ListBean) item;
        TextView textView = helper.getView(R.id.text);
        textView.setText(child.getName());
    }
}
