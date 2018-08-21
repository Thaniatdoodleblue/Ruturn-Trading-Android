package com.returntrader.view.fragment;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.FilterCategoryAdapter;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.common.Constants;
import com.returntrader.model.common.FilterCategory;
import com.returntrader.model.listener.IBottomSheetListener;

import java.util.ArrayList;
import java.util.List;

public class FilterBottomSheetFragment extends BottomSheetDialogFragment {

    public TextView vTvDay, vTvTopForty, vTvFavourite;
    private IBottomSheetListener iBottomSheetListener;
    private int mCurrentPage = Constants.LoadCompanyList.LOAD_DAY;
    private RecyclerView vRvCategoryList;

    @Override
    public void setupDialog(Dialog dialog, int style) {
//        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet_filter, null);
        bindDialog(contentView);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
           /* ((BottomSheetBehavior) behavior).setPeekHeight(1000);*/
            contentView.requestLayout();
        }
    }

    private void bindDialog(View contentView) {
        vRvCategoryList = contentView.findViewById(R.id.recycler_filter_category_list);
        vRvCategoryList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //setTextColor(mCurrentPage);
        setCategoryListAdapter();
    }


    private void setCategoryListAdapter() {
        FilterCategoryAdapter filterCategoryAdapter = new FilterCategoryAdapter(getCategoryList(), filterCategoryBaseRecyclerAdapterListener, mCurrentPage);
        vRvCategoryList.setAdapter(filterCategoryAdapter);
    }

    private List<FilterCategory> getCategoryList() {
        /*Favourites, Day, Top 40, Big Brands, Most Popular, REIT, AltX, A-Z*/
        List<FilterCategory> filterCategories = new ArrayList<>();
        filterCategories.add(new FilterCategory(getString(R.string.txt_fav), Constants.LoadCompanyList.LOAD_FAVOURITE));
        filterCategories.add(new FilterCategory(getString(R.string.txt_day), Constants.LoadCompanyList.LOAD_DAY));
        filterCategories.add(new FilterCategory(getString(R.string.txt_top_forty), Constants.LoadCompanyList.LOAD_TOP_FORTY));
        filterCategories.add(new FilterCategory(getString(R.string.txt_big_brands), Constants.LoadCompanyList.LOAD_BIG_BRANDS));
        filterCategories.add(new FilterCategory(getString(R.string.txt_most_popular), Constants.LoadCompanyList.LOAD_MOST_POPULAR));
        filterCategories.add(new FilterCategory(getString(R.string.txt_reit), Constants.LoadCompanyList.LOAD_REIT));
        filterCategories.add(new FilterCategory(getString(R.string.txt_altx), Constants.LoadCompanyList.LOAD_ALTX));
        filterCategories.add(new FilterCategory(getString(R.string.txt_az), Constants.LoadCompanyList.LOAD_A_Z));
        return filterCategories;
    }


    private BaseRecyclerAdapterListener<FilterCategory> filterCategoryBaseRecyclerAdapterListener = new BaseRecyclerAdapterListener<FilterCategory>() {
        @Override
        public void onClickItem(FilterCategory data, int position) {
            getDialog().dismiss();
            if (iBottomSheetListener != null) {
                iBottomSheetListener.onClickFilter(data.getType());
            }
        }
    };

    /*@Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.day:
                getDialog().dismiss();
                if (iBottomSheetListener != null) {
                    iBottomSheetListener.onClickFilter(Constants.LoadCompanyList.LOAD_DAY);
                }
                break;
            case R.id.top40:
                if (iBottomSheetListener != null) {
                    iBottomSheetListener.onClickFilter(Constants.LoadCompanyList.LOAD_TOP_FORTY);
                }
                getDialog().dismiss();
                break;
            case R.id.favourite:
                getDialog().dismiss();
                if (iBottomSheetListener != null) {
                    iBottomSheetListener.onClickFilter(Constants.LoadCompanyList.LOAD_FAVOURITE);
                }
                break;

        }
    }*/


    public void setBottomSheetListener(IBottomSheetListener bottomSheetListener) {
        this.iBottomSheetListener = bottomSheetListener;
    }


    public void setCurrentPage(int filterItemPosition) {
        this.mCurrentPage = filterItemPosition;
    }


    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };
}
