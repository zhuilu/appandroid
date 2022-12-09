package com.xinniu.android.qiqueqiao.adapter;


import android.content.Context;
import android.graphics.Color;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.VipListBean;

import java.util.List;

/**
 * Created by lzq on 2017/12/13.
 */

public class VipListAdapter extends RecyclerView.Adapter<VipListAdapter.ViewHolder> {

    private List<VipListBean.VipBean> list;
    private Context context;

    public VipListAdapter(Context context,List<VipListBean.VipBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(context).inflate(R.layout.item_vip_list, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(list.get(position).getName());
        holder.desc.setText(list.get(position).getDesc().replace("</br>","\n"));
        holder.price.setText("¥"+list.get(position).getPrice());
        holder.price.setTextColor(Color.parseColor("#eb3c42"));
        if (list.get(position).getBtn() == 1){
            holder.bugBt.setText("购买");
            holder.premium_pirce.setVisibility(View.GONE);
        }
        if (list.get(position).getBtn() == 2){
            holder.bugBt.setText("续费");
            holder.premium_pirce.setVisibility(View.GONE);
        }
        if (list.get(position).getBtn() == 3){
            holder.bugBt.setText("升级");
            String price = "¥"+list.get(position).getPrice();
            SpannableString priceStr = new SpannableString(price);
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            priceStr.setSpan(strikethroughSpan, 0, priceStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.price.setText(priceStr);
            holder.price.setTextColor(Color.parseColor("#999999"));
            holder.premium_pirce.setVisibility(View.VISIBLE);
            holder.premium_pirce.setText("¥"+list.get(position).getPremium_pirce());
            holder.premium_pirce.setTextColor(Color.parseColor("#eb3c42"));
        }
        if (list.get(position).getRecommend() == 1){
            holder.recommon.setVisibility(View.VISIBLE);
        }else{
            holder.recommon.setVisibility(View.GONE);
        }
        holder.bugBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnVipListItemClickListener != null){
                    mOnVipListItemClickListener.onItemClick(list.get(position));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView desc;
        private TextView price;
        private TextView bugBt;
        private ImageView recommon;
        private TextView premium_pirce;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            desc = (TextView) itemView.findViewById(R.id.desc);
            price = (TextView) itemView.findViewById(R.id.price);
            bugBt = (TextView) itemView.findViewById(R.id.bug_bt);
            recommon = (ImageView) itemView.findViewById(R.id.recommon);
            premium_pirce = (TextView) itemView.findViewById(R.id.premium_pirce);
        }
    }
    OnVipListItemClickListener mOnVipListItemClickListener;
    public interface OnVipListItemClickListener{
        void onItemClick(VipListBean.VipBean bean);
    }
    public void setOnVipListItemClickListener(OnVipListItemClickListener onVipListItemClickListener){
        this.mOnVipListItemClickListener = onVipListItemClickListener;
    }
}
