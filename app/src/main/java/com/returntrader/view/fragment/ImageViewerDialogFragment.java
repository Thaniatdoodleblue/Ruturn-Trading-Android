package com.returntrader.view.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.TraderApplication;
import com.returntrader.model.dto.response.FICAPreviewData;

import java.io.File;

/**
 * Created by nirmal on 2/15/2018.
 */

public class ImageViewerDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    //    private ZoomableImageView ivViewer;
    private ImageView ivViewer;
    private TextView tvClose;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogFragmentTheme;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivViewer = view.findViewById(R.id.ivViewer);
        tvClose = view.findViewById(R.id.tvClose);

        if (mFICAPreviewData != null) {
            tvClose.setText(TextUtils.isEmpty(mFICAPreviewData.getTitle()) ? "" : mFICAPreviewData.getTitle());
            String mImagePath = mFICAPreviewData.getImageUrl();
            File imageFile = mFICAPreviewData.getImageFile();

            if (!TextUtils.isEmpty(mImagePath)) {
                TraderApplication.getInstance().loadImage(mImagePath, ivViewer);
            } else {
                if (imageFile != null) {
                    TraderApplication.getInstance().loadImage(imageFile, ivViewer);
                } else {
                    showMessage("Sorry. We can't seem to find it.");
                }
            }


        }
        tvClose.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialogfragment_imageviewer;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvClose) {
            dismiss();
        }
    }

    private FICAPreviewData mFICAPreviewData;

    /***/
    public void setFICAPreviewData(FICAPreviewData mFICAPreviewData) {
        this.mFICAPreviewData = mFICAPreviewData;
    }
}
