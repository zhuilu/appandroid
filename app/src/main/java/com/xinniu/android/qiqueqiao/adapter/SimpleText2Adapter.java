package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.os.Handler;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.LLSimpleTextBean;
import com.xinniu.android.qiqueqiao.divider.ItemTouchHelperAdapter;
import com.xinniu.android.qiqueqiao.widget.SwipeMenuLayout;

import java.util.List;

/**
 * Created by zll on 2019/03/09.
 * 常用语Bean
 */

public class SimpleText2Adapter extends RecyclerView.Adapter<SimpleText2Adapter.ViewHolder> {
    private List<LLSimpleTextBean> list;
    private Context context;
    private Handler mHandler;
    private ItemSelect mItemSelect;

    public SimpleText2Adapter(Context context, Handler handler, List<LLSimpleTextBean> list) {
        this.context = context;
        this.list = list;
        mHandler = handler;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment_language_2, parent, false);
        SimpleText2Adapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.item.setText(list.get(position).getText());


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHandler != null) {
                    mHandler.obtainMessage(101, list.get(position).getText()).sendToTarget();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item;

        public ViewHolder(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.item);


        }
    }

    public interface ItemSelect {
        void itemSelect(LLSimpleTextBean selectCategories, int position, String type, ViewHolder viewHolder);
    }

    public void setmItemSelect(ItemSelect mItemSelect) {
        this.mItemSelect = mItemSelect;
    }
}
