package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.returntrader.R;

public class ConfirmPinActivity extends BaseActivity {

    private EditText vEdtTxtone, vEdtTxttwo, vEdtTxtthree, vEdtTxtfour;
    private Bundle mBundle;
    private String mPinValueFromactivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayoutId());

        findByViews();


        mBundle = getIntent().getExtras();
        mPinValueFromactivity = mBundle.getString("PinValue");
    }

    protected int getLayoutId() {
        return R.layout.activity_confirm_pin;
    }

    public void findByViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vEdtTxtone = (EditText) findViewById(R.id.firstDigit);
        vEdtTxttwo = (EditText) findViewById(R.id.secondDigit);
        vEdtTxtthree = (EditText) findViewById(R.id.thirdDigit);
        vEdtTxtfour = (EditText) findViewById(R.id.fourthDigit);

        vEdtTxtone.addTextChangedListener(MyTextWatcher);
        vEdtTxttwo.addTextChangedListener(MyTextWatcher);
        vEdtTxtthree.addTextChangedListener(MyTextWatcher);
        vEdtTxtfour.addTextChangedListener(MyTextWatcher);


        vEdtTxtfour.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    performNavigate();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(ConfirmPinActivity.this);
                onBackPressed();
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        getCodeSnippet().hideKeyboard(ConfirmPinActivity.this);
        super.onBackPressed();
    }

    void performNavigate() {
        String mPinValue1 = vEdtTxtone.getText().toString().trim();
        String mPinValue2 = vEdtTxttwo.getText().toString().trim();
        String mPinValue3 = vEdtTxtthree.getText().toString().trim();
        String mPinValue4 = vEdtTxtfour.getText().toString().trim();

        if (mPinValue1.length() != 0 && mPinValue1 != null && mPinValue2.length() != 0 && mPinValue2 != null && mPinValue3.length() != 0 && mPinValue3 != null && mPinValue4.length() != 0 && mPinValue4 != null) {
            String mPinValue = mPinValue1 + mPinValue2 + mPinValue3 + mPinValue4;
            if (mPinValueFromactivity.equalsIgnoreCase(mPinValue)) {
                if (getCodeSnippet().isAboveMarshmallow()) {
                    startActivity(new Intent(getApplicationContext(), FingerPrintActivity.class));
                    setResult(RESULT_OK);
                    finish();
                } else {
                    setResult(RESULT_OK);
                    startActivity(new Intent(getApplicationContext(), ThankYouActivity.class));
                    finish();
                }
            } else {
                showMessage("Please make sure Pin and Confirm pin should be same");
            }
        } else {
            showMessage("Please enter valid pin");
        }

    }

    private TextWatcher MyTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (vEdtTxtone.getText().hashCode() == s.hashCode()) {
                if (vEdtTxtone.getText().toString().length() == s.length())
                    vEdtTxttwo.requestFocus();
                if (vEdtTxtone.getText().toString().isEmpty()) vEdtTxtone.requestFocus();
            }
            if (vEdtTxttwo.getText().hashCode() == s.hashCode()) {
                if (vEdtTxttwo.getText().toString().length() == s.length())
                    vEdtTxtthree.requestFocus();
                if (vEdtTxttwo.getText().toString().isEmpty()) vEdtTxtone.requestFocus();
            }
            if (vEdtTxtthree.getText().hashCode() == s.hashCode()) {
                if (vEdtTxtthree.getText().toString().length() == s.length())
                    vEdtTxtfour.requestFocus();
                if (vEdtTxtthree.getText().toString().isEmpty()) vEdtTxttwo.requestFocus();
            }
            if (vEdtTxtfour.getText().hashCode() == s.hashCode()) {
                if (vEdtTxtfour.getText().toString().isEmpty()) vEdtTxtthree.requestFocus();
                else if (!vEdtTxtone.getText().toString().isEmpty() && !vEdtTxttwo.getText().toString().isEmpty() && !vEdtTxtthree.getText().toString().isEmpty() && !vEdtTxtfour.getText().toString().isEmpty())

                    performNavigate();
            }
        }
    };
}
