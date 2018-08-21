package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.FavouriteStatusChangeRequest;
import com.returntrader.model.dto.response.FavouriteResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by moorthy on 7/21/2017.
 */

public class FavouriteModel extends BaseRetroFitModel<FavouriteResponse> {


    private IBaseModelListener<FavouriteResponse> iBaseModelListener;

    public FavouriteModel(IBaseModelListener<FavouriteResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void postFavouriteStatus(long taskId, FavouriteStatusChangeRequest favouriteStatusChangeRequest) {
        enQueueTask(taskId, ApiClient.getClient().create(ApiInterface.class).setFavouriteStatus(favouriteStatusChangeRequest));
    }

    @Override
    public void onSuccessfulApi(long taskId, FavouriteResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
