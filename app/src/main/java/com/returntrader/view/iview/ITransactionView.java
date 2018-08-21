package com.returntrader.view.iview;

import com.returntrader.adapter.TransactionFeeDetailAdapter;

/**
 * Created by moorthy on 7/24/2017.
 */

public interface ITransactionView extends IView {

    void setFeeDetailAdapter(TransactionFeeDetailAdapter feeDetailAdapter);
}
