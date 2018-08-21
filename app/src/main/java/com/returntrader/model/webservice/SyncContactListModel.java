package com.returntrader.model.webservice;

import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.ContactSyncRequest;
import com.returntrader.model.dto.request.HistoryRequest;
import com.returntrader.model.dto.response.ContactSyncResponse;
import com.returntrader.model.dto.response.HistoryResponse;
import com.returntrader.model.listener.IBaseModelListener;

/**
 * Created by moorthy on 7/21/2017.
 */

public class SyncContactListModel extends BaseRetroFitModel<ContactSyncResponse> {


    private IBaseModelListener<ContactSyncResponse> iBaseModelListener;

    public SyncContactListModel(IBaseModelListener<ContactSyncResponse> iBaseModelListener) {
        this.iBaseModelListener = iBaseModelListener;
    }

    public void syncContact(long taskId, ContactSyncRequest syncRequest) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        enQueueTask(taskId, apiService.getContactSync(syncRequest));
    }

    @Override
    public void onSuccessfulApi(long taskId, ContactSyncResponse response) {
        iBaseModelListener.onSuccessfulApi(taskId, response);
    }

    @Override
    public void onFailureApi(long taskId, CustomException e) {
        iBaseModelListener.onFailureApi(taskId, e);
    }
}
