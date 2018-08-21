package com.returntrader.model.dto.request;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.io.File;

/**
 * Created by moorthy on 11/6/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class FicaDocumentRequest extends BaseRequest {


    private File frontFile;

    private File backFile;

    public FicaDocumentRequest() {
    }

    public FicaDocumentRequest(String userId) {
        super(userId);
    }

    public File getFrontFile() {
        return frontFile;
    }

    public void setFrontFile(File frontFile) {
        this.frontFile = frontFile;
    }

    public File getBackFile() {
        return backFile;
    }

    public void setBackFile(File backFile) {
        this.backFile = backFile;
    }
}
