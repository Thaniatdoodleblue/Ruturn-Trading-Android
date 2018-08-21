package com.returntrader.adapter.listener;

import com.returntrader.model.common.Option;

/**
 * Created by moorthy on 7/26/2017.
 */

public interface ISettingsAdapterListener {

    void onClickGeneral(Option data, int position);

    void onClickSocial(Option data, int position);

    void onClickSettings(Option data, int position);
}
