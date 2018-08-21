package com.returntrader.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.returntrader.R;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.adapter.viewholder.ShakeMakeGroupViewHolder;
import com.returntrader.adapter.viewholder.ShakeMakeMoneyViewHolder;
import com.returntrader.model.common.ShakeMakeGroupData;
import com.returntrader.model.common.ShakeMakeMoneyData;

import java.util.List;

public class ShakeMakeMoneyAdapter extends BaseRecyclerAdapter<ShakeMakeMoneyData, ShakeMakeMoneyViewHolder>  {

    private BaseRecyclerAdapterListener<ShakeMakeMoneyData> listener;

    public ShakeMakeMoneyAdapter(List<ShakeMakeMoneyData> shakeMakeMoneyDataList, BaseRecyclerAdapterListener<ShakeMakeMoneyData> mSearchAdapterListener) {
        super(shakeMakeMoneyDataList);
        this.listener = mSearchAdapterListener;
    }

    @Override
    public ShakeMakeMoneyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new ShakeMakeMoneyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_shake_make_money, parent, false),listener);
    }

}
