package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
//import android.support.annotation.Nullable;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.SeclectCateBean;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/6/28.
 */

public class OtherTagAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private SeclectCateBean seclectCateBean = new SeclectCateBean();
    private List<Integer>  datas = new ArrayList<>();
    private Context mContext;
    public static final int ONE_ITEM = 1;
    public static final int TWO_ITEM = 2;
    private List<SeclectCateBean.UserCategoryBean> userList = new ArrayList<>();

    public OtherTagAdapter(Context context,SeclectCateBean seclectCateBean,List<SeclectCateBean.UserCategoryBean> userList) {
        this.seclectCateBean = seclectCateBean;
        this.mContext = context;
        this.userList = userList;
    }

    public void setSeclectCateBean(SeclectCateBean seclectCateBean) {
        this.seclectCateBean = seclectCateBean;
    }

    public void setUserList(List<SeclectCateBean.UserCategoryBean> userList) {
        this.userList = userList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder = null;
        if (viewType == ONE_ITEM){
            View view = inflater.inflate(R.layout.item_label_typetv,parent,false);
            holder = new ViewHolder(view);
        }else {
            View view = inflater.inflate(R.layout.item_resource_other,parent,false);
            holder = new AddHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder) holder).tv.setText(userList.get(position).getName());
            if (userList.get(position).isCheck()){
                ((ViewHolder) holder).tv.setSelected(true);
            }else {
                ((ViewHolder) holder).tv.setSelected(false);
            }
            ((ViewHolder) holder).tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datas = new ArrayList<>();
                    for (int i = 0; i < seclectCateBean.getCommonCategory().size(); i++) {
                        for (int j = 0; j <seclectCateBean.getCommonCategory().get(i).getZlist().size() ; j++) {
                            if (seclectCateBean.getCommonCategory().get(i).getZlist().get(j).isCheck()){
                                datas.add(seclectCateBean.getCommonCategory().get(i).getZlist().get(j).getId());
                            }
                        }
                    }
                    for (int i = 0; i <userList.size() ; i++) {
                        if (userList.get(i).isCheck()){
                            datas.add(userList.get(i).getId());
                        }
                    }
                    if (userList.get(position).isCheck()){

                        userList.get(position).setCheck(false);

                    }else {
                        if (datas.size()<6) {
                            userList.get(position).setCheck(true);
                        }else {
                            userList.get(position).setCheck(false);
                            ToastUtils.showCentetToast(mContext,"最多只能选择6个标签");
                        }
                    }
                    notifyDataSetChanged();
                }
            });
        }else {
            ((AddHolder)holder).addImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datas = new ArrayList<>();
                    for (int i = 0; i < seclectCateBean.getCommonCategory().size(); i++) {
                        for (int j = 0; j <seclectCateBean.getCommonCategory().get(i).getZlist().size() ; j++) {
                            if (seclectCateBean.getCommonCategory().get(i).getZlist().get(j).isCheck()){
                                datas.add(seclectCateBean.getCommonCategory().get(i).getZlist().get(j).getId());
                            }
                        }
                    }
                    for (int i = 0; i <userList.size() ; i++) {
                        if (userList.get(i).isCheck()){
                            datas.add(userList.get(i).getId());
                        }
                    }
                     {
                        if (datas.size()<6) {
                            addTv.add();
                        }else {
                            ToastUtils.showCentetToast(mContext,"最多只能选择6个标签");
                        }
                    }
                }
            });



        }
    }

    @Override
    public int getItemViewType(int position) {
        int pot = userList.size();
        if (pot == position){
            return TWO_ITEM;
        }else {
            return ONE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return userList != null && userList.size()==0?1:userList.size()+1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_label_type);
        }
    }
    class AddHolder extends RecyclerView.ViewHolder{

        ImageView addImg;

        public AddHolder(View itemView) {
            super(itemView);
            addImg = (ImageView) itemView.findViewById(R.id.addImg);
        }
    }

    public interface addTv{
        void add();
    }

    private addTv addTv;

    public void setAddTv(OtherTagAdapter.addTv addTv) {
        this.addTv = addTv;
    }
}
