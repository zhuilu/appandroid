package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
//import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.bean.MyCompanyBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.List;

/**
 * Created by lzq on 2018/2/28.
 */

public class CompanyInfoAdapter extends RecyclerView.Adapter<CompanyInfoAdapter.ViewHolder> {

    private List<MyCompanyBean.UsersBean> list;
    private Context context;
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
    private View mHeaderView;
    private View mFooterView;


    public CompanyInfoAdapter(Context context, List<MyCompanyBean.UsersBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public CompanyInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new CompanyInfoAdapter.ViewHolder(mHeaderView);
        }
        // 实例化展示的view
        View v = LayoutInflater.from(context).inflate(R.layout.item_company_info, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (getItemViewType(position) == TYPE_HEADER) {
        } else {
            final int newPosition = position - 1;
            holder.workTv.setText(list.get(newPosition).getPosition());
            holder.nameTv.setText(list.get(newPosition).getRealname());
            ImageLoader.loadAvter(list.get(newPosition).getHead_pic(), holder.headIv);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersonCentetActivity.start(context, String.valueOf(list.get(newPosition).getUser_id()));
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        if (mHeaderView != null) {
            return list.size() + 1;
        }
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView workTv;
        private TextView nameTv;
        private ImageView headIv;

        public ViewHolder(View itemView) {
            super(itemView);
            workTv = (TextView) itemView.findViewById(R.id.item_work);
            nameTv = (TextView) itemView.findViewById(R.id.item_name);
            headIv = (ImageView) itemView.findViewById(R.id.item_talk_iv);
        }
    }

    public void setHeadView(View headView) {
        mHeaderView = headView;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
//        if (position == getItemCount()-1){
//            return TYPE_FOOTER;
//        }
        return TYPE_NORMAL;

    }
}
