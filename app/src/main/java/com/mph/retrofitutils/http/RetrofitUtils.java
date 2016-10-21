package com.mph.retrofitutils.http;

import com.mph.retrofitutils.RetrofitService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 基本数据请求工具类
 * Created by：hcs on 2016/10/17 15:15
 * e_mail：aaron1539@163.com
 */
public class RetrofitUtils {
    public static final String BASE_URL = RetrofitService.BASE_URL;
    private static final int DEFAULT_TIMEOUT = 10;
    private Retrofit retrofit;
    private RetrofitService retrofitService;
    private static RetrofitUtils INSTANCE;

    private RetrofitUtils(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
    }

    public static RetrofitUtils getInstance(){
        if(INSTANCE == null){
            synchronized (RetrofitUtils.class){
                if(INSTANCE == null){
                    INSTANCE = new RetrofitUtils();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * post、get等请求
     * @param httpResultFunc
     */
    public void doHttp(HttpResultFunc httpResultFunc){
        httpResultFunc.getObservable(retrofitService)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(httpResultFunc)
                .subscribe(httpResultFunc.getSubscriber());
    }
}
