package com.returntrader.adapter.viewholder;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.model.common.ShakeMakeMoneyData;

public class ShakeMakeMoneyDescViewHolder extends BaseViewHolder<ShakeMakeMoneyData> {


    private TextView vTvShakeMakeMoneyContainer, vTvShakeMakeMoneyContainerPercentage;


    public ShakeMakeMoneyDescViewHolder(View itemView) {
        super(itemView);
        bindView();
    }

    private void bindView() {
        vTvShakeMakeMoneyContainer = itemView.findViewById(R.id.text_shake_make_desc);
          vTvShakeMakeMoneyContainerPercentage = itemView.findViewById(R.id.text_shake_make_desc_percentage);


    }


    @Override
    void populateData(ShakeMakeMoneyData data) {


        if (!TextUtils.isEmpty(data.getAmount())) {
            String transactionTaxCost = TraderApplication.getInstance().getTransactionTaxCost(data.getAmount(), Constants.TransactionType.TRANSACTION_BUY);
            Log.d(TAG, "populateData transactionTaxCost: " +transactionTaxCost );
            vTvShakeMakeMoneyContainer.setText("R" + data.getAmount() );
            vTvShakeMakeMoneyContainerPercentage.setText(" (excl. Fee's: R"+transactionTaxCost+")");
        }

    }
}