package com.returntrader.presenter;

import android.os.Bundle;

import com.returntrader.adapter.ShakeMakeMoneyAdapter;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.model.common.ShakeMakeMoneyData;
import com.returntrader.model.dto.response.ShakeMakeMoneyResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.ShakeMakeMoneyModel;
import com.returntrader.presenter.ipresenter.IShakeMakeMoneyPresenter;
import com.returntrader.view.iview.IShakeMakeMoneyView;

import java.util.ArrayList;
import java.util.List;

public class ShakeMakeMoneyPresenter extends BasePresenter implements IShakeMakeMoneyPresenter {

    private IShakeMakeMoneyView iShakeMakeMoneyView;
    private ShakeMakeMoneyModel shakeMakeMoneyModel;
    private ShakeMakeMoneyAdapter shakeMakeMoneyAdapter;
    private List<ShakeMakeMoneyData> shakeMakeMoneyDataList = new ArrayList<>();
    private int previousPosition = -1;
    private ShakeMakeMoneyData shakeMakeMoneyData;
    private AppConfigurationManager appConfigurationManager;

    public ShakeMakeMoneyPresenter(IShakeMakeMoneyView iView) {
        super(iView);
        iShakeMakeMoneyView = iView;
        shakeMakeMoneyModel =  new ShakeMakeMoneyModel(responseIBaseModelListener);
        appConfigurationManager =  new AppConfigurationManager(iShakeMakeMoneyView.getActivity());

    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        getMoneyListFromAPI();
    }

    private void getMoneyListFromAPI() {

        if (iShakeMakeMoneyView.getCodeSnippet().hasNetworkConnection()) {
            iShakeMakeMoneyView.showProgressbar();
            shakeMakeMoneyModel.getShakeMakeMoneyApi(11);
        } else {
            iShakeMakeMoneyView.dismissProgressbar();
            iShakeMakeMoneyView.showNetworkMessage();
        }
    }
    /***/
    private IBaseModelListener<ShakeMakeMoneyResponse> responseIBaseModelListener = new IBaseModelListener<ShakeMakeMoneyResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, ShakeMakeMoneyResponse response) {
            iShakeMakeMoneyView.dismissProgressbar();

            if(response.getShakeMakeMoneyData() != null) {
                shakeMakeMoneyDataList = response.getShakeMakeMoneyData();
                setAdapter();


            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iShakeMakeMoneyView.dismissProgressbar();

        }
    };

    private void setAdapter() {
        shakeMakeMoneyAdapter =  new ShakeMakeMoneyAdapter(shakeMakeMoneyDataList, (ShakeMakeMoneyData data, int position) -> {
            shakeMakeMoneyData = data;
            iShakeMakeMoneyView.setSound();
            if(position != previousPosition){
                shakeMakeMoneyDataList.get(position).setSelected(true);
                if(previousPosition != -1)
                    shakeMakeMoneyDataList.get(previousPosition).setSelected(false);

            }
            iShakeMakeMoneyView.enableNextButton();
            shakeMakeMoneyAdapter.notifyDataSetChanged();
            previousPosition = position;


        });

        iShakeMakeMoneyView.setShakeMakeMoneyAdapter(shakeMakeMoneyAdapter);
    }

    @Override
    public void onClickNext() {
        appConfigurationManager.setShakeMakeMoneyData(shakeMakeMoneyData);
        iShakeMakeMoneyView.navigateToGetReadyShake();
    }


    @Override
    public String getLastKnownBalance() {
        return appConfigurationManager.getLastKnownBalance();
    }

    @Override
    public ShakeMakeMoneyData  getShakeMakeMoneyData(){
        return shakeMakeMoneyData;
    }

    @Override
    public void showInfoAlert() {
        iShakeMakeMoneyView.setDialogMoneyDesc(shakeMakeMoneyDataList);
    }
}
