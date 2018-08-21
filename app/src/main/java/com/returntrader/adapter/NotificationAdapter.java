package com.returntrader.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.returntrader.R;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.adapter.viewholder.NotificationViewHolder;
import com.returntrader.database.NotificationMeta;

import java.util.List;

/**
 * Created by moorthy on 7/26/2017.
 */

public class NotificationAdapter extends BaseRecyclerAdapter<NotificationMeta, NotificationViewHolder> {

    private BaseRecyclerAdapterListener<NotificationMeta> iNotificationOnClickListener;

    public NotificationAdapter(List<NotificationMeta> data, BaseRecyclerAdapterListener<NotificationMeta> notificationClickListener) {
        super(data);
        this.iNotificationOnClickListener = notificationClickListener;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotificationViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_item_notification, parent,
                                false),iNotificationOnClickListener);
    }
}
