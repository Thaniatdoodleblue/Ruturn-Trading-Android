package com.returntrader.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.model.common.FeeDetailItem;

/**
 * Created by moorthy on 7/24/2017.
 */

public class TransactionFeeDetailViewHolder extends BaseViewHolder<FeeDetailItem> {


    private TextView vTvTitle;
    private TextView vTvDescription;

    public TransactionFeeDetailViewHolder(View itemView) {
        super(itemView);
        bindHolder();
    }

    private void bindHolder() {
        vTvTitle = (TextView) itemView.findViewById(R.id.tv_fee_title);
        vTvDescription = (TextView) itemView.findViewById(R.id.tv_fee_description);
    }

    @Override
    void populateData(FeeDetailItem data) {
        vTvTitle.setText(data.getTitle());
        vTvDescription.setText(data.getDescription());
    }
}
