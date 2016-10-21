package com.mph.retrofitutils.subscribers;

import android.content.Context;

import com.mph.retrofitutils.progress.HHProgressAlertDialog;
import com.mph.retrofitutils.progress.ProgressCancelListener;
import com.mph.retrofitutils.utils.ToastUtils;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by：hcs on 2016/10/17 16:38
 * e_mail：aaron1539@163.com
 */
public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private HHProgressAlertDialog progressAlertDialog;
    private ToastUtils toast;
    private boolean isShowProgress = true;
    private WeakReference<Context> contextWeakReference;

    /**
     *  默认显示弹出框，不可以取消
     * @param context
     */
    public ProgressSubscriber(Context context){
        contextWeakReference = new WeakReference<Context>(context);
        init(false);
    }

    /**
     * 可设置弹出框是否显示取消
     * @param context
     * @param isShowProgress 是否显示弹出框
     * @param isCancel 是否可以取消
     */
    public ProgressSubscriber(Context context, boolean isShowProgress, boolean isCancel){
        this.isShowProgress = isShowProgress;
        this.contextWeakReference = new WeakReference<Context>(context);
        init(isCancel);
    }

    /**
     * 初始化
     * @param isCancel
     */
    private void init(boolean isCancel) {
        Context context = contextWeakReference.get();
        progressAlertDialog = new HHProgressAlertDialog(context);
        progressAlertDialog.setCancelable(isCancel);
        toast = new ToastUtils(context);
    }

    private void showProgress(){
        if(!isShowProgress){
            return;
        }
        if(progressAlertDialog!=null){
            if(!progressAlertDialog.isShowing()){
                progressAlertDialog.show();
            }
        }
    }

    private void dismissProgress(){
        if(!isShowProgress){
            return;
        }
        if(progressAlertDialog!=null){
            if(progressAlertDialog.isShowing()){
                progressAlertDialog.dismiss();
            }
        }
    }

    @Override
    public void onStart() {
        showProgress();
    }

    @Override
    public void onCompleted() {
        dismissProgress();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            toast.showSingleToast("网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            toast.showSingleToast("网络中断，请检查您的网络状态");
        } else {
            toast.showSingleToast("error:" + e.getMessage());
        }
        dismissProgress();
    }

    @Override
    public void onCancelProgress() {
        if(!this.isUnsubscribed()){
            this.unsubscribe();
        }
    }
}
