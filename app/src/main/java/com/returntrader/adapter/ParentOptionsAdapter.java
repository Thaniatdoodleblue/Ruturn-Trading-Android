package com.returntrader.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.returntrader.R;
import com.returntrader.adapter.listener.ISettingsAdapterListener;
import com.returntrader.adapter.viewholder.OptionViewHolder;
import com.returntrader.adapter.viewholder.ParentOptionViewHolder;
import com.returntrader.model.common.Option;
import com.returntrader.model.common.ParentOptions;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;


public class ParentOptionsAdapter extends ExpandableRecyclerViewAdapter<ParentOptionViewHolder, OptionViewHolder> {
    private ExpandableGroup expandedGroup;
    private ISettingsAdapterListener iSettingsAdapterListener;

    public ParentOptionsAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    public ParentOptionsAdapter(List<ParentOptions> listParentOptions, ISettingsAdapterListener iSettingsAdapterListener) {
        super(listParentOptions);
        this.iSettingsAdapterListener = iSettingsAdapterListener;
    }

    @Override
    public ParentOptionViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_settings_header, parent, false);
        return new ParentOptionViewHolder(view);
    }

    @Override
    public OptionViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_settings_categrory_item, parent, false);
        return new OptionViewHolder(view, iSettingsAdapterListener);
    }

    @Override
    public void onBindChildViewHolder(OptionViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        Option option = (Option) group.getItems().get(childIndex);
        holder.setOptionName(group.getTitle(), option);
    }

    @Override
    public void onBindGroupViewHolder(ParentOptionViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setParentOptionName(group.getTitle());
    }
}
