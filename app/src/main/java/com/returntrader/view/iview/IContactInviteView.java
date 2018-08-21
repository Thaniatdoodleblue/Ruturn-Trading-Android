package com.returntrader.view.iview;

import com.annimon.stream.Stream;
import com.returntrader.adapter.ContactListAdapter;

/**
 * Created by moorthy on 8/10/2017.
 */

public interface IContactInviteView extends IView {

    void setContactListAdapter(ContactListAdapter contactListAdapter);

    void sendSms(String phoneNumber);

    void requestContactPermission();

}
