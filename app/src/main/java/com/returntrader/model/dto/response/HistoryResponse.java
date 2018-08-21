package com.returntrader.model.dto.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.imodel.HistoricPriceLogs;

import java.util.List;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class HistoryResponse extends BaseResponse {

    @JsonField(name = "historicpricelogs")
    private List<HistoricPriceLogs> graphHistoryData;

    public List<HistoricPriceLogs> getGraphHistoryData() {
        return graphHistoryData;
    }

    public void setGraphHistoryData(List<HistoricPriceLogs> graphHistoryData) {
        this.graphHistoryData = graphHistoryData;
    }
}
