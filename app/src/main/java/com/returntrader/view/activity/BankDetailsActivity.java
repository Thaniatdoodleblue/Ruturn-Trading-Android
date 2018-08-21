package com.returntrader.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.model.dto.request.BankDetailRequest;
import com.returntrader.presenter.BankDetailsPresenter;
import com.returntrader.presenter.ipresenter.IBankDetailPresenter;
import com.returntrader.view.iview.IBankDetailsView;

/**
 * Created by moorthy on 8/2/2017.
 */

public class BankDetailsActivity extends BaseActivity implements View.OnClickListener, IBankDetailsView {
    private EditText mEtAccountHolderName;
    private Spinner mSpinnerBankList;
    private EditText mEtBranchName;
    private EditText mEtBranchCode;
    private EditText mEtAccountNumber;
    private Spinner mSpinnerAccountType;

    private IBankDetailPresenter iBankDetailPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);
        bindActivity();
        iBankDetailPresenter = new BankDetailsPresenter(this);
        iBankDetailPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.txt_bank_details);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSpinnerBankList = findViewById(R.id.spinnerBankName);
        mSpinnerAccountType = findViewById(R.id.spinnerAccountType);
        mEtAccountHolderName = findViewById(R.id.et_account_holder);
        mEtBranchName = findViewById(R.id.et_branch_name);
        mEtBranchCode = findViewById(R.id.et_branch_code);
        mEtAccountNumber = findViewById(R.id.et_account_number);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (isAllFieldsAreValid()) {
                    BankDetailRequest bankDetailRequest = new BankDetailRequest();
                    bankDetailRequest.setBankName(mSpinnerBankList.getSelectedItem().toString());
                    bankDetailRequest.setBankAccountType(mSpinnerAccountType.getSelectedItem().toString().trim());
                    bankDetailRequest.setAccountNumber(mEtAccountNumber.getText().toString().trim());
                    bankDetailRequest.setBranchName(mEtBranchName.getText().toString().trim());
                    bankDetailRequest.setBranchCode(mEtBranchCode.getText().toString().trim());
                    bankDetailRequest.setAccountHolderName(mEtAccountHolderName.getText().toString().trim());
                    iBankDetailPresenter.onClickSubmit(bankDetailRequest);
                }
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(BankDetailsActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setBankListAdapter(ArrayAdapter<String> bankListAdapter) {
        mSpinnerBankList.setAdapter(bankListAdapter);
    }

    @Override
    public void setAccountTypeAdapter(ArrayAdapter<String> accountTypeAdapter) {
        mSpinnerAccountType.setAdapter(accountTypeAdapter);
    }

    @Override
    public void redirectToPrevious() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    public boolean isAllFieldsAreValid() {
        if (TextUtils.isEmpty(mEtAccountHolderName.getText())) {
            showMessage(getString(R.string.txt_error_account_name));
            return false;
        }

        if (TextUtils.isEmpty(mEtBranchName.getText())) {
            showMessage(getString(R.string.txt_error_branch_name));
            return false;
        }

        if (TextUtils.isEmpty(mEtBranchCode.getText())) {
            showMessage(getString(R.string.txt_error_branch_code_empty));
            return false;
        }

        if (mEtBranchCode.length() < Constants.Common.MAX_BRANCH_CODE_LENGTH) {
            showMessage(getString(R.string.txt_error_branch_code));
            return false;
        }

        if (TextUtils.isEmpty(mEtAccountNumber.getText())) {
            showMessage(getString(R.string.txt_error_account_number));
            return false;
        }

       /* if (mEtAccountNumber.length() < Constants.Common.MAX_ACCOUNTNUMBER_LENGTH) {
            showMessage("Please enter valid account number");
            return false;
        }*/
        return true;
    }
}
