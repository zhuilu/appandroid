package com.xinniu.android.qiqueqiao.bean;

import java.util.ArrayList;
import java.util.List;

public class ClassRoomDetailBean {


    /**
     * id : 17
     * title : 砍死你看是你打瞌睡能打开看是你打瞌睡能打开是你的扩散到你酷暑难当看酷暑难当砍死你砍死你达康书记砍死你但是库
     * category : [{"name":"视频分类12"},{"name":"视频分类2"}]
     * introduce : 开始放假偶时间覅时间的佛
     * poster : http://timg.qiqueqiao.com/activity_category/2019/04-01/5ca2041263d9e.png
     * thumb_img : http://timg.qiqueqiao.com/video/1554122037/thumb/5ca204187dd55_thumb.png,http://timg.qiqueqiao.com/video/1554122037/thumb/5ca204189389c_thumb.png,http://timg.qiqueqiao.com/video/1554122037/thumb/5ca20418b2a9a_thumb.png,http://timg.qiqueqiao.com/video/1554122037/thumb/5ca20418e3281_thumb.png
     * images : http://timg.qiqueqiao.com/images/2019/04-01/5ca204187dd55.png,http://timg.qiqueqiao.com/images/2019/04-01/5ca204189389c.png,http://timg.qiqueqiao.com/images/2019/04-01/5ca20418b2a9a.png,http://timg.qiqueqiao.com/images/2019/04-01/5ca20418e3281.png
     * upvote_num : 10
     * create_time : 1554120776
     * video_id : c4585ec39c764dccb2fcffee95b833d1
     * user_id : 1376
     * head_pic : https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132
     * realname : 曾杨
     * position : 经理
     * company : 百分点
     * character_introduce : 人物标题人物标题人物标题
     * resource_id : 1112,4242
     * resources_title : 人物标题人物标题
     * image_title : 人物标题人物标题
     * introduce_title : 人物标题人物标题
     * character_title : 人物标题
     * resources_list : [{"head_pic":"https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132","user_id":1376,"realname":"曾杨","position":"经理","brand":"百分点","is_vip":2,"is_corporate_vip":1,"title":"暂时填充22222","view":325,"create_time":1547189155,"r_id":1112,"comment_count":0},{"head_pic":null,"user_id":null,"realname":null,"position":null,"brand":null,"is_vip":null,"is_corporate_vip":0,"title":"暂时填充","view":219,"create_time":1517881887,"r_id":4242,"comment_count":0}]
     * is_upvote : 1
     * comment_count : 45
     * share_url : https://t.qiqueqiao.com/resource/pages/didccp/didccp.html?id=17
     */

    private int id;
    private String title;
    private String introduce;
    private String poster;
    private String thumb_img;
    private String images;
    private int upvote_num;
    private long create_time;
    private String video_id;
    private Integer user_id;
    private String head_pic;
    private String realname;
    private String position;
    private String company;
    private String character_introduce;
    private String resource_id;
    private String resources_title;
    private String image_title;
    private String introduce_title;
    private String character_title;
    private int is_upvote;
    private int comment_count;
    private String share_url;
    private List<CategoryBean> category;
    private List<ResourcesListBean> resources_list;
    private String details;
    private int type;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getThumb_img() {
        return thumb_img;
    }

    public void setThumb_img(String thumb_img) {
        this.thumb_img = thumb_img;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getUpvote_num() {
        return upvote_num;
    }

    public void setUpvote_num(int upvote_num) {
        this.upvote_num = upvote_num;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCharacter_introduce() {
        return character_introduce;
    }

    public void setCharacter_introduce(String character_introduce) {
        this.character_introduce = character_introduce;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getResources_title() {
        return resources_title;
    }

    public void setResources_title(String resources_title) {
        this.resources_title = resources_title;
    }

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String image_title) {
        this.image_title = image_title;
    }

    public String getIntroduce_title() {
        return introduce_title;
    }

    public void setIntroduce_title(String introduce_title) {
        this.introduce_title = introduce_title;
    }

    public String getCharacter_title() {
        return character_title;
    }

    public void setCharacter_title(String character_title) {
        this.character_title = character_title;
    }

    public int getIs_upvote() {
        return is_upvote;
    }

    public void setIs_upvote(int is_upvote) {
        this.is_upvote = is_upvote;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

    public List<ResourcesListBean> getResources_list() {
        return resources_list;
    }

    public void setResources_list(List<ResourcesListBean> resources_list) {
        this.resources_list = resources_list;
    }

    public static class CategoryBean {
        /**
         * name : 视频分类12
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ResourcesListBean {
        /**
         * id:1
         * head_pic : https://wx.qlogo.cn/mmopen/vi_32/1pSuO8JYUGaSYo7QgOn4ZSf8SstThXJLmd3GQRcSJxeKvQN3JmaiczomUHb6cynKOnS0d9NTKS6ZPCibXU3libgsw/132
         * user_id : 1376
         * realname : 曾杨
         * position : 经理
         * brand : 百分点
         * is_vip : 2
         * is_corporate_vip : 1
         * title : 暂时填充22222
         * view : 325
         * create_time : 1547189155
         * r_id : 1112
         * comment_count : 0
         * company:"cccccc"
         */

        private String head_pic;
        private int user_id;
        private String realname;
        private String position;
        private String brand;
        private int is_vip;
        private int is_corporate_vip;
        private String title;
        private int view;
        private int create_time;
        private int r_id;
        private int comment_count;
        private int id;
        private String company;
        private String p_name;
        private List<CategoryListBean> category_list = new ArrayList<>();

        public String getP_name() {
            return p_name;
        }

        public void setP_name(String p_name) {
            this.p_name = p_name;
        }

        public List<CategoryListBean> getCategory_list() {
            return category_list;
        }

        public void setCategory_list(List<CategoryListBean> category_list) {
            this.category_list = category_list;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public int getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(int is_vip) {
            this.is_vip = is_vip;
        }

        public int getIs_corporate_vip() {
            return is_corporate_vip;
        }

        public void setIs_corporate_vip(int is_corporate_vip) {
            this.is_corporate_vip = is_corporate_vip;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getView() {
            return view;
        }

        public void setView(int view) {
            this.view = view;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getR_id() {
            return r_id;
        }

        public void setR_id(int r_id) {
            this.r_id = r_id;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }
        public static class CategoryListBean {
            /**
             * id : 2
             * title : 需求标签1
             * list : [{"id":11981,"name":"线下销售渠道","is_custom":0,"is_type":0,"user_id":0,"sort_order":1,"title_id":2}]
             */

            private int id;
            private String title;
            private List<IndexNewBean.ListBean.CategoryListBean.ListBeanX> list;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<IndexNewBean.ListBean.CategoryListBean.ListBeanX> getList() {
                return list;
            }

            public void setList(List<IndexNewBean.ListBean.CategoryListBean.ListBeanX> list) {
                this.list = list;
            }

            public static class ListBeanX {
                /**
                 * id : 11981
                 * name : 线下销售渠道
                 * is_custom : 0
                 * is_type : 0
                 * user_id : 0
                 * sort_order : 1
                 * title_id : 2
                 */

                private int id;
                private String name;
                private int is_custom;
                private int is_type;
                private int user_id;
                private int sort_order;
                private int title_id;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getIs_custom() {
                    return is_custom;
                }

                public void setIs_custom(int is_custom) {
                    this.is_custom = is_custom;
                }

                public int getIs_type() {
                    return is_type;
                }

                public void setIs_type(int is_type) {
                    this.is_type = is_type;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public int getSort_order() {
                    return sort_order;
                }

                public void setSort_order(int sort_order) {
                    this.sort_order = sort_order;
                }

                public int getTitle_id() {
                    return title_id;
                }

                public void setTitle_id(int title_id) {
                    this.title_id = title_id;
                }
            }
        }

    }
}
