package com.spinytech.macore.router;


import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wanglei on 16/6/14.
 */
public class RouterResponse {
    public static final int CODE_SUCCESS = 0x0000;
    public static final int CODE_ERROR = 0x0001;
    public static final int CODE_NOT_FOUND = 0X0002;
    public static final int CODE_INVALID = 0X0003;
    public static final int CODE_ROUTER_NOT_REGISTER = 0X0004;
    public static final int CODE_CANNOT_BIND_LOCAL = 0X0005;
    public static final int CODE_REMOTE_EXCEPTION = 0X0006;
    public static final int CODE_CANNOT_BIND_WIDE = 0X0007;
    public static final int CODE_TARGET_IS_WIDE = 0X0008;
    public static final int CODE_WIDE_STOPPING = 0X0009;
    public static final int CODE_NOT_IMPLEMENT = 0X000a;

    private int code;
    private String msg;
    private Bundle data;

    private RouterResponse(Builder builder) {
        this.code = builder.mCode;
        this.msg = builder.mMsg;
        this.data = builder.mData;
    }


    public Bundle getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static class Builder {
        private int mCode;
        private String mMsg;
        private Bundle mData;

        public Builder() {
            mCode = CODE_ERROR;
            mMsg = "";
            mData = null;
        }

        public Builder code(int code) {
            this.mCode = code;
            return this;
        }

        public Builder msg(String msg) {
            this.mMsg = msg;
            return this;
        }

        public Builder data(Bundle data) {
            this.mData = data;
            return this;
        }

        public RouterResponse build() {
            return new RouterResponse(this);
        }
    }
}
