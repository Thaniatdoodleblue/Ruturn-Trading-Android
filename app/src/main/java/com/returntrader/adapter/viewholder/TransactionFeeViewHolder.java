package com.returntrader.adapter.viewholder;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.library.Log;
import com.returntrader.model.common.TransactionFee;

/**
 * Created by moorthy on 7/21/2017.
 */

public class TransactionFeeViewHolder extends BaseViewHolder<TransactionFee> {


    private TextView vTvTaxName;
    private TextView vTvTaxCurrency;
    private TextView vTvTaxAmount;
    private String mSharePrice;
    private int mTransactionType;


    public TransactionFeeViewHolder(View itemView, String sharePrice, int transactionType) {
        super(itemView);
        this.mSharePrice = sharePrice;
        this.mTransactionType = transactionType;
        bindHolder();
    }

    private void bindHolder() {
        vTvTaxName = itemView.findViewById(R.id.tv_tax_name);
        vTvTaxCurrency = itemView.findViewById(R.id.tv_currency);
        vTvTaxAmount = itemView.findViewById(R.id.tv_trader_cost);
    }

    @Override
    void populateData(TransactionFee data) {
        vTvTaxName.setText(data.getTaxName());
        vTvTaxCurrency.setText(data.getCurrency());

        switch (data.getItemType()) {
            case 0: // For loading all tax as common
                vTvTaxAmount.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
                vTvTaxCurrency.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
                vTvTaxName.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
                vTvTaxAmount.setText(TraderApplication.getInstance().getTaxAmount(data.getTax(), mSharePrice));
                break;
            case 1:// For loading total amount
                vTvTaxAmount.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.homePage));
                vTvTaxCurrency.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.homePage));
                vTvTaxName.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.homePage));
                vTvTaxAmount.setText(TraderApplication.getInstance().getTransactionTaxCost(mSharePrice, mTransactionType));
                break;
            case 2: // Trade cost
                vTvTaxAmount.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
                vTvTaxCurrency.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
                vTvTaxName.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
                vTvTaxAmount.setText(Constants.TaxInfo.DEFAULT_TRADER_COST);
                break;
        }
    }
}
