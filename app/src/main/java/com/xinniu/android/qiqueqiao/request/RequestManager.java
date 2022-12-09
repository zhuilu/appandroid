package com.xinniu.android.qiqueqiao.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.xinniu.android.qiqueqiao.bean.*;
import com.xinniu.android.qiqueqiao.common.Constants;
import com.xinniu.android.qiqueqiao.request.callback.*;
import com.xinniu.android.qiqueqiao.request.converter.ResultDO;
import com.xinniu.android.qiqueqiao.user.UserInfoHelper;
import com.xinniu.android.qiqueqiao.utils.BitmapUtils;
//import com.xinniu.android.qiqueqiao.utils.IMUtils;
import com.xinniu.android.qiqueqiao.utils.Logger;
import com.xinniu.android.qiqueqiao.utils.StringUtils;
import com.xinniu.android.qiqueqiao.utils.ToastUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lzq on 2017/12/12.
 */

public class RequestManager {

    public final static int FOLLOW_ACTION_FOLLOW = 1;
    public final static int FOLLOW_ACTION_CANCEL_FOLLOW = 2;
    public final static int FOLLOW_ACTION_IS_FOLLOW = 1;
    public final static int FOLLOW_ACTION_UN_FOLLOW = 0;

    public final static String NO_NETWOEK = "网络连接失败";

    public static String vertion = "5.1.0";

    private static RequestManager instance;

    public static RequestManager getInstance() {
        if (instance == null) {
            instance = new RequestManager();
        }
        return instance;
    }

    public void loginByThird(int way, String openId, final LoginCallback callback) {
        Call<ResultDO<UserInfoBean>> call = RetrofitHelper.getInstence().getApiService().loginByThird(way, openId, ApiService.equipment);
        call.enqueue(new Callback<ResultDO<UserInfoBean>>() {
            @Override
            public void onResponse(Call<ResultDO<UserInfoBean>> call, Response<ResultDO<UserInfoBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {

                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<UserInfoBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);

            }
        });
    }

    public void loginByThirdV2(int way, String openId, String union_id, final LoginCallback callback) {
        Call<ResultDO<UserInfoBean>> call = RetrofitHelper.getInstence().getApiService().loginByThirdV2(way, openId, union_id);
        call.enqueue(new Callback<ResultDO<UserInfoBean>>() {
            @Override
            public void onResponse(Call<ResultDO<UserInfoBean>> call, Response<ResultDO<UserInfoBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
//                    UserInfoHelper.getIntance().setUserInfoBean(response.body().getData());
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<UserInfoBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }


    public void loginByPhone(String phone, String pwd, final LoginCallback callback) {
        Call<ResultDO<UserInfoBean>> call = RetrofitHelper.getInstence().getApiService().loginByPhone(phone, pwd, ApiService.equipment, "v1");
        call.enqueue(new Callback<ResultDO<UserInfoBean>>() {
            @Override
            public void onResponse(Call<ResultDO<UserInfoBean>> call, Response<ResultDO<UserInfoBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (!TextUtils.isEmpty(response.body().getData().rong_token)) {
                    UserInfoHelper.getIntance().setRongyunToken(response.body().getData().rong_token);
                }
//                UserInfoHelper.getIntance().setUserInfoBean(response.body().getData());
                callback.onSuccess(response.body().getData());
//                if (response.body() == null) {
//                    callback.onFailed(-1, "登录失败");
//                    return;
//                }
//                Log.i("lzq", "code : "+response.body().getCode());
//                if (response.body().getCode() == ResponseCode.OK) {
//                    Log.i("lzq", ""+response.body().getCode());
//                } else {
//                    callback.onFailed(response.body().getCode(), response.body().getMsg());
//                }
            }

            @Override
            public void onFailure(Call<ResultDO<UserInfoBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);

            }
        });
    }

    public void loginByYzm(String phone, String code, final LoginCallback callback) {

        Call<ResultDO<UserInfoBean>> call = RetrofitHelper.getInstence().getApiService().loginByYzm(phone, code, 1, Constants.lon, Constants.lat);
        call.enqueue(new Callback<ResultDO<UserInfoBean>>() {
            @Override
            public void onResponse(Call<ResultDO<UserInfoBean>> call, Response<ResultDO<UserInfoBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (!TextUtils.isEmpty(response.body().getData().rong_token)) {
                    UserInfoHelper.getIntance().setRongyunToken(response.body().getData().rong_token);
                }
//                UserInfoHelper.getIntance().setUserInfoBean(response.body().getData());
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultDO<UserInfoBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }


    public void getYzm(String phone, final int type, String sign, final YzmCallback callback) {
        Call<YzmBean> call = RetrofitHelper.getInstence().getApiService().getYzm(phone, type, sign, 1);
        call.enqueue(new Callback<YzmBean>() {
            @Override
            public void onResponse(Call<YzmBean> call, Response<YzmBean> response) {

                if (callback == null) {
                    return;
                }
                if (response != null && response.body() != null) {
                    if (response.body().code == ResponseCode.OK) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailed(response.body().msg, response.code());
                    }
                } else {
                    callback.onFailed("服务器繁忙", ResponseCode.NO_NETWORK_CODE);
                }
            }

            @Override
            public void onFailure(Call<YzmBean> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.msg, e.code);
                    return;
                }
                callback.onFailed(NO_NETWOEK, ResponseCode.NO_NETWORK_CODE);
            }
        });
    }

    public void getCategoryByPid(final Integer parent_id, final GetCategoryCallback callback) {
        Call<ResultDO<List<SelectCategory>>> call = RetrofitHelper.getInstence().getApiService().getCategoryByPid(parent_id);
        call.enqueue(new retrofit2.Callback<ResultDO<List<SelectCategory>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<SelectCategory>>> call, Response<ResultDO<List<SelectCategory>>> response) {
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<SelectCategory>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getCategoryByPidForCompany(final int corporate_id, final GetCategoryCallback callback) {
        Call<ResultDO<List<SelectCategory>>> call = RetrofitHelper.getInstence().getApiService().getCategoryByPidForCompany(corporate_id);
        call.enqueue(new retrofit2.Callback<ResultDO<List<SelectCategory>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<SelectCategory>>> call, Response<ResultDO<List<SelectCategory>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<SelectCategory>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void reSettingPwd(String mobile, String code, String pwd, final ReSettingPwdCallback callback) {
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().reSettingPwd(mobile, code, pwd);
        call.enqueue(new retrofit2.Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getCityList(final GetCityListCallback callback) {
        Call<ResultDO<CityListBean>> call = RetrofitHelper.getInstence().getApiService().getCityList();
        call.enqueue(new retrofit2.Callback<ResultDO<CityListBean>>() {
            @Override
            public void onResponse(Call<ResultDO<CityListBean>> call, Response<ResultDO<CityListBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<CityListBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getResourceInfo(int id, final GetResouceInfoCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<ResouceInfoBean>> call = RetrofitHelper.getInstence().getApiService().getResourceInfo(id, userId, token);
        call.enqueue(new retrofit2.Callback<ResultDO<ResouceInfoBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ResouceInfoBean>> call, Response<ResultDO<ResouceInfoBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ResouceInfoBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    if (e.code == 220) {
                        callback.onUndercarriage(e.data);
                    }
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getCoopDetail(int id, int page, final GetCoopDetailCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<CoopDetailBean>> call = RetrofitHelper.getInstence().getApiService().getCoopDetail(id, userId, token, page);
        call.enqueue(new Callback<ResultDO<CoopDetailBean>>() {
            @Override
            public void onResponse(Call<ResultDO<CoopDetailBean>> call, Response<ResultDO<CoopDetailBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<CoopDetailBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    if (e.code == 220) {
                        callback.onUndercarriage(e.data);
                    }
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }

    public void getServiceDetails(int id, final GetServiceDetailCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<ServiceDetailBean>> call = RetrofitHelper.getInstence().getApiService().getServiceDetails(id, userId);
        call.enqueue(new Callback<ResultDO<ServiceDetailBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ServiceDetailBean>> call, Response<ResultDO<ServiceDetailBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ServiceDetailBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    if (e.code == 220) {
                        callback.onUndercarriage(e.data);
                    }
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }

    public void getScreen(final Integer category, final GetCategoryCallback callback) {
        Call<ResultDO<List<SelectCategory>>> call = RetrofitHelper.getInstence().getApiService().getScreen(category);
        call.enqueue(new retrofit2.Callback<ResultDO<List<SelectCategory>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<SelectCategory>>> call, Response<ResultDO<List<SelectCategory>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    List<SelectCategory> list = new ArrayList<SelectCategory>();

//                    if (category == 2){
//                        SelectCategory category1 = new SelectCategory();
//                        category1.setId(0);
//                        category1.setName("不限");
//                        list.add(category1);
//                    }
                    list.addAll(response.body().getData());
                    callback.onSuccess(list);
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<SelectCategory>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getSeenUser(int page, final GetSeenCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();

        Call<ResultDO<SeenBean>> call = RetrofitHelper.getInstence().getApiService().getSeenUser(page, userId, token);
        call.enqueue(new retrofit2.Callback<ResultDO<SeenBean>>() {
            @Override
            public void onResponse(Call<ResultDO<SeenBean>> call, Response<ResultDO<SeenBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<SeenBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
//                callback.onFailed(-1000, (((ResultException) t).msg));
            }
        });
    }

    public void getCircleList(final RequestCallback<List<CircleBean>> callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<List<CircleBean>>> call =
                RetrofitHelper.getInstence().getApiService().getCircleList(token, userId);
//        Call<ResultDO<List<CircleBean>>> call =
//                RetrofitHelper.getInstence().getApiService().getCircleList();
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<List<CircleBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<CircleBean>>> call, Response<ResultDO<List<CircleBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<List<CircleBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    /**
     * 获取我的圈子列表
     *
     * @param callback
     */
    public void getMyCircleList(final RequestCallback<List<MyCircleBean>> callback) {
//        int userId = UserInfoHelper.getIntance().getUserId();
//        String token = UserInfoHelper.getIntance().getToken();
//        Call<ResultDO<List<MyCircleBean>>> call =
//                RetrofitHelper.getInstence().getApiService().getMyCircleList(token, userId);
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<List<MyCircleBean>>> call =
                RetrofitHelper.getInstence().getApiService().getMyCircleList(token, userId);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<List<MyCircleBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<MyCircleBean>>> call, Response<ResultDO<List<MyCircleBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<List<MyCircleBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    /**
     * 加入圈子
     *
     * @param circleId
     * @param callback
     */
    public void joinCircle(int circleId, final RequestCallback<String> callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<Object>> call =
                RetrofitHelper.getInstence().getApiService().joinCircle(token, userId, circleId);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData().toString());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    /**
     * 用户是否在圈子中
     *
     * @param circleId
     * @param callback
     */
    public void userBelongCircle(int circleId, final RequestCallback<String> callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<Object>> call =
                RetrofitHelper.getInstence().getApiService().userBelongCircle(token, userId, circleId);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData().toString());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    /**
     * 退出圈子
     *
     * @param circleId
     * @param callback
     */
    public void quitCircle(int circleId, final RequestCallback<String> callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO> call =
                RetrofitHelper.getInstence().getApiService().quitCircle(token, userId, circleId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getMsg());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void getCareList(int page, final GetCareListCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();

        Call<ResultDO<CareBean>> call = RetrofitHelper.getInstence().getApiService().getCareList(page, userId, token);
        call.enqueue(new retrofit2.Callback<ResultDO<CareBean>>() {
            @Override
            public void onResponse(Call<ResultDO<CareBean>> call, Response<ResultDO<CareBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<CareBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getResourceItem(int page, int cityId, String keyword, String keyword_range, int sort_order, String query_id, String industry, int p_id, int from, int time, int is_transaction, final GetResourceListCallback callback) {
        //  if (UserInfoHelper.getIntance().isLogin()) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<IndexNewBean>> call = RetrofitHelper.getInstence().getApiService().getResourceByUserId(userId, token, page, cityId, keyword, keyword_range, sort_order, query_id, industry, p_id, from, is_transaction, time);
        call.enqueue(new retrofit2.Callback<ResultDO<IndexNewBean>>() {
            @Override
            public void onResponse(Call<ResultDO<IndexNewBean>> call, Response<ResultDO<IndexNewBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {

                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<IndexNewBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
//        } else {
//            Call<ResultDO<IndexNewBean>> call = RetrofitHelper.getInstence().getApiService().getResource(page, cityId, keyword, sort_order, query_id, industry, from, time);
//            call.enqueue(new retrofit2.Callback<ResultDO<IndexNewBean>>() {
//                @Override
//                public void onResponse(Call<ResultDO<IndexNewBean>> call, Response<ResultDO<IndexNewBean>> response) {
//                    if (response == null) {
//                        return;
//                    }
//                    if (response.body() == null) {
//                        return;
//                    }
//                    if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
//
//                        callback.onSuccess(response.body().getData());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResultDO<IndexNewBean>> call, Throwable t) {
//                    if (t instanceof ResultException) {
//                        ResultException e = (ResultException) t;
//                        callback.onFailed(e.code, e.msg);
//                        return;
//                    }
//                    callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
//                }
//            });
        //   }

    }


    //获取新资源推荐
    public void getNewResourceItem(int page, final GetResourceListCallback callback) {
        String mToken = UserInfoHelper.getIntance().getToken();
        int user_Id = UserInfoHelper.getIntance().getUserId();

        Call<ResultDO<IndexNewBean>> call = RetrofitHelper.getInstence().getApiService().getNewResource(user_Id, mToken, page, 0, "", 34, "", "");
        call.enqueue(new retrofit2.Callback<ResultDO<IndexNewBean>>() {
            @Override
            public void onResponse(Call<ResultDO<IndexNewBean>> call, Response<ResultDO<IndexNewBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<IndexNewBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    //通过公司Id获取公司发布的资源
    public void getResourceByCorporateId(int page, int corporate_id, final GetResourceListCallback callback) {
        Call<ResultDO<IndexNewBean>> call = RetrofitHelper.getInstence().getApiService().getResourceByCorporateId(page, 0, "", 0, "", "", corporate_id);
        call.enqueue(new retrofit2.Callback<ResultDO<IndexNewBean>>() {
            @Override
            public void onResponse(Call<ResultDO<IndexNewBean>> call, Response<ResultDO<IndexNewBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<IndexNewBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void exchangeChatInfo(int info, int toUserId, final ExchageChatInfoCallback callback) {

        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Log.i("uuuuuuuuuuu", "info=" + info + "toUserId=" + toUserId + "userId=" + userId + "token=" + token);

        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().exchangeChatInfo(info, userId, toUserId, token);
        call.enqueue(new retrofit2.Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void talkToUser(int resouceId, int toUserId, final TalkToUserCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<TalkToUserBean>> call = RetrofitHelper.getInstence().getApiService().talkToUserV2(resouceId, userId, toUserId, token, 0);

        call.enqueue(new retrofit2.Callback<ResultDO<TalkToUserBean>>() {
            @Override
            public void onResponse(Call<ResultDO<TalkToUserBean>> call, Response<ResultDO<TalkToUserBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailue(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<TalkToUserBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
                callback.onFailue(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }


    public void talkToUserService(int serviceId, int toUserId, final TalkToUserCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<TalkToUserBean>> call = RetrofitHelper.getInstence().getApiService().serviceTalkToUserV2(serviceId, userId, toUserId, token, 0);

        call.enqueue(new retrofit2.Callback<ResultDO<TalkToUserBean>>() {
            @Override
            public void onResponse(Call<ResultDO<TalkToUserBean>> call, Response<ResultDO<TalkToUserBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailue(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<TalkToUserBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
                callback.onFailue(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }


    public void talkToUserNOResouceId(int toUserId, final TalkToUserCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<TalkToUserBean>> call = RetrofitHelper.getInstence().getApiService().talkToUserNoResourceId(userId, toUserId, token, 0);

        call.enqueue(new retrofit2.Callback<ResultDO<TalkToUserBean>>() {
            @Override
            public void onResponse(Call<ResultDO<TalkToUserBean>> call, Response<ResultDO<TalkToUserBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailue(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<TalkToUserBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
                callback.onFailue(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getVipList(final GetVipListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        final Call<ResultDO<VipListBean>> call = RetrofitHelper.getInstence().getApiService().getVipList(token, userId, 2);
        call.enqueue(new retrofit2.Callback<ResultDO<VipListBean>>() {
            @Override
            public void onResponse(Call<ResultDO<VipListBean>> call, Response<ResultDO<VipListBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<VipListBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void confirmExchange(int action, int recordId) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().confirmExchange(token, userId, recordId, action);
        call.enqueue(new retrofit2.Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
//                    callback.onSuccess(response.body().getData());
                } else {
//                    callback.onFailed(response.body().getCode(),response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
//                ResultException e = (ResultException) t;
//                callback.onFailed(e.code, e.msg);
            }
        });
    }

    public void bugVip(int id, int mode, final VipBugCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<OrderInfoBean>> call = RetrofitHelper.getInstence().getApiService().bugVip(token, userId, id, 2, mode);
        call.enqueue(new retrofit2.Callback<ResultDO<OrderInfoBean>>() {
            @Override
            public void onResponse(Call<ResultDO<OrderInfoBean>> call, Response<ResultDO<OrderInfoBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    callback.onFailed(-1, "创建订单失败");
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<OrderInfoBean>> call, Throwable t) {
//                ResultException e = (ResultException) t;
//                callback.onFailed(e.code, e.msg);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void bugVipV3(int id, final VipBugCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<OrderInfoBean>> call = RetrofitHelper.getInstence().getApiService().bugBipV3(token, userId, id, 2);
        call.enqueue(new Callback<ResultDO<OrderInfoBean>>() {
            @Override
            public void onResponse(Call<ResultDO<OrderInfoBean>> call, Response<ResultDO<OrderInfoBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    callback.onFailed(-1, "创建订单失败");
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<OrderInfoBean>> call, Throwable t) {
//                ResultException e = (ResultException) t;
//                callback.onFailed(e.code, e.msg);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });

    }


    public void sendResource(int provide_top, String provide_attr, String provide_remark, int need_top, String need_attr, String need_remark, String cooperation_mode, final ResourceReleaseCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<ResourceReleaseBean>> call = RetrofitHelper.getInstence().getApiService().sendResource(token, userId, provide_top, provide_attr, provide_remark, need_top, need_attr, need_remark, cooperation_mode);
        call.enqueue(new retrofit2.Callback<ResultDO<ResourceReleaseBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ResourceReleaseBean>> call, Response<ResultDO<ResourceReleaseBean>> response) {
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ResourceReleaseBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void sendResourceWithDes(int provide_top, String provide_attr, String provide_remark, int need_top, String need_attr, String need_remark, String cooperation_mode, String provideDescribe, String needDescribe, final ResourceReleaseCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<ResourceReleaseBean>> call = RetrofitHelper.getInstence().getApiService().sendResourceWithDes(token, userId, provide_top, provide_attr, provide_remark, need_top, need_attr, need_remark, cooperation_mode, provideDescribe, needDescribe);
        call.enqueue(new retrofit2.Callback<ResultDO<ResourceReleaseBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ResourceReleaseBean>> call, Response<ResultDO<ResourceReleaseBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ResourceReleaseBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void sendResourceV2(int provide_top, String provide_attr, String provide_remark, int need_top, String need_attr, String need_remark, String cooperation_mode, String provideDescribe, String needDescribe, String provideImg, String needImg, final ResourceReleaseCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<ResourceReleaseBean>> call = RetrofitHelper.getInstence().getApiService().sendResourceV2(token, userId, provide_top, provide_attr, provide_remark, provideDescribe, provideImg, need_top, need_attr, need_remark, needDescribe, needImg, cooperation_mode);
        call.enqueue(new retrofit2.Callback<ResultDO<ResourceReleaseBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ResourceReleaseBean>> call, Response<ResultDO<ResourceReleaseBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ResourceReleaseBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }


    public void userFollow(int targetId, int action, final UserFolloweCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<UserFollowBean>> call = RetrofitHelper.getInstence().getApiService().userFollow(token, userId, targetId, action);
        call.enqueue(new retrofit2.Callback<ResultDO<UserFollowBean>>() {
            @Override
            public void onResponse(Call<ResultDO<UserFollowBean>> call, Response<ResultDO<UserFollowBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultDO<UserFollowBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

//    public void register(String username, String pwd, String code, String openId, int make, String nickname, String sex, final RegisterCallback callback) {
//        Call<ResultDO<RegisterBean>> call = RetrofitHelper.getInstence().getApiService().register(username, pwd, code, openId, make, ApiService.equipment, nickname, sex);
//        call.enqueue(new Callback<ResultDO<RegisterBean>>() {
//            @Override
//            public void onResponse(Call<ResultDO<RegisterBean>> call, Response<ResultDO<RegisterBean>> response) {
//                callback.onSuccess(response.body().getData());
//            }
//
//            @Override
//            public void onFailure(Call<ResultDO<RegisterBean>> call, Throwable t) {
//                if (t instanceof ResultException) {
//                    ResultException e = (ResultException) t;
//                    callback.onFailed(e.code, e.msg);
//                    return;
//                }
//                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
//            }
//        });
//    }

    public void registerV2(String username, String code, int make, String nickname, String openid, String unionid, String head_pic, int sex, final RegisterCallback callback) {
        Call<ResultDO<RegisterBean>> call = RetrofitHelper.getInstence().getApiService().registerV2(username, code, make, nickname, openid, unionid, head_pic, sex, Constants.lon, Constants.lat);
        call.enqueue(new Callback<ResultDO<RegisterBean>>() {
            @Override
            public void onResponse(Call<ResultDO<RegisterBean>> call, Response<ResultDO<RegisterBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }

            }

            @Override
            public void onFailure(Call<ResultDO<RegisterBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }


    public void showUserInfo(int target_id, final GetOtherUserInfoCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<OtherUserInfo>> call = RetrofitHelper.getInstence().getApiService().showUserInfo(userId, target_id);
        call.enqueue(new Callback<ResultDO<OtherUserInfo>>() {
            @Override
            public void onResponse(Call<ResultDO<OtherUserInfo>> call, Response<ResultDO<OtherUserInfo>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                String userId = String.valueOf(response.body().getData().getUser_id());
                String name = response.body().getData().getRealname();
                String headUrl = response.body().getData().getHead_pic();
                if (userId != null && name != null && headUrl != null) {
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(userId, name, Uri.parse(headUrl)));
                }
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultDO<OtherUserInfo>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }


    /**
     * 查看用户信息
     */
    public void getUserInfo(final RequestCallback<DetailedUserInfoBean> callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<DetailedUserInfoBean>> call =
                RetrofitHelper.getInstence().getApiService().getUserInfo(token, userId);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<DetailedUserInfoBean>>() {
            @Override
            public void onResponse(Call<ResultDO<DetailedUserInfoBean>> call, Response<ResultDO<DetailedUserInfoBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        UserInfoHelper.getIntance().setHeadUrl(response.body().getData().getHead_pic());
                        if (!TextUtils.isEmpty(response.body().getData().getCompany())) {
                            UserInfoHelper.getIntance().setInfoCompany(response.body().getData().getCompany());
                        }
                        if (!TextUtils.isEmpty(response.body().getData().getPosition())) {
                            UserInfoHelper.getIntance().setInfoPosition(response.body().getData().getPosition());
                        }
                        UserInfoHelper.getIntance().setInfoIsV(response.body().getData().getIs_v());
                        if (!TextUtils.isEmpty(response.body().getData().getNickname())) {
                            UserInfoHelper.getIntance().setInfoNickName(response.body().getData().getNickname());
                        }
                        if (!TextUtils.isEmpty(response.body().getData().getRealname())) {
                            UserInfoHelper.getIntance().setInfoRealname(response.body().getData().getRealname());
                        }

                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<DetailedUserInfoBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void getUserInfo(int userId, String token, final RequestCallback<DetailedUserInfoBean> callback) {
//        int userId = UserInfoHelper.getIntance().getUserId();
//        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<DetailedUserInfoBean>> call =
                RetrofitHelper.getInstence().getApiService().getUserInfo(token, userId);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<DetailedUserInfoBean>>() {
            @Override
            public void onResponse(Call<ResultDO<DetailedUserInfoBean>> call, Response<ResultDO<DetailedUserInfoBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        UserInfoHelper.getIntance().setHeadUrl(response.body().getData().getHead_pic());
                        if (!TextUtils.isEmpty(response.body().getData().getCompany())) {
                            UserInfoHelper.getIntance().setInfoCompany(response.body().getData().getCompany());
                        }
                        if (!TextUtils.isEmpty(response.body().getData().getPosition())) {
                            UserInfoHelper.getIntance().setInfoPosition(response.body().getData().getPosition());
                        }
                        UserInfoHelper.getIntance().setInfoIsV(response.body().getData().getIs_v());
                        if (!TextUtils.isEmpty(response.body().getData().getNickname())) {
                            UserInfoHelper.getIntance().setInfoNickName(response.body().getData().getNickname());
                        }
                        if (!TextUtils.isEmpty(response.body().getData().getRealname())) {
                            UserInfoHelper.getIntance().setInfoRealname(response.body().getData().getRealname());
                        }

                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<DetailedUserInfoBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    /**
     * 修改用户信息
     */
    public void editUserInfo(int type, DetailedUserInfoBean bean, final RequestCallback<String> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();

        // TODO: 2017/12/20 行业Id
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().editUserInfo(token, userId, type, bean.getHead_pic()
                , bean.getRealname(), bean.getCompany(), bean.getPosition(), bean.getMobile(), bean.getSex(), bean.getWechat(), bean.getCompany_industry(), bean.getCity(), bean.getCorporate_name(), bean.getThumb_img());

        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        UserInfoHelper.getIntance().setPerfect(true);
                        callback.onSuccess(response.body().getData().toString());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    /**
     * 修改用户信息
     */
    public void editUserInfo(String token, final int userId, final int type, final DetailedUserInfoBean bean, final AllResultDoCallback callback) {
//        String token = UserInfoHelper.getIntance().getToken();
//        int userId = UserInfoHelper.getIntance().getUserId();

        // TODO: 2017/12/20 行业Id
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().editUserInfoV2(token, userId, type, bean.getHead_pic()
                , bean.getRealname(), bean.getCorporate_name(), bean.getPosition(), bean.getThumb_img());


        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        UserInfoHelper.getIntance().setPerfect(true);
                        callback.onSuccess(response.body().getData().toString());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
            }
        });
    }

    /**
     * 个人中心
     */
    public void center(final RequestCallback<CenterBean> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Log.d("myToken", token);
        Call<ResultDO<CenterBean>> call = RetrofitHelper.getInstence().getApiService().center(token, userId);
        if (callback != null) {
            callback.requestStart(call);
        }
        call.enqueue(new Callback<ResultDO<CenterBean>>() {
            @Override
            public void onResponse(Call<ResultDO<CenterBean>> call, Response<ResultDO<CenterBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        if (response.body().getData() != null) {
                            IMUtils.setTop(String.valueOf(response.body().getData().getUsers().getF_id()));
                        }
                        UserInfoHelper.getIntance().setCenterBean(response.body().getData());
                        UserInfoHelper.getIntance().setF_id(response.body().getData().getUsers().getF_id());
                        if (callback == null) {
                            return;
                        }
                        callback.onSuccess(response.body().getData());
                    } else {
                        if (callback == null) {
                            return;
                        }
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    if (callback == null) {
                        return;
                    }
                    callback.onFailed(response.code(), response.message());
                }
                if (callback == null) {
                    return;
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<CenterBean>> call, Throwable t) {
                if (callback != null) {
                    if (t instanceof ResultException) {
                        ResultException e = (ResultException) t;
                        callback.onFailed(e.code, e.msg);
                        callback.requestEnd();
                        return;
                    }
                    callback.onFailed(-1000, NO_NETWOEK);
                    callback.requestEnd();
                }
            }
        });
    }


    public void updateBase64(String path, final RequestCallback<UploadBean> callback) {

        //部分手机内存溢出处理
        Bitmap bm = null;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 1;
            bm = BitmapFactory.decodeFile(path, opts);
        } catch (OutOfMemoryError err) {
            bm = BitmapUtils.compressScale(path);

        }
        if (BitmapUtils.imgDegree(path) == 0) {

        } else {
            bm = BitmapUtils.rotateBitmap(BitmapUtils.imgDegree(path), bm);
        }

        String base64bm = BitmapUtils.bitmapToBase64(bm);
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<UploadBean>> call = RetrofitHelper.getInstence().getApiService().uploadResouceImg(token, userId, "resources", base64bm);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<UploadBean>>() {
            @Override
            public void onResponse(Call<ResultDO<UploadBean>> call, Response<ResultDO<UploadBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<UploadBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }


    public void update(String base64, final RequestCallback<UploadBean> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<UploadBean>> call = RetrofitHelper.getInstence().getApiService().upload(token, userId, base64);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<UploadBean>>() {
            @Override
            public void onResponse(Call<ResultDO<UploadBean>> call, Response<ResultDO<UploadBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<UploadBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void pwd(String oldPwd, String newPwd, final RequestCallback<String> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();

        Call<ResultDO<Object>> call = RetrofitHelper.getInstence().getApiService().pwd(token, userId, oldPwd, newPwd);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData().toString());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void cmsBind(String phone, String code, final RequestCallback<String> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<Object>> call = RetrofitHelper.getInstence().getApiService().cmsBind(token, userId, phone, code);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData().toString());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void card(String picture, final RequestCallback<String> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<Object>> call = RetrofitHelper.getInstence().getApiService().card(token, userId, picture);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData().toString());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void getMyPush(int page, final RequestCallback<MyPushBean> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<MyPushBean>> call = RetrofitHelper.getInstence().getApiService().getMyPush(token, userId, userId, page, 1);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<MyPushBean>>() {
            @Override
            public void onResponse(Call<ResultDO<MyPushBean>> call, Response<ResultDO<MyPushBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<MyPushBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }


    public void getMyServicePush(int page, final RequestCallback<MyServicePushBean> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<MyServicePushBean>> call = RetrofitHelper.getInstence().getApiService().getMyServiceList(token, userId, page);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<MyServicePushBean>>() {
            @Override
            public void onResponse(Call<ResultDO<MyServicePushBean>> call, Response<ResultDO<MyServicePushBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<MyServicePushBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void feedBack(String text, String images, String thumb_img, String mobile, final RequestCallback<String> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<Object>> call = RetrofitHelper.getInstence().getApiService().feedBack(userId, text, images, thumb_img, mobile);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData().toString());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void delResource(int resId, final RequestCallback<String> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<Object>> call = RetrofitHelper.getInstence().getApiService().delResource(token, userId, resId);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData().toString());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }


    public void operateService(int id, int operate_type, final RequestCallback<String> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<Object>> call = RetrofitHelper.getInstence().getApiService().operateService(token, userId, operate_type, id);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void delCase(int id, final RequestCallback<String> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<Object>> call = RetrofitHelper.getInstence().getApiService().delCase(token, userId, id);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData().toString());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void delResourceV2(int resId, final RequestCallback<String> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<Object>> call = RetrofitHelper.getInstence().getApiService().deleteV2Resource(token, userId, resId);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData().toString());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void getUserResources(int uid, int page, final GetUserResourcesCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<GetUserResourcesBean>> call = RetrofitHelper.getInstence().getApiService().getUserResources(token, uid, userId, page, 0);

        call.enqueue(new Callback<ResultDO<GetUserResourcesBean>>() {
            @Override
            public void onResponse(Call<ResultDO<GetUserResourcesBean>> call, Response<ResultDO<GetUserResourcesBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() != null) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
                }
            }

            @Override
            public void onFailure(Call<ResultDO<GetUserResourcesBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void member(final RequestCallback<MemberCenterBean.VipBean> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<MemberCenterBean.VipBean>> call = RetrofitHelper.getInstence().getApiService().member(token, userId, 2);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<MemberCenterBean.VipBean>>() {
            @Override
            public void onResponse(Call<ResultDO<MemberCenterBean.VipBean>> call, Response<ResultDO<MemberCenterBean.VipBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<MemberCenterBean.VipBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void upGrade(final RequestCallback<VipListBean> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<VipListBean>> call = RetrofitHelper.getInstence().getApiService().upGrade(token, userId, 2);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<VipListBean>>() {
            @Override
            public void onResponse(Call<ResultDO<VipListBean>> call, Response<ResultDO<VipListBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<VipListBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void isPerfectR(final CommonCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().perfect(token, userId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    UserInfoHelper.getIntance().setPerfect(false);
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body());
                    UserInfoHelper.getIntance().setPerfect(true);
                } else {
                    UserInfoHelper.getIntance().setPerfect(false);
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }


    public void isPerfect(final CommonCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().perfectV3(token, userId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    UserInfoHelper.getIntance().setPerfect(false);
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body());
                    UserInfoHelper.getIntance().setPerfect(true);
                } else {
                    UserInfoHelper.getIntance().setPerfect(false);
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void sendCheck(final CommonCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().sendCheck(token, userId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    UserInfoHelper.getIntance().setPerfect(false);
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body());
                    UserInfoHelper.getIntance().setPerfect(true);
                } else {
                    UserInfoHelper.getIntance().setPerfect(false);
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getHelp(final RequestCallback<List<HelpBean>> callback) {
        Call<ResultDO<List<HelpBean>>> call = RetrofitHelper.getInstence().getApiService().getHelp();
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<List<HelpBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<HelpBean>>> call, Response<ResultDO<List<HelpBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<List<HelpBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void getEditResourceInfo(int resId, final RequestCallback<EditResourceBean> callback) {
        Call<ResultDO<EditResourceBean>> call = RetrofitHelper.getInstence().getApiService().getEditResourceInfo(resId);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<EditResourceBean>>() {
            @Override
            public void onResponse(Call<ResultDO<EditResourceBean>> call, Response<ResultDO<EditResourceBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<EditResourceBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void getRePublishNew(int resId, final RequestCallback<RePublishBean> callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<RePublishBean>> call = RetrofitHelper.getInstence().getApiService().rePublishNew(resId, userId);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<RePublishBean>>() {
            @Override
            public void onResponse(Call<ResultDO<RePublishBean>> call, Response<ResultDO<RePublishBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<RePublishBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });


    }


    public void editResourceInfo(EditResourceBean bean, final RequestCallback<Object> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<Object>> call = RetrofitHelper.getInstence().getApiService().editResource(token, userId, bean.getId(),
                bean.getProvide_top(), bean.getProvide().replace(",", "_"), bean.getProvide_remark(),
                bean.getNeed_top(), bean.getNeed().replace(",", "_"), bean.getNeed_remark(),
                bean.getCooperation_mode(), bean.getProvide_describe(), bean.getNeed_describe(), bean.getProvide_img(), bean.getNeed_img());
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void editResourceInfoV3(RePublishBean bean, final RequestCallback<Object> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<Object>> call = RetrofitHelper.getInstence().getApiService().editResourceV3(token, userId,
                bean.getId(), bean.getTitle(), bean.getProvide_top(),
                bean.getProvide_attr(), bean.getProvide_remark(),
                bean.getNeed_top(), bean.getNeed_attr(), bean.getNeed_remark(),
                bean.getCooperation_mode(), bean.getProvide_describe(),
                bean.getNeed_describe(), bean.getImages());
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void editResourceInfoV4(RePublishBean bean, final ResourceReleaseCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<ResourceReleaseBean>> call = RetrofitHelper.getInstence().getApiService().editResourceV4(token, userId, bean.getId(), bean.getTitle(), bean.getProvide_attr(), bean.getProvide_remark(), bean.getNeed_attr(), bean.getNeed_remark(), bean.getCooperation_mode(), bean.getProvide_describe(), bean.getNeed_describe(), bean.getImages(), bean.getCity());
        call.enqueue(new Callback<ResultDO<ResourceReleaseBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ResourceReleaseBean>> call, Response<ResultDO<ResourceReleaseBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ResourceReleaseBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
            }
        });

    }


    public void payAli(String orderSn, String type, final PayCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<String>> call = RetrofitHelper.getInstence().getApiService().payAli(token, userId, orderSn, type);
        call.enqueue(new Callback<ResultDO<String>>() {
            @Override
            public void onResponse(Call<ResultDO<String>> call, Response<ResultDO<String>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                response.body().getCode();
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultDO<String>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void weChatPay(String orderSn, String type, final WechatPayCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<WeChatPayInfo>> call = RetrofitHelper.getInstence().getApiService().wechatPay(token, userId, orderSn, type);
        call.enqueue(new Callback<ResultDO<WeChatPayInfo>>() {
            @Override
            public void onResponse(Call<ResultDO<WeChatPayInfo>> call, Response<ResultDO<WeChatPayInfo>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultDO<WeChatPayInfo>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void receive(int id, final CommonCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().receive(token, userId, 2, id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void blacklistBehavior(int blackUserId, int action, final CommonCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().blacklistBehavior(token, userId, blackUserId, action);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {

                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    /**
     * 绑定解绑微信
     */
    public void bind(int bind, int make, String openId, final RequestCallback<Object> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<Object>> call = RetrofitHelper.getInstence().getApiService().bind(token, userId, bind, make, openId);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void getMessageList(int page, final GetSystemMsgCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<List<SystemMsgBean>>> call = RetrofitHelper.getInstence().getApiService().getMessageList(token, userId, page);
        call.enqueue(new Callback<ResultDO<List<SystemMsgBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<SystemMsgBean>>> call, Response<ResultDO<List<SystemMsgBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {

                    return;
                }
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultDO<List<SystemMsgBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getNews(final NewsCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<NewsBean>> call = RetrofitHelper.getInstence().getApiService().getNews(token, userId);
        call.enqueue(new Callback<ResultDO<NewsBean>>() {
            @Override
            public void onResponse(Call<ResultDO<NewsBean>> call, Response<ResultDO<NewsBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    callback.onFailed(ResponseCode.NO_NETWORK_CODE, "获取数据失败");
                    return;
                }
                if (response.body().getData() == null) {
                    callback.onFailed(ResponseCode.NO_NETWORK_CODE, "获取数据失败");
                    return;
                }
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultDO<NewsBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void resourceShield(int resourceId, int enumId, final CommonCallback commonCallback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().resourceShield(token, userId, resourceId, enumId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    commonCallback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    commonCallback.onFailed(e.code, e.msg);
                    return;
                }
                commonCallback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getAppVertion(final GetAppVertionCallback callback) {
        Call<ResultDO<AppVertion>> call = RetrofitHelper.getInstence().getApiService().getAppVersion("android", vertion);
        call.enqueue(new Callback<ResultDO<AppVertion>>() {
            @Override
            public void onResponse(Call<ResultDO<AppVertion>> call, Response<ResultDO<AppVertion>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
//                    if (StringUtils.vertionToInt(response.body().getData().getVersion()) > StringUtils.vertionToInt(vertion)) {
//                        callback.onSuccess(response.body().getData());
//                    }
                    //判断每位版本号3.6.0  与3.10.0
                    String[] data1 = response.body().getData().getVersion().split("\\.");
                    String[] data2 = vertion.split("\\.");
                    if (Integer.parseInt(data1[0]) > Integer.parseInt(data2[0])) {
                        callback.onSuccess(response.body().getData());
                    } else if (Integer.parseInt(data1[0]) == Integer.parseInt(data2[0])) {
                        if (Integer.parseInt(data1[1]) > Integer.parseInt(data2[1])) {
                            callback.onSuccess(response.body().getData());
                        } else if (Integer.parseInt(data1[1]) == Integer.parseInt(data2[1])) {
                            if (Integer.parseInt(data1[2]) > Integer.parseInt(data2[2])) {
                                callback.onSuccess(response.body().getData());
                            }
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<ResultDO<AppVertion>> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

//

    public void addTagV2(String name, int p_id, int is_type, final AddTagCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<SeclectCateBean.UserCategoryBean>> call = RetrofitHelper.getInstence().getApiService().addCategoryV2(token, userId, name, p_id, is_type);
        call.enqueue(new Callback<ResultDO<SeclectCateBean.UserCategoryBean>>() {

            @Override
            public void onResponse(Call<ResultDO<SeclectCateBean.UserCategoryBean>> call, Response<ResultDO<SeclectCateBean.UserCategoryBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<SeclectCateBean.UserCategoryBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
//            }
            }
        });

    }


    public void getUserCategory(int parent1, int parent2, final GetUserCategoryCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<List<SelectCategory>>> call = RetrofitHelper.getInstence().getApiService().getUserCategory(token, userId, parent1, parent2);
        call.enqueue(new Callback<ResultDO<List<SelectCategory>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<SelectCategory>>> call, Response<ResultDO<List<SelectCategory>>> response) {
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<SelectCategory>>> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getUserCategoryV2(final GetUserCategoryCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<List<SelectCategory>>> call = RetrofitHelper.getInstence().getApiService().getUserCategoryV2(token, userId);
        call.enqueue(new Callback<ResultDO<List<SelectCategory>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<SelectCategory>>> call, Response<ResultDO<List<SelectCategory>>> response) {
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<SelectCategory>>> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }


    public void deleteTag(int id, final CommonCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().deleteCategory(token, userId, id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void analysis(int provideTop, String provideAttr, int needTop, String needAttr, final AnalysisCallback callback) {
        provideAttr = StringUtils.reverseString(provideAttr);
        needAttr = StringUtils.reverseString(needAttr);
        Call<ResultDO<AnalysisBean>> call = RetrofitHelper.getInstence().getApiService().analysis(provideTop, provideAttr, needTop, needAttr);
        call.enqueue(new Callback<ResultDO<AnalysisBean>>() {
            @Override
            public void onResponse(Call<ResultDO<AnalysisBean>> call, Response<ResultDO<AnalysisBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<AnalysisBean>> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void refresh(int type, int resourcesId, final CommonCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().refresh(token, userId, resourcesId, type);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void scanQrcode(String code, final QrCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<QrcodeBean>> call = RetrofitHelper.getInstence().getApiService().scanQrcode(token, userId, code);
        call.enqueue(new Callback<ResultDO<QrcodeBean>>() {
            @Override
            public void onResponse(Call<ResultDO<QrcodeBean>> call, Response<ResultDO<QrcodeBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK, "");
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<QrcodeBean>> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg, e.data);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK, "");
            }
        });
    }

    public void getHotSeacrhs(final GetUserCategoryTwoCallback callback) {
        Call<ResultDO<SelectCategoryTwo>> call = RetrofitHelper.getInstence().getApiService().getHotSeacrhs();
        call.enqueue(new Callback<ResultDO<SelectCategoryTwo>>() {
            @Override
            public void onResponse(Call<ResultDO<SelectCategoryTwo>> call, Response<ResultDO<SelectCategoryTwo>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<SelectCategoryTwo>> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void preSeacrhs(String keyword, final PreSearchCallback callback) {
        Call<ResultDO<ResourcesTitleBean>> call = RetrofitHelper.getInstence().getApiService().preSearch(keyword);
        call.enqueue(new Callback<ResultDO<ResourcesTitleBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ResourcesTitleBean>> call, Response<ResultDO<ResourcesTitleBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ResourcesTitleBean>> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }


    public void getCircleInfo(int circle_id, final GetCircleInfoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<CircleInfobean>> call = RetrofitHelper.getInstence().getApiService().getCircleInfo(token, userId, circle_id);
        call.enqueue(new Callback<ResultDO<CircleInfobean>>() {
            @Override
            public void onResponse(Call<ResultDO<CircleInfobean>> call, Response<ResultDO<CircleInfobean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<CircleInfobean>> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getGroupMember(int circle_id, final GetGroupMemberCallback callback) {
        Call<ResultDO<List<MemberInfoBean>>> call = RetrofitHelper.getInstence().getApiService().getGroupMember(circle_id);
        call.enqueue(new Callback<ResultDO<List<MemberInfoBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<MemberInfoBean>>> call, Response<ResultDO<List<MemberInfoBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<MemberInfoBean>>> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getblackList(int circle_id, final GetGroupMemberCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<List<MemberInfoBean>>> call = RetrofitHelper.getInstence().getApiService().getblackList(token, userId, circle_id);
        call.enqueue(new Callback<ResultDO<List<MemberInfoBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<MemberInfoBean>>> call, Response<ResultDO<List<MemberInfoBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<MemberInfoBean>>> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getCircleResource(int page, long circle_id, final GetResourceListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<IndexNewBean>> call = RetrofitHelper.getInstence().getApiService().getCircleResource(token, userId, circle_id, page);
        call.enqueue(new retrofit2.Callback<ResultDO<IndexNewBean>>() {
            @Override
            public void onResponse(Call<ResultDO<IndexNewBean>> call, Response<ResultDO<IndexNewBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<IndexNewBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void bootCircle(String targetId, int circleId, final CommonCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().bootCircle(token, userId, circleId, targetId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void setBlack(int action, String targetId, int circleId, final CommonCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().setBlack(token, userId, circleId, action, targetId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void setNotification(int action, int circleId, final CommonCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().setNotification(token, userId, circleId, action);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void setCircleTop(int action, int circleId, final CommonCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().setCircleTop(token, userId, circleId, action);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getNoticeList(int circle_id, final GetNoticeListCallback callback) {

        Call<ResultDO<List<NoticeBean>>> call = RetrofitHelper.getInstence().getApiService().getNoticeList(circle_id);
        call.enqueue(new retrofit2.Callback<ResultDO<List<NoticeBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<NoticeBean>>> call, Response<ResultDO<List<NoticeBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<NoticeBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void circleJoins(int page, int circle_id, final CircleJoinsCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<CircleApplyBean>> call = RetrofitHelper.getInstence().getApiService().circleJoins(token, userId, circle_id, page);
        call.enqueue(new Callback<ResultDO<CircleApplyBean>>() {
            @Override
            public void onResponse(Call<ResultDO<CircleApplyBean>> call, Response<ResultDO<CircleApplyBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<CircleApplyBean>> call, Throwable t) {
                UserInfoHelper.getIntance().setPerfect(false);
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void circleHandle(int id, int type, final CommonCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().circleHandle(token, userId, id, type);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void applyCircle(int circle_id, String remark, final CircleCallCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<CircleCallBean>> call = RetrofitHelper.getInstence().getApiService().applyCircle(token, userId, circle_id, remark);
        call.enqueue(new Callback<ResultDO<CircleCallBean>>() {
            @Override
            public void onResponse(Call<ResultDO<CircleCallBean>> call, Response<ResultDO<CircleCallBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultDO<CircleCallBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg, e.data);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK, "");
            }
        });
    }

    public void checkCircle(int circle_id, final CheckCircleCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().checkCircle(token, userId, circle_id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == 200) {
                    callback.onSuccess();
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    if (e.code == 208) {
                        Gson gson = new Gson();
                        Logger.i("lzq", "e.data : " + e.data);
                        callback.onOther(gson.fromJson(e.data, CheckStatusBean.class));
                        return;
                    }
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void unReadCircle(int circle_id, final UnReadCircleCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<UnReadBean>> call = RetrofitHelper.getInstence().getApiService().unReadCircle(token, userId, circle_id);
        call.enqueue(new Callback<ResultDO<UnReadBean>>() {
            @Override
            public void onResponse(Call<ResultDO<UnReadBean>> call, Response<ResultDO<UnReadBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == 200) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<UnReadBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
                callback.onFailue(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getMyCompany(final GetMyCompanyCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<MyCompanyBean>> call = RetrofitHelper.getInstence().getApiService().getMyCompany(token, userId);
        call.enqueue(new Callback<ResultDO<MyCompanyBean>>() {
            @Override
            public void onResponse(Call<ResultDO<MyCompanyBean>> call, Response<ResultDO<MyCompanyBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
                    return;
                }
                if (response.body().getCode() == 200) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<MyCompanyBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getCorporateInfo(int id, final GetMyCompanyCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        if (userId == 0) {
            Call<ResultDO<MyCompanyBean>> call = RetrofitHelper.getInstence().getApiService().getCorporateInfo(id);
            call.enqueue(new Callback<ResultDO<MyCompanyBean>>() {
                @Override
                public void onResponse(Call<ResultDO<MyCompanyBean>> call, Response<ResultDO<MyCompanyBean>> response) {
                    if (response == null) {
                        return;
                    }
                    if (response.body() == null) {
                        callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
                        return;
                    }
                    if (response.body().getCode() == 200) {
                        callback.onSuccess(response.body().getData());
                    }
                }

                @Override
                public void onFailure(Call<ResultDO<MyCompanyBean>> call, Throwable t) {
                    if (t instanceof ResultException) {
                        ResultException e = (ResultException) t;
                        callback.onFailed(e.code, e.msg);
                        return;
                    }
                    callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
                }
            });
        } else {
            Call<ResultDO<MyCompanyBean>> call = RetrofitHelper.getInstence().getApiService().getCorporateInfoUser(userId, id);
            call.enqueue(new Callback<ResultDO<MyCompanyBean>>() {
                @Override
                public void onResponse(Call<ResultDO<MyCompanyBean>> call, Response<ResultDO<MyCompanyBean>> response) {
                    if (response == null) {
                        return;
                    }
                    if (response.body() == null) {
                        callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
                        return;
                    }
                    if (response.body().getCode() == 200) {
                        callback.onSuccess(response.body().getData());
                    }
                }

                @Override
                public void onFailure(Call<ResultDO<MyCompanyBean>> call, Throwable t) {
                    if (t instanceof ResultException) {
                        ResultException e = (ResultException) t;
                        callback.onFailed(e.code, e.msg);
                        return;
                    }
                    callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
                }
            });

        }
    }


    public void corporateEdit(int id, String logo, String img_banner, String url, int num, int stage_id, String introduce, final CommonCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().corporateEdit(token, userId, logo, img_banner, url, num, stage_id, introduce, id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }

                if (response.body().getCode() == 200) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void sendResourcex(String title, int provide_top, String provide_attr, String provide_remark, int need_top, String need_attr, String need_remark, int cooperation_mode, String provide_describe, String need_describe, String images, final ResourceReleaseCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<ResourceReleaseBean>> call = RetrofitHelper.getInstence().getApiService().sendResourcex(token, title, userId, provide_top, provide_attr, provide_remark, need_top, need_attr, need_remark, cooperation_mode, provide_describe, need_describe, images);
        call.enqueue(new retrofit2.Callback<ResultDO<ResourceReleaseBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ResourceReleaseBean>> call, Response<ResultDO<ResourceReleaseBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ResourceReleaseBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });

    }

    public void sendResourceV4(String title, String provide_attr, String provide_remark, String need_attr, String need_remark,
                               int cooperation_mode, String provide_describe, String need_describe,
                               String images, int city, final ResourceReleaseCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<ResourceReleaseBean>> call = RetrofitHelper.getInstence().getApiService().sendResourceV4(token, title, userId, provide_attr, provide_remark, need_attr, need_remark, cooperation_mode, provide_describe, need_describe, images, city);
        call.enqueue(new Callback<ResultDO<ResourceReleaseBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ResourceReleaseBean>> call, Response<ResultDO<ResourceReleaseBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ResourceReleaseBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getSourceScreen(final SourceScreenCallback callback) {
        Call<ResultDO<SourceScreenBean>> call = RetrofitHelper.getInstence().getApiService().sourceScreen();
        call.enqueue(new Callback<ResultDO<SourceScreenBean>>() {
            @Override
            public void onResponse(Call<ResultDO<SourceScreenBean>> call, Response<ResultDO<SourceScreenBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailue(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<SourceScreenBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
                callback.onFailue(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);

            }
        });
    }

    public void getVipV3x(final GetVipV3ListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int user_Id = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<VipV3Bean>> call = RetrofitHelper.getInstence().getApiService().getVipV3(token, user_Id);
        call.enqueue(new Callback<ResultDO<VipV3Bean>>() {
            @Override
            public void onResponse(Call<ResultDO<VipV3Bean>> call, Response<ResultDO<VipV3Bean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<VipV3Bean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    public void getSecretPhone(int toUser, final SecretPhoneCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<SecretPhoneBean>> call = RetrofitHelper.getInstence().getApiService().getSecretPhone(token, userId, toUser);
        call.enqueue(new Callback<ResultDO<SecretPhoneBean>>() {
            @Override
            public void onResponse(Call<ResultDO<SecretPhoneBean>> call, Response<ResultDO<SecretPhoneBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<SecretPhoneBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
            }
        });


    }

    public void sendTem(int toUserId, String text) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<String>> call = RetrofitHelper.getInstence().getApiService().sendTem(token, userId, toUserId, 1, text);
        call.enqueue(new Callback<ResultDO<String>>() {
            @Override
            public void onResponse(Call<ResultDO<String>> call, Response<ResultDO<String>> response) {

            }

            @Override
            public void onFailure(Call<ResultDO<String>> call, Throwable t) {

            }
        });


    }

    public void getCompanyList(int company_industry, int city, int page, final CompanyListCallback callback) {

        Call<ResultDO<CompanyListsBean>> call = RetrofitHelper.getInstence().getApiService().getCompanyList(company_industry, city, page);
        call.enqueue(new Callback<ResultDO<CompanyListsBean>>() {
            @Override
            public void onResponse(Call<ResultDO<CompanyListsBean>> call, Response<ResultDO<CompanyListsBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<CompanyListsBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
                callback.onFailue(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }

    public void getServiceBanner(final GetServiceBannerCallback callback) {
        Call<ResultDO<List<ServiceBannerBean>>> call = RetrofitHelper.getInstence().getApiService().getServiceBanner();
        call.enqueue(new Callback<ResultDO<List<ServiceBannerBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<ServiceBannerBean>>> call, Response<ResultDO<List<ServiceBannerBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<ServiceBannerBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getVedioList(int user_id, int page, int p_id, final VideoListCallback callback) {

        Call<ResultDO<ClassRoomListBean>> call = RetrofitHelper.getInstence().getApiService().getVideoList(user_id, 10, page, p_id);
        call.enqueue(new Callback<ResultDO<ClassRoomListBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ClassRoomListBean>> call, Response<ResultDO<ClassRoomListBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ClassRoomListBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
                callback.onFailue(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }

    public void getVedioDetail(int id, final VideoDetailCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<ClassRoomDetailBean>> call = RetrofitHelper.getInstence().getApiService().getVideoDetail(userId, id);
        call.enqueue(new Callback<ResultDO<ClassRoomDetailBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ClassRoomDetailBean>> call, Response<ResultDO<ClassRoomDetailBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ClassRoomDetailBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
                callback.onFailue(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }

    //获取留言数据
    public void getCommentList(int id, int page, final CommentListCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<CommentBean>> call = RetrofitHelper.getInstence().getApiService().getCommentList(userId, id, page, 20);
        call.enqueue(new Callback<ResultDO<CommentBean>>() {
            @Override
            public void onResponse(Call<ResultDO<CommentBean>> call, Response<ResultDO<CommentBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<CommentBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //添加留言
    public void goClassRoomComment(int video_id, String comment, int p_id, final CommentSuccessCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<CommentBean.ListBean>> call = RetrofitHelper.getInstence().getApiService().videoComment(token, userId, video_id, comment, p_id);
        call.enqueue(new Callback<ResultDO<CommentBean.ListBean>>() {
            @Override
            public void onResponse(Call<ResultDO<CommentBean.ListBean>> call, Response<ResultDO<CommentBean.ListBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<CommentBean.ListBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    public void getAccessToken(final GetAccessTokenCallback callback) {
        Call<ResultDO<VidStsBean>> call = RetrofitHelper.getInstence().getApiService().getAccess();
        call.enqueue(new Callback<ResultDO<VidStsBean>>() {
            @Override
            public void onResponse(Call<ResultDO<VidStsBean>> call, Response<ResultDO<VidStsBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<VidStsBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
                callback.onFailue(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }

    public void videoUpVote(int id, int type, int un_upvote, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().videoUpVote(token, userId, id, type, un_upvote);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    public void getCompanyUsers(int id, final CompanyUsersCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<List<MemberInfoBean>>> call = RetrofitHelper.getInstence().getApiService().getCompanyUserList(token, userId, id);
        call.enqueue(new Callback<ResultDO<List<MemberInfoBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<MemberInfoBean>>> call, Response<ResultDO<List<MemberInfoBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<MemberInfoBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
            }
        });


    }

    public void getCircleBasicInfo(int circle_id, final GroupInfoCallback callback) {

        Call<ResultDO<GroupBean>> call = RetrofitHelper.getInstence().getApiService().getCircleBasicInfo(circle_id);
        call.enqueue(new Callback<ResultDO<GroupBean>>() {
            @Override
            public void onResponse(Call<ResultDO<GroupBean>> call, Response<ResultDO<GroupBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                String GroupId = String.valueOf(response.body().getData().getId());
                String name = response.body().getData().getName();
                String headUrl = response.body().getData().getHead_pic();
                if (GroupId != null && name != null && headUrl != null) {
                    RongIM.getInstance().refreshGroupInfoCache(new Group(GroupId, name, Uri.parse(headUrl)));
                }
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultDO<GroupBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void commitCorporateEdit(int id, String logo, String companyName, String url, String brand, String introduce, int city, int company_industry, final CommitCorporateCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().commitCorporateEdit(token, userId, id, logo, companyName, url, brand, introduce, city, company_industry);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                callback.onSuccess();
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });

    }

    public void searchCompanyList(String keyWord, int page, final CompanyListCallback callback) {
        Call<ResultDO<CompanyListsBean>> call = RetrofitHelper.getInstence().getApiService().searchCompanyList(keyWord, page);
        call.enqueue(new Callback<ResultDO<CompanyListsBean>>() {
            @Override
            public void onResponse(Call<ResultDO<CompanyListsBean>> call, Response<ResultDO<CompanyListsBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<CompanyListsBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
            }
        });
    }

    public void myCollectList(int page, final GetResourceListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<IndexNewBean>> call = RetrofitHelper.getInstence().getApiService().myCollectList(token, userId, page);
        call.enqueue(new Callback<ResultDO<IndexNewBean>>() {
            @Override
            public void onResponse(Call<ResultDO<IndexNewBean>> call, Response<ResultDO<IndexNewBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {

                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<IndexNewBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }


    public void myServiceCollectList(int page, final ServiceListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<IndexServiceBean>> call = RetrofitHelper.getInstence().getApiService().myServiceCollectList(token, userId, page);
        call.enqueue(new Callback<ResultDO<IndexServiceBean>>() {
            @Override
            public void onResponse(Call<ResultDO<IndexServiceBean>> call, Response<ResultDO<IndexServiceBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {

                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<IndexServiceBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
                callback.onFailue(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }

    public void goToCollect(int resourceId, int action, final GotoCollectCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<GoToCollectBean>> call = RetrofitHelper.getInstence().getApiService().goToCollect(token, userId, resourceId, action);
        call.enqueue(new Callback<ResultDO<GoToCollectBean>>() {
            @Override
            public void onResponse(Call<ResultDO<GoToCollectBean>> call, Response<ResultDO<GoToCollectBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<GoToCollectBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }


    public void goToServiceCollect(int servicId, int action, final RequestCallback<String> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<Object>> call = RetrofitHelper.getInstence().getApiService().goToServiceCollect(token, userId, servicId, action);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData().toString());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    /**
     * 留言开启/关闭
     *
     * @param resourceId
     * @param action
     * @param callback
     */
    public void goToMessage(int resourceId, int action, final MessageCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().goTurnItOnOrOff(token, userId, resourceId, action);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                callback.onSuccess(response.body().getMsg());
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void goToReport(int target_id, int type, String content, int category_id, final ReportCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().goReport(token, userId, target_id, type, content, category_id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                callback.onSuccess(response.body().getMsg());
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });

    }

    public void getConfigV1(final GetConfigCallback callback) {
        Call<ResultDO<GetConfigBean>> call = RetrofitHelper.getInstence().getApiService().getConfigBeanV5();
        call.enqueue(new Callback<ResultDO<GetConfigBean>>() {
            @Override
            public void onResponse(Call<ResultDO<GetConfigBean>> call, Response<ResultDO<GetConfigBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<GetConfigBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }


    public void getTags(String tags, final GetTagsCallback callback) {
        Call<ResultDO<List<CellTagsBean>>> call = RetrofitHelper.getInstence().getApiService().getTags(tags);
        call.enqueue(new Callback<ResultDO<List<CellTagsBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<CellTagsBean>>> call, Response<ResultDO<List<CellTagsBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
//                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<CellTagsBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });

    }

    public void getUserCategoryV3(final GetSelectCateCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<SeclectCateBean>> call = RetrofitHelper.getInstence().getApiService().getUserCategoryV3(userId, token);
        call.enqueue(new Callback<ResultDO<SeclectCateBean>>() {
            @Override
            public void onResponse(Call<ResultDO<SeclectCateBean>> call, Response<ResultDO<SeclectCateBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<SeclectCateBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getAppArea(final GetAppAreaCallback callback) {
        Call<ResultDO<List<CityV2Bean>>> call = RetrofitHelper.getInstence().getApiService().getAppArea();
        call.enqueue(new Callback<ResultDO<List<CityV2Bean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<CityV2Bean>>> call, Response<ResultDO<List<CityV2Bean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<CityV2Bean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }

    public void dailyShare(final Context context) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().dailyShare(userId, token);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                ToastUtils.showCentetToast(context, response.body().getMsg());
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    ToastUtils.showCentetToast(context, e.msg);
                    return;
                }

            }
        });


    }

    public void getRecommend(String queryId, final GetRecommendCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<List<IndexNewBean.ListBean>>> call = RetrofitHelper.getInstence().getApiService().getRecommend(queryId, userId);
        call.enqueue(new Callback<ResultDO<List<IndexNewBean.ListBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<IndexNewBean.ListBean>>> call, Response<ResultDO<List<IndexNewBean.ListBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }

            }

            @Override
            public void onFailure(Call<ResultDO<List<IndexNewBean.ListBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    public void goToSign(final GoToSignCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().goToSign(token, userId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    public void getPopup(final GetPopupCallback callback) {
        Call<ResultDO<GetPopupBean>> call = RetrofitHelper.getInstence().getApiService().getPopup();
        call.enqueue(new Callback<ResultDO<GetPopupBean>>() {
            @Override
            public void onResponse(Call<ResultDO<GetPopupBean>> call, Response<ResultDO<GetPopupBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<GetPopupBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    public void getCategoryList(int category, final AddGroupClassifyCallback callback) {
        Call<ResultDO<List<AddGroupClassifyBean>>> call = RetrofitHelper.getInstence().getApiService().getCategoryList(category);
        call.enqueue(new Callback<ResultDO<List<AddGroupClassifyBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<AddGroupClassifyBean>>> call, Response<ResultDO<List<AddGroupClassifyBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }

            }

            @Override
            public void onFailure(Call<ResultDO<List<AddGroupClassifyBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    public void getGroupList(String keyword, int category, int page, final AddGroupListCallback callback) {
        Call<ResultDO<AddGroupListBean>> call = RetrofitHelper.getInstence().getApiService().getGroupList(keyword, category, page);
        call.enqueue(new Callback<ResultDO<AddGroupListBean>>() {
            @Override
            public void onResponse(Call<ResultDO<AddGroupListBean>> call, Response<ResultDO<AddGroupListBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }

            }

            @Override
            public void onFailure(Call<ResultDO<AddGroupListBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    public void establishGroup(final Context context, String name, int category, String introduction, int city, final AllResultDoCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String mtoken = UserInfoHelper.getIntance().getToken();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().establishGroup(mtoken, userId, name, category, introduction, city);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                } else {
                    ToastUtils.showCentetToast(context, response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //获取我的群组
    public void getMyGroupList(int page, final MyGroupListCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<List<MyGroupListBean>>> call = RetrofitHelper.getInstence().getApiService().getMyGroupList(token, userId, page);
        call.enqueue(new Callback<ResultDO<List<MyGroupListBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<MyGroupListBean>>> call, Response<ResultDO<List<MyGroupListBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<MyGroupListBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //获取群组成员
    public void getUserList(long groupId, String keyWord, final GetUserListCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String mtoken = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<List<GroupMemberManageBean>>> call = RetrofitHelper.getInstence().getApiService().getUserList(mtoken, groupId, userId, keyWord);
        call.enqueue(new Callback<ResultDO<List<GroupMemberManageBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<GroupMemberManageBean>>> call, Response<ResultDO<List<GroupMemberManageBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }

            }

            @Override
            public void onFailure(Call<ResultDO<List<GroupMemberManageBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //获取群组详细信息
    public void getGroupInformation(long groupId, final GetGroupInfoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<GroupInfoBean>> call = RetrofitHelper.getInstence().getApiService().getGroupInformation(token, userId, groupId);
        call.enqueue(new Callback<ResultDO<GroupInfoBean>>() {
            @Override
            public void onResponse(Call<ResultDO<GroupInfoBean>> call, Response<ResultDO<GroupInfoBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }

            }

            @Override
            public void onFailure(Call<ResultDO<GroupInfoBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //获取相似群组
    public void getSimilarGroup(int category, final SimilarGroupCallback callback) {
        String mtoken = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<List<SimilarGroupBean>>> call = RetrofitHelper.getInstence().getApiService().getSimilarGroup(mtoken, userId, category);
        call.enqueue(new Callback<ResultDO<List<SimilarGroupBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<SimilarGroupBean>>> call, Response<ResultDO<List<SimilarGroupBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<SimilarGroupBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //去成员管理
    public void goMemberManage(int operatedUser, long groupId, int identity, String name, int type, int code, String mobile, final AllResultDoCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String mtoken = UserInfoHelper.getIntance().getToken();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().goMemberManage(mtoken, userId, operatedUser, groupId, identity, name, type, code, mobile);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //取消创建群组
    public void groupCancle(int groupId, final AllResultDoCallback callback) {
        String mtoken = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().groupCancle(mtoken, userId, groupId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //申请加群
    public void applyGroup(long groupId, String name, int type, String remark, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().applyGroup(token, userId, groupId, name, type, remark);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }

            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //退出群组
    public void quitGroup(int groupId, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().quitGroup(token, userId, groupId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //升级群组
    public void upgradeGroup(String name, int groupId, final UpgradeGroupCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<UpgradeGroupBean>> call = RetrofitHelper.getInstence().getApiService().upgradeGroup(token, userId, name, groupId);
        call.enqueue(new Callback<ResultDO<UpgradeGroupBean>>() {
            @Override
            public void onResponse(Call<ResultDO<UpgradeGroupBean>> call, Response<ResultDO<UpgradeGroupBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<UpgradeGroupBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //编辑群公告
    public void editNotice(long groupId, String notice, String name, int type, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().editNotice(token, userId, groupId, notice, name, type);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //编辑群组资料
    public void modifyInfo(long groupId, String name, String introduction, int category, int city, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().modifyInfo(token, userId, groupId, name, introduction, category, city);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //同意或拒绝加入
    public void grouphandle(int id, int type, String refuse_remark, final GroupHandleCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<SystemMsgBean>> call = RetrofitHelper.getInstence().getApiService().grouphandle(token, userId, id, type, refuse_remark);
        call.enqueue(new Callback<ResultDO<SystemMsgBean>>() {
            @Override
            public void onResponse(Call<ResultDO<SystemMsgBean>> call, Response<ResultDO<SystemMsgBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<SystemMsgBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //设置加群方式
    public void joinManner(long group_id, int is_verify, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().joinManner(token, userId, group_id, is_verify);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //获取群组公告
    public void getNotice(long groupId, final GroupNoticeCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<GroupNoticeBean>> call = RetrofitHelper.getInstence().getApiService().getNotice(token, userId, groupId);
        call.enqueue(new Callback<ResultDO<GroupNoticeBean>>() {
            @Override
            public void onResponse(Call<ResultDO<GroupNoticeBean>> call, Response<ResultDO<GroupNoticeBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<GroupNoticeBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //检查用户创建群组资格
    public void groupCheck(final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().groupCheck(token, userId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //检查用户有无群组
    public void checkMyGroup(final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().checkMyGroup(token, userId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //获取留言数据
    public void getInquire(int topicId, int page, int commentId, int type, final InquireCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<InquireBean>> call = RetrofitHelper.getInstence().getApiService().getInquire(token, topicId, type, page, commentId);
        call.enqueue(new Callback<ResultDO<InquireBean>>() {
            @Override
            public void onResponse(Call<ResultDO<InquireBean>> call, Response<ResultDO<InquireBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<InquireBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //添加留言
    public void goComment(int topicId, String comment, String nickname, String thumb_img, int from_uid, String company, String position, int is_v, int type, final GoCommentCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<InquireBean.ListBean>> call = RetrofitHelper.getInstence().getApiService().goComment(token, topicId, comment, nickname, thumb_img, from_uid, company, position, is_v, type);
        call.enqueue(new Callback<ResultDO<InquireBean.ListBean>>() {
            @Override
            public void onResponse(Call<ResultDO<InquireBean.ListBean>> call, Response<ResultDO<InquireBean.ListBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<InquireBean.ListBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //回复留言
    public void goReplyComment(int topicId, int commentId, int replyType, int replyId, String comment, int fromUid, String fromHeadpic, String fromNickname, String fromCompany, String fromPosition, int isV, int type, final GoCommentCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<InquireBean.ListBean>> call = RetrofitHelper.getInstence().getApiService().goReplyComment(token, commentId, replyType, topicId, replyId, comment, fromUid, fromHeadpic, fromNickname, fromCompany, fromPosition, isV, type);
        call.enqueue(new Callback<ResultDO<InquireBean.ListBean>>() {
            @Override
            public void onResponse(Call<ResultDO<InquireBean.ListBean>> call, Response<ResultDO<InquireBean.ListBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<InquireBean.ListBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    public void getInteractiveNews(int page, final GetInteractNewsCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<InteractiveNewsBean>> call = RetrofitHelper.getInstence().getApiService().getInteractiveNews(token, userId, page);
        call.enqueue(new Callback<ResultDO<InteractiveNewsBean>>() {
            @Override
            public void onResponse(Call<ResultDO<InteractiveNewsBean>> call, Response<ResultDO<InteractiveNewsBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<InteractiveNewsBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //删除留言
    public void dodelComment(int id, int type, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().doDelComment(token, id, type);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }


    //删除留言
    public void doVideoDelComment(int id, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().doVideoDelComment(token, id, userId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //使用兑换码
    public void redeUseCode(String code, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().redeUseCode(token, userId, code);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //修改奖励券状态
    public void saveVoucher(String id, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().saveVoucher(token, userId, id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //用户登录推送服务经理
    public void loginPush() {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().loginPush(token, userId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }


            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    Log.d("==RequestManager", "e.code:" + e.code);
                    return;
                }
            }
        });


    }

    //用户发布资源V5
    public void sendResourceV5(String title, String provide_attr, String provide_remark, String need_attr, String need_remark,
                               int cooperation_mode, String provide_describe, String need_describe,
                               String images, String thumbImgs, int city, final ResourceReleaseCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<ResourceReleaseBean>> call = RetrofitHelper.getInstence().getApiService().sendResourceV5(token, title, userId, provide_attr, provide_remark, need_attr, need_remark, cooperation_mode, provide_describe, need_describe, images, thumbImgs, city);
        call.enqueue(new Callback<ResultDO<ResourceReleaseBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ResourceReleaseBean>> call, Response<ResultDO<ResourceReleaseBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ResourceReleaseBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    //是否有绑定的兑换码
    public void whetherBind(String name, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().whetherBind(token, userId, name);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //兑换码激活
    public void goActivation(final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().goActivation(token, userId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //站内消息
    public void getNewsV2(final GetNewsV2Callback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<NewsV2Bean>> call = RetrofitHelper.getInstence().getApiService().getNewsV2(token, userId);
        call.enqueue(new Callback<ResultDO<NewsV2Bean>>() {
            @Override
            public void onResponse(Call<ResultDO<NewsV2Bean>> call, Response<ResultDO<NewsV2Bean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<NewsV2Bean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //好友申请
    public void goFriendApply(int toUserId, int type, final GoFriendApplyCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<GoFriendApplyBean>> call = RetrofitHelper.getInstence().getApiService().goFriendApply(token, userId, toUserId, type);
        call.enqueue(new Callback<ResultDO<GoFriendApplyBean>>() {
            @Override
            public void onResponse(Call<ResultDO<GoFriendApplyBean>> call, Response<ResultDO<GoFriendApplyBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<GoFriendApplyBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //好友同意或拒绝
    public void friendIsAgree(int id, int operating, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().friendIsAgree(token, userId, id, operating);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }


    //好友邀请
    public void inviteConnections(String phone, String name, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().inviteConnections(token, userId, phone, name);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //好友列表
    public void getFriendList(String keywords, final GetFriendListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<GetFriendListBean>> call = RetrofitHelper.getInstence().getApiService().getFriendList(token, userId, keywords);
        call.enqueue(new Callback<ResultDO<GetFriendListBean>>() {
            @Override
            public void onResponse(Call<ResultDO<GetFriendListBean>> call, Response<ResultDO<GetFriendListBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<GetFriendListBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //分组好友列表
    public void getGroupFriendList(final GetGroupFriendListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<List<GroupFriendBean>>> call = RetrofitHelper.getInstence().getApiService().getGroupFriendList(token, userId);
        call.enqueue(new Callback<ResultDO<List<GroupFriendBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<GroupFriendBean>>> call, Response<ResultDO<List<GroupFriendBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<GroupFriendBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }


    public void getGroupFriendListFrist(final GetGroupFriendListFristCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<List<GroupFriendBean.UserListBean>>> call = RetrofitHelper.getInstence().getApiService().getGroupFriendListAll(token, userId);
        call.enqueue(new Callback<ResultDO<List<GroupFriendBean.UserListBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<GroupFriendBean.UserListBean>>> call, Response<ResultDO<List<GroupFriendBean.UserListBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<GroupFriendBean.UserListBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //打招呼语列表
    public void getGreetingsList(final GetGreentsCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<GreentingsBean>> call = RetrofitHelper.getInstence().getApiService().getGreetingsList(token, userId);
        call.enqueue(new Callback<ResultDO<GreentingsBean>>() {
            @Override
            public void onResponse(Call<ResultDO<GreentingsBean>> call, Response<ResultDO<GreentingsBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<GreentingsBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //合作名片分享
    public void shareCard(int toUserId, int shareUserId, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().shareCard(token, userId, toUserId, shareUserId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //资源分享
    public void shareResource(int toUserId, int resourceId, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().shareResource(token, userId, toUserId, resourceId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //删除好友
    public void delFriend(int toUserId, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().delFriend(token, userId, toUserId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //检查好友状态
    public void getFriendStatus(int toUserId, final GetFriendStatusCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<FriendStatusBean>> call = RetrofitHelper.getInstence().getApiService().getFriendStatus(token, userId, toUserId);
        call.enqueue(new Callback<ResultDO<FriendStatusBean>>() {
            @Override
            public void onResponse(Call<ResultDO<FriendStatusBean>> call, Response<ResultDO<FriendStatusBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<FriendStatusBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //存储接口错误日志
    public void saveLog(JSONObject object) {
        String data = object.toString();
        RetrofitHelper.getInstence().getApiService().saveLog(data);
    }


    //获取发布类型
    public void getReleaseType(int from, final GetReleaseTypeCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<List<GetReleaseTypeBean>>> call = RetrofitHelper.getInstence().getApiService().getReleaseType(token, userId, from);
        call.enqueue(new Callback<ResultDO<List<GetReleaseTypeBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<GetReleaseTypeBean>>> call, Response<ResultDO<List<GetReleaseTypeBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<GetReleaseTypeBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //获取资源发布标签模板
    public void getReleaseTemplate(int pId, final GetReleaseTemplateCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<GetReleaseTemplateNewBean>> call = RetrofitHelper.getInstence().getApiService().getReleaseTemplate(token, userId, pId);
        call.enqueue(new Callback<ResultDO<GetReleaseTemplateNewBean>>() {
            @Override
            public void onResponse(Call<ResultDO<GetReleaseTemplateNewBean>> call, Response<ResultDO<GetReleaseTemplateNewBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<GetReleaseTemplateNewBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }


    //发布资源V6
    public void sendResourceV6(String title, int p_id, String provide_attr
            , String provide_remark, String need_attr
            , String need_remark, String provide_describe, String need_describe,
                               String images, String thumb_img, int city, int is_combing, int is_transaction, final ResourceReleaseCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<ResourceReleaseBean>> call = RetrofitHelper.getInstence()
                .getApiService().sendResourceV6(token, userId, title, p_id,
                        provide_attr, provide_remark, need_attr
                        , need_remark, provide_describe, need_describe, images, thumb_img, city, is_combing, is_transaction);
        call.enqueue(new Callback<ResultDO<ResourceReleaseBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ResourceReleaseBean>> call, Response<ResultDO<ResourceReleaseBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ResourceReleaseBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //资源修改V5
    public void editResourceV5(int id, String title, String provide_attr,
                               String provide_remark, String need_attr,
                               String need_remark, String provide_describe, String need_describe,
                               String images, String thumb_img, int city, int p_id, int is_combing, int is_transaction, final ResourceReleaseCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<ResourceReleaseBean>> call = RetrofitHelper.getInstence().getApiService().editResourceV5(token, userId,
                id, title, provide_attr, provide_remark, need_attr, need_remark,
                provide_describe, need_describe, images, thumb_img, city, p_id, is_combing, is_transaction);
        call.enqueue(new Callback<ResultDO<ResourceReleaseBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ResourceReleaseBean>> call, Response<ResultDO<ResourceReleaseBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ResourceReleaseBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //解析tagsV2
    public void getTagsV3(int p_id, final GetTagsCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<CellTagsBean>> call = RetrofitHelper.getInstence().getApiService().getTagsV3(token, userId, p_id);
        call.enqueue(new Callback<ResultDO<CellTagsBean>>() {
            @Override
            public void onResponse(Call<ResultDO<CellTagsBean>> call, Response<ResultDO<CellTagsBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<CellTagsBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //获取异业课堂分类
    public void getVedioTags(final GetVedioCategoryCallback callback) {
        Call<ResultDO<List<VideoCategoryBean>>> call = RetrofitHelper.getInstence().getApiService().getVedioTags();
        call.enqueue(new Callback<ResultDO<List<VideoCategoryBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<VideoCategoryBean>>> call, Response<ResultDO<List<VideoCategoryBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<VideoCategoryBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //资源修改获取详情接口v2
    public void getEditResourceV2(int resourceId, final GetEditResourceV2Callback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<GetEditResourceInfoV2Bean>> call = RetrofitHelper.getInstence().getApiService().getEditResourceV2(token, userId, resourceId);
        call.enqueue(new Callback<ResultDO<GetEditResourceInfoV2Bean>>() {
            @Override
            public void onResponse(Call<ResultDO<GetEditResourceInfoV2Bean>> call, Response<ResultDO<GetEditResourceInfoV2Bean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<GetEditResourceInfoV2Bean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //修改登录密码
    public void changeCode(String mobile, String code, String newPsw, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().changeCode(token, userId, mobile, code, newPsw);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //发送语音验证码
    public void voiceVerity(String mobile, String sign, int type, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().voiceVerity(token, mobile, sign, type, 1);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //获取我的评论
    public void getMyComment(int page, final GetMyCommentCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<MyCommentBean>> call = RetrofitHelper.getInstence().getApiService().getMyComment(token, userId, page);
        call.enqueue(new Callback<ResultDO<MyCommentBean>>() {
            @Override
            public void onResponse(Call<ResultDO<MyCommentBean>> call, Response<ResultDO<MyCommentBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<MyCommentBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //获取每日热门列表
    public void getHotResource(final GetHotResourceCallback callback) {

        Call<ResultDO<List<HotResourceBean>>> call = RetrofitHelper.getInstence().getApiService().getHotResource();
        call.enqueue(new Callback<ResultDO<List<HotResourceBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<HotResourceBean>>> call, Response<ResultDO<List<HotResourceBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<HotResourceBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }


    //获取精选资源
    public void getSelectionResource(int page, final GetResourceListCallback callback) {
        Call<ResultDO<IndexNewBean>> call = RetrofitHelper.getInstence().getApiService().getSelectionResource(page, 1);
        call.enqueue(new Callback<ResultDO<IndexNewBean>>() {
            @Override
            public void onResponse(Call<ResultDO<IndexNewBean>> call, Response<ResultDO<IndexNewBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<IndexNewBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //获取我的活动列表
    public void getMyActList(int page, final GetMyActListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<MyActListBean>> call = RetrofitHelper.getInstence().getApiService().getMyActList(token, userId, page);
        call.enqueue(new Callback<ResultDO<MyActListBean>>() {
            @Override
            public void onResponse(Call<ResultDO<MyActListBean>> call, Response<ResultDO<MyActListBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<MyActListBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    //电子票详情
    public void getGoseeBill(int id, final GetGoseeBillCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<GoseeBillBean>> call = RetrofitHelper.getInstence().getApiService().getGoseeBill(token, userId, id);
        call.enqueue(new Callback<ResultDO<GoseeBillBean>>() {
            @Override
            public void onResponse(Call<ResultDO<GoseeBillBean>> call, Response<ResultDO<GoseeBillBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<GoseeBillBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //取消报名
    public void goCancelSignUp(int id, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().cancelSignUp(token, userId, id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    //邀请加入群组
    public void inviteToJoin(int toUserId, int groupId, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().inviteToJoin(token, userId, toUserId, groupId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //是否有激活兑换码弹窗
    public void vipPopup(final VipPopupCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<VipPopUpBean>> call = RetrofitHelper.getInstence().getApiService().vipPopup(token, userId);
        call.enqueue(new Callback<ResultDO<VipPopUpBean>>() {
            @Override
            public void onResponse(Call<ResultDO<VipPopUpBean>> call, Response<ResultDO<VipPopUpBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<VipPopUpBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }


    public void waitActivated(final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().waitActivated(token, userId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    public void talkPackageBuy(final TalkPackageCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<TalkPackageBuyBean>> call = RetrofitHelper.getInstence().getApiService().talkPackageBuy(token, userId, 2);
        call.enqueue(new Callback<ResultDO<TalkPackageBuyBean>>() {
            @Override
            public void onResponse(Call<ResultDO<TalkPackageBuyBean>> call, Response<ResultDO<TalkPackageBuyBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<TalkPackageBuyBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //获取人脉推荐列表
    public void getRecommendList(final GetRecommendListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<List<RecommendBean>>> call = RetrofitHelper.getInstence().getApiService().getRecommendList(token, userId);
        call.enqueue(new Callback<ResultDO<List<RecommendBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<RecommendBean>>> call, Response<ResultDO<List<RecommendBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<RecommendBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //获取人脉通讯录列表
    public void getAddressList(String mobile, int page, final GetAddressListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<AddressListBean>> call = RetrofitHelper.getInstence().getApiService().getAddressList(token, userId, mobile, page);
        call.enqueue(new Callback<ResultDO<AddressListBean>>() {
            @Override
            public void onResponse(Call<ResultDO<AddressListBean>> call, Response<ResultDO<AddressListBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<AddressListBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //好友批量添加
    public void goBatchAdd(String uidData, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().goBatchAdd(token, userId, uidData);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //获取刷新卡列表
    public void getTopCardList(final GetTopCardCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<List<TopCardBean>>> call = RetrofitHelper.getInstence().getApiService().getTopCardList(token, userId);
        call.enqueue(new Callback<ResultDO<List<TopCardBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<TopCardBean>>> call, Response<ResultDO<List<TopCardBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<TopCardBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //获取梳理卡列表
    public void getCombingCardList(final GetCombingCardCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<CombingCardBean>> call = RetrofitHelper.getInstence().getApiService().getCombingCardList(token, userId);
        call.enqueue(new Callback<ResultDO<CombingCardBean>>() {
            @Override
            public void onResponse(Call<ResultDO<CombingCardBean>> call, Response<ResultDO<CombingCardBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<CombingCardBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //获取置顶卡列表
    public void getFixedTopCardList(final GetTopCardCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<List<TopCardBean>>> call = RetrofitHelper.getInstence().getApiService().getFixedTopCardList(token, userId);
        call.enqueue(new Callback<ResultDO<List<TopCardBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<TopCardBean>>> call, Response<ResultDO<List<TopCardBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<TopCardBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //置顶卡生成订单
    public void goTopCardBuy(int id, final VipBugCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<OrderInfoBean>> call = RetrofitHelper.getInstence().getApiService().topCardBuy(token, userId, id, 2);
        call.enqueue(new Callback<ResultDO<OrderInfoBean>>() {
            @Override
            public void onResponse(Call<ResultDO<OrderInfoBean>> call, Response<ResultDO<OrderInfoBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<OrderInfoBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    public void goFixedTopCardBuy(int id, final VipBugCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<OrderInfoBean>> call = RetrofitHelper.getInstence().getApiService().fixedTopCardBuy(token, userId, id, 2);
        call.enqueue(new Callback<ResultDO<OrderInfoBean>>() {
            @Override
            public void onResponse(Call<ResultDO<OrderInfoBean>> call, Response<ResultDO<OrderInfoBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<OrderInfoBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    public void CombingCardBuy(int id, final VipBugCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<OrderInfoBean>> call = RetrofitHelper.getInstence().getApiService().combingCardBuy(token, userId, id, 2);
        call.enqueue(new Callback<ResultDO<OrderInfoBean>>() {
            @Override
            public void onResponse(Call<ResultDO<OrderInfoBean>> call, Response<ResultDO<OrderInfoBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<OrderInfoBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //刷新前检测接口(置顶功能)
    public void goRefreshCheck(int type, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().goRefreshCheck(token, userId, type);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //取消预约
    public void cancelFixedTop(int id, final CancleFixedTopCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<FixedTopCancleBean>> call = RetrofitHelper.getInstence().getApiService().cancelFixedTop(token, userId, id);
        call.enqueue(new Callback<ResultDO<FixedTopCancleBean>>() {
            @Override
            public void onResponse(Call<ResultDO<FixedTopCancleBean>> call, Response<ResultDO<FixedTopCancleBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData(), response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<FixedTopCancleBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //获取常用语列表
    public void getCommonlySentenceList(final GetCommlySentenceCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<List<LLSimpleTextBean>>> call = RetrofitHelper.getInstence().getApiService().getCommonlySentence(userId, token);
        call.enqueue(new Callback<ResultDO<List<LLSimpleTextBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<LLSimpleTextBean>>> call, Response<ResultDO<List<LLSimpleTextBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<LLSimpleTextBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //获取合作详情海报分享二维码
    public void getWechatQr(int resource_id, int invit_id, int type, final GetWechatQrCallback callback) {
        Call<ResultDO<WechatQr>> call = RetrofitHelper.getInstence().getApiService().getWechatQR(resource_id, invit_id, type);
        call.enqueue(new Callback<ResultDO<WechatQr>>() {
            @Override
            public void onResponse(Call<ResultDO<WechatQr>> call, Response<ResultDO<WechatQr>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<WechatQr>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }

    //获取资源发布模板
    public void getTemplate(int pId, final GetTemplateCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<ReleaseTemplateBean>> call = RetrofitHelper.getInstence().getApiService().getTemplate(userId, token, pId);
        call.enqueue(new Callback<ResultDO<ReleaseTemplateBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ReleaseTemplateBean>> call, Response<ResultDO<ReleaseTemplateBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ReleaseTemplateBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    //获取发布服务分类和标签
    public void getCategoryAndTag(final GetServiceCategoryAndTagCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<List<ServiceCategoryAndTag>>> call = RetrofitHelper.getInstence().getApiService().getCategoryAndTag(token);
        call.enqueue(new Callback<ResultDO<List<ServiceCategoryAndTag>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<ServiceCategoryAndTag>>> call, Response<ResultDO<List<ServiceCategoryAndTag>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<ServiceCategoryAndTag>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });

    }

    public void sendServiceInfo(int typeId, String offerAttr, String title, String detail, String provide_img, String thumb_img, final ReleaseServiceSuccessCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<ReleaseServiceSuccessBean>> call =
                RetrofitHelper.getInstence().getApiService().sendInfo(userId, token, title, offerAttr, provide_img, thumb_img, typeId, detail);
        call.enqueue(new Callback<ResultDO<ReleaseServiceSuccessBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ReleaseServiceSuccessBean>> call, Response<ResultDO<ReleaseServiceSuccessBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }

            }

            @Override
            public void onFailure(Call<ResultDO<ReleaseServiceSuccessBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }

            }
        });
    }

    public void sendServiceCase(int mServiceId, String title, String detail, String provide_img, String thumb_img, final ReleaseServiceSuccessCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<ReleaseServiceSuccessBean>> call =
                RetrofitHelper.getInstence().getApiService().sendServiceCase(userId, mServiceId, token, title, provide_img, thumb_img, detail);
        call.enqueue(new Callback<ResultDO<ReleaseServiceSuccessBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ReleaseServiceSuccessBean>> call, Response<ResultDO<ReleaseServiceSuccessBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }

            }

            @Override
            public void onFailure(Call<ResultDO<ReleaseServiceSuccessBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }

            }
        });
    }


    public void getCaseInfo(int id, final GetCaseDetailCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<CaseBean>> call =
                RetrofitHelper.getInstence().getApiService().caseInfo(token, id);
        call.enqueue(new Callback<ResultDO<CaseBean>>() {
            @Override
            public void onResponse(Call<ResultDO<CaseBean>> call, Response<ResultDO<CaseBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }

            }

            @Override
            public void onFailure(Call<ResultDO<CaseBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }

            }
        });
    }

    public void UpdateServiceInfo(int typeId, String offerAttr, String title, String detail, String provide_img, String thumb_img, int id, final ReleaseServiceSuccessCallback callback) {
        int userId = UserInfoHelper.getIntance().getUserId();
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<ReleaseServiceSuccessBean>> call =
                RetrofitHelper.getInstence().getApiService().editService(userId, token, title, offerAttr, provide_img, thumb_img, typeId, detail, id);
        call.enqueue(new Callback<ResultDO<ReleaseServiceSuccessBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ReleaseServiceSuccessBean>> call, Response<ResultDO<ReleaseServiceSuccessBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    callback.onFailed(response.body().getCode(), response.body().getMsg());
                }

            }

            @Override
            public void onFailure(Call<ResultDO<ReleaseServiceSuccessBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }

            }
        });
    }

    public void getServiceInfoList(int mSearchPage, String sort_order, int query_id, int p_id, final ServiceListCallback callback) {

        Call<ResultDO<IndexServiceBean>> call = RetrofitHelper.getInstence().getApiService().serviceInfoList(sort_order, query_id, p_id, mSearchPage);
        call.enqueue(new Callback<ResultDO<IndexServiceBean>>() {
            @Override
            public void onResponse(Call<ResultDO<IndexServiceBean>> call, Response<ResultDO<IndexServiceBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<IndexServiceBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
                callback.onFailue(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }

    public void getSearchServiceInfoList(int mSearchPage, String key, final ServiceListCallback callback) {

        Call<ResultDO<IndexServiceBean>> call = RetrofitHelper.getInstence().getApiService().serachServiceInfoList("2", mSearchPage, key);
        call.enqueue(new Callback<ResultDO<IndexServiceBean>>() {
            @Override
            public void onResponse(Call<ResultDO<IndexServiceBean>> call, Response<ResultDO<IndexServiceBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<IndexServiceBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
                callback.onFailue(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }

    public void getCompanyServiceInfoList(int mSearchPage, String sort_order, int query_id, int p_id, int companyId, final ServiceListCallback callback) {

        Call<ResultDO<IndexServiceBean>> call = RetrofitHelper.getInstence().getApiService().serviceInfoListtwo(sort_order, query_id, p_id, mSearchPage, companyId);
        call.enqueue(new Callback<ResultDO<IndexServiceBean>>() {
            @Override
            public void onResponse(Call<ResultDO<IndexServiceBean>> call, Response<ResultDO<IndexServiceBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailue(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<IndexServiceBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailue(e.code, e.msg);
                    return;
                }
                callback.onFailue(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }

    public void getCaseList(int id, final GetCaseListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<List<ServiceCaseBean>>> call = RetrofitHelper.getInstence().getApiService().caseList(token, id);
        call.enqueue(new Callback<ResultDO<List<ServiceCaseBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<ServiceCaseBean>>> call, Response<ResultDO<List<ServiceCaseBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<ServiceCaseBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }

        });
    }

    public void getWelfareClubList(final GetWelfareClubListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        Call<ResultDO<List<WelfareAgencyBean>>> call = RetrofitHelper.getInstence().getApiService().welfareClubList(token);
        call.enqueue(new Callback<ResultDO<List<WelfareAgencyBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<WelfareAgencyBean>>> call, Response<ResultDO<List<WelfareAgencyBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<WelfareAgencyBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }

        });
    }

    public void getVerifyInfo(int id, final GetVerifyInfoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<VerifyInfo>> call = RetrofitHelper.getInstence().getApiService().getVerifyInfo(token, userId, id);
        call.enqueue(new Callback<ResultDO<VerifyInfo>>() {
            @Override
            public void onResponse(Call<ResultDO<VerifyInfo>> call, Response<ResultDO<VerifyInfo>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<VerifyInfo>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }

        });
    }

    //固定预加载
    public void apptFixedTop(int p_id, final GetApptFixedCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<TimeBean>> call = RetrofitHelper.getInstence().getApiService().apptFixedTop(token, userId, p_id);
        call.enqueue(new Callback<ResultDO<TimeBean>>() {
            @Override
            public void onResponse(Call<ResultDO<TimeBean>> call, Response<ResultDO<TimeBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<TimeBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });


    }


    public void fixedTop(int id, int p_id, String time, final RequestCallback<String> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<Object>> call = RetrofitHelper.getInstence().getApiService().fixedTop(token, userId, p_id, id, time);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData().toString());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void getUserIdList(final GetUserIdListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<UserIdBean>> call = RetrofitHelper.getInstence().getApiService().getUserList(token, userId);
        call.enqueue(new Callback<ResultDO<UserIdBean>>() {
            @Override
            public void onResponse(Call<ResultDO<UserIdBean>> call, Response<ResultDO<UserIdBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<UserIdBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //添加分组
    public void createGroup(String name, final AddGroupCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<AddGroupBean>> call = RetrofitHelper.getInstence().getApiService().createGroup(token, userId, name);
        call.enqueue(new Callback<ResultDO<AddGroupBean>>() {
            @Override
            public void onResponse(Call<ResultDO<AddGroupBean>> call, Response<ResultDO<AddGroupBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<AddGroupBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //修改分组
    public void editGroupName(String name, int group_id, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().editGroupName(token, userId, group_id, name);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //删除分组
    public void delGroup(int group_id, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().delGroup(token, userId, group_id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //删除招呼语
    public void delGreetings(int id, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().delGreetings(token, userId, id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    //选择招呼语
    public void selectedGreetings(int id, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().selectedGreetings(token, userId, id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //重新分组
    public void moveGroup(int group_id, int member_id, final AddGroupCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<AddGroupBean>> call = RetrofitHelper.getInstence().getApiService().moveGroup(token, userId, group_id, member_id);
        call.enqueue(new Callback<ResultDO<AddGroupBean>>() {
            @Override
            public void onResponse(Call<ResultDO<AddGroupBean>> call, Response<ResultDO<AddGroupBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<AddGroupBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    //添加招呼语
    public void addGreetings(String title, final AddGroupCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<AddGroupBean>> call = RetrofitHelper.getInstence().getApiService().addGreetings(token, userId, title);
        call.enqueue(new Callback<ResultDO<AddGroupBean>>() {
            @Override
            public void onResponse(Call<ResultDO<AddGroupBean>> call, Response<ResultDO<AddGroupBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<AddGroupBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    public void getToken(String name, String identificationNumber, final GetTokenCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<TokenBean>> call = RetrofitHelper.getInstence().getApiService().getToken(token, userId, name, identificationNumber);
        call.enqueue(new Callback<ResultDO<TokenBean>>() {
            @Override
            public void onResponse(Call<ResultDO<TokenBean>> call, Response<ResultDO<TokenBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<TokenBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    public void getStatusCode(final GetTokenCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<TokenBean>> call = RetrofitHelper.getInstence().getApiService().getStatusCode(token, userId);
        call.enqueue(new Callback<ResultDO<TokenBean>>() {
            @Override
            public void onResponse(Call<ResultDO<TokenBean>> call, Response<ResultDO<TokenBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<TokenBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    public void initiateGuarantee(int id, String text, String price, int type, final RequestCallback<String> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<Object>> call = RetrofitHelper.getInstence().getApiService().initiateGuarantee(token, userId, type, text, id, price);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData().toString());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void getGuaranteeDetail(int id, final GetGuaranteeDetailtCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<GuaranteeDetailBean>> call = RetrofitHelper.getInstence().getApiService().getGuaranteeDetail(token, userId, id);
        call.enqueue(new Callback<ResultDO<GuaranteeDetailBean>>() {
            @Override
            public void onResponse(Call<ResultDO<GuaranteeDetailBean>> call, Response<ResultDO<GuaranteeDetailBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<GuaranteeDetailBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }

        });
    }


    //同意交易
    public void agreeGuarantee(int id, int is_agree, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().agreeGuarantee(token, userId, id, is_agree);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    //同意交易
    public void cancelGuarantee(int id, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().cancelGuarantee(token, userId, id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    //支付担保金和补交担保金
    public void payGuarantee(int id, int pay_type, String picture_proof, int type, String amount, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().payGuarantee(token, userId, id, pay_type, picture_proof, type, amount);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //支付详情
    public void payGuaranteeDetail(String order_sn, int type, final GetGuaranteePayDetailCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<PayDetailBean>> call = RetrofitHelper.getInstence().getApiService().payGuaranteeDetail(token, userId, order_sn, type);
        call.enqueue(new Callback<ResultDO<PayDetailBean>>() {
            @Override
            public void onResponse(Call<ResultDO<PayDetailBean>> call, Response<ResultDO<PayDetailBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<PayDetailBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    //申请结算
    public void applicationSettlement(int id, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().applicationSettlement(token, userId, id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    public void getGuaranteeOrderList(int page, final GetGuaranteeOrderCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<GuaranteeOrderBean>> call = RetrofitHelper.getInstence().getApiService().getGuaranteeOrderList(token, userId, page);
        call.enqueue(new Callback<ResultDO<GuaranteeOrderBean>>() {
            @Override
            public void onResponse(Call<ResultDO<GuaranteeOrderBean>> call, Response<ResultDO<GuaranteeOrderBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<GuaranteeOrderBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }


    //买方申请结算
    public void guaranteeSettlement(int id, String billing_amount, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().guaranteeSettlement(token, userId, id, billing_amount);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    //申请退款
    public void applicationRefund(String order_sn, int id, int obj_user_id, String guarantee_amount, String billing_amount, String refund_amount, int refund_status, String refund_desc, String images, String thumb_img, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().applicationRefund(token, userId, order_sn, id, obj_user_id, guarantee_amount, billing_amount, refund_amount, refund_status, refund_desc, images, thumb_img);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    public void refundDetail(String order_sn, final GetRefundDetailCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<RefundDetailBean>> call = RetrofitHelper.getInstence().getApiService().refundDetail(token, userId, order_sn);
        call.enqueue(new Callback<ResultDO<RefundDetailBean>>() {
            @Override
            public void onResponse(Call<ResultDO<RefundDetailBean>> call, Response<ResultDO<RefundDetailBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<RefundDetailBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }


    public void serviceInfo(int id, final GetGuaranteeServiceinfoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<GuaranteeServiceInfoBean>> call = RetrofitHelper.getInstence().getApiService().serviceInfo(token, userId, id);
        call.enqueue(new Callback<ResultDO<GuaranteeServiceInfoBean>>() {
            @Override
            public void onResponse(Call<ResultDO<GuaranteeServiceInfoBean>> call, Response<ResultDO<GuaranteeServiceInfoBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<GuaranteeServiceInfoBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }

    //买方取消退款
    public void cancelRefund(int id, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().cancelRefund(token, userId, id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //卖方同意退款
    public void agreeRefund(int id, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().agreeRefund(token, userId, id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //卖方拒绝退款
    public void refuseRefund(int id, String refuse_desc, String refuse_images, String refuse_thumb_img, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().refuseRefund(token, userId, id, refuse_desc, refuse_images, refuse_thumb_img);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    //申请客服介入
    public void applicationService(int id, int type, String desc, String images, String thumb_img, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().applicationService(token, userId, id, type, desc, images, thumb_img);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    public void userAccount(final GetMyWalletCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<MyWalletBean>> call = RetrofitHelper.getInstence().getApiService().userAccount(token, userId);
        call.enqueue(new Callback<ResultDO<MyWalletBean>>() {
            @Override
            public void onResponse(Call<ResultDO<MyWalletBean>> call, Response<ResultDO<MyWalletBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<MyWalletBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }


    public void getBillList(int page, final GetMyBillListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<BillBean>> call = RetrofitHelper.getInstence().getApiService().billList(token, userId, page);
        call.enqueue(new Callback<ResultDO<BillBean>>() {
            @Override
            public void onResponse(Call<ResultDO<BillBean>> call, Response<ResultDO<BillBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<BillBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }


    public void billDetail(int id, final GetWalletDetailCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<WalletDetailBean>> call = RetrofitHelper.getInstence().getApiService().billDetail(token, userId, id);
        call.enqueue(new Callback<ResultDO<WalletDetailBean>>() {
            @Override
            public void onResponse(Call<ResultDO<WalletDetailBean>> call, Response<ResultDO<WalletDetailBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<WalletDetailBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }


    public void withdrawList(int page, final GetCashWithdrawalCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<CashWithdrawalBean>> call = RetrofitHelper.getInstence().getApiService().withdrawList(token, userId, page);
        call.enqueue(new Callback<ResultDO<CashWithdrawalBean>>() {
            @Override
            public void onResponse(Call<ResultDO<CashWithdrawalBean>> call, Response<ResultDO<CashWithdrawalBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<CashWithdrawalBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }

    //提现
    public void withdraw(String code, String mobile, int account_type, String withdrawals_amount, String alipay_account, String alipay_name, String bank_account, String bank_account_name, String bank, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().withdraw(token, userId, code, mobile, account_type, withdrawals_amount, alipay_account, alipay_name, bank_account, bank_account_name, bank);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData().toString());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    public void handlingFee(String amount, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().handlingFee(token, userId, amount);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData().toString());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    public void getLaunchScreen(final GetLaunchScreenCallback callback) {
        Call<ResultDO<SplashBean>> call = RetrofitHelper.getInstence().getApiService().getLaunchScreen();
        call.enqueue(new Callback<ResultDO<SplashBean>>() {
            @Override
            public void onResponse(Call<ResultDO<SplashBean>> call, Response<ResultDO<SplashBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<SplashBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }


    //获取发布类型
    public void getTypeName(final GetRewardTypeCallback callback) {
        Call<ResultDO<RewardTypeBean>> call = RetrofitHelper.getInstence().getApiService().getTypeName();
        call.enqueue(new Callback<ResultDO<RewardTypeBean>>() {
            @Override
            public void onResponse(Call<ResultDO<RewardTypeBean>> call, Response<ResultDO<RewardTypeBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<RewardTypeBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //获取发布类型
    public void releaseReward(String type_name, String title, String detail, String amount, int number, final PublicRewardCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<PublicRewardBean>> call = RetrofitHelper.getInstence().getApiService().releaseReward(token, userId, type_name, title, detail, amount, number);
        call.enqueue(new Callback<ResultDO<PublicRewardBean>>() {
            @Override
            public void onResponse(Call<ResultDO<PublicRewardBean>> call, Response<ResultDO<PublicRewardBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<PublicRewardBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    public void rewardWeChatPay(int id, String orderSn, String type, final WechatPayCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<WeChatPayInfo>> call = RetrofitHelper.getInstence().getApiService().rewardPay(token, userId, orderSn, id, type);
        call.enqueue(new Callback<ResultDO<WeChatPayInfo>>() {
            @Override
            public void onResponse(Call<ResultDO<WeChatPayInfo>> call, Response<ResultDO<WeChatPayInfo>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultDO<WeChatPayInfo>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void rewardPayAli(int id, String orderSn, String type, final PayCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<String>> call = RetrofitHelper.getInstence().getApiService().rewardPayAli(token, userId, orderSn, id, type);
        call.enqueue(new Callback<ResultDO<String>>() {
            @Override
            public void onResponse(Call<ResultDO<String>> call, Response<ResultDO<String>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                response.body().getCode();
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultDO<String>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }


    public void getRewardList(int page, final getRewardListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<RewardListBean>> call = RetrofitHelper.getInstence().getApiService().getRewardList(token, userId, page);
        call.enqueue(new Callback<ResultDO<RewardListBean>>() {
            @Override
            public void onResponse(Call<ResultDO<RewardListBean>> call, Response<ResultDO<RewardListBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<RewardListBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }


    public void getRewardDetail(String order_sn, final GetRewardDetailCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<RewardDetailBean>> call = RetrofitHelper.getInstence().getApiService().getRewardDetail(token, userId, order_sn);
        call.enqueue(new Callback<ResultDO<RewardDetailBean>>() {
            @Override
            public void onResponse(Call<ResultDO<RewardDetailBean>> call, Response<ResultDO<RewardDetailBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<RewardDetailBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }


    public void takeReward(String order_sn, final RequestCallback<String> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<Object>> call = RetrofitHelper.getInstence().getApiService().takeReward(token, userId, order_sn);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<Object>>() {
            @Override
            public void onResponse(Call<ResultDO<Object>> call, Response<ResultDO<Object>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData().toString());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<Object>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }


    public void getMyTakeRewardList(int page, final RequestCallback<TakeRewardBean> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<TakeRewardBean>> call = RetrofitHelper.getInstence().getApiService().getMyTakeRewardList(token, userId, page);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<TakeRewardBean>>() {
            @Override
            public void onResponse(Call<ResultDO<TakeRewardBean>> call, Response<ResultDO<TakeRewardBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<TakeRewardBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }

    public void getMyPublicRewardList(int page, final RequestCallback<MyPublicRewardBean> callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<MyPublicRewardBean>> call = RetrofitHelper.getInstence().getApiService().getMyPublicRewardList(token, userId, page);
        callback.requestStart(call);
        call.enqueue(new Callback<ResultDO<MyPublicRewardBean>>() {
            @Override
            public void onResponse(Call<ResultDO<MyPublicRewardBean>> call, Response<ResultDO<MyPublicRewardBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callback.requestEnd();
                        return;
                    }
                    if (response.body().getCode() == ResponseCode.OK) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                } else {
                    callback.onFailed(response.code(), response.message());
                }
                callback.requestEnd();
            }

            @Override
            public void onFailure(Call<ResultDO<MyPublicRewardBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    callback.requestEnd();
                    return;
                }
                callback.onFailed(-1000, NO_NETWOEK);
                callback.requestEnd();
            }
        });
    }


    public void getRewardOrders(String order_sn, int id, final GetRewardOrderCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<RewardOrderBean>> call = RetrofitHelper.getInstence().getApiService().getRewardOrders(token, userId, order_sn, id);
        call.enqueue(new Callback<ResultDO<RewardOrderBean>>() {
            @Override
            public void onResponse(Call<ResultDO<RewardOrderBean>> call, Response<ResultDO<RewardOrderBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<RewardOrderBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });

    }

    //悬赏取消订单
    public void cancelReward(String order_sn, int id, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().cancelReward(token, userId, order_sn, id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //悬赏同意取消订单
    public void agreeCancelReward(String order_sn, int id, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().agreeCancelReward(token, userId, order_sn, id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    public void takingOrderPeople(String order_sn, final GetPersonListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<AcceptedOrdersPersonBean>> call = RetrofitHelper.getInstence().getApiService().takingOrderPeople(token, userId, order_sn);
        call.enqueue(new Callback<ResultDO<AcceptedOrdersPersonBean>>() {
            @Override
            public void onResponse(Call<ResultDO<AcceptedOrdersPersonBean>> call, Response<ResultDO<AcceptedOrdersPersonBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<AcceptedOrdersPersonBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });

    }

    //悬赏退款
    public void refundReward(String order_sn, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().refundReward(token, userId, order_sn);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //悬赏结算
    public void settlementReward(String order_sn, int id, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().settlementReward(token, userId, order_sn, id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //申请客服介入
    public void applicationRewardService(int id, String desc, String images, String thumb_img, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().applicationRewardService(token, userId, id, desc, images, thumb_img);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    public void serviceRewardInfo(int id, final GetGuaranteeServiceinfoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<GuaranteeServiceInfoBean>> call = RetrofitHelper.getInstence().getApiService().serviceRewardInfo(token, userId, id);
        call.enqueue(new Callback<ResultDO<GuaranteeServiceInfoBean>>() {
            @Override
            public void onResponse(Call<ResultDO<GuaranteeServiceInfoBean>> call, Response<ResultDO<GuaranteeServiceInfoBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess(response.body().getData());
                } else {
                    if (response.body().getCode() == ResponseCode.NO_LIMIT_CODE) {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    } else {
                        callback.onFailed(response.body().getCode(), response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultDO<GuaranteeServiceInfoBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }

    public void getGuaranteeUserInfo(String mobile, final GetOtherUserInfoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<OtherUserInfo>> call = RetrofitHelper.getInstence().getApiService().getGuaranteeUserInfo(token, userId, mobile);
        call.enqueue(new Callback<ResultDO<OtherUserInfo>>() {
            @Override
            public void onResponse(Call<ResultDO<OtherUserInfo>> call, Response<ResultDO<OtherUserInfo>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultDO<OtherUserInfo>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }


    //资源详情是否弹出担保交易弹窗
    public void isPopups(int id, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().isPopups(token, userId, id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }

    //资源详情弹出担保交易弹窗,同意或者取消
    public void clickPopups(int id, int type, final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().clickPopups(token, userId, id, type);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    public void registerCheckCode(String mobile, int code, String lon, String lat, int make, String openid, String unionid, final RegisterCheckCodeCallback callback) {
        Call<ResultDO<RegisterNewBean>> call = RetrofitHelper.getInstence().getApiService().checkCode(mobile, code, lon, lat, make, openid, unionid);
        call.enqueue(new Callback<ResultDO<RegisterNewBean>>() {
            @Override
            public void onResponse(Call<ResultDO<RegisterNewBean>> call, Response<ResultDO<RegisterNewBean>> response) {
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultDO<RegisterNewBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void registerCheckCodeTwo(String mobile, int code, String lon, String lat, int make, final RegisterCheckCodeCallback callback) {
        Call<ResultDO<RegisterNewBean>> call = RetrofitHelper.getInstence().getApiService().checkCode2(mobile, code, lon, lat, make);
        call.enqueue(new Callback<ResultDO<RegisterNewBean>>() {
            @Override
            public void onResponse(Call<ResultDO<RegisterNewBean>> call, Response<ResultDO<RegisterNewBean>> response) {
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultDO<RegisterNewBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }

    public void getAreaList(final GetAreaGroupCallback callback) {
        Call<ResultDO<List<AreaBean>>> call = RetrofitHelper.getInstence().getApiService().getAreaList();
        call.enqueue(new Callback<ResultDO<List<AreaBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<AreaBean>>> call, Response<ResultDO<List<AreaBean>>> response) {
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultDO<List<AreaBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }


    public void registerV3(String mobile, String lon, String lat, int make, String unionid, String openid, int city_pid, String city, final RegisterCallback callback) {
        Call<ResultDO<RegisterBean>> call = RetrofitHelper.getInstence().getApiService().registerV3(mobile, lon, lat, make, unionid, openid, city_pid, city);
        call.enqueue(new Callback<ResultDO<RegisterBean>>() {
            @Override
            public void onResponse(Call<ResultDO<RegisterBean>> call, Response<ResultDO<RegisterBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }

            }

            @Override
            public void onFailure(Call<ResultDO<RegisterBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });


    }


    public void getNewIndexList(final GetMainCallback callback) {
        Call<ResultDO<MainBean>> call = RetrofitHelper.getInstence().getApiService().getNewIndexList();
        call.enqueue(new Callback<ResultDO<MainBean>>() {
            @Override
            public void onResponse(Call<ResultDO<MainBean>> call, Response<ResultDO<MainBean>> response) {
                callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResultDO<MainBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK);
            }
        });
    }


    public void refreshToken(final AllResultDoCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().refreshToken(token, userId);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData().toString());
                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    public void releaseCheck(int p_id, final GetReleaseCheckCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO> call = RetrofitHelper.getInstence().getApiService().releaseCheck(token, userId, p_id);
        call.enqueue(new Callback<ResultDO>() {
            @Override
            public void onResponse(Call<ResultDO> call, Response<ResultDO> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {

                }
                if (response.body().getCode() == ResponseCode.OK) {
                    callback.onSuccess();

                } else {

                }
            }

            @Override
            public void onFailure(Call<ResultDO> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg, e.data);
                    return;
                }
                callback.onFailed(ResponseCode.NO_NETWORK_CODE, NO_NETWOEK, "");
            }
        });
    }


    public void getActivityColumnList(final GetAvtivityColumnCallback callback) {
        Call<ResultDO<ActivityColumnListBean>> call = RetrofitHelper.getInstence().getApiService().getActivityColumnList();
        call.enqueue(new Callback<ResultDO<ActivityColumnListBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ActivityColumnListBean>> call, Response<ResultDO<ActivityColumnListBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ActivityColumnListBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    public void getActivityList(int category_id, int is_duplicates, int page, final GetActivityListCallback callback) {
        Call<ResultDO<ActivityListBean>> call = RetrofitHelper.getInstence().getApiService().getActivityList(category_id, is_duplicates, page);
        call.enqueue(new Callback<ResultDO<ActivityListBean>>() {
            @Override
            public void onResponse(Call<ResultDO<ActivityListBean>> call, Response<ResultDO<ActivityListBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<ActivityListBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    //获取潜在客户
    public void getPotentialList(final GetRecommendListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<List<RecommendBean>>> call = RetrofitHelper.getInstence().getApiService().getPotentialList(token, userId);
        call.enqueue(new Callback<ResultDO<List<RecommendBean>>>() {
            @Override
            public void onResponse(Call<ResultDO<List<RecommendBean>>> call, Response<ResultDO<List<RecommendBean>>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<List<RecommendBean>>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }


    //获取沟通记录
    public void getCommunicationRecordList(int type,int time,int page,final GetCommunicationRecordListCallback callback) {
        String token = UserInfoHelper.getIntance().getToken();
        int userId = UserInfoHelper.getIntance().getUserId();
        Call<ResultDO<CommunicationRecordBean.DataBean>> call = RetrofitHelper.getInstence().getApiService().getCommunicationRecordList(token, userId,time,type,page);
        call.enqueue(new Callback<ResultDO<CommunicationRecordBean.DataBean>>() {
            @Override
            public void onResponse(Call<ResultDO<CommunicationRecordBean.DataBean>> call, Response<ResultDO<CommunicationRecordBean.DataBean>> response) {
                if (response == null) {
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                if (response.body().getCode() == Constants.NET_SUCCESS_CODE && response.body().getData() != null) {
                    callback.onSuccess(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ResultDO<CommunicationRecordBean.DataBean>> call, Throwable t) {
                if (t instanceof ResultException) {
                    ResultException e = (ResultException) t;
                    callback.onFailed(e.code, e.msg);
                    return;
                }
            }
        });
    }
}
