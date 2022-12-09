package com.xinniu.android.qiqueqiao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.OtherTagAdapter;
import com.xinniu.android.qiqueqiao.adapter.ResourceTypeAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.SeclectCateBean;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.customs.NestedRecyclerView;
import com.xinniu.android.qiqueqiao.customs.label.FlowLayoutManager;
import com.xinniu.android.qiqueqiao.customs.label.TypeTagAdapter;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.AddTagCallback;
import com.xinniu.android.qiqueqiao.request.callback.GetSelectCateCallback;
import com.xinniu.android.qiqueqiao.utils.NoScrollRecyclerView;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.utils.wxpay.NoReuseRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yuchance on 2018/4/12.
 */


public class ResourceTypeActivity extends BaseActivity {


    public final static String OFFERSELECTARRAY = "offerSelectArray";
    public final static String NEEDSELECTARRAY = "needSelectArray";
    public final static int OFFERREQUESTCODE = 5001;
    public final static int NEEDREQUESTCODE = 6001;
    public final static int OFFERRESULTCODE = 5002;
    public final static int NEEDRESULTCODE = 6002;
    @BindView(R.id.bt_return)
    RelativeLayout btReturn;
    @BindView(R.id.bt_finish)
    TextView btFinish;
    @BindView(R.id.resource_rv)
    NoReuseRecyclerView mresourceRv;
    @BindView(R.id.otherResource_rv)
    NestedRecyclerView otherResourceRv;
    private List<SeclectCateBean.CommonCategoryBean> datas = new ArrayList<>();
    private ResourceTypeAdapter adapter;
    private SeclectCateBean seclectCateBean = new SeclectCateBean();
    private OtherTagAdapter tagAdapter;
    private List<SeclectCateBean.UserCategoryBean> other = new ArrayList<>();
    private StringBuffer sb;
    private StringBuffer attr;
    private String type;
    public static String RESOURCETYPE = "RESOURCETYPE";
    public static String RESOURCENAME = "RESOURCENAME";
    public static String RESOURCEATTR = "RESOURCEATTR";
    private ArrayList<Integer> offerselectList = new ArrayList<>();
    private ArrayList<Integer> needselectList = new ArrayList<>();
    private ArrayList<String> offerString = new ArrayList<>();
    private ArrayList<String> needString = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_resource_type;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString(RESOURCETYPE);
        if (type.equals("offer")) {
            offerselectList = bundle.getIntegerArrayList(OFFERSELECTARRAY);
        }
        if (type.equals("need")) {
            needselectList = bundle.getIntegerArrayList(NEEDSELECTARRAY);
        }
        LinearLayoutManager manager = new LinearLayoutManager(ResourceTypeActivity.this, LinearLayoutManager.VERTICAL, false);
        mresourceRv.setLayoutManager(manager);
        adapter = new ResourceTypeAdapter(ResourceTypeActivity.this, R.layout.item_resource_type, datas, seclectCateBean,other);
        mresourceRv.setAdapter(adapter);
//        Li flowLayoutManager = new FlowLayoutManager();
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager(){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

//        LinearLayoutManager flowLayoutManager = new LinearLayoutManager(ResourceTypeActivity.this,LinearLayoutManager.HORIZONTAL,false);
        otherResourceRv.setLayoutManager(flowLayoutManager);
        tagAdapter = new OtherTagAdapter(ResourceTypeActivity.this,seclectCateBean,other);
        tagAdapter.setAddTv(new OtherTagAdapter.addTv() {
            @Override
            public void add() {
                setPopWindow(R.layout.view_resource_type_other);
                final EditText contentEt = (EditText) popview.findViewById(R.id.view_type_other_ed);
                TextView cancelTv = (TextView) popview.findViewById(R.id.view_other_cancelTv);
                TextView addTv = (TextView) popview.findViewById(R.id.view_other_addTv);
//                showSoftInputFromWindow(ResourceTypeActivity.this,contentEt);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                cancelTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dispopwindow();
                    }
                });
                addTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            if (!TextUtils.isEmpty(contentEt.getText().toString().trim())) {
                                addTag(contentEt.getText().toString().trim());
                            } else {
                                ToastUtils.showToast(ResourceTypeActivity.this, "请输入自定义类型");
                            }
                    }
                });
            }
        });
        otherResourceRv.setAdapter(tagAdapter);
        buildData();
    }

    private void buildData() {
        showBookingToast(2);
        RequestManager.getInstance().getUserCategoryV3(new GetSelectCateCallback() {
            @Override
            public void onSuccess(SeclectCateBean list) {
                dismissBookingToast();
                datas.addAll(list.getCommonCategory());
                other.addAll(list.getUserCategory());
                if (type.equals("offer")){
                    for (int i = 0; i < datas.size(); i++) {
                        for (int j = 0; j <datas.get(i).getZlist().size() ; j++) {
                            if (offerselectList.contains(datas.get(i).getZlist().get(j).getId())){
                                datas.get(i).getZlist().get(j).setCheck(true);
                            }else {
                                datas.get(i).getZlist().get(j).setCheck(false);
                            }
                        }
                    }
                    for (int i = 0; i <other.size() ; i++) {
                        if (offerselectList.contains(other.get(i).getId())){
                            other.get(i).setCheck(true);
                        }else{
                            other.get(i).setCheck(false);
                        }
                    }

                }
                if (type.equals("need")) {
                    for (int i = 0; i < datas.size(); i++) {
                        for (int j = 0; j <datas.get(i).getZlist().size() ; j++) {
                            if (needselectList.contains(datas.get(i).getZlist().get(j).getId())){
                                datas.get(i).getZlist().get(j).setCheck(true);
                            }else {
                                datas.get(i).getZlist().get(j).setCheck(false);
                            }
                        }
                    }
                    for (int i = 0; i <other.size() ; i++) {
                        if (needselectList.contains(other.get(i).getId())){
                            other.get(i).setCheck(true);
                        }else{
                            other.get(i).setCheck(false);
                        }
                    }
                }

                adapter.setSeclectCateBean(list);
                tagAdapter.setSeclectCateBean(list);
                tagAdapter.setUserList(list.getUserCategory());
                tagAdapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
            }
        });
    }

    @OnClick({R.id.bt_return, R.id.bt_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_return:
                finish();
                break;
            case R.id.bt_finish:
                if (type.equals("offer")) {
                    sb = new StringBuffer();
                    attr = new StringBuffer();
                    offerselectList.clear();
                    for (int i = 0; i < datas.size(); i++) {
                        for (int j = 0; j <datas.get(i).getZlist().size() ; j++) {
                            if (datas.get(i).getZlist().get(j).isCheck()){
                                offerselectList.add(datas.get(i).getZlist().get(j).getId());
                                offerString.add(datas.get(i).getZlist().get(j).getName());
                            }
                        }
                    }
                    for (int i = 0; i <other.size() ; i++) {
                        if (other.get(i).isCheck()){
                            offerselectList.add(other.get(i).getId());
                            offerString.add(other.get(i).getName());
                        }
                    }
                    for (int i = 0; i <offerselectList.size() ; i++) {
                     sb.append(offerString.get(i)+",");
                     attr.append(offerselectList.get(i)+"_");
                    }
                    if (TextUtils.isEmpty(attr)){
                        ToastUtils.showCentetToast(ResourceTypeActivity.this,"请先选择资源类型");
                        return;
                    }
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putIntegerArrayList(OFFERSELECTARRAY, offerselectList);
                    bundle.putString(RESOURCENAME, sb.delete(sb.length()-1,sb.length()).toString());
                    bundle.putString(RESOURCEATTR, attr.delete(attr.length()-1, attr.length()).toString());
                    intent.putExtras(bundle);
                    setResult(OFFERRESULTCODE, intent);
                    finish();
                }
                if (type.equals("need")){
                    sb = new StringBuffer();
                    attr = new StringBuffer();
                    needselectList.clear();
                    for (int i = 0; i < datas.size(); i++) {
                    for (int j = 0; j <datas.get(i).getZlist().size() ; j++) {
                        if (datas.get(i).getZlist().get(j).isCheck()){
                            needselectList.add(datas.get(i).getZlist().get(j).getId());
                            needString.add(datas.get(i).getZlist().get(j).getName());
                        }
                    }
                 }
                for (int i = 0; i <other.size() ; i++) {
                    if (other.get(i).isCheck()){
                        needselectList.add(other.get(i).getId());
                        needString.add(other.get(i).getName());
                    }
                }
                    for (int i = 0; i <needselectList.size() ; i++) {
                        sb.append(needString.get(i)+",");
                        attr.append(needselectList.get(i)+"_");
                    }
                    if (TextUtils.isEmpty(attr)){
                        ToastUtils.showCentetToast(ResourceTypeActivity.this,"请先选择资源类型");
                        return;
                    }
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putIntegerArrayList(NEEDSELECTARRAY, needselectList);
                    bundle.putString(RESOURCENAME, sb.delete(sb.length()-1,sb.length()).toString());
                    bundle.putString(RESOURCEATTR,attr.delete(attr.length()-1,attr.length()).toString());
                    intent.putExtras(bundle);
                    setResult(NEEDRESULTCODE, intent);
                    finish();
                }



                break;
            default:

                break;
        }
    }
    private void addTag(String name) {
//        RequestManager.getInstance().addTagV2(name, new AddTagCallback() {
//            @Override
//            public void onSuccess(SeclectCateBean.UserCategoryBean item) {
//                other.add(new SeclectCateBean.UserCategoryBean(item.getId(),item.getName(),true));
//                tagAdapter.setUserList(other);
//                tagAdapter.notifyDataSetChanged();
//                dispopwindow();
//            }
//
//            @Override
//            public void onFailed(int code, String msg) {
//                dispopwindow();
//                ToastUtils.showCentetImgToast(ResourceTypeActivity.this,msg);
//            }
//        });
    }
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

}
