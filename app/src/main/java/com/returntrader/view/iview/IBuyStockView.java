package com.returntrader.view.iview;

import com.returntrader.model.common.LineGraphDataSet;
import com.returntrader.model.common.StockHold;

/**
 * Created by Dell on 23-05-2017.
 */

public interface IBuyStockView extends IView {

    void setStockDetails(StockHold stockDetails);


    void setTotalTransactionCost(String totalTransactionCost, String totalSharePrice);

    void doUpdateBalance();

    void showQuestions();

    void postOrderProgress(int resultCode);

    void setUpTopBar();
}
