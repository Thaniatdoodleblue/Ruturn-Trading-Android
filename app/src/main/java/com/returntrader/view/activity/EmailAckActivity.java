package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.presenter.EmailAckPresenter;
import com.returntrader.presenter.ipresenter.IEmailAckActivityPresenter;
import com.returntrader.view.iview.IEmailAckActivityView;
import com.returntrader.view.widget.CustomTextView;
import com.returntrader.view.widget.DrawableClickListener;

public class EmailAckActivity extends BaseActivity implements IEmailAckActivityView, View.OnClickListener, DrawableClickListener {


    private TextView vTvRefresh;
    private TextView vTvStatus;
    private ImageView vImageEmailStatus;
    private CustomTextView vTvAckText;
    private Handler mTimeOutHandler;
    private IEmailAckActivityPresenter iEmailAckActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_email_ack);
        findByViews();
        iEmailAckActivityPresenter = new EmailAckPresenter(this);
        iEmailAckActivityPresenter.onCreatePresenter(getIntent().getExtras());
    }

    public void findByViews() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbarTitle =  toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.txt_title_email_verification);

        vTvRefresh =  findViewById(R.id.tv_refresh);
        vTvAckText =  findViewById(R.id.tv_ack_text);
        vTvStatus =  findViewById(R.id.tv_status);
        vImageEmailStatus =  findViewById(R.id.image_email_status);
        vTvRefresh.setOnClickListener(this);

        vTvAckText.setOnClickListener(this);
        vTvAckText.setDrawableClickListener(this);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(EmailAckActivity.this);
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_refresh:
                if (iEmailAckActivityPresenter.isEmailVerified()) {
                    //navigateToPinLock(null);
                } else {
                    iEmailAckActivityPresenter.requestToVerifyEmail();
                }
                break;
            case R.id.tv_ack_text:
                break;
            case R.id.tv_resend_email:
                iEmailAckActivityPresenter.reSendEmail();
                break;
        }
    }


    @Override
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

    @Override
    public void displayEmailVerificationDone() {
        vImageEmailStatus.setImageResource(R.drawable.ic_view_mail_verified);
        vTvRefresh.setText(R.string.txt_next);
        vTvStatus.setText(R.string.txt_email_verified);
        findViewById(R.id.tv_resend_email).setVisibility(View.GONE);
        findViewById(R.id.tv_label_verification).setVisibility(View.GONE);
        findViewById(R.id.note).setVisibility(View.GONE);
        vTvAckText.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mTimeOutHandler = new Handler();
        mTimeOutHandler.postDelayed(() -> {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BundleKey.BUNDLE_AUTHENTICATION_FROM, Constants.AuthenticationType.FROM_REGISTRATION);
            navigateToPinLock(bundle);
        }, 1000);
    }


    @Override
    public void onBackPressed() {
        getCodeSnippet().hideKeyboard(EmailAckActivity.this);
        int result = iEmailAckActivityPresenter.isEmailVerified() ? RESULT_OK : RESULT_CANCELED;
        setResult(result);
        super.onBackPressed();
    }

    @Override
    public void setEmailText(String emailId) {
        /*String label = getString(R.string.txt_email_verification_label, emailId);
        ImageSpan imagespan = new ImageSpan(getActivity(), R.drawable.ic_view_edit);
        SpannableString labelSpanText = new SpannableString(label);
        labelSpanText.setSpan(imagespan, label.length() - 1, label.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);*/
        vTvAckText.setText(emailId);
    }

    @Override
    public void onClick(DrawablePosition target) {
        switch (target) {
            case RIGHT:
                navigateToEmailEntry();
                break;
        }
    }
}