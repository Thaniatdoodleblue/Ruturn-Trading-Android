package com.returntrader.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.BankListAdapter;
import com.returntrader.model.common.BankItem;
import com.returntrader.presenter.AddMoneyPresenter;
import com.returntrader.presenter.ipresenter.IAddMoneyPresenter;
import com.returntrader.view.iview.IAddMoneyView;

/**
 * Created by moorthy on 7/26/2017.
 */

public class AddMoneyActivity extends BaseActivity implements IAddMoneyView, View.OnLongClickListener {
    private RecyclerView vRvBankList;
    private Spinner mSpinnerBankList;

    private TextView tvTvBankName;
    private TextView tvTvAllowDeposit;
    private TextView tvTvAccountNumber;
    private TextView tvTvBranchCode;
    private TextView tvTvBranchName;
    private TextView tvTvAccountHolder;
    private TextView tvTvReferenceNumber;
    private IAddMoneyPresenter iAddMoneyPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bindActivity();
        iAddMoneyPresenter = new AddMoneyPresenter(this);
        iAddMoneyPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSpinnerBankList =  findViewById(R.id.spinnerBankList);
        tvTvBankName =  findViewById(R.id.tv_bank_name);
        tvTvAllowDeposit = findViewById(R.id.tv_allow_deposit);
        tvTvAccountNumber =  findViewById(R.id.tv_account_number);
        tvTvBranchCode =  findViewById(R.id.tv_branch_code);
        tvTvBranchName =  findViewById(R.id.tv_branch_name);
        tvTvAccountHolder =  findViewById(R.id.tv_account_holder);
        tvTvReferenceNumber =  findViewById(R.id.tv_ee_reference_number);
        tvTvReferenceNumber.setOnLongClickListener(this);
        //vRvBankList = findViewById(R.id.recycler_bank_list);
        //vRvBankList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    protected int getLayoutId() {
        return R.layout.activity_add_money;
    }

    @Override
    public void setBankListAdapter(BankListAdapter bankListAdapter) {
        vRvBankList.setAdapter(bankListAdapter);
    }

    @Override
    public void setSpinnerAdapter(ArrayAdapter<String> spinnerAdapter) {
        mSpinnerBankList.setAdapter(spinnerAdapter);
        mSpinnerBankList.setOnItemSelectedListener(onItemSelectedListener);
    }

    @Override
    public void setBankInfo(BankItem data) {
        if (!(TextUtils.isEmpty(data.getBankName()))) {
            tvTvBankName.setText(data.getBankName());
        }

        if (!(TextUtils.isEmpty(data.getAllowDeposit()))) {
            tvTvAllowDeposit.setText(data.getAllowDeposit());
        }
        if (!(TextUtils.isEmpty(data.getAccountNumber()))) {
            tvTvAccountNumber.setText(data.getAccountNumber());
        }
        if (!(TextUtils.isEmpty(data.getBranchCode()))) {
            tvTvBranchCode.setText(data.getBranchCode());
        }
        if (!(TextUtils.isEmpty(data.getBranchName()))) {
            tvTvBranchName.setText(data.getBranchName());
        }
        if (!(TextUtils.isEmpty(data.getAccountHolder()))) {
            tvTvAccountHolder.setText(data.getAccountHolder());
        }
    }

    @Override
    public void setAccountInfo(String eftReferenceNumber) {
        if (!(TextUtils.isEmpty(eftReferenceNumber))) {
            tvTvReferenceNumber.setText(eftReferenceNumber);
        }
    }


    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            iAddMoneyPresenter.setBankDetails(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(AddMoneyActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.tv_ee_reference_number:
                String referenceNumber = tvTvReferenceNumber.getText().toString();
                if (!(TextUtils.isEmpty(referenceNumber))) {
                    getCodeSnippet().CopyTextIntoClipboard(referenceNumber);
                    showMessage(getString(R.string.txt_copied));
                }
                break;
        }
        return false;
    }
}
