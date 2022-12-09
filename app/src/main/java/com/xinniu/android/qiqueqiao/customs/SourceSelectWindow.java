package com.xinniu.android.qiqueqiao.customs;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.adapter.IndexLeftSelectAdapter;
import com.xinniu.android.qiqueqiao.adapter.IndexRightSelectAdapter;
import com.xinniu.android.qiqueqiao.bean.SelectCategory;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.utils.AnimationUtil;
import com.xinniu.android.qiqueqiao.utils.DeviceUtils;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.ResouceHelper;
import com.xinniu.android.qiqueqiao.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BDXK on 2017/11/9.
 */

public class SourceSelectWindow extends PopupWindow implements PopupWindow.OnDismissListener,
        AdapterView.OnItemClickListener, View.OnClickListener {

    private Activity activity;
    private ListView rightListView,leftListView;
    private IndexLeftSelectAdapter leftSelectAdapter;
    private IndexRightSelectAdapter rightSelectAdapter;
    private View contentView,listRoot,view;
    private List<SelectCategory> leftList = new ArrayList<>();
    private List<SelectCategory> rightList = new ArrayList<>();
    private TextView clearTv;
    private TextView btn;
    private TextView offerTv;
    private TextView needTv;
    private TextView offerAndNeedTv;
    boolean isClickBtn;//是否点击确定按钮

    public SourceSelectWindow(Activity activity,List<SelectCategory> leftList){
        this.activity = activity;
        this.leftList = leftList;
        initView();
    }

    private void initView(){
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        contentView = LayoutInflater.from(activity).inflate(R.layout.source_popup_layout,null);
        leftListView = (ListView) contentView.findViewById(R.id.leftListView);
        rightListView = (ListView) contentView.findViewById(R.id.rightListView);
        listRoot =  contentView.findViewById(R.id.listRoot);
        view =  contentView.findViewById(R.id.view1);
        btn = (TextView) contentView.findViewById(R.id.tvSubmit);
        clearTv = (TextView) contentView.findViewById(R.id.clear);
        clearTv.setOnClickListener(this);
        offerTv = (TextView) contentView.findViewById(R.id.offerTv);
        offerTv.setOnClickListener(this);
        needTv  = (TextView) contentView.findViewById(R.id.needTv);
        needTv.setOnClickListener(this);
        offerAndNeedTv = (TextView) contentView.findViewById(R.id.offerAndNeedTv);
        offerAndNeedTv.setOnClickListener(this);

        leftSelectAdapter = new IndexLeftSelectAdapter(activity,leftList,R.layout.left_item);
        leftListView.setAdapter(leftSelectAdapter);
        rightSelectAdapter = new IndexRightSelectAdapter(activity,rightList,R.layout.right_item);
        rightListView.setAdapter(rightSelectAdapter);

        //如果左侧被选中则更新右边
        leftSelectAdapter.setDefaultSelect(new IndexLeftSelectAdapter.DefaultSelect() {
            @Override
            public void defaultSelect(int id) {
                if (selectLeftItem!=null){
                    selectLeftItem.leftSelectItem(id);
                }
            }
        });
        view.setOnClickListener(this);
        btn.setOnClickListener(this);
        leftListView.setOnItemClickListener(this);
        listRoot.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int) (DeviceUtils.getDisplay(activity).heightPixels*0.63)));

        this.setContentView(contentView);//设置布局
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setAnimationStyle(R.style.SelectPopupWindow);
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        this.setOnDismissListener(this);
    }

    //显示
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent);
            AnimationUtil.createAnimation(true,contentView,listRoot,null);
        } else {
            dismissPopup();
        }
    }

    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT >= 24){
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
        }
        super.showAsDropDown(anchor);

    }

    //消失
    public void dismissPopup(){
        //如果没有点击确定按钮，并且tempLeftSelectPd != leftSelectPd
        if (!isClickBtn && ResouceHelper.tempLeftSelectPd != ResouceHelper.getInstance().leftSelectPd){
            for (SelectCategory category : ResouceHelper.getInstance().getLeftCategory()){
                category.setCheck(category.getId()==ResouceHelper.leftSelectPd?true:false);
            }
        }
        isClickBtn = false;
        super.dismiss();
    }

    @Override
    public void onDismiss() {
        startAnim(false);
    }

    @Override
    public void dismiss() {
        startAnim(false);
    }

    public void refreshRight(List<SelectCategory> categoryList){
        this.rightList.clear();
        this.rightList.addAll(categoryList);
        rightSelectAdapter.setListData(this.rightList);
    }

    /**
     * 左侧点击
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //请求子菜单，并更新
        if (selectLeftItem!=null){
            selectLeftItem.leftSelectItem(leftList.get(position).getId());
            for (SelectCategory category : leftList){
                category.setCheck(false);
            }
            leftList.get(position).setCheck(true);
            leftSelectAdapter.setListData(leftList);
        }
    }

    @Override
    public void onClick(View v) {
        checkHeadStatu(v.getId());
        switch (v.getId()){
            case R.id.tvSubmit://确定按钮，更新缓存
                isClickBtn = true;
                if (ResouceHelper.tempLeftSelectPd != ResouceHelper.leftSelectPd){
                    for (SelectCategory category : ResouceHelper.getInstance().getRightListById(ResouceHelper.leftSelectPd)) {
                        for (SelectCategory item : category.getZ_index()) {
                            item.setCheck(false);
                        }
                    }
                    ResouceHelper.leftSelectPd = ResouceHelper.tempLeftSelectPd;
                }
                //获取用户选择的PID：BaseApp.leftSelectPd 和 query_id
                ArrayList<String> arrayList = new ArrayList<>();
                for (SelectCategory category : ResouceHelper.getInstance().getRightListById(ResouceHelper.leftSelectPd)){
                    for (SelectCategory item : category.getZ_index()) {
                        if (item.isCheck()){
                            arrayList.add(String.valueOf(item.getId()));
                        }
                    }
                }
                //资源类型筛选,例如 10_19_80_11(三级资源)
                String query_id = StringUtils.listToString(arrayList,"_");
                if (selectLeftItem!=null){
                    selectLeftItem.backRequestPamaters(ResouceHelper.leftSelectPd,query_id,null,null,null);
                }
                startAnim(false);
                break;
            case R.id.view1:
                startAnim(false);
                break;
            case R.id.offerAndNeedTv:
                if (selectLeftItem != null){
                    selectLeftItem.onQuerySelected(Constants.QUERY_TYPE_NEED_AND_OFFER);
                }
                break;
            case R.id.needTv:
                if (selectLeftItem != null){
                    selectLeftItem.onQuerySelected(Constants.QUERY_TYPE_NEED);
                }
                break;
            case R.id.offerTv:
                if (selectLeftItem != null){
                    selectLeftItem.onQuerySelected(Constants.QUERY_TYPE_OFFER);
                }
                break;
            case R.id.clear:
                ResouceHelper.getInstance().reSettingResouce();
                leftSelectAdapter.notifyDataSetChanged();
                rightSelectAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void checkHeadStatu(int id){
        clearTv.setSelected(false);
        offerTv.setSelected(false);
        needTv.setSelected(false);
        offerAndNeedTv.setSelected(false);
        if (id == R.id.clear){
            clearTv.setSelected(true);
        }
        if (id == R.id.offerTv){
            offerTv.setSelected(true);
        }
        if (id == R.id.needTv){
            needTv.setSelected(true);
        }
        if (id == R.id.offerAndNeedTv){
            offerAndNeedTv.setSelected(true);
        }
    }

    private void startAnim(boolean isIn){
        AnimationUtil.createAnimation(isIn, contentView, listRoot, new AnimationUtil.AnimInterface() {
            @Override
            public void animEnd() {
                dismissPopup();
            }
        });
    }

    private SelectLeftItem selectLeftItem;

    public void setSelectLeftItem(SelectLeftItem selectLeftItem){
        this.selectLeftItem = selectLeftItem;
    }

    public interface SelectLeftItem{
        void leftSelectItem(int id);
        /**
         * @param Pid  顶级id
         * @param queryIds 资源类型筛选,例如 10_19_80_11(三级资源)
         * @param sort_order 排序id
         * @param industry 公司行业
         * @param cooperation_mode 合作方式
         */
        void backRequestPamaters(Integer Pid,String queryIds,Integer sort_order,String industry,String cooperation_mode);
        void onQuerySelected(int type);
    }
}
