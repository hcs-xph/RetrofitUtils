package com.mph.retrofitutils;

import com.mph.retrofitutils.http.BaseFileDownload;

import retrofit2.Call;

/**
 * 测试下载文件请求实体类(具体更具需求变更)
 * 必须继承BaseFileDownload
 * Created by：hcs on 2016/10/21 11:50
 * e_mail：aaron1539@163.com
 */
public class FileDownloadEntity extends BaseFileDownload {

    private String name;

    public FileDownloadEntity(String name) {
        this.name = name;
    }

    @Override
    public Call getFileDownloadCall(RetrofitService retrofitService) {
        return retrofitService.download(name);
    }
}
