package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.annotation.RequiresApi;
//import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CoopDetailActivity;
import com.xinniu.android.qiqueqiao.bean.IndexNewBean;
import com.xinniu.android.qiqueqiao.customs.label.FlowLayout;
import com.xinniu.android.qiqueqiao.customs.label.TagAdapter;
import com.xinniu.android.qiqueqiao.customs.label.TagFlowLayout;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/6/4.
 */

public class SearchIndexAdapter extends BaseQuickAdapter<IndexNewBean.ListBean, BaseViewHolder> {

    private Context context;

    private String keyWord;

    private int lineType;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public SearchIndexAdapter(Context context, int layoutResId, @Nullable List<IndexNewBean.ListBean> data, int lineType) {
        super(layoutResId, data);
        this.context = context;
        this.lineType = lineType;
    }

    @Override
    protected void convert(BaseViewHolder helper, final IndexNewBean.ListBean item) {
        //     if (!TextUtils.isEmpty(keyWord)) {
        String title = item.getTitle();
        String realName = item.getRealname();
        String company = item.getCompany();
        String position = item.getPosition();
//            if (item.getTitle().contains())
        helper.setText(R.id.item_index_nameTv, StringUtils.strToSpannableStr(realName, keyWord));
        helper.setText(R.id.item_index_position, StringUtils.strToSpannableStr(" · " + company + position, keyWord));
        //   helper.setText(R.id.item_index_recycler_mannertv, TimeUtils.getTimeStateNew(item.getCreate_time()+""));
        helper.setText(R.id.item_index_recycler_mannertv, item.getP_name());
        helper.setText(R.id.item_index_recycler_answer, item.getComment_count() + "");
        helper.setText(R.id.item_index_recycler_see, item.getView() + "");
        helper.setText(R.id.item_index_recycler_tv, StringUtils.strToSpannableStr("【" + item.getCompany() + "】" + title, keyWord));
        helper.setGone(R.id.yindex_on_ll, true);
        TextView onTv = helper.getView(R.id.msearchofferneedTv);
        onTv.setMaxLines(Integer.MAX_VALUE);
        String offerStr = item.getProvide_describe();
        String needStr = item.getNeed_describe();
        ShowUtils.showTextPerfect(onTv, offerStr);
        int xline = onTv.getLineCount();
        int lineNum = 1;
        if (xline > 1) {
            //取整数
            int entire = offerStr.length() / xline;
            //取余数
            int remain = offerStr.length() % xline;
            //计算每行字符数
            lineNum = (offerStr.length() - remain) / xline;
        }
        if (!TextUtils.isEmpty(offerStr) || !TextUtils.isEmpty(needStr)) {
            ShowUtils.showViewVisible(onTv, View.VISIBLE);
            if (!TextUtils.isEmpty(offerStr) && offerStr.contains(keyWord)) {
                ShowUtils.searchLineShow(offerStr, keyWord, lineNum, onTv);
            } else {
                if (needStr != null && needStr.contains(keyWord)) {
                    ShowUtils.searchLineShow(needStr, keyWord, lineNum, onTv);
                } else {
                    if (!TextUtils.isEmpty(offerStr)) {
                        ShowUtils.showTextPerfect(onTv, offerStr);
                        onTv.setMaxLines(5);
                    } else {
                        ShowUtils.showTextPerfect(onTv, needStr);
                        onTv.setMaxLines(5);
                    }
                }
            }
        } else {
            ShowUtils.showViewVisible(onTv, View.GONE);
        }
        ImageLoader.loadLocalImg(item.getHead_pic(), (ImageView) helper.getView(R.id.item_index_recycler_img));
        TextView nameTv = helper.getView(R.id.item_index_nameTv);
        //企业v显示,其他不显示
        if (item.getIs_corporate_vip() == 1) {
            helper.getView(R.id.item_index_new_company_v).setVisibility(View.VISIBLE);
            nameTv.setTextColor(ContextCompat.getColor(context, R.color.king_color));
            helper.getView(R.id.item_index_new_kingimg).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.item_index_new_company_v).setVisibility(View.GONE);
            helper.getView(R.id.item_index_new_kingimg).setVisibility(View.VISIBLE);
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
        }
        if (item.getIs_v() == 0) {
            helper.setVisible(R.id.index_new_isv, false);
        } else {
            helper.setVisible(R.id.index_new_isv, true);
        }


        LinearLayout cardView = helper.getView(R.id.item_index_cardview);
        cardView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("detailId", item.getId());
                Intent intent = new Intent(mContext, CoopDetailActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent, bundle);
            }
        });
        RelativeLayout yoffertagRl = helper.getView(R.id.yoffertagRl);
        RelativeLayout yneedtagRl = helper.getView(R.id.yneedtagRl);
        final TagFlowLayout offerTag = helper.getView(R.id.coop_moffer_label);
        final TagFlowLayout needTag = helper.getView(R.id.coop_mneed_label);
        if (!TextUtils.isEmpty(item.getProvide_remark())) {
            yoffertagRl.setVisibility(View.VISIBLE);
            String[] offerArr = item.getProvide_remark().split(",");
            List<String> offerList = new ArrayList<>();
            List<String> simiOffer = new ArrayList<>();
            List<String> nosiOffer = new ArrayList<>();
            for (int i = 0; i < offerArr.length; i++) {
                if (offerArr[i].contains(keyWord)) {
                    simiOffer.add(offerArr[i]);
                } else {
                    nosiOffer.add(offerArr[i]);
                }
            }
            offerList.addAll(simiOffer);
            offerList.addAll(nosiOffer);
            offerTag.setAdapter(new TagAdapter<String>(offerList) {
                @Override
                public View getView(FlowLayout parent, int position, String o) {
                    TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_label_searchtv, offerTag, false);
                    tv.setText(StringUtils.strToSpannableStr(o, keyWord));
                    return tv;
                }
            });


        } else {
            yoffertagRl.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.getNeed_remark())) {
            yneedtagRl.setVisibility(View.VISIBLE);
            String[] needArr = item.getNeed_remark().split(",");
            List<String> needList = new ArrayList<>();
            List<String> simiNeed = new ArrayList<>();
            List<String> nosiNeed = new ArrayList<>();
            for (int i = 0; i < needArr.length; i++) {
                if (needArr[i].contains(keyWord)) {
                    simiNeed.add(needArr[i]);
                } else {
                    nosiNeed.add(needArr[i]);
                }
            }
            needList.addAll(simiNeed);
            needList.addAll(nosiNeed);
            needTag.setAdapter(new TagAdapter<String>(needList) {
                @Override
                public View getView(FlowLayout parent, int position, String o) {
                    TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.item_label_searchtv, needTag, false);
                    tv.setText(StringUtils.strToSpannableStr(o, keyWord));
                    return tv;
                }
            });
        } else {
            yneedtagRl.setVisibility(View.GONE);
        }


    }

    // }
}
