package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import java.util.List;

/**
 * Created by lzq on 2017/12/19.
 */

public class StepModeAdapter extends RecyclerView.Adapter<StepModeAdapter.ViewHolder>{
    private Context context;
    private List<SelectCategory> list;

    public StepModeAdapter(Context context,List<SelectCategory> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.select_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textView.setText(list.get(position).getName());
        holder.textView.setSelected(list.get(position).isCheck());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (SelectCategory category : list){
                    category.setCheck(false);
                }
                list.get(position).setCheck(true);
                notifyDataSetChanged();
                if (onItemClickListener != null){
                    onItemClickListener.onItemClik(list.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }
    OnItemClickListener onItemClickListener;
    public void setOnItemtClikListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    };
    public interface OnItemClickListener{
        void onItemClik(SelectCategory content);
    }
}
