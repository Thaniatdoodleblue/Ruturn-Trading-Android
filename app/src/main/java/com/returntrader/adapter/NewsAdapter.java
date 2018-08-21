package com.returntrader.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.returntrader.R;
import com.returntrader.adapter.listener.INewsAdapterListener;
import com.returntrader.adapter.viewholder.NewsHeaderViewHolder;
import com.returntrader.adapter.viewholder.NewsViewHolder;
import com.returntrader.model.imodel.Stories;
import com.returntrader.utils.CodeSnippet;

import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;

/**
 * Created by Karthikeyan on 17-07-2017
 */

public class NewsAdapter extends BaseRecyclerAdapter<Stories, NewsViewHolder> implements
        StickyHeaderAdapter<NewsHeaderViewHolder> {


    private CodeSnippet mCodeSnippet;
    private INewsAdapterListener iNewsAdapterListener;


    public NewsAdapter(List<Stories> data, CodeSnippet mCodeSnippet, INewsAdapterListener iNewsAdapterListener) {
        super(data);
        this.mCodeSnippet = mCodeSnippet;
        this.iNewsAdapterListener = iNewsAdapterListener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_news_content, parent, false), iNewsAdapterListener);
    }


    @Override
    public long getHeaderId(int position) {
        return getItem(position).getTitle().hashCode();
    }

    @Override
    public NewsHeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new NewsHeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_news_header, parent, false), mCodeSnippet,iNewsAdapterListener);
    }

    @Override
    public void onBindHeaderViewHolder(NewsHeaderViewHolder viewHolder, int position) {
        viewHolder.setData(getItem(position));
    }
}

