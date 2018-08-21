package com.returntrader.model.dto.request;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.common.ContactItem;
import com.returntrader.model.dto.response.BaseResponse;

import java.util.List;

/**
 * Created by moorthy on 8/10/2017.
 */


@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ContactSyncRequest  {

    @JsonField(name = "numbers")
    private List<String> contactItemList;

    public ContactSyncRequest() {
    }

    public List<String> getContactItemList() {
        return contactItemList;
    }

    public void setContactItemList(List<String> contactItemList) {
        this.contactItemList = contactItemList;
    }

    public ContactSyncRequest(List<String> contactItemList) {
        this.contactItemList = contactItemList;
    }
}
