package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.signature.EmptySignature;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GetConfigBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.LocalCacheUtils;

import java.io.File;
import java.util.List;

/**
 * Created by yuchance on 2018/8/27.
 */

public class IndexCellTwoAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<GetConfigBean.NavBean> data;

    public IndexCellTwoAdapter(Context context, List<GetConfigBean.NavBean> data) {
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
            view = inflater.inflate(R.layout.item_index_cell, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.mimgItemCell);
            holder.name = (TextView) view.findViewById(R.id.mtvItemCell);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(data.get(position).getName());
        if (LocalCacheUtils.getLocalCache(data.get(position).getImg()) != null) {
            holder.icon.setImageBitmap(LocalCacheUtils.getLocalCache(data.get(position).getImg()));
        } else {
            ImageLoader.loadImageGQ1(data.get(position).getImg(), holder.icon);
        }
//        ImageLoader.loadImageGQ(data.get(position).getImg(), holder.icon);
        return view;
    }

    class ViewHolder {
        ImageView icon;
        TextView name;
    }

}
