package com.spinytech.maindemo;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.spinytech.macore.MaAction;
import com.spinytech.macore.router.ResponseCallback;
import com.spinytech.macore.router.RouterResponse;

import java.util.HashMap;

/**
 * Created by wanglei on 2017/2/15.
 */

public class AttachObjectAction extends MaAction {

    @Override
    public boolean isAsync(Context context, Bundle requestData) {
        return false;
    }

    @Override
    public void invoke(Context context, Bundle requestData, ResponseCallback callback) {
//        if (object instanceof TextView) {
//            ((TextView) object).setText("The text is changed by AttachObjectAction.");
//            Toast returnToast = Toast.makeText(context, "I am returned Toast.", Toast.LENGTH_SHORT);
//            return new RouterResponse.Builder().code(RouterResponse.CODE_SUCCESS).msg("success").object(returnToast).build();
//
//        }
    }
}
