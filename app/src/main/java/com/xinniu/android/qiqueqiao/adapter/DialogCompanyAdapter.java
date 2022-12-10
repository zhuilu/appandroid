package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;

import java.util.List;

/**
 * Created by lzq on 2017/12/21.
 */

public class DialogCompanyAdapter extends RecyclerView.Adapter<DialogCompanyAdapter.ViewHolder>{
    private List<SelectCategory> list;
    private Context context;


    public DialogCompanyAdapter(Context context,List<SelectCategory> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.item_comment_language,parent,false);
        DialogCompanyAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.item.setText(list.get(position).getName());
        holder.item.setSelected(list.get(position).isCheck());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (SelectCategory item : list){
                    item.setCheck(false);
                }
                list.get(position).setCheck(true);
                notifyDataSetChanged();
                if (defaultSelect!=null){
                    defaultSelect.itemSelect(list.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView item;
        public ViewHolder(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.item);
        }
    }

    private ItemSelect defaultSelect;

    public void setItemSelect(ItemSelect defaultSelect) {
        this.defaultSelect = defaultSelect;
    }

    public interface ItemSelect {
        void itemSelect(SelectCategory selectCategories);
    }
}
