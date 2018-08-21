package com.returntrader.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.returntrader.R;
import com.returntrader.adapter.viewholder.TransactionFeeViewHolder;
import com.returntrader.model.common.TransactionFee;

import java.util.List;

/**
 * Created by moorthy on 7/21/2017
 */

public class TransactionFeeAdapter extends BaseRecyclerAdapter<TransactionFee, TransactionFeeViewHolder> {

    private List<TransactionFee> mTransactionFees;
    private String mSharePrice;
    private int mTransactionType;


    public TransactionFeeAdapter(List<TransactionFee> data, String price,int transactionType) {
        super(data);
        this.mTransactionFees = data;
        this.mSharePrice = price;
        this.mTransactionType = transactionType;
    }

    @Override
    public TransactionFeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_transaction_cost, parent, false);
        return new TransactionFeeViewHolder(itemView, mSharePrice,mTransactionType);
    }
}
