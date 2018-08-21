package com.returntrader.model.webservice;


import android.text.TextUtils;
import android.util.Log;

import com.returntrader.common.Constants;
import com.returntrader.library.CustomException;
import com.returntrader.model.dto.response.BaseResponse;
import com.returntrader.model.listener.IBaseModelListener;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by guru on 1/6/2017.
 */

public abstract class BaseRetroFitModel<T extends BaseResponse> implements IBaseModelListener<T> {

    private long mCurrentTaskId = -1;
    private String TAG = getClass().getSimpleName();

    public BaseRetroFitModel() {
    }

    protected void enQueueTask(long taskId, Call<T> tCall) {
        this.mCurrentTaskId = taskId;
        tCall.enqueue(baseModelCallBackListener);
    }

    private Callback<T> baseModelCallBackListener = new Callback<T>() {
        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            Log.d(TAG, "status code : " + response.code());
            if (response.isSuccessful() && response.body() != null) {
                if (response.code() == Constants.InternalHttpCode.SUCCESS_WITH_CONTENT) {
                    Log.d(TAG, "success : " + response.code());
                    onSuccessfulApi(mCurrentTaskId, response.body());
                } else {
                    Log.d(TAG, "onFailureApi code > 200 : " + new CustomException(response.code(), response.body()).getException());
                    onFailureApi(mCurrentTaskId, new CustomException(response.code(), response.body()));
                }
            } else if (response.body() != null) {
                Log.d(TAG, "onFailureApi unsuccessfull + ");
                onFailureApi(mCurrentTaskId, new CustomException(response.code(), response.body()));
            } else {
                Log.d(TAG, "onFailureApi unknown");
                if (response.code() == 401) {
                    onFailureApi(mCurrentTaskId, new CustomException(401, Constants.HttpErrorMessage.SERVER_EXECUTION_ERROR));
                } else
                    onFailureApi(mCurrentTaskId, new CustomException(500, Constants.HttpErrorMessage.SERVER_EXECUTION_ERROR));
            }

        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            Log.d(TAG, "onFailure");
            onFailureApi(mCurrentTaskId, new CustomException(501, t.getLocalizedMessage()));
        }
    };


    /***/
    public MultipartBody.Part getMultiFormData(File file, String key) {
        MultipartBody.Part fileData = null;
        if (file != null) {
            fileData = MultipartBody.Part.createFormData(key, "doc" + System.currentTimeMillis() + ".jpg",
                    getRequestBody(file));
        }
        return fileData;
    }

    /***/
    public RequestBody getRequestBody(String data) {
        if (!(TextUtils.isEmpty(data))) {
            return RequestBody.create(MediaType.parse("text/plain"), data);
        }
        return null;
    }

    /***/
    public RequestBody getRequestBody(int data) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(data));
    }

    /***/
    public RequestBody getRequestBody(File data) {
        if (data != null) {
            return RequestBody.create(MediaType.parse("image/*"), data);
        }
        return null;
    }
}
