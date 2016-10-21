package com.mph.retrofitutils.http;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 自定义文件下载ResponseBody
 * Created by：hcs on 2016/10/20 14:17
 * e_mail：aaron1539@163.com
 */
public final class FileResponseBody<T> extends ResponseBody {

    /**
     * 实际请求体
     */
    private ResponseBody responseBody;

    /**
     * 下载回调接口
     */
    private RetrofitCallback<T> callback;

    private BufferedSource bufferedSource;

    public FileResponseBody(ResponseBody responseBody,RetrofitCallback<T> callback){
        super();
        this.responseBody = responseBody;
        this.callback = callback;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if(bufferedSource == null){
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source){
        return new ForwardingSource(source) {

            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink,byteCount);
                totalBytesRead += bytesRead!=-1 ?bytesRead:0;
                callback.onLoading(responseBody.contentLength(),totalBytesRead);
                return bytesRead;
            }
        };
    }
}
