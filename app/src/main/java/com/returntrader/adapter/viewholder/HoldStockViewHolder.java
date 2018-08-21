package com.returntrader.adapter.viewholder;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.Log;
import com.returntrader.model.common.StockHold;
import com.returntrader.utils.CodeSnippet;

/**
 * Created by moorthy on 8/10/2017.
 */

public class HoldStockViewHolder extends BaseViewHolder<StockHold> implements View.OnClickListener {
    private TextView mTvInvestedAmount;
    private TextView mTvHoldingAmount;
    private TextView mTvCompanyHint;
    private ImageView mImageCompanyIcon;
    private BaseRecyclerAdapterListener<StockHold> adapterListener;
    private CodeSnippet mCodeSnippet;
    private AppConfigurationManager mAppConfigurationManager;


    public HoldStockViewHolder(View itemView, BaseRecyclerAdapterListener<StockHold> adapterListener, CodeSnippet mCodeSnippet) {
        super(itemView);
        this.adapterListener = adapterListener;
        this.mCodeSnippet = mCodeSnippet;
        mAppConfigurationManager = new AppConfigurationManager(itemView.getContext());
        bindHolder();
    }


    /***/
    private void bindHolder() {
        mImageCompanyIcon = itemView.findViewById(R.id.image_company_info);
        mTvInvestedAmount = itemView.findViewById(R.id.tv_invested_amount);
        mTvHoldingAmount = itemView.findViewById(R.id.tv_holding_price);
        mTvCompanyHint = itemView.findViewById(R.id.tv_company_hint);
        itemView.setOnClickListener(this);
    }


    @Override
    void populateData(StockHold data) {
        boolean isCompanyAvailable = false;
        if (data != null) {
            if (!TextUtils.isEmpty(data.getCompanyAvailabilityStatus())) {
                isCompanyAvailable = data.getCompanyAvailabilityStatus().equalsIgnoreCase(Constants.Common.ENABLED_COMPANIES_STATUS) ? false : true;
            }
        }

        TraderApplication.getInstance().loadImage(data.getCompanyLogo(), mImageCompanyIcon);
        mTvInvestedAmount.setText(itemView.getContext().getString(R.string.price_text_rand_string, mCodeSnippet.getDecimalPoint(data.getCurrentInvestment())));
        //mTvCompanyHint.setText(data.getShareCode());
        mTvHoldingAmount.setText(itemView.getContext().getString(R.string.price_text_rand_string, mCodeSnippet.getDecimalPoint(data.getCurrentHolding())));
        boolean isHigh = TraderApplication.getInstance().isInvestedHigh(data.getCurrentHolding(), data.getCurrentInvestment());

        /*
        * Client asked to remove the isMarketAvailable check,so its commented
        * */
       // if (mAppConfigurationManager.isMarketAvailable()) {
            if (isHigh) {
                setHoldingProperties(R.color.colorGreen, R.drawable.ic_arrow_drop_up_green);
            } else {
                setHoldingProperties(R.color.colorRed, R.drawable.ic_arrow_drop_down_red);
            }
        /*} else {
            if (isHigh) {
                setHoldingProperties(R.color.colorSDGray, R.drawable.ic_arrow_drop_up_grey);
            } else {
                setHoldingProperties(R.color.colorSDGray, R.drawable.ic_arrow_drop_down_grey);
            }
        }*/

        if (!isCompanyAvailable) {
            if (isHigh) {
                setHoldingProperties(R.color.colorSDGray, R.drawable.ic_arrow_drop_up_grey);
            } else {
                setHoldingProperties(R.color.colorSDGray, R.drawable.ic_arrow_drop_down_grey);
            }
        }
    }

    /***/
    private void setHoldingProperties(int color, int drawableIcon) {
        mTvHoldingAmount.setTextColor(ContextCompat.getColor(itemView.getContext(), color));
        mTvHoldingAmount.setCompoundDrawablesWithIntrinsicBounds(drawableIcon, 0, 0, 0);
    }

    @Override
    public void onClick(View view) {
        adapterListener.onClickItem(data, getAdapterPosition());
    }
}
