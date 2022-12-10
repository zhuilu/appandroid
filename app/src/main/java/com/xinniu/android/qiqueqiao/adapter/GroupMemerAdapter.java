package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.bean.MemberInfoBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.widget.QQQCheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzq on 2018/2/1.
 */

public class GroupMemerAdapter extends RecyclerView.Adapter<GroupMemerAdapter.ViewHolder>{

    private Context context;
    private List<MemberInfoBean> list;
    private int isAdmin;
    private String key = "";
    private String company;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public GroupMemerAdapter(Context context, List<MemberInfoBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public GroupMemerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_group_member,parent,false);
        GroupMemerAdapter.ViewHolder viewHolder = new GroupMemerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroupMemerAdapter.ViewHolder holder, final int position) {
        holder.itemView.setVisibility(View.VISIBLE);
        if (isAdmin == 1){
            if (list.get(position).getStatus() == 1){
                holder.checkbox.setVisibility(View.GONE);
            }else{
                holder.checkbox.setVisibility(View.VISIBLE);
            }
        }else{
            holder.checkbox.setVisibility(View.GONE);
        }
        holder.name.setText(list.get(position).getRealname());
        company = list.get(position).getCompany();
        if (TextUtils.isEmpty(list.get(position).getCompany())){
            holder.content.setText(list.get(position).getPosition());
        }else {
            holder.content.setText( company +"|"+list.get(position).getPosition());
        }
        ImageLoader.loadAvter(list.get(position).getHead_pic(),holder.headTv);
        holder.checkbox.setCheck(list.get(position).isCheck());
        holder.checkbox.setOnCheckChangeListener(new QQQCheckBox.OnCheckChangeListener() {
            @Override
            public void onChange(boolean isCheak) {
                list.get(position).setCheck(isCheak);
            }
        });
        if (list.get(position).getStatus() == 1){
            holder.tip.setVisibility(View.VISIBLE);
        }else{
            holder.tip.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonCentetActivity.start(context,String.valueOf(list.get(position).getUser_id()));
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
        TextView content;
        TextView tip;
        QQQCheckBox checkbox;
        public ViewHolder(View itemView) {
            super(itemView);
            headTv = (ImageView) itemView.findViewById(R.id.head_iv);
            name = (TextView) itemView.findViewById(R.id.name);
            content = (TextView) itemView.findViewById(R.id.content);
            tip = (TextView) itemView.findViewById(R.id.tip);
            checkbox = (QQQCheckBox) itemView.findViewById(R.id.checkbox);
        }
    }

    public List<MemberInfoBean> filter(List<MemberInfoBean> peoples, String query) {
        final List<MemberInfoBean> filteredModelList = new ArrayList<>();
        if(TextUtils.isEmpty(query)){
            filteredModelList.addAll(peoples);
        }else {
//            query = query.toLowerCase();

            for (MemberInfoBean people : peoples) {

                String name = people.getRealname();

                if (!TextUtils.isEmpty(name)&& name.contains(query) ) {
                    Logger.i("lzq","name : " +name);
                    filteredModelList.add(people);
                }
            }
        }
//        list.clear();
//        list.addAll(filteredModelList);
//        notifyDataSetChanged();
        return filteredModelList;
    }

}
