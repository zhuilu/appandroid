package com.xinniu.android.qiqueqiao.adapter;

import android.graphics.Typeface;
//import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/5/31.
 */

public class ReportListAdapter extends BaseQuickAdapter<SelectCategory,BaseViewHolder> {

    private List<SelectCategory> data = new ArrayList<>();

    public ReportListAdapter(int layoutResId, @Nullable List<SelectCategory> data) {
        super(layoutResId, data);
        this.data =data;
    }

    @Override
    protected void convert(BaseViewHolder helper, final SelectCategory item) {
        helper.setText(R.id.reportTv,item.getName());
        TextView tv = helper.getView(R.id.reportTv);
        final CheckBox checkBox = helper.getView(R.id.reportCb);
        final RelativeLayout rl = helper.getView(R.id.item_report_rl);
        TextPaint paint = tv.getPaint();
        if (item.isCheck()){
            checkBox.setChecked(true);
            rl.setSelected(true);
            paint.setFakeBoldText(true);
        }else {
            checkBox.setChecked(false);
            rl.setSelected(false);
            paint.setFakeBoldText(false);
        }
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setCheck(false);
                }
                checkBox.setChecked(true);
                rl.setSelected(true);
                item.setCheck(true);
                notifyDataSetChanged();
            }
        });



    }
}
