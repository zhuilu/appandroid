package com.xinniu.android.qiqueqiao.adapter;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.CityV2Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/8/1.
 */

public class RightCityAdapter extends BaseQuickAdapter<CityV2Bean.ZlistBean,BaseViewHolder> {

    private List<CityV2Bean.ZlistBean> data = new ArrayList<>();
    private List<CityV2Bean> datax = new ArrayList<>();

    public RightCityAdapter(int layoutResId, @Nullable List<CityV2Bean.ZlistBean> data,List<CityV2Bean> datax) {
        super(layoutResId, data);
        this.data = data;
        this.datax = datax;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final CityV2Bean.ZlistBean item) {
        TextView rightTv = helper.getView(R.id.tvItem_city_right);
        rightTv.setText(item.getName());
        if (item.isCheck()){
            rightTv.setSelected(true);

        }else {
            rightTv.setSelected(false);
        }
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setCheck(false);
                }

                data.get(helper.getAdapterPosition()).setCheck(true);
//                setRightPostion.setRightPostion(helper.getAdapterPosition());
                if (setRightPostion!=null) {
                    setRightPostion.setRightPostion(item.getId(),item.getName());
                }
                notifyDataSetChanged();
            }
        });
    }

    public interface getRightPostion{
        void setRightPostion(int postion,String cityName);
    }

    private getRightPostion setRightPostion;

    public void getSetRightPostion(RightCityAdapter.getRightPostion setRightPostion) {
        this.setRightPostion = setRightPostion;
    }
}
