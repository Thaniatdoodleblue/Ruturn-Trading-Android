package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.model.dto.request.ForgotPinRequest;
import com.returntrader.presenter.ForgotPinPresenter;
import com.returntrader.presenter.ipresenter.IForgotPinPresenter;
import com.returntrader.view.iview.IForgotPinView;

public class ForgotPinActivity extends BaseActivity implements View.OnClickListener, IForgotPinView {

    private IForgotPinPresenter iForgotPinPresenter;
    private EditText mEtEmail;
    private EditText mEtIdNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin);
        bindActivity();
        iForgotPinPresenter = new ForgotPinPresenter(this);
        iForgotPinPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        mEtEmail = findViewById(R.id.et_email);
        mEtIdNumber = findViewById(R.id.et_rsa_id_number);
        toolbarTitle.setText(R.string.txt_title_forgot_pin);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                if (validateFields()) {
                    ForgotPinRequest forgotPinRequest = new ForgotPinRequest();
                    forgotPinRequest.setEmailId(mEtEmail.getText().toString().trim());
                    forgotPinRequest.setRsaId(mEtIdNumber.getText().toString().trim());
                    iForgotPinPresenter.onClickSubmit(forgotPinRequest);
                }
                break;
        }
    }


    @Override
    public void onBackPressed() {
        iForgotPinPresenter.disableAuth(false);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(ForgotPinActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /***/
    private boolean validateFields() {

        if (TextUtils.isEmpty(mEtEmail.getText().toString().trim())) {
            findViewById(R.id.tv_error_email).setVisibility(View.VISIBLE);
            return false;
        } else {
            findViewById(R.id.tv_error_email).setVisibility(View.GONE);
        }

        if (getCodeSnippet().isValidEmail(mEtEmail.getText().toString().trim())) {
            findViewById(R.id.tv_error_email).setVisibility(View.GONE);
        } else {
            findViewById(R.id.tv_error_email).setVisibility(View.VISIBLE);
            return false;
        }

        if (mEtIdNumber.length() == 0 || mEtIdNumber.length() < Constants.Common.MAX_RSA_ID_LENGTH) {
            findViewById(R.id.tv_error_id_number).setVisibility(View.VISIBLE);
            return false;
        } else {
            findViewById(R.id.tv_error_id_number).setVisibility(View.GONE);
        }

        String rsaDOB = mEtIdNumber.getText().toString().trim();
        String rsaDOBMonth = rsaDOB.substring(2, 4);
        String rsaDOBDate = rsaDOB.substring(4, 6);

        if (Integer.parseInt(rsaDOBMonth) > 12) {
//            showMessage("Month cannot be more than 12");
            findViewById(R.id.tv_error_id_number).setVisibility(View.VISIBLE);
            return false;
        } else {
            findViewById(R.id.tv_error_id_number).setVisibility(View.GONE);
        }

        if (Integer.parseInt(rsaDOBDate) > 31) {
//            showMessage("Date cannot be more than 31");
            findViewById(R.id.tv_error_id_number).setVisibility(View.VISIBLE);
            return false;
        } else {
            findViewById(R.id.tv_error_id_number).setVisibility(View.GONE);
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iForgotPinPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }


    @Override
    public void navigateToVerifyForgotPin(Bundle bundle) {
        Intent i = new Intent(getApplicationContext(), ForgotPinVerifyActivity.class);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        startActivityForResult(i, Constants.RequestCodes.NAVIGATE_FORGOT_PIN_TO_VERIFY_PIN);
    }

    @Override
    public String getRSAId() {
        return mEtIdNumber.getText().toString();
    }

    @Override
    public String getEmailId() {
        return mEtEmail.getText().toString();
    }


}
