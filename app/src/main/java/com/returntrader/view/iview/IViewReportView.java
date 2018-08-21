package com.returntrader.view.iview;

import android.widget.ArrayAdapter;

import com.returntrader.adapter.BankListAdapter;
import com.returntrader.adapter.ViewReportAdapter;
import com.returntrader.model.common.BankItem;

/**
 * Created by moorthy on 7/26/2017.
 */

public interface IViewReportView extends IView {

    void setReportListAdapter(ViewReportAdapter reportListAdapter);

}
