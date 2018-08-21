package com.returntrader.model.webservice;

import android.util.Log;

import com.returntrader.library.CustomException;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.executor.CustomService;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import retrofit2.Call;

public class CustomTwitterApiClient<T> extends TwitterApiClient {


    private String TAG = getClass().getSimpleName();
    private IBaseModelListener<T> iResponseListener;
    private long mCurrentTaskId;

    public CustomTwitterApiClient(TwitterSession session) {
        super(session);

    }


    public void setResponseListener(IBaseModelListener<T> iResponseListener) {
        this.iResponseListener = iResponseListener;
    }

    public void enQueueTask(long taskId, Call<T> tCall) {
        this.mCurrentTaskId = taskId;
        tCall.enqueue(baseModelCallBackListener);
    }


    private Callback<T> baseModelCallBackListener = new Callback<T>() {
        @Override
        public void success(Result<T> result) {
            iResponseListener.onSuccessfulApi(mCurrentTaskId,result.data);
        }

        @Override
        public void failure(TwitterException exception) {
            Log.d(TAG,"exception : "  +exception);
            iResponseListener.onFailureApi(mCurrentTaskId,new CustomException(201,exception.getMessage()));
        }
    };

    public CustomService getCustomService() {
        return getService(CustomService.class);
    }
}


