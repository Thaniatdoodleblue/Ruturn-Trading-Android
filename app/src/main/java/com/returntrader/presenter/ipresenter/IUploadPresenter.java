package com.returntrader.presenter.ipresenter;

import com.returntrader.model.common.FicaDocumentStatus;

/**
 * Created by moorthy on 8/26/2017.
 */

public interface IUploadPresenter extends IPresenter {

    void onUploadFront();

    void onUploadBack();

    void showImagePickerDialog();

    int currentType();

    boolean isFrontAndBackSelected();

    void onClickComplete();

    FicaDocumentStatus getFicaDocumentStatus();

    boolean getUploadStatus();
}
