package com.returntrader.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
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
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.presenter.RegistrationPhaseThreePresenter;
import com.returntrader.presenter.ipresenter.IRegistrationPhaseThreePresenter;
import com.returntrader.view.iview.IRegistrationPhaseThreeView;

public class RegistrationPhaseThreeActivity extends BaseActivity implements View.OnClickListener, IRegistrationPhaseThreeView {
    private EditText mEtTaxNumber;
    private Spinner mSpinnerIncome;
    private Spinner mSpinnerMaritalStatus;
    private NestedScrollView mNestedScrollViewContainer;
    private IRegistrationPhaseThreePresenter iRegistrationPhaseThreePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_phase_three);
        bindActivity();
        iRegistrationPhaseThreePresenter = new RegistrationPhaseThreePresenter(this);
        iRegistrationPhaseThreePresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.txt_title_user_registration_three);

        mEtTaxNumber = findViewById(R.id.et_tax_number);
        mSpinnerIncome = findViewById(R.id.spinner_income);
        mSpinnerMaritalStatus = findViewById(R.id.spinner_marital_status);
        mNestedScrollViewContainer = findViewById(R.id.container_phase_three);
        mNestedScrollViewContainer.setOnTouchListener(onTouchListener);
    }


    private View.OnTouchListener onTouchListener = (v, event) -> {
        getCodeSnippet().hideKeyboard(RegistrationPhaseThreeActivity.this);
        return false;
    };


    /***/
    private void doSubmit(boolean isSaveEnabled) {
        String tax = TextUtils.isEmpty(mEtTaxNumber.getText().toString()) ? "" : mEtTaxNumber.getText().toString().trim();
        iRegistrationPhaseThreePresenter.onClickSubmit(tax,
                mSpinnerIncome.getSelectedItemPosition(),
                mSpinnerMaritalStatus.getSelectedItemPosition(),
                isSaveEnabled);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (validateFields()) {
                    doSubmit(false);
                }
                break;
            case R.id.btn_save_and_exit:
                doSubmit(true);
                break;
        }
    }

    /***/
    private boolean validateFields() {
        /*if (mEtTaxNumber.length() <= 0) {
            showMessage(getString(R.string.txt_error_enter_tax_number));
            return false;
        }*/

        /*if (mSpinnerMaritalStatus.getSelectedItemPosition() == 0) {
            showMessage("Please choose marital status");
            return false;
        }

        if (mSpinnerIncome.getSelectedItemPosition() == 0) {
            showMessage("Please choose income band");
            return false;
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(RegistrationPhaseThreeActivity.this);
                onBackPressed();
                break;
        }
        return true;
    }


    @Override
    public void registrationSuccessful() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void populateUserInformation(FullRegistrationRequest fullRegistrationRequest) {

        if (fullRegistrationRequest == null) {
            return;
        }

        if (!(TextUtils.isEmpty(fullRegistrationRequest.getTaxNumber()))) {
            mEtTaxNumber.setText(fullRegistrationRequest.getTaxNumber());
        }
    }

    @Override
    public void postResult(Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(Activity.RESULT_FIRST_USER, intent);
        finish();
    }

    @Override
    public void setSpinnerAdapter(ArrayAdapter<String> spinnerAdapter, int type) {
        int position;
        switch (type) {
            case Constants.SpinnerType.TYPE_INCOME:
                position = spinnerAdapter.getPosition(iRegistrationPhaseThreePresenter.getSelectedIncome());
                mSpinnerIncome.setAdapter(spinnerAdapter);
                mSpinnerIncome.setSelection(position);
                break;
            case Constants.SpinnerType.TYPE_MARITAL_STATUS:
                position = spinnerAdapter.getPosition(iRegistrationPhaseThreePresenter.getSelectedMaritalStatus());
                mSpinnerMaritalStatus.setAdapter(spinnerAdapter);
                mSpinnerMaritalStatus.setSelection(position);
                break;
        }
    }


}
