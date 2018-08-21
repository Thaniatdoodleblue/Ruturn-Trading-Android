package com.returntrader.presenter.ipresenter;

import com.returntrader.model.common.ContactItem;

import java.util.List;

/**
 * Created by moorthy on 8/10/2017.
 */

public interface IContactInvitePresenter extends IPresenter {

    List<String> fetchContactList();

    void getContacts();

    void searchFilter(String value);
}
