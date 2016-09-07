package com.example.stefan.rxjava.subscribers;

import android.content.Context;
import android.widget.Toast;

import com.example.stefan.rxjava.progress.ProgressDialogCancler;
import com.example.stefan.rxjava.progress.ProgressHandler;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by Stefan on 2016/7/21.
 */
public class ProgressSubcribers<T> extends Subscriber<T> implements ProgressDialogCancler {

    private ProgressHandler progressHandler;
    ProgressSubcribersListen progressSubcribersListen;
    private Context context;

    public ProgressSubcribers(ProgressSubcribersListen progressSubcribersListen, Context context) {
        this.context = context;
        this.progressSubcribersListen = progressSubcribersListen;
        progressHandler = new ProgressHandler(context, this, true);

    }

    private void showDialog() {
        if (progressHandler != null) {
            progressHandler.obtainMessage(ProgressHandler.SHOW_PROGRESS).sendToTarget();
        }
    }

    private void dismissDialog() {
        if (progressHandler != null) {
            progressHandler.obtainMessage(ProgressHandler.DISS_PROGRESS).sendToTarget();
            progressHandler = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        showDialog();
    }

    @Override
    public void onCancelProgress() {
        if (this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    @Override
    public void onCompleted() {
        dismissDialog();
        Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "error....:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        dismissDialog();
    }

    @Override
    public void onNext(T t) {
        if (progressSubcribersListen != null){
            progressSubcribersListen.onNext(t);
        }

    }
}
