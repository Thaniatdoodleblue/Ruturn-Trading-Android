package com.returntrader.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.TransactionFeeDetailAdapter;
import com.returntrader.presenter.TransactionFeeDetailPresenter;
import com.returntrader.presenter.ipresenter.ITransactionFeeDetailPresenter;
import com.returntrader.view.iview.ITransactionView;

/**
 * Created by moorthy on 7/24/2017.
 */

public class TransactionFeeDetailsActivity extends BaseActivity implements ITransactionView {


    private RecyclerView vRvFeeDetails;
    private ITransactionFeeDetailPresenter iTransactionFeeDetailPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bindActivity();
        iTransactionFeeDetailPresenter = new TransactionFeeDetailPresenter(this);
        iTransactionFeeDetailPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        TextView toolBarTitle = (TextView) toolbar.findViewById(R.id.tv_toolbar_title);
        toolBarTitle.setText(R.string.title_fee_details);

        vRvFeeDetails = (RecyclerView) findViewById(R.id.recycler_fee_details);
        vRvFeeDetails.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    protected int getLayoutId() {
        return R.layout.activity_transaction_fee_detail;
    }

    @Override
    public void setFeeDetailAdapter(TransactionFeeDetailAdapter feeDetailAdapter) {
        vRvFeeDetails.setAdapter(feeDetailAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(TransactionFeeDetailsActivity.this);
                onBackPressed();

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
