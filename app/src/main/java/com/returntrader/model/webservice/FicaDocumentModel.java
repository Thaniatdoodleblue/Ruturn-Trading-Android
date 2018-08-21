package com.returntrader.model.webservice;

import android.text.TextUtils;
import android.util.Log;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.FicaDocumentRequest;
import com.returntrader.model.dto.response.FicaDocumentUploadResponse;
import com.returntrader.model.listener.IBaseModelListener;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by moorthy on 7/21/2017.
 */


public class FicaDocumentModel extends BaseRetroFitModel<FicaDocumentUploadResponse> {

    private String TAG = getClass().getSimpleName();

    private IBaseModelListener<FicaDocumentUploadResponse> iBaseModelListener;


    public FicaDocumentModel(IBaseModelListener<FicaDocumentUploadResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void submitGreenCard(int taskId, FicaDocumentRequest ficaDocumentRequest) {
        MultipartBody.Part fileDataFront = getMultiFormData(ficaDocumentRequest.getFrontFile(), "front");
        MultipartBody.Part fileDataBack = getMultiFormData(ficaDocumentRequest.getBackFile(), "back");
        RequestBody userId = getRequestBody(ficaDocumentRequest.getUserId());
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).submitGreenCard(userId, fileDataFront, fileDataBack));
    }

    public void submitIdCard(int taskId, FicaDocumentRequest ficaDocumentRequest) {
        MultipartBody.Part fileData = getMultiFormData(ficaDocumentRequest.getFrontFile(), "photo");
        RequestBody userId = getRequestBody(ficaDocumentRequest.getUserId());
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).submitIdCard(userId, fileData));
    }

    public void submitBankStatement(int taskId, FicaDocumentRequest ficaDocumentRequest) {
        MultipartBody.Part fileData = getMultiFormData(ficaDocumentRequest.getFrontFile(), "address");
        RequestBody userId = getRequestBody(ficaDocumentRequest.getUserId());
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).submitBankStatement(userId, fileData));
    }

    @Override
    public void onSuccessfulApi(long taskId, FicaDocumentUploadResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
