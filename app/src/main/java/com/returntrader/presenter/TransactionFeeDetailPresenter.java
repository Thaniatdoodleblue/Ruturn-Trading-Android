package com.returntrader.presenter;

import android.os.Bundle;

import com.returntrader.adapter.TransactionFeeDetailAdapter;
import com.returntrader.model.common.FeeDetailItem;
import com.returntrader.presenter.ipresenter.ITransactionFeeDetailPresenter;
import com.returntrader.view.iview.ITransactionView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moorthy on 7/24/2017.
 */

public class TransactionFeeDetailPresenter extends BasePresenter implements ITransactionFeeDetailPresenter {


    private ITransactionView iTransactionView;

    public TransactionFeeDetailPresenter(ITransactionView iView) {
        super(iView);
        this.iTransactionView = iView;
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        prepareAdapter();
    }

    private List<FeeDetailItem> getFeeDetails() {
        List<FeeDetailItem> feeDetailItems = new ArrayList<>();
        feeDetailItems.add(new FeeDetailItem("Per Trade Fee", "This is a flat cost per trade of R5"));
        feeDetailItems.add(new FeeDetailItem("Broker Commission", "This is a commission based fee"));
        feeDetailItems.add(new FeeDetailItem("Settlement and Administration", "Unfortunately we are not responsible for this fee. This fee is charged at flat rate of 0.075% of the value traded, ensuring the lowest possible cost to the investor.This fee includes the electronic settlement of your transactions through the electronic settlement authority for whole shares and the administration fee represents an upfront recovery on the fractional share rights (FSRs) portion."));
        feeDetailItems.add(new FeeDetailItem("Investor Protection Levi and Administration", "Unfortunately we are not responsible for this fee. The investor protection levy is a mandatory charge levied by the regulator at 0.0002% on the value of whole shares traded for the regulation of the securities market and in dealing with issues such as insider trading and market manipulation which is ultimately for the benefit of investors. The administration fee represents an upfront recovery on the fractional share rights (FSRs) portion."));
        feeDetailItems.add(new FeeDetailItem("Value-added Tax (VAT)", "As per government regualtions"));
        feeDetailItems.add(new FeeDetailItem("Securities transfer and admin", "Unfortunately we are not responsible for this fee. Security Transfer tax is levied by SARS at 0.25% and applies to the purchase and transfers of listed (whole shares) and unlisted securities (FSRs)."));
        return feeDetailItems;
    }

    private void prepareAdapter() {
        iTransactionView.setFeeDetailAdapter(new TransactionFeeDetailAdapter(getFeeDetails()));
    }
}
