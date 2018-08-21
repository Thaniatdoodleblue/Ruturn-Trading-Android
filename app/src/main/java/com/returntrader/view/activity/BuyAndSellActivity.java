package com.returntrader.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.helper.TopBarHelper;
import com.returntrader.model.common.LineGraphDataSet;
import com.returntrader.model.common.StockHold;
import com.returntrader.presenter.BuyAndSellPresenter;
import com.returntrader.presenter.ipresenter.IBuyAndSellPresenter;
import com.returntrader.view.fragment.HolidayDialogFragment;
import com.returntrader.view.iview.IBuyAndSellView;
import com.returntrader.view.widget.CustomXAxisRenderer;
import com.returntrader.view.widget.CustomYRenderer;
import com.returntrader.view.widget.MyYAxisValueFormatter;
import com.returntrader.view.widget.ThreeYearAxisValueFormatter;
import com.returntrader.view.widget.XAxisValueFormatterForDay;
import com.returntrader.view.widget.YearAxisValueFormatter;

public class BuyAndSellActivity extends BaseActivity implements IBuyAndSellView, View.OnClickListener {
    private Button vBtnSell;
    private Button vBtnBuy;
    private TextView vTvCurrentSharePrice;
    private TextView vTvSharePurchased;
    private TextView vTvCompanyHint;
    private ImageView vImageCompanyLogo;
    private TextView vTvPurchasedShare;
    private TextView vTvProfit;
    private TextView vTvProfitLossPerShare;
    private TextView vTvProfitLossValue;
    private TextView vTvCurrentShare;
    private TextView vTvTotalHoldingAmount;
    private TextView tvBack;
    //    private Toolbar mToolBar;
    private Spinner vSpinnerSort;
    private LineChart mLineChart;
    private TextView vTvHighLightedData;
    private IBuyAndSellPresenter iBuyAndSellPresenter;
    private TopBarHelper mTopBarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_share_price);
        bindActivity();
        iBuyAndSellPresenter = new BuyAndSellPresenter(this);
        iBuyAndSellPresenter.onCreatePresenter(getIntent().getExtras());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSellButton:
                iBuyAndSellPresenter.onClickSell();
                break;
            case R.id.btBuyButton:
                iBuyAndSellPresenter.onClickBuy();
                break;
            case R.id.tvBack:
                getCodeSnippet().hideKeyboard(BuyAndSellActivity.this);
                onBackPressed();
                break;
        }
    }

    @Override
    public void showHolidayDialog() {
        HolidayDialogFragment mHolidayDialogFragment = new HolidayDialogFragment();
        mHolidayDialogFragment.show(getSupportFragmentManager(), "ShowHolidayDialog");
    }


    @Override
    public void navigateToBuy(Bundle bundle) {
        Intent intent = new Intent(getApplicationContext(), BuyStockActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constants.RequestCodes.NAVIGATE_TO_BUY);
    }

    @Override
    public void navigateToSell(Bundle bundle) {
        Intent intent = new Intent(getApplicationContext(), SellStockActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constants.RequestCodes.NAVIGATE_TO_SELL);
    }


    /***/
    private void bindActivity() {
        mTopBarHelper = new TopBarHelper(this, getParentView());
//        mToolBar = findViewById(R.id.toolbar);
//        setSupportActionBar(mToolBar);
//        setTitle(R.string.txt_empty_string);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vBtnSell = findViewById(R.id.btSellButton);
        vBtnBuy = findViewById(R.id.btBuyButton);
        vTvCompanyHint = findViewById(R.id.tv_company_hint);
        vImageCompanyLogo = findViewById(R.id.image_company_icon);
        vTvCurrentSharePrice = findViewById(R.id.tv_invested_amount);
        vTvSharePurchased = findViewById(R.id.tv_share_purchased);
        vTvPurchasedShare = findViewById(R.id.tv_purchased_share);
        vTvCurrentShare = findViewById(R.id.tv_current_share);
        vTvProfit = findViewById(R.id.tv_profit);
        vTvProfitLossPerShare = findViewById(R.id.tv_profile_loss_per_share);
        vTvProfitLossValue = findViewById(R.id.tv_profit_loss_value);
        vTvTotalHoldingAmount = findViewById(R.id.tv_total_holding);
        tvBack = findViewById(R.id.tvBack);

        vTvHighLightedData = findViewById(R.id.tv_graph_data);
        mLineChart = findViewById(R.id.tv_line_chart);
        vSpinnerSort = findViewById(R.id.sortSpin);
        vSpinnerSort.setSelection(0);

        vSpinnerSort.setOnItemSelectedListener(onItemSelectedListener);
        vBtnSell.setOnClickListener(this);
        vBtnBuy.setOnClickListener(this);
        tvBack.setOnClickListener(this);
        mLineChart.setOnChartValueSelectedListener(onChartValueSelectedListener);

        mLineChart.getLegend().setEnabled(false);
        mLineChart.setPinchZoom(false);
        mLineChart.setDragEnabled(false);
        mLineChart.setScaleEnabled(false);
        mLineChart.setHighlightPerDragEnabled(true);
        mLineChart.getDescription().setEnabled(false);
        // mLineChart.setViewPortOffsets(40, 0, 10, 10);
        mLineChart.setViewPortOffsets(60, 28, 60, 68);
        mLineChart.setNoDataText(getString(R.string.txt_loading));
        mLineChart.setExtraBottomOffset(30f);
        bindReceiver();
    }


    /***/
    private OnChartValueSelectedListener onChartValueSelectedListener = new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {
            vTvHighLightedData.setText(getCodeSnippet().getDecimalPoint(e.getY()));
        }

        @Override
        public void onNothingSelected() {

        }
    };

    /***/
    private void clearHighLightText() {
        vTvHighLightedData.setText(R.string.txt_empty_string);
    }

    /***/
    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            clearHighLightText();
            switch (position) {
                case 0:
                    iBuyAndSellPresenter.onLoad(Constants.GraphFilter.FILTER_TYPE_TODAY);
                    break;
                case 1:
                    iBuyAndSellPresenter.onLoad(Constants.GraphFilter.FILTER_TYPE_LAST_WEEK);
                    break;
                case 2:
                    iBuyAndSellPresenter.onLoad(Constants.GraphFilter.FILTER_TYPE_LAST_MONTH);
                    break;
                case 3:
                    iBuyAndSellPresenter.onLoad(Constants.GraphFilter.FILTER_TYPE_ONE_YEAR);
                    break;
                case 4:
                    iBuyAndSellPresenter.onLoad(Constants.GraphFilter.FILTER_TYPE_THREE_YEARS);
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    @Override
    public void setGraphView(LineGraphDataSet lineGraphDataSet) {
        /*if (lineGraphDataSet.getLineData() == null) {
            mLineChart.clear();
        } else {
            YAxis y = mLineChart.getAxisLeft();
            y.setDrawGridLines(false);
            y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            y.setValueFormatter(new MyYAxisValueFormatter());
            y.setLabelCount(5, true);
            y.setDrawAxisLine(false);
            mLineChart.setRendererLeftYAxis(new CustomYRenderer(mLineChart.getViewPortHandler(),
                    mLineChart.getAxisLeft(), mLineChart.getTransformer(YAxis.AxisDependency.LEFT)));
            mLineChart.getAxisRight().setEnabled(false);
            mLineChart.clear();
            mLineChart.setData(lineGraphDataSet.getLineData());
            XAxis x = mLineChart.getXAxis();
            x.setValueFormatter(new IndexAxisValueFormatter((lineGraphDataSet.getLabelList())));
            CustomXAxisRenderer customXAxisRenderer = new CustomXAxisRenderer(mLineChart.getViewPortHandler(),
                    mLineChart.getXAxis(), mLineChart.getTransformer(YAxis.AxisDependency.LEFT), lineGraphDataSet.getLabelList(), lineGraphDataSet.getFilterType());
            switch (lineGraphDataSet.getFilterType()) {
                case Constants.GraphFilter.FILTER_TYPE_TODAY:
                    if (lineGraphDataSet.getLabelList().size() > 1) {
                        mLineChart.setXAxisRenderer(customXAxisRenderer);
                        x.setValueFormatter(new XAxisValueFormatterForDay(lineGraphDataSet.getLabelList()));
                    } else {
                        x.setValueFormatter(new IndexAxisValueFormatter(lineGraphDataSet.getLabelList()));
                    }
                    break;
                case Constants.GraphFilter.FILTER_TYPE_ONE_YEAR:
                    mLineChart.setXAxisRenderer(customXAxisRenderer);
                    x.setValueFormatter(new YearAxisValueFormatter(lineGraphDataSet.getLabelList()));
                    break;
                case Constants.GraphFilter.FILTER_TYPE_THREE_YEARS:
                    mLineChart.setXAxisRenderer(customXAxisRenderer);
                    x.setValueFormatter(new ThreeYearAxisValueFormatter(lineGraphDataSet.getLabelList()));
                    break;
                default:
                    x.setValueFormatter(new IndexAxisValueFormatter(lineGraphDataSet.getLabelList()));
            }

            x.setPosition(XAxis.XAxisPosition.BOTTOM);
            x.setCenterAxisLabels(true);
            x.setDrawGridLines(false);
            mLineChart.setExtraBottomOffset(50);
            x.setGranularityEnabled(true);
            mLineChart.invalidate();
        }*/


        if (lineGraphDataSet.getLineData() == null) {
            mLineChart.clear();
        } else {
            YAxis y = mLineChart.getAxisLeft();
            y.setDrawGridLines(false);
            y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
            y.setValueFormatter(new MyYAxisValueFormatter());
            y.setLabelCount(5, true);
            y.setDrawAxisLine(false);

            mLineChart.setRendererLeftYAxis(new CustomYRenderer(mLineChart.getViewPortHandler(),
                    mLineChart.getAxisLeft(), mLineChart.getTransformer(YAxis.AxisDependency.LEFT)));

            mLineChart.getAxisRight().setEnabled(false);
            mLineChart.clear();
            mLineChart.setXAxisRenderer(new XAxisRenderer(mLineChart.getViewPortHandler(),
                    mLineChart.getXAxis(), mLineChart.getTransformer(YAxis.AxisDependency.LEFT)));
            XAxis x = mLineChart.getXAxis();
            CustomXAxisRenderer customXAxisRenderer = new CustomXAxisRenderer(mLineChart.getViewPortHandler(),
                    mLineChart.getXAxis(), mLineChart.getTransformer(YAxis.AxisDependency.LEFT), lineGraphDataSet.getLabelList(), lineGraphDataSet.getFilterType());
            switch (lineGraphDataSet.getFilterType()) {
                case Constants.GraphFilter.FILTER_TYPE_TODAY:
                    if (lineGraphDataSet.getLabelList().size() > 1) {
                        mLineChart.setXAxisRenderer(customXAxisRenderer);
                        x.setValueFormatter(new XAxisValueFormatterForDay(lineGraphDataSet.getLabelList()));
                    } else {
                        x.setValueFormatter(new IndexAxisValueFormatter(lineGraphDataSet.getLabelList()));
                    }
                    break;
                case Constants.GraphFilter.FILTER_TYPE_ONE_YEAR:
                    mLineChart.setXAxisRenderer(customXAxisRenderer);
                    x.setValueFormatter(new YearAxisValueFormatter(lineGraphDataSet.getLabelList()));
                    break;
                case Constants.GraphFilter.FILTER_TYPE_THREE_YEARS:
                    mLineChart.setXAxisRenderer(customXAxisRenderer);
                    x.setValueFormatter(new ThreeYearAxisValueFormatter(lineGraphDataSet.getLabelList()));
                    break;
                default:
                    x.setValueFormatter(new IndexAxisValueFormatter(lineGraphDataSet.getLabelList()));
            }
            x.setPosition(XAxis.XAxisPosition.BOTTOM);
            x.setDrawGridLines(false);
            mLineChart.setExtraBottomOffset(30);
            x.setGranularityEnabled(true);
            mLineChart.setData(lineGraphDataSet.getLineData());
            mLineChart.invalidate();
        }
    }

    @Override
    public void setUpTopBar() {
        if (mTopBarHelper != null) {
            mTopBarHelper.setUpView();
        }
    }

    @Override
    public void setShareStockInfo(StockHold shareStockInfo) {
        TraderApplication.getInstance().loadImage(shareStockInfo.getCompanyLogo(), vImageCompanyLogo);

        if (!(TextUtils.isEmpty(shareStockInfo.getCurrentInvestment()))) {
            vTvCurrentSharePrice.setText(getString(R.string.price_text_rand_string, getCodeSnippet().getDecimalPoint(shareStockInfo.getCurrentInvestment())));
        }

        if (!(TextUtils.isEmpty(shareStockInfo.getShareCode()))) {
            vTvCompanyHint.setText(shareStockInfo.getShareCode());

        }

        if (!(TextUtils.isEmpty(shareStockInfo.getShares()))) {
            vTvSharePurchased.setText(getCodeSnippet().getDecimalPoint(shareStockInfo.getShares()));
        }

        vTvPurchasedShare.setText(getString(R.string.price_text_rand_string, getCodeSnippet().getDecimalPoint(shareStockInfo.getBaseCost())));
        /*if (!(TextUtils.isEmpty(shareStockInfo.getBaseCost()))) {
            vTvPurchasedShare.setText(getString(R.string.price_text_rand_string, getCodeSnippet().getDecimalPoint(shareStockInfo.getBaseCost())));
        }*/


        if (!(TextUtils.isEmpty(shareStockInfo.getCurrentHolding()))) {
            vTvTotalHoldingAmount.setText(getString(R.string.price_text_rand_string, getCodeSnippet().getDecimalPoint(shareStockInfo.getCurrentHolding())));
        }

        if (!(TextUtils.isEmpty(shareStockInfo.getCurrentHolding()))) {
            vTvCurrentShare.setText(getString(R.string.price_text_rand_string, getCodeSnippet().getDecimalPoint(shareStockInfo.getClosingPrice())));
        }


        Float profitOrLoss = TraderApplication.getInstance().getProfitOrLoss(shareStockInfo.getClosingPrice(), shareStockInfo.getBaseCost());
        Float profitOrLossShare = TraderApplication.getInstance().getProfitLossForShare(shareStockInfo.getClosingPrice(), shareStockInfo.getBaseCost(), Float.valueOf(shareStockInfo.getShares()));


        Float profitOrLossValue = TraderApplication.getInstance().getProfitLossForValue(
                Float.valueOf(TextUtils.isEmpty(shareStockInfo.getCurrentInvestment()) ?
                        "0.0" : shareStockInfo.getCurrentInvestment()),
                shareStockInfo.getClosingPrice(),
                Float.valueOf(TextUtils.isEmpty(shareStockInfo.getShares()) ?
                        "0.0" : shareStockInfo.getShares()));

         /*the above code is commended due to client requirement,
        the profitOrLossValue is to formatWithoutRoundOff is done*/

       /* Float profitOrLossValue = TraderApplication.getInstance().getProfitLossForValue(
                Float.valueOf(TextUtils.isEmpty(shareStockInfo.getCurrentInvestment())
                        ? "0.0" : getCodeSnippet().getDecimalPoint(shareStockInfo.getCurrentInvestment())),
                Float.valueOf(getCodeSnippet().getDecimalPoint(shareStockInfo.getClosingPrice())),
                Float.valueOf(TextUtils.isEmpty(shareStockInfo.getShares())
                        ? "0.0" : getCodeSnippet().getDecimalPoint(shareStockInfo.getShares())));*/



        vTvProfit.setText(getCodeSnippet().getDecimalPointWithMinus(profitOrLoss));
        vTvProfitLossPerShare.setText(getCodeSnippet().getDecimalPointWithMinusOrNotWithR(profitOrLossShare));
        vTvProfitLossValue.setText(getCodeSnippet().getDecimalPointWithMinusOrNotWithR(profitOrLossValue));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(BuyAndSellActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    /***/
    private void bindReceiver() {
        IntentFilter balanceUpdateIntentFilter = new IntentFilter(Constants.BroadCastKey.ACTION_BALANCE_UPDATE_COMPLETED);
        balanceUpdateIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(bReceiverBalanceUpdate, balanceUpdateIntentFilter);

        IntentFilter holdingUpdateIntentFilter = new IntentFilter(Constants.BroadCastKey.ACTION_DELAY_PRICE_UPDATE_COMPLETED);
        holdingUpdateIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(bReceiverUpdateHoldingInfo, holdingUpdateIntentFilter);
    }

    /***/
    private void unregisterReceiver() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(bReceiverBalanceUpdate);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(bReceiverUpdateHoldingInfo);
    }

    /***/
    private final BroadcastReceiver bReceiverBalanceUpdate = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
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
                iBuyAndSellPresenter.doDelayPriceUpdate();
            }
        }
    };

    /***/
    private void closeActivity(int code) {
        if (RESULT_OK == code) {
            getCodeSnippet().hideKeyboard(BuyAndSellActivity.this);
//            onBackPressed();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCodes.NAVIGATE_TO_SELL:
                closeActivity(resultCode);
                break;
            case Constants.RequestCodes.NAVIGATE_TO_BUY:
                closeActivity(resultCode);
                break;
        }
    }
}
