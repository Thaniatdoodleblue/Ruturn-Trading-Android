package com.returntrader.presenter;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.library.Log;
import com.returntrader.model.common.FicaDocumentStatus;
import com.returntrader.model.common.FicaItem;
import com.returntrader.model.dto.request.FicaDocumentRequest;
import com.returntrader.model.dto.response.FicaDocumentUploadResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.FicaDocumentModel;
import com.returntrader.presenter.ipresenter.IUploadPresenter;
import com.returntrader.utils.ImagePickerContext;
import com.returntrader.view.iview.IUploadView;
//import com.soundcloud.android.crop.Crop;

import java.io.File;

import static com.returntrader.common.Constants.BundleKey.BUNDLE_BANKSTATUS;
//import static com.returntrader.common.Constants.BundleKey.BUNDLE_ENTRY_TYPE;
import static com.returntrader.common.Constants.BundleKey.BUNDLE_IDCARDSTATUS;
import static com.returntrader.common.Constants.BundleKey.BUNDLE_VERIFICATIONSTATUS;
import static com.returntrader.common.Constants.FicaContentType.CONTENT_GREEN_CARD_ID;
import static com.returntrader.common.Constants.FicaContentType.CONTENT_ID_CARD;

/**
 * Created by moorthy on 8/26/2017.
 */

public class UploadPresenter extends BasePresenter implements IUploadPresenter {


    private IUploadView iUploadView;
    private ImagePickerContext mImagePickerContext;
    private File mFrontPageImage = null;
    private File mBackPageImage = null;
    private int mCurrentUploadPage = -1;
    private int mUploadContentType;
    private AppConfigurationManager mAppConfigurationManager;
    private FicaDocumentModel mFicaDocumentModel;

    private boolean idCardStatus;
    private boolean bankStatus;
    private boolean verificationStatus;

    public UploadPresenter(IUploadView iView) {
        super(iView);
        this.iUploadView = iView;
        this.mImagePickerContext = new ImagePickerContext();
        this.mAppConfigurationManager = new AppConfigurationManager(iView.getActivity());
        this.mFicaDocumentModel = new FicaDocumentModel(ficaDocumentUploadListener);
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (bundle != null) {
            mUploadContentType = bundle.getInt(Constants.BundleKey.BUNDLE_CONTENT_TYPE);
            idCardStatus = bundle.getBoolean(BUNDLE_IDCARDSTATUS);
            bankStatus = bundle.getBoolean(BUNDLE_BANKSTATUS);
            verificationStatus = bundle.getBoolean(BUNDLE_VERIFICATIONSTATUS);
            iUploadView.setContentText(mUploadContentType);
        }
    }


    @Override
    public int currentType() {
        return mUploadContentType;
    }

    @Override
    public void onUploadFront() {
        setCurrentUploadType(Constants.FicaContentType.FRONT_IMAGE);
        checkPermission();
    }


    @Override
    public void onUploadBack() {
        setCurrentUploadType(Constants.FicaContentType.BACK_IMAGE);
        checkPermission();
    }


    private void setCurrentUploadType(int section) {
        this.mCurrentUploadPage = section;
    }


    private void checkPermission() {
        if (iUploadView.getCodeSnippet().isAboveMarshmallow()) {
            iUploadView.requestCameraPermission();
        } else {
            showImagePickerDialog();
        }
    }

    @Override
    public void showImagePickerDialog() {
        mImagePickerContext.showChooserDialog(iUploadView.getActivity());
    }


    @Override
    public boolean isFrontAndBackSelected() {
        return (mFrontPageImage != null && mBackPageImage != null);
    }

    @Override
    public void onClickComplete() {
        FicaDocumentRequest ficaDocumentRequest = new FicaDocumentRequest(mAppConfigurationManager.getUserId());
        switch (currentType()) {
            case Constants.FicaContentType.CONTENT_BANK_STATEMENT:
                if (mFrontPageImage != null) {
                    iUploadView.showProgressbar();
                    ficaDocumentRequest.setFrontFile(mFrontPageImage);
                    mFicaDocumentModel.submitBankStatement(Constants.FicaContentType.CONTENT_BANK_STATEMENT, ficaDocumentRequest);
                } else {
                    iUploadView.showMessage("Please choose document");
                }
                break;
            case CONTENT_GREEN_CARD_ID:
                if (mFrontPageImage != null) {
                    iUploadView.showProgressbar();
                    ficaDocumentRequest.setFrontFile(mFrontPageImage);
                    mFicaDocumentModel.submitIdCard(CONTENT_GREEN_CARD_ID, ficaDocumentRequest); // We need to change
                } else {
                    iUploadView.showMessage("Please choose document");
                }
                break;
            case CONTENT_ID_CARD:
                if (isFrontAndBackSelected()) {
                    iUploadView.showProgressbar();
                    ficaDocumentRequest.setFrontFile(mFrontPageImage);
                    ficaDocumentRequest.setBackFile(mBackPageImage);
                    mFicaDocumentModel.submitGreenCard(CONTENT_ID_CARD, ficaDocumentRequest);
                } else {
                    iUploadView.showMessage("Please choose document");
                }
                break;
        }
    }

    @Override
    public FicaDocumentStatus getFicaDocumentStatus() {
        FicaDocumentStatus ficaDocumentStatus = new FicaDocumentStatus();
        String ficaStatus = mAppConfigurationManager.getFicaDocStatus();
        if (!(TextUtils.isEmpty(ficaStatus))) {
            ficaDocumentStatus = iUploadView.getCodeSnippet().getObjectFromJson(ficaStatus, FicaDocumentStatus.class);
        }
        return ficaDocumentStatus;
    }

    @Override
    public boolean getUploadStatus() {
        if (mUploadContentType == CONTENT_GREEN_CARD_ID || mUploadContentType == CONTENT_ID_CARD) {
            if (idCardStatus && !verificationStatus) {
                return false;
            } else {
                return true;
            }
        } else {
            if (bankStatus && !verificationStatus) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public void onActivityResultPresenter(int requestCode, int resultCode, Intent data) {
        super.onActivityResultPresenter(requestCode, resultCode, data);

        int aspectX = 1;
        int aspectY = 1;

        switch (requestCode) {
            case Constants.RequestCodes.PICK_CAMERA_IMAGE_REQUEST_CODE:
                File file = new File(mImagePickerContext.getSelectedImagePath());
                if (!file.exists()) {
                    return;
                }
//                Crop.of(Uri.fromFile(file), Uri.fromFile(file))
//                        .withAspect(aspectX, aspectY)
//                        .start(iUploadView.getActivity());
                setPageImage(file);
                break;

            case Constants.RequestCodes.PICK_GALLERY_IMAGE_REQUEST_CODE:
                if (data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = iUploadView.getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        if (picturePath != null) {
                            File source = new File(picturePath);
                            if (!source.exists()) {
                                return;
                            }
//                           Crop.of(Uri.fromFile(source), mImagePickerContext.getImageCaptureUri())
//                                    .withAspect(aspectX, aspectY)
//                                    .start(iUploadView.getActivity());
                            setPageImage(source);
                        }
                    }
                }
                break;
           /* case Crop.REQUEST_CROP:
                if (data == null) {
                    return;
                }
                // if nothing received
                if (Crop.getOutput(data) != null) {
                    File croppedFile = new File(Crop.getOutput(data).getPath());
                    Log.d(TAG, "selected file : " + croppedFile.getAbsolutePath());
                    if (croppedFile.exists()) {
                        setPageImage(croppedFile);
                    }
                }
                break;*/
        }
    }

    /***/
    private void setPageImage(File croppedImage) {
        switch (mCurrentUploadPage) {
            case Constants.FicaContentType.FRONT_IMAGE:
                this.mFrontPageImage = croppedImage;
                break;
            case Constants.FicaContentType.BACK_IMAGE:
                this.mBackPageImage = croppedImage;
                break;
        }
        iUploadView.displayImage(mCurrentUploadPage, croppedImage);
    }


    /***/
    private IBaseModelListener<FicaDocumentUploadResponse> ficaDocumentUploadListener = new IBaseModelListener<FicaDocumentUploadResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, FicaDocumentUploadResponse response) {
            iUploadView.dismissProgressbar();
            if (response.getFicaItem() == null) {
                return;
            }

            FicaDocumentStatus ficaDocumentStatus = getFicaDocumentStatus();

            if (ficaDocumentStatus == null) {
                return;
            }
            // doc uploaded
            mAppConfigurationManager.setFicaDetailCompletedStatus(true);

            switch ((int) taskId) {
                case CONTENT_GREEN_CARD_ID:
                    ficaDocumentStatus.setGreenIdCard(getFicaStatus(response.getFicaItem()));
                    break;
                case CONTENT_ID_CARD:
                    ficaDocumentStatus.setIdCard(getFicaStatus(response.getFicaItem()));
                    break;
                case Constants.FicaContentType.CONTENT_BANK_STATEMENT:
                    ficaDocumentStatus.setBankStatement(getFicaStatus(response.getFicaItem()));
                    break;
            }
            String convertedStatus = iUploadView.getCodeSnippet().getJsonStringFromObject(ficaDocumentStatus, FicaDocumentStatus.class);
            if (!(TextUtils.isEmpty(convertedStatus))) {
                mAppConfigurationManager.setFicaDocStatus(convertedStatus);
            }
            iUploadView.callTrustAccountApi();
            iUploadView.showMessage(iUploadView.getActivity().getString(R.string.txt_upload_success));
            iUploadView.getActivity().finish();
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iUploadView.dismissProgressbar();
            if (e != null) {
                iUploadView.showMessage(e.getException());
            }
        }
    };

    /***/
    private FicaItem getFicaStatus(FicaItem response) {
        response.setUploaded(true);
        return response;
    }
}
