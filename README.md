# RetrofitUtils
RxJava + Retrofit封装，包含对相同格式请求数据、相同格式返回数据处理，显示Material Design加载dialog，文件上传下载进度展示、全局异常捕捉。
# Preview
![image](https://github.com/hcs-xph/RetrofitUtils/blob/master/screen/retrofitutils.gif)<br/>
演示中upload.png为上传图片源文件。
# Describe
实现功能：<br/>
1、对于相同格式请求数据统一处理；<br/>
2、对于相同返回结果进行预处理；<br/>
3、显示加载dialog;<br/>
4、支持文件带进度上传下载。<br/><br/>
主要几个类作用：<br/>
1、[RetrofitUtils.java](https://github.com/hcs-xph/RetrofitUtils/blob/master/app/src/main/java/com/mph/retrofitutils/http/RetrofitUtils.java)
主要是数据post、get等请求工具类，所有请求调用doHttp()方法。<br/>
2、[RetrofitFileUtils.java](https://github.com/hcs-xph/RetrofitUtils/blob/master/app/src/main/java/com/mph/retrofitutils/http/RetrofitFileUtils.java)
主要用于文件下载工具类，包含uploadFile()文件上传、downloadFile()文件下载两个方法。<br/>
3、[BaseFileDownload.java](https://github.com/hcs-xph/RetrofitUtils/blob/master/app/src/main/java/com/mph/retrofitutils/http/BaseFileDownload.java)
文件下载请求基类，文件下载请求实体类需继承该类实现内部方法。<br/>
4、[BaseFileUpload.java](https://github.com/hcs-xph/RetrofitUtils/blob/master/app/src/main/java/com/mph/retrofitutils/http/BaseFileUpload.java)
文件上传请求基类，文件上传请求实体类需继承该类实现内部方法。<br/>
5、[HttpResultFunc.java](https://github.com/hcs-xph/RetrofitUtils/blob/master/app/src/main/java/com/mph/retrofitutils/http/HttpResultFunc.java)
基本数据请求基类，数据请求实体类需继承该类实现内部方法。<br/>
6、[RetrofitService.java](https://github.com/hcs-xph/RetrofitUtils/blob/master/app/src/main/java/com/mph/retrofitutils/RetrofitService.java)
主要用于管理请求接口，设置请求地址等。如需修改类名请在调用地方相应进行修改。
#Usage
使用方法，这里主要以post请求为例；<br/>
1、在RetrofitService.java中配置请求地址：<br/>
```java
    @FormUrlEncoded
    @POST("diseaseDBapi/getDisease")
    Observable<ResponseResult<DiseaseDetail>> getDiseaseDetail(@Field("diseaseId") int id);
```
2、定义一个类继承HttpResultFunc.java
```java
public class DiseaseDetailPost extends HttpResultFunc {

    private Subscriber subscriber;//带加载进度的Subscriber
    private int id;//请求参数，如有更多请求字段相应添加即可
    
    public DiseaseDetailPost(Subscriber subscriber, int id) {
        this.subscriber = subscriber;
        this.id = id;
    }

    @Override
    public Observable getObservable(RetrofitService retrofitService) {
        return retrofitService.getDiseaseDetail(id);//调用请求方法
    }

    @Override
    public Subscriber getSubscriber() {
        return subscriber;
    }

}
```
3、现在就可以进行调用了。
```java
RetrofitUtils.getInstance().doHttp(new DiseaseDetailPost(new ProgressSubscriber<DiseaseDetail>(MainActivity.this) {
                    @Override
                    public void onNext(DiseaseDetail diseaseDetail) {
                        mResultTextView.setText(diseaseDetail.toString());
                    }
                },7937));
```
这里就演示这个例子了，更多使用方法可以直接看demo。
<br/>最后非常感谢[RxJava 与 Retrofit 结合的最佳实践](https://gank.io/post/56e80c2c677659311bed9841)和[Retrofit2文件上传下载及其进度显示](http://blog.csdn.net/huyongl1989/article/details/52619236)
## **About Me**
[http://ivast.cn](http://ivast.cn)
