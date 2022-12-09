package com.xinniu.android.qiqueqiao.activity;

/**
 * Created by lzq on 2017/12/9.
 */


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;
import com.xinniu.android.qiqueqiao.MainActivity;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.HeaderRecyclerAndFooterWrapperAdapter;
import com.xinniu.android.qiqueqiao.adapter.SelectCityAdapter;
import com.xinniu.android.qiqueqiao.adapter.ViewHolder;
import com.xinniu.android.qiqueqiao.adapter.base.CommonAdapter;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.CityBean;
import com.xinniu.android.qiqueqiao.bean.CityHeaderBean;
import com.xinniu.android.qiqueqiao.bean.CityListBean;
import com.xinniu.android.qiqueqiao.bean.CityTopHeaderBean;
import com.xinniu.android.qiqueqiao.divider.DividerItemDecoration;
import com.xinniu.android.qiqueqiao.divider.SuspensionDecoration;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.GetCityListCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;
import com.xinniu.android.qiqueqiao.widget.IndexBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class SelectCityActivity extends BaseActivity implements View.OnClickListener,SelectCityAdapter.ChildItemClick{

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.bt_return)
    ImageView clostIv;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    @BindView(R.id.indexBar)
    IndexBar mIndexBar;

    LinearLayoutManager mManager;

    private SelectCityAdapter adapter;

    private SuspensionDecoration mDecoration;

    public final static String CITY_ID = "city_id";
    public final static String CITY_NAME = "city_name";
    public final static String FROM_TYPE = "FROM_TYPE";
    private int fromType = 0;


    private List<CityBean> mList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_city;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(false);
        fromType = getIntent().getIntExtra(FROM_TYPE,0);
        adapter = new SelectCityAdapter(this,mList);
        adapter.setChildItemClick(this);
        mManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mManager);
        mDecoration = new SuspensionDecoration(this, mList);
        mDecoration.setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics()));
        rv.addItemDecoration(mDecoration);
        rv.setAdapter(adapter);
        mIndexBar = (IndexBar) findViewById(R.id.indexBar);//IndexBar

        mIndexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager);
        showBookingToast(1);
        RequestManager.getInstance().getCityList(new GetCityListCallback() {
            @Override
            public void onSuccess(CityListBean cityListBean) {
                dismissBookingToast();
                CityBean hotBean = new CityBean();
                hotBean.tag = "热";
                if (fromType == 1){
                    CityListBean.GroupBean.ListBean listBean1 = new CityListBean.GroupBean.ListBean();
                    listBean1.setId(0);
                    listBean1.setName("全国");
                    hotBean.list.add(listBean1);
                }
                if (fromType == 2){
                    CityListBean.GroupBean.ListBean listBean1 = new CityListBean.GroupBean.ListBean();
                    listBean1.setId(0);
                    listBean1.setName("全国");
                    hotBean.list.add(listBean1);
                }
                for (int i = 0 ; i<cityListBean.getHost_city().size();i++) {
                    CityListBean.GroupBean.ListBean listBean = new CityListBean.GroupBean.ListBean();
                    listBean.setId(cityListBean.getHost_city().get(i).getId());
                    listBean.setName(cityListBean.getHost_city().get(i).getName());
                    hotBean.list.add(listBean);
                }
                mList.add(hotBean);
                for (int i =0 ;i < cityListBean.getGroup().size();i++) {
                    CityBean bean = new CityBean();
                    bean.tag = cityListBean.getGroup().get(i).getPinyin().toUpperCase();
                    bean.list = cityListBean.getGroup().get(i).getList();
                    mList.add(bean);
                }
                mDecoration.setmDatas(mList);
                if (mIndexBar!=null) {
                    mIndexBar.setmSourceDatas(mList,"city").invalidate();
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailed(int code, String msg) {
                dismissBookingToast();
            }
        });
    }

    @OnClick({R.id.bt_return})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_return){
            finish();
        }
    }

    @Override
    public void itemClik(CityListBean.GroupBean.ListBean bean) {
        if (fromType == 1) {
            Intent intent = new Intent();
            intent.putExtra(CITY_ID, bean.getId());
            intent.putExtra(CITY_NAME, bean.getName());
            setResult(MainActivity.SELECTED_REQUEST_CODE, intent);
            finish();
        }else if (fromType == 2){
            Intent intent = new Intent();
            intent.putExtra(CITY_ID, bean.getId());
            intent.putExtra(CITY_NAME, bean.getName());
            setResult(1020, intent);
            finish();
        }else {
            Intent intent = new Intent();
            intent.putExtra(CITY_ID, bean.getId());
            intent.putExtra(CITY_NAME, bean.getName());
            setResult(MainActivity.SELECTED_REQUEST_CODE, intent);
            finish();
        }

    }

    public class CityBean {
        public String tag;
        public List<CityListBean.GroupBean.ListBean> list = new ArrayList<>();
    }
}

