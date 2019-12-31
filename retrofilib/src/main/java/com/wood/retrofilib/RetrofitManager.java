package com.wood.retrofilib;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static RetrofitManager sInstance;
    private Retrofit mRetrofit;
    public static RetrofitManager getInstance() {
        if (null == sInstance) {
            synchronized (RetrofitManager.class) {
                if (null == sInstance) {
                    sInstance = new RetrofitManager();
                }
            }
        }
        return sInstance;
    }

    public void init(String baseUrl) {
        if(mRetrofit == null) {
            //初始化一个OkHttpClient
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(10000, TimeUnit.MILLISECONDS)
                    .readTimeout(10000, TimeUnit.MILLISECONDS)
                    .writeTimeout(10000, TimeUnit.MILLISECONDS);
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            builder.addInterceptor(interceptor);
            OkHttpClient okHttpClient = builder.build();

            //使用该OkHttpClient创建一个Retrofit对象
            mRetrofit = new Retrofit.Builder()
                    //添加Gson数据格式转换器支持
                    .addConverterFactory(GsonConverterFactory.create())
                    //添加RxJava语言支持
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    //指定网络请求client
                    .client(okHttpClient)
                    .baseUrl(baseUrl)
                    .build();
        }
    }

    public Retrofit getRetrofit() {
        if(mRetrofit == null) {
            throw  new IllegalStateException("Retrofit instance hasn't init!");
        }
        return mRetrofit;
    }
}
