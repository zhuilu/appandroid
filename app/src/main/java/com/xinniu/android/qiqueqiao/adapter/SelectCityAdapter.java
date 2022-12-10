package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.SelectCityActivity;
import com.xinniu.android.qiqueqiao.bean.CityListBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.customs.NoScrollGridView;
import com.xinniu.android.qiqueqiao.utils.ResouceHelper;

import java.util.List;

/**
 * Created by lzq on 2017/12/22.
 */

public class SelectCityAdapter extends RecyclerView.Adapter<SelectCityAdapter.ViewHolder>{
    private List<SelectCityActivity.CityBean> mList;
    private Context context;

    public SelectCityAdapter(Context context,List<SelectCityActivity.CityBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_select_city_new,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override

    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(position == 0){
            holder.pingyin.setVisibility(View.VISIBLE);
        }else{
            holder.pingyin.setVisibility(View.GONE);
        }
        final SelectCityGridAdapter adapter = new SelectCityGridAdapter(context, mList.get(position).list, R.layout.right_select_child_item);
        holder.itemRv.setAdapter(adapter);
        holder.itemRv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (childItemClick != null){
                    childItemClick.itemClik(mList.get(position).list.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        GridView itemRv;
        TextView pingyin;
        public ViewHolder(View itemView) {
            super(itemView);
            itemRv = (NoScrollGridView) itemView.findViewById(R.id.item_rv);
            pingyin = (TextView) itemView.findViewById(R.id.tag);
        }
    }

    private ChildItemClick childItemClick;

    public void setChildItemClick(ChildItemClick childItemClick) {
        this.childItemClick = childItemClick;
    }

    public interface ChildItemClick {
        void itemClik(CityListBean.GroupBean.ListBean bean);
    }
}
