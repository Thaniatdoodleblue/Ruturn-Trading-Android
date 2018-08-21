package com.returntrader.view.iview;

import com.returntrader.database.CompanyItemInfo;
import com.returntrader.model.common.DayHistoryDo;
import com.returntrader.model.common.LineGraphDataSet;

/**
 * Created by moorthy on 7/12/2017.
 */

public interface IGraphView extends IView {

    void setGraphView(LineGraphDataSet lineGraphDataSet);

    void setShareInfo(CompanyItemInfo itemInfo);

    void setMinMaxValue(Float open, Float min, Float max);
}
