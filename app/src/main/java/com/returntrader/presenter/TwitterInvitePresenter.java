package com.returntrader.presenter;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.returntrader.R;
import com.returntrader.adapter.FollowerListAdapter;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.library.CustomException;
import com.returntrader.model.dto.response.TwitterFollowerList;
import com.returntrader.model.dto.response.TwitterSendMessageResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.CustomTwitterApiClient;
import com.returntrader.presenter.ipresenter.ITwitterPresenter;
import com.returntrader.view.iview.ITwitterInviteView;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import retrofit2.Call;

/**
 * Created by moorthy on 9/4/2017.
 */

public class TwitterInvitePresenter extends BasePresenter implements ITwitterPresenter {


    private FollowerListAdapter mFollowerListAdapter;
    private ITwitterInviteView iTwitterInviteView;

    public TwitterInvitePresenter(ITwitterInviteView iView) {
        super(iView);
        this.iTwitterInviteView = iView;
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (iTwitterInviteView.getCodeSnippet().hasNetworkConnection()) {
            getFollowersList();
        } else {
            iTwitterInviteView.getCodeSnippet().showNetworkSettings();
        }
    }

    private void getFollowersList() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (session != null) {
            iTwitterInviteView.showProgressbar();
            CustomTwitterApiClient<TwitterFollowerList> customTwitterApiClient = new CustomTwitterApiClient(session);
            customTwitterApiClient.setResponseListener(followerListModelListener);
            Call<TwitterFollowerList> listCall = customTwitterApiClient.getCustomService().getFollowerList(session.getId());
            customTwitterApiClient.enQueueTask(0, listCall);
        }
    }

    private void sendDirectMessage(long id) {


        if (iTwitterInviteView.getCodeSnippet().hasNetworkConnection()){
            TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
            if (session != null) {
                iTwitterInviteView.showProgressbar();
                CustomTwitterApiClient<TwitterSendMessageResponse> customTwitterApiClient = new CustomTwitterApiClient(session);
                customTwitterApiClient.setResponseListener(directMessageResponseListener);
                Call<TwitterSendMessageResponse> listCall = customTwitterApiClient.getCustomService().sendMessage(id,iTwitterInviteView.getActivity().getString(R.string.txt_invite));
                customTwitterApiClient.enQueueTask(1, listCall);
            }
        }else {
            iTwitterInviteView.getCodeSnippet().showNetworkSettings();
        }


    }


    private void setFollowerListAdapter(List<User> users) {
        if (mFollowerListAdapter == null) {
            mFollowerListAdapter = new FollowerListAdapter(users, followerListListener,iTwitterInviteView.getCodeSnippet());
            iTwitterInviteView.setFollowerAdapter(mFollowerListAdapter);
        }

    }

    private BaseRecyclerAdapterListener<User> followerListListener = (data, position) -> {
        Log.d(TAG,"sender Id: " +data.getId());
            sendDirectMessage(data.getId());
    };



    private IBaseModelListener<TwitterSendMessageResponse> directMessageResponseListener = new IBaseModelListener<TwitterSendMessageResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, TwitterSendMessageResponse response) {
            iTwitterInviteView.dismissProgressbar();
            if (response == null || TextUtils.isEmpty(response.getCreatedAt())) {
                return;
            }
            iTwitterInviteView.showMessage("Invite has been successfully sent!");

        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iTwitterInviteView.dismissProgressbar();
            Log.d(TAG, "onFailureApi : " + e.getMessage());
        }
    };


    private IBaseModelListener<TwitterFollowerList> followerListModelListener = new IBaseModelListener<TwitterFollowerList>() {
        @Override
        public void onSuccessfulApi(long taskId, TwitterFollowerList response) {
            iTwitterInviteView.dismissProgressbar();
            if (response == null || response.getUsers() == null) {
                return;
            }
            setFollowerListAdapter(response.getUsers());
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iTwitterInviteView.dismissProgressbar();
            Log.d(TAG, "onFailureApi : " + e.getMessage());
        }
    };
}
