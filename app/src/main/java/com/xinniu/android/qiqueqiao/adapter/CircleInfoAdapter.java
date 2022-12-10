package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CircleInfoActivity;
import com.xinniu.android.qiqueqiao.bean.CircleInfobean;
import com.xinniu.android.qiqueqiao.bean.SystemMsgBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.List;

/**
 * Created by lzq on 2018/2/1.
 */

public class CircleInfoAdapter extends RecyclerView.Adapter<CircleInfoAdapter.ViewHolder>{

    private Context context;
    private List<CircleInfobean.UserListBean> list;

    public CircleInfoAdapter(Context context, List<CircleInfobean.UserListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CircleInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_circle_info_head,parent,false);
        CircleInfoAdapter.ViewHolder viewHolder = new CircleInfoAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CircleInfoAdapter.ViewHolder holder, final int position) {
//        holder.content.setText(list.get(position).getContent());
//        holder.content.setBackgroundResource(R.mipmap.rc_ic_bubble_left_file);
//        holder.time.setText(TimeUtils.millis2String(list.get(position).getCreate_time()));
//        holder.realname.setText(list.get(position).getRealname());
//        if (CircleInfoActivity.ADD_MEMBER.equals(list.get(position).getHead_pic())){
//            holder.headTv.setImageDrawable(context.getResources().getDrawable(R.mipmap.ali_pay));
//        }else
        if (CircleInfoActivity.DELETE_MEMBER.equals(list.get(position).getHead_pic())){
            holder.headTv.setImageDrawable(context.getResources().getDrawable(R.mipmap.delete_member));
            holder.name.setVisibility(View.INVISIBLE);
        }else{
            ImageLoader.loadAvter(list.get(position).getHead_pic(),holder.headTv);
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(list.get(position).getRealname());
        }

        holder.headTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CircleInfoActivity.DELETE_MEMBER.equals(list.get(position).getHead_pic())){
                    onCircleInfoItemClikListner.onDelete();
                }else{
                    onCircleInfoItemClikListner.onCommonItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView headTv;
        TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            headTv = (ImageView) itemView.findViewById(R.id.head_iv);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
    public interface OnCircleInfoItemClikListner{
        void onDelete();
        void onCommonItem(int position);
    }
    OnCircleInfoItemClikListner onCircleInfoItemClikListner;
    public void setOnCircleInfoItemClikListner(OnCircleInfoItemClikListner onCircleInfoItemClikListner){
        this.onCircleInfoItemClikListner = onCircleInfoItemClikListner;
    }
}
