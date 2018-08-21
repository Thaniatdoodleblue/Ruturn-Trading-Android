package com.returntrader.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.returntrader.R;
import com.returntrader.adapter.viewholder.TransactionFeeDetailViewHolder;
import com.returntrader.model.common.FeeDetailItem;

import java.util.List;

/**
 * Created by moorthy on 7/24/2017.
 */

public class TransactionFeeDetailAdapter extends BaseRecyclerAdapter<FeeDetailItem, TransactionFeeDetailViewHolder> {

    public TransactionFeeDetailAdapter(List<FeeDetailItem> data) {
        super(data);
    }

    @Override
    public TransactionFeeDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_transaction_fee_description, parent, false);
        return new TransactionFeeDetailViewHolder(itemView);
    }
}
