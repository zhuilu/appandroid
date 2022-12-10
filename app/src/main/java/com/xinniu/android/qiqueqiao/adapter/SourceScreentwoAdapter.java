package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
//import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.SourceScreenBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/4/17.
 */

public class SourceScreentwoAdapter extends BaseQuickAdapter<SourceScreenBean.CategoryListBean,BaseViewHolder> {
    private List<SourceScreenBean.CategoryListBean> data = new ArrayList<>();
    private Context mContext;
    private int mAllImageWidth;


    public SourceScreentwoAdapter(Context mContext, int layoutResId, @Nullable List<SourceScreenBean.CategoryListBean> data) {
        super(layoutResId, data);
        this.data = data;
        this.mContext = mContext;
        //获取屏幕宽高
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        mAllImageWidth = screenWidth - DensityUtil.dp2px(35);
    }

    public SourceScreentwoAdapter(@Nullable List<SourceScreenBean.CategoryListBean> data) {
        super(data);
    }

    public SourceScreentwoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SourceScreenBean.CategoryListBean item) {
        TextView tv = helper.getView(R.id.item_source_celltv);
        if (mAllImageWidth>0){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mAllImageWidth/4, DensityUtil.dp2px(32));
            params.setMargins(DensityUtil.dp2px(5),0,0,DensityUtil.dp2px(15));

            tv.setLayoutParams(params);
        }
        if (item.isCheck()){
            tv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_source_cell_two));
            helper.setTextColor(R.id.item_source_celltv,ContextCompat.getColor(mContext,R.color.white));
        }else {
            tv.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_source_cell));
            helper.setTextColor(R.id.item_source_celltv,ContextCompat.getColor(mContext,R.color._888));
        }
        helper.setText(R.id.item_source_celltv,item.getName());
        (helper.getView(R.id.item_source_celltv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for (int i = 0; i <data.size() ; i++) {
//                    data.get(i).setCheck(false);
//                }
                if (data.get(helper.getAdapterPosition()).isCheck()){
                    item.setCheck(false);
                }else {
                    item.setCheck(true);
                }
                notifyDataSetChanged();
            }
        });



    }

}
