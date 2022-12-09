package com.xinniu.android.qiqueqiao.customs.qldialog;

import android.content.Context;
//import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinniu.android.qiqueqiao.R;
import com.xinniu.android.qiqueqiao.bean.ReleaseTemplateBean;
import com.xinniu.android.qiqueqiao.customs.label.FlowLayout;
import com.xinniu.android.qiqueqiao.customs.label.TagAdapter;
import com.xinniu.android.qiqueqiao.customs.label.TagFlowLayout;
import com.xinniu.android.qiqueqiao.utils.ShowUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BDXK on 2019/3/18.
 * project : newbridge--- android xs
 */

public class ReleaseTemplateDialog extends QLDialog {
    private ReleaseTemplateBean releaseTemplateBean;
    private Context mContext;

    public void setReleaseTemplateBean(ReleaseTemplateBean releaseTemplateBean) {
        this.releaseTemplateBean = releaseTemplateBean;
    }

    public ReleaseTemplateDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    @Override
    protected void createDialog(View mView) {
        TextView tvTitle = (TextView) mView.findViewById(R.id.tv_title);
        LinearLayout coop_moffer_contentLl = (LinearLayout) mView.findViewById(R.id.coop_moffer_contentLl);
        final TagFlowLayout coop_moffer_label = (TagFlowLayout) mView.findViewById(R.id.coop_moffer_label);
        TextView coop_moffer_content = (TextView) mView.findViewById(R.id.coop_moffer_content);
        TextView moffer_titletv = (TextView) mView.findViewById(R.id.moffer_titletv);
        TextView coop_moffer_title = (TextView) mView.findViewById(R.id.coop_moffer_title);



        LinearLayout coop_mneed_contentLl = (LinearLayout) mView.findViewById(R.id.coop_mneed_contentLl);
        final TagFlowLayout coop_mneed_label = (TagFlowLayout) mView.findViewById(R.id.coop_mneed_label);
        TextView coop_mneed_content = (TextView) mView.findViewById(R.id.coop_mneed_content);
        TextView mneed_titletv = (TextView) mView.findViewById(R.id.mneed_titletv);
        TextView coop_mneed_title = (TextView) mView.findViewById(R.id.coop_mneed_title);



        ImageView bfinishImg = (ImageView) mView.findViewById(R.id.bfinishImg);
        tvTitle.setText(releaseTemplateBean.getTitle());

        bfinishImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        List<ReleaseTemplateBean.ProvideRemarkBean> provideList = new ArrayList<>();
        if (releaseTemplateBean.getProvide_tags() != null && releaseTemplateBean.getProvide_tags().size() > 0) {
            provideList.addAll(releaseTemplateBean.getProvide_tags());
        }
        List<String> provideLabel = new ArrayList<>();
        List<ReleaseTemplateBean.NeedRemarkBean> needList = new ArrayList<>();
        if (releaseTemplateBean.getNeed_tags() != null && releaseTemplateBean.getNeed_tags().size() > 0) {
            needList.addAll(releaseTemplateBean.getNeed_tags());
        }
        List<String> needLabel = new ArrayList<>();
        if (provideList.size() > 0) {
            for (int i = 0; i < provideList.size(); i++) {
                provideLabel.add(provideList.get(i).getName());
            }
            if (coop_moffer_label != null) {
                coop_moffer_label.setAdapter(new TagAdapter<String>(provideLabel) {
                    @Override
                    public View getView(FlowLayout parent, int position, String o) {
                        TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_label_tv_two, coop_moffer_label, false);
                        tv.setText(o);
                        return tv;
                    }
                });
            }
            if (!TextUtils.isEmpty(releaseTemplateBean.getProvide_category_title())) {
                ShowUtils.showTextPerfect(moffer_titletv, releaseTemplateBean.getProvide_category_title());
            } else {
                ShowUtils.showTextPerfect(moffer_titletv, "提供资源类型：");
                //  ShowUtils.showViewVisible(moffer_titletv, View.GONE);
            }

            if (!TextUtils.isEmpty(releaseTemplateBean.getProvide_description_title())) {
                ShowUtils.showTextPerfect(coop_moffer_title, releaseTemplateBean.getProvide_description_title());
            } else {
                ShowUtils.showTextPerfect(coop_moffer_title,"提供资源说明：");
            }
            if (!TextUtils.isEmpty(releaseTemplateBean.getProvide_describe())) {
                ShowUtils.showTextPerfect(coop_moffer_content, releaseTemplateBean.getProvide_describe());
            }
        }else{
            ShowUtils.showViewVisible(coop_moffer_contentLl, View.GONE);
        }


        if (needList.size() > 0) {
            for (int i = 0; i < needList.size(); i++) {
                needLabel.add(needList.get(i).getName());
            }
            coop_mneed_label.setAdapter(new TagAdapter<String>(needLabel) {
                @Override
                public View getView(FlowLayout parent, int position, String o) {
                    TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_label_tv_two, coop_mneed_label, false);
                    tv.setText(o);
                    return tv;
                }
            });

            if (!TextUtils.isEmpty(releaseTemplateBean.getNeed_describe())) {
                ShowUtils.showTextPerfect(coop_mneed_content, releaseTemplateBean.getNeed_describe());

            } else {
                ShowUtils.showViewVisible(coop_mneed_content, View.GONE);
            }
            if (!TextUtils.isEmpty(releaseTemplateBean.getNeed_category_title())) {
                ShowUtils.showTextPerfect(mneed_titletv, releaseTemplateBean.getNeed_category_title());
            } else {
//            ShowUtils.showViewVisible(mneed_titletv, View.GONE);
                ShowUtils.showTextPerfect(mneed_titletv, "需求资源类型：");
            }
            if (!TextUtils.isEmpty(releaseTemplateBean.getNeed_description_title())) {
                ShowUtils.showTextPerfect(coop_mneed_title, releaseTemplateBean.getNeed_description_title());
            } else {
//            ShowUtils.showViewVisible(coop_mneed_title, View.GONE);
                ShowUtils.showTextPerfect(coop_mneed_title,"需求资源说明：");
            }


        }else{
            ShowUtils.showViewVisible(coop_mneed_contentLl, View.GONE);
        }
    }

    public static class Builder extends QLDialog.AQLDialogBuilder<ReleaseTemplateDialog.Builder> {
        private ReleaseTemplateBean releaseTemplateBean;

        public Builder setReleaseTemplateBean(ReleaseTemplateBean releaseTemplateBean) {
            this.releaseTemplateBean = releaseTemplateBean;
            return this;
        }

        public Builder(Context context) {
            super(context);
        }

        public Builder(Context context, int theme) {
            super(context, theme);
        }

        @Override
        protected AppCompatDialog buildWithTheme(Context mContext, int mTheme) {
            return new ReleaseTemplateDialog(mContext, mTheme);
        }

        @Override
        protected void buildDialog(QLDialog dialog) {
            ReleaseTemplateDialog qlDialog = (ReleaseTemplateDialog) dialog;
            setView(qlDialog, R.layout.dialog_release_template);
            qlDialog.setReleaseTemplateBean(releaseTemplateBean);
            qlDialog.setCancelable(false);
        }
    }
}
