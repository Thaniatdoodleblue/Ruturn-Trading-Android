package com.returntrader.model.common;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.dto.response.BaseResponse;

import java.util.List;

/**
 * Created by moorthy on 10/26/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ConfigData extends BaseResponse {

    @JsonField(name = "titles")
    private List<Title> titles;

    @JsonField(name = "eftBanks")
    private List<BankItem> eftBanks;

    @JsonField(name = "tradingCurrencies")
    private List<TradingCurrency> tradingCurrencies;

    @JsonField(name = "banks")
    private List<String> banks;

    @JsonField(name = "provinces")
    private List<Province> provinces;

    @JsonField(name = "countries")
    private List<Country> countries;

    @JsonField(name = "debitOrderTriggerDays")
    private List<Integer> debitOrderTriggerDays;

    @JsonField(name = "marketingChannels")
    private List<String> marketingChannels;

    @JsonField(name = "securityQuestions")
    private List<String> securityQuestions;

    @JsonField(name = "incomeBands")
    private List<IncomeBand> incomeBands;

    @JsonField(name = "maritalStatuses")
    private List<MaritalStatus> maritalStatuses;

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public List<BankItem> getEftBanks() {
        return eftBanks;
    }

    public void setEftBanks(List<BankItem> eftBanks) {
        this.eftBanks = eftBanks;
    }

    public List<TradingCurrency> getTradingCurrencies() {
        return tradingCurrencies;
    }

    public void setTradingCurrencies(List<TradingCurrency> tradingCurrencies) {
        this.tradingCurrencies = tradingCurrencies;
    }

    public List<String> getBanks() {
        return banks;
    }

    public void setBanks(List<String> banks) {
        this.banks = banks;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Integer> getDebitOrderTriggerDays() {
        return debitOrderTriggerDays;
    }

    public void setDebitOrderTriggerDays(List<Integer> debitOrderTriggerDays) {
        this.debitOrderTriggerDays = debitOrderTriggerDays;
    }

    public List<String> getMarketingChannels() {
        return marketingChannels;
    }

    public void setMarketingChannels(List<String> marketingChannels) {
        this.marketingChannels = marketingChannels;
    }

    public List<String> getSecurityQuestions() {
        return securityQuestions;
    }

    public void setSecurityQuestions(List<String> securityQuestions) {
        this.securityQuestions = securityQuestions;
    }

    public List<IncomeBand> getIncomeBands() {
        return incomeBands;
    }

    public void setIncomeBands(List<IncomeBand> incomeBands) {
        this.incomeBands = incomeBands;
    }

    public List<MaritalStatus> getMaritalStatuses() {
        return maritalStatuses;
    }

    public void setMaritalStatuses(List<MaritalStatus> maritalStatuses) {
        this.maritalStatuses = maritalStatuses;
    }
}
