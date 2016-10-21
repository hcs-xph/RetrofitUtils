package com.mph.retrofitutils.http;

import com.mph.retrofitutils.RetrofitService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 文件下载工具类
 * Created by：hcs on 2016/10/20 14:48
 * e_mail：aaron1539@163.com
 */
public class RetrofitFileUtils {

    public static final String BASE_URL = RetrofitService.BASE_URL;

    private RetrofitFileUtils(){
    }

    private static <T> RetrofitService getRetrofitService(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        return retrofitService;
    }

    private static <T> RetrofitService getRetrofitService(final RetrofitCallback<T> callback){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response proceed = chain.proceed(chain.request());
                return proceed.newBuilder().body(new FileResponseBody<T>(proceed.body(),callback)).build();
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        return retrofitService;
    }

    /**
     * 文件上传
     * @param baseFileUpload
     * @param callback
     */
    public static <T> Call uploadFile(BaseFileUpload baseFileUpload, RetrofitCallback<T> callback){
        Call call = baseFileUpload.getFileUploadCall(getRetrofitService());
        call.enqueue(callback);
        return call;
    }

    /**
     * 文件下载
     * @param baseFileDownload
     * @param callback
     */
    public static <T> Call downloadFile(BaseFileDownload baseFileDownload,RetrofitCallback<T> callback){
        Call call = baseFileDownload.getFileDownloadCall(getRetrofitService(callback));
        call.enqueue(callback);
        return call;
    }

}
