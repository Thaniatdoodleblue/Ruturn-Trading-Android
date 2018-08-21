package com.returntrader.presenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.returntrader.R;
import com.returntrader.adapter.BankListAdapter;
import com.returntrader.common.Constants;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.model.common.BankItem;
import com.returntrader.presenter.ipresenter.IAddMoneyPresenter;
import com.returntrader.view.iview.IAddMoneyView;

import java.util.List;

/**
 * Created by moorthy on 7/26/2017
 */

public class AddMoneyPresenter extends BasePresenter implements IAddMoneyPresenter {

    private IAddMoneyView iAddMoneyView;
    private BankListAdapter mBankListAdapter;
    private List<BankItem> mBankItems;
    private AppConfigurationManager mAppConfigurationManager;

    public AddMoneyPresenter(IAddMoneyView iView) {
        super(iView);
        this.iAddMoneyView = iView;
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        iAddMoneyView.setAccountInfo(mAppConfigurationManager.getEftReferenceNumber());
        loadBankList();
    }

    private void loadBankList() {
        String bankList = iAddMoneyView.getCodeSnippet().loadJSONFromAsset(Constants.FileName.BANK_LIST_FILE_NAME);
        if (TextUtils.isEmpty(bankList)) {
            return;
        }

        mBankItems = iAddMoneyView.getCodeSnippet().getListFromJsonString(bankList, BankItem.class);
        if (mBankItems == null) {
            return;
        }
        //prepareBankListAdapter(mBankItems);
        prepareSpinnerAdapter();
    }


    private void prepareSpinnerAdapter() {
        List<String> names = Stream.of(mBankItems)
                .map(BankItem::getLabelName)
                .collect(Collectors.toList());
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(iAddMoneyView.getActivity(), R.layout.spinner_item_view, names); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iAddMoneyView.setSpinnerAdapter(spinnerArrayAdapter);
    }


    @Override
    public void setBankDetails(int position) {
        iAddMoneyView.setBankInfo(mBankItems.get(position));
    }

    private void prepareBankListAdapter(List<BankItem> bankItems) {
        if (mBankListAdapter == null) {
            mBankListAdapter = new BankListAdapter(bankItems);
            iAddMoneyView.setBankListAdapter(mBankListAdapter);
        } else {
            mBankListAdapter.resetItems(bankItems);
        }
    }
}
