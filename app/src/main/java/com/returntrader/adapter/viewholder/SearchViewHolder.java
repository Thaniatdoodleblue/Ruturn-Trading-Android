package com.returntrader.adapter.viewholder;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.SparkGraphAdapter;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.common.TraderApplication;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.Log;
import com.robinhood.spark.SparkView;

import static com.returntrader.common.Constants.Common.ENABLED_COMPANIES_STATUS;
import static com.returntrader.common.Constants.Common.NA;

/**
 * Created by Karthikeyan on 05-07-2017
 */
public class SearchViewHolder extends BaseViewHolder<CompanyItemInfo> implements View.OnClickListener {

    private TextView stockPrice;
    private TextView tvDifferencePrice;
    private ImageView companyLogo;
    private SparkView mSparkView;
    private SparkGraphAdapter mSpareAdapter;
    private BaseRecyclerAdapterListener<CompanyItemInfo> listener;
    private AppConfigurationManager mAppConfigurationManager;
    private CardView cvListContainer;

    public SearchViewHolder(View itemView, BaseRecyclerAdapterListener<CompanyItemInfo> listener) {
        super(itemView);
        this.listener = listener;
        bindHolder();
    }

    private void bindHolder() {
        mAppConfigurationManager = new AppConfigurationManager(itemView.getContext());
        mSparkView = itemView.findViewById(R.id.graph_deviation);
//        mSparkView.setFill(false);
//        mSparkView.setCornerRadius(0.0f);//To remove curvy edges
        tvDifferencePrice = itemView.findViewById(R.id.tv_difference_price);
        stockPrice = itemView.findViewById(R.id.stockPrice);
        companyLogo = itemView.findViewById(R.id.companylogo);
        cvListContainer = itemView.findViewById(R.id.cvListContainer);
        itemView.setOnClickListener(this);
    }

    @Override
    void populateData(CompanyItemInfo data) {


        //float progress = TraderApplication.getInstance().getLatestPrice(data);
        float progress = data.getLastKnownDelayPrice();
        boolean isHigh = TraderApplication.getInstance().isHigh(progress);
        mSparkView.setLineColor(ContextCompat.getColor(itemView.getContext(), R.color.colorWhite));
        cvListContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.white));

        if (mAppConfigurationManager.isMarketAvailable()) {
            showAsEnabled(isHigh);
        } else {
            showAsSuspended(isHigh);
        }

        if (data.getCompanyAvailabilityStatus() != null) {
            if (data.getCompanyAvailabilityStatus().equalsIgnoreCase(ENABLED_COMPANIES_STATUS)) {
                showAsSuspended(isHigh);
            } else if (data.getCompanyAvailabilityStatus().equalsIgnoreCase(NA)) {
                showAsSuspended(isHigh);
            }
        } else {
            cvListContainer.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.colorGray));
            showAsSuspended(isHigh);
        }


        Log.d("GraphList", "" + data.getGraphList());
        if (data.getGraphList() != null) {
            mSpareAdapter = new SparkGraphAdapter(data.getGraphList());
            mSparkView.setAdapter(mSpareAdapter);
        }

        stockPrice.setText("R " + TraderApplication.getInstance().getValue(data.getLastPrice()));
        tvDifferencePrice.setText(TraderApplication.getInstance().getValue(progress) + " %");
        TraderApplication.getInstance().loadImage(data.getCompanyImageUrl(), companyLogo);
    }

    /***/
    private void showAsEnabled(boolean isHigh) {
        if (isHigh) {
            tvDifferencePrice.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorGreen));
            stockPrice.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorGreen));
            mSparkView.setLineColor(ContextCompat.getColor(itemView.getContext(), R.color.colorGreen));
            tvDifferencePrice.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_green, 0, 0, 0);
        } else {
            tvDifferencePrice.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorRed));
            stockPrice.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorRed));
            mSparkView.setLineColor(ContextCompat.getColor(itemView.getContext(), R.color.colorRed));
            tvDifferencePrice.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_down_red, 0, 0, 0);
        }
    }

    /***/
    private void showAsSuspended(boolean isHigh) {
        mSparkView.setLineColor(ContextCompat.getColor(itemView.getContext(), R.color.colorSDGray));
        tvDifferencePrice.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorSDGray));
        stockPrice.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorSDGray));
        if (isHigh) {
            tvDifferencePrice.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_up_grey, 0, 0, 0);
        } else {
            tvDifferencePrice.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_arrow_drop_down_grey, 0, 0, 0);
        }
        stockPrice.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.colorSDGray));
    }

    @Override
    public void onClick(View view) {
        listener.onClickItem(data, getAdapterPosition());
    }


}
