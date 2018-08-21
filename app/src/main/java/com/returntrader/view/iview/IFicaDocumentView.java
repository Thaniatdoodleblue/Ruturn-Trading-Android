package com.returntrader.view.iview;

/**
 * Created by moorthy on 11/7/2017.
 */

public interface IFicaDocumentView extends IView {

    void setIdCardStatus(boolean isUploaded);

    void setBankStatementStatus(boolean isUploaded);

    void setUploadStatusText(int statusText);

    void showStatusPending(boolean isPendingOrVerified);
}
