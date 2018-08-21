package com.returntrader.view.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.adapter.ContactListAdapter;
import com.returntrader.common.TraderApplication;
import com.returntrader.permissions.IPermissionHandler;
import com.returntrader.permissions.RequestPermission;
import com.returntrader.presenter.ContactInvitePresenter;
import com.returntrader.presenter.ipresenter.IContactInvitePresenter;
import com.returntrader.view.iview.IContactInviteView;

/**
 * Created by moorthy on 8/2/2017.
 */

public class ContactInviteActivity extends BaseActivity implements IContactInviteView, com.returntrader.permissions.PermissionProducer {
    private IContactInvitePresenter iContactInvitePresenter;
    private RecyclerView mRvContactList;
    private EditText vEtSearch;
    private IPermissionHandler iPermissionHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bindActivity();
        iPermissionHandler = RequestPermission.newInstance(this);
        iContactInvitePresenter = new ContactInvitePresenter(this);
        iContactInvitePresenter.onCreatePresenter(getIntent().getExtras());
    }

    private void bindActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.txt_menu_invite_phone_contact);

        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRvContactList = findViewById(R.id.recycler_contacts);
        vEtSearch = findViewById(R.id.et_search);
        vEtSearch.addTextChangedListener(textWatcher);
        mRvContactList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s != null && !TextUtils.isEmpty(s)) {
               iContactInvitePresenter.searchFilter(String.valueOf(s));
            }else {
                iContactInvitePresenter.searchFilter(null);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public void sendSms(String phoneNumber) {
        try {
            String appUrl = TraderApplication.getInstance().getAppUrl();
            Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
            sendIntent.putExtra("sms_body", getString(R.string.txt_share_content, appUrl));
            sendIntent.putExtra("address", phoneNumber);
            sendIntent.setType("vnd.android-dir/mms-sms");
            startActivity(sendIntent);
        } catch (Exception e) {
            showMessage(getString(R.string.txt_no_application));
            e.printStackTrace();
        }
    }

    @Override
    public void requestContactPermission() {
        iPermissionHandler.callFetchContactPermissionHandler();
    }


    protected int getLayoutId() {
        return R.layout.activity_invite_contact;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(ContactInviteActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void setContactListAdapter(ContactListAdapter contactListAdapter) {
        mRvContactList.setAdapter(contactListAdapter);
    }

    @Override
    public void onReceivedPermissionStatus(int code, boolean isGrated) {
        switch (code) {
            case IPermissionHandler.PERMISSIONS_REQUEST_ACCESS_CONTACT:
                if (isGrated) {
                    iContactInvitePresenter.getContacts();
                } else {
                    iPermissionHandler.showPermissionExplainDialog(ContactInviteActivity.this, getString(R.string.txt_permission_request_contact));
                }
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case IPermissionHandler.PERMISSIONS_REQUEST_ACCESS_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    onReceivedPermissionStatus(IPermissionHandler.PERMISSIONS_REQUEST_ACCESS_CONTACT, true);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    onReceivedPermissionStatus(IPermissionHandler.PERMISSIONS_REQUEST_ACCESS_CONTACT, false);
                }

            }
        }
    }
}
