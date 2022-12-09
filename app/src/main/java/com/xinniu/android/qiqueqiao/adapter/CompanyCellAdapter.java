package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.bean.SourceScreenBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.List;

/**
 * Created by yuchance on 2018/8/27.
 */

public class CompanyCellAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<SelectCategory> data;
    public CompanyCellAdapter(Context context, List<SelectCategory> data) {
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
            view = inflater.inflate(R.layout.item_company_icon, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.icon);
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(data.get(position).getName());
        ImageLoader.loadCompanyCell(data.get(position).getImg(),holder.icon);
        return view;
    }
    class ViewHolder {
        ImageView icon;
        TextView name;
    }
}
