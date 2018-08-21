package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.returntrader.R;
import com.returntrader.presenter.ForgotPinVerifyPresenter;
import com.returntrader.presenter.ipresenter.IForgotPinVerifyPresenter;
import com.returntrader.view.iview.IForgotPinVerifyView;


public class ForgotPinVerifyActivity extends BaseActivity implements IForgotPinVerifyView, View.OnClickListener {

    private Button vBtn;
    //    private EditText vEdtTxtone, vEdtTxttwo, vEdtTxtthree, vEdtTxtfour;
    private TextView vTvAckText;
    private IForgotPinVerifyPresenter iForgotPinVerifyPresenter;
    private PinView pinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin_verify);
        findByViews();
        iForgotPinVerifyPresenter = new ForgotPinVerifyPresenter(this);
        iForgotPinVerifyPresenter.onCreatePresenter(getIntent().getExtras());
    }

    /***/
    public void findByViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.txt_title_verify_forgot_pin_otp);

        vBtn = findViewById(R.id.btn_next);
        pinView = findViewById(R.id.pinView);

//        vEdtTxtone = findViewById(R.id.firstDigit);
//        vEdtTxttwo = findViewById(R.id.secondDigit);
//        vEdtTxtthree = findViewById(R.id.thirdDigit);
//        vEdtTxtfour = findViewById(R.id.fourthDigit);
        vTvAckText = findViewById(R.id.tv_ack_text);

        vTvAckText.setOnClickListener(this);
        vBtn.setOnClickListener(this);
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pinView.getText().toString().length() == 4) {
                    performNext();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        vEdtTxtone.addTextChangedListener(MyTextWatcher);
//        vEdtTxttwo.addTextChangedListener(MyTextWatcher);
//        vEdtTxtthree.addTextChangedListener(MyTextWatcher);
//        vEdtTxtfour.addTextChangedListener(MyTextWatcher);


//        vEdtTxtfour.setOnEditorActionListener((v, actionId, event) -> {
//            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
//                performNext();
//            }
//            return false;
//        });

    }


    @Override
    public void onBackPressed() {
        getCodeSnippet().hideKeyboard(ForgotPinVerifyActivity.this);
        setResult(RESULT_CANCELED);
        finish();
    }


    @Override
    public void setEmailText(String emailId) {
        String label = getString(R.string.txt_email_verification_label) + emailId;
        vTvAckText.setText(label);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(ForgotPinVerifyActivity.this);
                onBackPressed();
                break;
        }
        return true;
    }

    /***/
    private void performNext() {
        String otp = pinView.getText().toString();
//                vEdtTxtone.getText().toString().trim() + vEdtTxttwo.getText().toString().trim() + vEdtTxtthree.getText().toString().trim() + vEdtTxtfour.getText().toString().trim();

        if (TextUtils.isEmpty(otp)) {
            showMessage(getString(R.string.enter_valid_otp));
            return;
        }

        if (otp.length() == 4) {
            iForgotPinVerifyPresenter.submitOtp(otp);
        } else {
            showMessage(getString(R.string.enter_valid_otp));
        }

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next:
                performNext();
                break;
        }
    }


//    /***/
//    private boolean isAllFieldsAreFilled() {
//        if (TextUtils.isEmpty(vEdtTxtone.getText())) {
//            return false;
//        }
//
//        if (TextUtils.isEmpty(vEdtTxttwo.getText())) {
//            return false;
//        }
//
//        if (TextUtils.isEmpty(vEdtTxtthree.getText())) {
//            return false;
//        }
//
//        if (TextUtils.isEmpty(vEdtTxtfour.getText())) {
//            return false;
//        }
//        return true;
//    }


    @Override
    public void navigateToResetPassword(Bundle bundle) {
        Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void enableOrDisableNext(boolean b) {
        vBtn.setEnabled(b);
    }


//    /***/
//    private TextWatcher MyTextWatcher = new TextWatcher() {
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            if (vEdtTxtone.getText().hashCode() == s.hashCode()) {
//                if (vEdtTxtone.getText().toString().length() == s.length())
//                    vEdtTxttwo.requestFocus();
//                if (vEdtTxtone.getText().toString().isEmpty()) vEdtTxtone.requestFocus();
//            }
//            if (vEdtTxttwo.getText().hashCode() == s.hashCode()) {
//                if (vEdtTxttwo.getText().toString().length() == s.length())
//                    vEdtTxtthree.requestFocus();
//                if (vEdtTxttwo.getText().toString().isEmpty()) vEdtTxtone.requestFocus();
//            }
//            if (vEdtTxtthree.getText().hashCode() == s.hashCode()) {
//                if (vEdtTxtthree.getText().toString().length() == s.length())
//                    vEdtTxtfour.requestFocus();
//                if (vEdtTxtthree.getText().toString().isEmpty()) vEdtTxttwo.requestFocus();
//            }
//            if (vEdtTxtfour.getText().hashCode() == s.hashCode()) {
//                if (vEdtTxtfour.getText().toString().isEmpty()) vEdtTxtthree.requestFocus();
//            }
//
//            if (isAllFieldsAreFilled()) {
//                performNext();
//            }
//        }
//    };

}
