package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.view.iview.IPinLockActivityView;
import com.returntrader.view.widget.NumberPadView;
import com.returntrader.view.widget.NumberView;

public class PinLockActivity extends BaseActivity implements IPinLockActivityView, TextView.OnEditorActionListener, View.OnClickListener {


    private Button btn;
    private EditText vEtTwo;
    private EditText vEdtTxtthree;
    private EditText vEdtTxtfour;
    private EditText vEdtTxtone;
    private FrameLayout mNumberPadContainer;
    private NumberPadView mNumberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pin_lock);
        bindFragment();

    }

    protected int getLayoutId() {
        return R.layout.activity_pin_lock;
    }

    public void bindFragment() {

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vEdtTxtone =  findViewById(R.id.firstDigit);
        vEtTwo =  findViewById(R.id.secondDigit);
        vEdtTxtthree =  findViewById(R.id.thirdDigit);
        vEdtTxtfour =  findViewById(R.id.fourthDigit);
        mNumberPadContainer =  findViewById(R.id.layout_number_pad_view);

        vEdtTxtone.addTextChangedListener(MyTextWatcher);
        vEtTwo.addTextChangedListener(MyTextWatcher);
        vEdtTxtthree.addTextChangedListener(MyTextWatcher);
        vEdtTxtfour.addTextChangedListener(MyTextWatcher);

        vEdtTxtfour.setOnEditorActionListener(this);

        setUpNumberPad();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(PinLockActivity.this);
                onBackPressed();
                break;
        }
        return true;
    }

    private void setUpNumberPad() {
        mNumberView = new NumberPadView(getActivity());
        mNumberPadContainer.removeAllViews();
        mNumberPadContainer.addView(mNumberView);
        mNumberView.setOnNumberListener(new NumberClickActivity());
    }

    void performNavigate() {
        String mPinValue1 = vEdtTxtone.getText().toString().trim();
        String mPinValue2 = vEtTwo.getText().toString().trim();
        String mPinValue3 = vEdtTxtthree.getText().toString().trim();
        String mPinValue4 = vEdtTxtfour.getText().toString().trim();

        if (mPinValue1.length() != 0 && mPinValue1 != null && mPinValue2.length() != 0 && mPinValue2 != null && mPinValue3.length() != 0 && mPinValue3 != null && mPinValue4.length() != 0 && mPinValue4 != null) {
            String mPinValue = mPinValue1 + mPinValue2 + mPinValue3 + mPinValue4;

            Intent mIntent = new Intent(PinLockActivity.this, ConfirmPinActivity.class);
            mIntent.putExtra("PinValue", mPinValue);
            startActivityForResult(mIntent, Constants.RequestCodes.NAVIGATE_CREATE_PIN_TO_CONFIRM_PIN);
            //finish();
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
                    vEtTwo.requestFocus();
                if (vEdtTxtone.getText().toString().isEmpty()) vEdtTxtone.requestFocus();
            }
            if (vEtTwo.getText().hashCode() == s.hashCode()) {
                if (vEtTwo.getText().toString().length() == s.length())
                    vEdtTxtthree.requestFocus();
                if (vEtTwo.getText().toString().isEmpty()) vEdtTxtone.requestFocus();
            }
            if (vEdtTxtthree.getText().hashCode() == s.hashCode()) {
                if (vEdtTxtthree.getText().toString().length() == s.length())
                    vEdtTxtfour.requestFocus();
                if (vEdtTxtthree.getText().toString().isEmpty()) vEtTwo.requestFocus();
            }
            if (vEdtTxtfour.getText().hashCode() == s.hashCode()) {
                if (vEdtTxtfour.getText().toString().isEmpty()) vEdtTxtthree.requestFocus();
                else if (!vEdtTxtone.getText().toString().isEmpty() && !vEtTwo.getText().toString().isEmpty() && !vEdtTxtthree.getText().toString().isEmpty() && !vEdtTxtfour.getText().toString().isEmpty())


//                    if ((new CommonUtils()) .isAboveMarshmallow()) {
//                        startActivity(new Intent(getApplicationContext(), FingerPrintActivity.class));
//                    }
//                    else{
//                        startActivity(new Intent(getApplicationContext(), DocumentUploadActivity.class));
//                    }
                    performNavigate();

            }
        }
    };

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
//                    if ((new CommonUtils()) .isAboveMarshmallow()) {
//                        startActivity(new Intent(getApplicationContext(), FingerPrintActivity.class));
//                    }
//                    else{
//                        Intent mIntent=new Intent(PinLockActivity.this,ConfirmPinActivity.class);
//                        startActivity(mIntent);
//                        startActivity(new Intent(getApplicationContext(), DocumentUploadActivity.class));
//                    }
            performNavigate();
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCodes.NAVIGATE_CREATE_PIN_TO_CONFIRM_PIN:
                if (resultCode == RESULT_OK) {
                    finish();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {

    }

    private class NumberClickActivity implements NumberView.OnNumberListener {

        @Override
        public void onStart() {

        }

        @Override
        public void onNumberButton(String newNumber) {
            Log.i(TAG, "onNumberButton" + newNumber);
        }

        @Override
        public void onClearButton() {

        }
    }
}