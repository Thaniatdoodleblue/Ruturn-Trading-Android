package com.returntrader.presenter;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.returntrader.adapter.ContactListAdapter;
import com.returntrader.adapter.listener.BaseRecyclerAdapterListener;
import com.returntrader.library.CustomException;
import com.returntrader.model.common.ContactItem;
import com.returntrader.model.dto.request.ContactSyncRequest;
import com.returntrader.model.dto.response.ContactSyncResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.SyncContactListModel;
import com.returntrader.presenter.ipresenter.IContactInvitePresenter;
import com.returntrader.view.iview.IContactInviteView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by moorthy on 8/10/2017.
 */

public class ContactInvitePresenter extends BasePresenter implements IContactInvitePresenter {
    private IContactInviteView iContactInviteView;
    private SyncContactListModel mSyncContactListModel;
    private List<ContactItem> contactItems;
    private ContactListAdapter contactListAdapter;


    public ContactInvitePresenter(IContactInviteView iView) {
        super(iView);
        this.iContactInviteView = iView;
        contactItems = new ArrayList<>();
        this.mSyncContactListModel = new SyncContactListModel(syncResponseIModelListener);

    }

    @Override
    public void onCreatePresenter(Bundle bundle) {
        if (iContactInviteView.getCodeSnippet().isAboveMarshmallow()) {
            iContactInviteView.requestContactPermission();
        } else {
            getContacts();
        }
    }

    /***/
    private void prepareAdapter(List<ContactItem> contactItems) {

        Collections.sort(contactItems, (obj1, obj2) -> {
            // ## Ascending order
            return (TextUtils.isEmpty(iContactInviteView.getCodeSnippet().getContactName(obj1.getPhoneNumber()))
                    ? "" : iContactInviteView.getCodeSnippet().getContactName(obj1.getPhoneNumber()))
                    .compareToIgnoreCase(TextUtils.isEmpty(iContactInviteView.getCodeSnippet().getContactName(obj2.getPhoneNumber()))
                            ? "" : iContactInviteView.getCodeSnippet().getContactName(obj2.getPhoneNumber()));

        });


        if (contactListAdapter == null) {
            contactListAdapter = new ContactListAdapter(contactItems, adapterListener,
                    iContactInviteView.getCodeSnippet());
            iContactInviteView.setContactListAdapter(contactListAdapter);
        } else {
            contactListAdapter.resetItems(contactItems);
        }

    }


    private BaseRecyclerAdapterListener<ContactItem> adapterListener = new BaseRecyclerAdapterListener<ContactItem>() {
        @Override
        public void onClickItem(ContactItem data, int position) {
            iContactInviteView.sendSms(data.getPhoneNumber());
        }
    };

    @Override
    public List<String> fetchContactList() {
        List<String> contactItems = null;
        ContentResolver cr = iContactInviteView.getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur != null && cur.getCount() > 0) {
            contactItems = new ArrayList<>();
            while (cur.moveToNext()) {

                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String photoUri = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

                // contactItem.setDisplayUri(Uri.parse(photoUri));
                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    if (pCur != null) {
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            if (!(TextUtils.isEmpty(phoneNo))) {
                                phoneNo = phoneNo.replaceAll("[\\D]", "");
                                contactItems.add(phoneNo);
                            }
                        }
                    }
                    if (pCur != null) {
                        pCur.close();
                    }
                }


            }
        }
        return contactItems;
    }

    @Override
    public void getContacts() {

        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                iContactInviteView.showProgressbar();
            }

            @Override
            protected List<String> doInBackground(Void... voids) {
                return fetchContactList();
            }

            @Override
            protected void onPostExecute(List<String> contactNumbers) {
                super.onPostExecute(contactNumbers);
                iContactInviteView.dismissProgressbar();
                if (contactNumbers == null) {
                    return;
                }
                syncWithDataBase(contactNumbers);
            }
        }.execute();

    }


    /*sync contact with database */
    private void syncWithDataBase(List<String> contactItemList) {
        if (iContactInviteView.getCodeSnippet().hasNetworkConnection()) {
            iContactInviteView.showProgressbar();
            mSyncContactListModel.syncContact(0, new ContactSyncRequest(contactItemList));
        } else {
            List<ContactItem> mSourceList = null;
            prepareAdapter(mSourceList);
        }
    }


    private IBaseModelListener<ContactSyncResponse> syncResponseIModelListener = new IBaseModelListener<ContactSyncResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, ContactSyncResponse response) {
            iContactInviteView.dismissProgressbar();
            if (response.getContactItemList() == null) {
                return;
            }
            contactItems = response.getContactItemList();
            prepareAdapter(response.getContactItemList());
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            iContactInviteView.dismissProgressbar();
        }
    };

    @Override
    public void searchFilter(String value) {
        if (!TextUtils.isEmpty(value)) {
            prepareAdapter(Stream.of(contactItems).filter(x -> iContactInviteView.getCodeSnippet().getContactName(x.getPhoneNumber()).toLowerCase().contains(value.toLowerCase())).collect(Collectors.toList()));
        } else prepareAdapter(contactItems);
    }
}
