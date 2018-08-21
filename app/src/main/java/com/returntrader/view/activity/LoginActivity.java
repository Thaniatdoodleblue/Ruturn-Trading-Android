package com.returntrader.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.model.dto.request.LoginRequest;
import com.returntrader.permissions.IPermissionHandler;
import com.returntrader.permissions.PermissionProducer;
import com.returntrader.permissions.RequestPermission;
import com.returntrader.presenter.LoginPresenter;
import com.returntrader.presenter.ipresenter.ILoginPresenter;
import com.returntrader.view.iview.ILoginView;

/**
 * Created by azeem on 10-Nov-17
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, ILoginView, PermissionProducer {
    private ILoginPresenter iLoginPresenter;
    private EditText mEtEmail, mEtCellNumber, mEtRsaId;
    private Button btNext;
    private IPermissionHandler iPermissionHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindActivity();
        iPermissionHandler = RequestPermission.newInstance(this);
        iLoginPresenter = new LoginPresenter(this);
        iLoginPresenter.onCreatePresenter(getIntent().getExtras());
    }

    /***/
    private void requestSmsPermission() {
        if (getCodeSnippet().isAboveMarshmallow()) {
            iPermissionHandler.callSmsPermissionHandler();
        }
    }

    @Override
    public void onReceivedPermissionStatus(int code, boolean isGrated) {
        switch (code) {
            case IPermissionHandler.PERMISSIONS_REQUEST_RECEIVE_SMS:
                doTriggerLogin();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case IPermissionHandler.PERMISSIONS_REQUEST_RECEIVE_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onReceivedPermissionStatus(IPermissionHandler.PERMISSIONS_REQUEST_RECEIVE_SMS, true);
                } else {
                    onReceivedPermissionStatus(IPermissionHandler.PERMISSIONS_REQUEST_RECEIVE_SMS, false);
                }
            }
        }
    }

    /***/
    private void bindActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.title_login);

        mEtEmail = findViewById(R.id.et_email);
        mEtCellNumber = findViewById(R.id.et_cell_number);
        mEtRsaId = findViewById(R.id.et_rsa_id_number);
        btNext = findViewById(R.id.btn_next);

        //Basic User
//        mEtEmail.setText("jk@mailinator.com");
//        mEtCellNumber.setText("0729700000");
//        mEtRsaId.setText("8904014567011");

        //FullReg User
//        mEtEmail.setText("nirmal@mailinator.com");
//        mEtCellNumber.setText("0769046240");
//        mEtRsaId.setText("9305123698753");

        //Live User
      /*  mEtEmail.setText("vvn0880@gmail.com");
        mEtCellNumber.setText("0729782960");
        mEtRsaId.setText("8808036161089");*/

//        mEtEmail.setText("km@mailinator.com");
//        mEtCellNumber.setText("0729708521");
//        mEtRsaId.setText("9004024444033");

//        mEtEmail.setText("jeff@mailinator.com");
//        mEtCellNumber.setText("0729712345");
//        mEtRsaId.setText("8904014444033");

        mEtEmail.addTextChangedListener(mTextWatcher);
        mEtCellNumber.addTextChangedListener(mTextWatcher);
        mEtRsaId.addTextChangedListener(mTextWatcher);
    }

    /***/
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (getCodeSnippet().isValidEmail(mEtEmail.getText()) && mEtCellNumber.getText().length() == 10 && mEtRsaId.getText().length() == Constants.Common.MAX_RSA_ID_LENGTH) {
                setNextButtonColor(true);
            } else {
                setNextButtonColor(false);
            }
        }
    };


    /***/
    private void setNextButtonColor(boolean isValid) {
        if (isValid) {
            btNext.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            btNext.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
        } else {
            btNext.setTextColor(ContextCompat.getColor(this, R.color.homePage));
            btNext.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        }
    }

    /*private void showLocationPicker() {
        try {
            PlaceAutocomplete.IntentBuilder intentBuilder = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY);
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setCountry(Constants.Common.DEFAULT_ISO_COUNTRY_CODE)
                    .build();
            intentBuilder.setFilter(typeFilter);
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, Constants.RequestCodes.PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);
            showMessage(message);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constants.RequestCodes.PLACE_AUTOCOMPLETE_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

                    try {
                        List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 5);
                        if (addresses.size() > 0) {
                            tvAddress.setText("getSubThoroughfare- " + addresses.get(0).getSubThoroughfare() + "\n" +
                                    "getAddressLine- " + addresses.get(0).getAddressLine(0) + "\n" +
                                    "getLocality- " + addresses.get(0).getLocality() + "\n" +
                                    "getAdminArea- " + addresses.get(0).getAdminArea() + "\n" +
                                    "getPostalCode- " + addresses.get(0).getPostalCode() + "\n" +
                                    "getCountryCode- " + addresses.get(0).getCountryCode() + "\n" +
                                    "getCountryName- " + addresses.get(0).getCountryName() + "\n" +
                                    "getFeatureName- " + addresses.get(0).getFeatureName() + "\n" +
                                    "getPhone- " + addresses.get(0).getPhone() + "\n" +
                                    "getPremises- " + addresses.get(0).getPremises() + "\n" +
                                    "getSubAdminArea- " + addresses.get(0).getSubAdminArea() + "\n" +
                                    "getSubLocality- " + addresses.get(0).getSubLocality() + "\n" +
                                    "getThoroughfare- " + addresses.get(0).getThoroughfare() + "\n" +
                                    "getUrl- " + addresses.get(0).getUrl() + "\n" +
                                    "getLatitude- " + addresses.get(0).getLatitude() + "\n" +
                                    "getLocale- " + addresses.get(0).getLocale() + "\n" +
                                    "getLongitude- " + addresses.get(0).getLongitude() + "\n" +
                                    "getMaxAddressLineIndex- " + addresses.get(0).getMaxAddressLineIndex() + "\n");


                            *//*Log.e("getSubThoroughfare", "" + addresses.get(0).getSubThoroughfare());
                            Log.e("getAddressLine", "" + addresses.get(0).getAddressLine(0));
                            Log.e("getLocality", "" + addresses.get(0).getLocality());
                            Log.e("getAdminArea", "" + addresses.get(0).getAdminArea());
                            Log.e("getPostalCode", "" + addresses.get(0).getPostalCode());
                            Log.e("getCountryCode", "" + addresses.get(0).getCountryCode());
                            Log.e("getCountryName", "" + addresses.get(0).getCountryName());
                            Log.e("getFeatureName", "" + addresses.get(0).getFeatureName());
                            Log.e("getPhone", "" + addresses.get(0).getPhone());
                            Log.e("getPremises", "" + addresses.get(0).getPremises());
                            Log.e("getSubAdminArea", "" + addresses.get(0).getSubAdminArea());
                            Log.e("getSubLocality", "" + addresses.get(0).getSubLocality());
                            Log.e("getThoroughfare", "" + addresses.get(0).getThoroughfare());
                            Log.e("getUrl", "" + addresses.get(0).getUrl());
                            Log.e("getLatitude", "" + addresses.get(0).getLatitude());
                            Log.e("getLocale", "" + addresses.get(0).getLocale());
                            Log.e("getLongitude", "" + addresses.get(0).getLongitude());
                            Log.e("getMaxAddressLineIndex", "" + addresses.get(0).getMaxAddressLineIndex());*//*
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                    Log.i(TAG, status.getStatusMessage());
                }
                break;
        }

    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                if (validateFields()) {
//                    doTriggerLogin();
                    requestSmsPermission();
                }
                break;
        }
    }

    /***/
    private void doTriggerLogin() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmailId(mEtEmail.getText().toString().trim());
        loginRequest.setPhoneNumber(mEtCellNumber.getText().toString().trim());
        loginRequest.setRsaId(mEtRsaId.getText().toString().trim());
        iLoginPresenter.doLogin(loginRequest);
    }

    /***/
    private boolean validateFields() {
        if (getCodeSnippet().isValidEmail(mEtEmail.getText().toString().trim())) {
            findViewById(R.id.tv_error_email).setVisibility(View.GONE);
        } else {
            findViewById(R.id.tv_error_email).setVisibility(View.VISIBLE);
            return false;
        }

        if (mEtCellNumber.length() == 0 || mEtCellNumber.length() < 10) {
            findViewById(R.id.tv_error_mobile_number).setVisibility(View.VISIBLE);
            return false;
        } else {
            findViewById(R.id.tv_error_mobile_number).setVisibility(View.GONE);
        }

        if (mEtRsaId.length() == 0 || mEtRsaId.length() < Constants.Common.MAX_RSA_ID_LENGTH) {
            findViewById(R.id.tv_error_rsa_id).setVisibility(View.VISIBLE);
            return false;
        } else {
            findViewById(R.id.tv_error_rsa_id).setVisibility(View.GONE);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(LoginActivity.this);
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void navigateToAuthentication(Bundle bundle) {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
    }
}
