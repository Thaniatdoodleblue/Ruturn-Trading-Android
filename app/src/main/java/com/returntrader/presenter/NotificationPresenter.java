package com.returntrader.presenter;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.returntrader.adapter.NotificationAdapter;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.database.DatabaseManager;
import com.returntrader.database.NotificationMeta;
import com.returntrader.presenter.ipresenter.INotificationPresenter;
import com.returntrader.view.iview.INotificationView;

import java.util.Collections;
import java.util.List;

/**
 * Created by moorthy on 11/23/2017.
 */

public class NotificationPresenter extends BasePresenter implements INotificationPresenter {


    private INotificationView iNotificationView;
    private DatabaseManager mDatabaseManager;
    private NotificationAdapter mNotificationAdapter;

    public NotificationPresenter(INotificationView iView) {
        super(iView);
        this.iNotificationView = iView;
        this.mDatabaseManager = new DatabaseManager(iView.getActivity());
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        fetchNotificationList();
    }


    private void setNotificationAdapter(List<NotificationMeta> notificationMetaList) {

        if (notificationMetaList == null) {
            return;
        }

        if (mNotificationAdapter == null) {
            mNotificationAdapter = new NotificationAdapter(notificationMetaList, notificationClickListener);
            iNotificationView.setNotificationListAdapter(mNotificationAdapter);
        } else {
            mNotificationAdapter.resetItems(notificationMetaList);
        }
    }


    private BaseRecyclerAdapterListener<NotificationMeta> notificationClickListener = new BaseRecyclerAdapterListener<NotificationMeta>() {
        @Override
        public void onClickItem(NotificationMeta data, int position) {
            if (!TextUtils.isEmpty(data.getWebLink())) {
                iNotificationView.navigateToLink(Uri.parse(data.getWebLink().contains("http://") ? data.getWebLink() : "https://" + data.getWebLink()));
            }
        }
    };

    private void fetchNotificationList() {
        List<NotificationMeta> notificationList = mDatabaseManager.getNotificationList();
        if (notificationList != null) {
            Collections.reverse(notificationList);
            setNotificationAdapter(notificationList);
        }
    }
}
