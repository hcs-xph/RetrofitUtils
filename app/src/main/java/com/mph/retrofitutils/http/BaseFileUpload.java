package com.mph.retrofitutils.http;

import com.mph.retrofitutils.RetrofitService;

import retrofit2.Call;

/**
 * 文件上传基类
 * Created by：hcs on 2016/10/20 11:29
 * e_mail：aaron1539@163.com
 */
public abstract class BaseFileUpload {

    public abstract Call getFileUploadCall(RetrofitService retrofitService);
}
