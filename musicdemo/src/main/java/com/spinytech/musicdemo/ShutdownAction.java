package com.spinytech.musicdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.spinytech.macore.MaAction;
import com.spinytech.macore.router.ResponseCallback;
import com.spinytech.macore.router.RouterResponse;

import java.util.HashMap;

/**
 * Created by wanglei on 2016/12/28.
 */

public class ShutdownAction extends MaAction {


    @Override
    public boolean isAsync(Context context, Bundle requestData) {
        return true;
    }

    @Override
    public void invoke(Context context, Bundle requestData, ResponseCallback callback) {
        RouterResponse result = new RouterResponse.Builder()
                .code(RouterResponse.CODE_SUCCESS)
                .msg("success")
                .build();
        context.getApplicationContext().stopService(new Intent(context,MusicService.class));

        callback.onInvoke(requestData, result);
    }
}
