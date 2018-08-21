package com.returntrader.view.fragment;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.model.listener.IBottomSheetListener;

public class CategoryBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    public TextView vTvJse;
    private IBottomSheetListener iBottomSheetListener;
    private int mCurrentPage;

    @Override
    public void setupDialog(Dialog dialog, int style) {
//        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet_category, null);
        bindDialog(contentView);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            //((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
            ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
            ((BottomSheetBehavior) behavior).setPeekHeight(1000);
            contentView.requestLayout();
        }
    }

    private void bindDialog(View contentView) {
        vTvJse = (TextView) contentView.findViewById(R.id.tv_jse);
        vTvJse.setOnClickListener(this);
        //setTextColor(mCurrentPage);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_jse:
                getDialog().dismiss();
                if (iBottomSheetListener != null) {
                    iBottomSheetListener.onClickFilter(Constants.LoadCompanyList.LOAD_A_Z);
                }
                break;
        }
    }


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
