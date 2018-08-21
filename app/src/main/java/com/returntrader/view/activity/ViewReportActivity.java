package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.returntrader.R;
import com.returntrader.adapter.ViewReportAdapter;
import com.returntrader.presenter.ReportProblemPresenter;
import com.returntrader.presenter.ViewReportPresenter;
import com.returntrader.presenter.ipresenter.IViewReportPresenter;
import com.returntrader.view.iview.IViewReportView;

/**
 * Created by moorthy on 7/26/2017.
 */

public class ViewReportActivity extends BaseActivity implements IViewReportView {


    private IViewReportPresenter iViewReportPresenter;
    private RecyclerView mRvReports;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        bindActivity();
        iViewReportPresenter = new ViewReportPresenter(this);
        iViewReportPresenter.onCreatePresenter(getIntent().getExtras());

    }

    private void bindActivity() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRvReports =  findViewById(R.id.recycler_reports);
        mRvReports.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(ViewReportActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void setReportListAdapter(ViewReportAdapter reportListAdapter) {
        mRvReports.setAdapter(reportListAdapter);
    }
}
