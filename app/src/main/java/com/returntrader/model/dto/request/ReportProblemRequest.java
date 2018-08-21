package com.returntrader.model.dto.request;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 11/17/2017.
 */


@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ReportProblemRequest extends BaseRequest {

    @JsonField(name = "content")
    private String content;

    @JsonField(name = "email")
    private String emailId;

    @JsonField(name = "updateFICA")
    private String updateFICA;

    @JsonField(name = "uploadBank")
    private String uploadBank;

    public ReportProblemRequest() {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUpdateFICA() {
        return updateFICA;
    }

    public void setUpdateFICA(String updateFICA) {
        this.updateFICA = updateFICA;
    }

    public String getUploadBank() {
        return uploadBank;
    }

    public void setUploadBank(String uploadBank) {
        this.uploadBank = uploadBank;
    }
}
