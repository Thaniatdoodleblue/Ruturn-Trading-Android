package com.returntrader.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.returntrader.R;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.adapter.viewholder.SearchViewHolder;
import com.returntrader.database.CompanyItemInfo;

import java.util.List;

/**
 * Created by Karthikeyan on 04-07-2017
 */

public class SearchHomeAdapter extends BaseRecyclerAdapter<CompanyItemInfo, SearchViewHolder>  {

    private BaseRecyclerAdapterListener<CompanyItemInfo> listener;

    public SearchHomeAdapter(List<CompanyItemInfo> mailDataList, BaseRecyclerAdapterListener<CompanyItemInfo> mSearchAdapterListener) {
        super(mailDataList);
        this.listener = mSearchAdapterListener;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new SearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_company_item, parent, false),listener);
    }

}
