package com.mph.retrofitutils.http;

import com.mph.retrofitutils.RetrofitService;
import com.mph.retrofitutils.entity.ResponseResult;
import com.mph.retrofitutils.exception.ResultErrorException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * 所有基本数据请求实体类基类，必须继承自该类
 * Created by：hcs on 2016/10/19 10:44
 * e_mail：aaron1539@163.com
 */
public abstract class HttpResultFunc<T> implements Func1<ResponseResult<T>,T> {

    @Override
    public T call(ResponseResult<T> tResponseResult) {
        if(tResponseResult.getStatus()!=0){
            throw  new ResultErrorException(tResponseResult.getMsg());
        }
        return tResponseResult.getData();
    }

    public abstract Observable getObservable(RetrofitService retrofitService);

    public abstract Subscriber getSubscriber();

}

