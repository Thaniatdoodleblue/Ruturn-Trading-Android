package com.returntrader.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.model.dto.request.BankDetailRequest;
import com.returntrader.model.dto.response.BaseResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.AddBankDetailsModel;
import com.returntrader.presenter.ipresenter.IBankDetailPresenter;
import com.returntrader.view.iview.IBankDetailsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moorthy on 8/29/2017.
 */

public class BankDetailsPresenter extends BasePresenter implements IBankDetailPresenter {
    private IBankDetailsView iBankDetailsView;
    private AddBankDetailsModel mAddBankDetailsModel;
    private AppConfigurationManager mAppConfigurationManager;

    public BankDetailsPresenter(IBankDetailsView iView) {
        super(iView);
        this.iBankDetailsView = iView;
        this.mAddBankDetailsModel = new AddBankDetailsModel(addBankResponseListener);
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        setSpinners();
    }

    /***/
    private void callAddBankDetailsApi(BankDetailRequest bankDetailRequest) {
        if (iBankDetailsView.getCodeSnippet().hasNetworkConnection()) {
            bankDetailRequest.setBankLocationCountryId(Constants.Common.DEFAULT_COUNTRY_ID);
            bankDetailRequest.setUserId(mAppConfigurationManager.getUserId());
            bankDetailRequest.setBankAccountType("Cheque");//bankDetailRequest.getBankAccountType()
            iBankDetailsView.showProgressbar();
            mAddBankDetailsModel.addBankDetails(bankDetailRequest);
        } else {
            iBankDetailsView.showNetworkMessage();
        }
    }

    private List<String> getAccountType() {
        List<String> accountType = new ArrayList<>();
//        accountType.add("Cheque");
        accountType.add("Current");
        accountType.add("Savings");
        accountType.add("Transmission");
        return accountType;
    }


    private List<String> getBankList() {
        List<String> bankName = new ArrayList<>();
        bankName.add("Absa");
        bankName.add("Capitec Bank");
        bankName.add("FNB/RMB");
        bankName.add("Nedbank");
        bankName.add("Standard Bank");
        bankName.add("Africa Bank");
        bankName.add("Albaraka Bank");
        bankName.add("Bank of Athens");
        bankName.add("Bank of Baroda");
        bankName.add("Bidvest Bank");
        bankName.add("Citibank South Africa");
        bankName.add("Deutsche Bank");
        bankName.add("Finbond Mutual Bank");
        bankName.add("Grindrod Bank");
        bankName.add("HBZ Bank");
        bankName.add("HSBC");
        bankName.add("Habib Overseas Bank");
        bankName.add("Investec Bank");
        bankName.add("JPMorgan Chase Bank");
        bankName.add("Mercantile Bank");
        bankName.add("Merrill Lynch South Africa");
        bankName.add("Postbank");
        bankName.add("Sasfin Bank");
        bankName.add("Standard Chartered Bank");
        bankName.add("UBS");
        return bankName;
    }

    private void setSpinners() {
        ArrayAdapter<String> spinnerBankListArrayAdapter = new ArrayAdapter<>(iBankDetailsView.getActivity(), android.R.layout.simple_spinner_dropdown_item, getBankList()); //selected item will look like a spinner set from XML
        spinnerBankListArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iBankDetailsView.setBankListAdapter(spinnerBankListArrayAdapter);

        ArrayAdapter<String> spinnerAccountTypeArrayAdapter = new ArrayAdapter<>(iBankDetailsView.getActivity(), android.R.layout.simple_spinner_dropdown_item, getAccountType()); //selected item will look like a spinner set from XML
        spinnerAccountTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iBankDetailsView.setAccountTypeAdapter(spinnerAccountTypeArrayAdapter);
    }


    /***/
    private IBaseModelListener<BaseResponse> addBankResponseListener = new IBaseModelListener<BaseResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, BaseResponse response) {
            iBankDetailsView.dismissProgressbar();
            if (response != null) {
                mAppConfigurationManager.setBankDetailCompletedStatus(true);
                iBankDetailsView.showMessage(R.string.txt_successfully_account_details_added);
                iBankDetailsView.redirectToPrevious();
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iBankDetailsView.dismissProgressbar();
            if (e != null) {
                iBankDetailsView.showMessage("Oops! Something went wrong. Please try again later.");
            }
        }
    };

    @Override
    public void onClickSubmit(BankDetailRequest bankDetailRequest) {
        callAddBankDetailsApi(bankDetailRequest);
    }
}
