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

public class IndexLeftSelectAdapter extends BDBaseAdapter {

    public IndexLeftSelectAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(BDViewHolder helper, Object item, int position) {
        SelectCategory category = (SelectCategory)item;
        TextView textView = helper.getView(R.id.text);
        textView.setSelected(category.isCheck());
        textView.setText(category.getName().replace("*","\n"));
        if (category.isCheck() && defaultSelect != null){
            defaultSelect.defaultSelect(category.getId());
        }

    }

    private DefaultSelect defaultSelect;

    public void setDefaultSelect(DefaultSelect defaultSelect){
        this.defaultSelect = defaultSelect;
    }

    public interface DefaultSelect{
        void defaultSelect(int id);
    }
}
