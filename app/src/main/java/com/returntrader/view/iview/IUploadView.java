package com.returntrader.view.iview;

import java.io.File;

/**
 * Created by moorthy on 8/26/2017.
 */

public interface IUploadView extends IView {

    void setContentText(int uploadContentType);

    void requestCameraPermission();

    void displayImage(int imageType, File displayImage);

    void callTrustAccountApi();
}
