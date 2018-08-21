package com.returntrader.view.iview;

import android.os.Bundle;

import com.returntrader.model.common.LineGraphDataSet;
import com.returntrader.model.common.StockHold;

/**
 * Created by Dell on 23-05-2017.
 */

public interface IBuyAndSellView extends IView {

    void setShareStockInfo(StockHold shareStockInfo);

    void navigateToBuy(Bundle bundle);

    void navigateToSell(Bundle bundle);

    void setGraphView(LineGraphDataSet lineGraphDataSet);

    void setUpTopBar();

    void showHolidayDialog();
}
