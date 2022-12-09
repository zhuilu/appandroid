package com.xinniu.android.qiqueqiao.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CoopDetailActivity;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.activity.MyPushActivity;
import com.xinniu.android.qiqueqiao.activity.SreachActivity;
import com.xinniu.android.qiqueqiao.bean.ClassRoomDetailBean;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.List;

/**
 * Created by yuchance on 2018/3/30.
 */

public class IndexNewTwoAdapter extends BaseQuickAdapter<ClassRoomDetailBean.ResourcesListBean, BaseViewHolder> {


    private Activity context;

    private String keyWord;

    private int lineType;

    private boolean isSearchPerch;

    public IndexNewTwoAdapter(Activity context, int layoutResId, @Nullable List<ClassRoomDetailBean.ResourcesListBean> data, int lineType, boolean isSearchPerch) {
        super(layoutResId, data);
        this.context = context;
        this.lineType = lineType;
        this.isSearchPerch = isSearchPerch;
    }

    public IndexNewTwoAdapter(Activity context, int layoutResId, @Nullable List<ClassRoomDetailBean.ResourcesListBean> data, int lineType) {
        super(layoutResId, data);
        this.context = context;
        this.lineType = lineType;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ClassRoomDetailBean.ResourcesListBean item) {
        if (lineType == 2) {
            //企业v显示
            if (item.getIs_corporate_vip() == 1) {
                helper.getView(R.id.item_index_new_company_v).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.item_index_new_company_v).setVisibility(View.GONE);
            }
        } else {
            helper.getView(R.id.item_index_new_company_v).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.getCompany())) {
            helper.setText(R.id.item_index_position, " · " + item.getCompany() + item.getPosition());
        } else {
            helper.setText(R.id.item_index_position, " · " + item.getPosition());
        }

        helper.setText(R.id.item_index_nameTv, item.getRealname());

        helper.setText(R.id.item_index_recycler_mannertv, item.getP_name());
        helper.setText(R.id.item_index_recycler_answer, item.getComment_count() + "");
        helper.setText(R.id.item_index_recycler_see, item.getView() + "");
        helper.setGone(R.id.yindex_on_ll, false);
        if (!TextUtils.isEmpty(item.getTitle())) {
            helper.setText(R.id.item_index_recycler_tv, "【" + item.getCompany() + "】" + item.getTitle());
        } else {
            helper.setText(R.id.item_index_recycler_tv, "【" + item.getCompany() + "】");

        }

        ImageLoader.loadAvter(item.getHead_pic(), (ImageView) helper.getView(R.id.item_index_recycler_img));
        TextView nameTv = helper.getView(R.id.item_index_nameTv);
        if (item.getIs_vip() == 1) {
            helper.getView(R.id.item_index_new_kingimg).setVisibility(View.VISIBLE);
            helper.setBackgroundRes(R.id.item_index_new_kingimg, R.mipmap.vip_iconx);
            nameTv.setTextColor(ContextCompat.getColor(context, R.color.king_color));
        } else if (item.getIs_vip() == 0) {
            helper.getView(R.id.item_index_new_kingimg).setVisibility(View.GONE);
            nameTv.setTextColor(ContextCompat.getColor(context, R.color.text_gray_color92));
        } else if (item.getIs_vip() == 2) {
            helper.getView(R.id.item_index_new_kingimg).setVisibility(View.VISIBLE);
            helper.setBackgroundRes(R.id.item_index_new_kingimg, R.mipmap.svip_iconx);
            nameTv.setTextColor(ContextCompat.getColor(context, R.color.king_color));
        }
        LinearLayout cardView = helper.getView(R.id.item_index_cardview);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserInfoHelper.getIntance().isLogin()) {
                    LoginNewActivity.start(mContext);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("detailId", item.getId());
                Intent intent = new Intent(mContext, CoopDetailActivity.class);
                intent.putExtras(bundle);
                context.startActivityForResult(intent, MyPushActivity.REQUESTCODE, bundle);
            }
        });
        if (item.getIs_vip() == 1) {
            helper.setVisible(R.id.index_new_isv, true);
        } else {
            helper.setVisible(R.id.index_new_isv, false);
        }

        helper.getView(R.id.item_index_perch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SreachActivity.start(mContext);
            }
        });
        final LinearLayout llayout = helper.getView(R.id.llayout_bq);
        if (item.getCategory_list() != null && !item.getCategory_list().equals("null")) {
            if (item.getCategory_list().size() > 0) {
                llayout.setVisibility(View.VISIBLE);
                helper.getView(R.id.llayout_bq_02).setVisibility(View.GONE);
                helper.setText(R.id.tv_bq_title01, item.getCategory_list().get(0).getTitle() + "：");
                helper.setText(R.id.tv_bq_01, item.getCategory_list().get(0).getList().get(0).getName());
                if (item.getCategory_list().size() >= 2) {
                    helper.getView(R.id.llayout_bq_02).setVisibility(View.VISIBLE);
                    helper.setText(R.id.tv_bq_title02, item.getCategory_list().get(1).getTitle() + "：");
                    helper.setText(R.id.tv_bq_02, item.getCategory_list().get(1).getList().get(0).getName());
                }

            } else {
                llayout.setVisibility(View.GONE);
            }
        } else {
            llayout.setVisibility(View.GONE);
        }
    }


}
