package com.returntrader.adapter.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.listener.INewsAdapterListener;
import com.returntrader.model.imodel.Stories;


public class NewsViewHolder extends BaseViewHolder<Stories> implements View.OnClickListener {
    private TextView vTvNewsContent;
    private INewsAdapterListener iNewsAdapterListener;

    public NewsViewHolder(View itemView, INewsAdapterListener iNewsAdapterListener) {
        super(itemView);
        this.iNewsAdapterListener = iNewsAdapterListener;
        bindHolder();
    }

    private void bindHolder() {
        vTvNewsContent = itemView.findViewById(R.id.tv_news_content);
        itemView.setOnClickListener(this);
    }

    @Override
    void populateData(Stories data) {
        vTvNewsContent.setText(TextUtils.isEmpty(data.getBody()) ? "Not Available" : data.getBody());
    }

    @Override
    public void onClick(View v) {
        iNewsAdapterListener.onLoadLink(data, getAdapterPosition());
    }
}
