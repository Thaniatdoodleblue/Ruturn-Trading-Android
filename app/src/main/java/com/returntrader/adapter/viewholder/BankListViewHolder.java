package com.returntrader.adapter.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.model.common.BankItem;

/**
 * Created by moorthy on 7/26/2017.
 */

public class BankListViewHolder extends BaseViewHolder<BankItem> {


    private TextView tvTvBankName;
    private TextView tvTvAllowDeposit;
    private TextView tvTvAccountNumber;
    private TextView tvTvBranchCode;
    private TextView tvTvBranchName;
    private TextView tvTvAccountHolder;


    public BankListViewHolder(View itemView) {
        super(itemView);
        bindHolder();

    }

    private void bindHolder() {

        tvTvBankName = (TextView) itemView.findViewById(R.id.tv_bank_name);
        tvTvAllowDeposit = (TextView) itemView.findViewById(R.id.tv_allow_deposit);
        tvTvAccountNumber = (TextView) itemView.findViewById(R.id.tv_account_number);
        tvTvBranchCode = (TextView) itemView.findViewById(R.id.tv_branch_code);
        tvTvBranchName = (TextView) itemView.findViewById(R.id.tv_branch_name);
        tvTvAccountHolder = (TextView) itemView.findViewById(R.id.tv_account_holder);

    }

    @Override
    void populateData(BankItem data) {
        if (!(TextUtils.isEmpty(data.getBankName()))) {
            tvTvBankName.setText(data.getBankName());
        }

        if (!(TextUtils.isEmpty(data.getAllowDeposit()))) {
            tvTvAllowDeposit.setText(data.getAllowDeposit());
        }
        if (!(TextUtils.isEmpty(data.getAccountNumber()))) {
            tvTvAccountNumber.setText(data.getAccountNumber());
        }
        if (!(TextUtils.isEmpty(data.getBranchCode()))) {
            tvTvBranchCode.setText(data.getBranchCode());
        }
        if (!(TextUtils.isEmpty(data.getBranchName()))) {
            tvTvBranchName.setText(data.getBranchName());
        }
        if (!(TextUtils.isEmpty(data.getAccountHolder()))) {
            tvTvAccountHolder.setText(data.getAccountHolder());
        }

    }
}
