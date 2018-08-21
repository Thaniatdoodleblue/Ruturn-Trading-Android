package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.presenter.SyncPresenter;
import com.returntrader.presenter.ipresenter.ISyncPresenter;
import com.returntrader.view.iview.ISyncView;

public class SyncActivity extends BaseActivity implements View.OnClickListener, ISyncView {


    private ProgressBar mProgressBar;
    private TextView mTvMessage;
    private ISyncPresenter iSyncPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sync_data);
        bindActivity();
        iSyncPresenter = new SyncPresenter(this);
        iSyncPresenter.onCreatePresenter(getIntent().getExtras());

    }

    private void bindActivity() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTvMessage = (TextView) findViewById(R.id.tv_progress_bar_message);
        mTvMessage.setTextColor(ContextCompat.getColor(this,R.color.homePage));
        mProgressBar.setVisibility(View.VISIBLE);
       // mTvMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                //navigateToEmailEntry();
                break;
        }
    }

    @Override
    public void showProgressbar() {
        //super.showProgressbar();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressbar() {
        //super.dismissProgressbar();

        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void navigateToHome(Bundle bundle) {
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void setNextButtonVisibility() {

    }

    @Override
    public void startDelayPriceUpdateService() {

    }

    /*@Override
    public void showStatusMessage(String message) {
        mTvMessage.setText(message);
    }*/
}
