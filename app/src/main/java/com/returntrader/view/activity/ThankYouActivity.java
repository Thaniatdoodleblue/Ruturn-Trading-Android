package com.returntrader.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.presenter.SyncPresenter;
import com.returntrader.presenter.ipresenter.ISyncPresenter;
import com.returntrader.sync.DelayPriceSyncService;
import com.returntrader.view.iview.ISyncView;

public class ThankYouActivity extends BaseActivity implements View.OnClickListener, ISyncView {


    private ProgressBar mProgressBar;
    private TextView mTvNext;
    private ISyncPresenter iSyncPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_thank_you);
        bindActivity();
        iSyncPresenter = new SyncPresenter(this);
        iSyncPresenter.onCreatePresenter(getIntent().getExtras());
    }


    private void bindActivity() {
        mProgressBar =  findViewById(R.id.progressBar);
        mTvNext =  findViewById(R.id.tv_next);
        mProgressBar.setVisibility(View.VISIBLE);
        mTvNext.setVisibility(View.VISIBLE);
        startTimer();
    }


    private void startTimer() {
        Handler mTimeOutHandler = new Handler();
        mTimeOutHandler.postDelayed(() -> navigateToHome(null), 1000);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                iSyncPresenter.setPartialRegisterCompleted();
                navigateToHome(null);
                break;
        }
    }


    @Override
    public void showProgressbar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressbar() {
        mProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void navigateToHome(Bundle bundle) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void setNextButtonVisibility() {
        boolean isDataDownloaded = iSyncPresenter.isDataDownloaded();
        if (isDataDownloaded) {
            dismissProgressbar();
            mTvNext.setVisibility(View.VISIBLE);
        } else {
            showProgressbar();
            mTvNext.setVisibility(View.GONE);
        }
    }

    @Override
    public void startDelayPriceUpdateService() {
        Intent updatePriceService = new Intent(getActivity(), DelayPriceSyncService.class);
        startService(updatePriceService);
    }
}
