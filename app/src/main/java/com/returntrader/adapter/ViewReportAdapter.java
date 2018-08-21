package com.returntrader.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.returntrader.R;
import com.returntrader.adapter.viewholder.BankListViewHolder;
import com.returntrader.adapter.viewholder.ReportViewHolder;
import com.returntrader.model.common.BankItem;
import com.returntrader.model.common.Report;
import com.returntrader.utils.CodeSnippet;

import java.util.List;

/**
 * Created by moorthy on 7/26/2017.
 */

public class ViewReportAdapter extends BaseRecyclerAdapter<Report, ReportViewHolder> {

    private CodeSnippet mCodeSnippet;

    public ViewReportAdapter(List<Report> data) {
        super(data);
    }

    public ViewReportAdapter(List<Report> data, CodeSnippet codeSnippet) {
        super(data);
        this.mCodeSnippet = codeSnippet;
    }

    @Override
    public ReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReportViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_item_report, parent, false),mCodeSnippet);
    }
}
