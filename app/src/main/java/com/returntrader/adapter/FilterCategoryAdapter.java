package com.returntrader.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.returntrader.R;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.adapter.viewholder.FilterCategoryViewHolder;
import com.returntrader.model.common.FilterCategory;

import java.util.List;

/**
 * Created by moorthy on 10/3/2017.
 */

public class FilterCategoryAdapter extends BaseRecyclerAdapter<FilterCategory, FilterCategoryViewHolder> {


    private BaseRecyclerAdapterListener<FilterCategory> recyclerAdapterListener;
    private int mCurrentSelectedType;

    public FilterCategoryAdapter(List<FilterCategory> data) {
        super(data);
    }

    public FilterCategoryAdapter(List<FilterCategory> data, BaseRecyclerAdapterListener<FilterCategory> recyclerAdapterListener) {
        super(data);
        this.recyclerAdapterListener = recyclerAdapterListener;
    }

    public FilterCategoryAdapter(List<FilterCategory> categoryList, BaseRecyclerAdapterListener<FilterCategory> recyclerAdapterListener, int currentPage) {
        super(categoryList);
        this.recyclerAdapterListener = recyclerAdapterListener;
        this.mCurrentSelectedType = currentPage;

    }

    @Override
    public FilterCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilterCategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_filter, parent, false), recyclerAdapterListener,mCurrentSelectedType);
    }

}
