package com.example.stefan.rxjava.progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Stefan on 2016/7/21.
 */
public class ProgressHandler extends Handler {
    public static final int SHOW_PROGRESS = 1;
    public static final int DISS_PROGRESS = 2;

    ProgressDialog pd;
    Context context;
    private boolean cancelable;
    private ProgressDialogCancler progressDialogCancler;

    public ProgressHandler(Context context, ProgressDialogCancler progressDialogCancler, boolean cancelable) {
        super();
        this.cancelable = cancelable;
        this.context = context;
        this.progressDialogCancler = progressDialogCancler;
    }

    private void iniDialog() {
        if (pd == null) {
            pd = new ProgressDialog(context);
            pd.setCancelable(cancelable);
            if (cancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        progressDialogCancler.onCancelProgress();
                    }
                });
            }
            if (!pd.isShowing()) {
                pd.show();
            }
        }
    }

    private void dismissDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS:
                iniDialog();
                break;
            case DISS_PROGRESS:
                dismissDialog();
                break;
        }
    }
}
