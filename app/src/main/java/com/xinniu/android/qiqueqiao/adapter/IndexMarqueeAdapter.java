package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
//import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.stx.xmarqueeview.XMarqueeView;
import com.stx.xmarqueeview.XMarqueeViewAdapter;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GetConfigBean;
import com.xinniu.android.qiqueqiao.utils.ComUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/12/26.
 */

public class IndexMarqueeAdapter extends XMarqueeViewAdapter<GetConfigBean.ScrollingInfoBean> {

    private List<GetConfigBean.ScrollingInfoBean> datas = new ArrayList<>();
    private Context context;

    public IndexMarqueeAdapter(Context context,List<GetConfigBean.ScrollingInfoBean> datas) {
        super(datas);
        this.context = context;
        this.datas = datas;
    }

    public void setDatas(List<GetConfigBean.ScrollingInfoBean> datas) {
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

    public void setSetOnClick(IndexMarqueeAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
