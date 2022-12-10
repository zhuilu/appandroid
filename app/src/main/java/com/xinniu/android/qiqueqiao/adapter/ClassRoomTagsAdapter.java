package com.xinniu.android.qiqueqiao.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.CellTagsBean;
import com.xinniu.android.qiqueqiao.bean.VideoCategoryBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yuchance on 2018/6/26.
 */

public class ClassRoomTagsAdapter extends BaseQuickAdapter<VideoCategoryBean, BaseViewHolder> {

    private List<VideoCategoryBean> datas = new ArrayList<>();
    private Context mContext;


    public ClassRoomTagsAdapter(Context context,int layoutResId, @Nullable List<VideoCategoryBean> data) {
        super(layoutResId, data);
        this.datas = data;
        this.mContext=context;

    }

    @Override
    protected void convert(final BaseViewHolder helper, final VideoCategoryBean item) {
        TextView cellTv = helper.getView(R.id.tvItemCell);
        View view_line = helper.getView(R.id.view);
        if (item != null) {
            if (!TextUtils.isEmpty(item.getName())) {
                helper.setText(R.id.tvItemCell, item.getName());
            }
            if (item.isCheck()) {
                cellTv.setSelected(true);
                view_line.setVisibility(View.VISIBLE);
                cellTv.setTextSize(13);
                TextPaint tp = cellTv.getPaint();//加粗
                tp.setFakeBoldText(true);
            } else {
                cellTv.setSelected(false);
                cellTv.setTextSize(12);
                view_line.setVisibility(View.INVISIBLE);
                TextPaint tp = cellTv.getPaint();
                tp.setFakeBoldText(false);
            }
            cellTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (item.isCheck()) {
                        //取消,默认选中全部
                        for (int i = 0; i < datas.size(); i++) {
                            datas.get(i).setCheck(false);
                            if (datas.get(i).getId() == 0) {
                                datas.get(i).setCheck(true);
                            }
                        }
                        setTags.setTags(0);
                    } else {
                        for (int i = 0; i < datas.size(); i++) {
                            datas.get(i).setCheck(false);
                        }
                        datas.get(helper.getLayoutPosition()).setCheck(true);

                        setTags.setTags(item.getId());
                    }

                    notifyDataSetChanged();
                }
            });
        }
    }

    public interface setTags {
        void setTags(int id);
    }

    private setTags setTags;

    public void setSetTags(ClassRoomTagsAdapter.setTags setTags) {
        this.setTags = setTags;
    }
}
