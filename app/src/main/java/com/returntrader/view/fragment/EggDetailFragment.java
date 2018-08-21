package com.returntrader.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.HoldStockAdapter;
import com.returntrader.common.Constants;
import com.returntrader.library.Log;
import com.returntrader.presenter.EggDetailPresenter;
import com.returntrader.presenter.ipresenter.IEggDetailPresenter;
import com.returntrader.view.activity.BuyAndSellActivity;
import com.returntrader.view.iview.IEggDetailView;
import com.returntrader.view.iview.IHomeView;

public class EggDetailFragment extends BaseFragment implements View.OnClickListener, IEggDetailView {

    private Button btnViewInvest, btnHideFunds;
    private LinearLayout mLayoutShare;
    private LinearLayout mCashInvestmentLayout;
    private RecyclerView mRvHoldingList;
    private ProgressBar mProgressBar;
    private FrameLayout mLayoutHolding;

    private TextView vTvCashAmount;
    private TextView vTvTotalInvestment;
    private TextView vTvTotalShare;
    private SwipeRefreshLayout vSwipeRefreshLayout;
    private IEggDetailPresenter iEggDetailPresenter;

    public EggDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getCodeSnippet().hideKeyboard(getActivity());
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_egg, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bindFragment(view);
        iEggDetailPresenter = new EggDetailPresenter(this);
        iEggDetailPresenter.onCreatePresenter(getArguments());
    }

    /***/
    private void bindFragment(View view) {
        vSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_holding);
        mLayoutHolding = view.findViewById(R.id.layout_holding);
        mProgressBar = view.findViewById(R.id.progressBar);
        mCashInvestmentLayout = view.findViewById(R.id.cashInvestlayout);
        mRvHoldingList = view.findViewById(R.id.recycler_view_holding);
        btnViewInvest = view.findViewById(R.id.viewInvestments);
        mLayoutShare = view.findViewById(R.id.sharesLayout);

        btnHideFunds = view.findViewById(R.id.hideFunds);
        vTvCashAmount = view.findViewById(R.id.tv_cash_egg);
        vTvTotalInvestment = view.findViewById(R.id.tv_total_investment);
        vTvTotalShare = view.findViewById(R.id.tv_total_share);

        view.findViewById(R.id.shares).setOnClickListener(this);
        btnViewInvest.setOnClickListener(this);
        btnHideFunds.setOnClickListener(this);
        mLayoutShare.setOnClickListener(this);
        vSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        mRvHoldingList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        bindReceiver();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewInvestments:
                if (mLayoutShare.isShown()) {
                    mLayoutHolding.setVisibility(View.GONE);
                    mLayoutShare.setVisibility(View.GONE);
                } else {
                    mLayoutShare.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.shares:
                showOrHideHoldings();
                break;
            case R.id.hideFunds:
                iEggDetailPresenter.setAuthenticationRequired(true);
                iEggDetailPresenter.setShowFingerPrint(true);
                ((IHomeView) getActivity()).hideTopBannerCardView(View.GONE);
                ((IHomeView) getActivity()).moveToHomeScreen(); // Move to home screen
                break;
        }
    }


    /***/
    private void showOrHideHoldings() {
        if (mLayoutHolding.isShown()) {
            mLayoutHolding.setVisibility(View.GONE);
        } else {
            mLayoutHolding.setVisibility(View.VISIBLE);
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
    public void setHoldingAdapter(HoldStockAdapter holdingAdapter) {
        mRvHoldingList.setAdapter(holdingAdapter);
    }

    @Override
    public void setBalanceData(String lastKnownBalance) {
        vTvCashAmount.setText(getString(R.string.price_text_rand_string, getCodeSnippet().formatStringTo2DecimalWithoutRound(lastKnownBalance)));
    }

    @Override
    public void sendBalanceUpdatedBroadCast() {
        Intent broadCastIntent = new Intent();
        broadCastIntent.setAction(Constants.BroadCastKey.ACTION_BALANCE_UPDATE_COMPLETED);
        broadCastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(broadCastIntent);
    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        vSwipeRefreshLayout.setRefreshing(isRefreshing);
    }

    @Override
    public void setInvestmentDetails(String investmentAmount, String totalShare) {
        setRefreshing(false);

        boolean isHigh = iEggDetailPresenter.isHigh();

        if (getCodeSnippet() == null) {
            return;
        }

        if (!TextUtils.isEmpty(investmentAmount)) {
            vTvTotalInvestment.setText(getString(R.string.price_text_rand_string, getCodeSnippet().formatStringTo2DecimalWithoutRound(investmentAmount)));
            sendBalanceUpdatedBroadCast();
        }

        if (!TextUtils.isEmpty(totalShare)) {
            vTvTotalShare.setText(getCodeSnippet().formatStringTo2DecimalWithoutRound(totalShare));
        }

        if (isHigh) {
            vTvTotalInvestment.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
            vTvTotalInvestment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_green, 0, 0, 0);

            vTvTotalShare.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
            vTvTotalShare.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_green, 0, 0, 0);
        } else {
            vTvTotalInvestment.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
            vTvTotalInvestment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_down_red, 0, 0, 0);

            vTvTotalShare.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
            vTvTotalShare.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_down_red, 0, 0, 0);
        }
    }

    @Override
    public void navigateToSellBuy(Bundle bundle) {
        Intent intent = new Intent(getActivity(), BuyAndSellActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void refreshContent(boolean canCallHoldings) {
        iEggDetailPresenter.refreshContent(canCallHoldings);
    }


    /***/
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            iEggDetailPresenter.refreshContent(true);
        }
    };

    /***/
    private void bindReceiver() {
        IntentFilter intentFilter = new IntentFilter(Constants.BroadCastKey.ACTION_BALANCE_UPDATE_COMPLETED);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mBalanceUpdateReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    /***/
    private void unregisterReceiver() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mBalanceUpdateReceiver);
    }

    /***/
    private final BroadcastReceiver mBalanceUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "Balance onReceive");
            switch (intent.getAction()) {
                case Constants.BroadCastKey.ACTION_BALANCE_UPDATE_COMPLETED:
                    iEggDetailPresenter.updateBalance();
                    break;
            }
        }
    };

}
