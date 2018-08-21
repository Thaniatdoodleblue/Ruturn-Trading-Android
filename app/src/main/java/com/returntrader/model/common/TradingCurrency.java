package com.returntrader.model.common;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 10/26/2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class TradingCurrency {

    @JsonField(name = "tradingCurrencyId")
    private String tradingCurrencyId;

    @JsonField(name = "tradingCurrencyName")
    private String tradingCurrencyName;

    @JsonField(name = "currencySymbol")
    private String currencySymbol;

    @JsonField(name = "isSimulated")
    private boolean Simulated;


    public String getTradingCurrencyId() {
        return tradingCurrencyId;
    }

    public void setTradingCurrencyId(String tradingCurrencyId) {
        this.tradingCurrencyId = tradingCurrencyId;
    }

    public String getTradingCurrencyName() {
        return tradingCurrencyName;
    }

    public void setTradingCurrencyName(String tradingCurrencyName) {
        this.tradingCurrencyName = tradingCurrencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public boolean isSimulated() {
        return Simulated;
    }

    public void setSimulated(boolean simulated) {
        Simulated = simulated;
    }
}
