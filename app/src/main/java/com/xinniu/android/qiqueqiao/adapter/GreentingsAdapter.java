package com.xinniu.android.qiqueqiao.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.GreentingsBean;
import com.xinniu.android.qiqueqiao.widget.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuchance on 2018/12/12.
 */

public class GreentingsAdapter extends BaseMultiItemQuickAdapter<GreentingsBean.SystemBean, BaseViewHolder> {

    private Activity mContext;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GreentingsAdapter(Activity context, List<GreentingsBean.SystemBean> data) {
        super(data);
        this.mContext = context;
        addItemType(1, R.layout.item_grrentings);
        addItemType(2, R.layout.item_grrentings_custom);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final GreentingsBean.SystemBean item) {
        switch (helper.getItemViewType()) {
            case 1:
                CheckBox checkBox = helper.getView(R.id.check);
                TextView tvName = helper.getView(R.id.tv_name);
                RelativeLayout rlayout_root=helper.getView(R.id.rlayout_root);

                if (item.getStatus() == 1) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                tvName.setText(item.getTitle());

                rlayout_root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onEdit(helper.getAdapterPosition(), 0);

                    }
                });

                break;
            case 2:
                CheckBox checkBox1 = helper.getView(R.id.check);
                RadioButton tvName1 = helper.getView(R.id.tv_name);
                TextView tvDelete = helper.getView(R.id.tv_delete);
                SwipeMenuLayout swipe_content=helper.getView(R.id.swipe_content);
                if (item.getStatus() == 1) {
                    checkBox1.setChecked(true);
                } else {
                    checkBox1.setChecked(false);
                }

                tvName1.setText(item.getTitle());

//                checkBox1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        callback.onEdit(helper.getAdapterPosition(), 0);
//
//                    }
//                });
                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.onEdit(helper.getAdapterPosition(), 1);

                    }
                });
                swipe_content.addListener(new SwipeMenuLayout.SwipeListener() {
                    @Override
                    public void onUpdate(SwipeMenuLayout layout, float offset) {

                    }

                    @Override
                    public void onOpened(SwipeMenuLayout layout) {

                    }

                    @Override
                    public void onClosed(SwipeMenuLayout layout) {

                    }

                    @Override
                    public void onClick(SwipeMenuLayout layout) {
                        callback.onEdit(helper.getAdapterPosition(), 0);
                    }
                });



                break;
            default:
                break;
        }

    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void onEdit(int position, int type);

    }
}
