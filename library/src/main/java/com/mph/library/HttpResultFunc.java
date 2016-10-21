package com.mph.library;

import com.mph.library.entity.ResponseResult;
import com.mph.library.exception.ResultErrorException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
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

