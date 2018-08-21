package com.returntrader.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.returntrader.common.Constants;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.model.common.ShakeMakeGroupData;
import com.returntrader.model.dto.response.ShakeMakeCompanyResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.ShakeMakeCompanyModel;
import com.returntrader.presenter.ipresenter.IShakeMakePresenter;
import com.returntrader.view.iview.IShakeMakeView;

public class ShakeMakePresenter extends BasePresenter implements IShakeMakePresenter {

    private IShakeMakeView iShakeMakeView;

    private ShakeMakeCompanyModel shakeMakeCompanyModel;
    private AppConfigurationManager appConfigurationManager;

    public ShakeMakePresenter(IShakeMakeView iView) {
        super(iView);
        iShakeMakeView = iView;
        shakeMakeCompanyModel = new ShakeMakeCompanyModel(responseIBaseModelListener);
        appConfigurationManager = new AppConfigurationManager(iShakeMakeView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {

    }

    @Override
    public void getCompanyDetailsApi() {
        if (iShakeMakeView.getCodeSnippet().hasNetworkConnection()) {
            iShakeMakeView.showProgressbar();
            ShakeMakeGroupData shakeMakeGroupData = new ShakeMakeGroupData();
            shakeMakeGroupData.setSnmGroupId(appConfigurationManager.getShakeMakeGroupData().getSnmGroupId());
            shakeMakeCompanyModel.getShakeMakeCompanyApi(11, shakeMakeGroupData);
        } else {
            iShakeMakeView.dismissProgressbar();
            iShakeMakeView.showNetworkMessage();
        }
    }

    /***/
    private IBaseModelListener<ShakeMakeCompanyResponse> responseIBaseModelListener = new IBaseModelListener<ShakeMakeCompanyResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, ShakeMakeCompanyResponse response) {
            iShakeMakeView.dismissProgressbar();
            if (response != null && response.getShakeMakeGroupData() != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.BundleKey.BUNDLE_SHAKE_MAKE_COMPANY_DATA, response.getShakeMakeGroupData());
                iShakeMakeView.navigateToShakeMakeConfirm(bundle);
                iShakeMakeView.unregisterShakeListener();
            }

        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iShakeMakeView.dismissProgressbar();
            iShakeMakeView.unregisterShakeListener();
            iShakeMakeView.showMessage(e.getException());

        }
    };


    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCodes.SHAKE_MAKE_SHAKE_AGAIN_REFRESH:
                if (resultCode == Activity.RESULT_OK) {
                    iShakeMakeView.getActivity().recreate();
                } else {
                    iShakeMakeView.getActivity().onBackPressed();
                }
                break;
            default:
                iShakeMakeView.getActivity().onBackPressed();
                break;
        }
    }
}
