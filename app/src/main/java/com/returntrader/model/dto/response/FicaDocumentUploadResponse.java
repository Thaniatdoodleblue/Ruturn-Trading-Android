package com.returntrader.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.common.FicaItem;

/**
 * Created by moorthy on 11/6/2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class FicaDocumentUploadResponse extends BaseResponse {

    @JsonField(name = "URL")
    private FicaItem ficaItem;


    public FicaItem getFicaItem() {
        return ficaItem;
    }

    public void setFicaItem(FicaItem ficaItem) {
        this.ficaItem = ficaItem;
    }
}
