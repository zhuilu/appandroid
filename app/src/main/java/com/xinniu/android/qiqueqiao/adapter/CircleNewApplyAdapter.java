package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CircleInfoActivity;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.bean.CircleApplyBean;
import com.xinniu.android.qiqueqiao.bean.CircleInfobean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;

import java.util.List;

/**
 * Created by lzq on 2018/2/5.
 */

public class CircleNewApplyAdapter extends BaseQuickAdapter<CircleApplyBean.ListBean,BaseViewHolder>{

    private Context context;

    public CircleNewApplyAdapter(Context context,int layoutResId, @Nullable List<CircleApplyBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CircleApplyBean.ListBean item) {
        helper.setText(R.id.content,item.getRemark());
        helper.setText(R.id.name,item.getNickname());
        helper.getView(R.id.refuse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCircleInfoItemClikListner != null){
                    onCircleInfoItemClikListner.onRefuse(item.getId());
                }
            }
        });
        helper.getView(R.id.agree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCircleInfoItemClikListner != null){
                    onCircleInfoItemClikListner.onAgree(item.getId());
                }
            }
        });
        helper.getView(R.id.itemView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonCentetActivity.start(context,String.valueOf(item.getUser_id()));
            }
        });
        int status = item.getStatus();
        if (status == 0){
            helper.setGone(R.id.tip,false);
            helper.setGone(R.id.refuse,true);
            helper.setGone(R.id.agree,true);
        }
        if (status == 1){
            helper.setGone(R.id.tip,true);
            helper.setGone(R.id.refuse,false);
            helper.setGone(R.id.agree,false);
            helper.setText(R.id.tip,"已同意");
        }
        if (status == 2){
            helper.setGone(R.id.tip,true);
            helper.setGone(R.id.refuse,false);
            helper.setGone(R.id.agree,false);
            helper.setText(R.id.tip,"已拒绝");
        }
    }
    public interface OnCircleNewApplyClikListner{
        void onRefuse(int id);
        void onAgree(int id);
    }
    OnCircleNewApplyClikListner onCircleInfoItemClikListner;
    public void setOnCircleNewApplyClikListner(OnCircleNewApplyClikListner onCircleInfoItemClikListner){
        this.onCircleInfoItemClikListner = onCircleInfoItemClikListner;
    }

}
