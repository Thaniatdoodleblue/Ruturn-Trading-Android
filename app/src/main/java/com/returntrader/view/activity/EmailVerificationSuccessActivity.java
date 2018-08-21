package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;

public class EmailVerificationSuccessActivity extends BaseActivity implements View.OnClickListener {


    private TextView vTvRefresh;
    private Handler mTimeOutHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_email_verification_success);
        findByViews();

    }

    public void findByViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.tv_toolbar_title);
      //  toolbarTitle.setText(R.string.txt_title_email_verification);

        vTvRefresh = (TextView) findViewById(R.id.tv_refresh);
        vTvRefresh.setOnClickListener(this);


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mTimeOutHandler = new Handler();
        mTimeOutHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.BundleKey.BUNDLE_AUTHENTICATION_FROM, Constants.AuthenticationType.FROM_REGISTRATION);
                navigateToPinLock(bundle);
            }
        }, 1000);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(EmailVerificationSuccessActivity.this);
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_refresh:
                //  navigateToPinLock(null);
                break;

        }
    }

    public void navigateToPinLock(Bundle bundle) {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
        setResult(RESULT_OK);
        finish();
    }

    public void navigateToEmailEntry() {
        setResult(RESULT_CANCELED);
        finish();
    }
}