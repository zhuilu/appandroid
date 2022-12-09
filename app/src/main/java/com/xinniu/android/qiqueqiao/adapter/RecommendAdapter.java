package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.RecommendBean;
import com.xinniu.android.qiqueqiao.customs.label.FlowLayout;
import com.xinniu.android.qiqueqiao.customs.label.TagAdapter;
import com.xinniu.android.qiqueqiao.customs.label.TagFlowLayout;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2019/2/13.
 */

public class RecommendAdapter extends BaseQuickAdapter<RecommendBean,BaseViewHolder> {

    private Context context;

    public RecommendAdapter(Context context,int layoutResId, @Nullable List<RecommendBean> data) {
        super(layoutResId, data);
        this.context = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, final RecommendBean item) {
        helper.setText(R.id.mitem_connect_nametv,item.getRealname());
        helper.setText(R.id.mitem_connect_positiontv,item.getCompany()+"|"+item.getPosition());
        List<String> datas = new ArrayList<>();
        String[] tagStr = item.getTag().split(",");
        for (int i = 0; i < tagStr.length; i++) {
            datas.add(tagStr[i]);
        }

        TagFlowLayout tagFlowLayout =  helper.getView(R.id.mitem_connect_potentialtv);
        tagFlowLayout.setAdapter(new TagAdapter<String>(datas) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_label_tayout,null);
                textView.setText(o);
                return textView;
            }
        });


        ImageLoader.loadAvter(item.getHead_pic(), (CircleImageView) helper.getView(R.id.mitem_connect_img));
        if (item.getFriend_status()==0){
            helper.getView(R.id.item_connect_add).setSelected(false);
            helper.setText(R.id.item_connect_add,"加好友");
            ((TextView)helper.getView(R.id.item_connect_add)).setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
        }else {
            helper.getView(R.id.item_connect_add).setSelected(true);
            helper.setText(R.id.item_connect_add,"已添加");
            ((TextView)helper.getView(R.id.item_connect_add)).setTextColor(ContextCompat.getColor(mContext,R.color.bg_gray_add_CC));
        }
        if (item.getIs_v()==0){
            helper.getView(R.id.mitem_is_v).setVisibility(View.GONE);
        }else {
            helper.getView(R.id.mitem_is_v).setVisibility(View.VISIBLE);
        }
        helper.getView(R.id.item_connect_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setOnClick(item.getUser_id(),item.getRealname(),item.getCompany()+item.getPosition(),item.getHead_pic(),0);
            }
        });
        helper.getView(R.id.mitem_connect_Rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setItemOnClick(item.getUser_id());
            }
        });


    }

    public interface setOnClick{
        void setOnClick(int id,String name,String position,String headPic,int isVip);
        void setItemOnClick(int id);
    }
    private setOnClick setOnClick;

    public void setSetOnClick(RecommendAdapter.setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }
}
