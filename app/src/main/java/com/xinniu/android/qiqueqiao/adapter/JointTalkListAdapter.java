package com.xinniu.android.qiqueqiao.adapter;


import android.content.Context;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.bean.ResouceInfoBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.List;

/**
 * Created by lzq on 2017/12/13.
 */

public class JointTalkListAdapter extends RecyclerView.Adapter<JointTalkListAdapter.ViewHolder> {

    private List<ResouceInfoBean.ZListBean> list;
    private Context context;
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
//    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
    private View mHeaderView;
    private View mFooterView;


    public JointTalkListAdapter(Context context,List<ResouceInfoBean.ZListBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        }
        // 实例化展示的view
        View v = LayoutInflater.from(context).inflate(R.layout.item_joint_talk_list, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if(getItemViewType(position) == TYPE_HEADER){
        }else{
            final int newPosition = position -1;
            holder.workTv.setText(list.get(newPosition).getRealname()+"/"+StringUtils.hintString(list.get(newPosition).getCompany()+list.get(newPosition).getPosition(),8));
            holder.timeTv.setText(TimeUtils.millis2String(list.get(newPosition).getCreate_time()));
            ImageLoader.loadAvter(list.get(newPosition).getHead_pic(),holder.headIv);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonCentetActivity.start(context,String.valueOf(list.get(newPosition).getUser_id()));
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        if (mHeaderView != null){
            return list.size()+1;
        }
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView timeTv;
        private TextView workTv;
        private ImageView headIv;
        public ViewHolder(View itemView) {
            super(itemView);
            timeTv = (TextView) itemView.findViewById(R.id.item_time);
            workTv = (TextView) itemView.findViewById(R.id.item_work);
            headIv = (ImageView) itemView.findViewById(R.id.item_talk_iv);
        }
    }

    public void setHeadView(View headView){
        mHeaderView = headView;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null){
            return TYPE_NORMAL;
        }
        if (position == 0){
            return TYPE_HEADER;
        }
//        if (position == getItemCount()-1){
//            return TYPE_FOOTER;
//        }
        return TYPE_NORMAL;

    }
}
