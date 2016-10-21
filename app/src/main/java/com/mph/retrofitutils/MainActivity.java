package com.mph.retrofitutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mph.retrofitutils.http.FileRequestBody;
import com.mph.retrofitutils.http.RetrofitCallback;
import com.mph.retrofitutils.http.RetrofitFileUtils;
import com.mph.retrofitutils.http.RetrofitUtils;
import com.mph.retrofitutils.subscribers.ProgressSubscriber;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mPost;
    private Button mGet;
    private Button upload;
    private Button download;
    private Button crash;
    String s = null ;

    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPost = (Button) findViewById(R.id.post);
        mGet = (Button) findViewById(R.id.get);
        upload = (Button) findViewById(R.id.upload);
        download = (Button) findViewById(R.id.download);
        crash = (Button) findViewById(R.id.crash);
        mResultTextView = (TextView) findViewById(R.id.result);

        mPost.setOnClickListener(this);
        mGet.setOnClickListener(this);
        upload.setOnClickListener(this);
        download.setOnClickListener(this);
        crash.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.post:
                RetrofitUtils.getInstance().doHttp(new DiseaseDetailPost(new ProgressSubscriber<DiseaseDetail>(MainActivity.this) {
                    @Override
                    public void onNext(DiseaseDetail diseaseDetail) {
                        mResultTextView.setText(diseaseDetail.toString());
                    }
                },7937));
                break;

            case R.id.get:
                break;

            case R.id.upload:
                Map<String,Object> map = new HashMap<>();
                map.put("address","");
                map.put("gender","");
                map.put("height",42);
                map.put("weight",21);
                map.put("realname","fsd");
                map.put("waist",43);
                map.put("userid",285);
                File file = new File("/sdcard/retrofittest/upload.png");
                RequestBody body1 = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
                RetrofitCallback<User> callback = new RetrofitCallback<User>() {
                    @Override
                    public void onSuccess(Call<User> call, Response<User> response) {
                        Log.d("debug","返回结果--》"+response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onLoading(final long total, final long progress) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("debug","总大小--》"+total+"进度-->"+progress);
                                if(total!=progress){
                                    mResultTextView.setText("总大小--》"+total+",,进度-->"+progress);
                                }else {
                                    mResultTextView.setText("上传成功");
                                }
                            }
                        });

                    }
                };
                FileRequestBody body = new FileRequestBody(body1, callback);
                MultipartBody.Part part = MultipartBody.Part.createFormData("avatarByte",file.getName(),body);
                FileUploadEntity entity = new FileUploadEntity(part,map);
                RetrofitFileUtils.uploadFile(entity, callback);
                break;

            case R.id.download:
                FileDownloadEntity downloadEntity = new FileDownloadEntity("yingyongbao");
                RetrofitFileUtils.downloadFile(downloadEntity, new RetrofitCallback<ResponseBody>() {
                    @Override
                    public void onSuccess(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            InputStream is = response.body().byteStream();
                            String path = "/sdcard/retrofittest";
                            File f = new File(path);
                            if(!f.exists()){
                                f.mkdirs();
                            }
                            File file = new File(f, "download.apk");
                            FileOutputStream fos = new FileOutputStream(file);
                            BufferedInputStream bis = new BufferedInputStream(is);
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = bis.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                            }
                            fos.flush();
                            fos.close();
                            bis.close();
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onLoading(final long total, final long progress) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("debug","总大小--》"+total+"下载进度-->"+progress);
                                if(total!=progress){
                                    mResultTextView.setText("总大小--》"+total+",,下载进度-->"+progress);
                                }else{
                                    mResultTextView.setText("下载成功");
                                }
                            }
                        });
                    }
                });
                break;

            case R.id.crash:
                try {
                    s.toString();
                }catch (Exception e){
                    throw new  RuntimeException(e);
                }
                break;
        }
    }
}
