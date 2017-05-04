package com.spinytech.maindemo;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.spinytech.macore.MaAction;
import com.spinytech.macore.router.ResponseCallback;
import com.spinytech.macore.router.RouterResponse;

import java.util.HashMap;

/**
 * Created by wanglei on 2016/12/28.
 */

public class AsyncAction extends MaAction {

    @Override
    public boolean isAsync(Context context, Bundle requestData) {
        return true;
    }

    @Override
    public void invoke(Context context, Bundle requestData, ResponseCallback callback) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String temp = "";
        if(!TextUtils.isEmpty(requestData.getString("key1"))){
            temp+=requestData.get("key1");
        }
        if(!TextUtils.isEmpty(requestData.getString("key2"))){
            temp+=requestData.get("key2");
        }
        Log.e("AsyncAction",temp);
        Bundle bundle = new Bundle();
        bundle.putString("result", temp);
        RouterResponse result = new RouterResponse.Builder()
                .code(RouterResponse.CODE_SUCCESS)
                .msg("success")
                .data(bundle)
                .build();
        callback.onInvoke(requestData, result);
    }
}
