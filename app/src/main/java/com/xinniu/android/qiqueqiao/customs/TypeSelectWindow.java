package com.xinniu.android.qiqueqiao.customs;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.IndexTypeSelectAdapter;
import com.xinniu.android.qiqueqiao.adapter.base.BDBaseAdapter;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.utils.AnimationUtil;
import com.xinniu.android.qiqueqiao.utils.ResouceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BDXK on 2017/11/9.
 */

public class TypeSelectWindow extends PopupWindow implements  PopupWindow.OnDismissListener,AdapterView.OnItemClickListener {

    private AppCompatActivity activity;
    private BDBaseAdapter bdBaseAdapter ;
    private View pView;
    View contentView;
    GridView gridView;
    ListView listView;
    private View animTargetView;
    private List<SelectCategory> categoryList = new ArrayList<>();
    private int type;//

    public TypeSelectWindow(AppCompatActivity activity, List<SelectCategory> categoryList, int type){
        this.activity = activity;
        this.categoryList = categoryList;
        this.type = type;
        initView();
    }

    private void initView(){
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        if (type==Constants.TYPE_SELECT_ORDER){
            contentView = LayoutInflater.from(activity).inflate(R.layout.order_popup_layout,null);
            listView = (ListView) contentView.findViewById(R.id.listView);
            bdBaseAdapter = new IndexTypeSelectAdapter(activity,categoryList,R.layout.order_select_item);
            listView.setAdapter(bdBaseAdapter);
            listView.setOnItemClickListener(this);
            animTargetView = listView;
        }else {
            contentView = LayoutInflater.from(activity).inflate(R.layout.join_popup_layout,null);
            gridView = (GridView) contentView.findViewById(R.id.gridView);
            bdBaseAdapter = new IndexTypeSelectAdapter(activity,categoryList,R.layout.select_item);
            gridView.setAdapter(bdBaseAdapter);
            gridView.setOnItemClickListener(this);
            animTargetView = gridView;
        }
        pView = contentView.findViewById(R.id.view1);
        pView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnim(false);
            }
        });
        this.setContentView(contentView);//设置布局
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setAnimationStyle(R.style.SelectPopupWindow);
        this.setOutsideTouchable(true);
        this.setFocusable(true);
    }


    //显示
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAtLocation(parent, Gravity.CENTER,0,0);
//            this.showAsDropDown(parent);
            AnimationUtil.createAnimation(true,contentView,animTargetView,null);
        } else {
            dismissPopup();
        }
    }
    public void showPopupWindowx(View parent) {
        if (!this.isShowing()) {
            if(Build.VERSION.SDK_INT == 24) {
                Rect rect = new Rect();
                parent.getGlobalVisibleRect(rect);
                int h = parent.getResources().getDisplayMetrics().heightPixels - rect.bottom;
                setHeight(h);
            }
//            int windowPos[] = ComUtils.calculatePopWindowPos(parent, contentView);
            this.showAsDropDown(parent);
            AnimationUtil.createAnimation(true,contentView,animTargetView,null);
        } else {
            dismissPopup();
        }
    }

    //消失
    public void dismissPopup(){
        super.dismiss();
    }

    @Override
    public void dismiss() {
        startAnim(false);
    }

    @Override
    public void onDismiss() {
        startAnim(false);
    }

    private void startAnim(boolean isIn){
        AnimationUtil.createAnimation(false, contentView, animTargetView, new AnimationUtil.AnimInterface() {
            @Override
            public void animEnd() {
                dismissPopup();
            }
        });
    }
    /**
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<SelectCategory> list =
                type== Constants.TYPE_SELECT_COMPANY?ResouceHelper.getInstance().getCompanyList():
                type==Constants.TYPE_SELECT_ORDER?ResouceHelper.getInstance().getOrderList():
                        ResouceHelper.getInstance().getJoinList();
        for (SelectCategory selectCategory : list){
            selectCategory.setCheck(false);
        }
        if (list!=null && list.size()!=0){
            list.get(position).setCheck(true);
        }
        if (selectLeftItem!=null){
            selectLeftItem.backRequestPamaters(
                    null,null,
                    type== Constants.TYPE_SELECT_ORDER?categoryList.get(position).getId():null,
                    type== Constants.TYPE_SELECT_COMPANY?categoryList.get(position).getId()+"":null,
                    type== Constants.TYPE_SELECT_JOIN?categoryList.get(position).getId()+"":null
            );
        }
        if (listner != null){
            listner.onItemClik(type,list.get(position));
        }
        startAnim(false);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (!this.isShowing()) {
            if (Build.VERSION.SDK_INT >= 24) {
                Rect visibleFrame = new Rect();
                anchor.getGlobalVisibleRect(visibleFrame);
                int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
                setHeight(height);
            }
            AnimationUtil.createAnimation(true, contentView, listView, null);
            super.showAsDropDown(anchor);
        }else {
            dismissPopup();
        }
    }

    private SourceSelectWindow.SelectLeftItem selectLeftItem;

    public void setSelectLeftItem(SourceSelectWindow.SelectLeftItem selectLeftItem){
        this.selectLeftItem = selectLeftItem;
    }

    public interface ItemClikListner{
        void onItemClik(int type,SelectCategory selectCategory);
    }
    ItemClikListner listner;
    public void setItemClikListner(ItemClikListner listner){
        this.listner = listner;
    }

}
