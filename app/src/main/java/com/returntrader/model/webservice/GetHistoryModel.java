package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.HistoryRequest;
import com.returntrader.model.dto.response.HistoryResponse;
import com.returntrader.model.dto.response.NewsResponse;
import com.returntrader.model.listener.IBaseModelListener;

import retrofit2.Call;

/**
 * Created by moorthy on 7/21/2017.
 */

public class GetHistoryModel extends BaseRetroFitModel<HistoryResponse> {


    private IBaseModelListener<HistoryResponse> iBaseModelListener;

    public GetHistoryModel(IBaseModelListener<HistoryResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void getHistoryLogs(long taskId, HistoryRequest historyRequest) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        enQueueTask(taskId, apiService.getHistoryData(historyRequest));
    }

    @Override
    public void onSuccessfulApi(long taskId, HistoryResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
