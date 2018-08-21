package com.returntrader.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.model.common.FicaDocumentStatus;
import com.returntrader.model.common.FicaItem;
import com.returntrader.model.dto.response.FICAPreviewData;
import com.returntrader.permissions.IPermissionHandler;
import com.returntrader.permissions.PermissionProducer;
import com.returntrader.permissions.RequestPermission;
import com.returntrader.presenter.UploadPresenter;
import com.returntrader.presenter.ipresenter.IUploadPresenter;
import com.returntrader.sync.SyncAccountDetailService;
import com.returntrader.view.fragment.ImageViewerDialogFragment;
import com.returntrader.view.iview.IUploadView;

import java.io.File;

import static com.returntrader.common.Constants.BundleKey.BUNDLE_TOOLBAR_TITLE;

/**
 * Created by moorthy on 8/2/2017
 */

public class UploadActivity extends BaseActivity implements View.OnClickListener, IUploadView, PermissionProducer {
    private TextView vTvFrontText;
    private TextView vTvDescription;
    private LinearLayout mLayoutFront;
    private LinearLayout mLayoutBack;
    private TextView mTvUploadIconFront;
    private TextView mTvUploadIconBack;
    private ImageView mImageFront;
    private ImageView mImageBack;
    private Button vBtnComplete;
    private TextView mToolbarTitle;
    private IUploadPresenter iUploadPresenter;
    private IPermissionHandler iPermissionHandler;
    private ImageButton ibPreviewFront;
    private ImageButton ibPreviewBack;
    private TextView tvChangePhotoFront;
    private TextView tvChangePhotoBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        bindActivity();
        iPermissionHandler = RequestPermission.newInstance(this);
        iUploadPresenter = new UploadPresenter(this);
        iUploadPresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mToolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        mToolbarTitle.setText(R.string.title_bank_statement);

        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vTvFrontText = findViewById(R.id.tv_front);
        vTvDescription = findViewById(R.id.tv_upload_text_description);
        mLayoutFront = findViewById(R.id.layout_front);
        mLayoutBack = findViewById(R.id.layout_back);
        vBtnComplete = findViewById(R.id.btn_complete);

        tvChangePhotoFront = findViewById(R.id.tv_change_photo_front);
        tvChangePhotoBack = findViewById(R.id.tv_change_photo_back);

        mTvUploadIconFront = findViewById(R.id.tv_upload_front);
        mTvUploadIconBack = findViewById(R.id.tv_upload_back);

        mImageFront = findViewById(R.id.image_front);
        mImageBack = findViewById(R.id.image_back);

        ibPreviewFront = findViewById(R.id.ibPreviewFront);
        ibPreviewBack = findViewById(R.id.ibPreviewBack);

        ibPreviewFront.setVisibility(View.GONE);
        ibPreviewBack.setVisibility(View.GONE);

        mLayoutBack.setOnClickListener(this);
        mLayoutFront.setOnClickListener(this);
        vBtnComplete.setOnClickListener(this);
        ibPreviewFront.setOnClickListener(this);
        ibPreviewBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        FICAPreviewData mFICAPreviewData = new FICAPreviewData();
        mFICAPreviewData.setTitle("FICA Preview");//mToolbarTitle.getText().toString()

        if (view.getId() == R.id.ibPreviewFront) {
            if (tvChangePhotoFront.getVisibility() == View.VISIBLE) {
                mFICAPreviewData.setImageFile(fileFront);
            } else {
                mFICAPreviewData.setImageUrl(frontUrl);
            }
            previewWithImageViewer(mFICAPreviewData);
        } else if (view.getId() == R.id.ibPreviewBack) {
            if (tvChangePhotoBack.getVisibility() == View.VISIBLE) {
                mFICAPreviewData.setImageFile(fileBack);
            } else {
                mFICAPreviewData.setImageUrl(backUrl);
            }
            previewWithImageViewer(mFICAPreviewData);
        } else {
            if (iUploadPresenter.getUploadStatus()) {
                switch (view.getId()) {
                    case R.id.layout_front:
                        iUploadPresenter.onUploadFront();
                        break;
                    case R.id.layout_back:
                        iUploadPresenter.onUploadBack();
                        break;
                    case R.id.btn_complete:
                        iUploadPresenter.onClickComplete();
                        break;
                }
            } else {
                showMessage("Please wait until your previous document get verified");
            }
        }
    }

    /***/
    private void previewWithImageViewer(FICAPreviewData mFICAPreviewData) {
        ImageViewerDialogFragment mImageViewerDialogFragment = new ImageViewerDialogFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString(Constants.BundleKey.BUNDLE_IMAGEPATH, imageUrl);
//            bundle.putString(BUNDLE_TOOLBAR_TITLE, "FICA Preview");//mToolbarTitle.getText().toString()
//            mImageViewerDialogFragment.setArguments(bundle);
        mImageViewerDialogFragment.setFICAPreviewData(mFICAPreviewData);
        mImageViewerDialogFragment.show(getSupportFragmentManager(), "ImageViewer");
    }

    /***/
    private boolean isFicaStatusIsNotNull(FicaDocumentStatus ficaDocumentStatus) {
        return ficaDocumentStatus != null;
    }

    private String frontUrl, backUrl;

    @Override
    public void setContentText(int uploadContentType) {
        FicaDocumentStatus ficaDocumentStatus = iUploadPresenter.getFicaDocumentStatus();
        switch (uploadContentType) {
            case Constants.FicaContentType.CONTENT_BANK_STATEMENT: //  single picture upload
                mToolbarTitle.setText(R.string.title_bank_statement);
                vTvDescription.setText(R.string.txt_title_bank_statement);
                mLayoutBack.setVisibility(View.GONE);
                vTvFrontText.setVisibility(View.GONE);
                if (isFicaStatusIsNotNull(ficaDocumentStatus)) {
                    FicaItem bankStatement = ficaDocumentStatus.getBankStatement();
                    if (bankStatement != null && bankStatement.isUploaded()) {
                        frontUrl = bankStatement.getFrontThumbnail();
                        TraderApplication.getInstance().loadImage(bankStatement.getFrontThumbnail(), mImageFront);
                    }
                }
                break;
            case Constants.FicaContentType.CONTENT_ID_CARD: // front and back pictures
                mToolbarTitle.setText(R.string.title_id_card);
                if (isFicaStatusIsNotNull(ficaDocumentStatus)) {
                    FicaItem bankStatement = ficaDocumentStatus.getIdCard();
                    if (bankStatement != null && bankStatement.isUploaded()) {
                        frontUrl = bankStatement.getFrontThumbnail();
                        backUrl = bankStatement.getBackThumbnail();
                        TraderApplication.getInstance().loadImage(bankStatement.getFrontThumbnail(), mImageFront);
                        TraderApplication.getInstance().loadImage(bankStatement.getBackThumbnail(), mImageBack);
                    }
                }
                break;
            case Constants.FicaContentType.CONTENT_GREEN_CARD_ID: // single picture upload
                vTvDescription.setText(R.string.txt_title_id_document);
                mToolbarTitle.setText("ID Document");
                vTvFrontText.setVisibility(View.GONE);
                mLayoutBack.setVisibility(View.GONE);
                if (isFicaStatusIsNotNull(ficaDocumentStatus)) {
                    FicaItem bankStatement = ficaDocumentStatus.getGreenIdCard();
                    if (bankStatement != null && bankStatement.isUploaded()) {
                        frontUrl = bankStatement.getFrontThumbnail();
                        TraderApplication.getInstance().loadImage(bankStatement.getFrontThumbnail(), mImageFront);
                    }
                }
                break;
        }

        if (!TextUtils.isEmpty(frontUrl)) {
            ibPreviewFront.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(backUrl)) {
            ibPreviewBack.setVisibility(View.VISIBLE);
        }
    }

    /***/
    private void showChangePhotoBack() {
        mTvUploadIconBack.setVisibility(View.GONE);
        tvChangePhotoBack.setVisibility(View.VISIBLE);
    }

    /***/
    private void showChangePhotoFront() {
        mTvUploadIconFront.setVisibility(View.GONE);
        tvChangePhotoFront.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestCameraPermission() {
        iPermissionHandler.callCameraPermissionHandler();
    }

    File fileFront, fileBack;

    @Override
    public void displayImage(int imageType, File image) {
        switch (imageType) {
            case Constants.FicaContentType.BACK_IMAGE:
                fileBack = image;
                ibPreviewBack.setVisibility(View.VISIBLE);
                TraderApplication.getInstance().loadImage(image, mImageBack);
                showChangePhotoBack();
                break;
            case Constants.FicaContentType.FRONT_IMAGE:
                fileFront = image;
                ibPreviewFront.setVisibility(View.VISIBLE);
                TraderApplication.getInstance().loadImage(image, mImageFront);
                showChangePhotoFront();
                break;
        }
        int type = iUploadPresenter.currentType();
        switch (type) {
            case Constants.FicaContentType.CONTENT_BANK_STATEMENT:
                setCompleteButtonColor(); // Single image required
                break;
            case Constants.FicaContentType.CONTENT_ID_CARD:
                //Front and back required.
                if (iUploadPresenter.isFrontAndBackSelected()) {
                    setCompleteButtonColor();
                }
                break;
            case Constants.FicaContentType.CONTENT_GREEN_CARD_ID:
                setCompleteButtonColor(); // Single image required
                break;
        }
    }

    @Override
    public void callTrustAccountApi() {
        Intent updatePriceService = new Intent(getActivity(), SyncAccountDetailService.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.AccountSync.BUNDLE_SYNC_TYPE, Constants.AccountSync.CHECK_TRUSTED_ACCOUNT_STATUS);
        updatePriceService.putExtras(bundle);
        startService(updatePriceService);
    }


    private void setCompleteButtonColor() {
        vBtnComplete.setBackgroundColor(ContextCompat.getColor(this, R.color.homePage));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case IPermissionHandler.PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    onReceivedPermissionStatus(IPermissionHandler.PERMISSIONS_REQUEST_CAMERA, true);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    onReceivedPermissionStatus(IPermissionHandler.PERMISSIONS_REQUEST_CAMERA, false);
                }

            }
        }
    }

    @Override
    public void onReceivedPermissionStatus(int code, boolean isGrated) {
        switch (code) {
            case IPermissionHandler.PERMISSIONS_REQUEST_CAMERA:
                if (isGrated) {
                    iUploadPresenter.showImagePickerDialog();
                } else {
                    iPermissionHandler.showPermissionExplainDialog(UploadActivity.this, getString(R.string.txt_permission_request_camera_storage));
                }
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(UploadActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iUploadPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }
}
