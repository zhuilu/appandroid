package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;

import java.util.List;

/**
 * Created by lzq on 2018/1/20.
 */

public class SreachItemAdapter extends RecyclerView.Adapter<SreachItemAdapter.ViewHodler> {

    private Context context;
    private List<String> mList;
    private String type;
    private OnItemClickListener onItemClickListener;


    public SreachItemAdapter(Context context, List<String> list, String type) {
        this.mList = list;
        this.context = context;
        this.type = type;

    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        ViewHodler viewHolder = new ViewHodler(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, final int position) {
        holder.contentTv.setText(mList.get(position));
        holder.contentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClik(mList.get(position),type);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder {

        TextView contentTv;

        public ViewHodler(View itemView) {
            super(itemView);
            contentTv = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public interface OnItemClickListener {
        void onItemClik(String item,String type);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
