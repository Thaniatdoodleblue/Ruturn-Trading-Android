package com.returntrader.model.dto.request;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by moorthy on 12/1/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class BankDetailRequest extends BaseRequest {

    @JsonField(name = "accountHolderName")
    private String accountHolderName;

    @JsonField(name = "bankAccountType")
    private String bankAccountType;

    @JsonField(name = "bankName")
    private String bankName;

    @JsonField(name = "branchCode")
    private String branchCode;

    @JsonField(name = "branchName")
    private String branchName;

    @JsonField(name = "bankLocationCountryId")
    private String bankLocationCountryId;

    @JsonField(name = "accountNumber")
    private String accountNumber;


    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getBankLocationCountryId() {
        return bankLocationCountryId;
    }

    public void setBankLocationCountryId(String bankLocationCountryId) {
        this.bankLocationCountryId = bankLocationCountryId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
