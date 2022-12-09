package com.xinniu.android.qiqueqiao.adapter;

//import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.MyGroupListBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.List;

/**
 * Created by yuchance on 2018/10/9.
 */

public class FuzzySearchAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public FuzzySearchAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        helper.setText(R.id.tv_company_name, item);
        helper.getView(R.id.rlayout_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.click(item,helper.getLayoutPosition());
            }
        });
    }

    private Onclick onclick;

    public interface Onclick {
        void click(String name,int position);

    }

    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }
}
