package com.returntrader.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.common.ContactItem;

import java.util.List;

/**
 * Created by moorthy on 8/10/2017.
 */


@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ContactSyncResponse extends BaseResponse {

    @JsonField(name = "user_invite")
    private List<ContactItem> contactItemList;

    public List<ContactItem> getContactItemList() {
        return contactItemList;
    }

    public void setContactItemList(List<ContactItem> contactItemList) {
        this.contactItemList = contactItemList;
    }
}
