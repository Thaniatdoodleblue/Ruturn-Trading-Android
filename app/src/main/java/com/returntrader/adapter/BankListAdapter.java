package com.returntrader.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.returntrader.R;
import com.returntrader.adapter.viewholder.BankListViewHolder;
import com.returntrader.model.common.BankItem;

import java.util.List;

/**
 * Created by moorthy on 7/26/2017.
 */

public class BankListAdapter extends BaseRecyclerAdapter<BankItem, BankListViewHolder> {

    public BankListAdapter(List<BankItem> data) {
        super(data);
    }

    @Override
    public BankListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BankListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_bank_item, parent, false));
    }
}
