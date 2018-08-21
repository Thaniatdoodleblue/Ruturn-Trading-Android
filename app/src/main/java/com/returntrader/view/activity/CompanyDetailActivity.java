package com.returntrader.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.CompanyDetailsAdapter;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.helper.TopBarHelper;
import com.returntrader.library.Log;
import com.returntrader.presenter.CompanyDetailPresenter;
import com.returntrader.presenter.ipresenter.ICompanyDetailPresenter;
import com.returntrader.view.fragment.HolidayDialogFragment;
import com.returntrader.view.fragment.QuestionDialogFragment;
import com.returntrader.view.fragment.TransactionCostBottomSheet;
import com.returntrader.view.iview.ICompanyDetailsView;

import java.util.ArrayList;
import java.util.List;

public class CompanyDetailActivity extends BaseActivity implements ICompanyDetailsView, View.OnClickListener {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private ImageView vImageLogo;
    private Button vBtnBuy;
    private ImageView vImageBrokeAgeAmount;
    private EditText vEtSharePrice;
    // private LinearLayout vLayoutTransactionAmount;
    private TextView vTvTotalAmount;
    private TextView vTvContractCode;
    private CheckBox vImageFavourite;
    private TextView vStockPriceIndicator;
    private TextView vLastKnownStockPrice;
    private ICompanyDetailPresenter iCompanyDetailPresenter;
    private List<Drawable> mTabDrawables;
    private TopBarHelper mTopBarHelper;
    private QuestionDialogFragment mQuestionDialogFragment;
    private String totalTransactionAmount = "0.00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtn_group_ltd);
        bindActivity();
        iCompanyDetailPresenter = new CompanyDetailPresenter(this);
        iCompanyDetailPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        mToolbarTitle = findViewById(R.id.tv_toolbar_title);

        vImageLogo = findViewById(R.id.detailCompanyLogo);
        mTabLayout = findViewById(R.id.tab_layout);

        vBtnBuy = findViewById(R.id.vbuyBtn);
        vImageBrokeAgeAmount = findViewById(R.id.brokrages);
        vEtSharePrice = findViewById(R.id.enterTheStockprice);
        //vLayoutTransactionAmount = findViewById(R.id.totaltransactionamount);
        vTvTotalAmount = findViewById(R.id.transAmt1);
        vTvContractCode = findViewById(R.id.tv_contractCode);
        vStockPriceIndicator = findViewById(R.id.layout_closed_price2);
        vLastKnownStockPrice = findViewById(R.id.mtnRate);
        vImageFavourite = findViewById(R.id.image_fav);
//        findViewById(R.id.layout_favourite).setOnClickListener(this);
        vImageFavourite.setOnClickListener(this);
        vBtnBuy.setOnClickListener(this);
        vImageBrokeAgeAmount.setOnClickListener(this);
        vEtSharePrice.addTextChangedListener(textWatcher);

        bindReceiver();
        prepareTabIconsIcons();
        setUpTabLayout();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    @Override
    public void showHolidayDialog() {
        HolidayDialogFragment mHolidayDialogFragment = new HolidayDialogFragment();
        mHolidayDialogFragment.show(getSupportFragmentManager(), "ShowHolidayDialog");
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
    private final BroadcastReceiver bReceiverUpdateHoldingInfo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(Constants.BroadCastKey.ACTION_DELAY_PRICE_UPDATE_COMPLETED)) {
                iCompanyDetailPresenter.doDelayPriceUpdate();
            }
        }
    };

    /***/
    private void unregisterReceiver() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mBalanceUpdateReceiver);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(bReceiverUpdateHoldingInfo);
    }

    /***/
    private final BroadcastReceiver mBalanceUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.BroadCastKey.ACTION_BALANCE_UPDATE_COMPLETED:
                    setUpTopBar();
                    break;
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        setUpTopBar();
    }

    private void prepareTabIconsIcons() {
        mTabDrawables = new ArrayList<>();
        mTabDrawables.add(ContextCompat.getDrawable(getActivity(), R.drawable.ic_menu_selector_graph));
        mTabDrawables.add(ContextCompat.getDrawable(getActivity(), R.drawable.ic_menu_selector_company));
        mTabDrawables.add(ContextCompat.getDrawable(getActivity(), R.drawable.ic_menu_selector_news));
        mTabDrawables.add(ContextCompat.getDrawable(getActivity(), R.drawable.ic_menu_selector_fundamental));
    }

    private void setUpTabLayout() {
        mTabLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getApplicationContext(), R.color.homePage));
        mTabLayout.setTabTextColors(ContextCompat.getColor(getApplicationContext(), R.color.grey), ContextCompat.getColor(getApplicationContext(), R.color.homePage));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mViewPager = findViewById(R.id.mtnPager);
        mTabLayout.addOnTabSelectedListener(onTabSelectedListener);
    }


    public void showTransactionCost() {
        getCodeSnippet().hideKeyboard(CompanyDetailActivity.this);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKey.BUNDLE_SHARE_AMOUNT, TextUtils.isEmpty(vEtSharePrice.getText().toString()) ? "0.0" : getCodeSnippet().doReplaceComma(vEtSharePrice.getText().toString()));
        TransactionCostBottomSheet transactionCostBottomSheet = new TransactionCostBottomSheet();
        transactionCostBottomSheet.setArguments(bundle);
        transactionCostBottomSheet.show(getSupportFragmentManager(), transactionCostBottomSheet.getTag());
    }


    @Override
    public void setViewPagerAdapter(CompanyDetailsAdapter adapter) {
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(4);
        setTabLayoutIcons();
    }

    private void setTabLayoutIcons() {
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            mTabLayout.getTabAt(i).setIcon(mTabDrawables.get(i));
        }
    }


    private String getCategoryLabel(CompanyItemInfo companyData) {
        if (companyData.getIsMain() == 1) {
            return getString(R.string.txt_main);
        } else if (companyData.getIsReit() == 1) {
            return getString(R.string.txt_reit);
        } else if (companyData.getIsAltx() == 1) {
            return getString(R.string.txt_altx);
        }
        return getString(R.string.txt_main);
    }

    @Override
    public void setCompanyData(CompanyItemInfo companyData, String companyDetailFrom) {
        mTopBarHelper = new TopBarHelper(this, getParentView());

        if (!(TextUtils.isEmpty(companyData.getInstrumentName()))) {
            mToolbarTitle.setText(companyData.getInstrumentName());
            mToolbarTitle.setSelected(true);
        }

        TraderApplication.getInstance().loadImage(companyData.getCompanyImageUrl(), vImageLogo);

        String code = companyData.getContractCode();
        if (!(TextUtils.isEmpty(code))) {
            mToolbarTitle.setText(companyData.getInstrumentName() + " - " + code.substring(code.lastIndexOf(".") + 1));
        }

        vImageFavourite.setText(getCategoryLabel(companyData));

        // float progress = TraderApplication.getInstance().getLatestPrice(companyData);
        float progress = companyData.getLastKnownDelayPrice();
        if (TraderApplication.getInstance().isHigh(progress)) {
            vStockPriceIndicator.setBackgroundResource(R.drawable.ic_background_rounded_corner_green);
            vStockPriceIndicator.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_white, 0, 0, 0);
        } else {
            vStockPriceIndicator.setBackgroundResource(R.drawable.ic_background_rounded_corner_red);
            vStockPriceIndicator.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_down_white, 0, 0, 0);
        }

        vLastKnownStockPrice.setText("R " + TraderApplication.getInstance().getValue(companyData.getLastPrice()));
        vStockPriceIndicator.setText(TraderApplication.getInstance().getValue(progress) + " %");

    }

    @Override
    public void setFavouriteStatus(boolean isFavourite) {
        vImageFavourite.setChecked(isFavourite);
    }

    @Override
    public void postOrderProgress(int resultOk) {
        if (mQuestionDialogFragment != null) {
            mQuestionDialogFragment.updateProgressStatus(resultOk);
        }
    }

    @Override
    public void doBalanceUpdate() {
        vEtSharePrice.getText().clear();
        syncAccount(Constants.AccountSync.SYNC_BALANCE);
    }

    @Override
    public void showQuestions() {
        mQuestionDialogFragment = new QuestionDialogFragment();
        mQuestionDialogFragment.show(getSupportFragmentManager(), "QuestionDialogFragment");
    }

    @Override
    public String getBuyAmount() {
        return vEtSharePrice.getText().toString();
    }

    @Override
    public void setUpTopBar() {
        if (mTopBarHelper != null) {
            mTopBarHelper.setUpView();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(CompanyDetailActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.brokrages:
                showTransactionCost();
                /*if (!(TextUtils.isEmpty(vEtSharePrice.getText().toString()))) {
                    showTransactionCost();
                } else {
                    showMessage("Enter Amount!");
                }*/
                break;
            case R.id.image_fav:
                CheckBox checkBox = (CheckBox) view;
                iCompanyDetailPresenter.onClickFavourite(checkBox.isChecked());
                break;
            case R.id.vbuyBtn:
                doBuy();
                break;

        }
    }

    @Override
    public void doBuy() {
        if (iCompanyDetailPresenter.hasTransactionAccess()) {
            if (iCompanyDetailPresenter.isCompanyAvailable()) {
                float maxTradeAmnt = Float.valueOf(iCompanyDetailPresenter.getMaxTradeAmount());// / 100f;
                if (vEtSharePrice.length() > 0) {
                    if (getCodeSnippet().isGreaterZero(iCompanyDetailPresenter.getLastKnownBalance())) {
                        if (getCodeSnippet().isGreaterThanBalance(iCompanyDetailPresenter.getLastKnownBalance(), vEtSharePrice.getText().toString())
                                /*this is additional check */ && getCodeSnippet().isGreaterThanBalance(iCompanyDetailPresenter.getLastKnownBalance(), totalTransactionAmount)) {
                            if (getCodeSnippet().isGreaterThanMaxTrade(vEtSharePrice.getText().toString(), maxTradeAmnt)) {
                                showMessage(getString(R.string.txt_error_maxtrade) + " R " + getCodeSnippet().formatStringTo2DecimalWithoutRound(maxTradeAmnt));
                            } else {
                                if (getCodeSnippet().isAmountLesser(vEtSharePrice.getText().toString())) {
                                    showBuySuggestionDialog();
                                } else {
                                    /*14/8/16
                                     * client asked to hide this conditions,
                                     * he asked us to do the process even if the market is closed also.
                                     * For that purpose dev has been hide this conditions.
                                     * 21/8/16
                                     * Again he asked to show the validation
                                     * */
                                     if (iCompanyDetailPresenter.isMarketAvailable()) {
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
                }
            } else {
                showHolidayDialog();
            }
        } else {
            showMessage(getString(R.string.txt_doc_pending));
        }
    }

    /***/
    private void navigateToAskAuthentication() {
        iCompanyDetailPresenter.setAuthNeeded();
        Intent intent = new Intent(getActivity(), AskAuthenticationActivity.class);
        startActivityForResult(intent, Constants.RequestCodes.COMPANY_DETAILS_BUY_TO_AUTHENTICATE);
    }


    private TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };


    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                vBtnBuy.setEnabled(false);
                vBtnBuy.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.rounded_corner));
            } else {
                vBtnBuy.setEnabled(true);
                vBtnBuy.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_background_rounded_corner_green));
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence price, int start,
                                  int before, int count) {
            if (price.length() == 0) {
                // vLayoutTransactionAmount.setVisibility(View.GONE);
//                vImageBrokeAgeAmount.setVisibility(View.GONE);
                vTvTotalAmount.setText("Total Transaction Amount: R 0.00");
                totalTransactionAmount = "0.00";
            } else {
                vTvTotalAmount.setVisibility(View.VISIBLE);
//                vImageBrokeAgeAmount.setVisibility(View.VISIBLE);
                //vLayoutTransactionAmount.setVisibility(View.VISIBLE);

                totalTransactionAmount = TraderApplication.getInstance().getTotalAmount(price.toString(), Constants.TransactionType.TRANSACTION_BUY);

                Log.d(TAG, "onTextChanged: totalTransactionAmount" + totalTransactionAmount);

                vTvTotalAmount.setText("Total Transaction Amount: R" + " " +
                        TraderApplication.getInstance().getTotalAmount(price.toString(), Constants.TransactionType.TRANSACTION_BUY) + " ");
            }
        }
    };


    private void showBuySuggestionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CompanyDetailActivity.this);
//        builder.setTitle(R.string.alert_title_suggestions);
        builder.setMessage(R.string.txt_suggestion_low_amount);
        builder.setPositiveButton(R.string.txt_procced, (dialog, which) -> {
            getCodeSnippet().hideKeyboard(CompanyDetailActivity.this);
            dialog.dismiss();
            /*client asked to hide this conditions,
             * he asked us to do the process even if the market is closed also.
             * For that purpose dev has been hide this conditions.
             * */
            //  if (iCompanyDetailPresenter.isMarketAvailable()) {
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
    public void onBackPressed() {
        getCodeSnippet().hideKeyboard(CompanyDetailActivity.this);
        Intent intent = new Intent();
        intent.putExtras(iCompanyDetailPresenter.getUpdatedBundle());
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iCompanyDetailPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }
}
