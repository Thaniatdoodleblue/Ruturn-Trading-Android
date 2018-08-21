package com.returntrader.adapter.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.listener.INewsAdapterListener;
import com.returntrader.library.Log;
import com.returntrader.model.imodel.Stories;
import com.returntrader.utils.CodeSnippet;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import static com.returntrader.common.Constants.Common.DATE_FORMAT_NEWS_ACTUALUTC;
import static com.returntrader.common.Constants.Common.DATE_FORMAT_NEWS_DATE;
import static com.returntrader.common.Constants.Common.DATE_FORMAT_NEWS_TIME;

/**
 * Created by moorthy on 7/22/2017.
 */

public class NewsHeaderViewHolder extends BaseViewHolder<Stories> implements View.OnClickListener {


    private TextView vTvTitle;
    private TextView vTvDate;
    private TextView vTvTime;
    private CodeSnippet mCodeSnippet;
    private INewsAdapterListener iNewsAdapterListener;

    public NewsHeaderViewHolder(View inflate, CodeSnippet codeSnippet, INewsAdapterListener iNewsAdapterListener) {
        super(inflate);
        this.mCodeSnippet = codeSnippet;
        this.iNewsAdapterListener = iNewsAdapterListener;
        bindHolder();
    }

    private void bindHolder() {
        vTvTitle = itemView.findViewById(R.id.tv_news_title);
        vTvDate = itemView.findViewById(R.id.tv_date);
        vTvTime = itemView.findViewById(R.id.tv_time);
        itemView.setOnClickListener(this);
        vTvTitle.setOnClickListener(this);
        vTvDate.setOnClickListener(this);
        vTvTime.setOnClickListener(this);
    }

    @Override
    void populateData(Stories data) {
        DATE_FORMAT_NEWS_ACTUALUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        String displayDate = mCodeSnippet.getFormattedNewsDate(TextUtils.isEmpty(data.getPublishedAt()) ? "" : data.getPublishedAt(),
                DATE_FORMAT_NEWS_ACTUALUTC, DATE_FORMAT_NEWS_DATE);

        String displayTime = mCodeSnippet.getFormattedNewsDate(TextUtils.isEmpty(data.getPublishedAt()) ? "" : data.getPublishedAt(),
                DATE_FORMAT_NEWS_ACTUALUTC, DATE_FORMAT_NEWS_TIME);

        vTvDate.setText(TextUtils.isEmpty(displayDate) ? "Not Available" : displayDate);
        vTvTime.setText(TextUtils.isEmpty(displayTime) ? "Not Available" : displayTime);
        vTvTitle.setText(TextUtils.isEmpty(data.getTitle()) ? "Not Available" : data.getTitle());
    }

    @Override
    public void onClick(View v) {
        iNewsAdapterListener.onLoadLink(data, getAdapterPosition());
    }
}
