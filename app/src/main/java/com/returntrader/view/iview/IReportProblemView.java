package com.returntrader.view.iview;

import android.os.Bundle;

/**
 * Created by moorthy on 11/17/2017.
 */

public interface IReportProblemView extends IView {

    void clearText();

    void canViewReport(int canSee);

    void navigateToViewReports(Bundle bundle);

    void canViewUpdates(int canShow);
}
