package com.xinniu.android.qiqueqiao.adapter;

import static com.xinniu.android.qiqueqiao.fragment.push.ResourcePushFragment.MYPUSHCODETWO;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.CoopDetailActivity;
import com.xinniu.android.qiqueqiao.activity.MyPushActivity;
import com.xinniu.android.qiqueqiao.activity.SuperExposureActivity;
import com.xinniu.android.qiqueqiao.bean.MyPushBean;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;
import com.xinniu.android.qiqueqiao.widget.CircleImageView;

import java.util.List;

//import android.support.annotation.RequiresApi;
//import android.support.v4.content.ContextCompat;

/**
 * Created by yuchance on 2018/12/12.
 */

public class MyPushAdapter extends BaseMultiItemQuickAdapter<MyPushBean.ListBean, BaseViewHolder> {
    private Callback callback;
    private AppCompatActivity mContext;
    private String company;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MyPushAdapter(AppCompatActivity context, List<MyPushBean.ListBean> data) {
        super(data);
        this.mContext = context;
        addItemType(MyPushBean.ListBean.COMMON, R.layout.item_my_push);
        addItemType(MyPushBean.ListBean.THETOP, R.layout.item_mypush_top);
    }


    public void setCompany(String company) {
        this.company = company;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MyPushBean.ListBean item) {
        switch (helper.getItemViewType()) {
            case MyPushBean.ListBean.COMMON:
                TextView titleTv = helper.getView(R.id.item_my_resourcex_title);
                TextView eyeTv = helper.getView(R.id.resource_eyeTv);
                TextView tellTv = helper.getView(R.id.resource_tellTv);
                TextView stateTv = helper.getView(R.id.resource_tv);
                LinearLayout Ll = helper.getView(R.id.item_resource_ll);
                TextView editTv = helper.getView(R.id.bresource_editTv);
                final TextView refresh = helper.getView(R.id.bresource_refreshTv);
                TextView top = helper.getView(R.id.bresource_top);
                TextView downTv = helper.getView(R.id.bresource_downTv);
                TextView deleteTv = helper.getView(R.id.bresource_deleteTv);
                TextView resonImg = helper.getView(R.id.img_reson);
                LinearLayout resonLl = helper.getView(R.id.llayout_reson);
                ImageLoader.loadAvter(item.getP_img(), (CircleImageView) helper.getView(R.id.item_typeimg));
                helper.setText(R.id.item_typenametv, item.getP_name());

                if (!TextUtils.isEmpty(item.getTitle())) {
                    titleTv.setText("【" + company + "】" + item.getTitle());
                }
                eyeTv.setText(item.getView() + "");
                tellTv.setText(item.getComment_count() + "");

                if (item.getIs_verify() == 0) {
                    stateTv.setText("审核中");
                    stateTv.setTextColor(ContextCompat.getColor(mContext, R.color.king_color));
                    editTv.setVisibility(View.VISIBLE);
//                    refresh.setText("刷新");
//                    refresh.setBackgroundResource(R.drawable.seletor_textblue);
//                    refresh.setTextColor(ContextCompat.getColor(mContext, R.color.blue_bg_4B96F3));
                    refresh.setVisibility(View.GONE);
                    top.setVisibility(View.GONE);
                    downTv.setVisibility(View.GONE);
                    resonImg.setVisibility(View.GONE);
                    deleteTv.setVisibility(View.VISIBLE);
                } else if (item.getIs_verify() == 1) {
                    if (item.getStatus() == 0) {
                        stateTv.setText("被下架");
                        stateTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_gray_color2));
                        editTv.setVisibility(View.VISIBLE);
                        refresh.setText("刷新");
                        refresh.setBackgroundResource(R.drawable.seletor_textblue);
                        refresh.setTextColor(ContextCompat.getColor(mContext, R.color.blue_bg_4B96F3));
                        refresh.setVisibility(View.VISIBLE);
                        downTv.setVisibility(View.GONE);
                        resonImg.setVisibility(View.GONE);
                        top.setVisibility(View.GONE);
                        deleteTv.setVisibility(View.VISIBLE);


                    } else {
                        //已发布下判断是否置顶预约
                        //0：已取消，1：预约中，2：置顶中，3：置顶已完成，4：未预约
                        if (item.getReservation_status() == 0 || item.getReservation_status() == 3 || item.getReservation_status() == 4) {
                            stateTv.setText("已发布");
                            stateTv.setTextColor(ContextCompat.getColor(mContext, R.color.vip_info_color));
                            editTv.setVisibility(View.VISIBLE);
                            refresh.setText("刷新");
                            refresh.setBackgroundResource(R.drawable.seletor_textblue);
                            refresh.setTextColor(ContextCompat.getColor(mContext, R.color.blue_bg_4B96F3));
                            refresh.setVisibility(View.VISIBLE);
                            downTv.setVisibility(View.VISIBLE);
                            top.setVisibility(View.VISIBLE);
                            resonImg.setVisibility(View.GONE);
                            deleteTv.setVisibility(View.VISIBLE);
                        } else if (item.getReservation_status() == 1) {
                            if(item.getReservation_time().size()>1){
                                stateTv.setText("已预约" + "  (" + item.getReservation_time().size() + "天)");
                            }else {
                                if(item.getReservation_time().size()>0) {
                                    String time = TimeUtils.fromatDate(item.getReservation_time().get(0) * 1000, "MM月dd日");
                                    stateTv.setText("已预约" + "  (" + time + ")");
                                }
                            }
                            stateTv.setTextColor(ContextCompat.getColor(mContext, R.color.text_color_green));
                            editTv.setVisibility(View.GONE);
                            refresh.setText("取消预约");
                            refresh.setBackgroundResource(R.drawable.seletor_textred);
                            refresh.setTextColor(ContextCompat.getColor(mContext, R.color.bg_color_red_DE6654));
                            refresh.setVisibility(View.VISIBLE);
                            downTv.setVisibility(View.GONE);
                            top.setVisibility(View.GONE);
                            resonImg.setVisibility(View.GONE);
                            deleteTv.setVisibility(View.GONE);

                        } else if (item.getReservation_status() == 2) {
                            stateTv.setText("置顶中");
                            stateTv.setTextColor(ContextCompat.getColor(mContext, R.color.vip_info_color));
                            editTv.setVisibility(View.GONE);
                            refresh.setText("取消置顶");
                            refresh.setBackgroundResource(R.drawable.seletor_textred);
                            refresh.setTextColor(ContextCompat.getColor(mContext, R.color.bg_color_red_DE6654));
                            refresh.setVisibility(View.VISIBLE);
                            downTv.setVisibility(View.GONE);
                            top.setVisibility(View.GONE);
                            resonImg.setVisibility(View.GONE);
                            deleteTv.setVisibility(View.GONE);
                        }
                    }
                } else if (item.getIs_verify() == 2) {
                    stateTv.setText("审核未通过");
                    stateTv.setTextColor(ContextCompat.getColor(mContext, R.color.king_color));
                    resonImg.setVisibility(View.VISIBLE);
                    editTv.setVisibility(View.VISIBLE);
                    deleteTv.setVisibility(View.VISIBLE);
                    refresh.setVisibility(View.GONE);
                    downTv.setVisibility(View.GONE);
                    top.setVisibility(View.GONE);
                    if (item.getSort_verify() == 3) {
                        //重新发布
                        editTv.setVisibility(View.GONE);
                        refresh.setText("重新发布");
                        refresh.setBackgroundResource(R.drawable.seletor_textblue);
                        refresh.setTextColor(ContextCompat.getColor(mContext, R.color.blue_bg_4B96F3));
                        refresh.setVisibility(View.VISIBLE);
                    }
                }
                editTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onMore(helper.getAdapterPosition(), "1");
                    }
                });

                top.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onMore(helper.getAdapterPosition(), "6");
                    }
                });
                refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (refresh.getText().toString().equals("刷新")) {
                            callback.onMore(helper.getAdapterPosition(), "3");
                        } else if (refresh.getText().toString().equals("取消预约")) {
                            callback.onMore(helper.getAdapterPosition(), "7");
                        } else if (refresh.getText().toString().equals("取消置顶")) {
                            callback.onMore(helper.getAdapterPosition(), "8");
                        } else if (refresh.getText().toString().equals("重新发布")) {
                            callback.onMore(helper.getAdapterPosition(), "9");
                        }

                    }
                });
                downTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onMore(helper.getAdapterPosition(), "2");

                    }
                });
                deleteTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onMore(helper.getAdapterPosition(), "4");
                    }
                });

                Ll.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        if (item.getStatus() == 1) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("detailId", item.getId());
                            Intent intent = new Intent(mContext, CoopDetailActivity.class);
                            intent.putExtras(bundle);
                            mContext.startActivityForResult(intent, MyPushActivity.REQUESTCODE, bundle);
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putInt("detailId", item.getId());
                            Intent intent = new Intent(mContext, CoopDetailActivity.class);
                            intent.putExtras(bundle);
                            mContext.startActivityForResult(intent, MyPushActivity.REQUESTCODE, bundle);
                        }
                    }
                });
                resonLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item.getIs_verify() == 2) {
                            //查看未审核通过原因
                            callback.onMore(helper.getAdapterPosition(), "5");
                        }

                    }

                });
                break;
            case MyPushBean.ListBean.THETOP:
//                helper.getView(R.id.bgetmypush_toptv).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String content = "服务经理你好，我想要获得超级曝光特权";
//                        //IMUtils.singleChat(mContext, String.valueOf(UserInfoHelper.getIntance().getCenterBean().getUsers().getF_id()), "客服", "1", content);
//
//                    }
//                });


                SuperExposureActivity.start(mContext,MYPUSHCODETWO);

                break;
            default:
                break;
        }

    }

    public interface Callback {
        void onEdit(int position);

        void onMore(int position, String isUp);
    }
}
