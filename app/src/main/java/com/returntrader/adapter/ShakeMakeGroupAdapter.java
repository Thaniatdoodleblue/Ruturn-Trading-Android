package com.returntrader.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.returntrader.R;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.adapter.viewholder.SearchViewHolder;
import com.returntrader.adapter.viewholder.ShakeMakeGroupViewHolder;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.model.common.ShakeMakeGroupData;

import java.util.List;

public class ShakeMakeGroupAdapter extends BaseRecyclerAdapter<ShakeMakeGroupData, ShakeMakeGroupViewHolder>  {

    private BaseRecyclerAdapterListener<ShakeMakeGroupData> listener;

    public ShakeMakeGroupAdapter(List<ShakeMakeGroupData> mailDataList, BaseRecyclerAdapterListener<ShakeMakeGroupData> mSearchAdapterListener) {
        super(mailDataList);
        this.listener = mSearchAdapterListener;
    }

    @Override
    public ShakeMakeGroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new ShakeMakeGroupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_shake_make_group, parent, false),listener);
    }

}
