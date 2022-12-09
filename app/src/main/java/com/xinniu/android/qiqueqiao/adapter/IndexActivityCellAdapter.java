package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GetConfigBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.LocalCacheUtils;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.List;

/**
 * 首页-优质活动
 * Created by yuchance on 2018/8/27.
 */

public class IndexActivityCellAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<GetConfigBean.EventBean> data;

    public IndexActivityCellAdapter(Context context, List<GetConfigBean.EventBean> data) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (null == view) {
            view = inflater.inflate(R.layout.item_index_activity, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.mindex_actone_img);
            holder.name = (TextView) view.findViewById(R.id.mindex_act_onetitletv);
            holder.address = (TextView) view.findViewById(R.id.mindex_act_oneplacetv);
            holder.time = (TextView) view.findViewById(R.id.mindex_act_onetimetv);
            holder.llayout_more = (LinearLayout) view.findViewById(R.id.llayout_more);
            holder.llayout_default = (LinearLayout) view.findViewById(R.id.llayout_default);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (data.get(position).getId() == -1) {//显示更多
            holder.llayout_default.setVisibility(View.GONE);
            holder.llayout_more.setVisibility(View.VISIBLE);
        } else {
            holder.llayout_default.setVisibility(View.VISIBLE);
            holder.llayout_more.setVisibility(View.GONE);
            holder.name.setText(data.get(position).getTitle());

            if (data.get(position).getProvince_name().equals(data.get(position).getCity_name())) {
                holder.address.setText(data.get(position).getCity_name());
            } else {
                holder.address.setText(data.get(position).getProvince_name() + data.get(position).getCity_name());
            }
            holder.time.setText(TimeUtils.time2monthday(data.get(position).getCreate_time() * 1000) + TimeUtils.time2Weekt(data.get(position).getCreate_time() * 1000));

//            if (LocalCacheUtils.getLocalCache(data.get(position).getPoster()) != null) {
//                holder.icon.setImageBitmap(LocalCacheUtils.getLocalCache(data.get(position).getPoster()));
//            } else {
//                ImageLoader.loadImageGQ1(data.get(position).getPoster(), holder.icon);
//            }

            ImageLoader.loadImageGQ(data.get(position).getPoster(), holder.icon);
        }
        return view;
    }

    class ViewHolder {
        ImageView icon;
        TextView name;
        TextView address;
        TextView time;
        LinearLayout llayout_more;
        LinearLayout llayout_default;
    }
}
