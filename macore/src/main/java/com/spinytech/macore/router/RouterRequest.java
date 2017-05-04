package com.spinytech.macore.router;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.spinytech.macore.tools.Logger;
import com.spinytech.macore.tools.ProcessUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wanglei on 2016/12/27.
 */

public class RouterRequest {
    private static final String TAG = "RouterRequest";
    private static volatile String DEFAULT_PROCESS = "";
    private String from;
    private String domain;
    private String provider;
    private String action;
    private Bundle data;
    AtomicBoolean isIdle = new AtomicBoolean(true);

    private static final int length = 64;
    private static AtomicInteger sIndex = new AtomicInteger(0);
    private static final int RESET_NUM = 1000;
    private static volatile RouterRequest[] table = new RouterRequest[length];

    static {
        for (int i = 0; i < length; i++) {
            table[i] = new RouterRequest();
        }
    }

    private RouterRequest() {
        this.from = DEFAULT_PROCESS;
        this.domain = DEFAULT_PROCESS;
        this.provider = "";
        this.action = "";
        this.data = new Bundle();
    }


    private RouterRequest(Context context) {
        this.from = getProcess(context);
        this.domain = getProcess(context);
        this.provider = "";
        this.action = "";
        this.data = new Bundle();
    }

    private RouterRequest(Builder builder) {
        this.from = builder.mFrom;
        this.domain = builder.mDomain;
        this.provider = builder.mProvider;
        this.action = builder.mAction;
        this.data = builder.mData;
    }

    public String getFrom() {
        return from;
    }

    public String getDomain() {
        return domain;
    }

    public String getProvider() {
        return provider;
    }

    public String getAction() {
        return action;
    }

    public Bundle getData() {
        return data;
    }

    private static String getProcess(Context context) {
        if (TextUtils.isEmpty(DEFAULT_PROCESS) || ProcessUtil.UNKNOWN_PROCESS_NAME.equals(DEFAULT_PROCESS)) {
            DEFAULT_PROCESS = ProcessUtil.getProcessName(context, ProcessUtil.getMyProcessId());
        }
        return DEFAULT_PROCESS;
    }

    public RouterRequest domain(String domain) {
        this.domain = domain;
        return this;
    }


    public RouterRequest provider(String provider) {
        this.provider = provider;
        return this;
    }


    public RouterRequest action(String action) {
        this.action = action;
        return this;
    }


    public RouterRequest data(Bundle data) {
        this.data = data;
        return this;
    }

    public static RouterRequest obtain(Context context) {
        return obtain(context, 0);
    }

    private static RouterRequest obtain(Context context, int retryTime) {
        int index = sIndex.getAndIncrement();
        if (index > RESET_NUM) {
            sIndex.compareAndSet(index, 0);
            if (index > RESET_NUM * 2) {
                sIndex.set(0);
            }
        }

        int num = index % (length - 1);

        RouterRequest target = table[num];

        if (target.isIdle.compareAndSet(true, false)) {
            target.from = getProcess(context);
            target.domain = getProcess(context);
            target.provider = "";
            target.action = "";
            target.data.clear();
            return target;
        } else {
            if (retryTime < 5) {
                return obtain(context, retryTime++);
            } else {
                return new RouterRequest(context);
            }

        }
    }

    @Deprecated
    public static class Builder {
        private String mFrom;
        private String mDomain;
        private String mProvider;
        private String mAction;
        private Bundle mData;

        public Builder(Context context) {
            mFrom = getProcess(context);
            mDomain = getProcess(context);
            mProvider = "";
            mAction = "";
            mData = new Bundle();
        }

        public Builder url(String url) {
            int questIndex = url.indexOf('?');
            String[] urls = url.split("\\?");
            if (urls.length != 1 && urls.length != 2) {
                Logger.e(TAG, "The url is illegal.");
                return this;
            }
            String[] targets = urls[0].split("/");
            if (targets.length == 3) {
                this.mDomain = targets[0];
                this.mProvider = targets[1];
                this.mAction = targets[2];
            } else {
                Logger.e(TAG, "The url is illegal.");
                return this;
            }
            //Add params
            if (questIndex != -1) {
                String queryString = urls[1];
                if (queryString != null && queryString.length() > 0) {
                    int ampersandIndex, lastAmpersandIndex = 0;
                    String subStr, key, value;
                    String[] paramPair, values, newValues;
                    do {
                        ampersandIndex = queryString.indexOf('&', lastAmpersandIndex) + 1;
                        if (ampersandIndex > 0) {
                            subStr = queryString.substring(lastAmpersandIndex, ampersandIndex - 1);
                            lastAmpersandIndex = ampersandIndex;
                        } else {
                            subStr = queryString.substring(lastAmpersandIndex);
                        }
                        paramPair = subStr.split("=");
                        key = paramPair[0];
                        value = paramPair.length == 1 ? "" : paramPair[1];
                        try {
                            value = URLDecoder.decode(value, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        mData.putString(key, value);
                    } while (ampersandIndex > 0);
                }
            }
            return this;
        }

        public Builder domain(String domain) {
            this.mDomain = domain;
            return this;
        }


        public Builder provider(String provider) {
            this.mProvider = provider;
            return this;
        }


        public Builder action(String action) {
            this.mAction = action;
            return this;
        }


        public Builder data(Bundle data) {
            this.mData = data;
            return this;
        }

        public RouterRequest build() {
            return new RouterRequest(this);
        }
    }

}
