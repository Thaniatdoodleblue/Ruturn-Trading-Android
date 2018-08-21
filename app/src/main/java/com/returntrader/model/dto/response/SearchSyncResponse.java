package com.returntrader.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.dto.request.BaseRequest;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class SearchSyncResponse extends BaseRequest {

    private String id;

    private String updated_at;

    private String contarct_code;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getContarct_code() {
        return contarct_code;
    }

    public void setContarct_code(String contarct_code) {
        this.contarct_code = contarct_code;
    }
}
