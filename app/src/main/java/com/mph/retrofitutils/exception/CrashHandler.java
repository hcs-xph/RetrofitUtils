package com.mph.retrofitutils.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 * Created by：hcs on 2016/10/14 14:12
 * e_mail：aaron1539@163.com
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    //程序的Context对象
    private Context mContext;
    //用来存储设备信息和异常信息
    private Map<String,String> infos = new HashMap<>();
    //用于格式化日期,作为日志文件名的一部分
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private CrashHandler(){}

    public static CrashHandler getInstance(){
        return INSTANCE;
    }

    public void init(Context context){
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     * @param thread
     * @param ex
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(!handleException(ex) && mDefaultHandler!=null){
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread,ex);
        }else{
            try {
                Thread.sleep(2000);
            }catch (InterruptedException e){
                Log.d(TAG,"error:",e);
            }
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告
     * @param ex true:处理了该异常信息返回true,否则返回false;
     * @return
     */
    private boolean handleException(Throwable ex) {
        if(ex==null){
            return false;
        }
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        collectDeviceInfo(mContext);
        saveCrashInfoToFile(ex);
        //TODO 上传log日志文件
        return true;
    }

    /**
     * 保存日志文件
     * @param ex
     * @return  返回文件名称,便于将文件传送到服务器
     */
    private String saveCrashInfoToFile(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key+"="+value+"\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause!=null){
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timeMillis = System.currentTimeMillis();
            String time = this.format.format(new Date());
            String fileName = "crash"+time+"-"+timeMillis+".log";
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                //TODO 替换成自己的保存日志文件目录
                String path = "/sdcard/retrofittest/";
                File dir = new File(path);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path+fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        }catch (Exception e){
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }

    /**
     * 收集设备信息
     * @param mContext
     */
    private void collectDeviceInfo(Context mContext) {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if(pi != null){
                String versionName = pi.versionName == null?"null":pi.versionName;
                String versionCode = pi.versionCode+"";
                infos.put("versionName",versionName);
                infos.put("versionCode",versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field :
                fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(),field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            }catch (Exception e){
                Log.d(TAG, "an error occured when collect crash info", e);
            }
        }
    }
}
