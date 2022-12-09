package com.xinniu.android.qiqueqiao.request;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by lzq on 2017/10/19.
 */

public class RetrofitHelper {
    private okhttp3.OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private static volatile RetrofitHelper mRetrofitHelper;
        public static String API_URL = "https://q.qiqueqiao.com/";//正式
//   public static String API_URL = "https://t.qiqueqiao.com/";


    public static RetrofitHelper getInstence() {
        if (mRetrofitHelper == null) {
            synchronized (RetrofitHelper.class) {
                if (mRetrofitHelper == null) {
                    mRetrofitHelper = new RetrofitHelper();
                }
            }
        }
        return mRetrofitHelper;
    }

    private RetrofitHelper() {
        if (mOkHttpClient == null) {
            okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder();

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);

            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("version", RequestManager.vertion)
                            .addHeader("equipment", ApiService.equipment)
                            .build();
                    return chain.proceed(request);

                }
            };

            builder.addInterceptor(interceptor);
//            builder.cache(cache);
            //设置超时
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
//            builder.writeTimeout(30, TimeUnit.SECONDS);
            //错误重连
//            builder.retryOnConnectionFailure(true);
            mOkHttpClient = builder.build();
        }
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ResponseConverterFactory.create())
                    .baseUrl(API_URL)
                    .client(mOkHttpClient)
                    .build();
        }
    }

    public ApiService getApiService() {
        return mRetrofit.create(ApiService.class);
    }
}

