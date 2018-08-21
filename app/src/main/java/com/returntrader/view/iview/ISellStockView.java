package com.returntrader.view.iview;

import com.returntrader.model.common.SellNetAmount;
import com.returntrader.model.common.StockHold;

/**
 * Created by moorthy on 9/20/2017.
 */

public interface ISellStockView extends IView {

    void setStockDetails(StockHold currentStock);

    void setPercentageValue(String percentageValue);

    void setRandValue(String randValue);

    void setNetAmount(SellNetAmount netAmount);

    void doBalanceUpdate();

    void postOrderProgress(int resultOk);

    void showQuestions();

    String getSellPercentage();

    void setUpTopBar();
}
