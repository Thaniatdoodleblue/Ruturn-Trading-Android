package com.returntrader.sync.listener;

import com.returntrader.database.CompanyItemInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by moorthy on 9/9/2017.
 */

public interface ISyncStatusListener {

    Observable<Boolean> onObserver();
}
