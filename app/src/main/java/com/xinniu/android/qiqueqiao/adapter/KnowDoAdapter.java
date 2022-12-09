package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.AddressListBean;
import com.xinniu.android.qiqueqiao.customs.label.FlowLayout;
import com.xinniu.android.qiqueqiao.customs.label.TagAdapter;
import com.xinniu.android.qiqueqiao.customs.label.TagFlowLayout;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2019/2/13.
 */

public class KnowDoAdapter extends BaseQuickAdapter<AddressListBean.ListBean, BaseViewHolder> {

    private Context context;

    public KnowDoAdapter(Context context, int layoutResId, @Nullable List<AddressListBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AddressListBean.ListBean item) {
        helper.setText(R.id.mitem_connect_nametv, item.getRealname());

        List<String> datas = new ArrayList<>();
        String[] tagStr = item.getTag().split(",");
        for (int i = 0; i < tagStr.length; i++) {
            datas.add(tagStr[i]);
        }

        TagFlowLayout tagFlowLayout = helper.getView(R.id.mitem_connect_potentialtv);
        tagFlowLayout.setAdapter(new TagAdapter<String>(datas) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_label_tayout, null);
                textView.setText(o);
                return textView;
            }
        });

        if (item.getType() == 1) {
            helper.getView(R.id.img_phone).setVisibility(View.GONE);
            helper.setText(R.id.mitem_connect_positiontv, item.getCompany() + "|" + item.getPosition());
            helper.getView(R.id.mitem_connect_img).setVisibility(View.VISIBLE);
            helper.getView(R.id.rlayout_avatar).setVisibility(View.GONE);
            ImageLoader.loadAvter(item.getHead_pic(), (CircleImageView) helper.getView(R.id.mitem_connect_img));
            if (item.getFriend_status() == 0) {
                helper.getView(R.id.item_connect_add).setSelected(false);
                helper.setText(R.id.item_connect_add, "加好友");
                ((TextView) helper.getView(R.id.item_connect_add)).setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            } else {
                helper.getView(R.id.item_connect_add).setSelected(true);
                helper.setText(R.id.item_connect_add, "已添加");
                ((TextView) helper.getView(R.id.item_connect_add)).setTextColor(ContextCompat.getColor(mContext, R.color.bg_gray_add_CC));
            }
        } else {
            helper.getView(R.id.img_phone).setVisibility(View.VISIBLE);
            helper.setText(R.id.mitem_connect_positiontv, "手机联系人 " + item.getMobile());
            helper.getView(R.id.mitem_connect_img).setVisibility(View.GONE);
            helper.getView(R.id.rlayout_avatar).setVisibility(View.VISIBLE);
            if (!StringUtils.isEmpty(item.getRealname())) {
                helper.setText(R.id.tv_name_avtar, item.getRealname().substring(0, 1));
            } else {

            }
            if (item.getIs_invite() == 0) {
                helper.getView(R.id.item_connect_add).setSelected(false);
                helper.setText(R.id.item_connect_add, "邀请");
                ((TextView) helper.getView(R.id.item_connect_add)).setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            } else {
                helper.getView(R.id.item_connect_add).setSelected(true);
                helper.setText(R.id.item_connect_add, "已邀请");
                ((TextView) helper.getView(R.id.item_connect_add)).setTextColor(ContextCompat.getColor(mContext, R.color.bg_gray_add_CC));
            }
        }


        helper.getView(R.id.item_connect_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setOnClick(item.getUser_id(), item, helper.getLayoutPosition());
            }
        });

        if (item.getIs_v() == 0) {
            helper.getView(R.id.mitem_is_v).setSelected(false);
        } else {
            helper.getView(R.id.mitem_is_v).setSelected(false);
        }
        helper.getView(R.id.mitem_connect_Rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnClick.setItemOnClick(item.getUser_id());
            }
        });


    }

    public interface setOnClick {
        void setOnClick(int id, AddressListBean.ListBean type, int layoutPosition);

        void setItemOnClick(int id);
    }

    private setOnClick setOnClick;

    public void setSetOnClick(setOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }


}
