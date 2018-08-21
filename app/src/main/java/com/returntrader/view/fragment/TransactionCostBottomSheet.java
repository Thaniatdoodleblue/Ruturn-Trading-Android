package com.returntrader.view.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.returntrader.R;
import com.returntrader.adapter.TransactionFeeAdapter;
import com.returntrader.common.Constants;
import com.returntrader.library.Log;
import com.returntrader.model.common.TransactionFee;
import com.returntrader.view.activity.TransactionFeeDetailsActivity;

import java.util.ArrayList;
import java.util.List;


public class TransactionCostBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    private RecyclerView mRvTransactionCostList;


    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.bottomsheet, null);
        Button button = contentView.findViewById(R.id.close);
        button.setOnClickListener(this);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
           // ((BottomSheetBehavior) behavior).setPeekHeight(1000);
            contentView.requestLayout();
        }
        bindBottomSheet(contentView);
    }


    /***/
    private void bindBottomSheet(View contentView) {
        contentView.findViewById(R.id.layout_view_tax_info).setOnClickListener(this);
        mRvTransactionCostList = contentView.findViewById(R.id.recycler_transactionCost);
        mRvTransactionCostList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        Bundle bundle = getArguments();
        String price = bundle.getString(Constants.BundleKey.BUNDLE_SHARE_AMOUNT);
        int transactionType = bundle.getInt(Constants.BundleKey.BUNDLE_TRANSACTION_COST_FROM);

        if (!(TextUtils.isEmpty(price))) {
            prepareAdapter(price, transactionType);
            //((TextView) contentView.findViewById(R.id.tv_total_amount)).setText(TraderApplication.getInstance().getTotalAmount(price));
        }
    }

    /***/
    private void prepareAdapter(String price, int transactionType) {
        List<TransactionFee> transactionFees = new ArrayList<>();
        transactionFees.add(new TransactionFee("Per Trade Cost", Constants.TaxInfo.TAX_TRADER_COST, "R", 2));
        transactionFees.add(new TransactionFee("Broker Commission (0.5%)", Constants.TaxInfo.TAX_BROKER_COMMISSION));
        if (transactionType == Constants.TransactionType.TRANSACTION_BUY) {
            transactionFees.add(new TransactionFee("Securities Transfer Tax (STT) and Administration (0.25%)", Constants.TaxInfo.TAX_SECURITY_TRANSFER_ADMINISTRATION));
        }
        transactionFees.add(new TransactionFee("Settlement and Administration (0.075%)", Constants.TaxInfo.TAX_SETTLE_MENT_ADMINISTRATION));
        transactionFees.add(new TransactionFee("Investor Protection Levy (IPL) and Administration (0.0002%)", Constants.TaxInfo.TAX_IPL));
        transactionFees.add(new TransactionFee("Tax on costs (VAT 15%)", Constants.TaxInfo.TAX_VAT));
        transactionFees.add(new TransactionFee("Total Transaction Cost", 1));
        TransactionFeeAdapter transactionFeeAdapter = new TransactionFeeAdapter(transactionFees, price, transactionType);
        mRvTransactionCostList.setAdapter(transactionFeeAdapter);
    }

    /*private void calculateTranactionsFees(View contentView) {
        Bundle bundle = getArguments();
        String price = bundle.getString(Constants.BundleKey.BUNDLE_SHARE_AMOUNT);

        if (!(TextUtils.isEmpty(price))) {
            ((TextView) contentView.findViewById(R.id.tv_trader_cost)).setText("5.00");
            ((TextView) contentView.findViewById(R.id.tv_brokage_amount)).setText(TraderApplication.getInstance().getTaxAmount("0.25", price));
            ((TextView) contentView.findViewById(R.id.tv_secure_transfer_tax)).setText(TraderApplication.getInstance().getTaxAmount("0.25", price));
            ((TextView) contentView.findViewById(R.id.tv_settlement_tax)).setText(TraderApplication.getInstance().getTaxAmount("0.075", price));
            ((TextView) contentView.findViewById(R.id.tv_inverster_protection_tax)).setText(TraderApplication.getInstance().getTaxAmount("0.0002", price));
            ((TextView) contentView.findViewById(R.id.tv_vat_tax)).setText(TraderApplication.getInstance().getTaxAmount("14", price));

        }
    }*/


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.close:
                getDialog().dismiss();
                break;
            case R.id.layout_view_tax_info:
                //getDialog().dismiss();
                navigateToTransactionFeeDetails();
                break;

        }
    }

    private void navigateToTransactionFeeDetails() {
        Intent intent = new Intent(getActivity(), TransactionFeeDetailsActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }
}
