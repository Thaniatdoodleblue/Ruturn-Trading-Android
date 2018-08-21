package com.returntrader.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.Log;
import com.returntrader.model.common.FicaDocumentStatus;
import com.returntrader.presenter.ipresenter.IFicaDocumentPresenter;
import com.returntrader.view.iview.IFicaDocumentView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by moorthy on 11/7/2017.
 */

public class FicaDocumentPresenter extends BasePresenter implements IFicaDocumentPresenter {

    private IFicaDocumentView iFicaDocumentView;
    private AppConfigurationManager mAppConfigurationManager;

    public FicaDocumentPresenter(IFicaDocumentView iView) {
        super(iView);
        this.iFicaDocumentView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        setFicaStatus();
    }

    /***/
    private void setFicaStatus() {
        boolean isFicaCompleted = false;
        FicaDocumentStatus ficaDocumentStatus = getFicaDocumentStatus();
        if (ficaDocumentStatus != null) {
            if (ficaDocumentStatus.getGreenIdCard() != null || ficaDocumentStatus.getIdCard() != null) {
                if (ficaDocumentStatus.getIdCard() != null && ficaDocumentStatus.getIdCard().isUploaded()) {
                    isFicaCompleted = true;
                    iFicaDocumentView.setIdCardStatus(ficaDocumentStatus.getIdCard().isUploaded());
                } else if (ficaDocumentStatus.getGreenIdCard() != null && ficaDocumentStatus.getGreenIdCard().isUploaded()) {
                    isFicaCompleted = true;
                    iFicaDocumentView.setIdCardStatus(ficaDocumentStatus.getGreenIdCard().isUploaded());
                }
            }
            if (ficaDocumentStatus.getBankStatement() != null) {
                iFicaDocumentView.setBankStatementStatus(ficaDocumentStatus.getBankStatement().isUploaded());
                if (isFicaCompleted && ficaDocumentStatus.getBankStatement().isUploaded()) {
                    //  mAppConfigurationManager.setFicaDetailCompletedStatus(true);
                }
            }
        }

        if (mAppConfigurationManager.isFicaVerified()) {
            iFicaDocumentView.setUploadStatusText(R.string.txt_update);
        }

        if (mAppConfigurationManager.isFicaDetailCompleted() &&
                !mAppConfigurationManager.isFicaVerified()) {
            iFicaDocumentView.showStatusPending(false);
        }

        if (mAppConfigurationManager.isFicaDetailCompleted() &&
                mAppConfigurationManager.isFicaVerified()) {
            iFicaDocumentView.showStatusPending(true);
        }
    }

    /***/
    private FicaDocumentStatus getFicaDocumentStatus() {
        FicaDocumentStatus ficaDocumentStatus = new FicaDocumentStatus();
        String ficaStatus = mAppConfigurationManager.getFicaDocStatus();
        if (!(TextUtils.isEmpty(ficaStatus))) {
            ficaDocumentStatus = iFicaDocumentView.getCodeSnippet().getObjectFromJson(ficaStatus, FicaDocumentStatus.class);
        }
        return ficaDocumentStatus;
    }


    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResultPresenter");
        switch (requestCode) {
            case Constants.RequestCodes.NAVIGATE_FICA_TO_UPLOAD:
                setFicaStatus();
                break;
        }
    }

    @Override
    public void updateFICAStatus(boolean isUploaded) {
        mAppConfigurationManager.setFicaDetailCompletedStatus(isUploaded);
    }
}
