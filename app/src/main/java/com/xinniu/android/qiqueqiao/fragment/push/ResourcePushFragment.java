package com.xinniu.android.qiqueqiao.fragment.push;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.activity.AppointmentTimeActivity;
import com.xinniu.android.qiqueqiao.activity.CertificationActivity;
import com.xinniu.android.qiqueqiao.activity.CompanyEditActivity;
import com.xinniu.android.qiqueqiao.activity.PublishNewActivity;
import com.xinniu.android.qiqueqiao.activity.PublishSelectTypeActivity;
import com.xinniu.android.qiqueqiao.activity.SuperExposureActivity;
import com.xinniu.android.qiqueqiao.activity.TopCardActivity;
import com.xinniu.android.qiqueqiao.adapter.MyPushAdapter;
import com.xinniu.android.qiqueqiao.base.LazyBaseFragment;
import com.xinniu.android.qiqueqiao.bean.FixedTopCancleBean;
import com.xinniu.android.qiqueqiao.bean.MyPushBean;
import com.xinniu.android.qiqueqiao.bean.VerifyInfo;
import com.xinniu.android.qiqueqiao.customs.qldialog.QLTipDialog;
import com.xinniu.android.qiqueqiao.dialog.AlertDialogUtils;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AllResultDoCallback;
import com.xinniu.android.qiqueqiao.request.callback.CancleFixedTopCallback;
import com.xinniu.android.qiqueqiao.request.callback.CommonCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetVerifyInfoCallback;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

public class ResourcePushFragment extends LazyBaseFragment {
    @BindView(R.id.msurplus_num)
    TextView msurplusNum;
    @BindView(R.id.bmypush_buytop)
    TextView bmypushBuytop;
    @BindView(R.id.mtopcard_num)
    TextView mtopcardNum;
    @BindView(R.id.pushtv)
    TextView pushtv;
    @BindView(R.id.bgotoPush)
    TextView bgotoPush;
    @BindView(R.id.yperch_Rl)
    RelativeLayout yperchRl;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.my_push_swipe)
    SmartRefreshLayout myPushSwipe;
    @BindView(R.id.mtopcard_num_2)
    TextView mtopcardNum2;
    @BindView(R.id.bmypush_buytop_2)
    TextView bmypushBuytop2;
    private List<MyPushBean.ListBean> mData = new ArrayList<>();
    private MyPushAdapter mAdapter;
    private int curPage = 1;
    public static int MYPUSHCODE = 101;
    public static int MYPUSHCODETWO = 102;
    public static int MYPUSHCODETHREE = 108;
    private Call mCall;
    private int refreshNum;
    private int cardNum;
    private int fixedNum;
    protected View footView1;

    public static ResourcePushFragment newInstance() {

        Bundle args = new Bundle();

        ResourcePushFragment fragment = new ResourcePushFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_resource_push;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        footView1 = getActivity().getLayoutInflater().inflate(R.layout.view_unload_more_gray, null);
        initAdapter();
        getResource(curPage, true);
        myPushSwipe.setEnableRefresh(false);
        mAdapter.setCallback(new MyPushAdapter.Callback() {
            @Override
            public void onEdit(int position) {

            }

            @Override
            public void onMore(final int pos, String isUp) {
                if (isUp.equals("1")) {
                    PublishNewActivity.start((AppCompatActivity) getActivity(), mData.get(pos).getId(), mData.get(pos).getP_name(), mData.get(pos).getP_id(), 1000);
                } else if (isUp.equals("2")) {
                    showDeleteDialog(pos);
                } else if (isUp.equals("3")) {
                    unrefresh(pos);
                } else if (isUp.equals("4")) {
                    showDeleteV2Dialog(pos);
                } else if (isUp.equals("5")) {
                    //查看未审核通过原因
                    showResonDialog(pos);
                } else if (isUp.equals("6")) {
                    //置顶
                    untop(pos);
                } else if (isUp.equals("7")) {
                    //取消预约
                    AlertDialogUtils.AlertDialog(getContext(), "确定取消预约", "取消预约将返回消耗的置顶卡", "再想想", "确定取消", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(DialogInterface dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {
                            //取消操作
                            cancleFixTop(pos);

                        }
                    });
                } else if (isUp.equals("8")) {
                    //取消置顶
                    AlertDialogUtils.AlertDialog(getContext(), "确定取消置顶", "取消后此合作信息将不再置顶", "再想想", "确定取消", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(DialogInterface dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {
                            //取消操作
                            cancleFixTop(pos);
                        }
                    });
                } else if (isUp.equals("9")) {
                    //重新发布
                    toReleaseActivity();
                }

            }
        });


        myPushSwipe.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                curPage++;
                getResource(curPage, false);
            }
        });

    }

    private void cancleFixTop(final int pos) {
        RequestManager.getInstance().cancelFixedTop(mData.get(pos).getId(), new CancleFixedTopCallback() {
            @Override
            public void onSuccess(FixedTopCancleBean item, String message) {
                ToastUtils.showCentetImgToast(getContext(), message);
//                curPage = 1;
//                getResource(curPage, false);
                MyPushBean.ListBean listBean = mData.get(pos);

                listBean.setReservation_status(0);
                mAdapter.notifyItemChanged(pos, listBean);
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(getActivity(), msg);
            }
        });

    }

    //置顶操作
    private void untop(final int pos) {


        if (fixedNum > 0) {
            //跳到预约时间
            int p_id = mData.get(pos).getP_id();
            String p_name = mData.get(pos).getP_name();
            AppointmentTimeActivity.start((AppCompatActivity) getActivity(), p_id, p_name, mData.get(pos).getId(), fixedNum, pos, MYPUSHCODETHREE);

        } else {
            AlertDialogUtils.AlertDialog(getContext(), "置顶卡数量不足", "请购买置顶卡置顶您的合作信息", "取消", "去购买", new AlertDialogUtils.setOnClick() {
                @Override
                public void setLeftOnClick(DialogInterface dialog) {
                    dialog.dismiss();
                }

                @Override
                public void setRightOnClick(DialogInterface dialog) {
                    SuperExposureActivity.start((AppCompatActivity) getActivity(), MYPUSHCODETWO);
                }
            });
        }


    }

    private void showResonDialog(int pos) {
        RequestManager.getInstance().getVerifyInfo(mData.get(pos).getId(), new GetVerifyInfoCallback() {
            @Override
            public void onSuccess(VerifyInfo item) {
                new QLTipDialog.Builder(getActivity())
                        .setCancelable(true)
                        .setCancelableOnTouchOutside(true)
                        .setTitle("审核未通过")
                        .setMessage(item.getMessage())
                        .setPositiveButton("我知道了", new QLTipDialog.IPositiveCallback() {
                            @Override
                            public void onClick() {

                            }
                        })
                        .show((AppCompatActivity) getActivity());
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(getContext(), msg);
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    private void showDeleteDialog(final int position) {
        new QLTipDialog.Builder(getContext())
                .setMessage("您确定下架这个资源吗？")
                .setNegativeButton("取消", new QLTipDialog.INegativeCallback() {
                    @Override
                    public void onClick() {

                    }
                })
                .setPositiveButton("确定", new QLTipDialog.IPositiveCallback() {
                    @Override
                    public void onClick() {
                        delete(position);
                    }
                })
                .show((AppCompatActivity) getActivity());
    }

    private void showDeleteV2Dialog(final int position) {
        new QLTipDialog.Builder(getContext())
                .setMessage("您确定删除这个资源吗？")
                .setNegativeButton("取消", new QLTipDialog.INegativeCallback() {
                    @Override
                    public void onClick() {

                    }
                })
                .setPositiveButton("确定", new QLTipDialog.IPositiveCallback() {
                    @Override
                    public void onClick() {
                        deleteResource(mData.get(position).getId());
                    }
                })
                .show((AppCompatActivity) getActivity());
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MyPushAdapter((AppCompatActivity) getActivity(), mData);
        recyclerView.setAdapter(mAdapter);
    }

    private void getResource(final int page, final boolean isShow) {

        RequestManager.getInstance().getMyPush(page, new RequestCallback<MyPushBean>() {
            @Override
            public void requestStart(Call call) {
                if (isShow) {
                    showBookingToast(3);
                }
                mCall = call;
            }

            @Override
            public void onSuccess(MyPushBean myPushBean) {
                curPage = page;
                refreshNum = myPushBean.getRefresh_num();
                cardNum = myPushBean.getCard_num();
                fixedNum = myPushBean.getFixed_top_card_num();
                msurplusNum.setText("今日剩余刷新权限: " + refreshNum + "次");
                mtopcardNum.setText("刷新卡 X " + cardNum);
                mtopcardNum2.setText("置顶卡 X " + fixedNum);
                if (page == 1) {
                    mData.clear();
                    if (myPushBean.getList().size() == 0) {
                        yperchRl.setVisibility(View.VISIBLE);
                        mAdapter.removeAllFooterView();
                        myPushSwipe.setEnableLoadMore(false);
                    } else {
                        yperchRl.setVisibility(View.GONE);
                        if (myPushBean.getHasMore() == 0) {
                            mAdapter.setFooterView(footView1);
                            myPushSwipe.setEnableLoadMore(false);
                        } else {
                            mAdapter.removeAllFooterView();
                            myPushSwipe.setEnableLoadMore(true);
                        }
                    }
                } else {
                    if (myPushBean.getHasMore() == 0) {
                        mAdapter.setFooterView(footView1);
                        myPushSwipe.setEnableLoadMore(false);
                    } else {
                        mAdapter.removeAllFooterView();
                        myPushSwipe.setEnableLoadMore(true);
                    }
                }
                for (int i = 0; i < myPushBean.getList().size(); i++) {
                    myPushBean.getList().get(i).setItemType(MyPushBean.ListBean.COMMON);
                }
//                if (page == 1) {
//                    myPushBean.getList().add(0, new MyPushBean.ListBean(MyPushBean.ListBean.THETOP));
//                }
                mData.addAll(myPushBean.getList());
                mAdapter.setCompany(myPushBean.getCompany());
                mAdapter.notifyDataSetChanged();
                if (myPushSwipe != null) {
                    if (myPushSwipe.isEnableRefresh()) {
                        myPushSwipe.finishRefresh(true);
                    }
                    if (myPushSwipe.isEnableLoadMore()) {
                        myPushSwipe.finishLoadMore(true);
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(getContext(), msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });
    }

    private void delete(int position) {
        RequestManager.getInstance().delResource(mData.get(position).getId(), new RequestCallback<String>() {
            @Override
            public void requestStart(Call call) {
                showBookingToast(2);
                mCall = call;
            }

            @Override
            public void onSuccess(String s) {
                ToastUtils.showCentetImgToast(getContext(), "下架成功");
                curPage = 1;
                getResource(curPage, false);
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(getContext(), msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
    }

    private void unrefresh(final int pos) {
        RequestManager.getInstance().goRefreshCheck(1, new AllResultDoCallback() {
            @Override
            public void onSuccess(String msg) {
                if (refreshNum > 0) {
                    AlertDialogUtils.AlertDialog(getContext(), "将此条合作信息刷新至最高排名", "取消", "确认刷新", new AlertDialogUtils.setOnClick() {
                        @Override
                        public void setLeftOnClick(DialogInterface dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void setRightOnClick(DialogInterface dialog) {
                            refresh(pos);
                        }
                    });
                } else {
                    if (cardNum > 0) {
                        AlertDialogUtils.AlertDialog(getActivity(), "您本日刷新权限已不足，本次刷新将消耗1张刷新卡", "取消", "立即刷新", new AlertDialogUtils.setOnClick() {
                            @Override
                            public void setLeftOnClick(DialogInterface dialog) {
                                dialog.dismiss();
                            }

                            @Override
                            public void setRightOnClick(DialogInterface dialog) {
                                refresh(pos);
                            }
                        });

                    } else {
                        AlertDialogUtils.AlertDialog(getContext(), "本日刷新权限已用完", "请购买刷新卡刷新您的合作信息", "取消", "去购买", new AlertDialogUtils.setOnClick() {
                            @Override
                            public void setLeftOnClick(DialogInterface dialog) {
                                dialog.dismiss();
                            }

                            @Override
                            public void setRightOnClick(DialogInterface dialog) {
                                TopCardActivity.start((AppCompatActivity) getActivity(), MYPUSHCODE);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {

                ToastUtils.showCentetToast(getContext(), msg);

            }
        });
    }

    private void refresh(int pos) {
        RequestManager.getInstance().refresh(1, mData.get(pos).getId(), new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                ToastUtils.showCentetImgToast(getActivity(), resultDO.getMsg());
                curPage = 1;
                getResource(curPage, false);
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(getActivity(), msg);
            }
        });
    }


    private void deleteResource(int resourceId) {
        RequestManager.getInstance().delResourceV2(resourceId, new RequestCallback<String>() {
            @Override
            public void requestStart(Call call) {
                showBookingToast(2);
                mCall = call;
            }

            @Override
            public void onSuccess(String s) {
                ToastUtils.showCentetImgToast(getContext(), "下架成功");
                curPage = 1;
                getResource(curPage, false);
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(getContext(), msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();

            }
        });
    }


    @OnClick({R.id.bgotoPush, R.id.bmypush_buytop, R.id.bmypush_buytop_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bgotoPush:
                toReleaseActivity();
                break;
            case R.id.bmypush_buytop:
                TopCardActivity.start((AppCompatActivity) getActivity(), MYPUSHCODE);
                break;
            case R.id.bmypush_buytop_2:
                SuperExposureActivity.start((AppCompatActivity) getActivity(), MYPUSHCODETWO);
                break;
            default:
                break;
        }

    }

    /**
     * 转调到发布页面
     */
    public void toReleaseActivity() {
        showBookingToast(2);
        RequestManager.getInstance().isPerfect(new CommonCallback() {
            @Override
            public void onSuccess(ResultDO resultDO) {
                dismissBookingToast();
//                startActivityForResult(new Intent(MyPushActivity.this, PublishActivity.class), MainActivity.RELEASE_SUCCESS);
                PublishSelectTypeActivity.start((AppCompatActivity) getActivity());
            }

            @Override
            public void onFailed(int code, final String msg) {
                dismissBookingToast();
                if (code == 202) {
                    new QLTipDialog.Builder(getContext())
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("我知道了", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            })
                            .show((AppCompatActivity) getActivity());
                } else if (code == 305) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(msg);
                    builder.setPositiveButton("去完善", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            ToastUtils.showCentetImgToast(MainActivity.this, msg);
                            Intent intent = new Intent(getContext(), CompanyEditActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }else if (code == 310) {
                    //未实人认证
                    new QLTipDialog.Builder(getContext())
                            .setCancelable(true)
                            .setCancelableOnTouchOutside(true)
                            .setMessage(msg)
                            .setNegativeButton("取消", new QLTipDialog.INegativeCallback() {
                                @Override
                                public void onClick() {

                                }
                            }).setPositiveButton("去认证", new QLTipDialog.IPositiveCallback() {
                        @Override
                        public void onClick() {
                            CertificationActivity.start(getContext(), 1);
                        }
                    })
                            .show((AppCompatActivity) getActivity());
                }
            }
        });
    }

    public void refresh() {

        curPage = 1;
        getResource(curPage, false);


    }

    public void refreshTwo(int fixed_top_card_num, List<Long> reservation_time, int p) {
        fixedNum = fixed_top_card_num;
        mtopcardNum2.setText("置顶卡 X " + fixedNum);
        if (p == -1) {
            curPage = 1;
            getResource(curPage, false);
        } else {
            MyPushBean.ListBean listBean = mData.get(p);
            listBean.setReservation_time(reservation_time);
            listBean.setReservation_status(1);
            mAdapter.notifyItemChanged(p, listBean);
        }


    }

}
