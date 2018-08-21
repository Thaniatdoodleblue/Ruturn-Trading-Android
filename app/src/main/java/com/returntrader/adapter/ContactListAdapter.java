package com.returntrader.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.returntrader.R;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.adapter.viewholder.BankListViewHolder;
import com.returntrader.adapter.viewholder.ContactViewHolder;
import com.returntrader.model.common.BankItem;
import com.returntrader.model.common.ContactItem;
import com.returntrader.utils.CodeSnippet;

import java.util.List;

/**
 * Created by moorthy on 7/26/2017.
 */

public class ContactListAdapter extends BaseRecyclerAdapter<ContactItem, ContactViewHolder> {


    private BaseRecyclerAdapterListener<ContactItem> adapterListener;
    private CodeSnippet mCodeSnippet;

    public ContactListAdapter(List<ContactItem> data, BaseRecyclerAdapterListener<ContactItem> adapterListener, CodeSnippet codeSnippet) {
        super(data);
        this.adapterListener = adapterListener;
        this.mCodeSnippet = codeSnippet;
    }

    public ContactListAdapter(List<ContactItem> data, BaseRecyclerAdapterListener<ContactItem> adapterListener) {
        super(data);
        this.adapterListener = adapterListener;
    }

    public ContactListAdapter(List<ContactItem> data) {
        super(data);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_contact_item, parent, false),adapterListener,mCodeSnippet);
    }
}
