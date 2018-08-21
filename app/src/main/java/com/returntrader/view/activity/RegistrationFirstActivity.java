package com.returntrader.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.library.Log;

import java.util.Arrays;
import java.util.List;

import static android.content.DialogInterface.*;

public class RegistrationFirstActivity extends BaseActivity implements View.OnClickListener {

    private EditText vEdtTxtTitle;
    private EditText vEdtTxtFirstNme;
    private EditText vEdtTxtLastName;
    private EditText vEdtTxUserName;
    private EditText vEdtTxtPassword;
    private EditText vEdtTxtConfirmPassword;
    private EditText vEdtTxtPhnWork;
    private EditText vEdtTxtPhnMobile;
    private EditText vEdtTxtSecurityQsn;
    private EditText vEdtTxtSecurityAnswer;
    private EditText vEdtTxtIdentityNum;
    private EditText vEdtTxtIdentityType;
    private EditText vEdtTxtEmailId;

    private Button vBtnNext;
    private int mSelectPosi = 0;
    private int mSelectPosiSecurity = 0;

    private List<String> mTitleList = Arrays.asList("Dr", "Miss", "Mr", "Mrs", "Ms", "Prof", "Sir");

    private List<String> mSecurityQsnList = Arrays.asList("What is the name of your pet?", "What is your mother's maiden name?", "What is the name of your birth city/town?");

    private RadioGroup vGender_radio_group;
    private RadioButton vMale, vFemale;

    private String mFirstnameValue;
    private String mLastnameValue;
    private String mGenderValue;
    private String mEmailValue;
    private String mUserNameValue;
    private String mPasswordValue;
    private String mConfirmPasswordValue;
    private String mWorkPhoneValue;
    private String mMobilenumberValue;
    private String mSecurityqsnValue;
    private String mSecurityAnswerValue;
    private String mIdentityTypeValue;
    private String mIdentityNumberValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initializeViews();
    }

    public void initializeViews() {

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        vEdtTxtTitle =  findViewById(R.id.vEdtTxtTitle);
        vEdtTxtTitle.setOnClickListener(this);
        vEdtTxtFirstNme = findViewById(R.id.vEdtTxtFirstNme);
        vEdtTxtLastName =  findViewById(R.id.vEdtTxtLastName);


        vEdtTxtFirstNme.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        vEdtTxtLastName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        vEdtTxUserName =  findViewById(R.id.vEdtTxUserName);
        vEdtTxtEmailId =  findViewById(R.id.vEdtTxtEmailId);

        vEdtTxtPassword =  findViewById(R.id.vEdtTxtPassword);
        vEdtTxtConfirmPassword =  findViewById(R.id.vEdtTxtConfirmPassword);
        vEdtTxtPhnWork =  findViewById(R.id.vEdtTxtPhnWork);
        vEdtTxtPhnMobile =  findViewById(R.id.vEdtTxtPhnMobile);
        vEdtTxtSecurityQsn =  findViewById(R.id.vEdtTxtSecurityQsn);
        vEdtTxtSecurityQsn.setOnClickListener(this);

        vEdtTxtSecurityAnswer =  findViewById(R.id.vEdtTxtSecurityAnswer);
        vGender_radio_group =  findViewById(R.id.vGender_radio_group);
        vMale =  findViewById(R.id.vMale);
        vFemale =  findViewById(R.id.vFemale);

        vEdtTxtIdentityNum =  findViewById(R.id.vEdtTxtIdentityNum);
        vEdtTxtIdentityType =  findViewById(R.id.vEdtTxtIdentityType);


        vEdtTxtIdentityType.setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                performNext();
            }


            return false;
        });

        vBtnNext = findViewById(R.id.vBtnNext);
        vBtnNext.setOnClickListener(this);
    }

    protected int getLayoutId() {
        return R.layout.activity_registration_first;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(RegistrationFirstActivity.this);
                onBackPressed();
                break;
        }
        return true;
    }

    void performNext() {
        String mTitleValue = vEdtTxtTitle.getText().toString().trim();
        mFirstnameValue = vEdtTxtFirstNme.getText().toString().trim();
        mEmailValue = vEdtTxtEmailId.getText().toString().trim();
        mLastnameValue = vEdtTxtLastName.getText().toString().trim();

        mUserNameValue = vEdtTxUserName.getText().toString().trim();
        mPasswordValue = vEdtTxtPassword.getText().toString().trim();
        mConfirmPasswordValue = vEdtTxtConfirmPassword.getText().toString().trim();

        mWorkPhoneValue = vEdtTxtPhnWork.getText().toString().trim();
        mMobilenumberValue = vEdtTxtPhnMobile.getText().toString().trim();

        mSecurityqsnValue = vEdtTxtSecurityQsn.getText().toString().trim();
        mSecurityAnswerValue = vEdtTxtSecurityAnswer.getText().toString().trim();
        mIdentityNumberValue = vEdtTxtIdentityNum.getText().toString().trim();
        mIdentityTypeValue = vEdtTxtIdentityType.getText().toString().trim();


        int selectedRadioId = vGender_radio_group.getCheckedRadioButtonId();
        vMale = findViewById(selectedRadioId);
        mGenderValue = vMale.getText().toString().trim();


        if (mTitleValue == null || mTitleValue.length() == 0) {
            showMessage("Please select the title");
        } else if (mFirstnameValue == null || mFirstnameValue.length() == 0) {
            showMessage("Please enter the Firstname");
        } else if (mLastnameValue == null || mLastnameValue.length() == 0) {
            showMessage("Please enter the Lastname");
        } else if (mEmailValue == null || mEmailValue.length() == 0) {
            showMessage("Please enter the Email Id");
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmailValue).matches()) {
            showMessage("Please enter the Valid Email Id");
        } else if (mUserNameValue == null || mUserNameValue.length() == 0) {
            showMessage("Please enter the Username");
        } else if (mPasswordValue == null || mPasswordValue.length() == 0) {
            showMessage("Please enter the Password");
        } else if (mConfirmPasswordValue == null || mConfirmPasswordValue.length() == 0) {
            showMessage("Please enter the Confirm Password");
        } else if (mWorkPhoneValue == null || mWorkPhoneValue.length() == 0) {
            showMessage("Please enter the Work Phone");
        } else if (mMobilenumberValue == null || mMobilenumberValue.length() == 0) {
            showMessage("Please enter the Mobile number");
        } else if (mSecurityqsnValue == null || mSecurityqsnValue.length() == 0) {
            showMessage("Please select a Security Question");
        } else if (mSecurityAnswerValue == null || mSecurityAnswerValue.length() == 0) {
            showMessage("Please enter the Answer for Security Question");
        } else if (mIdentityNumberValue == null || mIdentityNumberValue.length() == 0) {
            showMessage("Please enter the Identity Number");
        } else if (mIdentityTypeValue == null || mIdentityTypeValue.length() == 0) {
            showMessage("Please enter the Identity Type");
        } else if (mGenderValue == null || mGenderValue.length() == 0) {
            showMessage("Please select the gender");
        } else if (!mPasswordValue.equalsIgnoreCase(mConfirmPasswordValue)) {
            showMessage("Please make sure password and confirm password should be the same");
        } else {
            Intent intent = new Intent(RegistrationFirstActivity.this, RegistrationSecondActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.vEdtTxtTitle:
                onCreateDialogSingleChoice(mTitleList, "Title", mSelectPosi);
                break;
            case R.id.vEdtTxtSecurityQsn:
                onCreateDialogSingleChoice(mSecurityQsnList, "Security Question", mSelectPosiSecurity);
                break;
            case R.id.vBtnNext:
                performNext();
                break;
        }
    }

    public void onCreateDialogSingleChoice(List<String> list, final String title, int pos) {

//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//Source of the data in the DIalog
        String[] array = list.toArray(new String[list.size()]);
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
// Set the dialog title
        builder.setTitle(title)

// Specify the list array, the items to be selected by default (null for none),
// and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(array, pos, (dialog, which) -> {
// TODO Auto-generated method stub


                })

// Set the action buttons
                .setPositiveButton("Ok", (dialog, id) -> {
// User clicked OK, so save the result somewhere
// or return them to the component that opened the dialog

                    ListView lw = ((AlertDialog) dialog).getListView();
                    Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());

                    if (title.equalsIgnoreCase("Title")) {
                        vEdtTxtTitle.setText(checkedItem.toString());
                        mSelectPosi = lw.getCheckedItemPosition();
                    } else if (title.equalsIgnoreCase("Security Question")) {
                        vEdtTxtSecurityQsn.setText(checkedItem.toString());
                        mSelectPosiSecurity = lw.getCheckedItemPosition();
                    }

                    Log.d(getLocalClassName(), " Selected Item " + checkedItem.toString());
//                        ad.dismiss();
                    dialog.dismiss();

                })
                .setNegativeButton("Cancel", (dialog, id) -> {
//                        ad.dismiss();
                    dialog.dismiss();
                });
        builder.show();
    }
}
