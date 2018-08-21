package com.returntrader.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.helper.TopBarHelper;
import com.returntrader.library.Log;
import com.returntrader.model.common.StockHold;
import com.returntrader.presenter.BuyStockPresenter;
import com.returntrader.presenter.ipresenter.IBuyStockPresenter;
import com.returntrader.view.fragment.QuestionDialogFragment;
import com.returntrader.view.fragment.TransactionCostBottomSheet;
import com.returntrader.view.iview.IBuyStockView;

import java.math.BigDecimal;

public class BuyStockActivity extends BaseActivity implements View.OnClickListener, IBuyStockView {


    private TextView vTvCurrentSharePrice;
    private TextView vTvShareHolds;
    private TextView vTvRandValueHolding;
    private TextView vTvHint;
    private ImageView vImageCompanyIcon;
    private EditText vEtStockPrice;

    private TextView vTransactionCostLablel;
    private TextView vTransactionCostPrice;
    private TextView vTotalAmount;
    private TopBarHelper mTopBarHelper;
    private QuestionDialogFragment mQuestionDialogFragment;
    private IBuyStockPresenter iBuyStockPresenter;
    private String totalTransactionAmount = "0.00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_stock);
        bindActivity();
        iBuyStockPresenter = new BuyStockPresenter(this);
        iBuyStockPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        mTopBarHelper = new TopBarHelper(BuyStockActivity.this, getParentView());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText("Buy");
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vImageCompanyIcon = findViewById(R.id.image_company_icon);
        vEtStockPrice = findViewById(R.id.et_stock_price);
        vTvHint = findViewById(R.id.tv_company_hint);
        vTvCurrentSharePrice = findViewById(R.id.tv_current_share_price);
        vTvShareHolds = findViewById(R.id.tv_share_holding);
        vTvRandValueHolding = findViewById(R.id.tv_rand_value_holding);
        vTransactionCostLablel = findViewById(R.id.tv_transaction_cost_label);
        vTransactionCostPrice = findViewById(R.id.tv_transaction_cost);
        vTotalAmount = findViewById(R.id.tv_total_cost);


        vEtStockPrice.addTextChangedListener(textWatcher);


        vTransactionCostLablel.setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);

        bindReceiver();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                getCodeSnippet().hideKeyboard(this);
                if (iBuyStockPresenter.hasTransactionAccess()) {
                    float maxTradeAmnt = Float.valueOf(TextUtils.isEmpty(iBuyStockPresenter.getCurrentStock().getMaxTradeAmount()) ? "0.0" : getCodeSnippet().doReplaceComma(iBuyStockPresenter.getCurrentStock().getMaxTradeAmount())) / 100f;
                    if (!TextUtils.isEmpty(vEtStockPrice.getText())) {
                        if (getCodeSnippet().isGreaterZero(iBuyStockPresenter.getLastKnownBalance())) {
                            if (getCodeSnippet().isGreaterThanBalance(iBuyStockPresenter.getLastKnownBalance(), vEtStockPrice.getText().toString())
                                    /*this is additional check */ && getCodeSnippet().isGreaterThanBalance(iBuyStockPresenter.getLastKnownBalance(), totalTransactionAmount)) {
                                if (getCodeSnippet().isGreaterThanMaxTrade(vEtStockPrice.getText().toString(), maxTradeAmnt)) {
                                    showMessage(getString(R.string.txt_error_maxtrade) + " R " + getCodeSnippet().formatStringTo2DecimalWithoutRound(maxTradeAmnt));
                                } else {
                                    if (getCodeSnippet().isAmountLesser(vEtStockPrice.getText().toString())) {
                                        showBuySuggestionDialog();
                                    } else {
                                        /*14/8/16
                                         * client asked to hide this conditions,
                                         * he asked us to do the process even if the market is closed also.
                                         * For that purpose dev has been hide this conditions.
                                         * 21/8/16
                                         * Again he asked to show the validation
                                         * */
                                          if (iBuyStockPresenter.isMarketAvailable()) {
                                        navigateToAskAuthentication();
                                         } else {
                                             showMarketAvailabilityDialog(Constants.RequestCodes.COMPANY_DETAILS_BUY_TO_AUTHENTICATE);
                                         }
                                    }
                                }
                            } else {
                                showMessage(getString(R.string.txt_error_balance_low));
                            }
                        } else {
                            showMessage(getString(R.string.txt_error_balance_low));
                        }
                    } else {
                        showMessage(getString(R.string.txt_error_buyenteramount));
                    }

                    /*if (vEtStockPrice.length() > 0) {
                        if (getCodeSnippet().isGreaterZero(iBuyStockPresenter.getLastKnownBalance())) {
                            if (getCodeSnippet().isGreaterThanBalance(iBuyStockPresenter.getLastKnownBalance(), vEtStockPrice.getText().toString())) {
                                if (getCodeSnippet().isAmountLesser(vEtStockPrice.getText().toString())) {
                                    showBuySuggestionDialog();
                                } else {
                                    if (iBuyStockPresenter.isMarketAvailable()) {
                                        navigateToAskAuthentication();
                                    } else {
                                        showMarketAvailabilityDialog(Constants.RequestCodes.COMPANY_DETAILS_BUY_TO_AUTHENTICATE);
                                    }
                                }
                            } else {
                                showMessage(getString(R.string.txt_error_balance_low));
                            }
                        } else {
                            showMessage(getString(R.string.txt_error_balance_low));
                        }
                    }*/
                } else {
                    showMessage(getString(R.string.txt_doc_pending));
                }
                break;
            case R.id.tv_transaction_cost_label:
                showTransactionCost();
               /* if (!(TextUtils.isEmpty(vEtStockPrice.getText().toString()))) {
                    showTransactionCost();
                } else {
                    showMessage("Enter Amount!");
                }*/
                break;
            default:
                break;
        }
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (vEtStockPrice.length() > 0) {
                iBuyStockPresenter.calculateTransactionCost(vEtStockPrice.getText().toString().trim());
            }
        }
    };

    private void navigateToAskAuthentication() {
        iBuyStockPresenter.setAuthNeeded();
        Intent intent = new Intent(getActivity(), AskAuthenticationActivity.class);
        startActivityForResult(intent, Constants.RequestCodes.COMPANY_DETAILS_BUY_TO_AUTHENTICATE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(BuyStockActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void postOrderProgress(int resultOk) {
        if (mQuestionDialogFragment != null) {
            mQuestionDialogFragment.updateProgressStatus(resultOk);
        }
    }

    @Override
    public void setUpTopBar() {
        if (mTopBarHelper != null) {
            mTopBarHelper.setUpView();
        }
    }


    @Override
    public void showQuestions() {
        mQuestionDialogFragment = new QuestionDialogFragment();
        mQuestionDialogFragment.show(getSupportFragmentManager(), "QuestionDialogFragment");
    }

    @Override
    public void setStockDetails(StockHold stockDetails) {
        vTvCurrentSharePrice.setText(getString(R.string.price_text_rand_string, getCodeSnippet().formatStringTo2DecimalWithoutRound(stockDetails.getClosingPrice())));
        vTvShareHolds.setText(getCodeSnippet().formatStringTo2DecimalWithoutRound(stockDetails.getShares()));
        BigDecimal randHolding = new BigDecimal(TraderApplication.getInstance().getTotalHoldingByPrice(stockDetails.getShares(), String.valueOf(stockDetails.getClosingPrice())));
        if (randHolding != null) {
            vTvRandValueHolding.setText(getString(R.string.price_text_rand_string, getCodeSnippet().formatStringTo2DecimalWithoutRound("" + randHolding)));
        }

        vTvHint.setText(stockDetails.getShareCode());
        TraderApplication.getInstance().loadImage(stockDetails.getCompanyLogo(), vImageCompanyIcon);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getCodeSnippet().hideKeyboard(BuyStockActivity.this);
        iBuyStockPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }


    public void showTransactionCost() {
        getCodeSnippet().hideKeyboard(BuyStockActivity.this);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKey.BUNDLE_SHARE_AMOUNT, TextUtils.isEmpty(vEtStockPrice.getText().toString()) ? "0.0" : getCodeSnippet().doReplaceComma(vEtStockPrice.getText().toString()));
        TransactionCostBottomSheet transactionCostBottomSheet = new TransactionCostBottomSheet();
        transactionCostBottomSheet.setArguments(bundle);
        transactionCostBottomSheet.show(getSupportFragmentManager(), transactionCostBottomSheet.getTag());
    }

    private void showBuySuggestionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BuyStockActivity.this);
//        builder.setTitle(R.string.alert_title_suggestions);
        builder.setMessage(R.string.txt_suggestion_low_amount);
        builder.setPositiveButton(R.string.txt_procced, (dialog, which) -> {
            dialog.dismiss();
            /*client asked to hide this conditions,
             * he asked us to do the process even if the market is closed also.
             * For that purpose dev has been hide this conditions.
             * */
          //  if (iBuyStockPresenter.isMarketAvailable()) {
                navigateToAskAuthentication();
          //  } else {
          //      showMarketAvailabilityDialog(Constants.RequestCodes.COMPANY_DETAILS_BUY_TO_AUTHENTICATE);
          //  }
        });
        builder.setNegativeButton(R.string.txt_alert_edit, (dialog, which) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void setTotalTransactionCost(String totalTransactionCost, String totalSharePrice) {
        Log.d(TAG, "setTotalTransactionCost: before" +totalTransactionAmount);
        totalTransactionAmount = getCodeSnippet().formatStringTo2DecimalWithoutRound(totalSharePrice);
        Log.d(TAG, "setTotalTransactionCost: after" +totalTransactionAmount);
        vTotalAmount.setText(getString(R.string.price_text_rand_string, getCodeSnippet().formatStringTo2DecimalWithoutRound(totalSharePrice)));
        vTransactionCostPrice.setText(getString(R.string.price_text_rand_string, getCodeSnippet().formatStringTo2DecimalWithoutRound(totalTransactionCost)));
    }

    @Override
    public void doUpdateBalance() {
        vEtStockPrice.getText().clear();
        syncAccount(Constants.AccountSync.SYNC_BALANCE);
//        setResult(Activity.RESULT_OK);
        //finish();
    }


    @Override
    public void onBackPressed() {
        getCodeSnippet().hideKeyboard(BuyStockActivity.this);
        super.onBackPressed();
    }


    /***/
    private void bindReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constants.BroadCastKey.ACTION_BALANCE_UPDATE_COMPLETED);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mBalanceUpdateReceiver, intentFilter);

        IntentFilter holdingUpdateIntentFilter = new IntentFilter(Constants.BroadCastKey.ACTION_DELAY_PRICE_UPDATE_COMPLETED);
        holdingUpdateIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(bReceiverUpdateHoldingInfo, holdingUpdateIntentFilter);
    }

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

    /***/
    private final BroadcastReceiver bReceiverUpdateHoldingInfo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(Constants.BroadCastKey.ACTION_DELAY_PRICE_UPDATE_COMPLETED)) {
                iBuyStockPresenter.doDelayPriceUpdate();
            }
        }
    };

    /***/
    private void unregisterReceiver() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mBalanceUpdateReceiver);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(bReceiverUpdateHoldingInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }
}
