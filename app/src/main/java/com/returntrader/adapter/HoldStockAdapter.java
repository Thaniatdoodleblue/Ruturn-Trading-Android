package com.returntrader.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.returntrader.R;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.adapter.viewholder.HoldStockViewHolder;
import com.returntrader.model.common.StockHold;
import com.returntrader.utils.CodeSnippet;

import java.util.List;

/**
 * Created by moorthy on 7/26/2017.
 */

public class HoldStockAdapter extends BaseRecyclerAdapter<StockHold, HoldStockViewHolder> {
    private BaseRecyclerAdapterListener<StockHold> adapterListener;
    private CodeSnippet mCodeSnippet;

    public HoldStockAdapter(List<StockHold> data, BaseRecyclerAdapterListener<StockHold> adapterListener, CodeSnippet codeSnippet) {
        super(data);
        this.adapterListener = adapterListener;
        this.mCodeSnippet = codeSnippet;
    }

    @Override
    public HoldStockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HoldStockViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_hold_stock, parent, false), adapterListener,mCodeSnippet);
    }
}
