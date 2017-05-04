package com.spinytech.macore;

import android.content.Context;
import android.os.Bundle;

import com.spinytech.macore.router.ResponseCallback;
import com.spinytech.macore.router.RouterResponse;

/**
 * Created by wanglei on 2016/11/29.
 */

public abstract class MaAction {
    public abstract boolean isAsync(Context context, Bundle requestData);
    public abstract void invoke(Context context, Bundle requestData, ResponseCallback callback);
}
