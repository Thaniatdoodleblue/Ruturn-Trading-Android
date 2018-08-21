package com.returntrader.view.iview;

import android.widget.ArrayAdapter;

/**
 * Created by moorthy on 8/29/2017.
 */

public interface IBankDetailsView extends IView {

    void setBankListAdapter(ArrayAdapter<String> bankListAdapter);

    void setAccountTypeAdapter(ArrayAdapter<String> accountTypeAdapter);

    void redirectToPrevious();
}
