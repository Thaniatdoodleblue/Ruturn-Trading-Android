package com.returntrader.model.webservice.executor;

import android.os.AsyncTask;

import com.returntrader.common.ExceptionTracker;
import com.returntrader.model.webservice.APIRequestDTO;
import com.returntrader.model.webservice.executor.controller.LocalServer;


/**
 * Created by Guru karthik on 26-11-2016.
 */

public class TaskManager<Response> extends AsyncTask<APIRequestDTO, Void, Response> {

    private OnNetworkCallListener<Response> mNetworkCallListener;
    private long mTaskId = -1;
    private Exception mThrowableException;

    public void startTask(long taskId, APIRequestDTO requestDTO, OnNetworkCallListener<Response> networkCallListener) {
        // TODO Auto-generated method stub
        this.mNetworkCallListener = networkCallListener;
        this.mTaskId = taskId;
        executeOnExecutor(THREAD_POOL_EXECUTOR, requestDTO);
    }

    public Response startTask(APIRequestDTO requestDTO) {
        try {
            return execute(requestDTO).get();
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    @Override
    protected Response doInBackground(APIRequestDTO... params) {
        // TODO Auto-generated method stub
        try {
            if (null != mNetworkCallListener) {
                return new LocalServer().getLocalData(params[0]);
            }
        } catch (Exception e) {
            this.mThrowableException = e;
            ExceptionTracker.track(e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        if (null != mNetworkCallListener) {
            if (null == mThrowableException) {
                mNetworkCallListener.onSuccess(mTaskId, response);
            } else {
                mNetworkCallListener.onFailure(mTaskId, mThrowableException);
            }
        }
    }
}
