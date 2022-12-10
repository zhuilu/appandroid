package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.os.Handler;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.LLSimpleTextBean;
import com.xinniu.android.qiqueqiao.customs.SwipeMenuLayout;
import com.xinniu.android.qiqueqiao.divider.ItemTouchHelperAdapter;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;

import java.util.Collections;
import java.util.List;

/**
 * Created by zll on 2019/03/09.
 * 常用语Bean
 */

public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    private List<LLSimpleTextBean> list;
    private Context context;
    private boolean isShow = false;
    private ItemSelect mItemSelect;
    private SparseBooleanArray sparseBooleanArray;

    public SimpleTextAdapter(Context context, List<LLSimpleTextBean> list) {
        this.context = context;
        this.list = list;
        sparseBooleanArray = new SparseBooleanArray();

    }

    public SparseBooleanArray getSparseBooleanArray() {
        return sparseBooleanArray;
    }

    //改变显示删除的imageview，通过定义变量isShow去接收变量isManager
    public void changetShowDelImage(boolean isShow) {
        this.isShow = isShow;
        sparseBooleanArray.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment_language, parent, false);
        SimpleTextAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.item.setText(list.get(position).getText());
        //初始化状态
        if (sparseBooleanArray.get(position)) {
            holder.swipe_content.smoothExpand();
        } else {
            holder.swipe_content.smoothClose();
        }
        if (isShow) {
            holder.llayout_delete.setVisibility(View.VISIBLE);
            holder.img_delete.setVisibility(View.VISIBLE);
            holder.img_manger.setVisibility(View.VISIBLE);
            holder.swipe_content.setSwipeEnable(false);//不可滑动删除，只能点击删除


        } else {
            holder.llayout_delete.setVisibility(View.GONE);
            holder.img_delete.setVisibility(View.GONE);
            holder.img_manger.setVisibility(View.GONE);
            holder.swipe_content.setSwipeEnable(true);//可以滑动删除
        }
        //可以编辑
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemSelect != null && !isShow) {
                    mItemSelect.itemSelect(list.get(position), position, "编辑", holder);
                }
            }
        });
        holder.llayout_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sparseBooleanArray.clear();
                sparseBooleanArray.put(holder.getAdapterPosition(), true);
                notifyDataSetChanged();
            }
        });
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sparseBooleanArray.clear();//删除当前展开的那条
                //删除
                if (mItemSelect != null) {
                    mItemSelect.itemSelect(list.get(position), position, "删除", holder);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换位置
        if (fromPosition < toPosition) {
            //从上往下拖动，每滑动一个item，都将list中的item向下交换，向上滑同理。
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(list, i, i + 1);//交换数据源两个数据的位置
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(list, i, i - 1);//交换数据源两个数据的位置
            }
        }
        //更新视图
        notifyItemMoved(fromPosition, toPosition);
        Gson gson = new Gson();//保存在本地
        String json = gson.toJson(list);
        UserInfoHelper.getIntance().setCL(json);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView item;
        ImageView img_delete;
        ImageView img_manger;
        TextView tv_delete;
        SwipeMenuLayout swipe_content;
        LinearLayout llayout_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.item);
            img_delete = (ImageView) itemView.findViewById(R.id.img_delete);
            img_manger = (ImageView) itemView.findViewById(R.id.img_manger);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
            swipe_content = (SwipeMenuLayout) itemView.findViewById(R.id.swipe_content);
            llayout_delete = (LinearLayout) itemView.findViewById(R.id.llayout_1);
        }
    }

    public interface ItemSelect {
        void itemSelect(LLSimpleTextBean selectCategories, int position, String type, ViewHolder viewHolder);
    }

    public void setmItemSelect(ItemSelect mItemSelect) {
        this.mItemSelect = mItemSelect;
    }
}
