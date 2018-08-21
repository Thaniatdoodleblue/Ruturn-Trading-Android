package com.returntrader.view.iview;

import android.net.Uri;

import com.returntrader.adapter.NotificationAdapter;

/**
 * Created by moorthy on 11/23/2017.
 */

public interface INotificationView extends IView {

    void setNotificationListAdapter(NotificationAdapter notificationAdapter);

    void navigateToLink(Uri data);
}
