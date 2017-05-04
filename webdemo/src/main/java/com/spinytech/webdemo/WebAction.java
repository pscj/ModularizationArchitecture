package com.spinytech.webdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.spinytech.macore.MaAction;
import com.spinytech.macore.router.ResponseCallback;
import com.spinytech.macore.router.RouterResponse;

import java.util.HashMap;

/**
 * Created by wanglei on 2017/1/4.
 */

public class WebAction extends MaAction {

    @Override
    public boolean isAsync(Context context, Bundle requestData) {
        return false;
    }

    @Override
    public void invoke(Context context, Bundle requestData, ResponseCallback callback) {
        if(context instanceof Activity){
            Intent i = new Intent(context, WebActivity.class);
            context.startActivity(i);
        }else{
            Intent i = new Intent(context, WebActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
        callback.onInvoke(requestData, new RouterResponse.Builder().code(RouterResponse.CODE_SUCCESS).msg("success").build());
    }
}
