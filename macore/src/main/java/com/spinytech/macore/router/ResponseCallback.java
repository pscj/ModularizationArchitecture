package com.spinytech.macore.router;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by chengjiang on 2017/5/4.
 */

public interface ResponseCallback {
    void onInvoke(Bundle requestData, RouterResponse response);
}
