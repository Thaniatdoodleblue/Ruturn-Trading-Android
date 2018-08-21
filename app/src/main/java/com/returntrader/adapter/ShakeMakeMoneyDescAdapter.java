package com.returntrader.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.returntrader.R;
import com.returntrader.adapter.viewholder.ShakeMakeMoneyDescViewHolder;
import com.returntrader.model.common.ShakeMakeMoneyData;

import java.util.List;

public class ShakeMakeMoneyDescAdapter extends BaseRecyclerAdapter<ShakeMakeMoneyData, ShakeMakeMoneyDescViewHolder> {


    public ShakeMakeMoneyDescAdapter(List<ShakeMakeMoneyData> shakeMakeMoneyDataList) {
        super(shakeMakeMoneyDataList);

    }

    @Override
    public ShakeMakeMoneyDescViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShakeMakeMoneyDescViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_shake_make_money_desc, parent, false));
    }

}

