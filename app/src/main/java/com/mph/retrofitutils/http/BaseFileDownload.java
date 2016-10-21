package com.mph.retrofitutils.http;

import com.mph.retrofitutils.RetrofitService;

import retrofit2.Call;

/**
 * 文件下载请求基类
 * Created by：hcs on 2016/10/20 14:10
 * e_mail：aaron1539@163.com
 */
public abstract class BaseFileDownload {
    public abstract Call getFileDownloadCall(RetrofitService retrofitService);
}
