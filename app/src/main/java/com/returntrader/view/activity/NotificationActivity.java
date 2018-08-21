package com.returntrader.view.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.returntrader.R;
import com.returntrader.adapter.NotificationAdapter;
import com.returntrader.library.Log;
import com.returntrader.presenter.NotificationPresenter;
import com.returntrader.presenter.ipresenter.INotificationPresenter;
import com.returntrader.view.iview.INotificationView;

/**
 * Created by moorthy on 7/26/2017.
 */

public class NotificationActivity extends BaseActivity implements INotificationView {


    private INotificationPresenter iNotificationPresenter;
    private RecyclerView mRvReports;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notification);
        bindActivity();
        iNotificationPresenter = new NotificationPresenter(this);
        iNotificationPresenter.onCreatePresenter(getIntent().getExtras());

    }

    private void bindActivity() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRvReports =  findViewById(R.id.recycler_notification);
        mRvReports.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(NotificationActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void setNotificationListAdapter(NotificationAdapter notificationAdapter) {
        mRvReports.setAdapter(notificationAdapter);
    }

    @Override
    public void navigateToLink(Uri data) {
        Log.d("Data***", "" + data);
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(data);
            startActivity(browserIntent);
        } catch (ActivityNotFoundException e) {
            showMessage(getString(R.string.txt_no_application));
            e.printStackTrace();
        }
    }
}
