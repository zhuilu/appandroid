package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.stx.xmarqueeview.XMarqueeView;
import com.stx.xmarqueeview.XMarqueeViewAdapter;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GetConfigBean;
import com.xinniu.android.qiqueqiao.bean.MainBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/12/26.
 */

public class IndexMarqueeNewAdapter extends XMarqueeViewAdapter<MainBean.ScrollingInfoBean> {

    private List<MainBean.ScrollingInfoBean> datas = new ArrayList<>();
    private Context context;

    public IndexMarqueeNewAdapter(Context context, List<MainBean.ScrollingInfoBean> datas) {
        super(datas);
        this.context = context;
        this.datas = datas;
    }

    public void setDatas(List<MainBean.ScrollingInfoBean> datas) {
        this.datas = datas;
    }

    @Override
    public View onCreateView(XMarqueeView parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_index_marquee,null);
    }

    @Override
    public void onBindView(View parent, View view, final int position) {
        TextView name = (TextView) view.findViewById(R.id.mindex_nametv);
        TextView type = (TextView) view.findViewById(R.id.mindex_matypetv);
        name.setText(datas.get(position).getCompany() + datas.get(position).getPosition());
        type.setText(datas.get(position).getP_name());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setOnClick(datas.get(position).getId());
            }
        });
    }

    public interface setOnClick{
        void setOnClick(int resourceId);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(IndexMarqueeNewAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
