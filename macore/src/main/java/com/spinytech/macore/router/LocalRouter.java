package com.spinytech.macore.router;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.spinytech.macore.ErrorAction;
import com.spinytech.macore.MaAction;
import com.spinytech.macore.MaApplication;
import com.spinytech.macore.MaProvider;
import com.spinytech.macore.tools.Logger;
import com.spinytech.macore.tools.ProcessUtil;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Local Router
 */

public class LocalRouter {
    private static final String TAG = "LocalRouter";
    private String mProcessName = ProcessUtil.UNKNOWN_PROCESS_NAME;
    private static LocalRouter sInstance = null;
    private HashMap<String, MaProvider> mProviders = null;
    private MaApplication mApplication;
    private static ExecutorService threadPool = null;


    private LocalRouter(MaApplication context) {
        mApplication = context;
        mProcessName = ProcessUtil.getProcessName(context, ProcessUtil.getMyProcessId());
        mProviders = new HashMap<>();
    }

    public static synchronized LocalRouter getInstance(@NonNull MaApplication context) {
        if (sInstance == null) {
            sInstance = new LocalRouter(context);
        }
        return sInstance;
    }

    private static synchronized ExecutorService getThreadPool() {
        if (null == threadPool) {
            threadPool = Executors.newCachedThreadPool();
        }
        return threadPool;
    }

    public void registerProvider(String providerName, MaProvider provider) {
        mProviders.put(providerName, provider);
    }

    public void route(Context context, @NonNull RouterRequest routerRequest){
        route(context, routerRequest, null);
    }

    public void route(Context context, @NonNull RouterRequest routerRequest, ResponseCallback callback){
        Logger.d(TAG, "Process:" + mProcessName + "\nLocal route start: " + System.currentTimeMillis());
        //RouterResponse routerResponse = new RouterResponse();
        // Local request
        if (mProcessName.equals(routerRequest.getDomain())) {
            Bundle params = new Bundle();
            params.putAll(routerRequest.getData());
            Logger.d(TAG, "Process:" + mProcessName + "\nLocal find action start: " + System.currentTimeMillis());
            MaAction targetAction = findRequestAction(routerRequest);
            routerRequest.isIdle.set(true);
            Logger.d(TAG, "Process:" + mProcessName + "\nLocal find action end: " + System.currentTimeMillis());
            boolean isAsync = targetAction.isAsync(context, params);

            if(isAsync){
                LocalTask task = new LocalTask(params, context, targetAction, callback);
                getThreadPool().submit(task);
            }else{
                if (callback == null){
                    callback = new ResponseCallback() {
                        @Override
                        public void onInvoke(Bundle requestData, RouterResponse response) {
                            Logger.d(TAG, "Process:" + mProcessName + "\nonInvoke without callback" + System.currentTimeMillis());
                        }
                    };
                }
                targetAction.invoke(context, params, callback);
            }
            //RouterResponse result = attachment == null ? targetAction.invoke(context, params) : targetAction.invoke(context, params, attachment);
            //routerResponse.mResultString = result.toString();
            //routerResponse.mObject = result.getObject();
            Logger.d(TAG, "Process:" + mProcessName + "\nLocal sync end: " + System.currentTimeMillis());
//            // Async result, use the thread pool to execute the task.
//            else {
//                LocalTask task = new LocalTask(routerResponse, params,attachment, context, targetAction);
//                routerResponse.mAsyncResponse = getThreadPool().submit(task);
//            }
        }
    }

    private MaAction findRequestAction(RouterRequest routerRequest) {
        MaProvider targetProvider = mProviders.get(routerRequest.getProvider());
        ErrorAction defaultNotFoundAction = new ErrorAction(false, RouterResponse.CODE_NOT_FOUND, "Not found the action.");
        if (null == targetProvider) {
            return defaultNotFoundAction;
        } else {
            MaAction targetAction = targetProvider.findAction(routerRequest.getAction());
            if (null == targetAction) {
                return defaultNotFoundAction;
            } else {
                return targetAction;
            }
        }
    }

    private class LocalTask implements Callable<String> {
        private Bundle mRequestData;
        private Context mContext;
        private MaAction mAction;
        private ResponseCallback callback;
        private ResponseCallback innnerCallback;
        private Handler handler;

        public LocalTask(Bundle requestData, Context context, MaAction maAction, final ResponseCallback callback) {
            this.mContext = context;
            this.mRequestData = requestData;
            this.mAction = maAction;
            this.callback = callback;
            handler = new Handler(Looper.myLooper());

            innnerCallback = new ResponseCallback() {
                @Override
                public void onInvoke(final Bundle requestData, final RouterResponse response) {
                    if( callback != null){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onInvoke(requestData,response);
                            }
                        });
                    }

                }
            };
        }

        @Override
        public String call() throws Exception {
            mAction.invoke(mContext, mRequestData, innnerCallback);
            Logger.d(TAG, "Process:" + mProcessName + "\nLocal async end: " + System.currentTimeMillis());
            return "";
        }
    }

}
