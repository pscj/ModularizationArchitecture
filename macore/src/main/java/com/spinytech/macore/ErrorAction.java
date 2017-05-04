package com.spinytech.macore;

import android.content.Context;
import android.os.Bundle;

import com.spinytech.macore.router.ResponseCallback;
import com.spinytech.macore.router.RouterResponse;

import java.util.HashMap;

/**
 * Created by wanglei on 2016/12/28.
 */

public class ErrorAction extends MaAction {

    private static final String DEFAULT_MESSAGE = "Something was really wrong. Ha ha!";
    private int mCode;
    private String mMessage;
    private boolean mAsync;

    public ErrorAction() {
        mCode = RouterResponse.CODE_ERROR;
        mMessage = DEFAULT_MESSAGE;
        mAsync = false;
    }

    public ErrorAction(boolean isAsync,int code, String message) {
        this.mCode = code;
        this.mMessage = message;
        this.mAsync = isAsync;
    }

    @Override
    public boolean isAsync(Context context, Bundle requestData) {
        return mAsync;
    }

    @Override
    public void invoke(Context context, Bundle requestData, ResponseCallback callback) {
        RouterResponse result = new RouterResponse.Builder()
                .code(mCode)
                .msg(mMessage)
                .build();
        callback.onInvoke(requestData, result);
    }
}
