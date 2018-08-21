package com.returntrader.model.common;

/**
 * Created by moorthy on 11/7/2017.
 */

public class FicaDocumentStatus {

    private FicaItem greenIdCard;

    private FicaItem idCard;

    private FicaItem bankStatement;

    public FicaItem getGreenIdCard() {
        return greenIdCard;
    }

    public void setGreenIdCard(FicaItem greenIdCard) {
        this.greenIdCard = greenIdCard;
    }

    public FicaItem getIdCard() {
        return idCard;
    }

    public void setIdCard(FicaItem idCard) {
        this.idCard = idCard;
    }

    public FicaItem getBankStatement() {
        return bankStatement;
    }

    public void setBankStatement(FicaItem bankStatement) {
        this.bankStatement = bankStatement;
    }
}
