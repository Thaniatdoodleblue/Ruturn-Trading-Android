package com.returntrader.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.common.BalanceItem;

import java.util.List;

/**
 * Created by moorthy on 9/15/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class BalanceResponse extends BaseResponse {

    @JsonField(name = "balance")
    private List<BalanceItem> balanceItems;

    public List<BalanceItem> getBalanceItems() {
        return balanceItems;
    }

    public void setBalanceItems(List<BalanceItem> balanceItems) {
        this.balanceItems = balanceItems;
    }
}
