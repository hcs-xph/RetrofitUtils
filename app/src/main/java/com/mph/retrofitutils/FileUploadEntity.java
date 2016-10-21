package com.mph.retrofitutils;

import com.mph.retrofitutils.http.BaseFileUpload;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * 测试文件上传请求实体类,必须继承BaseFileUpload
 * Created by：hcs on 2016/10/20 11:27
 * e_mail：aaron1539@163.com
 */
public class FileUploadEntity extends BaseFileUpload{

    private MultipartBody.Part file;
    private Map<String,Object> map;

    public FileUploadEntity(MultipartBody.Part file, Map<String,Object> map) {
        this.file = file;
        this.map = map;
    }

    @Override
    public Call getFileUploadCall(RetrofitService retrofitService) {
        return retrofitService.uploadFile(file,map);
    }
}
