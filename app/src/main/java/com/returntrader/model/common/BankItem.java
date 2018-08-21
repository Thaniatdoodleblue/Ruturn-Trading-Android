package com.returntrader.model.common;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.dto.request.BaseRequest;

/**
 * Created by moorthy on 7/26/2017.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class BankItem extends BaseRequest {

    @JsonField(name = "labelName")
    private String labelName;

    @JsonField(name = "bankName")
    private String bankName;

    @JsonField(name = "allowDeposit")
    private String allowDeposit;

    @JsonField(name = "accountNumber")
    private String accountNumber;

    @JsonField(name = "branchCode")
    private String branchCode;

    @JsonField(name = "branchName")
    private String branchName;

    @JsonField(name = "accountHolder")
    private String accountHolder;

    @JsonField(name = "currency")
    private String currency;

    @JsonField(name = "country")
    private String country;

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAllowDeposit() {
        return allowDeposit;
    }

    public void setAllowDeposit(String allowDeposit) {
        this.allowDeposit = allowDeposit;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
