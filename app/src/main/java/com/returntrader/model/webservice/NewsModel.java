package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.NewsRequest;
import com.returntrader.model.dto.response.NewsResponse;
import com.returntrader.model.listener.IBaseModelListener;

import retrofit2.Call;

/**
 * Created by moorthy on 7/21/2017.
 */

public class NewsModel extends BaseRetroFitModel<NewsResponse> {


    private IBaseModelListener<NewsResponse> iBaseModelListener;

    public NewsModel(IBaseModelListener<NewsResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void getNews(long taskId, String url) {
        ApiInterface apiService = ApiThirdPartyClient.getClient().create(ApiInterface.class);
        Call<NewsResponse> call = apiService.callNewsData(url);
        enQueueTask(taskId, call);
    }

    public void getNews(long taskId, NewsRequest newsRequest) {
        ApiInterface apiService = ApiThirdPartyClient.getClient().create(ApiInterface.class);
        Call<NewsResponse> call = apiService.callNewsApi(newsRequest.getSource(), newsRequest.getSortBy(), newsRequest.getApiKey());
        enQueueTask(taskId, call);
    }

    @Override
    public void onSuccessfulApi(long taskId, NewsResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
