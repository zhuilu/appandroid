package com.xinniu.android.qiqueqiao.request;

import com.xinniu.android.qiqueqiao.bean.*;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * // 戴超   18806171210  / 123456
 * 荣繁 17767218864 123456
 * 保障 13736996320
 * 曾杨 18605811452
 */

public interface ApiService {

    String version = "v1";
    String equipment = "Android";

    @FormUrlEncoded
    @POST("/api/" + version + "/resource/getCategoryByPid")
    Call<ResultDO<List<SelectCategory>>> getCategoryByPid(@Field("parent_id") Integer parentId);

    @FormUrlEncoded
    @POST("/api/" + version + "/resource/getCategoryByPid")
    Call<ResultDO<List<SelectCategory>>> getCategoryByPidForCompany(@Field("corporate_id") int corporate_id);

    @FormUrlEncoded
    @POST("/api/" + version + "/resource/getScreen")
        //分类 1合作方式 2公司行业 3职位4圈子类型
    Call<ResultDO<List<SelectCategory>>> getScreen(@Field("category") Integer category);


    @FormUrlEncoded
    @POST("/api/v6/resource/getResource")
    Call<ResultDO<IndexNewBean>> getNewResource(
            @Query("user_id") int userId,
            @Header("token") String token,
            @Field("page") int page,
            @Field("city") Integer city,
            @Field("keyword") String keyword,
            @Field("sort_order") Integer sort_order,
            @Field("query_id") String query_id,//资源类型筛选,例如 10_19_80_11(三级资源)
            @Field("industry") String industry//筛选的公司行业 1,2
    );


    /**
     * 首页资源列表
     *
     * @param page
     * @param keyword
     * @param sort_order
     * @param query_id
     * @return
     */
    @FormUrlEncoded
    @POST("/api/v6/resource/getResource")
    Call<ResultDO<IndexNewBean>> getResourceByCorporateId(
            @Field("page") int page,
            @Field("city") Integer city,
            @Field("keyword") String keyword,
            @Field("sort_order") Integer sort_order,
            @Field("query_id") String query_id,//资源类型筛选,例如 10_19_80_11(三级资源)
            @Field("industry") String industry,//筛选的公司行业 1,2
            @Field("corporate_id") int corporate_id);

    /**
     * 首页资源列表
     *
     * @param page
     * @param keyword
     * @param sort_order
     * @param query_id
     * @return
     */
    @FormUrlEncoded
    @POST("/api/v6/resource/getResource")
    Call<ResultDO<IndexNewBean>> getResourceByUserId(
            @Query("user_id") int userId,
            @Header("token") String token,
            @Field("page") int page,
            @Field("city") Integer city,
            @Field("keyword") String keyword,
            @Field("keyword_range") String keyword_range,
            @Field("sort_order") Integer sort_order,
            @Field("query_id") String query_id,//资源类型筛选,例如 10_19_80_11(三级资源)
            @Field("industry") String industry,//筛选的公司行业 1,2
            @Field("p_id") int p_id,
            @Field("from") int from,
            @Field("is_transaction") int is_transaction,
            @Field("create_time") int create_time //上一页最后一条的时间
    );



    @FormUrlEncoded
    @POST("/api/v2/login")
    Call<ResultDO<UserInfoBean>> loginByPhone(@Field("mobile") String mobile,
                                              @Field("password") String password,
                                              @Field("equipment") String equipment,
                                              @Field("version") String v1);

    @FormUrlEncoded
    @POST("/api/v3/user/codeLogin")
    Call<ResultDO<UserInfoBean>> loginByYzm(@Field("mobile") String mobile,
                                            @Field("code") String code,
                                            @Field("make") int make,
                                            @Field("lon") double lon,
                                            @Field("lat") double lat
    );


    @FormUrlEncoded
    @POST("/api/" + version + "/thirdLogin")
    Call<ResultDO<UserInfoBean>> loginByThird(@Field("make") int make,
                                              @Field("openid") String openid,
                                              @Field("equipment") String equipment);


    @FormUrlEncoded
    @POST("/api/v3/user/thirdLogin")
    Call<ResultDO<UserInfoBean>> loginByThirdV2(@Field("make") int make,
                                                @Field("openid") String openid,
                                                @Field("unionid") String union_id);


    @FormUrlEncoded
    @POST("/api/" + version + "/register")
    Call<ResultDO<RegisterBean>> register(@Field("mobile") String mobile,
                                          @Field("password") String password,
                                          @Field("code") String code,
                                          @Field("openid") String openid,
                                          @Field("make") int make,
                                          @Field("equipment") String equipment,
                                          @Field("nickname") String nickname,
                                          @Field("head_pic") String sex);

    @FormUrlEncoded
    @POST("/api/v2/user/register")
    Call<ResultDO<RegisterBean>> registerV2(@Field("mobile") String mobile,
                                            @Field("code") String code,
                                            @Field("make") int make,
                                            @Field("nickname") String nickname,
                                            @Field("openid") String openid,
                                            @Field("unionid") String unionid,
                                            @Field("head_pic") String head_pic,
                                            @Field("sex") int sex,
                                            @Field("lon") double lon,
                                            @Field("lat") double lat);


    @FormUrlEncoded
    @POST("/api/v2/sendCode")
    Call<YzmBean> getYzm(@Field("mobile") String mobile,
                         @Field("type") int type,
                         @Field("sign") String sign,
                         @Field("platform") int platform
    );

    @FormUrlEncoded
    @POST("/api/" + version + "/forgot")
    Call<ResultDO> reSettingPwd(@Field("mobile") String mobile,
                                @Field("code") String code,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/v2/resource/getSeenUser")
    Call<ResultDO<SeenBean>> getSeenUser(@Field("page") int page,
                                         @Field("user_id") int userId,
                                         @Header("token") String token);


    @GET("/api/" + version + "/getAppArea")
    Call<ResultDO<CityListBean>> getCityList();

    @GET("/api/v3/resource/getResourceInfo")
    Call<ResultDO<ResouceInfoBean>> getResourceInfo(@Query("id") int groupId,
                                                    @Query("user_id") int userId,
                                                    @Header("token") String token);


    @GET("/api/v4/resource/getResourceInfo")
    Call<ResultDO<CoopDetailBean>> getCoopDetail(@Query("id") int groupId,
                                                 @Query("user_id") int userId,
                                                 @Header("token") String token,
                                                 @Query("page") int page);

    @GET("/api/v1/ServiceProvider/getServiceDetails")
    Call<ResultDO<ServiceDetailBean>> getServiceDetails(@Query("id") int id,
                                                        @Query("user_id") int userId);

    /**
     * 退出圈子
     */
    @FormUrlEncoded
    @POST("/api/" + version + "/circle/quitCircle")
    Call<ResultDO> quitCircle(@Header("token") String token,
                              @Field("user_id") int userId,
                              @Field("circle_id") int circleId);

    /**
     * 获取圈子列表
     */
    @FormUrlEncoded
    @POST("/api/v2/circle/getCircleList")
    Call<ResultDO<List<CircleBean>>> getCircleList(@Header("token") String token,
                                                   @Field("user_id") int userId);

    /**
     * 获取圈子列表
     */
    @GET("/api/v2/circle/getCircleList")
    Call<ResultDO<List<CircleBean>>> getCircleList();

    /**
     * 获取我的圈子列表
     */
    @FormUrlEncoded
    @POST("/api/" + version + "/circle/getMyCircleList")
    Call<ResultDO<List<MyCircleBean>>> getMyCircleList(@Header("token") String token,
                                                       @Field("user_id") int userId);

    /**
     * 加入圈子
     */
    @FormUrlEncoded
    @POST("/api/v2/circle/joinCircle")
    Call<ResultDO<Object>> joinCircle(@Header("token") String token,
                                      @Field("user_id") int userId,
                                      @Field("circle_id") int circleId);

    /**
     * 用户是否在圈子中
     */
    @FormUrlEncoded
    @POST("/api/" + version + "/circle/userBelongCircle")
    Call<ResultDO<Object>> userBelongCircle(@Header("token") String token,
                                            @Field("user_id") int userId,
                                            @Field("circle_id") int circleId);

    /**
     * 修改用户信息
     */
    @FormUrlEncoded
    @POST("/api/v2/user/edit")
    Call<ResultDO> editUserInfo(@Header("token") String token,
                                @Field("user_id") int userId,
                                @Field("type") Integer type,
                                @Field("head_pic") String headPic,
                                @Field("realname") String realname,
                                @Field("company") String company,
                                @Field("position") String position,
                                @Field("mobile") String mobile,
                                @Field("sex") int sex,
                                @Field("wechat") String wechat,
                                @Field("company_industry") int company_industry,
                                @Field("city") int city,
                                @Field("corporate_name") String corporate_name,
                                @Field("thumb_img") String thumb_img);

    /**
     * 修改用户信息
     */
    @FormUrlEncoded
    @POST("/api/v2/user/edit")
    Call<ResultDO> editUserInfoV2(@Header("token") String token,
                                  @Field("user_id") int userId,
                                  @Field("type") Integer type,
                                  @Field("head_pic") String headPic,
                                  @Field("realname") String realname,
                                  @Field("corporate_name") String company,
                                  @Field("position") String position,
                                  @Field("thumb_img") String thumb_img);


    @FormUrlEncoded
    @POST("/api/v2/user_follow")
    Call<ResultDO<CareBean>> getCareList(@Field("page") int page,
                                         @Field("user_id") int userId,
                                         @Header("token") String token);

    @FormUrlEncoded
    @POST("/api/" + version + "/resource/exchange")
    Call<ResultDO> exchangeChatInfo(@Field("info") int info,
                                    @Field("user_id") int userId,
                                    @Field("to_user_id") int toUserId,
                                    @Header("token") String token);

    @FormUrlEncoded
    @POST("/api/" + version + "/resource/talkToUser")
    Call<ResultDO<TalkToUserBean>> talkToUser(@Field("resource_id") int resourceId,
                                              @Field("user_id") int userId,
                                              @Field("to_user_id") int toUserId,
                                              @Header("token") String token);

    @FormUrlEncoded
    @POST("/api/v2/resource/talkToUser")
    Call<ResultDO<TalkToUserBean>> talkToUserV2(@Field("resource_id") int resourceId,
                                                @Field("user_id") int userId,
                                                @Field("to_user_id") int toUserId,
                                                @Header("token") String token,
                                                @Field("from") int from);


    @FormUrlEncoded
    @POST("/api/v1/ServiceProvider/talkToUser")
    Call<ResultDO<TalkToUserBean>> serviceTalkToUserV2(@Field("service_id") int service_id,
                                                       @Field("user_id") int userId,
                                                       @Field("to_user_id") int toUserId,
                                                       @Header("token") String token,
                                                       @Field("from") int from);

    @FormUrlEncoded
    @POST("/api/v2/resource/talkToUser")
    Call<ResultDO<TalkToUserBean>> talkToUserNoResourceId(
            @Field("user_id") int userId,
            @Field("to_user_id") int toUserId,
            @Header("token") String token,
            @Field("from") int from
    );

    /**
     * 统一下单
     * https://api.mch.weixin.qq.com/pay/unifiedorder
     */
    @POST("https://api.mch.weixin.qq.com/payAli/unifiedorder")
    Call<ResponseBody> unifiedorder(@Body RequestBody body);


    @FormUrlEncoded
    @POST("/api/v2/vip")
    Call<ResultDO<VipListBean>> getVipList(@Header("token") String token,
                                           @Field("user_id") int userId,
                                           @Field("type") int type);

    @FormUrlEncoded
    @POST("/api/" + version + "/resource/confirmExchange")
    Call<ResultDO> confirmExchange(@Header("token") String token,
                                   @Field("user_id") int userId,
                                   @Field("record_id") int recordId,
                                   @Field("action") int action);


    /**
     * 个人资料显示
     */
    @FormUrlEncoded
    @POST("/api/" + version + "/getUserInfo")
    Call<ResultDO<DetailedUserInfoBean>> getUserInfo(@Header("token") String token,
                                                     @Field("user_id") int userId);

    /**
     * 密码修改
     */
    @FormUrlEncoded
    @POST("/api/" + version + "/pwd")
    Call<ResultDO<Object>> pwd(@Header("token") String token,
                               @Field("user_id") int userId,
                               @Field("old_pwd") String oldPwd,
                               @Field("new_pwd") String newPwd);

    /**
     * 个人资料是否完善
     */
    @FormUrlEncoded
    @POST("/api/v2/perfect")
    Call<ResultDO> perfect(@Header("token") String token,
                           @Field("user_id") int userId);

    /**
     * 个人资料是否完善
     */
    @FormUrlEncoded
    @POST("/api/v3/perfect")
    Call<ResultDO> perfectV3(@Header("token") String token,
                             @Field("user_id") int userId);

    /**
     * 发布服务前资料是否完善
     */
    @FormUrlEncoded
    @POST("/api/v1/ServiceProvider/sendCheck")
    Call<ResultDO> sendCheck(@Header("token") String token,
                             @Field("user_id") int userId);

    @FormUrlEncoded
    @POST("/api/" + version + "/vip_buy")
    Call<ResultDO<OrderInfoBean>> bugVip(@Header("token") String token,
                                         @Field("user_id") int userId,
                                         @Field("id") int id,
                                         @Field("plat_form") int plat_form,
                                         @Field("mode") int modo);

    @FormUrlEncoded
    @POST("/api/v3/vip_buy")
    Call<ResultDO<OrderInfoBean>> bugBipV3(@Header("token") String token,
                                           @Field("user_id") int userId,
                                           @Field("id") int id,
                                           @Field("plat_form") int platForm
    );


    @FormUrlEncoded
    @POST("/api/" + version + "/resource/sendResource")
    Call<ResultDO<ResourceReleaseBean>> sendResource(@Header("token") String token,
                                                     @Field("user_id") int userId,
                                                     @Field("provide_top") int provide_top,
                                                     @Field("provide_attr") String provide_attr,
                                                     @Field("provide_remark") String provide_remark,
                                                     @Field("need_top") int need_top,
                                                     @Field("need_attr") String need_attr,
                                                     @Field("need_remark") String need_remark,
                                                     @Field("cooperation_mode") String cooperation_mode);

    @FormUrlEncoded
    @POST("/api/v2/resource/sendResource")
    Call<ResultDO<ResourceReleaseBean>> sendResourceV2(@Header("token") String token,
                                                       @Field("user_id") int userId,
                                                       @Field("provide_top") int provide_top,
                                                       @Field("provide_attr") String provide_attr,
                                                       @Field("provide_remark") String provide_remark,
                                                       @Field("provide_describe") String provide_describe,
                                                       @Field("provide_img") String provide_img,
                                                       @Field("need_top") int need_top,
                                                       @Field("need_attr") String need_attr,
                                                       @Field("need_remark") String need_remark,
                                                       @Field("need_describe") String need_describe,
                                                       @Field("need_img") String need_img,
                                                       @Field("cooperation_mode") String cooperation_mode);

    @FormUrlEncoded
    @POST("/api/" + version + "/resource/sendResource")
    Call<ResultDO<ResourceReleaseBean>> sendResourceWithDes(@Header("token") String token,
                                                            @Field("user_id") int userId,
                                                            @Field("provide_top") int provide_top,
                                                            @Field("provide_attr") String provide_attr,
                                                            @Field("provide_remark") String provide_remark,
                                                            @Field("need_top") int need_top,
                                                            @Field("need_attr") String need_attr,
                                                            @Field("need_remark") String need_remark,
                                                            @Field("cooperation_mode") String cooperation_mode,
                                                            @Field("provide_describe") String provide_describe,
                                                            @Field("need_describe") String need_describe);

    @FormUrlEncoded
    @POST("/api/" + version + "/resource/userFollow")
    Call<ResultDO<UserFollowBean>> userFollow(@Header("token") String token,
                                              @Field("user_id") int userId,
                                              @Field("target_id") int target_id,
                                              @Field("action") int action);

    @FormUrlEncoded
    @POST("/api/" + version + "/show")
    Call<ResultDO<OtherUserInfo>> showUserInfo(@Field("user_id") int userId,
                                               @Field("target_id") int target_id);

    /**
     * 用户黑名单操作
     */
    @FormUrlEncoded
    @POST("/api/" + version + "/user/blacklistBehavior")
    Call<ResultDO> blacklistBehavior(@Header("token") String token,
                                     @Field("user_id") int userId,
                                     @Field("black_user_id") int black_user_id,//	被操作用户id
                                     @Field("action") int action);//1加入黑名单 2移除黑名单

    /**
     * 图片上传
     */
    @FormUrlEncoded
    @POST("/api/" + version + "/upload")
    Call<ResultDO<UploadBean>> uploadResouceImg(@Header("token") String token,
                                                @Field("user_id") int userId,
                                                @Field("type") String type,
                                                @Field("head_pic") String head_pic);

    /**
     * 图片上传
     */
    @FormUrlEncoded
    @POST("/api/" + version + "/upload")
    Call<ResultDO<UploadBean>> upload(@Header("token") String token,
                                      @Field("user_id") int userId,
                                      @Field("head_pic") String head_pic);

    /**
     * 设置手机号
     */
    @FormUrlEncoded
    @POST("/api/" + version + "/cms_bind")
    Call<ResultDO<Object>> cmsBind(@Header("token") String token,
                                   @Field("user_id") int userId,
                                   @Field("mobile") String mobile,
                                   @Field("code") String code);

    /**
     * 公司行业列表
     */
    @POST("/api/" + version + "/industry_select")
    Call<ResultDO<Object>> industrySelect();

    /**
     * 上传名片
     */
    @FormUrlEncoded
    @POST("/api/" + version + "/card")
    Call<ResultDO<Object>> card(@Header("token") String token,
                                @Field("user_id") int userId,
                                @Field("card") String card);

//    /**
//     * 我的会员
//     */
//    @FormUrlEncoded
//    @POST("/api/" + version + "/vip")
//    Call<ResultDO<Object>> vip(@Header("token") String token,
//                               @Field("user_id") int userId,
//                               @Field("plat_form") int plat_form);//	平台标识1:iso 2:android

    /**
     * 个人中心
     */
    @FormUrlEncoded
    @POST("/api/v4/center")
    Call<ResultDO<CenterBean>> center(@Header("token") String token,
                                      @Field("user_id") int userId);


    /**
     * 意见反馈
     */
    @FormUrlEncoded
    @POST("/api/v1/feedBack")
    Call<ResultDO<Object>> feedBack(@Field("user_id") int user_id,
                                    @Field("text") String text,
                                    @Field("images") String images,
                                    @Field("thumb_img") String thumb_img,
                                    @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("/api/" + "v3" + "/resource/getUserResources")
    Call<ResultDO<GetUserResourcesBean>> getUserResources(@Header("token") String token,
                                                          @Field("uid") int uid,
                                                          @Field("user_id") int userId,
                                                          @Field("page") int page,
                                                          @Field("from") int from);

    /**
     * 获取我的发布
     */
    @FormUrlEncoded
    @POST("/api/v4/resource/getUserResources")
    Call<ResultDO<MyPushBean>> getMyPush(@Header("token") String token,
                                         @Field("uid") int uid,
                                         @Field("user_id") int userId,
                                         @Field("page") int page,
                                         @Field("from") int from);

    /**
     * 下架我的发布
     */
    @FormUrlEncoded
    @POST("/api/" + version + "/resource/delResource")
    Call<ResultDO<Object>> delResource(@Header("token") String token,
                                       @Field("user_id") int userId,
                                       @Field("id") int id);


    /**
     * 下架我的发布
     */
    @FormUrlEncoded
    @POST("/api/v1/ServiceProvider/operate")
    Call<ResultDO<Object>> operateService(@Header("token") String token,
                                          @Field("user_id") int userId,
                                          @Field("operate_type") int operate_type,//1：上架，2：下架，3：删除
                                          @Field("service_id") int service_id);

    /**
     * 删除我的发布
     */
    @FormUrlEncoded
    @POST("/api/v2/resource/delResource")
    Call<ResultDO<Object>> deleteV2Resource(@Header("token") String token,
                                            @Field("user_id") int userId,
                                            @Field("id") int id);


    /**
     * 获取我的服务
     */
    @FormUrlEncoded
    @POST("/api/v1/ServiceProvider/myServiceList")
    Call<ResultDO<MyServicePushBean>> getMyServiceList(@Header("token") String token,
                                                       @Field("user_id") int userId,
                                                       @Field("page") int page);


    /**
     * 我的会员
     */
    @FormUrlEncoded
    @POST("/api/v2/member")
    Call<ResultDO<MemberCenterBean.VipBean>> member(@Header("token") String token,
                                                    @Field("user_id") int userId,
                                                    @Field("plat_form") int type);

    /**
     * 我的会员
     */
    @FormUrlEncoded
    @POST("/api/" + version + "/up_grade")
    Call<ResultDO<VipListBean>> upGrade(@Header("token") String token,
                                        @Field("user_id") int userId,
                                        @Field("type") int type);

    /**
     * 帮助
     */
    @GET("/api/" + version + "/getHelp")
    Call<ResultDO<List<HelpBean>>> getHelp();

    /**
     * 资源修改获取详情
     */
    @FormUrlEncoded
    @POST("/api/" + version + "/resource/getEditResourceInfo")
    Call<ResultDO<EditResourceBean>> getEditResourceInfo(@Field("id") int resId);

    /**
     * 资源修改获取详情接口
     */
    @FormUrlEncoded
    @POST("/api/v2/resource/getEditResourceInfo")
    Call<ResultDO<RePublishBean>> rePublishNew(@Field("id") int resId,
                                               @Field("user_id") int userId
    );


    /**
     * 修改资源
     */
    @FormUrlEncoded
    @POST("/api/" + version + "/resource/editResource")
    Call<ResultDO<Object>> editResource(@Header("token") String token,
                                        @Field("user_id") int userId,
                                        @Field("id") int resId,
                                        @Field("provide_top") int provide_top,
                                        @Field("provide_attr") String provide_attr,
                                        @Field("provide_remark") String provide_remark,
                                        @Field("need_top") int need_top,
                                        @Field("need_attr") String need_attr,
                                        @Field("need_remark") String need_remark,
                                        @Field("cooperation_mode") String cooperation_mode,
                                        @Field("provide_describe") String provide_describe,
                                        @Field("need_describe") String need_describe,
                                        @Field("provide_img") String provide_img,
                                        @Field("need_img") String need_img);

    /**
     * 修改资源接口
     */
    @FormUrlEncoded
    @POST("/api/v3/resource/editResource")
    Call<ResultDO<Object>> editResourceV3(@Header("token") String token,
                                          @Field("user_id") int user_id,
                                          @Field("id") int resId,
                                          @Field("title") String title,
                                          @Field("provide_top") int provide_top,
                                          @Field("provide_attr") String provide_attr,
                                          @Field("provide_remark") String provide_remark,
                                          @Field("need_top") int need_top,
                                          @Field("need_attr") String need_attr,
                                          @Field("need_remark") String need_remark,
                                          @Field("cooperation_mode") String cooperation_mode,
                                          @Field("provide_describe") String provide_describe,
                                          @Field("need_describe") String need_describe,
                                          @Field("images") String images
    );


    @FormUrlEncoded
    @POST("/api/v3/pay")
    Call<ResultDO<String>> payAli(@Header("token") String token,
                                  @Field("user_id") int userId,
                                  @Field("order_sn") String order_sn,
                                  @Field("type") String type);

    @FormUrlEncoded
    @POST("/api/v3/pay")
    Call<ResultDO<WeChatPayInfo>> wechatPay(@Header("token") String token,
                                            @Field("user_id") int userId,
                                            @Field("order_sn") String order_sn,
                                            @Field("type") String type);

    @FormUrlEncoded
    @POST("/api/" + version + "/receive")
    Call<ResultDO> receive(@Header("token") String token,
                           @Field("user_id") int userId,
                           @Field("plat_form") int plat_form,
                           @Field("id") int id);

    @FormUrlEncoded
    @POST("/api/" + version + "/bind")
    Call<ResultDO<Object>> bind(@Header("token") String token,
                                @Field("user_id") int userId,
                                @Field("bind") int bind,
                                @Field("make") int make,
                                @Field("openid") String openid);

    @FormUrlEncoded
    @POST("/api/" + version + "/list")
    Call<ResultDO<List<SystemMsgBean>>> getMessageList(@Header("token") String token,
                                                       @Field("user_id") int userId,
                                                       @Field("page") int page);

    @FormUrlEncoded
    @POST("/api/" + version + "/news")
    Call<ResultDO<NewsBean>> getNews(@Header("token") String token,
                                     @Field("user_id") int userId);


    @FormUrlEncoded
    @POST("/api/" + version + "/resource/shield")
    Call<ResultDO> resourceShield(@Header("token") String token,
                                  @Field("user_id") int userId,
                                  @Field("resource_id") int resource_id,
                                  @Field("enum_id") int enum_id);

    @GET("/api/" + version + "/getAppVersion")
    Call<ResultDO<AppVertion>> getAppVersion(@Query("app_type") String appType,
                                             @Query("version") String versionName);

    @FormUrlEncoded
    @POST("/api/" + version + "/resource/addCategory")
    Call<ResultDO<SelectCategory>> addCategory(
            @Header("token") String token,
            @Field("user_id") int userId,
            @Field("parent_id_1") int parent_id_1,
            @Field("parent_id_2") int parent_id_2,
            @Field("name") String name);

    @FormUrlEncoded
    @POST("/api/v2/resource/addCategory")
    Call<ResultDO<SeclectCateBean.UserCategoryBean>> addCategoryV2(@Header("token") String token,
                                                                   @Field("user_id") int user_id,
                                                                   @Field("name") String name,
                                                                   @Field("p_id") int p_id,
                                                                   @Field("is_type") int is_type);


    @FormUrlEncoded
    @POST("/api/" + version + "/resource/getUserCategory")
    Call<ResultDO<List<SelectCategory>>> getUserCategory(
            @Header("token") String token,
            @Field("user_id") int userId,
            @Field("parent_id_1") int parent_id_1,
            @Field("parent_id_2") int parent_id_2
    );

    @FormUrlEncoded
    @POST("/api/v2/resource/getCategoryList")
    Call<ResultDO<List<SelectCategory>>> getUserCategoryV2(
            @Header("token") String token,
            @Field("user_id") int user_id
    );


    @FormUrlEncoded
    @POST("/api/" + version + "/resource/delCategory")
    Call<ResultDO> deleteCategory(
            @Header("token") String token,
            @Field("user_id") int userId,
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("/api/" + version + "/resource/analysis")
    Call<ResultDO<AnalysisBean>> analysis(
            @Field("provide_top") int provide_top,
            @Field("provide_attr") String provide_attr,
            @Field("need_top") int need_top,
            @Field("need_attr") String need_attr
    );

    @FormUrlEncoded
    @POST("/api/" + version + "/refresh")
    Call<ResultDO> refresh(
            @Header("token") String token,
            @Field("user_id") int userId,
            @Field("resources_id") int resources_id,
            @Field("type") int type
    );

    @FormUrlEncoded
    @POST("/api/" + version + "/share/scanQrcode")
    Call<ResultDO<QrcodeBean>> scanQrcode(
            @Header("token") String token,
            @Field("user_id") int userId,
            @Field("code") String code
    );


    @POST("/api/v2/getHotSearchs")
    Call<ResultDO<SelectCategoryTwo>> getHotSeacrhs();

    @FormUrlEncoded
    @POST("/api/v1/Search/preSearch")
    Call<ResultDO<ResourcesTitleBean>> preSearch(
            @Field("keyword") String keyword);

    @FormUrlEncoded
    @POST("/api/" + version + "/circle/getGroupMember")
    Call<ResultDO<List<MemberInfoBean>>> getGroupMember(@Field("circle_id") int circle_id);

    @FormUrlEncoded
    @POST("/api/" + version + "/circle/getCircleInfo")
    Call<ResultDO<CircleInfobean>> getCircleInfo(@Header("token") String token,
                                                 @Field("user_id") int userId,
                                                 @Field("circle_id") int circle_id);


    @FormUrlEncoded
    @POST("/api/" + version + "/circle/bootCircle")
    Call<ResultDO> bootCircle(@Header("token") String token,
                              @Field("user_id") int userId,
                              @Field("circle_id") int circle_id,
                              @Field("target_id") String target_id);

    @FormUrlEncoded
    @POST("/api/" + version + "/circle/setBlack")
    Call<ResultDO> setBlack(@Header("token") String token,
                            @Field("user_id") int userId,
                            @Field("circle_id") int circle_id,
                            @Field("action") int action,
                            @Field("target_id") String target_id);

    @FormUrlEncoded
    @POST("/api/v2/circle/getResource")
    Call<ResultDO<IndexNewBean>> getCircleResource(@Header("token") String token,
                                                   @Field("user_id") int userId,
                                                   @Field("circle_id") long circle_id,
                                                   @Field("page") int page);

    @FormUrlEncoded
    @POST("/api/" + version + "/circle/setNotification")
    Call<ResultDO> setNotification(@Header("token") String token,
                                   @Field("user_id") int userId,
                                   @Field("circle_id") int circle_id,
                                   @Field("action") int action);

    @FormUrlEncoded
    @POST("/api/" + version + "/circle/getNotice")
    Call<ResultDO<List<NoticeBean>>> getNoticeList(@Field("circle_id") int circle_id);

    @FormUrlEncoded
    @POST("/api/" + version + "/circle/setTop")
    Call<ResultDO> setCircleTop(@Header("token") String token,
                                @Field("user_id") int userId,
                                @Field("circle_id") int circle_id,
                                @Field("action") int action);

    @FormUrlEncoded
    @POST("/api/" + version + "/circle/getblackList")
    Call<ResultDO<List<MemberInfoBean>>> getblackList(@Header("token") String token,
                                                      @Field("user_id") int userId,
                                                      @Field("circle_id") int circle_id);

    @FormUrlEncoded
    @POST("/api/v2/circle_joins")
    Call<ResultDO<CircleApplyBean>> circleJoins(@Header("token") String token,
                                                @Field("user_id") int userId,
                                                @Field("circle_id") int circle_id,
                                                @Field("page") int page);

    @FormUrlEncoded
    @POST("/api/" + version + "/circle_handle")
    Call<ResultDO> circleHandle(@Header("token") String token,
                                @Field("user_id") int userId,
                                @Field("id") int id,
                                @Field("type") int type);

    @FormUrlEncoded
    @POST("/api/" + version + "/circle/applyCircle")
    Call<ResultDO<CircleCallBean>> applyCircle(@Header("token") String token,
                                               @Field("user_id") int userId,
                                               @Field("circle_id") int circle_id,
                                               @Field("remark") String remark);

    @FormUrlEncoded
    @POST("/api/" + version + "/circle/checkCircle")
    Call<ResultDO> checkCircle(@Header("token") String token,
                               @Field("user_id") int userId,
                               @Field("circle_id") int circle_id);

    @FormUrlEncoded
    @POST("/api/" + version + "/circle_unread")
    Call<ResultDO<UnReadBean>> unReadCircle(@Header("token") String token,
                                            @Field("user_id") int userId,
                                            @Field("circle_id") int circle_id);

    @FormUrlEncoded
    @POST("/api/" + version + "/corporate/getCorporate")
    Call<ResultDO<MyCompanyBean>> getMyCompany(@Header("token") String token,
                                               @Field("user_id") int userId);

    @FormUrlEncoded
    @POST("/api/" + version + "/corporate/edit")
    Call<ResultDO> corporateEdit(@Header("token") String token,
                                 @Field("user_id") int userId,
                                 @Field("logo") String logo,
                                 @Field("img_banner") String img_banner,
                                 @Field("url") String url,
                                 @Field("num") int num,
                                 @Field("stage_id") int stage_id,
                                 @Field("introduce") String introduce,
                                 @Field("id") int id);

    @FormUrlEncoded
    @POST("/api/" + version + "/corporate/getCorporateInfo")
    Call<ResultDO<MyCompanyBean>> getCorporateInfo(
            @Field("id") int id);

    @FormUrlEncoded
    @POST("/api/" + version + "/corporate/getCorporateInfo")
    Call<ResultDO<MyCompanyBean>> getCorporateInfoUser(
            @Field("user_id") int user_id,
            @Field("id") int id);


    @FormUrlEncoded
    @POST("/api/v3/resource/sendResource")
    Call<ResultDO<ResourceReleaseBean>> sendResourcex(@Header("token") String token,
                                                      @Field("title") String title,
                                                      @Field("user_id") int userId,
                                                      @Field("provide_top") int provide_top,
                                                      @Field("provide_attr") String provide_attr,
                                                      @Field("provide_remark") String provide_remark,
                                                      @Field("need_top") int need_top,
                                                      @Field("need_attr") String need_attr,
                                                      @Field("need_remark") String need_remark,
                                                      @Field("cooperation_mode") int cooperation_mode,
                                                      @Field("provide_describe") String provide_describe,
                                                      @Field("need_describe") String need_describe,
                                                      @Field("images") String images);


    @FormUrlEncoded
    @POST("/api/v4/resource/sendResource")
    Call<ResultDO<ResourceReleaseBean>> sendResourceV4(@Header("token") String token,
                                                       @Field("title") String title,
                                                       @Field("user_id") int userId,
                                                       @Field("provide_attr") String provide_attr,
                                                       @Field("provide_remark") String provide_remark,
                                                       @Field("need_attr") String need_attr,
                                                       @Field("need_remark") String need_remark,
                                                       @Field("cooperation_mode") int cooperation_mode,
                                                       @Field("provide_describe") String provide_describe,
                                                       @Field("need_describe") String need_describe,
                                                       @Field("images") String images,
                                                       @Field("city") int city
    );

    @FormUrlEncoded
    @POST("/api/v4/resource/editResource")
    Call<ResultDO<ResourceReleaseBean>> editResourceV4(@Header("token") String token,
                                                       @Field("user_id") int userId,
                                                       @Field("id") int id,
                                                       @Field("title") String title,
                                                       @Field("provide_attr") String provide_attr,
                                                       @Field("provide_remark") String provide_remark,
                                                       @Field("need_attr") String need_attr,
                                                       @Field("need_remark") String need_remark,
                                                       @Field("cooperation_mode") String cooperation_mode,
                                                       @Field("provide_describe") String provide_describe,
                                                       @Field("need_describe") String need_describe,
                                                       @Field("images") String images,
                                                       @Field("city") int city
    );


    @POST("/api/v2/resource/getScreenList")
    Call<ResultDO<SourceScreenBean>> sourceScreen();


    @FormUrlEncoded
    @POST("/api/v6/vip")
    Call<ResultDO<VipV3Bean>> getVipV3(@Header("token") String token,
                                       @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("/api/v2/aliyun/bindAxb")
    Call<ResultDO<SecretPhoneBean>> getSecretPhone(@Header("token") String token,
                                                   @Field("from_user_id") int from_user_id,
                                                   @Field("to_user_id") int to_user_id
    );


    @FormUrlEncoded
    @POST("/api/v1/wechat/template")
    Call<ResultDO<String>> sendTem(@Header("token") String token,
                                   @Field("user_id") int user_id,
                                   @Field("to_user_id") int to_user_id,
                                   @Field("make") int make,
                                   @Field("text") String text);


    @FormUrlEncoded
    @POST("/api/v2/corporate/lists")
    Call<ResultDO<CompanyListsBean>> getCompanyList(@Field("company_industry") int company_industry,
                                                    @Field("city") int city,
                                                    @Field("page") int page);

    @FormUrlEncoded
    @POST("/api/v1/Video/list")
    Call<ResultDO<ClassRoomListBean>> getVideoList(@Field("user_id") int user_id,
                                                   @Field("limit") int limit,
                                                   @Field("page") int page,
                                                   @Field("p_id") int p_id //	默认0 ,全部分类
    );


    @POST("/api/v1/ServiceProvider/getBanner")
    Call<ResultDO<List<ServiceBannerBean>>> getServiceBanner();

    //点赞
    @FormUrlEncoded
    @POST("/api/v1/Video/upVote")
    Call<ResultDO> videoUpVote(@Header("token") String token,
                               @Field("user_id") int user_id,
                               @Field("id") int id,//评论或视频id
                               @Field("type") int type,//1视频，2：评论
                               @Field("un_upvote") int un_upvote//	1点赞，0取消点赞
    );

    @FormUrlEncoded
    @POST("/api/v1/Video/detail")
    Call<ResultDO<ClassRoomDetailBean>> getVideoDetail(@Field("user_id") int user_id,
                                                       @Field("id") int id);

    @POST("/api/v1/Video/getAccess")
    Call<ResultDO<VidStsBean>> getAccess();

    @FormUrlEncoded
    @POST("/api/v1/corporate/users")
    Call<ResultDO<List<MemberInfoBean>>> getCompanyUserList(@Header("token") String token,
                                                            @Field("user_id") int userId,
                                                            @Field("id") int id
    );


    @FormUrlEncoded
    @POST("/api/v1/Video/comment")
    Call<ResultDO<CommentBean.ListBean>> videoComment(@Header("token") String token,
                                                      @Field("user_id") int userId,
                                                      @Field("video_id") int video_id,
                                                      @Field("content") String content,
                                                      @Field("p_id") int p_id  //默认0：回复时传评论id

    );

    @FormUrlEncoded
    @POST("/api/v1/Video/commentList")
    Call<ResultDO<CommentBean>> getCommentList(@Field("user_id") int userId,
                                               @Field("video_id") int video_id,
                                               @Field("page") int page,
                                               @Field("limit") int limit

    );

    @FormUrlEncoded
    @POST("/api/v1/circle/getCircleBasicInfo")
    Call<ResultDO<GroupBean>> getCircleBasicInfo(@Field("circle_id") int circle_id);


    @FormUrlEncoded
    @POST("/api/v2/corporate/edit")
    Call<ResultDO> commitCorporateEdit(@Header("token") String token,
                                       @Field("user_id") int user_id,
                                       @Field("id") int id,
                                       @Field("logo") String logo,
                                       @Field("name") String name,
                                       @Field("url") String url,
                                       @Field("brand") String brand,
                                       @Field("introduce") String introduce,
                                       @Field("city") int city,
                                       @Field("company_industry") int company_industry);

    @FormUrlEncoded
    @POST("/api/v2/corporate/search")
    Call<ResultDO<CompanyListsBean>> searchCompanyList(@Field("keyword") String keyword,
                                                       @Field("page") int page);

    @FormUrlEncoded
    @POST("/api/v2/user_collection")
    Call<ResultDO<IndexNewBean>> myCollectList(@Header("token") String token,
                                               @Field("user_id") int user_id,
                                               @Field("page") int page);

    @FormUrlEncoded
    @POST("/api/v1/ServiceProvider/collectionList")
    Call<ResultDO<IndexServiceBean>> myServiceCollectList(@Header("token") String token,
                                                          @Field("user_id") int user_id,
                                                          @Field("page") int page);

    @FormUrlEncoded
    @POST("/api/v1/resource/userCollection")
    Call<ResultDO<GoToCollectBean>> goToCollect(@Header("token") String token,
                                                @Field("user_id") int user_id,
                                                @Field("resource_id") int resource_id,
                                                @Field("action") int action
    );

    @FormUrlEncoded
    @POST("/api/v1/ServiceProvider/userCollection")
    Call<ResultDO<Object>> goToServiceCollect(@Header("token") String token,
                                              @Field("user_id") int user_id,
                                              @Field("service_id") int service_id,
                                              @Field("action") int action
    );


    @FormUrlEncoded
    @POST("/api/v2/resource/report")
    Call<ResultDO> goReport(@Header("token") String token,
                            @Field("user_id") int user_id,
                            @Field("target_id") int target_id,
                            @Field("type") int type,
                            @Field("content") String content,
                            @Field("category_id") int category_id
    );

    @FormUrlEncoded
    @POST("/api/v2/resource/getConfig")
    Call<ResultDO<GetConfigBean>> getConfig(@Field("version") String version);

    @FormUrlEncoded
    @POST("/api/v1/resource/getTags")
    Call<ResultDO<List<CellTagsBean>>> getTags(@Field("tags") String tags);

    @FormUrlEncoded
    @POST("/api/v3/resource/getCategoryList")
    Call<ResultDO<SeclectCateBean>> getUserCategoryV3(@Field("user_id") int userId,
                                                      @Header("token") String token
    );

    @POST("/api/v2/getAppArea")
    Call<ResultDO<List<CityV2Bean>>> getAppArea();

    @FormUrlEncoded
    @POST("/api/v1/daily/share")
    Call<ResultDO> dailyShare(@Field("user_id") int userId,
                              @Header("token") String token
    );

    @FormUrlEncoded
    @POST("/api/v1/resource/getUserSendRecommend")
    Call<ResultDO<List<IndexNewBean.ListBean>>> getRecommend(@Field("query_id") String query_id,
                                                             @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("/api/v1/daily/sign")
    Call<ResultDO> goToSign(@Header("token") String token,
                            @Field("user_id") int userId);

    //首页活动弹窗
    @GET("/api/v1/popup/getPopup")
    Call<ResultDO<GetPopupBean>> getPopup();

    //群组类别列表
    @GET("/api/v2/group/getCategoryList")
    Call<ResultDO<List<AddGroupClassifyBean>>> getCategoryList(@Query("category") int category);

    //群组列表
    @GET("api/v2/group/getGroupList")
    Call<ResultDO<AddGroupListBean>> getGroupList(@Query("keyword") String keyword,
                                                  @Query("category") int category,
                                                  @Query("page") int page
    );

    //创建群组接口
    @FormUrlEncoded
    @POST("/api/v2/group/establishGroup")
    Call<ResultDO> establishGroup(
            @Header("token") String token,
            @Field("user_id") int userId,
            @Field("name") String name,
            @Field("category") int category,
            @Field("introduction") String introduction,
            @Field("city") int city
    );

    //我的群组列表
    @GET("api/v2/group/getMyGroupList")
    Call<ResultDO<List<MyGroupListBean>>> getMyGroupList(
            @Header("token") String token,
            @Query("user_id") int userId,
            @Query("page") int page
    );

    //群组成员
    @GET("/api/v2/group/getUserList")
    Call<ResultDO<List<GroupMemberManageBean>>> getUserList(
            @Header("token") String token,
            @Query("group_id") long group_id,
            @Query("user_id") int user_id,
            @Query("keyword") String keyword

    );

    //群组详细信息查看
    @GET("/api/v2/group/getInformation")
    Call<ResultDO<GroupInfoBean>> getGroupInformation(
            @Header("token") String token,
            @Query("user_id") int user_id,
            @Query("group_id") long group_id
    );

    //获取相似群组
    @FormUrlEncoded
    @POST("/api/v2/group/similarGroup")
    Call<ResultDO<List<SimilarGroupBean>>> getSimilarGroup(
            @Header("token") String token,
            @Field("user_id") int userId,
            @Field("category") int category
    );

    //成员管理
    @FormUrlEncoded
    @POST("/api/v2/group/administration")
    Call<ResultDO> goMemberManage(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("be_operated_user") int be_operated_user,
            @Field("group_id") long group_id,
            @Field("identity_type") int identity_type,
            @Field("name") String name,
            @Field("type") int type,
            @Field("code") int code,
            @Field("mobile") String mobile
    );

    //取消创建正在审核中的群组
    @FormUrlEncoded
    @POST("/api/v2/group/cancel")
    Call<ResultDO> groupCancle(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("group_id") int group_id
    );

    //申请加群
    @FormUrlEncoded
    @POST("/api/v2/group/applyGroup")
    Call<ResultDO> applyGroup(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("group_id") long group_id,
            @Field("name") String name,
            @Field("type") int type,
            @Field("remark") String remark
    );

    //退出群组
    @FormUrlEncoded
    @POST("/api/v2/group/quitGroup")
    Call<ResultDO> quitGroup(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("group_id") int group_id
    );

    //升级群组
    @FormUrlEncoded
    @POST("/api/v2/group/upgrade")
    Call<ResultDO<UpgradeGroupBean>> upgradeGroup(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("name") String name,
            @Field("group_id") int group_id
    );

    //编辑群公告
    @FormUrlEncoded
    @POST("/api/v2/group/editNotice")
    Call<ResultDO> editNotice(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("group_id") long group_id,
            @Field("notice") String notice,
            @Field("name") String name,
            @Field("type") int type
    );

    //编辑群组资料
    @FormUrlEncoded
    @POST("/api/v2/group/modifyInfo")
    Call<ResultDO> modifyInfo(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("group_id") long group_id,
            @Field("name") String name,
            @Field("introduction") String introduction,
            @Field("category") int category,
            @Field("city") int city
    );

    //同意或拒绝加入
    @FormUrlEncoded
    @POST("/api/v2/group/group_handle")
    Call<ResultDO<SystemMsgBean>> grouphandle(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("type") int type,
            @Field("refuse_remark") String refuse_remark
    );

    //设置加群方式
    @FormUrlEncoded
    @POST("/api/v2/group/joinManner")
    Call<ResultDO> joinManner(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("group_id") long group_id,
            @Field("is_verify") int is_verify
    );

    //获取群组公告
    @FormUrlEncoded
    @POST("/api/v2/group/getNotice")
    Call<ResultDO<GroupNoticeBean>> getNotice(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("group_id") long group_id
    );

    //检查用户创建群组资格
    @FormUrlEncoded
    @POST("/api/v2/group/check_type")
    Call<ResultDO> groupCheck(
            @Header("token") String token,
            @Field("user_id") int user_id
    );

    //检查用户有无群组
    @FormUrlEncoded
    @POST("/api/v2/group/checkMyGroup")
    Call<ResultDO> checkMyGroup(
            @Header("token") String token,
            @Field("user_id") int user_id

    );

    //获取留言数据
    @FormUrlEncoded
    @POST("/api/v1/comment/inquire")
    Call<ResultDO<InquireBean>> getInquire(
            @Header("token") String token,
            @Field("topic_id") int topic_id,
            @Field("topic_type") int topic_type, //	1：资源，2：服务商，默认1
            @Field("page") int page,
            @Field("comment_id") int commentId

    );

    //添加留言
    @FormUrlEncoded
    @POST("/api/v1/comment/comment")
    Call<ResultDO<InquireBean.ListBean>> goComment(
            @Header("token") String token,
            @Field("topic_id") int topic_id,
            @Field("content") String content,
            @Field("nickname") String nickname,
            @Field("thumb_img") String thumb_img,
            @Field("from_uid") int from_uid,
            @Field("company") String company,
            @Field("position") String position,
            @Field("is_v") int is_v,
            @Field("topic_type") int topic_type //	1：资源，2：服务商，默认1
    );


    //回复
    @FormUrlEncoded
    @POST("/api/v1/comment/reply")
    Call<ResultDO<InquireBean.ListBean>> goReplyComment(
            @Header("token") String token,
            @Field("comment_id") int comment_id,
            @Field("reply_type") int reply_type,
            @Field("topic_id") int topic_id,
            @Field("reply_id") int reply_id,
            @Field("content") String content,
            @Field("from_uid") int from_uid,
            @Field("from_thumb_img") String from_thumb_img,
            @Field("from_nickname") String from_nickname,
            @Field("from_company") String from_company,
            @Field("from_position") String from_position,
            @Field("is_v") int is_v,
            @Field("topic_type") int topic_type //	1：资源，2：服务商，默认1
    );

    //获取互动消息
    @FormUrlEncoded
    @POST("/api/v1/comment/interactiveNews")
    Call<ResultDO<InteractiveNewsBean>> getInteractiveNews(
            @Header("token") String token,
            @Field("user_id") int userId,
            @Field("page") int page
    );

//    //获取兑换码套餐
//    @FormUrlEncoded
//    @POST("/api/v1/redemptionCode/codePackage")
//    Call<ResultDO>

    //删除留言
    @FormUrlEncoded
    @POST("/api/v1/comment/doDel")
    Call<ResultDO> doDelComment(
            @Header("token") String token,
            @Field("id") int id,
            @Field("type") int type
    );

    //删除视频留言
    @FormUrlEncoded
    @POST("/api/v1/Video/delComment")
    Call<ResultDO> doVideoDelComment(
            @Header("token") String token,
            @Field("id") int id, //	评论id
            @Field("user_id") int user_id
    );

    //使用兑换码
    @FormUrlEncoded
    @POST("/api/v1/redemptionCode/useCode")
    Call<ResultDO> redeUseCode(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("code") String code
    );

    //修改奖励券状态
    @FormUrlEncoded
    @POST("/api/v4/lottery/saveVoucher")
    Call<ResultDO> saveVoucher(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") String id
    );

    //用户登录推送服务经理
    @FormUrlEncoded
    @POST("/api/v1/loginPush")
    Call<ResultDO> loginPush(
            @Header("token") String token,
            @Field("user_id") int user_id
    );

    //用户发布资源
    @FormUrlEncoded
    @POST("/api/v5/resource/sendResource")
    Call<ResultDO<ResourceReleaseBean>> sendResourceV5(@Header("token") String token,
                                                       @Field("title") String title,
                                                       @Field("user_id") int userId,
                                                       @Field("provide_attr") String provide_attr,
                                                       @Field("provide_remark") String provide_remark,
                                                       @Field("need_attr") String need_attr,
                                                       @Field("need_remark") String need_remark,
                                                       @Field("cooperation_mode") int cooperation_mode,
                                                       @Field("provide_describe") String provide_describe,
                                                       @Field("need_describe") String need_describe,
                                                       @Field("images") String images,
                                                       @Field("thumb_img") String thumb_img,
                                                       @Field("city") int city
    );

    //是否有绑定的兑换码
    @FormUrlEncoded
    @POST("/api/v1/redemptionCode/whetherBind")
    Call<ResultDO> whetherBind(@Header("token") String token,
                               @Field("user_id") int user_id,
                               @Field("name") String name);


    //兑换码激活
    @FormUrlEncoded
    @POST("/api/v1/redemptionCode/activation")
    Call<ResultDO> goActivation(@Header("token") String token,
                                @Field("user_id") int user_id);

    //站内消息
    @FormUrlEncoded
    @POST("/api/v2/news")
    Call<ResultDO<NewsV2Bean>> getNewsV2(@Header("token") String token,
                                         @Field("user_id") int user_id);

    //好友申请
    @FormUrlEncoded
    @POST("/api/v1/friend/applyFor")
    Call<ResultDO<GoFriendApplyBean>> goFriendApply(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("to_user_id") int to_user_id,
            @Field("type") int type
    );


    //好友邀请
    @FormUrlEncoded
    @POST("/api/v2/Connections/invite")
    Call<ResultDO> inviteConnections(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("mobile") String mobile,
            @Field("name") String name
    );

    //好友同意或拒绝
    @FormUrlEncoded
    @POST("/api/v1/friend/isAgree")
    Call<ResultDO> friendIsAgree(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("operating") int operating
    );

    //好友列表
    @FormUrlEncoded
    @POST("/api/v1/friend/getList")
    Call<ResultDO<GetFriendListBean>> getFriendList(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("keywords") String keywords
    );

    //分组列表
    @FormUrlEncoded
    @POST("/api/v1/user/getGroupUser")
    Call<ResultDO<List<GroupFriendBean>>> getGroupFriendList(
            @Header("token") String token,
            @Field("user_id") int user_id
    );

    //分组列表全部成员
    @FormUrlEncoded
    @POST("/api/v1/user/usersUnderName")
    Call<ResultDO<List<GroupFriendBean.UserListBean>>> getGroupFriendListAll(
            @Header("token") String token,
            @Field("user_id") int user_id
    );

    //添加分组
    @FormUrlEncoded
    @POST("/api/v1/user/createGroup")
    Call<ResultDO<AddGroupBean>> createGroup(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("name") String name
    );

    //修改分组名
    @FormUrlEncoded
    @POST("/api/v1/user/editGroupName")
    Call<ResultDO> editGroupName(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("group_id") int group_id,
            @Field("name") String name
    );

    //删除分组
    @FormUrlEncoded
    @POST("/api/v1/user/delGroup")
    Call<ResultDO> delGroup(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("group_id") int group_id
    );

    //删除分组
    @FormUrlEncoded
    @POST("/api/v1/user/moveGroup")
    Call<ResultDO<AddGroupBean>> moveGroup(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("group_id") int group_id,
            @Field("member_id") int member_id
    );

    //分组列表全部成员
    @FormUrlEncoded
    @POST("/api/v1/greetings/getGreetingsList")
    Call<ResultDO<GreentingsBean>> getGreetingsList(
            @Header("token") String token,
            @Field("user_id") int user_id
    );

    //招呼语列表
    @FormUrlEncoded
    @POST("/api/v1/greetings/addGreetings")
    Call<ResultDO<AddGroupBean>> addGreetings(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("title") String title
    );

    //删除招呼语
    @FormUrlEncoded
    @POST("/api/v1/greetings/delGreetings")
    Call<ResultDO> delGreetings(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //设置招呼语
    @FormUrlEncoded
    @POST("/api/v1/greetings/selected")
    Call<ResultDO> selectedGreetings(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //合作名片分享
    @FormUrlEncoded
    @POST("/api/v1/friend/contactShare")
    Call<ResultDO> shareCard(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("to_user_id") int to_user_id,
            @Field("share_user_id") int share_user_id
    );

    //资源分享
    @FormUrlEncoded
    @POST("/api/v1/friend/resourcesShare")
    Call<ResultDO> shareResource(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("to_user_id") int to_user_id,
            @Field("resources_id") int resources_id

    );

    //删除好友
    @FormUrlEncoded
    @POST("/api/v1/friend/delFriend")
    Call<ResultDO> delFriend(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("to_user_id") int to_user_id
    );


    //好友状态查询
    @FormUrlEncoded
    @POST("/api/v1/friend/statusInquire")
    Call<ResultDO<FriendStatusBean>> getFriendStatus(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("to_user_id") int to_user_id

    );

    //app错误信息保存
    @FormUrlEncoded
    @POST("/api/v1/saveLog")
    Call<ResultDO> saveLog(
            @Field("data") String data
    );


    //获取发布类型
    @FormUrlEncoded
    @POST("/api/v3/resource/getReleaseType")
    Call<ResultDO<List<GetReleaseTypeBean>>> getReleaseType(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("from") int from
    );

    //获取资源发布标签模板
    @FormUrlEncoded
    @POST("/api/v6/resource/getReleaseTemplate")
    Call<ResultDO<GetReleaseTemplateNewBean>> getReleaseTemplate(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("p_id") int p_id
    );

    //资源发布V6
    @FormUrlEncoded
    @POST("/api/v6/resource/sendResource")
    Call<ResultDO<ResourceReleaseBean>> sendResourceV6(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("title") String title,
            @Field("p_id") int p_id,
            @Field("provide_attr") String provide_attr,
            @Field("provide_remark") String provide_remark,
            @Field("need_attr") String need_attr,
            @Field("need_remark") String need_remark,
            @Field("provide_describe") String provide_describe,
            @Field("need_describe") String need_describe,
            @Field("images") String images,
            @Field("thumb_img") String thumb_img,
            @Field("city") int city,
            @Field("is_combing") int is_combing,  //是否使用梳理卡，默认0未使用，1使用
            @Field("is_transaction") int is_transaction
    );

    //资源修改V5
    @FormUrlEncoded
    @POST("/api/v5/resource/editResource")
    Call<ResultDO<ResourceReleaseBean>> editResourceV5(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("title") String title,
            @Field("provide_attr") String provide_attr,
            @Field("provide_remark") String provide_remark,
            @Field("need_attr") String need_attr,
            @Field("need_remark") String need_remark,
            @Field("provide_describe") String provide_describe,
            @Field("need_describe") String need_describe,
            @Field("images") String images,
            @Field("thumb_img") String thumb_img,
            @Field("city") int city,
            @Field("p_id") int p_id,
            @Field("is_combing") int is_combing,
            @Field("is_transaction") int is_transaction
    );


    //解析tagsV3
    @FormUrlEncoded
    @POST("/api/v3/resource/getTags")
    Call<ResultDO<CellTagsBean>> getTagsV3(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("p_id") int p_id
    );

    //获取异业课堂分类
    @POST("/api/v1/Video/categoryList")
    Call<ResultDO<List<VideoCategoryBean>>> getVedioTags();

    //资源修改获取详情接口v3
    @FormUrlEncoded
    @POST("/api/v3/resource/getEditResourceInfo")
    Call<ResultDO<GetEditResourceInfoV2Bean>> getEditResourceV2(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //修改登录密码
    @FormUrlEncoded
    @POST("/api/v2/pwd")
    Call<ResultDO> changeCode(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("mobile") String mobile,
            @Field("code") String code,
            @Field("new_password") String new_password
    );

    //发送语音验证码
    @FormUrlEncoded
    @POST("/api/v1/voiceVerify")
    Call<ResultDO> voiceVerity(
            @Header("token") String token,
            @Field("mobile") String mobile,
            @Field("sign") String sign,
            @Field("type") int type,
            @Field("platform") int platform
    );

    //我的留言列表
    @FormUrlEncoded
    @POST("/api/v1/comment/myComment")
    Call<ResultDO<MyCommentBean>> getMyComment(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("page") int page

    );

    //获取App配置V4
    @FormUrlEncoded
    @POST("/api/v4/resource/getConfig")
    Call<ResultDO<GetConfigBean>> getConfigBeanV4(
            @Field("version") String version
    );

    //获取App配置V5
    @POST("/api/v7/resource/getConfig")
    Call<ResultDO<GetConfigBean>> getConfigBeanV5(
    );

    //获取每日热门列表
    @POST("/api/v1/resource/hotResources")
    Call<ResultDO<List<HotResourceBean>>> getHotResource();


    //获取精选资源

    @FormUrlEncoded
    @POST("/api/v6/resource/getResource")
    Call<ResultDO<IndexNewBean>> getSelectionResource(
            @Field("page") int page,
            @Field("is_featured") int is_featured
    );

    //我的活动列表
    @FormUrlEncoded
    @POST("/api/v1/Activity/myList")
    Call<ResultDO<MyActListBean>> getMyActList(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //电子票详情
    @FormUrlEncoded
    @POST("/api/v1/Activity/eventTicket")
    Call<ResultDO<GoseeBillBean>> getGoseeBill(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //取消报名
    @FormUrlEncoded
    @POST("/api/v1/Activity/cancelSignUp")
    Call<ResultDO> cancelSignUp(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //邀请加入群组
    @FormUrlEncoded
    @POST("/api/v2/group/inviteToJoin")
    Call<ResultDO> inviteToJoin(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("to_user_id") int to_user_id,
            @Field("group_id") int group_id

    );

    //是否有激活兑换码弹窗
    @FormUrlEncoded
    @POST("/api/v1/popup/vipPopup")
    Call<ResultDO<VipPopUpBean>> vipPopup(
            @Header("token") String token,
            @Field("user_id") int user_id
    );

    //暂不激活
    @FormUrlEncoded
    @POST("/api/v1/popup/waitActivated")
    Call<ResultDO> waitActivated(
            @Header("token") String token,
            @Field("user_id") int user_id
    );


    //加油包生成订单
    @FormUrlEncoded
    @POST("/api/v3/talkPackage_buy")
    Call<ResultDO<TalkPackageBuyBean>> talkPackageBuy(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("plat_form") int plat_form
    );

    //获取人脉推荐列表
    @FormUrlEncoded
    @POST("/api/v1/Connections/recommendList")
    Call<ResultDO<List<RecommendBean>>> getRecommendList(
            @Header("token") String token,
            @Field("user_id") int user_id
    );

    //获取人脉通讯录列表
    @FormUrlEncoded
    @POST("/api/v2/Connections/addressList")
    Call<ResultDO<AddressListBean>> getAddressList(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("mobile_list") String mobile_list,
            @Field("page") int page
    );

    //好友批量添加
    @FormUrlEncoded
    @POST("/api/v1/friend/batchAdd")
    Call<ResultDO> goBatchAdd(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("uid_data") String uid_data

    );

    //获取刷新卡列表
    @FormUrlEncoded
    @POST("/api/v6/topCard")
    Call<ResultDO<List<TopCardBean>>> getTopCardList(
            @Header("token") String token,
            @Field("user_id") int user_id
    );

    //获取梳理卡列表
    @FormUrlEncoded
    @POST("/api/v6/combingCard")
    Call<ResultDO<CombingCardBean>> getCombingCardList(
            @Header("token") String token,
            @Field("user_id") int user_id
    );

    //获取置顶卡列表
    @FormUrlEncoded
    @POST("/api/v6/fixedTopCard")
    Call<ResultDO<List<TopCardBean>>> getFixedTopCardList(
            @Header("token") String token,
            @Field("user_id") int user_id
    );

    //刷新卡生成订单
    @FormUrlEncoded
    @POST("/api/v3/topCard_buy")
    Call<ResultDO<OrderInfoBean>> topCardBuy(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("plat_form") int plat_form
    );

    //置顶卡生成订单
    @FormUrlEncoded
    @POST("/api/v3/fixedTopCard_buy")
    Call<ResultDO<OrderInfoBean>> fixedTopCardBuy(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("plat_form") int plat_form
    );

    //梳理卡生成订单
    @FormUrlEncoded
    @POST("/api/v3/combingCard_buy")
    Call<ResultDO<OrderInfoBean>> combingCardBuy(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("plat_form") int plat_form
    );


    //刷新前检测接口(置顶功能)
    @FormUrlEncoded
    @POST("/api/v1/refreshCheck")
    Call<ResultDO> goRefreshCheck(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("type") int type     //1：资源，2：服务商
    );

    @FormUrlEncoded
    @POST("/api/v1/comment/turnItOnOrOff")
    Call<ResultDO> goTurnItOnOrOff(
            @Header("token") String token,
            @Field("user_id") int user_id,//	用户UID
            @Field("resource_id") int resource_id, //资源ID
            @Field("type") int type //	类型，1:关闭，2：开启
    );

    @FormUrlEncoded
    @POST("/api/v1/resource/commonlySentence")
    Call<ResultDO<List<LLSimpleTextBean>>> getCommonlySentence(
            @Field("user_id") int user_id,
            @Header("token") String token
    );


    @FormUrlEncoded
    @POST("/api/v1/wechat/wechatQR")
    Call<ResultDO<WechatQr>> getWechatQR(
            @Field("resource_id") int resource_id,
            @Field("invit_id") int invit_id,
            @Field("type") int type //个人分享传2，资源传1
    );


    @FormUrlEncoded
    @POST("/api/v1/resource/getTemplate")
    Call<ResultDO<ReleaseTemplateBean>> getTemplate(
            @Field("user_id") int user_id,
            @Header("token") String token,
            @Field("p_id") int p_id

    );


    @POST("/api/v1/ServiceProvider/categoryAndTag")
    Call<ResultDO<List<ServiceCategoryAndTag>>> getCategoryAndTag(
            @Header("token") String token

    );

    //发布服务
    @FormUrlEncoded
    @POST("/api/v1/ServiceProvider/sendInfo")
    Call<ResultDO<ReleaseServiceSuccessBean>> sendInfo(
            @Field("user_id") int user_id,
            @Header("token") String token,
            @Field("title") String title,
            @Field("attr") String attr,
            @Field("images") String images,
            @Field("thumb_img") String thumb_img,
            @Field("p_id") int p_id,
            @Field("details") String details

    );

    //发布案例
    @FormUrlEncoded
    @POST("/api/v1/ServiceProvider/sendServiceCase")
    Call<ResultDO<ReleaseServiceSuccessBean>> sendServiceCase(
            @Field("user_id") int user_id,
            @Field("service_id") int service_id,
            @Header("token") String token,
            @Field("title") String title,
            @Field("images") String images,
            @Field("thumb_img") String thumb_img,
            @Field("details") String details

    );

    //发布服务
    @FormUrlEncoded
    @POST("/api/v1/ServiceProvider/editService")
    Call<ResultDO<ReleaseServiceSuccessBean>> editService(
            @Field("user_id") int user_id,
            @Header("token") String token,
            @Field("title") String title,
            @Field("attr") String attr,
            @Field("images") String images,
            @Field("thumb_img") String thumb_img,
            @Field("p_id") int p_id,
            @Field("details") String details,
            @Field("service_id") int service_id

    );

    //服务列表
    @FormUrlEncoded
    @POST("/api/v1/ServiceProvider/infoList")
    Call<ResultDO<IndexServiceBean>> serviceInfoList(
            @Field("sort_order") String sort_order,
            @Field("query_id") int query_id,
            @Field("p_id") int p_id,
            @Field("page") int page

    );

    //搜索服务商
    @FormUrlEncoded
    @POST("/api/v1/ServiceProvider/infoList")
    Call<ResultDO<IndexServiceBean>> serachServiceInfoList(
            @Field("sort_order") String sort_order,
            @Field("page") int page,
            @Field("keyword") String keyword

    );

    //案例列表
    @FormUrlEncoded
    @POST("/api/v1/ServiceProvider/caseList")
    Call<ResultDO<List<ServiceCaseBean>>> caseList(
            @Header("token") String token,
            @Field("service_id") int service_id

    );


    //福利社列表

    @POST("/api/v1/welfareClub/getList")
    Call<ResultDO<List<WelfareAgencyBean>>> welfareClubList(
            @Header("token") String token

    );

    //案例详情
    @FormUrlEncoded
    @POST("/api/v1/ServiceProvider/caseInfo")
    Call<ResultDO<CaseBean>> caseInfo(
            @Header("token") String token,
            @Field("case_id") int case_id

    );


    //公司全部服务列表
    @FormUrlEncoded
    @POST("/api/v1/ServiceProvider/infoList")
    Call<ResultDO<IndexServiceBean>> serviceInfoListtwo(
            @Field("sort_order") String sort_order,
            @Field("query_id") int query_id,
            @Field("p_id") int p_id,
            @Field("page") int page,
            @Field("corporate_id") int corporate_id


    );

    /**
     * 删除案例
     */
    @FormUrlEncoded
    @POST("/api/v1/ServiceProvider/delCase")
    Call<ResultDO<Object>> delCase(@Header("token") String token,
                                   @Field("user_id") int userId,
                                   @Field("id") int id);


    /**
     * 查看审核未通过原因
     */
    @FormUrlEncoded
    @POST("/api/v1/ServiceProvider/getVerifyInfo")
    Call<ResultDO<VerifyInfo>> getVerifyInfo(@Header("token") String token,
                                             @Field("user_id") int userId,
                                             @Field("id") int id);


    /**
     * 取消预约
     */
    @FormUrlEncoded
    @POST("/api/v2/resource/cancelFixedTop")
    Call<ResultDO<FixedTopCancleBean>> cancelFixedTop(@Header("token") String token,
                                                      @Field("user_id") int userId,
                                                      @Field("resources_id") int resources_id);

    /**
     * 固定预加载
     */
    @FormUrlEncoded
    @POST("/api/v1/resource/apptFixedTop")
    Call<ResultDO<TimeBean>> apptFixedTop(@Header("token") String token,
                                          @Field("user_id") int userId,
                                          @Field("p_id") int p_id);

    /**
     * 预约时间
     */
    @FormUrlEncoded
    @POST("/api/v2/resource/fixedTop")
    Call<ResultDO<Object>> fixedTop(@Header("token") String token,
                                    @Field("user_id") int userId,
                                    @Field("p_id") int p_id,
                                    @Field("resources_id") int resources_id,
                                    @Field("reservation_time") String reservation_time
    );


    /**
     * 获取服务经理名下所有用户
     */
    @FormUrlEncoded
    @POST("/api/v1/user/getUserList")
    Call<ResultDO<UserIdBean>> getUserList(@Header("token") String token,
                                           @Field("user_id") int userId
    );


    /**
     * 获取实人认证token
     */
    @FormUrlEncoded
    @POST("/api/v1/CloudAuth/getToken")
    Call<ResultDO<TokenBean>> getToken(@Header("token") String token,
                                       @Field("user_id") int userId,
                                       @Field("name") String name,
                                       @Field("IdentificationNumber") String IdentificationNumber
    );


    /**
     * 查询实人认证结果
     */
    @FormUrlEncoded
    @POST("/api/v1/CloudAuth/getStatusCode")
    Call<ResultDO<TokenBean>> getStatusCode(@Header("token") String token,
                                            @Field("user_id") int userId
    );


    /**
     * 发起担保交易
     */
    @FormUrlEncoded
    @POST("/api/v1/Guarantee/initiateGuarantee")
    Call<ResultDO<Object>> initiateGuarantee(@Header("token") String token,
                                             @Field("user_id") int userId,
                                             @Field("type") int type,
                                             @Field("text") String text,
                                             @Field("obj_user_id") int obj_user_id,
                                             @Field("estimated_amount") String estimated_amount
    );


    /**
     * 担保交易详情
     */
    @FormUrlEncoded
    @POST("/api/v1/Guarantee/getDetail")
    Call<ResultDO<GuaranteeDetailBean>> getGuaranteeDetail(@Header("token") String token,
                                                           @Field("user_id") int userId,
                                                           @Field("id") int id
    );

    //同意或不同意担保交易
    @FormUrlEncoded
    @POST("/api/v1/Guarantee/agreeGuarantee")
    Call<ResultDO> agreeGuarantee(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("is_agree") int is_agree   //1同意，0不同意，默认1
    );

    //取消担保交易
    @FormUrlEncoded
    @POST("/api/v1/Guarantee/cancelGuarantee")
    Call<ResultDO> cancelGuarantee(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //支付担保金
    @FormUrlEncoded
    @POST("/api/v1/Guarantee/pay")
    Call<ResultDO> payGuarantee(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("pay_type") int pay_type,//转账类型
            @Field("picture_proof") String picture_proof, //转账截图
            @Field("type") int type, //1：首次，4：补交
            @Field("amount") String amount  //补交金额，默认0
    );

    //支付担保金详情
    @FormUrlEncoded
    @POST("/api/v1/Guarantee/payDetail")
    Call<ResultDO<PayDetailBean>> payGuaranteeDetail(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("order_sn") String order_sn,
            @Field("event_status") int event_status//	1：首次交易，2：补交
    );

    //卖方申请结算
    @FormUrlEncoded
    @POST("/api/v1/Guarantee/applicationSettlement")
    Call<ResultDO> applicationSettlement(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id
    );


    //交易订单列表
    @FormUrlEncoded
    @POST("/api/v1/Guarantee/getList")
    Call<ResultDO<GuaranteeOrderBean>> getGuaranteeOrderList(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("page") int page
    );

    //买方申请结算
    @FormUrlEncoded
    @POST("/api/v1/Guarantee/settlement")
    Call<ResultDO> guaranteeSettlement(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("billing_amount") String billing_amount
    );

    //买方申请退款
    @FormUrlEncoded
    @POST("/api/v1/Guarantee/applicationRefund")
    Call<ResultDO> applicationRefund(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("order_sn") String order_sn,
            @Field("id") int id,
            @Field("obj_user_id") int obj_user_id,
            @Field("guarantee_amount") String guarantee_amount,
            @Field("billing_amount") String billing_amount,
            @Field("refund_amount") String refund_amount,
            @Field("refund_status") int refund_status,
            @Field("refund_desc") String refund_desc,
            @Field("images") String images,
            @Field("thumb_img") String thumb_img

    );


    //退款详情
    @FormUrlEncoded
    @POST("/api/v1/Guarantee/refundDetail")
    Call<ResultDO<RefundDetailBean>> refundDetail(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("order_sn") String order_sn
    );


    //申请详情
    @FormUrlEncoded
    @POST("/api/v1/Guarantee/serviceInfo")
    Call<ResultDO<GuaranteeServiceInfoBean>> serviceInfo(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    //取消退款
    @FormUrlEncoded
    @POST("/api/v1/Guarantee/cancelRefund")
    Call<ResultDO> cancelRefund(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id
    );


    //同意退款
    @FormUrlEncoded
    @POST("/api/v1/Guarantee/agreeRefund")
    Call<ResultDO> agreeRefund(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id
    );


    //拒绝退款
    @FormUrlEncoded
    @POST("/api/v1/Guarantee/refuseRefund")
    Call<ResultDO> refuseRefund(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("refuse_desc") String refuse_desc,
            @Field("refuse_images") String refuse_images,
            @Field("refuse_thumb_img") String refuse_thumb_img
    );

    //申请客服介入
    @FormUrlEncoded
    @POST("/api/v1/Guarantee/applicationService")
    Call<ResultDO> applicationService(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("type") int type,
            @Field("desc") String desc,
            @Field("images") String images,
            @Field("thumb_img") String thumb_img
    );

    //我的钱包
    @FormUrlEncoded
    @POST("/api/v1/Account/userAccount")
    Call<ResultDO<MyWalletBean>> userAccount(
            @Header("token") String token,
            @Field("user_id") int user_id

    );

    //账单列表
    @FormUrlEncoded
    @POST("/api/v1/Account/billList")
    Call<ResultDO<BillBean>> billList(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("page") int page

    );

    //账单详情
    @FormUrlEncoded
    @POST("/api/v1/Account/billDetail")
    Call<ResultDO<WalletDetailBean>> billDetail(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id

    );

    //提现列表
    @FormUrlEncoded
    @POST("/api/v1/Account/withdrawList")
    Call<ResultDO<CashWithdrawalBean>> withdrawList(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("page") int page

    );

    //提现
    @FormUrlEncoded
    @POST("/api/v1/Account/withdraw")
    Call<ResultDO> withdraw(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("code") String code,
            @Field("mobile") String mobile,
            @Field("account_type") int account_type,
            @Field("withdrawals_amount") String withdrawals_amount,
            @Field("alipay_account") String alipay_account,
            @Field("alipay_name") String alipay_name,
            @Field("bank_account") String bank_account,
            @Field("bank_account_name") String bank_account_name,
            @Field("bank") String bank
    );

    //提现列表
    @FormUrlEncoded
    @POST("/api/v1/Account/handlingFee")
    Call<ResultDO> handlingFee(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("amount") String amount

    );

    //提现列表
    @POST("/api/v1/getLaunchScreen")
    Call<ResultDO<SplashBean>> getLaunchScreen(
    );

    //获取发布悬赏求助类型接口
    @POST("/api/v1/Reward/getTypeName")
    Call<ResultDO<RewardTypeBean>> getTypeName(
    );

    //悬赏发布接口
    @FormUrlEncoded
    @POST("/api/v1/Reward/release")
    Call<ResultDO<PublicRewardBean>> releaseReward(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("type_name") String type_name,
            @Field("title") String title,
            @Field("detail") String detail,
            @Field("amount") String amount,
            @Field("number") int number
    );

    @FormUrlEncoded
    @POST("/api/v1/Reward/rewardPay")
    Call<ResultDO<WeChatPayInfo>> rewardPay(@Header("token") String token,
                                            @Field("user_id") int userId,
                                            @Field("order_sn") String order_sn,
                                            @Field("id") int id,
                                            @Field("type") String type);


    @FormUrlEncoded
    @POST("/api/v1/Reward/rewardPay")
    Call<ResultDO<String>> rewardPayAli(@Header("token") String token,
                                        @Field("user_id") int userId,
                                        @Field("order_sn") String order_sn,
                                        @Field("id") int id,
                                        @Field("type") String type);


    //悬赏列表
    @FormUrlEncoded
    @POST("/api/v1/Reward/getList")
    Call<ResultDO<RewardListBean>> getRewardList(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("page") int page

    );

    //悬赏详情
    @FormUrlEncoded
    @POST("/api/v1/Reward/getDetail")
    Call<ResultDO<RewardDetailBean>> getRewardDetail(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("order_sn") String order_sn

    );


    /**
     * 接悬赏
     */
    @FormUrlEncoded
    @POST("/api/v1/Reward/orderTaking")
    Call<ResultDO<Object>> takeReward(@Header("token") String token,
                                      @Field("user_id") int userId,
                                      @Field("order_sn") String order_sn

    );

    /**
     * 我接单的悬赏
     */
    @FormUrlEncoded
    @POST("/api/v1/Reward/mineOrders")
    Call<ResultDO<TakeRewardBean>> getMyTakeRewardList(@Header("token") String token,
                                                       @Field("user_id") int userId,
                                                       @Field("page") int page);


    /**
     * 我发布的悬赏
     */
    @FormUrlEncoded
    @POST("/api/v1/Reward/mineRelease")
    Call<ResultDO<MyPublicRewardBean>> getMyPublicRewardList(@Header("token") String token,
                                                             @Field("user_id") int userId,
                                                             @Field("page") int page);


    /**
     * 悬赏订单详情
     */
    @FormUrlEncoded
    @POST("/api/v1/Reward/rewardOrders")
    Call<ResultDO<RewardOrderBean>> getRewardOrders(@Header("token") String token,
                                                    @Field("user_id") int userId,
                                                    @Field("order_sn") String order_sn,
                                                    @Field("id") int id);


    @FormUrlEncoded
    @POST("/api/v1/Reward/cancel")
    Call<ResultDO> cancelReward(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("order_sn") String order_sn,
            @Field("id") int id

    );

    @FormUrlEncoded
    @POST("/api/v1/Reward/agreeCancel")
    Call<ResultDO> agreeCancelReward(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("order_sn") String order_sn,
            @Field("id") int id

    );

    @FormUrlEncoded
    @POST("/api/v1/Reward/takingOrderPeople")
    Call<ResultDO<AcceptedOrdersPersonBean>> takingOrderPeople(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("order_sn") String order_sn
    );


    @FormUrlEncoded
    @POST("/api/v1/Reward/refund")
    Call<ResultDO> refundReward(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("order_sn") String order_sn

    );

    @FormUrlEncoded
    @POST("/api/v1/Reward/settlement")
    Call<ResultDO> settlementReward(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("order_sn") String order_sn,
            @Field("id") int id

    );

    //申请客服介入
    @FormUrlEncoded
    @POST("/api/v1/Reward/applicationService")
    Call<ResultDO> applicationRewardService(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("desc") String desc,
            @Field("images") String images,
            @Field("thumb_img") String thumb_img
    );

    //申请详情
    @FormUrlEncoded
    @POST("/api/v1/Reward/serviceInfo")
    Call<ResultDO<GuaranteeServiceInfoBean>> serviceRewardInfo(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("/api/v1/Guarantee/getUserInfo")
    Call<ResultDO<OtherUserInfo>> getGuaranteeUserInfo(@Header("token") String token,
                                                       @Field("user_id") int userId,
                                                       @Field("mobile") String mobile);


    @FormUrlEncoded
    @POST("/api/v1/resource/isPopups")
    Call<ResultDO> isPopups(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id

    );

    @FormUrlEncoded
    @POST("/api/v1/resource/clickPopups")
    Call<ResultDO> clickPopups(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("id") int id,
            @Field("type") int type  //	1：同意 ，2：取消

    );

    //新注册接口，检查验证码
    @FormUrlEncoded
    @POST("/api/v1/checkCode")
    Call<ResultDO<RegisterNewBean>> checkCode(
            @Field("mobile") String mobile,
            @Field("code") int code,
            @Field("lon") String lon,
            @Field("lat") String lat,
            @Field("make") int make,//1:手机验证码，2：微信
            @Field("openid") String openid,
            @Field("unionid") String unionid

    );

    //新注册接口，检查验证码
    @FormUrlEncoded
    @POST("/api/v1/checkCode")
    Call<ResultDO<RegisterNewBean>> checkCode2(
            @Field("mobile") String mobile,
            @Field("code") int code,
            @Field("lon") String lon,
            @Field("lat") String lat,
            @Field("make") int make//1:手机验证码，2：微信

    );


    @POST("/api/v1/getAreaList")
    Call<ResultDO<List<AreaBean>>> getAreaList(
    );


    @FormUrlEncoded
    @POST("/api/v3/user/register")
    Call<ResultDO<RegisterBean>> registerV3(@Field("mobile") String mobile,
                                            @Field("lon") String lon,
                                            @Field("lat") String lat,
                                            @Field("make") int make,
                                            @Field("unionid") String unionid,
                                            @Field("openid") String openid,
                                            @Field("city_pid") int city_pid,
                                            @Field("city") String city
    );


    @POST("/api/v6/indexConfig/getConfig")
    Call<ResultDO<MainBean>> getNewIndexList(
    );

    @FormUrlEncoded
    @POST("/api/v1/resource/refreshToken")
    Call<ResultDO> refreshToken(
            @Header("token") String token,
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("/api/v1/resource/releaseCheck")
    Call<ResultDO> releaseCheck(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("p_id") int p_id
    );


    @POST("/api/v1/Activity/columnList")
    Call<ResultDO<ActivityColumnListBean>> getActivityColumnList(
    );


    @FormUrlEncoded
    @POST("/api/v1/Activity/activityList")
    Call<ResultDO<ActivityListBean>> getActivityList(
            @Field("category_id") int category_id,
            @Field("is_duplicates") int is_duplicates,
            @Field("page") int page
    );

    //获取潜在客户
    @FormUrlEncoded
    @POST("/api/v1/user/potentialClient")
    Call<ResultDO<List<RecommendBean>>> getPotentialList(
            @Header("token") String token,
            @Field("user_id") int user_id
    );


    //沟通记录
    @FormUrlEncoded
    @POST("/api/v1/CommunicationRecord/getList")
    Call<ResultDO<CommunicationRecordBean.DataBean>> getCommunicationRecordList(
            @Header("token") String token,
            @Field("user_id") int user_id,
            @Field("time") int time,
            @Field("type") int type,
            @Field("page") int page
    );
}
