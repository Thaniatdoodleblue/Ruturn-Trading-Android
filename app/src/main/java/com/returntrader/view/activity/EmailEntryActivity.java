package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.library.Log;
import com.returntrader.model.common.UserInfo;
import com.returntrader.presenter.EmailEntryPresenter;
import com.returntrader.presenter.ipresenter.IEmailEntryPresenter;
import com.returntrader.view.iview.IEmailEntryView;

public class EmailEntryActivity extends BaseActivity implements View.OnClickListener, IEmailEntryView {

    private IEmailEntryPresenter iEmailEntryPresenter;
    private EditText mEtEmail;
    private EditText mEtIdNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_entry);
        bindActivity();
        iEmailEntryPresenter = new EmailEntryPresenter(this);
        iEmailEntryPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        mEtEmail = findViewById(R.id.et_email);
        mEtIdNumber = findViewById(R.id.et_rsa_id_number);
        toolbarTitle.setText(R.string.txt_enter_further_details);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                if (validateFields()) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setEmailId(mEtEmail.getText().toString().trim());
                    userInfo.setRsaIdentificationId(mEtIdNumber.getText().toString().trim());
                    iEmailEntryPresenter.onClickSubmit(userInfo);
                }
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(EmailEntryActivity.this);
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

        Log.d("RSAMonth = " + rsaDOBMonth, "RSADate = " + rsaDOBDate);

        if (iEmailEntryPresenter.isNotAboveTeen(mEtIdNumber.getText().toString().trim())) {
            //findViewById(R.id.tv_error_id_number).setVisibility(View.VISIBLE);
            //((TextView) findViewById(R.id.tv_error_id_number)).setText(R.string.txt_error_label_id_age_limit_error);
            showAgeRestrictionAlert();
            return false;
        } else {
            findViewById(R.id.tv_error_id_number).setVisibility(View.GONE);
        }

        return true;
    }

    /***/
    private void showAgeRestrictionAlert() {
        // TODO Auto-generated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Unfortunately Ruturn Trading only caters for South African citizens, over the age of 18yrs.");

        builder.setNegativeButton("OK", (dialog, which) -> dialog.cancel());

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iEmailEntryPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressed() {
        getCodeSnippet().hideKeyboard(EmailEntryActivity.this);
        doubleBackToExit();
    }

    @Override
    public void navigateToEmailAck(Bundle bundle) {
        Intent i = new Intent(getApplicationContext(), EmailAckActivity.class);
        if (bundle != null) i.putExtras(bundle);
        startActivityForResult(i, Constants.RequestCodes.NAVIGATE_EMAIL_TO_VERIFY_EMAIL);
    }
}
