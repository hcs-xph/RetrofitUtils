package com.mph.retrofitutils;

import com.mph.retrofitutils.DiseaseDetail;
import com.mph.retrofitutils.User;
import com.mph.retrofitutils.entity.ResponseResult;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 所有接口均写在该类下,类名不可修改，如需修改需在其他调用文件相应修改
 * Created by：hcs on 2016/10/19 11:07
 * e_mail：aaron1539@163.com
 */
public interface RetrofitService {

    //测试接口
    public static final String BASE_URL = "接口地址";

    @FormUrlEncoded
    @POST("diseaseDBapi/getDisease")
    Observable<ResponseResult<DiseaseDetail>> getDiseaseDetail(@Field("diseaseId") int id);

    @Multipart
    @POST("casuserroleapi/editUserInfo")
    Call<User> uploadFile(@Part MultipartBody.Part file, @PartMap Map<String,Object> map);

    @GET("edition/testDownload")
    Call<ResponseBody> download(@Query("name") String name);

}
