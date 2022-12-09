package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.base.BDBaseAdapter;
import com.xinniu.android.qiqueqiao.adapter.base.BDViewHolder;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;

import java.util.List;

/**
 * Created by BDXK on 2017/11/14.
 */

public class IndexTypeSelectAdapter extends BDBaseAdapter {

    public IndexTypeSelectAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(BDViewHolder helper, Object item, final int position) {
        final SelectCategory child = (SelectCategory) item;
        TextView textView = helper.getView(R.id.text);
        textView.setSelected(child.isCheck());
        textView.setText(child.getName());
    }


    private ChildItemClick childItemClick;

    public void setChildItemClick(ChildItemClick childItemClick) {
        this.childItemClick = childItemClick;
    }

    public interface ChildItemClick {
        void check(int position);
    }
}
