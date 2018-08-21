package com.returntrader.view.iview;

import android.widget.ArrayAdapter;

import com.returntrader.adapter.BankListAdapter;
import com.returntrader.model.common.BankItem;

/**
 * Created by moorthy on 7/26/2017.
 */

public interface IAddMoneyView extends IView {

    void setBankListAdapter(BankListAdapter bankListAdapter);

    void setSpinnerAdapter(ArrayAdapter<String> spinnerAdapter);

    void setBankInfo(BankItem bankInfo);

    void setAccountInfo(String eftReferenceNumber);
}
