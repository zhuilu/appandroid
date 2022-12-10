package com.xinniu.android.qiqueqiao.adapter;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.CityV2Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/8/1.
 */

public class LeftCityAdapter extends BaseQuickAdapter<CityV2Bean,BaseViewHolder> {

    private List<CityV2Bean> data = new ArrayList<>();

    public LeftCityAdapter(int layoutResId, @Nullable List<CityV2Bean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CityV2Bean item) {
        TextView leftTv = helper.getView(R.id.tvItem_city_left);
        leftTv.setText(item.getName());
        if (item.isCheck()){
            leftTv.setSelected(true);
            rightList.setRightPostion(helper.getAdapterPosition());
        }else {
            leftTv.setSelected(false);
        }
        leftTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isCheck()){

                }else {
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setCheck(false);
                    }
                    data.get(helper.getAdapterPosition()).setCheck(true);

                    notifyDataSetChanged();
                }
            }
        });
    }

    public interface setRightList{
        void setRightPostion(int position);
    }

    private setRightList rightList;

    public void setRightList(setRightList rightList) {
        this.rightList = rightList;
    }
}
