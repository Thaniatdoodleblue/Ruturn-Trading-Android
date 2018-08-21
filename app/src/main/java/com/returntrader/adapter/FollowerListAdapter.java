package com.returntrader.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.returntrader.R;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.adapter.viewholder.ContactViewHolder;
import com.returntrader.adapter.viewholder.FollowerViewHolder;
import com.returntrader.model.common.ContactItem;
import com.returntrader.utils.CodeSnippet;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

/**
 * Created by moorthy on 7/26/2017.
 */

public class FollowerListAdapter extends BaseRecyclerAdapter<User, FollowerViewHolder> {
    private BaseRecyclerAdapterListener<User> adapterListener;
    private CodeSnippet mCodeSnippet;

    public FollowerListAdapter(List<User> data, BaseRecyclerAdapterListener<User> adapterListener, CodeSnippet codeSnippet) {
        super(data);
        this.adapterListener = adapterListener;
        this.mCodeSnippet = codeSnippet;
    }

    @Override
    public FollowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FollowerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_user, parent, false),adapterListener,mCodeSnippet);
    }
}
