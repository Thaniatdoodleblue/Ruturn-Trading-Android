package com.returntrader.adapter.viewholder;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.common.Constants;
import com.returntrader.model.common.FilterCategory;

/**
 * Created by moorthy on 8/10/2017.
 */

public class FilterCategoryViewHolder extends BaseViewHolder<FilterCategory> implements View.OnClickListener {


    private TextView mTvFilterName;
    private LinearLayout filterItemLayout;
    private BaseRecyclerAdapterListener<FilterCategory> adapterListener;
    private int mCurrentSelectedType;


    public FilterCategoryViewHolder(View itemView, BaseRecyclerAdapterListener<FilterCategory> adapterListener, int currentSelectedType) {
        super(itemView);
        this.adapterListener = adapterListener;
        this.mCurrentSelectedType = currentSelectedType;
        bindHolder();

    }


    private void bindHolder() {
        mTvFilterName = (TextView) itemView.findViewById(R.id.tv_filter_type);
        filterItemLayout = (LinearLayout) itemView.findViewById(R.id.filter_item_layout);
        itemView.setOnClickListener(this);
    }


    @Override
    void populateData(FilterCategory data) {
        /*if (mCurrentSelectedType == data.getType()) {
            mTvFilterName.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.homePage));
        } else {
            mTvFilterName.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
        }*/

       /* Typeface typeface = Typeface.createFromAsset(itemView.getContext().getAssets(), "PROXIMANOVA_REGULAR.OTF");

        if (mCurrentSelectedType == data.getType()) {
            mTvFilterName.setTypeface(typeface, Typeface.BOLD);
        } else {
            mTvFilterName.setTypeface(typeface, Typeface.NORMAL);
        }*/

        switch (data.getType()) {
            case Constants.LoadCompanyList.LOAD_FAVOURITE:
                filterItemLayout.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.filterYellow));
                break;
            case Constants.LoadCompanyList.LOAD_DAY:
                filterItemLayout.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.filterGreen));
                break;
            case Constants.LoadCompanyList.LOAD_TOP_FORTY:
                filterItemLayout.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.filterBlue));
                break;
            default:
                filterItemLayout.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
                break;
        }

        mTvFilterName.setText(data.getCategoryName());
    }

    @Override
    public void onClick(View view) {
        adapterListener.onClickItem(data, getAdapterPosition());
    }
}
