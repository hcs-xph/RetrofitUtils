package com.mph.retrofitutils;

import com.mph.retrofitutils.http.HttpResultFunc;

import rx.Observable;
import rx.Subscriber;

/**
 * 测试post请求实体类必须继承HttpResultFunc
 * 该类主要用于请求参数传递
 * Created by：hcs on 2016/10/19 11:32
 * e_mail：aaron1539@163.com
 */
public class DiseaseDetailPost extends HttpResultFunc {

    private Subscriber subscriber;
    private int id;

    public DiseaseDetailPost(Subscriber subscriber, int id) {
        this.subscriber = subscriber;
        this.id = id;
    }

    @Override
    public Observable getObservable(RetrofitService retrofitService) {
        return retrofitService.getDiseaseDetail(id);
    }

    @Override
    public Subscriber getSubscriber() {
        return subscriber;
    }

}
