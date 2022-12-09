package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.AcceptedOrdersPersonActivity;
import com.xinniu.android.qiqueqiao.activity.ApproveCardActivity;
import com.xinniu.android.qiqueqiao.activity.CardingCardActivity;
import com.xinniu.android.qiqueqiao.activity.CoopDetailActivity;
import com.xinniu.android.qiqueqiao.activity.DataPlusBuyActivity;
import com.xinniu.android.qiqueqiao.activity.LoginNewActivity;
import com.xinniu.android.qiqueqiao.activity.NewResourceActivity;
import com.xinniu.android.qiqueqiao.activity.PersonCentetActivity;
import com.xinniu.android.qiqueqiao.activity.RewardDetailActivity;
import com.xinniu.android.qiqueqiao.activity.RewardOrderDetailActivity;
import com.xinniu.android.qiqueqiao.activity.ServiceDetailActivity;
import com.xinniu.android.qiqueqiao.activity.SuperExposureActivity;
import com.xinniu.android.qiqueqiao.activity.TopCardActivity;
import com.xinniu.android.qiqueqiao.activity.TransactionDetailsActivity;
import com.xinniu.android.qiqueqiao.activity.VipV4ListActivity;
import com.xinniu.android.qiqueqiao.activity.WalletDetailActivity;
import com.xinniu.android.qiqueqiao.bean.SystemMsgBean;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.ImageLoader;
import com.xinniu.android.qiqueqiao.utils.TimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzq on 2017/12/25.
 */

public class SystemMsgAdapter extends BaseMultiItemQuickAdapter<SystemMsgBean, BaseViewHolder> {

    private Context context;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public SystemMsgAdapter(Context context, List<SystemMsgBean> data) {
        super(data);
        this.context = context;
        addItemType(SystemMsgBean.COMMMON, R.layout.item_system_msg);
        addItemType(SystemMsgBean.GROUPTYPE, R.layout.item_system_group);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SystemMsgBean item) {
        switch (helper.getItemViewType()) {
            case SystemMsgBean.COMMMON:
                helper.setText(R.id.time, TimeUtils.millis2Stringx(item.getCreate_time() * 1000));
                helper.setText(R.id.content, item.getTitle());
                helper.setText(R.id.contentTv, item.getContent());
                if (!(item.getType() == 2 || item.getType() == 3)) {
                    helper.setVisible(R.id.system_Rl, false);
                } else {
                    helper.setVisible(R.id.system_Rl, true);
                }
                helper.getView(R.id.gotosee).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getType() == 2) {
                            if (item.getUrl().startsWith("qiqueqiao://www.xinniu.com/groupDetailNormal")) {
                                String groupDetail = item.getUrl();
                                groupDetail = groupDetail.replace("qiqueqiao://www.xinniu.com/groupDetailNormal?", "");
                                String[] groupId = groupDetail.split("=");
                                if (item.getTitle().contains("通过")) {
                                    goToOperation.goToGroupDetail(Integer.parseInt(groupId[1]), 1);
                                } else {
                                    goToOperation.goToGroupDetail(Integer.parseInt(groupId[1]), 2);
                                }


                            } else {
                                if (item.getUrl().startsWith("qiqueqiao://www.xinniu.com/taskCenter")) {
                                    //任务中心
                                    ApproveCardActivity.start(context, "task");
                                } else if (item.getUrl().startsWith("qiqueqiao://www.xinniu.com/vipCenter")) {
                                    //vip
                                    Intent intent = new Intent(context, VipV4ListActivity.class);
                                    context.startActivity(intent);

                                } else if (item.getUrl().startsWith("qiqueqiao://www.xinniu.com/userCenterInfo")) {
                                    //用户个人中心
                                    String[] urls = item.getUrl().split("=");
                                    if (!UserInfoHelper.getIntance().isLogin()) {
                                        LoginNewActivity.start(context);
                                        return;
                                    }
                                    if (urls.length > 1) {
                                        String targetId = urls[1];
                                        PersonCentetActivity.start(context, targetId, "1");
                                    }


                                } else if (item.getUrl().startsWith("qiqueqiao://www.xinniu.com/newResource")) {
                                    //合作信息列表
                                    NewResourceActivity.start(mContext);

                                } else if (item.getUrl().startsWith("qiqueqiao://www.xinniu.com/guaranteeDetail")) {
                                    //担保交易详情
                                    String[] urls = item.getUrl().split("=");
                                    if (!UserInfoHelper.getIntance().isLogin()) {
                                        LoginNewActivity.start(context);
                                        return;
                                    }
                                    if (urls.length > 1) {
                                        String targetId = urls[1];
                                        TransactionDetailsActivity.start(context, targetId);
                                    }


                                } else if (item.getUrl().startsWith("qiqueqiao://www.xinniu.com/accountBillDetail")) {
                                    //账单详情
                                    String[] urls = item.getUrl().split("=");
                                    if (!UserInfoHelper.getIntance().isLogin()) {
                                        LoginNewActivity.start(context);
                                        return;
                                    }
                                    if (urls.length > 1) {
                                        String targetId = urls[1];
                                        WalletDetailActivity.start(context, Integer.parseInt(targetId));
                                    }
                                } else if (item.getUrl().startsWith("qiqueqiao://www.xinniu.com/RewardDetail")) {
                                    //悬赏详情
                                    String[] urls = item.getUrl().split("=");
                                    if (!UserInfoHelper.getIntance().isLogin()) {
                                        LoginNewActivity.start(context);
                                        return;
                                    }
                                    if (urls.length > 1) {
                                        String targetId = urls[1];
                                        RewardDetailActivity.start(context, targetId);
                                    }
                                } else if (item.getUrl().startsWith("qiqueqiao://www.xinniu.com/RewardReceivedOrderPeople")) {
                                    //已接单人
                                    String[] urls = item.getUrl().split("=");
                                    if (!UserInfoHelper.getIntance().isLogin()) {
                                        LoginNewActivity.start(context);
                                        return;
                                    }
                                    if (urls.length > 1) {
                                        String order_sn = urls[1];
                                        AcceptedOrdersPersonActivity.start(context, order_sn);
                                    }
                                } else if (item.getUrl().startsWith("qiqueqiao://www.xinniu.com/RewardReceivedOrder")) {
                                    //悬赏订单详情
                                    if (!UserInfoHelper.getIntance().isLogin()) {
                                        LoginNewActivity.start(mContext);
                                        return;
                                    }
                                    Map<String, String> typeMap = new HashMap<>();
                                    Uri uri = Uri.parse(item.getUrl());
                                    String queryStr = uri.getQuery();
                                    String[] ids = queryStr.split("&");
                                    for (int i = 0; i < ids.length; i++) {
                                        String[] type = ids[i].split("=");
                                        if (type.length > 0) {
                                            typeMap.put(type[0], type[1]);
                                        }
                                    }
                                    String id = typeMap.get("id");
                                    String order_sn = typeMap.get("order_sn");
                                    RewardOrderDetailActivity.start(mContext, order_sn, Integer.parseInt(id));

                                } else if (item.getUrl().startsWith("qiqueqiao://www.xinniu.com/PropsMallList")) {
//                qiqueqiao://www.xinniu.com/PropsMallList?mall_type=1
//                刷新卡
//                置顶卡
//                梳理卡
//                加油包
                                    String[] urls = item.getUrl().split("=");
                                    if (!UserInfoHelper.getIntance().isLogin()) {
                                        LoginNewActivity.start(mContext);
                                        return;
                                    }
                                    if (urls.length > 1) {
                                        String type = urls[1];
                                        if (type.equals("1")) {
                                            TopCardActivity.start(mContext, "1");
                                        } else if (type.equals("2")) {
                                            SuperExposureActivity.start(mContext, "1");
                                        } else if (type.equals("3")) {
                                            CardingCardActivity.start(mContext, "1");
                                        } else if (type.equals("4")) {
                                            DataPlusBuyActivity.start(mContext);
                                        }
                                    }
                                } else {
//                                    Intent intent = new Intent(context, AgreeMentActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putString("theUrl", item.getUrl());
//                                    bundle.putString("thetitle", item.getTitle());
//                                    intent.putExtras(bundle);
//                                    context.startActivity(intent, bundle);
                                    if (item.getUrl().contains("needLogin=1")) {
                                        if (!UserInfoHelper.getIntance().isLogin()) {
                                            LoginNewActivity.start(mContext);
                                            return;
                                        }
                                    }
                                    Intent intent = new Intent(mContext, ApproveCardActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("theUrl", item.getUrl());
                                    bundle.putString("thetitle", "推广活动");
                                    bundle.putString("webType", "Event");
                                    intent.putExtras(bundle);
                                    mContext.startActivity(intent, bundle);
                                }
                            }
                        } else if (item.getType() == 3) {
                            CoopDetailActivity.start(context, item.getResource_id());
                        } else if (item.getType() == 6) {
                            ServiceDetailActivity.start(context, item.getResource_id());
                            //服务详情
                        }

                    }
                });
                if (item.isNew()) {
                    helper.getView(R.id.item_system_Rl).setSelected(true);
                } else {
                    helper.getView(R.id.item_system_Rl).setSelected(false);
                }

                break;
            case SystemMsgBean.GROUPTYPE:
                helper.setText(R.id.time, TimeUtils.millis2Stringx(item.getCreate_time() * 1000));
                helper.setText(R.id.content, item.getTitle());
                helper.setText(R.id.mgroupcontent, item.getContent());
                ImageLoader.loadAvter(item.getHead_pic(), (ImageView) helper.getView(R.id.mheadImg));

                if (item.getType() == 4) {
                    if (item.getApplication_status() == 0) {
                        helper.setVisible(R.id.msystem_explain, false);
                        helper.setVisible(R.id.bsystem_agreeTv, true);
                        helper.setVisible(R.id.bsystem_refuseTv, true);
                    } else if (item.getApplication_status() == 1) {
                        if (item.getOperate_identity() == 1) {
                            helper.setText(R.id.msystem_explain, "已同意  由管理员" + "“" + item.getOperate_username() + "”" + "处理");
                        } else if (item.getOperate_identity() == 2) {
                            helper.setText(R.id.msystem_explain, "已同意  由群主" + "“" + item.getOperate_username() + "”" + "处理");
                        }
                        helper.setVisible(R.id.bsystem_agreeTv, false);
                        helper.setVisible(R.id.bsystem_refuseTv, false);
                    } else {
                        if (item.getOperate_identity() == 1) {
                            helper.setText(R.id.msystem_explain, "已拒绝  由管理员" + "“" + item.getOperate_username() + "”" + "处理");
                        } else if (item.getOperate_identity() == 2) {
                            helper.setText(R.id.msystem_explain, "已拒绝  由群主" + "“" + item.getOperate_username() + "”" + "处理");
                        }
                        helper.setVisible(R.id.bsystem_agreeTv, false);
                        helper.setVisible(R.id.bsystem_refuseTv, false);
                    }
                } else {
                    helper.setVisible(R.id.msystemgroupRl, false);

                }
                helper.getView(R.id.bsystem_agreeTv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToOperation.goToAgree(item.getJoin_id(), helper.getAdapterPosition());
                    }
                });
                helper.getView(R.id.bsystem_refuseTv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToOperation.goToRefuse(item.getJoin_id(), helper.getAdapterPosition());
                    }
                });
                helper.getView(R.id.mheadImg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToOperation.goToPerson(item.getUid());
                    }
                });
                if (item.isNew()) {
                    helper.getView(R.id.item_system_Rl).setSelected(true);
                }

                break;
            default:
                break;


        }

    }

    public interface goToOperation {
        void goToAgree(int id, int postion);

        void goToRefuse(int id, int postion);

        void goToGroupDetail(int groupId, int type);

        void goToPerson(String uid);
    }

    private goToOperation goToOperation;

    public void setGoToOperation(SystemMsgAdapter.goToOperation goToOperation) {
        this.goToOperation = goToOperation;
    }
}
