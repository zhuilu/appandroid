package com.xinniu.android.qiqueqiao.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.xinniu.android.qiqueqiao.bean.CoopDetailBean;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.customs.label.FlowLayout;
import com.xinniu.android.qiqueqiao.customs.label.TagAdapter;
import com.xinniu.android.qiqueqiao.customs.label.TagFlowLayout;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.List;

import io.rong.imageloader.utils.L;

/**
 * Created by yuchance on 2018/3/30.
 */

public class IndexNewAdapter extends BaseQuickAdapter<IndexNewBean.ListBean, BaseViewHolder> {


    private Activity context;

    private String keyWord;

    private int lineType;
    private int type;


    private boolean isSearchPerch;

//    public IndexNewAdapter(Activity context, int layoutResId, @Nullable List<IndexNewBean.ListBean> data, int lineType, boolean isSearchPerch) {
//        super(layoutResId, data);
//        this.context = context;
//        this.lineType = lineType;
//        this.isSearchPerch = isSearchPerch;
//    }

    public IndexNewAdapter(Activity context, int layoutResId, @Nullable List<IndexNewBean.ListBean> data, int lineType, int type) {
        super(layoutResId, data);
        this.context = context;
        this.lineType = lineType;
        this.type = type;

    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    protected void convert(BaseViewHolder helper, final IndexNewBean.ListBean item) {
        TextView nameTv = helper.getView(R.id.item_index_nameTv);
        View view = helper.getView(R.id.view);
        if (lineType == 2) {
            //企业v显示
            if (item.getIs_corporate_vip() == 1) {
                helper.getView(R.id.item_index_new_company_v).setVisibility(View.VISIBLE);
                nameTv.setTextColor(ContextCompat.getColor(context, R.color.king_color));
                helper.getView(R.id.item_index_new_kingimg).setVisibility(View.GONE);
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.king_color));
            } else {
                helper.getView(R.id.item_index_new_company_v).setVisibility(View.GONE);
                helper.getView(R.id.item_index_new_kingimg).setVisibility(View.VISIBLE);
                if (item.getIs_vip() == 1) {
                    helper.getView(R.id.item_index_new_kingimg).setVisibility(View.VISIBLE);
                    helper.setBackgroundRes(R.id.item_index_new_kingimg, R.mipmap.vip_iconx);
                    nameTv.setTextColor(ContextCompat.getColor(context, R.color.king_color));
                    view.setBackgroundColor(ContextCompat.getColor(context, R.color.king_color));
                } else if (item.getIs_vip() == 0) {
                    helper.getView(R.id.item_index_new_kingimg).setVisibility(View.GONE);
                    nameTv.setTextColor(ContextCompat.getColor(context, R.color.text_gray_color92));
                    view.setBackgroundColor(ContextCompat.getColor(context, R.color.text_gray_color92));
                } else if (item.getIs_vip() == 2) {
                    helper.getView(R.id.item_index_new_kingimg).setVisibility(View.VISIBLE);
                    helper.setBackgroundRes(R.id.item_index_new_kingimg, R.mipmap.svip_iconx);
                    nameTv.setTextColor(ContextCompat.getColor(context, R.color.king_color));
                    view.setBackgroundColor(ContextCompat.getColor(context, R.color.king_color));
                }
            }
        } else {
            helper.getView(R.id.item_index_new_company_v).setVisibility(View.GONE);
            helper.getView(R.id.item_index_new_kingimg).setVisibility(View.VISIBLE);
            if (item.getIs_vip() == 1) {
                helper.getView(R.id.item_index_new_kingimg).setVisibility(View.VISIBLE);
                helper.setBackgroundRes(R.id.item_index_new_kingimg, R.mipmap.vip_iconx);
                nameTv.setTextColor(ContextCompat.getColor(context, R.color.king_color));
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.king_color));
            } else if (item.getIs_vip() == 0) {
                helper.getView(R.id.item_index_new_kingimg).setVisibility(View.GONE);
                nameTv.setTextColor(ContextCompat.getColor(context, R.color.text_gray_color92));
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.text_gray_color92));
            } else if (item.getIs_vip() == 2) {
                helper.getView(R.id.item_index_new_kingimg).setVisibility(View.VISIBLE);
                helper.setBackgroundRes(R.id.item_index_new_kingimg, R.mipmap.svip_iconx);
                nameTv.setTextColor(ContextCompat.getColor(context, R.color.king_color));
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.king_color));
            }
        }
        helper.setText(R.id.item_index_nameTv, item.getRealname());
        if (item.isU()) {
            helper.getView(R.id.view).setVisibility(View.VISIBLE);

        } else {
            helper.getView(R.id.view).setVisibility(View.GONE);

        }
        if (!TextUtils.isEmpty(item.getCompany())) {
            helper.setText(R.id.item_index_position, " · " + item.getCompany() + item.getPosition());
        } else {
            helper.setText(R.id.item_index_position, " · " + item.getPosition());
        }
        final LinearLayout llayout = helper.getView(R.id.llayout_bq);


        if (type == 1) {
            helper.getView(R.id.item_index_recycler_mannertv).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_time).setVisibility(View.GONE);
            helper.setText(R.id.item_index_recycler_mannertv, item.getP_name());
            llayout.setVisibility(View.GONE);
        } else {
            helper.getView(R.id.item_index_recycler_mannertv).setVisibility(View.GONE);
            helper.getView(R.id.tv_time).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_time, TimeUtils.getTimeStateNewTwo(item.getCreate_time() + ""));
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
        helper.setText(R.id.item_index_recycler_answer, item.getComment_count() + "");
        helper.setText(R.id.item_index_recycler_see, item.getView() + "");
        helper.setGone(R.id.yindex_on_ll, false);
        if (!TextUtils.isEmpty(item.getTitle())) {
            if (item.getCompany() != null && item.getCompany().length() > 0) {
                helper.setText(R.id.item_index_recycler_tv, "【" + item.getCompany() + "】" + item.getTitle());
            } else {
                helper.setText(R.id.item_index_recycler_tv, item.getTitle());
            }
        } else {
            if (item.getCompany() != null && item.getCompany().length() > 0) {
                helper.setText(R.id.item_index_recycler_tv, "【" + item.getCompany() + "】");
            } else {
                helper.setText(R.id.item_index_recycler_tv, "");
            }


        }

        ImageLoader.loadAvter(item.getHead_pic(), (ImageView) helper.getView(R.id.item_index_recycler_img));

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
                bundle.putBoolean("isU", item.isU());
                Intent intent = new Intent(mContext, CoopDetailActivity.class);
                intent.putExtras(bundle);
                context.startActivityForResult(intent, MyPushActivity.REQUESTCODE, bundle);
            }
        });
        if (item.getIs_v() == 1) {
            helper.setVisible(R.id.index_new_isv, true);
        } else {
            helper.setVisible(R.id.index_new_isv, false);
        }
        if (item.isSearchPerch()) {
            helper.setGone(R.id.item_index_perch, true);
            helper.setGone(R.id.item_index_cardview, false);
        } else {
            helper.setGone(R.id.item_index_perch, false);
            helper.setGone(R.id.item_index_cardview, true);
        }
        if (item.getIs_cloud_auth() == 0) {
            helper.getView(R.id.item_index_new_re).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.item_index_new_re).setVisibility(View.VISIBLE);

        }

        if (item.getIs_recommend() == 0) {
            helper.getView(R.id.mindex_romotion).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.mindex_romotion).setVisibility(View.VISIBLE);
        }
        helper.getView(R.id.item_index_perch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SreachActivity.start(mContext);
            }
        });

    }


}
