package com.returntrader.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.library.Log;

import java.util.Arrays;
import java.util.List;

public class RegistrationSecondActivity extends BaseActivity implements View.OnClickListener {


    boolean mPostSelectBool;
    List<String> mProvincesList;
    List<String> mMarketingList;
    String mBankNameValue;
    String mProviceDropValue;
    String mMarketingChannelValue;
    String mProvincePostValue;
    String mAccnTypeValue;
    String mAccntNumValue;
    String mAccntHolderNameValue;
    String mBrnchNameValue;
    String mBrnchCodeValue;
    String mLine1Value;
    String mSuburbValue;
    String mCityValue;
    String mCodeValue;
    String mLine1PostValue;
    String mSuburbPostValue;
    String mCityPostValue;
    String mCodePostValue;
    String mMrktngChnlPostValue;
    String mProviceDropPostValue;
    int mSelectPosi = 0, mSelectposiProvince = 0, mSelectPosiMarketing = 0, mSelectposiProvincePost = 0;
    private Button vBtnSubmit;
    private TextView vTxtagreeText;
    private CheckBox checkBoxPost, checkBoxTerms;
    private LinearLayout vPostalLay;
    private EditText vEdtTxtBankName;
    private EditText vEdtTxtProvince;
    private EditText vEdtTxtProvincePost;
    private EditText vEdtTxtAccntType;
    private EditText vEdtTxtAccountNum;
    private EditText vEdtTxtAccountHolderName;
    private EditText vEdtTxtBranchName;
    private
    EditText vEdtTxtBranhCode;
    private EditText vEdtTxtMarketingChannel;
    private EditText vEdtTxtSuburb;
    private EditText vEdtTxtCity;
    private EditText vEdtTxtCode;
    private EditText vEdtTxtLine1Post;
    private EditText vEdtTxtSuburbPost;
    private EditText vEdtTxtCityPost;
    private EditText vEdtTxtLine1;
    private EditText vEdtTxtCodePost;
    private List<String> bankList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        iNitialize();
        listViewData();
    }

    protected int getLayoutId() {
        return R.layout.activity_registration_second;
    }

    public void iNitialize() {
        vTxtagreeText = (TextView) findViewById(R.id.vTxtagreeText);
        checkBoxPost = (CheckBox) findViewById(R.id.checkBoxPost);

        vPostalLay = (LinearLayout) findViewById(R.id.vPostalLay);

        vEdtTxtBankName = (EditText) findViewById(R.id.vEdtTxtBankName);
        vEdtTxtProvince = (EditText) findViewById(R.id.vEdtTxtProvince);
        vEdtTxtMarketingChannel = (EditText) findViewById(R.id.vEdtTxtMarketingChannel);
        vEdtTxtProvincePost = (EditText) findViewById(R.id.vEdtTxtProvincePost);


        vEdtTxtAccntType = (EditText) findViewById(R.id.vEdtTxtAccntType);
        vEdtTxtAccountNum = (EditText) findViewById(R.id.vEdtTxtAccountNum);
        vEdtTxtAccountHolderName = (EditText) findViewById(R.id.vEdtTxtAccountHolderName);
        vEdtTxtBranchName = (EditText) findViewById(R.id.vEdtTxtBranchName);


        vEdtTxtBranhCode = (EditText) findViewById(R.id.vEdtTxtBranhCode);
        vEdtTxtLine1 = (EditText) findViewById(R.id.vEdtTxtLine1);
        vEdtTxtSuburb = (EditText) findViewById(R.id.vEdtTxtSuburb);
        vEdtTxtCity = (EditText) findViewById(R.id.vEdtTxtCity);
        vEdtTxtCode = (EditText) findViewById(R.id.vEdtTxtCode);


        vEdtTxtLine1Post = (EditText) findViewById(R.id.vEdtTxtLine1Post);
        vEdtTxtSuburbPost = (EditText) findViewById(R.id.vEdtTxtSuburbPost);
        vEdtTxtCityPost = (EditText) findViewById(R.id.vEdtTxtCityPost);
        vEdtTxtCodePost = (EditText) findViewById(R.id.vEdtTxtCodePost);


        checkBoxTerms = (CheckBox) findViewById(R.id.checkBoxTerms);


        vEdtTxtBankName.setOnClickListener(this);
        vEdtTxtProvince.setOnClickListener(this);
        vEdtTxtMarketingChannel.setOnClickListener(this);
        vEdtTxtProvincePost.setOnClickListener(this);
        checkBoxPost.setOnClickListener(this);
        String agree1 = "<font color='#3d3d3d'>I agree</font>";
        String agree2 = "<font color='#3498DB'>&nbsp;Terms and conditions </font>";
        vTxtagreeText.setText(Html.fromHtml(agree1 + agree2));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        vBtnSubmit = (Button) findViewById(R.id.vBtnSubmit);
        vBtnSubmit.setOnClickListener(this);

    }

    void listViewData() {
        mProvincesList = Arrays.asList("Eastern Cape", "Free State", "Gauteng", "Kwa-Zulu Natal", "Limpopo", "Mpumalanga", "Non Resident", "North West", "Northern Cape", "Western Cape");
        mMarketingList = Arrays.asList("UJ FM", "Fintech Africa 2016", "None of the above", "Investa on WiFi TV", "Radio", "#Invest", "Online", "Television", "Street Poles/Billboards", "Rapport", "IIG Annual Dinner 2016", "BizNews", "From a friend");

    }

    void performSubmit() {

        mBankNameValue = vEdtTxtBankName.getText().toString().trim();
        mProviceDropValue = vEdtTxtProvince.getText().toString().trim();
        mProvincePostValue = vEdtTxtProvincePost.getText().toString().trim();
        mMarketingChannelValue = vEdtTxtMarketingChannel.getText().toString().trim();

        mAccnTypeValue = vEdtTxtAccntType.getText().toString().trim();
        mAccntNumValue = vEdtTxtAccountNum.getText().toString().trim();
        mAccntHolderNameValue = vEdtTxtAccountHolderName.getText().toString().trim();

        mBankNameValue = vEdtTxtBranchName.getText().toString().trim();
        mBrnchCodeValue = vEdtTxtBranhCode.getText().toString().trim();

        mLine1Value = vEdtTxtLine1.getText().toString().trim();
        mSuburbValue = vEdtTxtSuburb.getText().toString().trim();
        mCityValue = vEdtTxtCity.getText().toString().trim();
        mProviceDropValue = vEdtTxtProvince.getText().toString().trim();
        mCodeValue = vEdtTxtCode.getText().toString().trim();

        mLine1PostValue = vEdtTxtLine1Post.getText().toString().trim();
        mSuburbPostValue = vEdtTxtSuburbPost.getText().toString().trim();
        mCityPostValue = vEdtTxtCityPost.getText().toString().trim();
        mProviceDropPostValue = vEdtTxtProvincePost.getText().toString().trim();
        mCodePostValue = vEdtTxtCodePost.getText().toString().trim();
        mBrnchNameValue = vEdtTxtBranchName.getText().toString().trim();


        if (mAccnTypeValue == null || mAccnTypeValue.length() == 0) {
            showMessage("Please enter the Account type");
        } else if (mBankNameValue == null || mBankNameValue.length() == 0) {
            showMessage("Please select the Bank name");
        } else if (mProviceDropValue == null || mProviceDropValue.length() == 0) {
            showMessage("Please select the Province");
        } else if (mMarketingChannelValue == null || mMarketingChannelValue.length() == 0) {
            showMessage("Please select the How did you find about us");
        } else if (mAccntNumValue == null || mAccntNumValue.length() == 0) {
            showMessage("Please enter the Account number");
        } else if (mAccntHolderNameValue == null || mAccntHolderNameValue.length() == 0) {
            showMessage("Please enter the Account holder name");
        } else if (mBankNameValue == null || mBankNameValue.length() == 0) {
            showMessage("Please select the Bank type");
        } else if (mBrnchCodeValue == null || mBrnchCodeValue.length() == 0) {
            showMessage("Please enter the Branchcode");
        } else if (mLine1Value == null || mLine1Value.length() == 0) {
            showMessage("Please enter the Line1 ");
        } else if (mSuburbValue == null || mSuburbValue.length() == 0) {
            showMessage("Please enter the Suburb");
        } else if (mCityValue == null || mCityValue.length() == 0) {
            showMessage("Please enter the City");
        } else if (mCodeValue == null || mCodeValue.length() == 0) {
            showMessage("Please enter the Code");
        } else if (!checkBoxPost.isChecked()) {
            if (mLine1PostValue == null || mLine1PostValue.length() == 0) {
                showMessage("Please enter the Line1 in postal address");
            } else if (mSuburbPostValue == null || mSuburbPostValue.length() == 0) {
                showMessage("Please enter the Suburb in postal address");
            } else if (mCityPostValue == null || mCityPostValue.length() == 0) {
                showMessage("Please enter the City in postal address");
            } else if (mProviceDropPostValue == null || mProviceDropPostValue.length() == 0) {
                showMessage("Please select the Province in postal address");
            } else if (mCodePostValue == null || mCodePostValue.length() == 0) {
                showMessage("Please enter the Code in postal address");
            }
        } else if (!checkBoxTerms.isChecked()) {
            showMessage("Please Agree Terms and Conditions");
        } else {
            startActivity(new Intent(getApplicationContext(), EmailAckActivity.class));
        }


    }


    void performSettingPostalAddress() {


        mLine1Value = vEdtTxtLine1.getText().toString().trim();
        mSuburbValue = vEdtTxtSuburb.getText().toString().trim();
        mCityValue = vEdtTxtCity.getText().toString().trim();
        mProviceDropValue = vEdtTxtProvince.getText().toString().trim();
        mCodeValue = vEdtTxtCode.getText().toString().trim();
        if (mLine1Value != null && mLine1Value.length() != 0 && mSuburbValue != null && mSuburbValue.length() != 0 && mCityValue != null && mCityValue.length() != 0 && mProviceDropValue != null && mProviceDropValue.length() != 0 && mCodeValue != null && mCodeValue.length() != 0) {

            vEdtTxtLine1Post.setText(mLine1Value);
            vEdtTxtSuburbPost.setText(mSuburbValue);
            vEdtTxtCityPost.setText(mCityValue);
            vEdtTxtProvincePost.setText(mProviceDropValue);
            vEdtTxtCodePost.setText(mCodeValue);

            checkBoxPost.setChecked(true);
            vPostalLay.setVisibility(View.GONE);
        } else {
            vEdtTxtLine1Post.setText("");
            vEdtTxtSuburbPost.setText("");
            vEdtTxtCityPost.setText("");
            vEdtTxtProvincePost.setText("");
            vEdtTxtCodePost.setText("");

            checkBoxPost.setChecked(false);
            vPostalLay.setVisibility(View.VISIBLE);
            showMessage("Please fill the Residential address");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(RegistrationSecondActivity.this);
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.checkBoxPost:

                performSettingPostalAddress();

                if (checkBoxPost.isChecked()) {
                    vPostalLay.setVisibility(View.GONE);
                } else if (!checkBoxPost.isChecked()) {
                    vPostalLay.setVisibility(View.VISIBLE);
                }


                break;
            case R.id.vBtnSubmit:
                performSubmit();
//                startActivity(new Intent(getApplicationContext(), EmailAckActivity.class));
                break;

            case R.id.vEdtTxtBankName:
                onCreateDialogSingleChoice(getBankList(), "Bank Name", mSelectPosi);
                break;
            case R.id.vEdtTxtProvince:
                onCreateDialogSingleChoice(mProvincesList, "Province", mSelectposiProvince);
                break;
            case R.id.vEdtTxtMarketingChannel:
                onCreateDialogSingleChoice(mMarketingList, "How did you find about us?", mSelectPosiMarketing);
                break;
            case R.id.vEdtTxtProvincePost:
                onCreateDialogSingleChoice(mProvincesList, "Province ", mSelectposiProvincePost);
                break;
        }

    }

    public void onCreateDialogSingleChoice(List<String> list, final String title, int pos) {

//Initialize the Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] array = list.toArray(new String[list.size()]);
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        builder.setTitle(title)
                .setSingleChoiceItems(array, pos, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        ListView lw = ((AlertDialog) dialog).getListView();
                        Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());

                        if (title.equalsIgnoreCase("Bank Name")) {
                            vEdtTxtBankName.setText(checkedItem.toString());
                            mSelectPosi = lw.getCheckedItemPosition();
                        } else if (title.equalsIgnoreCase("Province")) {
                            vEdtTxtProvince.setText(checkedItem.toString());
                            mSelectposiProvince = lw.getCheckedItemPosition();
                        } else if (title.equalsIgnoreCase("How did you find about us?")) {
                            vEdtTxtMarketingChannel.setText(checkedItem.toString());
                            mSelectPosiMarketing = lw.getCheckedItemPosition();
                        } else if (title.equalsIgnoreCase("Province ")) {
                            vEdtTxtProvincePost.setText(checkedItem.toString());
                            mSelectposiProvincePost = lw.getCheckedItemPosition();
                        }

                        Log.d(getLocalClassName(), " Selected Item " + checkedItem.toString());
//                        ad.dismiss();
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
//                        ad.dismiss();
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    public List<String> getBankList() {
        return Arrays.asList("ABSA",
                "Standard Bank",
                "FNB", "Nedbank",
                "Capitec", "African Bank",
                "Albaraka Bank",
                "Bidvest Bank",
                "First Rand Bank",
                "Investec Bank",
                "Mercantile",
                "PostBank");
    }
}
