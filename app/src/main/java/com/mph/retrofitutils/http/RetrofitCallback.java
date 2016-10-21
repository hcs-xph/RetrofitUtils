package com.mph.retrofitutils.http;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 文件上传下载带进度回调
 * Created by：hcs on 2016/10/20 10:28
 * e_mail：aaron1539@163.com
 */
public abstract class RetrofitCallback<T> implements Callback<T>{
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()){
            onSuccess(call,response);
        }else {
            onFailure(call,new Throwable(response.message()));
        }
    }

    public abstract void onSuccess(Call<T> call,Response<T> response);

    public void onLoading(long total,long progress){}
}
