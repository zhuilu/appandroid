package com.xinniu.android.qiqueqiao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.base.BaseActivity;
import com.xinniu.android.qiqueqiao.bean.HelpBean;
import com.xinniu.android.qiqueqiao.request.RequestManager;
import com.xinniu.android.qiqueqiao.request.callback.RequestCallback;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;

/**
 * Created by qinlei
 * Created on 2017/12/20
 * Created description :
 */

public class HelpActivity extends BaseActivity {
    @BindView(R.id.expendlist)
    ExpandableListView expendlist;
    @BindView(R.id.tb_return)
    ImageView tbReturn;
    @BindView(R.id.tb_title)
    TextView tbTitle;
    @BindView(R.id.tb_menu)
    ImageView tbMenu;
    private HelpAdapter mAdapter;
    private List<String> group_list = new ArrayList<>();
    private List<List<String>> item_list = new ArrayList<>();
    private Call mCall;


    public static void start(Context context) {
        Intent starter = new Intent(context, HelpActivity.class);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        topStatusBar(true);
        tbReturn.setImageResource(R.mipmap.chevron);
        tbReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tbTitle.setText("帮助");

        RequestManager.getInstance().getHelp(new RequestCallback<List<HelpBean>>() {
            @Override
            public void requestStart(Call call) {
                showBookingToast(1);
                mCall = call;
            }

            @Override
            public void onSuccess(List<HelpBean> helpBeen) {
                if (helpBeen != null && helpBeen.size() > 0) {
                    for (HelpBean helpBean : helpBeen) {
                        group_list.add(helpBean.getTitle());
                        ArrayList<String> item_lt = new ArrayList<>();
                        item_lt.add(helpBean.getContent());
                        item_list.add(item_lt);
                        initadapter();
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastUtils.showCentetImgToast(HelpActivity.this, msg);
            }

            @Override
            public void requestEnd() {
                dismissBookingToast();
            }
        });


    }

    private void initadapter() {
        expendlist.setGroupIndicator(null);
        // 监听组点击
        expendlist.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (item_list.get(groupPosition).isEmpty()) {
                    return true;
                }
                return false;
            }
        });
        mAdapter = new HelpAdapter(HelpActivity.this);
        expendlist.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
    }

    class HelpAdapter extends BaseExpandableListAdapter {

        private Context context;

        public HelpAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getGroupCount() {
            return group_list.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return item_list.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return group_list.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return item_list.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupHolder groupHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.help_parent_item, null);
                groupHolder = new GroupHolder();
                groupHolder.txt = (TextView) convertView.findViewById(R.id.tv);
                groupHolder.img = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }

            if (!isExpanded) {
                groupHolder.img.setBackgroundResource(R.mipmap.down_0);
            } else {
                groupHolder.img.setBackgroundResource(R.mipmap.up_0);
            }

            groupHolder.txt.setText(group_list.get(groupPosition));
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ItemHolder itemHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.help_child_item, null);
                itemHolder = new ItemHolder();
                itemHolder.txt = (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ItemHolder) convertView.getTag();
            }
            itemHolder.txt.setText(item_list.get(groupPosition).get(childPosition).replace("</br>","\n"));
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

    class GroupHolder {
        public TextView txt;

        public ImageView img;
    }

    class ItemHolder {
        public TextView txt;
    }

}
