package com.returntrader.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.helper.TopBarHelper;
import com.returntrader.library.Log;
import com.returntrader.model.common.SellNetAmount;
import com.returntrader.model.common.StockHold;
import com.returntrader.presenter.SellStockPresenter;
import com.returntrader.presenter.ipresenter.ISellStockPresenter;
import com.returntrader.view.fragment.QuestionDialogFragment;
import com.returntrader.view.fragment.TransactionCostBottomSheet;
import com.returntrader.view.iview.ISellStockView;

import java.math.BigDecimal;

public class SellStockActivity extends BaseActivity implements ISellStockView, View.OnClickListener {


    private TextInputEditText mEtRandValue;
    private TextInputEditText mEtPercentageValue;
    private TextView vTvCurrentSharePrice;
    private TextView vTvHint;
    private TextView vTvGrossAmount;
    private TextView vTvInvestmentCost;
    private TextView vTvNetAmount;
    private TextView vTvTransactionCostHint;
    private ImageView vImageCompanyIcon;
    private ISellStockPresenter iSellStockPresenter;
    private TopBarHelper mTopBarHelper;
    private QuestionDialogFragment mQuestionDialogFragment;

    private String mNetValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_stock);
        bindActivity();
        iSellStockPresenter = new SellStockPresenter(this);
        iSellStockPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        mTopBarHelper = new TopBarHelper(SellStockActivity.this, getParentView());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vTvCurrentSharePrice = findViewById(R.id.tv_current_share_price);
        vTvTransactionCostHint = findViewById(R.id.tv_share_purchased);
        mEtRandValue = findViewById(R.id.et_amount_to_sell);
        mEtPercentageValue = findViewById(R.id.et_value_percentage);
        vTvHint = findViewById(R.id.tv_company_hint);
        vTvGrossAmount = findViewById(R.id.tv_gross_amount_estimated);
        vTvInvestmentCost = findViewById(R.id.tv_invested_cost);
        vTvNetAmount = findViewById(R.id.tv_net_amount);
        vImageCompanyIcon = findViewById(R.id.image_company_icon);


        mEtRandValue.addTextChangedListener(percentageWatcher);
        mEtPercentageValue.addTextChangedListener(percentageWatcher);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        vTvTransactionCostHint.setOnClickListener(this);

        bindReceiver();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(SellStockActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /***/
    private void showMinusNetAmountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(R.string.alert_title_suggestions);
        builder.setMessage(R.string.txt_alert_minus_netamount);
        builder.setPositiveButton(R.string.txt_confirmbtn, (dialog, which) -> {
            dialog.dismiss();
            if (getCodeSnippet().isGreaterThanBalance(iSellStockPresenter.getLastKnownBalance(), mNetValue)) {
                /*14/8/16
                 * client asked to hide this conditions,
                 * he asked us to do the process even if the market is closed also.
                 * For that purpose dev has been hide this conditions.
                 * 21/8/16
                 * Again he asked to show the validation
                 * */
                 if (iSellStockPresenter.isMarketAvailable()) {
                navigateToAskAuthentication();
                   } else {
                      showMarketAvailabilityDialog(Constants.RequestCodes.COMPANY_DETAILS_BUY_TO_AUTHENTICATE);
                   }
            } else {
                showMessage(getString(R.string.txt_error_balance_low));

            }


        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                getCodeSnippet().hideKeyboard(this);
                if (mEtRandValue.length() > 0) {
                    if (validate()) {
                        /*this condition is for check vTvNetAmount is greater then zero
                         * if it's zero then zero direct to navigateToAskAuthentication
                         * else show  showMinusNetAmountDialog*/
                        if (getCodeSnippet().isGreaterZero(mNetValue.trim())) {
                            /*14/8/16
                             * client asked to hide this conditions,
                             * he asked us to do the process even if the market is closed also.
                             * For that purpose dev has been hide this conditions.
                             * 21/8/16
                             * Again he asked to show the validation
                             * */
                            if (iSellStockPresenter.isMarketAvailable()) {
                            navigateToAskAuthentication();
                             } else {
                                 showMarketAvailabilityDialog(Constants.RequestCodes.COMPANY_DETAILS_BUY_TO_AUTHENTICATE);
                            }
                        } else {
                            showMinusNetAmountDialog();
                        }
                    }
                } else {
                    showMessage("Enter valid amount to sell");
                }
                break;
            case R.id.tv_share_purchased:
                showTransactionCost();
                /*if (!(TextUtils.isEmpty(mEtRandValue.getText().toString()))) {
                    showTransactionCost();
                } else {
                    showMessage("Enter Amount!");
                }*/
                break;
        }
    }

    public void showTransactionCost() {
        getCodeSnippet().hideKeyboard(SellStockActivity.this);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKey.BUNDLE_SHARE_AMOUNT, TextUtils.isEmpty(mEtRandValue.getText().toString()) ? "0.0" : getCodeSnippet().doReplaceComma(mEtRandValue.getText().toString()));
        bundle.putInt(Constants.BundleKey.BUNDLE_TRANSACTION_COST_FROM, Constants.TransactionType.TRANSACTION_SELL);
        TransactionCostBottomSheet transactionCostBottomSheet = new TransactionCostBottomSheet();
        transactionCostBottomSheet.setArguments(bundle);
        transactionCostBottomSheet.show(getSupportFragmentManager(), transactionCostBottomSheet.getTag());
    }

    /***/
    private boolean validate() {
        try {
            BigDecimal bigDecimal = new BigDecimal(TextUtils.isEmpty(mEtRandValue.getText()) ? "0.0" : getCodeSnippet().doReplaceComma(mEtRandValue.getText().toString()));
            if (bigDecimal.compareTo(new BigDecimal(Constants.Common.DEFAULT_PRICE_ZERO)) == 1) {
                if (bigDecimal.compareTo(iSellStockPresenter.getCurrentAvailableSell()) <= 0) {
                    return true;
                } else {
                    showMessage("Enter valid amount or percentage to sell");
                }
            } else {
                showMessage("Enter valid amount or percentage to sell");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void postOrderProgress(int resultOk) {
        if (mQuestionDialogFragment != null) {
            mQuestionDialogFragment.updateProgressStatus(resultOk);
        }
    }


    @Override
    public void showQuestions() {
        mQuestionDialogFragment = new QuestionDialogFragment();
        mQuestionDialogFragment.show(getSupportFragmentManager(), "QuestionDialogFragment");
    }

    @Override
    public String getSellPercentage() {
        return mEtPercentageValue.getText().toString();
    }

    @Override
    public void setUpTopBar() {
        if (mTopBarHelper != null) {
            mTopBarHelper.setUpView();
        }
    }

    @Override
    public void doBalanceUpdate() {
        syncAccount(Constants.AccountSync.SYNC_BALANCE);
        //setResult(Activity.RESULT_OK);
        //finish();
    }

    @Override
    public void setPercentageValue(String percentageValue) {
        // mEtPercentageValue.setText(getCodeSnippet().getDecimalPoint(percentageValue));
        /*client asked to round off to one decimal, above method is without round off value*/
        mEtPercentageValue.setText(getCodeSnippet().getDecimalPointWithOneRoundOff(percentageValue));
    }

    @Override
    public void setRandValue(String randValue) {
        mEtRandValue.setText(getCodeSnippet().getDecimalPoint(randValue));
    }


    private TextWatcher percentageWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (mEtPercentageValue.getText().hashCode() == editable.hashCode() && mEtPercentageValue.isFocused()) {
                iSellStockPresenter.calculateRand(mEtPercentageValue.getText().toString().trim());
            } else if (mEtRandValue.getText().hashCode() == editable.hashCode() && mEtRandValue.isFocused()) {
                String value = mEtRandValue.getText().toString().trim();
                iSellStockPresenter.calculatePercentage(value);
            }

        }
    };

    /***/
    private void navigateToAskAuthentication() {
        iSellStockPresenter.setAuthNeeded();
        Intent intent = new Intent(getActivity(), AskAuthenticationActivity.class);
        startActivityForResult(intent, Constants.RequestCodes.COMPANY_DETAILS_BUY_TO_AUTHENTICATE);
    }

    @Override
    public void setNetAmount(SellNetAmount netAmount) {
        Log.d(TAG, "setNetAmount: ");
        mNetValue = netAmount.getNetAmount();
        vTvGrossAmount.setText(getString(R.string.price_text_rand_string, getCodeSnippet().getDecimalPoint(netAmount.getGrossAmount())));
        vTvNetAmount.setText(getCodeSnippet().getDecimalPointWithMinusSell(Float.valueOf(netAmount.getNetAmount())));
        vTvInvestmentCost.setText("- (" + getCodeSnippet().getDecimalPoint(netAmount.getInvestmentCost()) + ")");
        vTvNetAmount.setTextColor(ContextCompat.getColor(this, vTvNetAmount.getText().toString().contains("(") ? R.color.mtnRed : R.color.colorTextBlue));
    }

    @Override
    public void setStockDetails(StockHold currentStock) {
        vTvCurrentSharePrice.setText(getString(R.string.price_text_rand_string, getCodeSnippet().getDecimalPoint(currentStock.getCurrentHolding())));
        vTvHint.setText(currentStock.getShareCode());
        TraderApplication.getInstance().loadImage(currentStock.getCompanyLogo(), vImageCompanyIcon);
        mEtRandValue.setText(getCodeSnippet().getDecimalPoint(currentStock.getCurrentHolding()));
        mEtPercentageValue.setText("100");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getCodeSnippet().hideKeyboard(SellStockActivity.this);
        iSellStockPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }

    private void bindReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constants.BroadCastKey.ACTION_BALANCE_UPDATE_COMPLETED);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mBalanceUpdateReceiver, intentFilter);

        IntentFilter holdingUpdateIntentFilter = new IntentFilter(Constants.BroadCastKey.ACTION_DELAY_PRICE_UPDATE_COMPLETED);
        holdingUpdateIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(bReceiverUpdateHoldingInfo, holdingUpdateIntentFilter);
    }

    private void unregisterReceiver() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mBalanceUpdateReceiver);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(bReceiverUpdateHoldingInfo);
    }

    /***/
    private final BroadcastReceiver bReceiverUpdateHoldingInfo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(Constants.BroadCastKey.ACTION_DELAY_PRICE_UPDATE_COMPLETED)) {
                iSellStockPresenter.doDelayPriceUpdate();
            }
        }
    };

    /***/
    private final BroadcastReceiver mBalanceUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "Balance onReceive");
            switch (intent.getAction()) {
                case Constants.BroadCastKey.ACTION_BALANCE_UPDATE_COMPLETED:
                    setUpTopBar();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    @Override
    public void onBackPressed() {
        getCodeSnippet().hideKeyboard(SellStockActivity.this);
        super.onBackPressed();
    }
}
