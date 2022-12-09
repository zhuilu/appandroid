package com.xinniu.android.qiqueqiao.bean;

import java.util.List;

public class ResourcesTitleBean {

    /**
     * corporate_count : 16
     * resources_title_list : [{"title":"我是说"},{"title":"我是说"},{"title":"我是说"},{"title":"讹看电视呢！这些是你给我们带来惊喜"},{"title":"我们不一样，有啥不一样，就是不一样。不一样到底是咋样。说了不"},{"title":"我知道你在听这么讲理想中你也爱我一点点"}]
     */

    private int corporate_count;
    private List<ResourcesTitleListBean> resources_title_list;

    public int getCorporate_count() {
        return corporate_count;
    }

    public void setCorporate_count(int corporate_count) {
        this.corporate_count = corporate_count;
    }

    public List<ResourcesTitleListBean> getResources_title_list() {
        return resources_title_list;
    }

    public void setResources_title_list(List<ResourcesTitleListBean> resources_title_list) {
        this.resources_title_list = resources_title_list;
    }

    public static class ResourcesTitleListBean {
        /**
         * title : 我是说
         */

        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
