package com.returntrader.presenter;

import android.os.Bundle;

import com.returntrader.adapter.ShakeMakeGroupAdapter;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.model.common.ShakeMakeGroupData;
import com.returntrader.model.dto.response.ShakeMakeGroupResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.ShakeMakeGroupModel;
import com.returntrader.presenter.ipresenter.IShakeMakeGroupPresenter;
import com.returntrader.view.iview.IShakeMakeGroupView;

import java.util.ArrayList;
import java.util.List;

public class ShakeMakeGroupPresenter extends BasePresenter implements IShakeMakeGroupPresenter {

    private IShakeMakeGroupView iShakeMakeGroupView;
    private ShakeMakeGroupModel shakeMakeGroupModel;
    private ShakeMakeGroupAdapter shakeMakeGroupAdapter;
    private List<ShakeMakeGroupData> shakeMakeGroupDataList = new ArrayList<>();
    private int previousPosition = -1;
    private ShakeMakeGroupData shakeMakeGroupData;
    private AppConfigurationManager appConfigurationManager;

    public ShakeMakeGroupPresenter(IShakeMakeGroupView iView) {
        super(iView);
        iShakeMakeGroupView = iView;
        shakeMakeGroupModel =  new ShakeMakeGroupModel(responseIBaseModelListener);
        appConfigurationManager =  new AppConfigurationManager(iShakeMakeGroupView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {

        getGroupListFromAPI();
    }

    private void getGroupListFromAPI() {

        if (iShakeMakeGroupView.getCodeSnippet().hasNetworkConnection()) {
            iShakeMakeGroupView.showProgressbar();
            shakeMakeGroupModel.getShakeMakeGroupApi(11);
        } else {
            iShakeMakeGroupView.dismissProgressbar();
            iShakeMakeGroupView.showNetworkMessage();
        }
    }

    /***/
    private IBaseModelListener<ShakeMakeGroupResponse> responseIBaseModelListener = new IBaseModelListener<ShakeMakeGroupResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, ShakeMakeGroupResponse response) {
            iShakeMakeGroupView.dismissProgressbar();

            if(response.getShakeMakeGroupData() != null) {
                shakeMakeGroupDataList = response.getShakeMakeGroupData();
                setAdapter();
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iShakeMakeGroupView.dismissProgressbar();

        }
    };

    private void setAdapter() {
        shakeMakeGroupAdapter =  new ShakeMakeGroupAdapter(shakeMakeGroupDataList, (ShakeMakeGroupData data, int position) -> {
            shakeMakeGroupData = data;
            iShakeMakeGroupView.setSound();
            if(position != previousPosition){
                shakeMakeGroupDataList.get(position).setSelected(true);
                if(previousPosition != -1)
                shakeMakeGroupDataList.get(previousPosition).setSelected(false);
            }
            iShakeMakeGroupView.enableNextButton();
            shakeMakeGroupAdapter.notifyDataSetChanged();
            previousPosition = position;
        });

        iShakeMakeGroupView.setGroupAdapter(shakeMakeGroupAdapter);
    }

    @Override
    public void onClickNext() {
        appConfigurationManager.setShakeMakeGroupData(shakeMakeGroupData);

        iShakeMakeGroupView.navigateToShakeMakeMoney();
    }
}
